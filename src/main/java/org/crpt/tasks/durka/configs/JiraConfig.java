package org.crpt.tasks.durka.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@Data
public class JiraConfig {
    @Value("${jira.url}")
    String url;
    @Value("${jira.username}")
    String username;
    @Value("${jira.password}")
    String password;
    @Value("jira.assignee")
    String assignee;
    @Value("jira.projectKey")
    String projectKey;
}
