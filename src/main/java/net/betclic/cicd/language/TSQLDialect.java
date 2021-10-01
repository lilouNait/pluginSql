package net.betclic.cicd.language;

import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.language.tsql.TsqlLexer;
import net.betclic.cicd.language.tsql.TsqlParser;
import net.betclic.cicd.rules.SQLDialectRules;
import net.betclic.cicd.settings.Constants;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TSQLDialect {
    private static final Logger LOGGER = Loggers.get(TSQLDialect.class);
    SQLDialectRules sqlDialectRules = new SQLDialectRules();

    protected Lexer getLexer(CharStream charStream) {
        TsqlLexer tsqlLexer = new TsqlLexer(charStream);
        tsqlLexer.removeErrorListeners();
        return tsqlLexer;
    }

    protected ParseTree getParseTree(CommonTokenStream stream) {
        TsqlParser parser = new TsqlParser(stream);
        parser.removeErrorListeners();
        return parser.tsql_file();
    }

    protected DialectLanguageTypesMap getTypesMap() {

        return new DialectLanguageTypesMap().addCommentToken(TsqlParser.COMMENT)
                .addCommentToken(TsqlParser.LINE_COMMENT).addStringToken(TsqlParser.STRING)
                .addCyclomaticComplexityType(TsqlParser.Search_condition_notContext.class)
                .addCyclomaticComplexityType(TsqlParser.Try_catch_statementContext.class)

                .addCognitiveComplexityType(TsqlParser.Search_condition_notContext.class)
                .addCognitiveComplexityType(TsqlParser.Function_callContext.class)
                .addCognitiveComplexityType(TsqlParser.Join_partContext.class)
                .addCognitiveComplexityType(TsqlParser.Order_by_expressionContext.class)
                .addCognitiveComplexityType(TsqlParser.Dml_clauseContext.class)
                .addCognitiveComplexityType(TsqlParser.Sql_clauseContext.class);
    }

    public AntlrContext parse(String text) {
        if (StringUtils.contains(text, Constants.PLUGIN_SQL_LINES_SKIP)) {
            text = skipLines(text, Constants.PLUGIN_SQL_LINES_SKIP, Constants.PLUGIN_SQL_LINES_SKIP_CLOSE);
        }
        final CharStream charStream = new ChangingCharStream(CharStreams.fromString(text), true);
        AntlrContext antlrContext = new AntlrContext();
        try {
            Lexer lexer = this.getLexer(charStream);
            final CommonTokenStream stream = new CommonTokenStream(lexer);
            stream.fill();

            ParseTree tree = this.getParseTree(stream);
            antlrContext.setTree(tree);
            antlrContext.setStream(stream);
            antlrContext.setTypes(getTypesMap());
            antlrContext.setLexer(lexer);
            antlrContext.setBasicText(text);
            String textWithoutComments = replaceComments(text, Constants.PLUGIN_SQL_COMMENT_START, Constants.PLUGIN_SQL_COMMENTS_FINISH);
            antlrContext.setTextWithoutComments(textWithoutComments.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            LOGGER.warn("unxepected exception while trying to get lexer and set antlrContext attributes", e);
        }
        return antlrContext;
    }

    public AntlrContext addRulesToContext(AntlrContext antlrContext) {
        try {
            sqlDialectRules.getRules().forEach(r -> antlrContext.getRules().add(r));
        } catch (Exception e) {
            LOGGER.warn("unxepected exception while trying to get Rules: ", e);
        }
        return antlrContext;
    }

    private String skipLines(String text, String textStartSkip, String textEndSkip) {
        boolean isFinishSkipLinesFound = false;
        String[] lines = text.split(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        for (int i = 0; i < lines.length; i++) {
            if (isFinishSkipLinesFound) {
                isFinishSkipLinesFound = false;
                continue;
            }
            if (StringUtils.contains(lines[i], (textStartSkip))) {
                for (int j = i + 1; j < lines.length; j++) {

                    if (StringUtils.contains(lines[j], (textEndSkip))) {
                        isFinishSkipLinesFound = true;
                        break;
                    } else {
                        lines[j] = "--";
                    }
                }
            }
        }
        text = String.join(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX, lines);
        return text;
    }

    private String replaceComments(String text, String startComment, String finishComment) {
        String textWithoutComment = text.toUpperCase(Locale.ROOT);
        textWithoutComment.replace(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_WINDOWS, Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        String[] lines = textWithoutComment.split(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);

        if (lines.length == 1) {
            try {
                Pattern pattern = Pattern.compile(Constants.PLUGIN_SQL_REGEX_COMMENTS_MANY_LINES, Pattern.DOTALL);
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    textWithoutComment = matcher.replaceFirst(Constants.PLUGIN_SQL_COMMENTS_REPLACEMENT);
                }


            } catch (Exception e) {
                LOGGER.warn("unxepected exception while trying to replace comments in one lign sql file: ", e);
            }
        } else {
            replaceManyLineComments(startComment, finishComment, lines);
            textWithoutComment = String.join(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX, lines);
        }
        return textWithoutComment;
    }

    private void replaceManyLineComments(String startComment, String finishComment, String[] lines) {
        boolean isFinishComment = false;
        for (int i = 0; i < lines.length; i++) {
            if (isFinishComment) {
                isFinishComment = false;
                continue;
            }
            if (StringUtils.contains(lines[i], startComment)) {
                isFinishComment = replaceCommentsInManyLines(finishComment, lines, i);
            }
            if (StringUtils.contains(lines[i], Constants.PLUGIN_SQL_COMMENTS_SQL_LINE)) {
                lines[i] = lines[i].replaceAll(Constants.PLUGIN_SQL_REGEX_COMMENTS_ONE_LINE, Constants.PLUGIN_SQL_COMMENTS_REPLACEMENT);
            }
        }
    }

    private boolean replaceCommentsInManyLines(String finishComment, String[] lines, int i) {
        boolean isFinishComment = false;
        for (int j = i; j < lines.length; j++) {
            if (StringUtils.contains(lines[j], finishComment)) {
                lines[j] = lines[j].replaceAll(Constants.PLUGIN_SQL_REGEX_COMMENT_FINISH, Constants.PLUGIN_SQL_COMMENTS_REPLACEMENT);
                isFinishComment = true;
                break;
            } else if (j == i) {
                lines[j] = lines[j].replaceAll(Constants.PLUGIN_SQL_REGEX_COMMENT_START, Constants.PLUGIN_SQL_COMMENTS_REPLACEMENT);
            } else {
                lines[j] = Constants.PLUGIN_SQL_COMMENTS_REPLACEMENT;
            }
        }
        return isFinishComment;
    }
}