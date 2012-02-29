package org.netmelody.panto;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.types.resources.Resources;

public final class RecordingJUnitTask {

    private final Resources batches = new Resources();
    private final Project project;
    
    public RecordingJUnitTask(Project project) {
        this.project = project;
    }

    public Resources createBatchTest() {
        final Resources batchTest = new Resources();
        batches.add(batchTest);
        return batchTest;
    }
    
    public JUnitTask spawnTaskForStripe(int stripeNumber, int stripeCount) {
        final JUnitTask junit = (JUnitTask) project.createTask("junit");
        junit.createBatchTest().add(new StripedResourceCollection(stripeNumber, stripeCount, batches));
        return junit;
    }
}
