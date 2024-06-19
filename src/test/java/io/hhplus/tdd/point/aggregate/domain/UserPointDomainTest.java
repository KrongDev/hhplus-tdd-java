package io.hhplus.tdd.point.aggregate.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPointDomainTest {

    @Test
    @DisplayName("포인트 충전 도메인 로직 테스트")
    public void chargePoint() {
        //Given
        long chargePoint = 100L;
        UserPointDomain userPointDomain = new UserPointDomain();
        //When
        userPointDomain.chargePoint(chargePoint);
        //Then
        assertEquals(100, userPointDomain.getPoint());
        assertNotEquals(0, userPointDomain.getUpdateMillis());
    }

    @Test
    @DisplayName("포인트 사용 도메인 로직 테스트")
    public void usePoint() {
        //Given
        UserPointDomain userPointDomain = new UserPointDomain(0, 100, 0);
        long usePoint = 100L;
        //When
        userPointDomain.usePoint(usePoint);
        //Then
        assertEquals(0, userPointDomain.getPoint());
        assertNotEquals(0, userPointDomain.getUpdateMillis());
    }
}
