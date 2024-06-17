package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;

import java.util.List;

public interface PointHistoryRepository {
    PointHistoryInfo save(long userId, long amount, TransactionType type, long updateMillis);
    List<PointHistoryInfo> findById(long userId);
}
