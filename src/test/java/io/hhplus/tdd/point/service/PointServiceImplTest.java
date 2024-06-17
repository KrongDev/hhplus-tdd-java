package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PointServiceImplTest {

    @Autowired
    private PointService pointService;

    @Test
    @Description("포인트 충전 동시성 테스트")
    void chargePoint() throws InterruptedException {
        //Given
        int userId = 1;
        int amount = 100;

        //When
        int memberCount = 10;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);

        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    pointService.chargePoint(userId, amount);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        List<PointHistoryInfo> histories = pointService.loadHistory(userId);
        int historySize = histories.size();

        //Then
        assertEquals(histories.get(historySize - 1).getAmount(), 1000);
    }


    @Test
    @Description("포인트 사용 동시성 테스트")
    void usePoint() throws InterruptedException {
        //Given
        int userId = 1;
        int amount = 100;
        int useAmount = 20;
        pointService.chargePoint(userId, amount);

        //When
        int memberCount = 10;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);


        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    pointService.usePoint(userId, useAmount);
                    successCount.incrementAndGet();
                } catch (RuntimeException e) {
                  failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //Then
        assertAll(
            () -> assertEquals(successCount.get(), 5),
            () -> assertEquals(failCount.get(), 5)
        );
    }
}
