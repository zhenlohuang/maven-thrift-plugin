package org.apache.maven.plugin.unit_test;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.Thrift;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThriftTest extends TestCase {

    private final File THRIFT_FILE = new File("src/test/resources/thrift/tutorial.thrift");
    private final File THRIFT_INCLUDE_DIRECTORY = new File("src/test/resources/thrift/include/");
    private final File TEST_OUT_DIRECTORY = new File("target/generate-sources/thrift/");
    private final String SHOULD_BE_EXCEPTION_BUT_NOT = "This test should throw exception but not.";

    @Before
    public void setUp() {
        try {
            FileUtils.deleteDirectory(TEST_OUT_DIRECTORY);
        } catch (IOException e) {
            //ignore
        }
        TEST_OUT_DIRECTORY.mkdirs();
    }

    @Test
    public void testThriftRequiredParameterChecking() throws Exception {
        Thrift thrift = new Thrift();
        try {
            thrift.compile();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains("thriftExecutable should not be null."));
        }
        thrift.setThriftExecutable("thrift");

        try {
            thrift.compile();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains("thriftFile should not be null."));
        }
        thrift.setThriftFile(THRIFT_FILE);

        try {
            thrift.compile();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains("generator should not be null."));
        }
        thrift.setGenerator(Thrift.Generator.JAVA);

        try {
            thrift.compile();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains("outDirectory should not be null."));
        }
    }

    @Test
    public void testThriftCompile() throws Exception {
        List<File> includeDirectories = new ArrayList<File>();
        includeDirectories.add(THRIFT_INCLUDE_DIRECTORY);

        Thrift thrift = new Thrift()
                .setThriftExecutable("thrift")
                .setThriftFile(THRIFT_FILE)
                .setIncludeDirectories(includeDirectories)
                .setGenerator(Thrift.Generator.JAVA)
                .setOutDirectory(TEST_OUT_DIRECTORY);
        int exitValue = thrift.compile();
        assertEquals(0, exitValue);
        assertEquals(2, TEST_OUT_DIRECTORY.list().length);
        File tutorialDir = new File(TEST_OUT_DIRECTORY, "tutorial");
        assertEquals(5, tutorialDir.list().length);
        File sharedDir = new File(TEST_OUT_DIRECTORY, "shared");
        assertEquals(2, sharedDir.list().length);
    }

    @Test
    public void testThriftCompileWithoutRecurse() throws Exception {
        List<File> includeDirectories = new ArrayList<File>();
        includeDirectories.add(THRIFT_INCLUDE_DIRECTORY);

        Thrift thrift = new Thrift()
                .setThriftExecutable("thrift")
                .setThriftFile(THRIFT_FILE)
                .setGenerator(Thrift.Generator.JAVA)
                .setOutDirectory(TEST_OUT_DIRECTORY)
                .setIncludeDirectories(includeDirectories)
                .setRecurse(false);
        int exitValue = thrift.compile();
        assertEquals(0, exitValue);
        assertEquals(1, TEST_OUT_DIRECTORY.list().length);
        File tutorialDir = new File(TEST_OUT_DIRECTORY, "tutorial");
        assertEquals(5, tutorialDir.list().length);
    }
}
