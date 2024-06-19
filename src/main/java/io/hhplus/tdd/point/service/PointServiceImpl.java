package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
    //
    public UserPointDomain loadPoint(long userId) {
        UserPointDomain userPointDomain = UserPointDomain.sample();
        userPointDomain.setId(userId);
        return userPointDomain;
    }

    public List<PointHistoryDomain> loadHistory(long userId) {
        return List.of();
    }

    public UserPointDomain chargePoint(long userId, long chargePoint) {
        if(chargePoint <= 0)
            throw new RuntimeException("충전하려는 금액이 이상합니다");
        UserPointDomain userPointDomain = UserPointDomain.sample();
        userPointDomain.setId(userId);
        userPointDomain.chargePoint(chargePoint);
        return userPointDomain;
    }

    public UserPointDomain usePoint(long userId, long usePoint) {
        UserPointDomain userPointDomain = UserPointDomain.sample();
        userPointDomain.setId(userId);
        if(usePoint <= 0)
            throw new RuntimeException("사용하려는 포인트액수가 이상합니다.");
        if(userPointDomain.isPointInsufficient(usePoint))
            throw new RuntimeException("잔액이 부족합니다");
        userPointDomain.usePoint(usePoint);
        return userPointDomain;
    }
}
