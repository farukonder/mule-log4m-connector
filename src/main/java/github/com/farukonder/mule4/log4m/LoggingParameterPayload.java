package github.com.farukonder.mule4.log4m;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class LoggingParameterPayload extends LoggingParameter {
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = 
	"#[output application/java\n"
	+ "---\n"
	+ "attributes"
	+ "]")	
	private Object attributes;
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
//	@Optional(defaultValue = "#[payload]")
	@Optional(defaultValue = 
	"#[output application/java\n"
	+ "---\n"
	+ "payload"
	+ "]")
	Object payload;
	

	
	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	
	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
