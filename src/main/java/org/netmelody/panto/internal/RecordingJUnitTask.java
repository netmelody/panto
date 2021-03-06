package org.netmelody.panto.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.BatchTest;
import org.apache.tools.ant.taskdefs.optional.junit.FormatterElement;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.ForkMode;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask.SummaryAttribute;
import org.apache.tools.ant.types.Commandline.Argument;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.Union;
import org.netmelody.panto.type.StripedResourceCollection;

public final class RecordingJUnitTask {

    private final RecordingBatchTest batches = new RecordingBatchTest();
    private final Project project;

    private final List<Path> classpaths = new ArrayList<Path>();
    private final List<FormatterElement> formatters = new ArrayList<FormatterElement>();
    private final List<PropertySet> syspropertysets = new ArrayList<PropertySet>();
    private final List<Environment.Variable> sysproperties = new ArrayList<Environment.Variable>();
    private final List<RecordingJvmArg> jvmArgs = new ArrayList<RecordingJvmArg>();

    private boolean fork;
    private ForkMode forkMode = new ForkMode("once");
    private boolean cloneVm;
    private SummaryAttribute printSummary;
    private boolean haltOnFail;
    private boolean haltOnError;
    private String failureProperty;
    private File tempDir;
    private boolean showOutput;
    private boolean outputToFormatters = true;

    public RecordingJUnitTask(Project project) {
        this.project = project;
    }

    public Path createClasspath() {
        final Path path = new Path(project);
        classpaths.add(path);
        return path;
    }

    public RecordingBatchTest createBatchTest() {
        return batches;
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

    public void setHaltonerror(boolean value) {
        this.haltOnError = value;
    }

    public void setHaltonfailure(boolean value) {
        this.haltOnFail = value;
    }

    public void setFailureProperty(String propertyName) {
        this.failureProperty = propertyName;
    }

    public void setShowOutput(boolean showOutput) {
        this.showOutput = showOutput;
    }

    public void setOutputToFormatters(boolean outputToFormatters) {
        this.outputToFormatters = outputToFormatters;
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
        junit.init();

        final BatchTest junitBatchTest = junit.createBatchTest();
        junitBatchTest.add(new StripedResourceCollection(stripeNumber, stripeCount, batches));
        junitBatchTest.setTodir(batches.getTodir());
        junitBatchTest.setFork(batches.isFork());

        final Path junitCasspath = junit.createClasspath();
        for (Path classpath : classpaths) {
            junitCasspath.add(classpath);
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
        
        if (printSummary != null) {
            junit.setPrintsummary(printSummary);
        }
        junit.setHaltonerror(haltOnError);
        junit.setHaltonfailure(haltOnFail);
        junit.setFailureProperty(failureProperty);
        junit.setShowOutput(showOutput);
        junit.setOutputToFormatters(outputToFormatters);
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
    
    public static final class RecordingBatchTest implements ResourceCollection {
        private Union resourceUnion = new Union();
        private File toDir;
        private boolean fork;

        public void setTodir(File toDir) {
            this.toDir = toDir;
        }

        public File getTodir() {
            return toDir;
        }

        public void setFork(boolean value) {
            this.fork = value;
        }

        public boolean isFork() {
            return fork;
        }

        public void add(ResourceCollection rc) {
            resourceUnion.add(rc);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public Iterator<Resource> iterator() {
            return resourceUnion.iterator();
        }

        @Override
        public int size() {
            return resourceUnion.size();
        }

        @Override
        public boolean isFilesystemOnly() {
            return resourceUnion.isFilesystemOnly();
        }
    }
}