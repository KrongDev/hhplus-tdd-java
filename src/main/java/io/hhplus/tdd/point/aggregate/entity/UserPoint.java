package io.hhplus.tdd.point.aggregate.entity;

import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;
import org.springframework.beans.BeanUtils;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public UserPointInfo toDomain() {
        UserPointInfo userPointInfo = new UserPointInfo();
        BeanUtils.copyProperties(this, userPointInfo);
        return userPointInfo;
    }
}
