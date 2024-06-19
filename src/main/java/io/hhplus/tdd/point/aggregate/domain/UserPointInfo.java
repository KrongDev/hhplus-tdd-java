package io.hhplus.tdd.point.aggregate.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPointInfo {
    private long id;
    private long point;
    private long updateMillis;

    /**
     * 포인트 충전 메소드
     * @param point 충전할 포인트
     */
    public void chargePoint(long point) {
        update();
        this.point += point;
    }

    /**
     * 포인트 사용시 포인트 차감 메소드
     * 현재 포인트가 0원인데 차감할 포인트가 0 보다 큰 경우 RuntimeException
     * @param point 사용할 포인트
     */
    public void usePoint(long point) {
        if(this.point < point)
            throw new RuntimeException(String.format("잔여금보다 많은 금액을 사용하셨습니다. 잔여포인트: %d", this.point));
        update();
        this.point -= point;
    }

    /**
     * 업데이트 발생시 필수적으로 업데이트 해줘야하는 항목
     */
    private void update() {
        this.updateMillis = System.currentTimeMillis();
    }

    /**
     * 테스트를 위한 데이터 생성
     * @return UserPointInfo
     */
    public static UserPointInfo sample() {
        return UserPointInfo.builder()
                .id(0)
                .point(0)
                .updateMillis(System.currentTimeMillis())
                .build();
    }
}
