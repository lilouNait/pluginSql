
package JaxbClasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleResultType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ruleResultType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="FailIfFound"/>
 *     &lt;enumeration value="FailIfNotFound"/>
 *     &lt;enumeration value="FailIfLessFound"/>
 *     &lt;enumeration value="FailIfMoreFound"/>
 *     &lt;enumeration value="SkipIfFound"/>
 *     &lt;enumeration value="SkipIfNotFound"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ruleResultType")
@XmlEnum
public enum RuleResultType {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("FailIfFound")
    FAIL_IF_FOUND("FailIfFound"),
    @XmlEnumValue("FailIfNotFound")
    FAIL_IF_NOT_FOUND("FailIfNotFound"),
    @XmlEnumValue("FailIfLessFound")
    FAIL_IF_LESS_FOUND("FailIfLessFound"),
    @XmlEnumValue("FailIfMoreFound")
    FAIL_IF_MORE_FOUND("FailIfMoreFound"),
    @XmlEnumValue("SkipIfFound")
    SKIP_IF_FOUND("SkipIfFound"),
    @XmlEnumValue("SkipIfNotFound")
    SKIP_IF_NOT_FOUND("SkipIfNotFound");
    private final String value;

    RuleResultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RuleResultType fromValue(String v) {
        for (RuleResultType c: RuleResultType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
