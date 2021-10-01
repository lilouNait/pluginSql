package net.betclic.cicd.scanner;

import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.issuesearcher.IssuesSearcher;
import net.betclic.cicd.language.TSQLDialect;
import net.betclic.cicd.measures.IssuesProvider;
import net.betclic.cicd.measures.SqlIssuesList;
import net.betclic.cicd.settings.Constants;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SqlSensor implements Sensor {

    private static final Logger LOGGER = Loggers.get(SqlSensor.class);
    TSQLDialect tsqlDialect = new TSQLDialect();

    private final IssuesProvider provider = new IssuesProvider();
    private final IssuesSearcher issuesSearcher = new IssuesSearcher();

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor.onlyOnLanguage(Constants.LANGUAGE_KEY);
    }

    @Override
    public void execute(SensorContext context) {
        final ExecutorService service = Executors.newWorkStealingPool();
        org.sonar.api.batch.fs.FileSystem fs = context.fileSystem();
        final Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasLanguage(Constants.LANGUAGE_KEY));

        for (InputFile inputFile : files) {
            final AntlrContext antlrContext;
            try {
                if (!StringUtils.contains(inputFile.contents(), Constants.PLUGIN_SQL_FILE_SKIP)) {

                    antlrContext = tsqlDialect.parse(inputFile.contents());

                    tsqlDialect.addRulesToContext(antlrContext);
                    service.execute(() -> {

                        //Parse the content of input file in tsql dialect to create an antlr context and
                        parseAnalyzeAndFill(context, inputFile, antlrContext);

                    });

                }
            } catch (IOException e) {
                LOGGER.warn("unxepected exception while parsing and adding rules", e);
            }
        }
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.warn("unxepected exception while waiting for executor service to finish", e);
        }
    }

    private void parseAnalyzeAndFill(SensorContext context, InputFile inputFile, AntlrContext antlrContext) {
        try {
            // Parse input file

            // Analyze to find issues - recursive way
            SqlIssuesList list = provider.recursiveAnalyzer(antlrContext);
            //Fill issues - iterative way
            SqlIssuesList list1 = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
            // Analyse to find issues - iterative way
            IssueUtil.addIssues(context, list, inputFile);
            IssueUtil.addIssues(context, list1, inputFile);
            if (list.getAllIssues() != null || list1.getAllIssues() != null) {
                LOGGER.info(">> sonar sql issues found in the file " + inputFile.filename() + " ,Check on sonar for more details");
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected exception while analyzing file: " + inputFile, e);
        }
    }

}
