package org.netmelody.panto;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.fail;

public final class StripedJUnitTaskTest {

    @Test public void
    isATask() {
        assertThat(StripedJUnitTask.class, is(typeCompatibleWith(Task.class)));
    }

    @Test public void
    failsIfStripeCountSetToZero() {
        final StripedJUnitTask task = new StripedJUnitTask();
        try {
            task.setStripeCount(0);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    failsIfStripeCountSetToLessThanZero() {
        final StripedJUnitTask task = new StripedJUnitTask();
        try {
            task.setStripeCount(-1);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }
}