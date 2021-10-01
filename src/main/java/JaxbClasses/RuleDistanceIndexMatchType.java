
package JaxbClasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleDistanceIndexMatchType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ruleDistanceIndexMatchType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="More"/>
 *     &lt;enumeration value="Less"/>
 *     &lt;enumeration value="Equals"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ruleDistanceIndexMatchType")
@XmlEnum
public enum RuleDistanceIndexMatchType {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("More")
    MORE("More"),
    @XmlEnumValue("Less")
    LESS("Less"),
    @XmlEnumValue("BeforeOrAfter")
    BEFOREORAFTER("BeforeOrAfter"),
    @XmlEnumValue("Equals")
    EQUALS("Equals");
    private final String value;

    RuleDistanceIndexMatchType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RuleDistanceIndexMatchType fromValue(String v) {
        for (RuleDistanceIndexMatchType c: RuleDistanceIndexMatchType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
