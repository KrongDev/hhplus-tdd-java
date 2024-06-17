package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;

import java.util.List;

public interface PointService {
    UserPointInfo loadPoint(long userId);
    List<PointHistoryInfo> loadHistory(long userId);
    UserPointInfo chargePoint(long userId, long amount);
    UserPointInfo usePoint(long userId, long amount);
}
