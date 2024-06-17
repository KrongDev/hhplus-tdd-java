package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.entity.PointHistory;
import io.hhplus.tdd.point.aggregate.vo.TransactionType;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest({PointHistoryRepositoryImpl.class, PointHistoryTable.class})
class PointHistoryRepositoryImplTest {

    @Autowired
    private PointHistoryRepository repository;
    @Autowired
    private PointHistoryTable table;

    @Test
    @Description("PointHistoryTable에 정상적으로 PointHistory를 저장하는지 확인하는 테스트")
    void save() {
        //Given
        long userId = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();

        //When
        PointHistoryInfo history = repository.save(userId, amount, type, updateMillis);

        //Then
        assertNotNull(history);
        assertEquals(userId, history.getId());
        assertEquals(amount, history.getAmount());
        assertEquals(type, history.getType());
        assertEquals(updateMillis, history.getUpdateMillis());
    }

    @Test
    @Description("PointHistoryTable에서 UserId로 PointHistory를 정상적으로 조회하는지 확인하는 테스트")
    void findById() {
        //Given
        long userId = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();
        PointHistory history = table.insert(userId, amount, type, updateMillis);

        //When
        List<PointHistoryInfo> historyInfos = repository.findById(history.userId());

        //Then
        PointHistoryInfo historyInfo = historyInfos.get(0);
        assertEquals(1, historyInfos.size());
        assertEquals(history.userId(), historyInfo.getId());
        assertEquals(history.amount(), historyInfo.getAmount());
        assertEquals(history.type(), historyInfo.getType());
        assertEquals(history.updateMillis(), historyInfo.getUpdateMillis());
    }
}
