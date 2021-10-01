package net.betclic.cicd.measures;

public class SqlIssue {

    private int line;
    private String repo;
    private String fileName;
    private boolean isExternal = true;
    private boolean isAdhoc;
    private String description;
    private String name;
    private String message;
    private String key;
    private String severity;
    private long debtRemediationEffort = 0;
    private String ruleType = "CODE_SMELL";


    @Override
    public String toString() {
        return "SqlIssue [line=" + line + ", repo=" + repo + ", fileName=" + fileName + ", isAdhoc=" + isAdhoc
                + ", description=" + description + ", name=" + name + ", message=" + message + ", key=" + key
                + ", severity=" + severity + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + (isAdhoc ? 1231 : 1237);
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + line;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((repo == null) ? 0 : repo.hashCode());
        result = prime * result + ((severity == null) ? 0 : severity.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        boolean equalsObj = true;
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            equalsObj = false;
        SqlIssue other = (SqlIssue) obj;
        if (other != null && sqlIssueEquals(other)) equalsObj = false;
        return equalsObj;
    }

    private boolean sqlIssueEquals(SqlIssue other) {
        boolean sqlIssueEqualsResult =isIssueEqualsResult(other, false);
        return sqlIssueEqualsResult;
    }

    private boolean isIssueEqualsResult(SqlIssue other,boolean isIssueNotEquals) {
        if (((description == null) && (other.description != null)) || ((description != null) && (!description.equals(other.description))) || ((fileName == null) && (other.fileName != null)) || ((fileName != null) && (!fileName.equals(other.fileName))) ||
                isAdhoc != other.isAdhoc || ((key == null) && (other.key != null)) || ((key != null) && (!key.equals(other.key))) || (line != other.line) || ((message == null) && (other.message != null)) || ((message != null) && (!message.equals(other.message))) ||
                ((name == null) && (other.name != null)) || ((name != null) && (!name.equals(other.name))) || ((repo == null) && (other.repo != null)) || ((repo != null) && (!repo.equals(other.repo))) ||
                ((severity == null) && (other.severity != null)) || ((severity != null) && (!severity.equals(other.severity))))
            isIssueNotEquals = true;
        return isIssueNotEquals;

    }

    public int getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }

    public String getRepo() {
        return repo;
    }

    public String getUniqueKey() {
        return this.repo + ":" + this.key;
    }


    public String getKey() {
        return key;
    }


    public String getDescription() {
        return description;
    }


    public long getDebtRemediationEffort() {
        return debtRemediationEffort;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRuleType() {
        if (ruleType == null) {
            return "CODE_SMELL";
        }
        return ruleType;
    }

    public String getName() {
        return name;
    }

    public boolean isAdhoc() {
        return isAdhoc;
    }

    public String getSeverity() {
        return severity;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public void setAdhoc(boolean adhoc) {
        isAdhoc = adhoc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setDebtRemediationEffort(long debtRemediationEffort) {
        this.debtRemediationEffort = debtRemediationEffort;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
}
