package net.betclic.cicd.measures;

import java.util.*;

public class SqlIssuesList {
    private final Map<String, Set<SqlIssue>> issues = new HashMap<>();

    public Map<String, Set<SqlIssue>> getIssues() {
        return issues;
    }
    public Collection<SqlIssue> getAllIssues() {
        Set<SqlIssue> issueSet = new HashSet<>();
        this.issues.forEach((k,v) -> issueSet.addAll(v));
        return issueSet;
    }
    public SqlIssuesList addIssue(final SqlIssue issue) {
        final Set<SqlIssue> sqlIssues = this.issues.getOrDefault(issue.getFileName(), new HashSet<SqlIssue>());
        sqlIssues.add(issue);
        this.issues.put(issue.getFileName(), sqlIssues);
        return this;
    }
}
