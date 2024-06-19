package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    PointServiceImpl pointService;

    @Test
    @DisplayName("포인트 조회")
    void loadPoint() {
        //Given
        long userId = 1;
        //When
        UserPointDomain userPoint = pointService.loadPoint(userId);
        //Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
    }

    @Test
    @DisplayName("포인트 조회")
    void loadHistory() {
        //Given
        long userId = 1;
        //When
        List<PointHistoryDomain> pointHistory = pointService.loadHistory(userId);
        //Then
        assertNotNull(pointHistory);
    }

    @Test
    @DisplayName("포인트 충전")
    @Description("포인트 충전시 0원이거나 0원 이하의 경우 에러 발생")
    void chargePoint() {
        //Given
        long userId = 1;
        long chargePoint = 100;
        UserPointDomain prevUserPoint = pointService.loadPoint(userId);
        //When
        UserPointDomain userPoint = pointService.chargePoint(userId, chargePoint);
        //Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(prevUserPoint.getPoint() + chargePoint, userPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 사용")
    @Description("잔여 포인트보다 사용하려는 포인트가 많거나 사용하려는 포인트가 0원이면 에러 발생")
    void usePoint() {
        //Given
        long userId = 1;
        long usePoint = 0;
        UserPointDomain prevUserPoint = pointService.loadPoint(userId);
        //When
        UserPointDomain userPoint = pointService.usePoint(userId, usePoint);
        //Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(prevUserPoint.getPoint() - usePoint, userPoint.getPoint());
    }
}
