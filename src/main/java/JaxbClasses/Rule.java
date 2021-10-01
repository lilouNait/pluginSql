
package JaxbClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import net.betclic.cicd.issuesearcher.BehaviourAction;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.debt.DebtRemediationFunction;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}key"/>
 *         &lt;element ref="{}name"/>
 *         &lt;element ref="{}internalKey"/>
 *         &lt;element ref="{}descriptionFormat"/>
 *         &lt;element ref="{}description"/>
 *         &lt;element ref="{}severity"/>
 *         &lt;element ref="{}cardinality"/>
 *         &lt;element ref="{}status"/>
 *         &lt;element ref="{}remediationFunction"/>
 *         &lt;element ref="{}remediationFunctionBaseEffort"/>
 *         &lt;element ref="{}debtRemediationFunctionCoefficient"/>
 *         &lt;element ref="{}tag"/>
 *         &lt;element ref="{}ruleImplementation"/>
 *         &lt;element ref="{}source"/>
 *         &lt;element ref="{}ruleType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "key",
        "name",
        "internalKey",
        "descriptionFormat",
        "description",
        "severity",
        "cardinality",
        "status",
        "remediationFunction",
        "remediationFunctionBaseEffort",
        "debtRemediationFunctionCoefficient",
        "tag",
        "ruleImplementation",
        "source",
        "ruleType",
        "contextRegex",
        "listIssuesRegex",
        "conditionContainedInFileName",
        "conditionContainedInFile",
        "behaviourIfConditionFileFound"


})
@XmlRootElement(name = "rule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {

    @JacksonXmlProperty
    protected String key;
    @JacksonXmlProperty
    protected String name;
    @JacksonXmlProperty
    protected String internalKey;
    @JacksonXmlProperty
    protected String descriptionFormat;
    @JacksonXmlProperty
    protected String description;
    @JacksonXmlProperty
    protected String severity;
    @JacksonXmlProperty
    protected String cardinality;
    @JacksonXmlProperty
    protected String status;
    @JacksonXmlProperty
    protected String remediationFunction;
    @JacksonXmlProperty
    protected String remediationFunctionBaseEffort;
    @JacksonXmlProperty
    protected String debtRemediationFunctionCoefficient;
    @JacksonXmlProperty
    protected String tag;
    @JacksonXmlProperty
    protected RuleImplementation ruleImplementation;
    @JacksonXmlProperty
    protected String source;
    @JacksonXmlProperty
    protected String ruleType;
    @JacksonXmlProperty
    protected String contextRegex;
    @JacksonXmlElementWrapper(localName = "listIssuesRegex")
    @JacksonXmlProperty(localName = "regex")
    protected List<String> listIssuesRegex;
    @JacksonXmlProperty
    protected String conditionContainedInFileName;
    @JacksonXmlProperty
    protected String conditionContainedInFile;
    @JacksonXmlProperty
    protected BehaviourAction behaviourIfConditionFileFound;

    public Rule() {
    }

    public Rule(String key, String name, String descriptionRule, String tag, String debt) {
        this.key = key;
        this.internalKey = key;
        this.name = name;
        this.tag = tag;
        this.description = descriptionRule;
        this.severity = Severity.MINOR;
        this.remediationFunction = String.valueOf(DebtRemediationFunction.Type.LINEAR);
        this.debtRemediationFunctionCoefficient = debt;
    }

    public Rule(String key, String name, String description, String tag, String debt, String contextRegex, List<String> listIssuesRegex) {
        this(key, name, description, tag, debt);
        this.contextRegex = contextRegex;
        this.listIssuesRegex = listIssuesRegex;
    }

    public Rule(String keyRule, String nameRule, String description, String tag, String debt, String contextRegex, List<String> listIssuesRegex, String conditionContainedInFileName) {
        this(keyRule, nameRule, description, tag, debt, contextRegex, listIssuesRegex);
        this.conditionContainedInFileName = conditionContainedInFileName;
    }

    public Rule(String key, String name, String description, String tag, String debt, String contextRegex,
                List<String> listIssuesRegex, String conditionContainedInFileName, String conditionContainedInFile, BehaviourAction behaviourAction) {
        this(key, name, description, tag, debt, contextRegex, listIssuesRegex, conditionContainedInFileName);
        this.conditionContainedInFile = conditionContainedInFile;
        this.behaviourIfConditionFileFound = behaviourAction;
    }

    /**
     * Gets the value of the key property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the internalKey property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInternalKey() {
        return internalKey;
    }

    /**
     * Sets the value of the internalKey property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInternalKey(String value) {
        this.internalKey = value;
    }

    /**
     * Gets the value of the descriptionFormat property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescriptionFormat() {
        return descriptionFormat;
    }

    /**
     * Sets the value of the descriptionFormat property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescriptionFormat(String value) {
        this.descriptionFormat = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the severity property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSeverity(String value) {
        this.severity = value;
    }

    /**
     * Gets the value of the cardinality property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCardinality() {
        return cardinality;
    }

    /**
     * Sets the value of the cardinality property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCardinality(String value) {
        this.cardinality = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the remediationFunction property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRemediationFunction() {
        return remediationFunction;
    }

    /**
     * Sets the value of the remediationFunction property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRemediationFunction(String value) {
        this.remediationFunction = value;
    }

    /**
     * Gets the value of the remediationFunctionBaseEffort property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRemediationFunctionBaseEffort() {
        return remediationFunctionBaseEffort;
    }

    /**
     * Sets the value of the remediationFunctionBaseEffort property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRemediationFunctionBaseEffort(String value) {
        this.remediationFunctionBaseEffort = value;
    }

    /**
     * Gets the value of the debtRemediationFunctionCoefficient property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDebtRemediationFunctionCoefficient() {
        return debtRemediationFunctionCoefficient;
    }

    /**
     * Sets the value of the debtRemediationFunctionCoefficient property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDebtRemediationFunctionCoefficient(String value) {
        this.debtRemediationFunctionCoefficient = value;
    }

    /**
     * Gets the value of the tag property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the ruleImplementation property.
     *
     * @return possible object is
     * {@link RuleImplementation }
     */
    public RuleImplementation getRuleImplementation() {
        return ruleImplementation;
    }

    /**
     * Sets the value of the ruleImplementation property.
     *
     * @param value allowed object is
     *              {@link RuleImplementation }
     */
    public void setRuleImplementation(RuleImplementation value) {
        this.ruleImplementation = value;
    }

    /**
     * Gets the value of the source property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the ruleType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * Sets the value of the ruleType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRuleType(String value) {
        this.ruleType = value;
    }

    public String getContextRegex() {
        return contextRegex;
    }

    public void setContextRegex(String contextRegex) {
        this.contextRegex = contextRegex;
    }

    public List<String> getListIssuesRegex() {
        return listIssuesRegex;
    }

    public void setListIssuesRegex(List<String> listIssuesRegex) {
        this.listIssuesRegex = listIssuesRegex;
    }

    public String getConditionContainedInFileName() {
        return conditionContainedInFileName;
    }

    public void setConditionContainedInFileName(String conditionContainedInFileName) {
        this.conditionContainedInFileName = conditionContainedInFileName;
    }

    public String getConditionContainedInFile() {
        return conditionContainedInFile;
    }

    public void setConditionContainedInFile(String conditionContainedInFile) {
        this.conditionContainedInFile = conditionContainedInFile;
    }

    public BehaviourAction getBehaviourIfConditionFileFound() {
        return behaviourIfConditionFileFound;
    }

    public void setBehaviourIfConditionFileFound(BehaviourAction behaviourIfConditionFileFound) {
        this.behaviourIfConditionFileFound = behaviourIfConditionFileFound;
    }
}
