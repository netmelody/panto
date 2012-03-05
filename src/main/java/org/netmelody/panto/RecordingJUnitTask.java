package org.netmelody.panto;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.FormatterElement;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.ForkMode;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.SummaryAttribute;
import org.apache.tools.ant.types.resources.Resources;

public final class RecordingJUnitTask {

    private final Resources batches = new Resources();
    private final Project project;

    private final List<FormatterElement> formatters = new ArrayList<FormatterElement>();

    private boolean fork;
    private ForkMode forkMode;
    private boolean cloneVm;
    private SummaryAttribute printSummary;
    
    public RecordingJUnitTask(Project project) {
        this.project = project;
    }

    public Resources createBatchTest() {
        final Resources batchTest = new Resources();
        batches.add(batchTest);
        return batchTest;
    }

    public void setFork(boolean value) {
        this.fork = value;
    }

    public void setForkMode(ForkMode mode) {
        this.forkMode = mode;
    }

    public void setCloneVm(boolean cloneVm) {
        this.cloneVm = cloneVm;
    }

    public void setPrintsummary(SummaryAttribute printSummary) {
        this.printSummary = printSummary;
    }

    public void addFormatter(FormatterElement fe) {
        formatters.add(fe);
    }

    public JUnitTask spawnTaskForStripe(int stripeNumber, int stripeCount) {
        final JUnitTask junit = (JUnitTask) project.createTask("junit");
        junit.createBatchTest().add(new StripedResourceCollection(stripeNumber, stripeCount, batches));

        for (FormatterElement formatter : formatters) {
            junit.addFormatter(formatter);
        }

        junit.setFork(fork);
        junit.setForkMode(forkMode);
        junit.setCloneVm(cloneVm);
        junit.setPrintsummary(printSummary);

        return junit;
    }
}
