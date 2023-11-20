
package github.com.farukonder.mule4.log4m;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LogStep {

	SUCCESS("SUCCESS"), FAIL("FAIL");

	private final String value;
	private final static Map<String, LogStep> CONSTANTS = new HashMap<String, LogStep>();

	static {
		for (LogStep c : values()) {
			CONSTANTS.put(c.value, c);
		}
	}

	private LogStep(String value) {
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
	public static LogStep fromValue(String value) {
		LogStep constant = CONSTANTS.get(value);
		if (constant == null) {
			throw new IllegalArgumentException(value);
		} else {
			return constant;
		}
	}

}
