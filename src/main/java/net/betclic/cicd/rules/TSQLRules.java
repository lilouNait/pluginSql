package net.betclic.cicd.rules;

import JaxbClasses.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.betclic.cicd.language.tsql.TsqlParser;
import net.betclic.cicd.settings.Constants;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class TSQLRules {

    private final static XmlMapper xmlMapper = new XmlMapper();
    static BaseRules baseRules = BaseRules.INSTANCE;
    private static final Logger LOGGER = Loggers.get(TSQLRules.class);

    private TSQLRules() {
        throw new IllegalStateException("Utility class");
    }

    private static List<SqlRules> allRules = null;

    public static synchronized List<SqlRules> getRules() {
        if (allRules == null) {
            allRules = loadRules();
        }
        return allRules;
    }

    public static synchronized List<SqlRules> loadRules() {

        List<SqlRules> rules = new ArrayList<>();
        List<Rule> listNativeRules = addNativeRules();
        List<Rule> listCustomRules = Collections.emptyList();
        try {
            listCustomRules = addCustomRules();
        } catch (IOException e) {
            LOGGER.error("!!! Unexpected error when trying to load rules from xml custom rules files: {} ", e);
        }
        initializeRepositoryRules(rules, listNativeRules);
        LOGGER.info(">>> native rules loaded: {} ", listNativeRules.size());
        initializeRepositoryRules(rules, listCustomRules);
        LOGGER.info(">>> Custom rules loaded: {} ", listCustomRules.size());
        return rules;
    }

    private static void initializeRepositoryRules(List<SqlRules> rules, List<Rule> listNativeRules) {
        listNativeRules.forEach(rule -> {
            SqlRules nativeRules = new SqlRules("SQL Plugin checks", "SQL-RULES", "SQL");
            nativeRules.getRules().add(rule);
            rules.add(nativeRules);
        });
    }

    private static List<Rule> addNativeRules() {
        return Arrays.asList(
                createUseRule(),
                createIdentityRule(),
                createErrorRule(),
                createErrorHandleRule(),
                createThrowRule(),
                createCreationProcedureRule(),
                createCreationViewRule(),
                createCreationTriggerRule(),
                createHeaderRule(),
                createCreateFunctionRule(),
                createGrantRule(),
                createGrantGoRule(),
                createPrintRule(),
                createSchemaObjectCreateProcedureRule(),
                createSchemaObjectGrantRule(),
                createGrantMissingRule());
    }

    private static List<Rule> addCustomRules() throws IOException {
        List<Rule> listCustomRules = new ArrayList<>();
        final String path = Constants.CUSTOM_RULES_FOLDER;
        final File foundResource = new File(TSQLRules.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (foundResource.isFile()) {
            try (JarFile jar = new JarFile(foundResource)) {
                loadCustomRulesFromPluginJar(listCustomRules, path, jar);
            } catch (Exception e) {
                LOGGER.error("Unexpected exception while trying to use the Jar file" + e);
            }
        } else {
            loadCustomRules(listCustomRules, path);
        }
        return listCustomRules;
    }

    static void loadCustomRulesFromPluginJar(List<Rule> listCustomRules, String path, JarFile jarFile) {
        final Enumeration<JarEntry> entries = jarFile.entries();
        List<JarEntry> listEntries = Collections.list(entries).stream()
                .filter(e -> e.getName().endsWith(".xml") && e.getName().startsWith(path))
                .collect(Collectors.toList());

        for (JarEntry e : listEntries) {
            try (InputStream inputStream = jarFile.getInputStream(e)) {
                Rule rule = xmlMapper.readValue(inputStream, Rule.class);
                listCustomRules.add(rule);
            } catch (IOException ioe) {
                LOGGER.error("Unexpected exception while trying to map xml file of custom rules " + ioe);
            }
        }
    }


    private static void loadCustomRules(List<Rule> listCustomRules, String path) throws IOException {
        InputStream inputStream = TSQLRules.class.getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String resource;
        while ((resource = br.readLine()) != null) {
            if (resource.endsWith(".xml")) {
                Rule rule = readRuleFromXml(path.concat(resource));
                listCustomRules.add(rule);
            }
        }
        LOGGER.info(">>> Number of files found in the directory of customRules " + listCustomRules.size());
    }

    private static Rule readRuleFromXml(String fileNameBasePath) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        // First check if resource exists
        if (TSQLRules.class.getClassLoader().getResource(fileNameBasePath) == null) {
            throw new FileNotFoundException("Resource not found: " + fileNameBasePath);
        }
        // Then get the contents (stream) and create a reader to read its data
        if (TSQLRules.class.getClassLoader().getResourceAsStream(fileNameBasePath) == null) {
            throw new FileNotFoundException("Resource not found: " + fileNameBasePath);
        }

        InputStream stream = TSQLRules.class.getClassLoader().getResourceAsStream(fileNameBasePath);
        return xmlMapper.readValue(stream, Rule.class);

    }

    public static Rule createUseRule() {

        Rule rule = baseRules.createUseRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("USE");
        ruleImpl.setTextToFind(textToFind);
        ruleImpl.setTextCheckType(TextCheckType.CONTAINS);
        ruleImpl.setRuleMatchType(RuleMatchType.TEXT_AND_CLASS);

        Names names = new Names();
        names.getTextItem().add(TsqlParser.Use_statementContext.class.getSimpleName());
        ruleImpl.setNames(names);
        List<String> textItem = new ArrayList<>();
        textItem.add(TsqlParser.Use_statementContext.class.getSimpleName());

        return setNames(rule, ruleImpl, textItem);
    }

    public static Rule createIdentityRule() {

        Rule rule = baseRules.createIdentityRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("@@IDENTITY");
        return setForbiddenText(rule, ruleImpl, textToFind);
    }

    public static Rule createPrintRule() {
        Rule rule = baseRules.createPrintRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("PRINT");
        return setForbiddenText(rule, ruleImpl, textToFind);
    }

    public static Rule setForbiddenText(Rule rule, RuleImplementation ruleImpl, TextToFind textToFind) {
        ruleImpl.setTextToFind(textToFind);
        ruleImpl.setTextCheckType(TextCheckType.STRICT);
        ruleImpl.setRuleMatchType(RuleMatchType.TEXT_ONLY);

        Names names = new Names();
        names.getTextItem().add(TsqlParser.Primitive_expressionContext.class.getSimpleName());
        ruleImpl.setNames(names);
        List<String> textItem = new ArrayList<>();
        textItem.add(TsqlParser.Primitive_expressionContext.class.getSimpleName());
        return setNames(rule, ruleImpl, textItem);
    }

    public static Rule createErrorRule() {

        Rule rule = baseRules.createErrorRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("@@ERROR");
        return setRuleImplementation(rule, ruleImpl, textToFind);
    }

    public static Rule createErrorHandleRule() {

        Rule rule = baseRules.createErrorHandleRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("error_handle");
        return setRuleImplementation(rule, ruleImpl, textToFind);
    }

    public static Rule createGrantMissingRule() {
        return baseRules.createGrantBlockMissingRule();
    }

    public static Rule createCreationTriggerRule() {
        Rule rule = baseRules.createTriggerRule();
        RuleImplementation ruleImplementation = rule.getRuleImplementation();
        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("[");
        textToFind.getTextItem().add("]");
        textToFind.getTextItem().add("[u_");
        textToFind.getTextItem().add("[i_");
        textToFind.getTextItem().add("[d_");

        Names names = new Names();
        names.getTextItem().add("CREATETRIGGER");
        ruleImplementation.setNames(names);
        return rule;
    }

    public static Rule createThrowRule() {

        Rule rule = baseRules.createThrowRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();

        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("THROW");
        ruleImpl.setTextToFind(textToFind);
        ruleImpl.setTextCheckType(TextCheckType.STRICT);
        ruleImpl.setRuleMatchType(RuleMatchType.TEXT_ONLY);

        Names names = new Names();
        names.getTextItem().add(TsqlParser.Throw_statementContext.class.getSimpleName());
        ruleImpl.setNames(names);
        List<String> textItem = new ArrayList<>();
        textItem.add(TsqlParser.Throw_statementContext.class.getSimpleName());
        Names names1 = new Names();
        names1.setTextItem(textItem);
        ruleImpl.setNames(names1);
        ruleImpl.setRuleResultType(RuleResultType.FAIL_IF_NOT_FOUND);

        setRuleImplChild(ruleImpl);

        return rule;
    }

    public static Rule createHeaderRule() {
        Rule rule = baseRules.createHeaderRule();
        RuleImplementation ruleImplementation = rule.getRuleImplementation();
        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("Description");
        textToFind.getTextItem().add("History");
        textToFind.getTextItem().add("Title");
        textToFind.getTextItem().add("Parameters");
        Names names = new Names();
        names.getTextItem().add("createfunction");
        names.getTextItem().add("createoralterprocedure");

        ruleImplementation.setNames(names);
        return rule;
    }

    /**
     * Declaring what will be looked for is sql code using this rule.
     * names represent the class od sql code that will be checked using this rule
     * TextToFind represent the text to search in the sql code that will be checked using this rule
     */

    public static Rule createCreateFunctionRule() {
        Rule rule = baseRules.createFunctionRule();
        RuleImplementation ruleImplementation = rule.getRuleImplementation();
        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("[");
        textToFind.getTextItem().add("].");
        textToFind.getTextItem().add("]");

        Names names = new Names();
        names.getTextItem().add("CREATEFUNCTION");
        ruleImplementation.setNames(names);
        return rule;
    }

    public static Rule createCreationViewRule() {
        Rule rule = baseRules.createViewRule();
        RuleImplementation ruleImpl = rule.getRuleImplementation();
        Names names = new Names();
        names.getTextItem().add(TsqlParser.Create_viewContext.class.getSimpleName());
        ruleImpl.setNames(names);
        ruleImpl.setRuleMatchType(RuleMatchType.CLASS_ONLY);

        RuleImplementation child1 = new RuleImplementation();
        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("view");
        child1.setTextToFind(textToFind);
        Names names1 = new Names();
        names1.getTextItem().add(TerminalNodeImpl.class.getSimpleName());
        child1.setNames(names1);
        child1.setRuleResultType(RuleResultType.SKIP_IF_NOT_FOUND);
        child1.setRuleMatchType(RuleMatchType.TEXT_AND_CLASS);

        ChildrenRules childrenRules = new ChildrenRules();
        childrenRules.getRuleImplementation().add(child1);
        ruleImpl.setChildrenRules(childrenRules);

        RuleImplementation main = new RuleImplementation();
        Names names2 = new Names();
        names2.getTextItem().add(TsqlParser.View_nameContext.class.getSimpleName());
        main.setNames(names2);
        main.setTextCheckType(TextCheckType.CONTAINS);
        TextToFind textToFind1 = new TextToFind();
        textToFind1.getTextItem().add("].[v");

        main.setTextToFind(textToFind1);
        main.setRuleMatchType(RuleMatchType.TEXT_AND_CLASS);
        main.setRuleResultType(RuleResultType.FAIL_IF_NOT_FOUND);

        ChildrenRules childrenRules1 = new ChildrenRules();
        childrenRules1.getRuleImplementation().add(main);
        ruleImpl.setChildrenRules(childrenRules1);
        return rule;
    }

    public static Rule createCreationProcedureRule() {
        Rule rule = baseRules.createProcedureRule();
        RuleImplementation ruleImplementation = rule.getRuleImplementation();
        TextToFind textToFind = new TextToFind();
        textToFind.getTextItem().add("AS");
        textToFind.getTextItem().add("BEGIN");
        textToFind.getTextItem().add("TRY");

        Names names = new Names();
        names.getTextItem().add("CREATE");
        ruleImplementation.setNames(names);
        return rule;
    }

    public static Rule createGrantRule() {
        return BaseRules.createGrantRule();
    }

    public static Rule createGrantGoRule() {
        return baseRules.createGrantGoRule();
    }

    public static Rule createSchemaObjectCreateProcedureRule() {
        return baseRules.createSchemaObjectRule();
    }

    public static Rule createSchemaObjectGrantRule() {
        return baseRules.createSchemaObjectGrantRule();
    }

    private static void setRuleImplChild(RuleImplementation ruleImpl) {
        List<String> textItemDefine = new ArrayList<>();
        textItemDefine.add(TsqlParser.Throw_statementContext.class.getSimpleName());
        Names namesDefine = new Names();
        namesDefine.setTextItem(textItemDefine);

        setChildrenRules(ruleImpl, namesDefine);
    }

    private static void setChildrenRules(RuleImplementation ruleImpl, Names namesDefine) {
        ChildrenRules childrenValue = new ChildrenRules();
        RuleImplementation child2 = new RuleImplementation();
        child2.setNames(namesDefine);
        child2.setTextCheckType(TextCheckType.STRICT);
        child2.setRuleResultType(RuleResultType.FAIL_IF_FOUND);
        child2.setRuleMatchType(RuleMatchType.CLASS_ONLY);

        List<RuleImplementation> listRuleImplementation = new ArrayList<>();
        listRuleImplementation.add(child2);
        childrenValue.setRuleImplementation(listRuleImplementation);
        ruleImpl.setChildrenRules(childrenValue);
    }

    private static Rule setRuleImplementation(Rule rule, RuleImplementation ruleImpl, TextToFind textToFind) {
        ruleImpl.setTextToFind(textToFind);
        ruleImpl.setTextCheckType(TextCheckType.STRICT);
        ruleImpl.setRuleMatchType(RuleMatchType.TEXT_ONLY);

        Names names = new Names();
        names.getTextItem().add(TsqlParser.Raiseerror_statementContext.class.getSimpleName());
        ruleImpl.setNames(names);
        List<String> textItem = new ArrayList<>();
        textItem.add(TsqlParser.Try_catch_statementContext.class.getSimpleName());
        return setNames(rule, ruleImpl, textItem);
    }

    private static Rule setNames(Rule rule, RuleImplementation ruleImpl, List<String> textItem) {
        Names names1 = new Names();
        names1.setTextItem(textItem);
        ruleImpl.setNames(names1);
        ruleImpl.setRuleResultType(RuleResultType.FAIL_IF_FOUND);


        List<String> textItemDefine = new ArrayList<>();
        textItemDefine.add(TsqlParser.Primitive_expressionContext.class.getSimpleName());
        Names namesDefine = new Names();
        namesDefine.setTextItem(textItemDefine);
        setRuleImplChild(ruleImpl);

        return rule;
    }

}


