package org.netmelody.panto;

import org.junit.Before;
import org.junit.Test;

public final class StripedJUnitTaskBuildTest extends AntBuildTestCase {

    @Before
    public void setup() throws Exception {
        configureProject("StripedJUnitTaskTestBuild.xml");
        
        this.folder.newFile("DummyJUnitTestCase.java");
    }

    @Test public void
    executesSimpleTarget() {
        executeTarget("simple");
    }
    
}
