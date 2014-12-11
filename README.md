Welcome to Maven Thrift Plugin
=====================
Maven Thrift Plugin is used to compile the thrift file of you project.

**Notes**:Make sure Thrift has been installed. For Thrift installation guide please see: [Thrift installation](https://thrift.apache.org/docs/install/)

# Goals Overview
+ **generate-java** is bound to the generate-sources phase and is used to compile the thrift file.
+ **generate-java-test** is bound to the generate-test-sources phase and is used to compile the test thrift file.

# Execution Parameters
<table>
<tr><td>thriftExecutable</td><td>The path that points to thrift, default value is thrift in PATH.</td></tr>
<tr><td>thriftFile</td><td>Thrift file that will be compiled. This parameter is required.</td></tr>
<tr><td>recurse</td><td>Also generate included files.</td></tr>
<tr><td>includeDirectory</td><td>The directory searched for include directives</td></tr>
<tr><td>includeDirectories</td><td>Add a directory to the list of directories searched for include directives.</td></tr>
<tr><td>outDirectory</td><td>Set the ouput location for generated files, default value is ${project.build.directory}/generated-sources/thrift/gen-java or ${project.build.directory}/generated-test-sources/thrift/gen-java</td></tr>
</table>

# Example
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>my-project-groupId</groupId>
    <artifactId>my-project-artifactId</artifactId>
    <version>1.0-SNAPSHOT</version>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-thrift-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <thriftExecutable>/usr/local/bin/thrift</thriftExecutable>
                    <thriftFile>${basedir}/src/test/resources/thrift/tutorial.thrift</thriftFile>
                    <recurse>true</recurse>
                    <includeDirectories>
                        <include>${basedir}/src/test/resources/thrift/include</include>
                    </includeDirectories>
                    <outDirectory>${project.build.directory}/generate-sources/thrift/gen-java</outDirectory>
                </configuration>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>generate-java</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```