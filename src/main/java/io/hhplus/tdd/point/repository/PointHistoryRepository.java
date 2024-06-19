package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;

import java.util.List;

public interface PointHistoryRepository {
    PointHistoryDomain save(long userId, long amount, TransactionType type, long updateMillis);
    List<PointHistoryDomain> findAllByUserId(long userId);
}
