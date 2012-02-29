package org.netmelody.panto;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Parallel;

public final class StripedJUnitTask extends Task {
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
        Parallel parallelTask = (Parallel) getProject().createTask("parallel");
        parallelTask.setThreadsPerProcessor(1);
        
        if (stripeCount <= 0) {
            stripeCount = Runtime.getRuntime().availableProcessors() * 2;
        }
        for (int i = 0; i < stripeCount; i++) {
            parallelTask.addTask(embeddedJUnitTask.spawnTaskForStripe(i + 1, stripeCount));
        }
        
        parallelTask.perform();
    }
}