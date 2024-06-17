package io.hhplus.tdd.point;

import io.hhplus.tdd.point.service.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PointService pointService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String rootPath = "/point";
    private final long userId = 0;
    private final String actionUrl = String.format("%s/%d", rootPath, userId);

    @Test
    @Description("User Id로 포인트 조회 API Test")
    void point() throws Exception {
        mockMvc.perform(get(actionUrl))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.point").value(0))
            .andExpect(jsonPath("$.updateMillis").value(not(0)));
    }

    @Test
    @Description("User Id로 포인트 충전/이용 내역 조회 API Test")
    void history() throws Exception {
        //Given
        pointService.chargePoint(userId, 100);
        pointService.usePoint(userId, 50);

        //When-Then
        mockMvc.perform(get(actionUrl + "/histories"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].userId", everyItem(is(0))));
    }

    @Test
    @Description("User Id로 포인트 충전 API Test")
    void charge() throws Exception {
        long amount = 100;
        mockMvc.perform(
            patch(actionUrl + "/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.point").value(amount));
    }

    @Test
    @Description("User Id로 포인트 사용 API Test")
    void use() throws Exception {
        //Given
        long amount = 100;
        pointService.chargePoint(userId, amount);

        //When-Then
        long useAmount = 50;
        mockMvc.perform(
            patch(actionUrl + "/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(useAmount))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.point").value(amount - useAmount));
    }
}
