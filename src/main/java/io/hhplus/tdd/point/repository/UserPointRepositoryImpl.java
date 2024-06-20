package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepository{

    private final UserPointTable userPointTable;

    @Override
    public UserPointDomain findById(Long id) {
        return userPointTable.selectById(id).toDomain();
    }

    @Override
    public UserPointDomain save(long id, long amount) {
        return userPointTable.insertOrUpdate(id, amount).toDomain();
    }
}
