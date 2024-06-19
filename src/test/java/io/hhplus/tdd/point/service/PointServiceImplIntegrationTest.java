package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PointServiceImplIntegrationTest {

    @Autowired
    private PointService pointService;
    @Autowired
    private UserPointRepository userPointRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Test
    @DisplayName("포인트 충전 - 정상 충전 케이스")
    @Description("포인트 충전이 정상적으로 수행되는지 테스트")
    void chargePoint() {
        //Given
        long userId = 1;
        long chargePoint = 100;
        //When
        UserPointDomain userPointDomain = pointService.chargePoint(userId, chargePoint);
        //Then
        List<PointHistoryDomain> histories = pointHistoryRepository.findAllByUserId(userId);
        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals(userPointDomain.getId(), histories.get(0).getUserId());
        assertEquals(userPointDomain.getPoint(), histories.get(0).getAmount());
        assertEquals(TransactionType.CHARGE, histories.get(0).getType());
        assertEquals(userPointDomain.getUpdateMillis(), histories.get(0).getUpdateMillis());
    }

    @Test
    @DisplayName("포인트 사용 - 정상 사용 케이스")
    @Description("포인트 사용이 정상적으로 수행되는지 테스트")
    void usePoint() {
        //Given
        long userId = 2;
        long usePoint = 100;
        userPointRepository.save(userId, 100);
        //When
        UserPointDomain userPointDomain = pointService.usePoint(userId, usePoint);
        //Then
        List<PointHistoryDomain> histories = pointHistoryRepository.findAllByUserId(userId);
        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals(userPointDomain.getId(), histories.get(0).getUserId());
        assertEquals(userPointDomain.getPoint(), histories.get(0).getAmount());
        assertEquals(TransactionType.USE, histories.get(0).getType());
        assertEquals(userPointDomain.getUpdateMillis(), histories.get(0).getUpdateMillis());
    }
}
