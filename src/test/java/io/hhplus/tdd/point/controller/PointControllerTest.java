package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @InjectMocks
    PointController pointController;

    @Mock
    PointService pointService;

    private final long userId = 1;
    private UserPointDomain userPointDomain;

    @BeforeEach
    void setUp() {
        userPointDomain = new UserPointDomain(userId, 0, 0); // 적절한 사용자 ID로 설정
    }

    @Test
    @DisplayName("포인트 조회 - 정상적으로 포인트가 조회되는지")
    void point() {
        // Given
        when(pointService.loadPoint(userId)).thenReturn(userPointDomain);

        // When
        UserPointDomain userPoint = pointController.point(userId);

        // Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
    }

    @Test
    @DisplayName("포인트 내역 조회 - 정상적으로 포인트 내역이 조회되는지")
    void history() {
        // Given
        List<PointHistoryDomain> mockPointHistories = Collections.emptyList();
        when(pointService.loadHistory(userId)).thenReturn(mockPointHistories);

        // When
        List<PointHistoryDomain> pointHistories = pointController.history(userId);

        // Then
        assertNotNull(pointHistories);
        assertEquals(0, pointHistories.size());
    }

    @Test
    @DisplayName("포인트 충전 - 정상적으로 충전이 이뤄지는지")
    void charge() {
        // Given
        long amount = 100;
        when(pointService.chargePoint(userId, amount)).thenReturn(new UserPointDomain(userId, amount, System.currentTimeMillis()));

        // When
        UserPointDomain userPoint = pointController.charge(userId, amount);

        // Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(amount, userPoint.getPoint());
        assertNotEquals(0, userPoint.getUpdateMillis());
    }

    @Test
    @DisplayName("포인트 사용 - 정상적으로 포인트를 사용할 수 있는지")
    void use() {
        // Given
        long amount = 100;
        when(pointService.usePoint(userId, amount)).thenReturn(new UserPointDomain(userId, amount, System.currentTimeMillis()));

        // When
        UserPointDomain userPoint = pointController.use(userId, amount);

        // Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(amount, userPoint.getPoint());
        assertNotEquals(0, userPoint.getUpdateMillis());
    }
}
