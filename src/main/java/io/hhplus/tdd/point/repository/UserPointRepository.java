package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;

public interface UserPointRepository {
    UserPointInfo save(long userId, long amount);
    UserPointInfo findById(long userId);
}
