package net.betclic.cicd.language;

import net.betclic.cicd.settings.Constants;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

public final class SqlLanguages extends AbstractLanguage {

    protected static final String NAME = "SQL";
    protected static final String[] DEFAULT_FILE_SUFFIXES = new String[]{".sql"};
    private final org.sonar.api.config.Configuration configuration;

    public SqlLanguages(final Configuration configuration) {
        super(Constants.LANGUAGE_KEY, NAME);
        this.configuration = configuration;
    }

    @Override
    public String[] getFileSuffixes() {
        final String[] suffixes = configuration.getStringArray(Constants.PLUGIN_SUFFIXES);
        if (suffixes == null || suffixes.length == 0) {
            return DEFAULT_FILE_SUFFIXES;
        }
        return suffixes;
    }
}
