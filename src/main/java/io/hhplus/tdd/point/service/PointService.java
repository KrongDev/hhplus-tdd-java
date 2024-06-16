package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointService {
    UserPoint loadPoint(long userId);
    List<PointHistory> loadHistory(long userId);
    UserPoint chargePoint(long userId, long amount);
    UserPoint usePoint(long userId, long amount);
}
