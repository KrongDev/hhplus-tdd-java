package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("포인트 충전 - 정상 충전 케이스")
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
    @DisplayName("포인트 충전 - 0포인트 충전 케이스")
    @Description("0포인트 충전 요청시")
    void chargeZeroPointException() {
        //Given
        long userId = 1;
        long usePoint = 0;
        assertThrows(RuntimeException.class, () -> pointService.chargePoint(userId, usePoint));
    }

    @Test
    @DisplayName("포인트 사용 - 정상 사용 케이스")
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

    @Test
    @DisplayName("포인트 사용 - 잔여 포인트 부족 케이스")
    @Description("잔여포인트가 부족한 경우 에러 발생 테스트")
    void usePointOverException() {
        //Given
        long userId = 1;
        long usePoint = 100;
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 10, 0));

        assertThrows(RuntimeException.class, () -> pointService.usePoint(userId, usePoint));
    }

    @Test
    @DisplayName("포인트 사용 - 0포인트 사용 케이스")
    @Description("0포인트 사용 요청시 에러 발생 테스트")
    void useZeroPointException() {
        //Given
        long userId = 1;
        long usePoint = 0;
        when(userPointRepository.findById(userId)).thenReturn(new UserPointDomain(userId, 10, 0));

        assertThrows(RuntimeException.class, () -> pointService.usePoint(userId, usePoint));
    }

    @Test
    @DisplayName("포인트 내역")
    @Description("포인트 충전/사용 내역이 정상적으로 조회되는지 테스트")
    void usePointHistory() {
        //Given
        long userId = 1;
        long time = System.currentTimeMillis();
        when(pointHistoryRepository.findAllByUserId(userId)).thenReturn(List.of(new PointHistoryDomain(0, userId, 100, TransactionType.CHARGE, time), new PointHistoryDomain(0, userId, 10, TransactionType.USE, time)));
        //When
        List<PointHistoryDomain> histories = pointService.loadHistory(userId);
        //Then
        assertNotNull(histories);
        assertEquals(2, histories.size());
        histories.forEach(history -> assertAll(
                "History validate",
                () -> assertEquals(userId, history.getUserId()),
                () -> assertNotNull(history.getType()),
                () -> assertNotEquals(0, history.getUpdateMillis())
        ));
    }
}
