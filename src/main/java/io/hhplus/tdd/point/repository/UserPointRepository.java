package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;

public interface UserPointRepository {
    UserPointDomain findById(Long id);
    UserPointDomain save(long id, long amount);
}
