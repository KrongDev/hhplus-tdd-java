package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    //
    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    /**
     * 유저아이디로 유저포인트 현황을 조회한다.
     * @param userId 유저아이디
     * @return UserPointInfo
     */
    @Override
    public UserPointInfo loadPoint(long userId) {
        //
        return this.userPointRepository.findById(userId);
    }

    /**
     * 유저아이디로 Point내역들을 조회한다.
     * @param userId 유저아이디
     * @return List<PointHistoryInfo>
     */
    @Override
    public List<PointHistoryInfo> loadHistory(long userId) {
        //
        return this.pointHistoryRepository.findById(userId);
    }

    /**
     * 유저포인트를 충전한다.
     * 동시성을 위해 synchronized 사용
     * @param userId 유저아이디
     * @param amount 충전할 포인트
     * @return UserPointInfo
     */
    @Override
    synchronized public UserPointInfo chargePoint(long userId, long amount) {
        UserPointInfo userPoint = loadPoint(userId);
        userPoint.chargePoint(amount);
        userPoint = this.userPointRepository.save(userId, userPoint.getPoint());
        this.pointHistoryRepository.save(userId, userPoint.getPoint(), TransactionType.CHARGE, userPoint.getUpdateMillis());
        return userPoint;
    }

    /**
     * 유저포인트를 사용한다.
     * 포인트 사용시 잔여포인트보다 사용할 포인트가 많을 시 오류 발행.
     * @param userId 유저아이디
     * @param amount 사용할 포인트
     * @return UserPointInfo
     */
    @Override
    synchronized public UserPointInfo usePoint(long userId, long amount) {
        UserPointInfo userPoint = loadPoint(userId);
        userPoint.usePoint(amount);
        userPoint = this.userPointRepository.save(userId, userPoint.getPoint());
        this.pointHistoryRepository.save(userId, userPoint.getPoint(), TransactionType.USE, userPoint.getUpdateMillis());
        return userPoint;
    }
}
