package github.com.farukonder.mule4.log4m;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class LoggingParameterError extends LoggingParameter {
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = 
	"#[output application/java\n"
	  + "---\n"
	  + "error.description]")
	private Object error;
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = 
	"#[output application/java\n"
	  + "---\n"
	  + "error.errorMessage.payload]")
	private Object errorPayload;
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "#[error.errorType.asString]")
	private Object errorType;
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = 
	"#[output application/java\n"
	  + "---\n"
	  + "error.muleMessage.typedAttributes]")
	private Object errorAttributes;
	
	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public Object getErrorPayload() {
		return errorPayload;
	}

	public void setErrorPayload(Object errorPayload) {
		this.errorPayload = errorPayload;
	}

	public Object getErrorType() {
		return errorType;
	}

	public void setErrorType(Object errorType) {
		this.errorType = errorType;
	}

	public Object getErrorAttributes() {
		return errorAttributes;
	}

	public void setErrorAttributes(Object errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

}
