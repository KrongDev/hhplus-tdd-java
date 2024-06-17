package io.hhplus.tdd.point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String rootPath = "/point";
    private final long userId = 0;
    private final String actionUrl = String.format("%s/%d", rootPath, userId);


    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .alwaysDo(print())
                .build();
    }

    /**
     * User Id로 포인트 조회 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void point() throws Exception {
        mockMvc.perform(get(actionUrl))
                .andExpect(status().isOk());
    }

    /**
     * User Id로 포인트 충전/이용 내역 조회 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void history() throws Exception {
        charge();
        use();

        mockMvc.perform(get(actionUrl + "/histories"))
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
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(amount))
                )
                .andExpect(status().isOk());
    }

    /**
     * User Id로 포인트 사용 API Test
     *
     * @throws Exception MockMvc perform exception
     */
    @Test
    void use() throws Exception {
        long useAmount = 50;
        charge();
        mockMvc.perform(
                        patch(actionUrl + "/use")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(useAmount))
                )
                .andExpect(status().isOk());
    }
}
