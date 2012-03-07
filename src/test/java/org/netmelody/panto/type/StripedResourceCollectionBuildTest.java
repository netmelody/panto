package org.netmelody.panto.type;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.netmelody.panto.AntBuildTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class StripedResourceCollectionBuildTest extends AntBuildTestCase {

    @Before
    public void setup() {
        configureProject("StripedResourceTestBuild.xml");
    }

    @Test public void
    executesSimpleTarget() throws Exception {
        final File file1 = folder.newFile("test1.txt");
        folder.newFile("test2.txt");
        final File file3 = folder.newFile("test3.txt");
        
        executeTarget("simple");
        assertThat(getLog(), is(file1.getAbsolutePath() + "," + file3.getAbsolutePath()));
    }
}