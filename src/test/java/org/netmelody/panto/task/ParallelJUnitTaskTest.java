package org.netmelody.panto.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.netmelody.panto.internal.RecordingJUnitTask;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.fail;

public final class ParallelJUnitTaskTest {

    @Test public void
    isATask() {
        assertThat(ParallelJUnitTask.class, is(typeCompatibleWith(Task.class)));
    }

    @Test public void
    failsIfStripeCountSetToZero() {
        final ParallelJUnitTask task = new ParallelJUnitTask();
        try {
            task.setStripeCount(0);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }

    @Test public void
    failsIfStripeCountSetToLessThanZero() {
        final ParallelJUnitTask task = new ParallelJUnitTask();
        try {
            task.setStripeCount(-1);
            fail("Expected BuildException");
        }
        catch (BuildException e) { /* pass */ }
    }
    
    @Test public void
    createsRecordingJUnitTask() {
        final ParallelJUnitTask task = new ParallelJUnitTask();
        assertThat(task.createJUnit(), is(Matchers.instanceOf(RecordingJUnitTask.class)));
    }
}