package org.apache.maven.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "generate-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ThriftGenerateJavaMojo extends AbstractThriftGenerateMojo {

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/thrift/gen-java")
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
