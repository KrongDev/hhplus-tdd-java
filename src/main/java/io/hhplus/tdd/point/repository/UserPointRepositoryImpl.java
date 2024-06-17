package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;
import io.hhplus.tdd.point.aggregate.entity.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepository{
    //
    private final UserPointTable userPointTable;

    @Override
    public UserPointInfo save(long userId, long amount) {
        //
        UserPoint userPoint = this.userPointTable.insertOrUpdate(userId, amount);
        return userPoint.toDomain();
    }

    @Override
    public UserPointInfo findById(long userId) {
        //
        UserPoint userPoint = this.userPointTable.selectById(userId);
        return userPoint.toDomain();
    }
}
