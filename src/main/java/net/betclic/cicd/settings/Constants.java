package net.betclic.cicd.settings;

public final class Constants {
    public static final String LANGUAGE_KEY = "sql";
    public static final String PLUGIN_SQL_DIALECT = "sql";
    public static final String PLUGIN_SUFFIXES = ".sql";
    public static final String PLUGIN_SQL_RULES_SKIP = "sonar.sql.rules.skip";
    public static final String PLUGIN_SQL_FILE_SKIP = "//NOSONAR";
    public static final String PLUGIN_SQL_LINES_SKIP = "SONAR_SKIP_LINES";
    public static final String PLUGIN_SQL_LINES_SKIP_CLOSE = "SONAR_END_SKIP_LINES";
    public static final String PLUGIN_SQL_REGEX_COMMENT_START = "\\/\\*.*";
    public static final String PLUGIN_SQL_COMMENT_START = "/*";
    public static final String PLUGIN_SQL_REGEX_COMMENT_FINISH = ".*\\*\\/";
    public static final String PLUGIN_SQL_COMMENTS_FINISH = "*/";
    public static final String PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX = "\n";
    public static final String PLUGIN_SQL_FILES_LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM = System.getProperty("line.separator");
    public static final String PLUGIN_SQL_COMMENTS_SQL_LINE = "--";
    public static final String PLUGIN_SQL_REGEX_COMMENTS_MANY_LINES = "\\/\\*.*\\*\\/|(--.*$)";
    public static final String PLUGIN_SQL_REGEX_COMMENTS_ONE_LINE = "--.*";
    public static final String PLUGIN_SQL_COMMENTS_REPLACEMENT = "/* */";
    public static final String CUSTOM_RULES_FOLDER = "rules/customRules/";
    public static final String NATIVE_RULES_FOLDER = "rules/nativeRules/";

}
