package net.betclic.cicd.measures;

import JaxbClasses.Rule;
import JaxbClasses.SqlRules;
import net.betclic.cicd.scanner.IParsedNode;

public class FoundMatch {
    private IParsedNode node;
    private Rule rule;
    private SqlRules sqlRules;

    public void setNode(IParsedNode node) {
        this.node = node;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setSqlRules(SqlRules sqlRules) {
        this.sqlRules = sqlRules;
    }

    public IParsedNode getNode() {
        return node;
    }

    public Rule getRule() {
        return rule;
    }

    public SqlRules getSqlRules() {
        return sqlRules;
    }

    public int getLine() {
        return (node == null) ? 1 : node.getLine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FoundMatch other = (FoundMatch) obj;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        return true;
    }
}
