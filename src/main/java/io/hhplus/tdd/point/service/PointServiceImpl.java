package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    //
    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public UserPoint loadPoint(long userId) {
        //
        return this.userPointRepository.findById(userId);
    }

    @Override
    public List<PointHistory> loadHistory(long userId) {
        //
        return this.pointHistoryRepository.findById(userId);
    }

    @Override
    public UserPoint chargePoint(long userId, long amount) {
        UserPoint userPoint = loadPoint(userId);
        long changePoint = userPoint.point() + amount;
        long updateTime = System.currentTimeMillis();
        userPoint = this.userPointRepository.save(userId, changePoint);
        this.pointHistoryRepository.save(userId, changePoint, TransactionType.CHARGE, updateTime);
        return userPoint;
    }

    @Override
    public UserPoint usePoint(long userId, long amount) {
        UserPoint userPoint = loadPoint(userId);
        long changePoint = userPoint.point() - amount;
        if (changePoint < 0)
            throw new RuntimeException();
        long updateTime = System.currentTimeMillis();
        userPoint = this.userPointRepository.save(userId, changePoint);
        this.pointHistoryRepository.save(userId, changePoint, TransactionType.USE, updateTime);
        return userPoint;
    }
}
