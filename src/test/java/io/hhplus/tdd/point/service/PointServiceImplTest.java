package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    PointServiceImpl pointService;

    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @Mock
    private UserPointRepository userPointRepository;

    @Test
    @DisplayName("포인트 충전")
    @Description("포인트 충전이 정상적으로 수행되는지 테스트")
    void chargePoint() {
        //Given
        long userId = 1;
        long chargePoint = 100;
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 0, 0));
        UserPointDomain prevUserPoint = pointService.loadPoint(userId);
        //When
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 0, 0));
        UserPointDomain userPoint = pointService.chargePoint(userId, chargePoint);
        //Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(prevUserPoint.getPoint() + chargePoint, userPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 사용")
    @Description("포인트가 정상적으로 사용되는지 테스트")
    void usePoint() {
        //Given
        long userId = 1;
        long usePoint = 10;
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 100, 0));
        UserPointDomain prevUserPoint = pointService.loadPoint(userId);
        //When
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 100, 0));
        UserPointDomain userPoint = pointService.usePoint(userId, usePoint);
        //Then
        assertNotNull(userPoint);
        assertEquals(userId, userPoint.getId());
        assertEquals(prevUserPoint.getPoint() - usePoint, userPoint.getPoint());
    }
}
