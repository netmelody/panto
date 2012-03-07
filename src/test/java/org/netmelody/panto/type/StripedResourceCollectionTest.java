package org.netmelody.panto.type;

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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.fail;

public final class StripedResourceCollectionTest {

    @Test public void
    isAResourceCollection() {
        assertThat(StripedResourceCollection.class, is(typeCompatibleWith(ResourceCollection.class)));
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
        collection.setStripeCount(2);

        try {
            collection.setStripeNum(3);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }
    
    @Test public void
    failsToProvideIteratorIfStripeCountSetLessThanStripeNumber() {
        final StripedResourceCollection collection = new StripedResourceCollection();
        collection.setStripeNum(3);
        
        try {
            collection.setStripeCount(2);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    isTransparentInDefaultConfiguration() throws Exception {
        final StripedResourceCollection collection = new StripedResourceCollection();
        
        collection.add(new StringResource("a"));
        collection.add(new StringResource("b"));
        
        assertThat(collection.size(), is(2));
        assertThat(collection.isFilesystemOnly(), is(false));
        assertThat(contentsOf(collection.iterator()),
                   contains((Resource)new StringResource("a"), (Resource)new StringResource("b")));
    }

    @Test public void
    stripesACollectionOfResourcesEqualInSizeToTheNumberOfStripes() throws Exception {
        final Iterable<Resource> stripe1 = contentsOf(stripeOf(1, 3, "a", "b", "c").iterator());
        final Iterable<Resource> stripe2 = contentsOf(stripeOf(2, 3, "a", "b", "c").iterator());
        final Iterable<Resource> stripe3 = contentsOf(stripeOf(3, 3, "a", "b", "c").iterator());
        
        assertThat(stripe1, contains((Resource)new StringResource("a")));
        assertThat(stripe2, contains((Resource)new StringResource("b")));
        assertThat(stripe3, contains((Resource)new StringResource("c")));
    }

    @Test public void
    stripesACollectionOfResourcesSmallerInSizeThanTheNumberOfStripes() throws Exception {
        final Iterable<Resource> stripe1 = contentsOf(stripeOf(1, 3, "a", "b").iterator());
        final Iterable<Resource> stripe2 = contentsOf(stripeOf(2, 3, "a", "b").iterator());
        final Iterable<Resource> stripe3 = contentsOf(stripeOf(3, 3, "a", "b").iterator());
        
        assertThat(stripe1, contains((Resource)new StringResource("a")));
        assertThat(stripe2, contains((Resource)new StringResource("b")));
        assertThat(stripe3, is(Matchers.<Resource>emptyIterable()));
    }

    @Test public void
    stripesACollectionOfResourcesLargerInSizeThanTheNumberOfStripes() throws Exception {
        final Iterable<Resource> stripe1 = contentsOf(stripeOf(1, 3, "a", "b", "c", "d").iterator());
        final Iterable<Resource> stripe2 = contentsOf(stripeOf(2, 3, "a", "b", "c", "d").iterator());
        final Iterable<Resource> stripe3 = contentsOf(stripeOf(3, 3, "a", "b", "c", "d").iterator());
        
        assertThat(stripe1, contains((Resource)new StringResource("a"), new StringResource("d")));
        assertThat(stripe2, contains((Resource)new StringResource("b")));
        assertThat(stripe3, contains((Resource)new StringResource("c")));
    }

    @Test public void
    stripesAnEmptyCollectionOfResources() throws Exception {
        final Iterable<Resource> stripe1 = contentsOf(stripeOf(1, 3).iterator());
        final Iterable<Resource> stripe2 = contentsOf(stripeOf(2, 3).iterator());
        final Iterable<Resource> stripe3 = contentsOf(stripeOf(3, 3).iterator());
        
        assertThat(stripe1, is(Matchers.<Resource>emptyIterable()));
        assertThat(stripe2, is(Matchers.<Resource>emptyIterable()));
        assertThat(stripe3, is(Matchers.<Resource>emptyIterable()));
    }

    @Test public void
    sortsResourcesBeforeStriping() throws Exception {
        final Iterable<Resource> stripe1 = contentsOf(stripeOf(1, 3, "a", "b", "c").iterator());
        final Iterable<Resource> stripe2 = contentsOf(stripeOf(2, 3, "c", "a", "b").iterator());
        final Iterable<Resource> stripe3 = contentsOf(stripeOf(3, 3, "b", "c", "a").iterator());
        
        assertThat(stripe1, contains((Resource)new StringResource("a")));
        assertThat(stripe2, contains((Resource)new StringResource("b")));
        assertThat(stripe3, contains((Resource)new StringResource("c")));
    }

    private static StripedResourceCollection stripeOf(int stripeNum, int stripeCount, String... contents) {
        final StripedResourceCollection collection = new StripedResourceCollection();
        for (String string : contents) {
            collection.add(new StringResource(string));
        }
        collection.setStripeCount(stripeCount);
        collection.setStripeNum(stripeNum);
        return collection;
    }

    private static Iterable<Resource> contentsOf(Iterator<Resource> iterator) {
        final List<Resource> result = new ArrayList<Resource>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}