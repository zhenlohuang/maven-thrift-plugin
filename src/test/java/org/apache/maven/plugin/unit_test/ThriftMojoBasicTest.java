package org.apache.maven.plugin.unit_test;

import org.apache.maven.plugin.ThriftGenerateJavaMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ThriftMojoBasicTest extends AbstractMojoTestCase {

    private final String THRIFT_EXECUTABLE = "/usr/local/bin/thrift";
    private final String BASIC_TEST_PLUGIN_CONFIG_PATH = "src/test/resources/basic-test/plugin-test-config.xml";
    private final File THRIFT_FILE = new File(getBasedir(), "src/test/resources/thrift/tutorial.thrift");
    private final File THRIFT_INCLUDE_DIRECTORY = new File(getBasedir(), "src/test/resources/thrift/include/");
    private final File TEST_OUT_DIRECTORY = new File(getBasedir(), "target/test-generate-sources/thrift/");
    private final File NO_EXISTED_FILE = new File(getBasedir(), "no_existed_file.thrift");
    private final File NO_EXISTED_DIRECTORY = new File(getBasedir(), "no_existed_directory");
    private final String SHOULD_BE_EXCEPTION_BUT_NOT = "This test should throw exception but not.";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testMojoBasics() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);
        assertNotNull(mojo);

        assertEquals(THRIFT_EXECUTABLE, getVariableValueFromObject(mojo, "thriftExecutable"));
        assertEquals(THRIFT_FILE, getVariableValueFromObject(mojo, "thriftFile"));
        assertEquals(true, getVariableValueFromObject(mojo, "recurse"));
        assertEquals(THRIFT_INCLUDE_DIRECTORY, getVariableValueFromObject(mojo, "includeDirectory"));
        List<File> includeDirectories = new ArrayList<File>();
        includeDirectories.add(THRIFT_INCLUDE_DIRECTORY);
        assertEquals(includeDirectories, getVariableValueFromObject(mojo, "includeDirectories"));
        assertEquals(TEST_OUT_DIRECTORY, getVariableValueFromObject(mojo, "outDirectory"));
        assertEquals(true, getVariableValueFromObject(mojo, "debug"));
    }

    @Test
    public void testThriftExecutableParameterCheck() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);

        setVariableValueToObject(mojo, "thriftExecutable", null);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertEquals("thriftExecutable should not be null.", ex.getMessage());
        }
    }

    @Test
    public void testThriftFileParameterCheck() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);

        setVariableValueToObject(mojo, "thriftFile", null);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertEquals("thriftFile should not be null.", ex.getMessage());
        }

        setVariableValueToObject(mojo, "thriftFile", NO_EXISTED_FILE);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }

        setVariableValueToObject(mojo, "thriftFile", THRIFT_INCLUDE_DIRECTORY);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("is not a file"));
        }
    }

    @Test
    public void testIncludeDirectoryParameterCheck() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);

        setVariableValueToObject(mojo, "includeDirectory", NO_EXISTED_DIRECTORY);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }

        setVariableValueToObject(mojo, "includeDirectory", new File(THRIFT_INCLUDE_DIRECTORY, "shared.thrift"));
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("is not a directory"));
        }
    }

    @Test
    public void testIncludeDirectoriesParameterCheck() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);

        List<File> includeDirectores = new ArrayList<File>();
        includeDirectores.add(new File(THRIFT_INCLUDE_DIRECTORY, "shared.thrift"));
        setVariableValueToObject(mojo, "includeDirectories", includeDirectores);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("is not a directory"));
        }

        includeDirectores.clear();
        includeDirectores.add(NO_EXISTED_DIRECTORY);
        setVariableValueToObject(mojo, "includeDirectories", includeDirectores);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void testOutDirectoryParameterCheck() throws Exception {
        File testPom = new File(getBasedir(), BASIC_TEST_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);

        setVariableValueToObject(mojo, "outDirectory", null);
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (NullPointerException ex) {
            assertEquals("outDirectory should not be null.", ex.getMessage());
        }

        setVariableValueToObject(mojo, "outDirectory", new File(THRIFT_INCLUDE_DIRECTORY, "shared.thrift"));
        try {
            mojo.execute();
            fail(SHOULD_BE_EXCEPTION_BUT_NOT);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("is not a directory"));
        }
    }
}
