package org.netmelody.panto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public Iterator<Resource> iterator() {
        @SuppressWarnings("unchecked")
        final Iterator<Resource> iterator = resources.iterator();
        final int stripeIndex = Math.max(stripeNum - 1, 0);
        final int stripeCnt = Math.max(stripeCount, 1);
        
        int i = stripeCnt;
        final List<Resource> result = new ArrayList<Resource>();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if ((i % stripeCnt) == stripeIndex) {
                result.add(resource);
            }
            i = i + 1;
        }
        
        return result.iterator();
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