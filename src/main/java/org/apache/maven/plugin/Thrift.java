package org.apache.maven.plugin;

import org.apache.commons.exec.*;

import java.io.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

final public class Thrift {

    private String thriftExecutable;
    private File thriftFile;
    private List<File> includeDirectories;
    private File outDirectory;
    private boolean noWarning = false;
    private boolean strictWarning = false;
    private boolean verbose = false;
    private boolean recurse = true;
    private boolean debug = false;
    private boolean allowNegKeys = false;
    private boolean allow64bitConsts = false;
    private String generator;

    public Thrift setThriftExecutable(String thriftExecutable) {
        this.thriftExecutable = thriftExecutable;
        return this;
    }

    public Thrift setThriftFile(File thriftFile) {
        this.thriftFile = thriftFile;
        return this;
    }

    public Thrift setIncludeDirectories(List<File> includeDirectories) {
        this.includeDirectories = includeDirectories;
        return this;
    }

    public Thrift setOutDirectory(File outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public Thrift setNoWarning(boolean noWarning) {
        this.noWarning = noWarning;
        return this;
    }

    public Thrift setStrictWarning(boolean strictWarning) {
        this.strictWarning = strictWarning;
        return this;
    }

    public Thrift setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    public Thrift setRecurse(boolean recurse) {
        this.recurse = recurse;
        return this;
    }

    public Thrift setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public Thrift setAllowNegKeys(boolean allowNegKeys) {
        this.allowNegKeys = allowNegKeys;
        return this;
    }

    public Thrift setAllow64bitConsts(boolean allow64bitConsts) {
        this.allow64bitConsts = allow64bitConsts;
        return this;
    }

    public Thrift setGenerator(String generator) {
        this.generator = generator;
        return this;
    }

    public int compile() throws Exception {
        checkParameters();
        CommandLine command = buildCommand();
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(stdout);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(streamHandler);
        int exitValue = executor.execute(command);
        System.out.println(stdout);

        return exitValue;
    }

    public class Generator {
        public static final String JAVA = "java";
    }

    private void checkParameters() {

        checkNotNull(thriftExecutable, "thriftExecutable should not be null.");
        checkNotNull(thriftFile, "thriftFile should not be null.");
        checkNotNull(generator, "generator should not be null.");
        checkNotNull(outDirectory, "outDirectory should not be null.");
    }

    private CommandLine buildCommand() throws IOException {

        CommandLine command = new CommandLine(thriftExecutable);
        command.addArgument("-out");
        command.addArgument(outDirectory.getAbsolutePath());
        if (includeDirectories != null && !includeDirectories.isEmpty()) {
            for(File include : includeDirectories) {
                command.addArgument("-I");
                command.addArgument(include.getAbsolutePath());
            }
        }
        if (noWarning) {
            command.addArgument("-nowarn");
        }
        if (strictWarning) {
            command.addArgument("-strict");
        }
        if (verbose) {
            command.addArgument("-v");
        }
        if (recurse) {
            command.addArgument("-r");
        }
        if (debug) {
            command.addArgument("-debug");
        }
        if (allowNegKeys) {
            command.addArgument("--allow-neg-keys");
        }
        if (allow64bitConsts) {
            command.addArgument("--allow-64bit-consts");
        }
        command.addArgument("--gen");
        command.addArgument(generator);
        command.addArgument(thriftFile.getAbsolutePath());

        return command;
    }
}
