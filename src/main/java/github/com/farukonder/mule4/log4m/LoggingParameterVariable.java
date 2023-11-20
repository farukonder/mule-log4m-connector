package github.com.farukonder.mule4.log4m;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class LoggingParameterVariable extends LoggingParameter {
	
	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = 
			"#[output application/java\n"
			+ "---\n"
			+ "payload"
			+ "]")
	Object variable;

	public Object getVariable() {
		return variable;
	}

	public void setVariable(Object variable) {
		this.variable = variable;
	}
	


}
