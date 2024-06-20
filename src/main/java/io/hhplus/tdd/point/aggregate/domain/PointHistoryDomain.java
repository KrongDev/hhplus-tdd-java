package io.hhplus.tdd.point.aggregate.domain;

import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryDomain {
    long id;
    long userId;
    long amount;
    TransactionType type;
    long updateMillis;
}
