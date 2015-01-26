package scripts.ldap;


/**
 * Classe permettant de réunir un doublon Attribut-Valeur, qui est une entrée
 * commune dans le protocole LDAP
 */
public class LDAPAttribute {
	private String attribute;
	private String value;
	
	
	public LDAPAttribute() {
		this(null, null);
	}
	
	public LDAPAttribute(String _attribute, String _value) {
		attribute = _attribute;
		value = _value;
	}
	
	
	public void setAttribute(String _attribute) {
		attribute = _attribute;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
	public void setValue(String _value) {
		value = _value;
	}
	
	public String getValue() {
		return value;
	}
}
