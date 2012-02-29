package org.netmelody.panto;

import org.junit.Before;
import org.junit.Test;

public final class StripedResourceCollectionBuildTest extends AntBuildTestCase {

    @Before
    public void setup() {
        configureProject("StripedResourceTestBuild.xml");
    }

    @Test public void
    executesSimpleTarget() {
        executeTarget("simple");
    }
    
}
