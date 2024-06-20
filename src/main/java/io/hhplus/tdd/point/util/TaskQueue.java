package io.hhplus.tdd.point.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

public class TaskQueue {
    private final ThreadPoolExecutor executor;

    public TaskQueue() {
        //
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(
                1,
                1,
                0L, TimeUnit.MICROSECONDS,
                taskQueue
        );
    }

    public Future<?> addTask(Runnable task) {
        return executor.submit(task);
    }
}
