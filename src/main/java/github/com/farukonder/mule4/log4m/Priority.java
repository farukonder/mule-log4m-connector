
package github.com.farukonder.mule4.log4m;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {

	TRACE("TRACE"), 
	DEBUG("DEBUG"), 
	INFO("INFO"), 
	WARN("WARN"), 
	ERROR("ERROR");

	private final String value;
	private final static Map<String, Priority> CONSTANTS = new HashMap<String, Priority>();

	static {
		for (Priority c : values()) {
			CONSTANTS.put(c.value, c);
		}
	}

	private Priority(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@JsonValue
	public String value() {
		return this.value;
	}

	@JsonCreator
	public static Priority fromValue(String value) {
		Priority constant = CONSTANTS.get(value);
		if (constant == null) {
			throw new IllegalArgumentException(value);
		} else {
			return constant;
		}
	}

}
