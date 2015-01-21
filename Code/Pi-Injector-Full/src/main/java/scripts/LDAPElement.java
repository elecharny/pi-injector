package scripts;


public class LDAPElement {
	private String attribute;
	private String value;
	
	
	public LDAPElement() {
		this(null, null);
	}
	
	public LDAPElement(String _attribute, String _value) {
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
