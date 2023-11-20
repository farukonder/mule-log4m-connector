package github.com.farukonder.mule4.log4m;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class LoggingParameter {

	@Parameter
	@Optional(defaultValue = "INFO")
	@Summary("Logger priority")
	private Priority priority;

	@Parameter
	@Optional(defaultValue = "SUCCESS")
	@Summary("Logger step")
	private LogStep logStep;

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "#[correlationId]")
	Object correlationId;
	
	@Parameter
	@Optional()
	@Summary("execution point")
	private String executionPoint;

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "#[correlationId]")
	Object transactionId;

	public Object getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Object transactionId) {
		this.transactionId = transactionId;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getExecutionPoint() {
		return executionPoint;
	}

	public void setExecutionPoint(String executionPoint) {
		this.executionPoint = executionPoint;
	}

	public Object getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
		this.correlationId = correlationId;
	}

	public LogStep getLogStep() {
		return logStep;
	}

	public void setLogStep(LogStep logStep) {
		this.logStep = logStep;
	}

}
