package io.hhplus.tdd.point.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
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

    private final String rootPath = "/point";
    private final long userId = 0;
    private final String actionUrl = String.format("%s/%d", rootPath, userId);

    @Test
    @DisplayName("포인트 충전 - 정상적으로 충전이 이뤄지는지")
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
    @DisplayName("포인트 충전 - 정상적으로 충전이 이뤄지는지")
    void chargeZeroException() throws Exception {
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
    @DisplayName("포인트 사용 - 보유 포인트보다 많이 사용하려할 때 에러 테스트")
    void useOverException() throws Exception {
        long useAmount = 150;
        mockMvc.perform(
                        patch(actionUrl + "/use")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(useAmount))
                )
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("포인트 사용 - 사용포인트 0원일때 에러 발생")
    void useZeroException() throws Exception {
        long useAmount = 0;
        mockMvc.perform(
                        patch(actionUrl + "/use")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(useAmount))
                )
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("포인트 내역 조회 - 정상적으로 포인트 내역이 조회되는지")
    void history() throws Exception {
        mockMvc.perform(get(actionUrl + "/histories"))
                .andDo(print())
                .andExpect(status().isOk())
                // UserId는 long type이기 때문에 is()에 넣을시 0 != 0L 이유로 인한 오류 발생
                .andExpect(jsonPath("$[*].userId", everyItem(is(0))));
    }

    @Test
    @DisplayName("포인트 조회 - 정상적으로 포인트가 조회되는지")
    void point() throws Exception {
        mockMvc.perform(get(actionUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.point").value(0));
    }
}
