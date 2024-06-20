package io.hhplus.tdd.point.util.task;

import io.hhplus.tdd.point.util.TaskQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component
public class UserTaskQueue {
    private final ConcurrentHashMap<Long, TaskQueue> userTaskQueues = new ConcurrentHashMap<>();

    public Future<?> addTask(long userId, Runnable task) {
        return userTaskQueues.computeIfAbsent(userId, k -> new TaskQueue()).addTask(task);
    }
}
