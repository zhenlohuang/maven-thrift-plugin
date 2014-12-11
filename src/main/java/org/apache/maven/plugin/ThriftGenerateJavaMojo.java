package org.apache.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "generate-java")
public class ThriftGenerateJavaMojo extends AbstractThriftGenerateMojo {

    @Override
    protected String getGenerator() {
        return Thrift.Generator.JAVA;
    }


}
