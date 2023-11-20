package github.com.farukonder.mule4.log4m;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "log4m")
@Extension(name = "Log4M Log for Mule4")
@Configurations(LoggingConfiguration.class)
public class LoggingExtension {

}
