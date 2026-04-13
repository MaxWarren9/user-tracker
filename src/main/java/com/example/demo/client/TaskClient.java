package com.example.demo.client;

import com.example.demo.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class TaskClient {

    private final RestTemplate restTemplate;

    @Value("${integration.task-service.url}")
    private String taskServiceUrl;

    public boolean existsByAssigneeId(Long userId) {
        try {
            Boolean result = restTemplate.getForObject(
                    taskServiceUrl + "/tasks/exists/assignee/" + userId,
                    Boolean.class
            );
            return Boolean.TRUE.equals(result);
        } catch (Exception ex) {
            throw new UserException("Task service is unavailable");
        }
    }
}
