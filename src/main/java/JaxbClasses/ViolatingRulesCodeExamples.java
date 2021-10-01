
package JaxbClasses;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
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
 *         &lt;element ref="{}ruleCodeExample" maxOccurs="unbounded" minOccurs="0"/>
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
        "ruleCodeExample"
})
@XmlRootElement(name = "violatingRulesCodeExamples")
@JacksonXmlRootElement
public class ViolatingRulesCodeExamples {
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<String> ruleCodeExample;

    public void setRuleCodeExample(List<String> ruleCodeExample) {
        this.ruleCodeExample = ruleCodeExample;
    }

    /**
     * Gets the value of the ruleCodeExample property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ruleCodeExample property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRuleCodeExample().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRuleCodeExample() {
        if (ruleCodeExample == null) {
            ruleCodeExample = new ArrayList<String>();
        }
        return this.ruleCodeExample;
    }

}
