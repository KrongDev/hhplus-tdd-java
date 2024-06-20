package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import io.hhplus.tdd.point.util.task.UserTaskQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
    //
    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserTaskQueue taskQueue;
    /**
     * 유저아이디로 유저포인트 현황을 조회한다.
     * @param userId 유저아이디
     * @return UserPointInfo
     */
    public UserPointDomain loadPoint(long userId) {
        return this.userPointRepository.findById(userId);
    }

    /**
     * 유저아이디로 Point내역들을 조회한다.
     * @param userId 유저아이디
     * @return List<PointHistoryInfo>
     */
    public List<PointHistoryDomain> loadHistory(long userId) {
        return this.pointHistoryRepository.findAllByUserId(userId);
    }

    /**
     * 유저포인트를 충전한다.
     * 동시성을 위해 synchronized 사용
     * @param userId 유저아이디
     * @param chargePoint 충전할 포인트
     * @return UserPointInfo
     */
    public UserPointDomain chargePoint(long userId, long chargePoint) throws ExecutionException, InterruptedException {
        log.info("charge point >>> {} ", chargePoint);
        Future<?> task = taskQueue.addTask(userId, () -> {
            if(chargePoint <= 0)
                throw new RuntimeException("충전하려는 금액이 이상합니다");
            UserPointDomain userPointDomain = this.userPointRepository.findById(userId);
            userPointDomain.chargePoint(chargePoint);
            this.userPointRepository.save(userPointDomain.getId(), userPointDomain.getPoint());
            this.pointHistoryRepository.save(userPointDomain.getId(), chargePoint, TransactionType.CHARGE, userPointDomain.getUpdateMillis());
        });
        task.get();
        return this.userPointRepository.findById(userId);
    }

    /**
     * 유저포인트를 사용한다.
     * 포인트 사용시 잔여포인트보다 사용할 포인트가 많을 시 오류 발행.
     * @param userId 유저아이디
     * @param usePoint 사용할 포인트
     * @return UserPointInfo
     */
    public UserPointDomain usePoint(long userId, long usePoint) throws ExecutionException, InterruptedException {
        Future<?> task = taskQueue.addTask(userId, () -> {
            UserPointDomain userPointDomain = this.userPointRepository.findById(userId);
            if(usePoint <= 0)
                throw new RuntimeException("사용하려는 포인트액수가 이상합니다.");
            if(userPointDomain.isPointInsufficient(usePoint))
                throw new RuntimeException("잔액이 부족합니다");
            userPointDomain.usePoint(usePoint);
            this.userPointRepository.save(userPointDomain.getId(), userPointDomain.getPoint());
            this.pointHistoryRepository.save(userPointDomain.getId(), usePoint, TransactionType.USE, userPointDomain.getUpdateMillis());
        });
        task.get();
        return this.userPointRepository.findById(userId);
    }
}
