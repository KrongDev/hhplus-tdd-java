package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;
import io.hhplus.tdd.point.aggregate.entity.UserPoint;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest({UserPointRepositoryImpl.class, UserPointTable.class})
class UserPointRepositoryImplTest {

    @Autowired
    private UserPointRepository userPointRepository;
    @Autowired
    private UserPointTable userPointTable;

    @Test
    @Description("UserPointTable에 정상적으로 UserPoint정보를 저장하는지 확인하는 Test")
    void save() {
        //Given
        long userId = 1L;
        long amount = 100L;

        //When
        UserPointInfo userPointInfo = userPointRepository.save(userId, amount);

        //Then
        assertNotNull(userPointInfo);
        assertEquals(userId, userPointInfo.getId());
        assertEquals(amount, userPointInfo.getPoint());
    }

    @Test
    @Description("UserPointTable에서 정상적으로 UserPoint정보를 조회하는지 확인하는 Test")
    void findById() {
        //Given
        long userId = 1L;
        long amount = 100L;
        UserPoint userPoint = userPointTable.insertOrUpdate(userId, amount);

        //When
        UserPointInfo userPointInfo = userPointRepository.findById(userId);

        //Then
        assertNotNull(userPointInfo);
        assertEquals(userPoint.id(), userPointInfo.getId());
        assertEquals(userPoint.point(), userPointInfo.getPoint());
        assertEquals(userPoint.updateMillis(), userPointInfo.getUpdateMillis());
    }
}
