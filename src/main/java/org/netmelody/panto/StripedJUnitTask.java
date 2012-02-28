package org.netmelody.panto;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public final class StripedJUnitTask extends Task {

    private int stripeCount = 1;

    public void setStripeCount(int stripeCount) {
        if (stripeCount <= 0) {
            throw new BuildException("There must be at least one stripe");
        }
        this.stripeCount = stripeCount;
    }
}
