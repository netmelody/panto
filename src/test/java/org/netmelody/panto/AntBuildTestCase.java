/*
 * Created on 01.07.2004
 */
package org.netmelody.panto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.tools.ant.BuildFileTest;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class AntBuildTestCase {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private static final class BuildFileTestHelper extends BuildFileTest {
        @Override
        public void tearDown() throws Exception {
            super.tearDown();
        }
    }
    
    private final BuildFileTestHelper antTestcase = new BuildFileTestHelper();

    @After
    public final void tearDownAntTestcase() throws Exception {
        antTestcase.tearDown();
    }
    
    protected final void configureProject(final String antBuildFilename) {
        final File antBuildFile = copyResource(antBuildFilename, folder.getRoot());
        try {
            antTestcase.configureProject(antBuildFile.getAbsolutePath());
        }
        catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private final File copyResource(final String resourceName, File dest) {
        final File file = new File(dest, resourceName);
        final InputStream in = AntBuildTestCase.class.getResourceAsStream("/" + resourceName);
        try {
            final FileOutputStream out = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int len = in.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
            out.close();
            in.close();
        }
        catch (final Exception e) {
            throw new IllegalStateException(e);
        }
        return file;
    }

    protected final  void executeTarget(final String targetName) {
        antTestcase.executeTarget(targetName);
    }

    protected final  String getOutput() {
        return antTestcase.getOutput();
    }
}