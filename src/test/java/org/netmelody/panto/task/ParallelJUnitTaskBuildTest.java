package org.netmelody.panto.task;

import org.junit.Before;
import org.junit.Test;
import org.netmelody.panto.AntBuildTestCase;

public final class ParallelJUnitTaskBuildTest extends AntBuildTestCase {

    @Before
    public void setup() throws Exception {
        configureProject("ParallelJUnitTaskTestBuild.xml");
        
        this.folder.newFile("DummyJUnitTestCase.java");
    }

    @Test public void
    executesSimpleTarget() {
        executeTarget("simple");
    }
    
}
