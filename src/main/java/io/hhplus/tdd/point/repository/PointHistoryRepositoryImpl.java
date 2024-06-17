package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.entity.PointHistory;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository{
    //
    private final PointHistoryTable pointHistoryTable;

    public PointHistoryInfo save(long userId, long amount, TransactionType type, long updateMillis) {
        //
        PointHistory history =  pointHistoryTable.insert(userId, amount, type, updateMillis);
        return history.toDomain();
    }

    public List<PointHistoryInfo> findById(long userId) {
        //
        List<PointHistory> histories = this.pointHistoryTable.selectAllByUserId(userId);
        return histories.stream().map(PointHistory::toDomain).toList();
    }
}
