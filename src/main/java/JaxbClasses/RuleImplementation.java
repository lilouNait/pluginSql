
package JaxbClasses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.debt.DebtRemediationFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}names"/>
 *         &lt;element ref="{}textToFind"/>
 *         &lt;element ref="{}parentRules" minOccurs="0"/>
 *         &lt;element ref="{}childrenRules" minOccurs="0"/>
 *         &lt;element ref="{}siblingsRules" minOccurs="0"/>
 *         &lt;element ref="{}usesRules" minOccurs="0"/>
 *         &lt;element ref="{}ruleViolationMessage"/>
 *         &lt;element name="times" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="distance" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="indexCheckType" type="{}ruleDistanceIndexMatchType"/>
 *         &lt;element name="distanceCheckType" type="{}ruleDistanceIndexMatchType"/>
 *         &lt;element name="ruleMode" type="{}ruleMode"/>
 *         &lt;element name="ruleMatchType" type="{}ruleMatchType"/>
 *         &lt;element name="ruleResultType" type="{}ruleResultType"/>
 *         &lt;element name="textCheckType" type="{}textCheckType"/>
 *         &lt;element ref="{}violatingRulesCodeExamples"/>
 *         &lt;element ref="{}compliantRulesCodeExamples"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "names",
    "textToFind",
    "parentRules",
    "childrenRules",
    "siblingsRules",
    "usesRules",
    "ruleViolationMessage",
    "times",
    "distance",
    "index",
    "indexCheckType",
    "distanceCheckType",
    "ruleMode",
    "ruleMatchType",
    "ruleResultType",
    "textCheckType",
    "violatingRulesCodeExamples",
    "compliantRulesCodeExamples"
})
@XmlRootElement(name = "ruleImplementation")
public class RuleImplementation {

    @JacksonXmlProperty
    protected Names names;
    @JacksonXmlProperty
    protected TextToFind textToFind;
    protected ParentRules parentRules;
    protected ChildrenRules childrenRules;
    protected SiblingsRules siblingsRules;
    protected UsesRules usesRules;
    @JacksonXmlProperty
    protected String ruleViolationMessage;
    @XmlElement(defaultValue = "1")
    protected int times;
    @XmlElement(defaultValue = "0")
    protected int distance;
    @XmlElement(defaultValue = "0")
    protected int index;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RuleDistanceIndexMatchType indexCheckType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RuleDistanceIndexMatchType distanceCheckType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RuleMode ruleMode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RuleMatchType ruleMatchType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RuleResultType ruleResultType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TextCheckType textCheckType;
    @JacksonXmlProperty
    protected ViolatingRulesCodeExamples violatingRulesCodeExamples;
    @JacksonXmlProperty
    protected CompliantRulesCodeExamples compliantRulesCodeExamples;

    public RuleImplementation(){
    }

    public RuleImplementation(String ruleViolationMessage, ViolatingRulesCodeExamples violatingRulesCodeExamples, CompliantRulesCodeExamples compliantRulesCodeExamples){
        this.ruleViolationMessage = ruleViolationMessage;
        this.violatingRulesCodeExamples = violatingRulesCodeExamples;
        this.compliantRulesCodeExamples = compliantRulesCodeExamples;
    }
    /**
     * Gets the value of the names property.
     * 
     * @return
     *     possible object is
     *     {@link Names }
     *     
     */
    public Names getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     * 
     * @param value
     *     allowed object is
     *     {@link Names }
     *     
     */
    public void setNames(Names value) {
        this.names = value;
    }

    /**
     * Gets the value of the textToFind property.
     * 
     * @return
     *     possible object is
     *     {@link TextToFind }
     *     
     */
    public TextToFind getTextToFind() {
        return textToFind;
    }

    /**
     * Sets the value of the textToFind property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextToFind }
     *     
     */
    public void setTextToFind(TextToFind value) {
        this.textToFind = value;
    }

    /**
     * Gets the value of the parentRules property.
     * 
     * @return
     *     possible object is
     *     {@link ParentRules }
     *     
     */
    public ParentRules getParentRules() {
        return parentRules;
    }

    /**
     * Sets the value of the parentRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentRules }
     *     
     */
    public void setParentRules(ParentRules value) {
        this.parentRules = value;
    }

    /**
     * Gets the value of the childrenRules property.
     * 
     * @return
     *     possible object is
     *     {@link ChildrenRules }
     *     
     */
    public ChildrenRules getChildrenRules() {
        return childrenRules;
    }

    /**
     * Sets the value of the childrenRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildrenRules }
     *     
     */
    public void setChildrenRules(ChildrenRules value) {
        this.childrenRules = value;
    }

    /**
     * Gets the value of the siblingsRules property.
     * 
     * @return
     *     possible object is
     *     {@link SiblingsRules }
     *     
     */
    public SiblingsRules getSiblingsRules() {
        return siblingsRules;
    }

    /**
     * Sets the value of the siblingsRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link SiblingsRules }
     *     
     */
    public void setSiblingsRules(SiblingsRules value) {
        this.siblingsRules = value;
    }

    /**
     * Gets the value of the usesRules property.
     * 
     * @return
     *     possible object is
     *     {@link UsesRules }
     *     
     */
    public UsesRules getUsesRules() {
        return usesRules;
    }

    /**
     * Sets the value of the usesRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsesRules }
     *     
     */
    public void setUsesRules(UsesRules value) {
        this.usesRules = value;
    }

    /**
     * Gets the value of the ruleViolationMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleViolationMessage() {
        return ruleViolationMessage;
    }

    /**
     * Sets the value of the ruleViolationMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleViolationMessage(String value) {
        this.ruleViolationMessage = value;
    }

    /**
     * Gets the value of the times property.
     * 
     */
    public int getTimes() {
        return times;
    }

    /**
     * Sets the value of the times property.
     * 
     */
    public void setTimes(int value) {
        this.times = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     */
    public void setDistance(int value) {
        this.distance = value;
    }

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the indexCheckType property.
     * 
     * @return
     *     possible object is
     *     {@link RuleDistanceIndexMatchType }
     *     
     */
    public RuleDistanceIndexMatchType getIndexCheckType() {
        return indexCheckType;
    }

    /**
     * Sets the value of the indexCheckType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleDistanceIndexMatchType }
     *     
     */
    public void setIndexCheckType(RuleDistanceIndexMatchType value) {
        this.indexCheckType = value;
    }

    /**
     * Gets the value of the distanceCheckType property.
     * 
     * @return
     *     possible object is
     *     {@link RuleDistanceIndexMatchType }
     *     
     */
    public RuleDistanceIndexMatchType getDistanceCheckType() {
        return distanceCheckType;
    }

    /**
     * Sets the value of the distanceCheckType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleDistanceIndexMatchType }
     *     
     */
    public void setDistanceCheckType(RuleDistanceIndexMatchType value) {
        this.distanceCheckType = value;
    }

    /**
     * Gets the value of the ruleMode property.
     * 
     * @return
     *     possible object is
     *     {@link RuleMode }
     *     
     */
    public RuleMode getRuleMode() {
        return ruleMode;
    }

    /**
     * Sets the value of the ruleMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleMode }
     *     
     */
    public void setRuleMode(RuleMode value) {
        this.ruleMode = value;
    }

    /**
     * Gets the value of the ruleMatchType property.
     * 
     * @return
     *     possible object is
     *     {@link RuleMatchType }
     *     
     */
    public RuleMatchType getRuleMatchType() {
        return ruleMatchType;
    }

    /**
     * Sets the value of the ruleMatchType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleMatchType }
     *     
     */
    public void setRuleMatchType(RuleMatchType value) {
        this.ruleMatchType = value;
    }

    /**
     * Gets the value of the ruleResultType property.
     * 
     * @return
     *     possible object is
     *     {@link RuleResultType }
     *     
     */
    public RuleResultType getRuleResultType() {
        return ruleResultType;
    }

    /**
     * Sets the value of the ruleResultType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleResultType }
     *     
     */
    public void setRuleResultType(RuleResultType value) {
        this.ruleResultType = value;
    }

    /**
     * Gets the value of the textCheckType property.
     * 
     * @return
     *     possible object is
     *     {@link TextCheckType }
     *     
     */
    public TextCheckType getTextCheckType() {
        return textCheckType;
    }

    /**
     * Sets the value of the textCheckType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextCheckType }
     *     
     */
    public void setTextCheckType(TextCheckType value) {
        this.textCheckType = value;
    }

    /**
     * Gets the value of the violatingRulesCodeExamples property.
     * 
     * @return
     *     possible object is
     *     {@link ViolatingRulesCodeExamples }
     *     
     */
    public ViolatingRulesCodeExamples getViolatingRulesCodeExamples() {
        return violatingRulesCodeExamples;
    }

    /**
     * Sets the value of the violatingRulesCodeExamples property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViolatingRulesCodeExamples }
     *     
     */
    public void setViolatingRulesCodeExamples(ViolatingRulesCodeExamples value) {
        this.violatingRulesCodeExamples = value;
    }

    /**
     * Gets the value of the compliantRulesCodeExamples property.
     * 
     * @return
     *     possible object is
     *     {@link CompliantRulesCodeExamples }
     *     
     */
    public CompliantRulesCodeExamples getCompliantRulesCodeExamples() {
        return compliantRulesCodeExamples;
    }

    /**
     * Sets the value of the compliantRulesCodeExamples property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompliantRulesCodeExamples }
     *     
     */
    public void setCompliantRulesCodeExamples(CompliantRulesCodeExamples value) {
        this.compliantRulesCodeExamples = value;
    }

}
