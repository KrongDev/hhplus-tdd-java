package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.aggregate.entity.PointHistory;
import io.hhplus.tdd.point.aggregate.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointControllerTest {

    private final PointController pointController = new PointController();
    private final long userId = 1;

    @Test
    @DisplayName("포인트 조회")
    void point() {
        UserPointDomain userPoint = pointController.point(userId);

        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
    }

    @Test
    @DisplayName("포인트 내역 조회")
    void history() {
        List<PointHistoryDomain> pointHistories = pointController.history(userId);

        assertNotNull(pointHistories);
        assertEquals(0, pointHistories.size());
    }

    @Test
    @DisplayName("포인트 충전")
    void charge() {
        long amount = 100;
        UserPointDomain userPoint = pointController.charge(userId, amount);
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(amount, userPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 사용")
    void use() {
        long amount = 100;
        UserPointDomain userPoint = pointController.use(userId, amount);
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(amount, userPoint.getPoint());
    }
}
