package org.netmelody.panto;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;

public final class StripedJUnitTask extends Task {

    private int stripeCount = 1;

    public void setStripeCount(int stripeCount) {
        if (stripeCount <= 0) {
            throw new BuildException("There must be at least one stripe");
        }
        this.stripeCount = stripeCount;
    }

    public JUnitTask createJUnit() {
        try {
            return new RecordingJUnitTask();
        }
        catch (Exception e) {
            throw new BuildException(e);
        }
    }
}
