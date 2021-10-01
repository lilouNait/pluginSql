
package JaxbClasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ruleMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Group"/>
 *     &lt;enumeration value="Single"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ruleMode")
@XmlEnum
public enum RuleMode {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Group")
    GROUP("Group"),
    @XmlEnumValue("Single")
    SINGLE("Single");
    private final String value;

    RuleMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RuleMode fromValue(String v) {
        for (RuleMode c: RuleMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
