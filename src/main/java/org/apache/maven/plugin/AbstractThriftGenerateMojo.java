package org.apache.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.exception.ThriftCompileException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractThriftGenerateMojo extends AbstractMojo {

    @Parameter(defaultValue = "thrift")
    protected String thriftExecutable;

    @Parameter(required = true)
    protected File thriftFile;

    @Parameter(defaultValue = "true")
    protected boolean recurse;

    @Parameter
    protected File includeDirectory;

    @Parameter
    protected List<File> includeDirectories;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        checkParameter();
        if (!getOutDirectory().exists()) {
            try {
                FileUtils.forceMkdir(getOutDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getLog().info( "Thrift Parameters:"  + "\n"
                + "thriftExecutable: " + thriftExecutable + "\n"
                + "thriftFile: " + thriftFile + "\n"
                + "recurse: " + recurse + "\n"
                + "includeDirectories: " + includeDirectories + "\n"
                + "generator: " + getGenerator() + "\n"
                + "outDirectory: " + getOutDirectory());
        Thrift thrift = new Thrift()
                .setThriftExecutable(thriftExecutable)
                .setThriftFile(thriftFile)
                .setRecurse(recurse)
                .setIncludeDirectories(includeDirectories)
                .setGenerator(getGenerator())
                .setOutDirectory(getOutDirectory());

        int result = 0;
        try {
            result = thrift.compile();
        } catch (Exception e) {
            new ThriftCompileException("Thrift compile failed.");
        }
        if (result != 0) {
            new ThriftCompileException("Thrift compile failed.");
        }
    }

    protected abstract String getGenerator();

    protected abstract File getOutDirectory();

    private void checkParameter() {

        checkNotNull(thriftExecutable, "thriftExecutable should not be null.");
        checkNotNull(thriftFile, "thriftFile should not be null.");
        checkArgument(thriftFile.exists(), thriftFile +" not found.");
        checkArgument(thriftFile.isFile(), thriftFile +" is not a file.");
        if (includeDirectory != null) {
            checkArgument(includeDirectory.exists(), includeDirectory + " not found.");
            checkArgument(includeDirectory.isDirectory(), includeDirectory + " is not a directory.");
        }
        if (includeDirectories != null) {
            for(File includeDir : includeDirectories) {
                checkArgument(includeDir.exists(), includeDir + "not found.");
                checkArgument(includeDir.isDirectory(), includeDir + " is not a directory.");
            }
        }
        checkNotNull(getOutDirectory(), "outDirectory should not be null.");
        if (getOutDirectory().exists()) {
            checkArgument(getOutDirectory().isDirectory(), getOutDirectory() + " is not a directory.");
        }
    }
}
