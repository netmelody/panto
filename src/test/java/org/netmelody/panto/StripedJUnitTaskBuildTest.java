package org.netmelody.panto;

import org.junit.Before;
import org.junit.Test;

public final class StripedJUnitTaskBuildTest extends AntBuildTestCase {

    @Before
    public void setup() {
        configureProject("StripedJUnitTaskTestBuild.xml");
    }

    @Test public void
    executesSimpleTarget() {
        executeTarget("simple");
    }
    
}
