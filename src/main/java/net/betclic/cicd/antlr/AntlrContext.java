package net.betclic.cicd.antlr;

import JaxbClasses.SqlRules;
import net.betclic.cicd.language.DialectLanguageTypesMap;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AntlrContext {

    private org.antlr.v4.runtime.Lexer lexer;
    private ParseTree tree;
    String basicText;
    String textWithoutComments;
    private CommonTokenStream stream;
    private DialectLanguageTypesMap types;
    private List<SqlRules> rules = new ArrayList<>();

    public DialectLanguageTypesMap getTypes() {
        return types;
    }

    public void setTypes(DialectLanguageTypesMap types) {
        this.types = types;
    }

    public String getBasicText() {
        return basicText;
    }

    public void setBasicText(String basicText) {
        this.basicText = basicText;
    }


    public String getTextWithoutComments() {
        return textWithoutComments;
    }

    public void setTextWithoutComments(String textWithoutComments) {
        this.textWithoutComments = textWithoutComments;
    }

    public org.antlr.v4.runtime.Lexer getLexer() {
        return lexer;
    }

    public void setLexer(org.antlr.v4.runtime.Lexer lexer) {
        this.lexer = lexer;
    }


    public ParseTree getTree() {
        return tree;
    }

    public void setTree(ParseTree tree) {
        this.tree = tree;
    }


    public CommonTokenStream getStream() {
        return stream;
    }

    public void setStream(CommonTokenStream stream) {
        this.stream = stream;
    }


    public List<SqlRules> getRules() {
        return rules;
    }

    public void setRules(List<SqlRules> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntlrContext that = (AntlrContext) o;
        return Objects.equals(lexer, that.lexer) && Objects.equals(tree, that.tree) && Objects.equals(stream, that.stream) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexer, tree, stream, rules);
    }

    public boolean isString(Token token) {
        return types.getStringTokens().contains(token.getType());
    }

    public boolean isComment(Token token) {
        return types.getCommentTokens().contains(token.getType());
    }

    public boolean isKeyword(Token token) {
        if (lexer != null && lexer.getVocabulary() != null) {
            String symbolicName = lexer.getVocabulary().getSymbolicName(token.getType());
            if (symbolicName != null) {
                return (StringUtils.equalsAnyIgnoreCase(symbolicName, token.getText()))
                        || symbolicName.toLowerCase().contains(token.getText().toLowerCase());
            }
        }
        return false;
    }

    public List<Token> getAllTokens() {
        return stream.getTokens();
    }
}
