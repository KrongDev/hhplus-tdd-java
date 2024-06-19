package io.hhplus.tdd.point.aggregate.entity;

import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public UserPointDomain toDomain() {
        return new UserPointDomain(id, point, updateMillis);
    }
}
