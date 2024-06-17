package io.hhplus.tdd.point.aggregate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserPointInfoTest {

    private UserPointInfo userPointInfo;

    @BeforeEach
    public void setUp() {
        //Given
        userPointInfo = UserPointInfo.sample();
    }

    @Test
    @Description("UserPointInfo ChargePoint Method 정상 포인트 적립 단위 테스트")
    public void chargePointTest() {
        // When
        userPointInfo.chargePoint(100);

        // Then
        assertEquals(100, userPointInfo.getPoint());
        assertNotEquals(0, userPointInfo.getUpdateMillis());
    }

    @Test
    @Description("UserPointInfo usePoint Method 잔여포인트 부족 에러 상황 테스트")
    public void usePointExceptionTest() {
        // When
        userPointInfo.usePoint(100);

        // Then
        assertEquals(100, userPointInfo.getPoint());
    }

    @Test
    @Description("UserPointInfo usePoint Method 정상적 포인트 사용 테스트")
    public void usePointTest() {
        //Given
        userPointInfo.setPoint(150);

        // When
        userPointInfo.usePoint(100);

        // Then
        assertEquals(50, userPointInfo.getPoint());
        assertNotEquals(0, userPointInfo.getUpdateMillis());
    }
}
