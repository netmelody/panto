package org.netmelody.panto;

import org.apache.tools.ant.types.ResourceCollection;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;

public final class StripedResourceCollectionTest {

    @Test public void
    implementsResourceCollection() {
        assertThat(StripedResourceCollection.class, is(typeCompatibleWith(ResourceCollection.class)));
    }

}