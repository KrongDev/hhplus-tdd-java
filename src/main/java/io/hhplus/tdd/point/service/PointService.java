package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;

import java.util.List;

public interface PointService {
    UserPointDomain loadPoint(long userId);
    List<PointHistoryDomain> loadHistory(long userId);
    UserPointDomain chargePoint(long userId, long chargePoint);
    UserPointDomain usePoint(long userId, long usePoint);
}
