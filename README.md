# Log4m connector for mule4

![splash](./content/log4m.png)

Mule has a build in log activity. It has some lack of futures though. With this log4m connector below are the futures on top of the default one.
 - built-in json logger
 - correlationId ( either use default correlation id or a custom one )
 - transactionId ( business transaction )
 - log type ( SUCCESS or FAIL )
 - execution point name
 - attributes of the execution point of the flow
 - payload: 
 - - flow payload,
 - - error payload, in case flow gets error
 - - http response payload along with error details without any configuration, in case its http error 


with combining this connector with elasticsearch, its really easy to classify any flow logs based on above fields easily.

![elastic-fields](./content/e1.png)

elasticsearch classification

![elastic-classification](./content/e2.png)

elasticsearch flow trace with details

![elastic-fields](./content/e3.png)

## using in anypoint

after adding the connector, below is the usage details.

anypoint studio palette

![palette](./content/palette.png)

aynpoint action details
![action](./content/log-connector-conf.png)


## compile

```sh
mvn clean package -DskipTests
```

## define in pom.xml

Add this dependency to your application pom.xml

```xml
<dependency>
	<groupId>github.com.farukonder.mule4.connectors</groupId>
	<artifactId>mule-log4m-connector</artifactId>
	<version>0.0.1</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## publish

put below xml snipped in your pom.xml file. and run mvn deploy in order to put this jar to artifact repository

```xml
	<distributionManagement>
		<repository>
			<id>cci-repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/repo-mule/maven/v1</url>
		</repository>
		<snapshotRepository>
			<id>cci-repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/repo-mule/maven/v1</url>
		</snapshotRepository>
	</distributionManagement>
```

**know issues**
 - There's no XML files that has a <module> root element, thus is impossible to auto generate a [mule-artifact.json] descriptor file. The file must start with [module-] and end with [.xml], such as [module-foo.xml] 
 - - set JAVA_HOME to your env. in my case export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_301.jdk/Contents/Home
