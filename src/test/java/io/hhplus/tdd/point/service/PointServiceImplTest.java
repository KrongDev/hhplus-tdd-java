package io.hhplus.tdd.point.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class PointServiceImplTest {

    @Autowired
    private PointService pointService;

    @Test
    void chargePoint() throws InterruptedException, JsonProcessingException {
        int userId = 1;
        int amount = 100;

        int memberCount = 10;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);

        for (int i = 0; i < memberCount; i++) {
            int finalI = i;
            executorsService.submit(() -> {
                try {
                    pointService.chargePoint(userId, amount + finalI);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        List<PointHistory> histories = pointService.loadHistory(userId);
        System.out.println(JsonUtil.toPrettyJson(histories));
    }
}
