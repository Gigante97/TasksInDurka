package org.crpt.tasks.durka.services;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import lombok.AllArgsConstructor;
import org.crpt.tasks.durka.configs.JiraConfig;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class JiraService  {
    JiraConfig jiraConfig;

    String createIssue(String summary,String description) {
        JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(
                        URI.create(jiraConfig.getUrl()),
                        jiraConfig.getUsername(),
                        jiraConfig.getPassword()
                );
        IssueRestClient issueRestClient = jiraRestClient.getIssueClient();
        IssueInputBuilder issueInputBuilder = new IssueInputBuilder();
        issueInputBuilder.setAssigneeName(jiraConfig.getAssignee());
        issueInputBuilder.setSummary(summary);
        issueInputBuilder.setProjectKey(jiraConfig.getProjectKey());
        issueInputBuilder.setPriorityId(3L);
        issueInputBuilder.setDescription(description);
        issueInputBuilder.setIssueTypeId(new Long(10005));

        IssueInput issueInput = issueInputBuilder.build();
       return issueRestClient.createIssue(issueInput).claim().getKey();

    }

    public  String getType() throws ExecutionException, InterruptedException {
        JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(
                        URI.create(jiraConfig.getUrl()),
                        jiraConfig.getUsername(),
                        jiraConfig.getPassword()
                );
        IssueRestClient issueRestClient = jiraRestClient.getIssueClient();
        IssueType issueType = issueRestClient.getIssue("GISMTOPS-898").get().getIssueType();
        System.out.println(issueType.getId());
        return "УРА";
    }


}
