package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.aggregate.domain.UserPointDomain;
import io.hhplus.tdd.point.aggregate.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPointRepositoryImplTest {

    @InjectMocks
    private UserPointRepositoryImpl userPointRepository;

    @Mock
    private UserPointTable userPointTable;

    @Test
    @DisplayName("Database 조회")
    void findById() {
        //Given
        long userId = 1;
        long amount = 100;
        UserPoint userPoint = new UserPoint(userId, amount, 0);
        when(userPointTable.selectById(userId)).thenReturn(userPoint);

        //When
        UserPointDomain userPointDomain = userPointRepository.findById(userId);

        //Then
        assertNotNull(userPointDomain);
        assertEquals(userId, userPointDomain.getId());
        assertEquals(amount, userPointDomain.getPoint());
        assertEquals(0, userPointDomain.getUpdateMillis());
    }

    @Test
    @DisplayName("Database 저장")
    void save() {
        //Given
        long userId = 1;
        long amount = 100;
        UserPoint userPoint = new UserPoint(userId, amount, 0);
        when(userPointTable.insertOrUpdate(userId, amount)).thenReturn(userPoint);

        //When
        UserPointDomain userPointDomain = userPointRepository.save(userId, amount);

        //Then
        assertNotNull(userPointDomain);
        assertEquals(userId, userPointDomain.getId());
        assertEquals(amount, userPointDomain.getPoint());
        assertEquals(0, userPointDomain.getUpdateMillis());
    }
}
