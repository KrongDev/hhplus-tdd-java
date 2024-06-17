package io.hhplus.tdd.point.aggregate.entity;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import org.springframework.beans.BeanUtils;

public record PointHistory(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {

    public PointHistoryInfo toDomain() {
        PointHistoryInfo pointHistoryInfo = new PointHistoryInfo();
        BeanUtils.copyProperties(this, pointHistoryInfo);
        return pointHistoryInfo;
    }
}
