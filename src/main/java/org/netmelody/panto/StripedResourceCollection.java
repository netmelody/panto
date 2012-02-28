package org.netmelody.panto;

import java.util.Iterator;

import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;

public final class StripedResourceCollection implements ResourceCollection {

    @Override
    public Iterator<Resource> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isFilesystemOnly() {
        return false;
    }
}
