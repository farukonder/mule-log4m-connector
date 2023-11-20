package github.com.farukonder.mule4.log4m;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;
import org.mule.extension.http.api.HttpRequestAttributes;
import org.mule.extension.http.api.HttpResponseAttributes;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.runtime.parameter.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class LoggingOperations { 

	private static final Logger logger = LoggerFactory.getLogger("github.com.farukonder.mule4.log4m");

	public void logPayload(@Config LoggingConfiguration configuration,
			@ParameterGroup(name = "Logging Configurations") LoggingParameterPayload loggingParameter,
			ComponentLocation location) {
		doLog(configuration, loggingParameter, location);
	}

	public void logVariable(@Config LoggingConfiguration configuration,
			@ParameterGroup(name = "Logging Configurations") LoggingParameterVariable loggingParameter,
			ComponentLocation location) {
		doLog(configuration, loggingParameter, location);
	}

	public void logError(@Config LoggingConfiguration configuration,
			@ParameterGroup(name = "Logging Configurations") LoggingParameterError loggingParameter,
			ComponentLocation location) {
		doLog(configuration, loggingParameter, location);
	}

	private void doLog(@Config LoggingConfiguration configuration,
			@ParameterGroup(name = "Logging Configurations") LoggingParameter loggingParameter,
			ComponentLocation location) {

		JSONObject jsonLog = new JSONObject();
		try {

			Map<String, String> conf = envMap(configuration);

			jsonLog.put("configInfo", conf);

			if (isLogEnabled(loggingParameter.getPriority().toString())) {

				PropertyUtils.describe(loggingParameter).forEach((k, v) -> {

					if (v != null) {
						
						String k_ = k ;// +"2";

						if (v instanceof java.util.Map) {
							
							Map<String, Object> m = ((java.util.Map)v);
							
							jsonLog.put(k_, m.entrySet().stream()
									.filter((e) -> e.getKey() != null  && e.getValue() != null)
									.collect(Collectors.toMap(e -> e.getKey(), e -> proccessValue(e.getKey(), e.getValue()))));
							
						} else if (v instanceof org.mule.extension.http.api.HttpRequestAttributes) {
							JSONObject jsonInnerLog = new JSONObject();
							HttpRequestAttributes httpRequestAttributes = ((org.mule.extension.http.api.HttpRequestAttributes) v);
							jsonInnerLog.put("listenerPath", httpRequestAttributes.getListenerPath());
							jsonInnerLog.put("localAddress", httpRequestAttributes.getLocalAddress());
							jsonInnerLog.put("method", httpRequestAttributes.getMethod());
							jsonInnerLog.put("queryString", httpRequestAttributes.getQueryString());
							jsonInnerLog.put("remoteAddress", httpRequestAttributes.getRemoteAddress());
							jsonInnerLog.put("requestPath", httpRequestAttributes.getRequestPath());
							jsonInnerLog.put("scheme", httpRequestAttributes.getScheme());
							jsonInnerLog.put("uriParams", httpRequestAttributes.getUriParams());
//							jsonInnerLog.put("requestUri", httpRequestAttributes.getRequestUri());
//							jsonInnerLog.put("queryParams", httpRequestAttributes.getQueryParams());
//							jsonInnerLog.put("rawRequestPath", httpRequestAttributes.getRawRequestPath());
//							jsonInnerLog.put("rawRequestUri", httpRequestAttributes.getRawRequestUri());
//							jsonInnerLog.put("maskedRequestPath", httpRequestAttributes.getMaskedRequestPath());

							MultiMap<String, String> headers = httpRequestAttributes.getHeaders();
							if (headers != null)
								jsonInnerLog.put("headers", headers.entrySet().stream().collect(
										Collectors.toMap(e -> e.getKey(), e -> maskString(e.getKey(), e.getValue()))));
							
							//jsonLog.put(k, jsonInnerLog);
							jsonLog.put(k_, jsonInnerLog.toString());
						} else if (v instanceof org.mule.extension.http.api.HttpResponseAttributes) {
							JSONObject jsonInnerLog = new JSONObject();
							HttpResponseAttributes httpResponseAttributes = ((org.mule.extension.http.api.HttpResponseAttributes) v);
							jsonInnerLog.put("reasonPhrase", httpResponseAttributes.getReasonPhrase());
							jsonInnerLog.put("statusCode", httpResponseAttributes.getStatusCode());

							MultiMap<String, String> headers = httpResponseAttributes.getHeaders();
							if (headers != null)
								jsonInnerLog.put("headers", headers.entrySet().stream().collect(
										Collectors.toMap(e -> e.getKey(), e -> maskString(e.getKey(), e.getValue()))));
							
							//jsonLog.put(k, jsonInnerLog);
							jsonLog.put(k_, jsonInnerLog.toString());
						} else if (v instanceof ParameterResolver) {
							v = ((ParameterResolver) v).resolve();
							jsonLog.put(k, Collections.singletonMap("ParameterResolver", String.valueOf(v)));

						} else if (v instanceof Priority) {
							// omit Priority
							// jsonLog.put(k, String.valueOf(v));
						} else if (v instanceof LogStep) {
							jsonLog.put(k, String.valueOf(v));
						} else {
							
							/* option 1
							JSONObject jsonV = new JSONObject();
							jsonV.put("v", String.valueOf(v));
							jsonV.put("t", v.getClass().getCanonicalName());
							// can also be utilized reflection! not for now :)
							jsonLog.put(k_, jsonV);
							*/
							
							// option 2
							jsonLog.put(k_, String.valueOf(v));
						}
					} else {
						jsonLog.put(k, String.valueOf("null"));
					}

				});
			}

			doLog(loggingParameter.getPriority().value(), covertMap(jsonLog));

		} catch (Exception e) {
			e.printStackTrace();
			doLog("ERROR", "exception in yet-another-logging-extention : \n" + e.getMessage());
			doLog(loggingParameter.getPriority().value(), covertMap(jsonLog));
		}

	}

	private static String maskString(String key, String value) {
		List<String> keysToMask = new ArrayList<>();
		keysToMask.add("client_secret");

		if (keysToMask.contains(key))
			return "*****";
		return value;
	}
	
	private static Object proccessValue(String key, Object value) {
		List<String> keysToMask = new ArrayList<>();
		keysToMask.add("client_secret");

		if (keysToMask.contains(key))
			return "*****";
		
		return value;
	}

	private Map<String, String> locationInfoToMap(ComponentLocation location) {
		Map<String, String> locationInfo = new HashMap<String, String>();
		locationInfo.put("location", location.getLocation());
		locationInfo.put("rootContainer", location.getRootContainerName());
		locationInfo.put("component", location.getComponentIdentifier().getIdentifier().toString());
		locationInfo.put("fileName", location.getFileName().orElse(""));
		locationInfo.put("lineInFile", String.valueOf(location.getLineInFile().orElse(null)));
		return locationInfo;
	}

	private Map<String, String> envMap(LoggingConfiguration configuration) {
		Map<String, String> mapEnv = new HashMap<String, String>();
		mapEnv.put("serverHostName", configuration.getServerHostName());
		mapEnv.put("serverHostIp", configuration.getServerIp());
		return mapEnv;
	}

	private Boolean isLogEnabled(String priority) {
		switch (priority) {
		case "TRACE":
			return logger.isTraceEnabled();
		case "DEBUG":
			return logger.isDebugEnabled();
		case "INFO":
			return logger.isInfoEnabled();
		case "WARN":
			return logger.isWarnEnabled();
		case "ERROR":
			return logger.isErrorEnabled();
		}
		return false;
	}

	private String removeNewLines(String text) {
		return text.replaceAll("\\r\\n|\\r|\\n", " ");
	}

	private String covertMap(Map<String, Map<String, String>> map) {
		JSONObject json = new JSONObject();
		json.put("log4m", map);
		return json.toString();
	}

	private String covertMap(JSONObject log) {
		JSONObject json = new JSONObject();
		json.put("log4m", log);
		return json.toString();
	}

	private void doLog(String priority, String logLine) {
		switch (priority) {
		case "TRACE":
			logger.trace(logLine);
			break;
		case "DEBUG":
			logger.debug(logLine);
			break;
		case "INFO":
			logger.info(logLine);
			break;
		case "WARN":
			logger.warn(logLine);
			break;
		case "ERROR":
			logger.error(logLine);
			break;
		}
	}

}
