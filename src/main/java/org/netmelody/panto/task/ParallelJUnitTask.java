package org.netmelody.panto.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Parallel;
import org.netmelody.panto.internal.RecordingJUnitTask;

public final class ParallelJUnitTask extends Task {
    private RecordingJUnitTask embeddedJUnitTask;
    private int stripeCount = -1;

    public void setStripeCount(int stripeCount) {
        if (stripeCount <= 0) {
            throw new BuildException("There must be at least one stripe");
        }
        this.stripeCount = stripeCount;
    }

    public RecordingJUnitTask createJUnit() {
        try {
            embeddedJUnitTask = new RecordingJUnitTask(getProject());
            return embeddedJUnitTask;
        }
        catch (Exception e) {
            throw new BuildException(e);
        }
    }
    
    @Override
    public void execute() throws BuildException {
        final Parallel parallelTask = (Parallel) getProject().createTask("parallel");
        parallelTask.init();
        parallelTask.setThreadsPerProcessor(1);
        
        if (stripeCount <= 0) {
            stripeCount = Runtime.getRuntime().availableProcessors();
        }
        for (int i = 0; i < stripeCount; i++) {
            parallelTask.addTask(embeddedJUnitTask.spawnTaskForStripe(i + 1, stripeCount));
        }
        
        parallelTask.perform();
    }
}
