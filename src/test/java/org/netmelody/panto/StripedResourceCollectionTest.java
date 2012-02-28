package org.netmelody.panto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.StringResource;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.fail;

public final class StripedResourceCollectionTest {

    @Test public void
    isAResourceCollection() {
        assertThat(StripedResourceCollection.class, is(typeCompatibleWith(ResourceCollection.class)));
    }
    
    @Test public void
    isTransparentInDefaultConfiguration() throws Exception {
        final StripedResourceCollection collection = new StripedResourceCollection();
        
        collection.add(new StringResource("sausage"));
        collection.add(new StringResource("turnip"));
        
        assertThat(collection.size(), is(2));
        assertThat(collection.isFilesystemOnly(), is(false));
        assertThat(contentsOf(collection.iterator()),
                   Matchers.<Resource>contains(new StringResource("sausage"),
                                               new StringResource("turnip")));
    }

    @Test public void
    failsIfStripeCountSetToZero() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        try {
            collection.setStripeCount(0);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    failsIfStripeCountSetToLessThanZero() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        try {
            collection.setStripeCount(-1);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    failsIfStripeNumSetToZero() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        try {
            collection.setStripeNum(0);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    failsIfStripeNumSetToLessThanZero() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        try {
            collection.setStripeNum(-1);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }
    
    @Test public void
    failsToProvideIteratorIfStripeNumSetGreaterThanStripeCount() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        collection.setStripeNum(3);

        try {
            collection.setStripeCount(2);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    private static Iterable<Resource> contentsOf(Iterator<Resource> iterator) {
        final List<Resource> result = new ArrayList<Resource>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}