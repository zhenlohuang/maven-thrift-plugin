package org.apache.maven.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "generate-java-test", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES)
public class ThriftGenerateJavaTestMojo extends AbstractThriftGenerateMojo {

    @Parameter(defaultValue = "${project.build.directory}/generated-test-sources/thrift")
    protected File outDirectory;

    @Override
    protected String getGenerator() {
        return Thrift.Generator.JAVA;
    }

    @Override
    protected File getOutDirectory() {
        return outDirectory;
    }


}
