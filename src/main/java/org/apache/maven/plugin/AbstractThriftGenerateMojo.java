package org.apache.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractThriftGenerateMojo extends AbstractMojo {

//    @Parameter(property = "project", defaultValue = "${project}", required = true)
//    protected MavenProject project;
//
//    protected MavenProjectHelper projectHelper;

    @Parameter(property = "thriftExecutable", defaultValue = "thrift")
    protected String thriftExecutable;

    @Parameter(property = "thriftFile", required = true)
    protected File thriftFile;

    @Parameter(property = "recurse")
    protected boolean recurse;

    @Parameter(property = "includeDirectory")
    protected File includeDirectory;

    @Parameter(property = "includeDirectories")
    protected List<File> includeDirectories;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/thrift")
    protected File outDirectory;

    @Parameter(property = "debug", defaultValue = "false")
    protected boolean debug;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        checkParameter();
        if (!outDirectory.exists()) {
            try {
                FileUtils.forceMkdir(outDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Thrift thrift = new Thrift()
                .setThriftExecutable(thriftExecutable)
                .setThriftFile(thriftFile)
                .setRecurse(recurse)
                .setIncludeDirectories(includeDirectories)
                .setGenerator(getGenerator())
                .setOutDirectory(outDirectory);
        // TODO: refactoring
        try {
            int result = thrift.compile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract String getGenerator();

    private void checkParameter() {

//        checkNotNull(project, "project should not be null");
//        checkNotNull(projectHelper, "projectHelper should not be null");
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
        checkNotNull(outDirectory, "outDirectory should not be null.");
        if (outDirectory.exists()) {
            checkArgument(outDirectory.isDirectory(), outDirectory + " is not a directory.");
        }
    }
}
