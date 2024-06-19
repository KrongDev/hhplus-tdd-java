package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.aggregate.domain.PointHistoryInfo;
import io.hhplus.tdd.point.aggregate.domain.UserPointInfo;
import io.hhplus.tdd.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPointInfo point(
            @PathVariable long id
    ) {
        log.info("userId: {}", id);
        return this.pointService.loadPoint(id);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistoryInfo> history(
            @PathVariable long id
    ) {
        log.info("userId: {}", id);
        return this.pointService.loadHistory(id);
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPointInfo charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        log.info("userId: {}, amount: {}", id, amount);
        return this.pointService.chargePoint(id, amount);
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPointInfo use(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        log.info("userId: {}, amount: {}", id, amount);
        return this.pointService.usePoint(id, amount);
    }
}
