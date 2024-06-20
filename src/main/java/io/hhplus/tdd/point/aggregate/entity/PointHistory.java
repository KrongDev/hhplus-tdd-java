package io.hhplus.tdd.point.aggregate.entity;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;

public record PointHistory(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {
    public PointHistoryDomain toDomain() {
        return new PointHistoryDomain(id, userId, amount, type, updateMillis);
    }
}
