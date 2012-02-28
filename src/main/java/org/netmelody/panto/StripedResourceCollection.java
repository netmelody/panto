package org.netmelody.panto;

import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.Resources;

public final class StripedResourceCollection implements ResourceCollection {

    private final Resources resources = new Resources();
    private int stripeCount = -1;
    private int stripeNum = -1;
    
    public void add(ResourceCollection rc) {
        resources.add(rc);
    }

    public void setStripeCount(int stripeCount) {
        if (stripeCount <= 0) {
            throw new BuildException("There must be at least one stripe");
        }
        validate(stripeCount, stripeNum);
        this.stripeCount = stripeCount;
    }

    public void setStripeNum(int stripeNum) {
        if (stripeNum<= 0) {
            throw new BuildException("The stripeNum must be greater than zero");
        }
        validate(stripeCount, stripeNum);
        this.stripeNum = stripeNum;
    }

    private void validate(int proposedStripeCount, int proposedStripeNum) {
        if (proposedStripeCount != -1 && proposedStripeNum != -1 && proposedStripeNum > proposedStripeCount) {
            throw new BuildException("The stripeNum cannot be beyond the stripeCount");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Resource> iterator() {
        return resources.iterator();
    }

    @Override
    public int size() {
        return resources.size();
    }

    @Override
    public boolean isFilesystemOnly() {
        return resources.isFilesystemOnly();
    }
}