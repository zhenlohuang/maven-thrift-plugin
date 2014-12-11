package org.apache.maven.plugin.unit_test;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.ThriftGenerateJavaMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ThriftGenerateJavaMojoTest extends AbstractMojoTestCase {

    private final String GEN_JAVA_PLUGIN_CONFIG_PATH = "src/test/resources/generate-java-config/plugin-test-config.xml";
    private final String GEN_JAVA_WITHOUT_RECURSE_PLUGIN_CONFIG_PATH = "src/test/resources/generate-java-config/plugin-test-config-without-recurse.xml";
    private final File TEST_OUT_DIRECTORY = new File(getBasedir(), "target/generate-sources/thrift/");

    @Override
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        try {
            FileUtils.deleteDirectory(TEST_OUT_DIRECTORY);
        } catch (IOException e) {
            //ignore
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testThriftGenerateJavaCodes() throws Exception {
        File testPom = new File(getBasedir(), GEN_JAVA_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);
        mojo.execute();

        assertEquals(2, TEST_OUT_DIRECTORY.list().length);
        File tutorialDir = new File(TEST_OUT_DIRECTORY, "tutorial");
        assertEquals(5, tutorialDir.list().length);

        File sharedDir = new File(TEST_OUT_DIRECTORY, "shared");
        assertEquals(2, sharedDir.list().length);
    }

    @Test
    public void testThriftGenerateJavaCodesWithoutRecurse() throws Exception {
        File testPom = new File(getBasedir(), GEN_JAVA_WITHOUT_RECURSE_PLUGIN_CONFIG_PATH);
        ThriftGenerateJavaMojo mojo = (ThriftGenerateJavaMojo)lookupMojo("generate-java", testPom);
        mojo.execute();

        assertEquals(1, TEST_OUT_DIRECTORY.list().length);
        File tutorialDir = new File(TEST_OUT_DIRECTORY, "tutorial");
        assertEquals(5, tutorialDir.list().length);
    }
}
