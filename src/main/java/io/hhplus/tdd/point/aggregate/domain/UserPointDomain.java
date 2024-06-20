package io.hhplus.tdd.point.aggregate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPointDomain {
    private long id;
    private long point;
    private long updateMillis;

    /**
     * 포인트를 충전할 때 사용하는 메소드
     * @param point 충전할 포인트
     */
    public void chargePoint(long point) {
        this.point += point;
        this.updateMillis = System.currentTimeMillis();
    }

    /**
     * 포인트를 사용하는 메소드
     * @param point 사용할 포인트
     */
    public void usePoint(long point) {
        this.point -= point;
        this.updateMillis = System.currentTimeMillis();
    }

    /**
     * 잔고가 부족한지 확인하는 메소드
     * @param point 사용할 포인트
     * @return 잔고가 부족한지에 대한 여부
     */
    public boolean isPointInsufficient(long point) {
        return this.point < point;
    }

    public static UserPointDomain sample() {
        return new UserPointDomain(0, 0, 0);
    }
}
