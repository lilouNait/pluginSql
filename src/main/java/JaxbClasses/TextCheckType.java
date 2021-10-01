
package JaxbClasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for textCheckType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="textCheckType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Contains"/>
 *     &lt;enumeration value="Regexp"/>
 *     &lt;enumeration value="Strict"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "textCheckType")
@XmlEnum
public enum TextCheckType {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Contains")
    CONTAINS("Contains"),
    @XmlEnumValue("Regexp")
    REGEXP("Regexp"),
    @XmlEnumValue("Strict")
    STRICT("Strict");
    private final String value;

    TextCheckType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextCheckType fromValue(String v) {
        for (TextCheckType c: TextCheckType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
