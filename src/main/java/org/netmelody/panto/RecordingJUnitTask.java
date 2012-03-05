package org.netmelody.panto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.FormatterElement;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.ForkMode;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.SummaryAttribute;
import org.apache.tools.ant.types.Commandline.Argument;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.resources.Resources;

public final class RecordingJUnitTask {

    private final Resources batches = new Resources();
    private final Project project;

    private final List<Path> classpaths = new ArrayList<Path>();
    private final List<FormatterElement> formatters = new ArrayList<FormatterElement>();
    private final List<PropertySet> syspropertysets = new ArrayList<PropertySet>();
    private final List<Environment.Variable> sysproperties = new ArrayList<Environment.Variable>();
    private final List<RecordingJvmArg> jvmArgs = new ArrayList<RecordingJvmArg>();

    private boolean fork;
    private ForkMode forkMode;
    private boolean cloneVm;
    private SummaryAttribute printSummary;
    private boolean haltOnFail;
    private String failureProperty;
    private File tempDir;

    public RecordingJUnitTask(Project project) {
        this.project = project;
    }

    public Path createClasspath() {
        final Path path = new Path(project);
        classpaths.add(path);
        return path;
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

    public void setHaltonfailure(boolean value) {
        this.haltOnFail = value;
    }

    public void setFailureProperty(String propertyName) {
        this.failureProperty = propertyName;
    }

    public RecordingJvmArg createJvmarg() {
        final RecordingJvmArg result = new RecordingJvmArg();
        jvmArgs.add(result);
        return result;
    }

    public void setTempdir(File tmpDir) {
        this.tempDir = tmpDir;
    }

    public void setPrintsummary(SummaryAttribute printSummary) {
        this.printSummary = printSummary;
    }

    public void addFormatter(FormatterElement fe) {
        this.formatters.add(fe);
    }

    public void addSyspropertyset(PropertySet sysPropSet) {
        this.syspropertysets.add(sysPropSet);
    }

    public void addConfiguredSysproperty(Environment.Variable sysp) {
        this.sysproperties.add(sysp);
    }

    public JUnitTask spawnTaskForStripe(int stripeNumber, int stripeCount) {
        final JUnitTask junit = (JUnitTask) project.createTask("junit");
        junit.createBatchTest().add(new StripedResourceCollection(stripeNumber, stripeCount, batches));

        for (Path classpath : classpaths) {
            junit.createClasspath().add(classpath);
        }

        for (FormatterElement formatter : formatters) {
            junit.addFormatter(formatter);
        }

        for (RecordingJvmArg jvmArg : jvmArgs) {
            jvmArg.applyTo(junit.createJvmarg());
        }

        for (PropertySet sysPropSet : syspropertysets) {
            junit.addSyspropertyset(sysPropSet);
        }

        junit.setFork(fork);
        junit.setForkMode(forkMode);
        junit.setCloneVm(cloneVm);
        junit.setPrintsummary(printSummary);
        junit.setHaltonfailure(haltOnFail);
        junit.setFailureProperty(failureProperty);
        junit.setTempdir(tempDir);

        for (Environment.Variable sysProp : sysproperties) {
            junit.addConfiguredSysproperty(sysProp);
        }

        return junit;
    }

    public static final class RecordingJvmArg {

        private String value;

        public void setValue(String value) {
            this.value = value;
        }

        public void applyTo(Argument realJvmArg) {
            if (value != null) {
                realJvmArg.setValue(value);
            }
        }
    }
}