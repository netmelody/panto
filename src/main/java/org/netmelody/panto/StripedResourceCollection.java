package org.netmelody.panto;

import java.util.Iterator;

import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.Resources;

public final class StripedResourceCollection implements ResourceCollection {

    private Resources resources = new Resources();
    
    public void add(ResourceCollection rc) {
        resources.add(rc);
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
