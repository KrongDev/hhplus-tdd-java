package io.hhplus.tdd.point;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String rootPath = "/point";
    private final long userId = 0;
    private final String actionUrl = String.format("%s/%d", rootPath, userId);

    /**
     * User Id로 포인트 조회 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void point() throws Exception {
        mockMvc.perform(get(actionUrl))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Id로 포인트 충전/이용 내역 조회 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void history() throws Exception {
        mockMvc.perform(get(actionUrl + "/histories"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Id로 포인트 충전 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void charge() throws Exception {
        long amount = 100;
        mockMvc.perform(
                        patch(actionUrl + "/charge")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(String.valueOf(amount))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Id로 포인트 사용 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void use() throws Exception {
        long amount = 100;
        mockMvc.perform(
                        patch(actionUrl + "/use")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(String.valueOf(amount))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
