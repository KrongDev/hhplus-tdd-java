package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.aggregate.domain.PointHistoryDomain;
import io.hhplus.tdd.point.aggregate.entity.PointHistory;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointHistoryRepositoryImplTest {

    @InjectMocks
    private PointHistoryRepositoryImpl pointHistoryRepository;

    @Mock
    private PointHistoryTable pointHistoryTable;

    @Test
    void save() {
        //Given
        long userId = 1;
        long amount = 100;
        TransactionType type = TransactionType.CHARGE;
        long time = System.currentTimeMillis();
        PointHistory expectedPointHistory = new PointHistory(0, userId, amount, type, time);
        when(pointHistoryTable.insert(userId, amount, type, time)).thenReturn(expectedPointHistory);

        //When
        PointHistoryDomain savedPointHistoryDomain = pointHistoryRepository.save(userId, amount, type, time);

        //Then
        assertEquals(userId, savedPointHistoryDomain.getUserId());
        assertEquals(amount, savedPointHistoryDomain.getAmount());
        assertEquals(type, savedPointHistoryDomain.getType());
        assertEquals(time, savedPointHistoryDomain.getUpdateMillis());
    }

    @Test
    void findAllByUserId() {
        //Given
        long userId = 1;
        long amount = 100;
        TransactionType type = TransactionType.CHARGE;
        long time = System.currentTimeMillis();
        PointHistory expectedPointHistory = new PointHistory(0, userId, amount, type, time);
        when(pointHistoryTable.selectAllByUserId(userId)).thenReturn(List.of(expectedPointHistory));

        //When
        List<PointHistoryDomain> pointHistoryDomains = pointHistoryRepository.findAllByUserId(userId);

        //Then
        assertEquals(1, pointHistoryDomains.size());
        assertEquals(userId, pointHistoryDomains.get(0).getUserId());
        assertEquals(amount, pointHistoryDomains.get(0).getAmount());
        assertEquals(type, pointHistoryDomains.get(0).getType());
        assertEquals(time, pointHistoryDomains.get(0).getUpdateMillis());
    }
}
