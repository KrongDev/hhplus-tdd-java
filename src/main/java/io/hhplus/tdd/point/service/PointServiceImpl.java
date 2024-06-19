package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
    //
    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public UserPointDomain loadPoint(long userId) {
        return this.userPointRepository.findById(userId);
    }

    public List<PointHistoryDomain> loadHistory(long userId) {
        return this.pointHistoryRepository.findAllByUserId(userId);
    }

    public UserPointDomain chargePoint(long userId, long chargePoint) {
        if(chargePoint <= 0)
            throw new RuntimeException("충전하려는 금액이 이상합니다");
        UserPointDomain userPointDomain = this.userPointRepository.findById(userId);
        userPointDomain.chargePoint(chargePoint);
        this.userPointRepository.save(userPointDomain.getId(), userPointDomain.getPoint());
        return userPointDomain;
    }

    public UserPointDomain usePoint(long userId, long usePoint) {
        UserPointDomain userPointDomain = this.userPointRepository.findById(userId);
        if(usePoint <= 0)
            throw new RuntimeException("사용하려는 포인트액수가 이상합니다.");
        if(userPointDomain.isPointInsufficient(usePoint))
            throw new RuntimeException("잔액이 부족합니다");
        userPointDomain.usePoint(usePoint);
        this.userPointRepository.save(userPointDomain.getId(), userPointDomain.getPoint());
        return userPointDomain;
    }
}
