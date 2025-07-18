/*
 * Copyright 2010-2025 Australian Signals Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.gov.asd.tac.constellation.help;

import au.gov.asd.tac.constellation.help.preferences.HelpPreferenceKeys;
import au.gov.asd.tac.constellation.help.utilities.Generator;
import au.gov.asd.tac.constellation.help.utilities.HelpMapper;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import org.apache.commons.io.IOUtils;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author aldebaran30701
 */
public class ConstellationHelpDisplayerNGTest {
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Not currently required
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Not currently required
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        // Not currently required
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }
    
    /**
     * Test of display method, of class ConstellationHelpDisplayer.
     */
    @Test
    public void testDisplayOnline() {
        System.out.println("display mocking online");

        final String helpId = "helpID";
        final HelpCtx helpCtx = new HelpCtx(helpId);

        // mock some methods of the instance, call real display method
        final ConstellationHelpDisplayer instance = mock(ConstellationHelpDisplayer.class);
        when(instance.display(Mockito.any())).thenCallRealMethod();

        final String key = HelpPreferenceKeys.HELP_KEY;
        final boolean onlineReturnValue = true;
        final Preferences prefs = mock(Preferences.class);
        when(prefs.getBoolean(Mockito.eq(key), Mockito.anyBoolean())).thenReturn(onlineReturnValue);

        // Create static mock of NbPreferences to return the preferences mock
        try (final MockedStatic<NbPreferences> mockedStatic = Mockito.mockStatic(NbPreferences.class);
                final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic = Mockito.mockStatic(ConstellationHelpDisplayer.class);
                final MockedStatic<HelpMapper> mockedHelpMapperStatic = Mockito.mockStatic(HelpMapper.class);
                final MockedStatic<Generator> generatorStaticMock = Mockito.mockStatic(Generator.class);
                final MockedStatic<Desktop> desktopStaticMock = Mockito.mockStatic(Desktop.class)) {
            mockedStatic.when(() -> NbPreferences.forModule(Mockito.eq(HelpPreferenceKeys.class))).thenReturn(prefs);

            mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.browse(Mockito.any())).thenReturn(null);

            final String sep = File.separator;
            final String helpModulePath = "ext" + sep + "docs" + sep + "CoreHelp" + sep;
            mockedHelpMapperStatic.when(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId))).thenReturn(helpModulePath + "help-options.md");

            generatorStaticMock.when(() -> Generator.getBaseDirectory()).thenReturn("C://Users/anyperson");

            final Desktop mockDesktop = mock(Desktop.class);
            when(mockDesktop.isSupported(Mockito.eq(Desktop.Action.BROWSE))).thenReturn(true);

            desktopStaticMock.when(() -> Desktop.isDesktopSupported()).thenReturn(true);
            desktopStaticMock.when(() -> Desktop.getDesktop()).thenReturn(mockDesktop);

            assertTrue(instance.display(helpCtx));

            // verify mock interactions
            verify(prefs, times(1)).getBoolean(Mockito.eq(key), Mockito.anyBoolean());
            mockedHelpMapperStatic.verify(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId)), times(1));
            verify(mockDesktop, times(1)).isSupported(Mockito.eq(Desktop.Action.BROWSE));
            desktopStaticMock.verify(() -> Desktop.isDesktopSupported(), times(1));
            desktopStaticMock.verify(() -> Desktop.getDesktop(), times(1));
            mockedHelpDisplayerStatic.verify(() -> ConstellationHelpDisplayer.browse(Mockito.any()), times(1));

        }
    }

    /**
     * Test of display method, of class ConstellationHelpDisplayer.
     */
    @Test
    public void testDisplayOnlineUnsupportedDesktop() {
        System.out.println("display mocking onlineUnsupportedDesktop");
        
        final String helpId = "helpID";
        final HelpCtx helpCtx = new HelpCtx(helpId);

        // mock some methods of the instance, call real display method
        final ConstellationHelpDisplayer instance = mock(ConstellationHelpDisplayer.class);
        when(instance.display(Mockito.any())).thenCallRealMethod();

        final String key = HelpPreferenceKeys.HELP_KEY;
        final boolean onlineReturnValue = true;
        final Preferences prefs = mock(Preferences.class);
        when(prefs.getBoolean(Mockito.eq(key), Mockito.anyBoolean())).thenReturn(onlineReturnValue);

        // Create static mock of NbPreferences to return the preferences mock
        try (final MockedStatic<NbPreferences> mockedStatic = Mockito.mockStatic(NbPreferences.class);
                final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic = Mockito.mockStatic(ConstellationHelpDisplayer.class);
                final MockedStatic<HelpMapper> mockedHelpMapperStatic = Mockito.mockStatic(HelpMapper.class);
                final MockedStatic<Generator> generatorStaticMock = Mockito.mockStatic(Generator.class);
                final MockedStatic<Desktop> desktopStaticMock = Mockito.mockStatic(Desktop.class)) {
            mockedStatic.when(() -> NbPreferences.forModule(Mockito.eq(HelpPreferenceKeys.class))).thenReturn(prefs);

            mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.browse(Mockito.any())).thenReturn(null);

            final String sep = File.separator;
            final String helpModulePath = "ext" + sep + "docs" + sep + "CoreHelp" + sep;
            mockedHelpMapperStatic.when(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId))).thenReturn(helpModulePath + "help-options.md");

            generatorStaticMock.when(() -> Generator.getBaseDirectory()).thenReturn("C://Users/anyperson");

            final Desktop mockDesktop = mock(Desktop.class);
            when(mockDesktop.isSupported(Mockito.eq(Desktop.Action.BROWSE))).thenReturn(false);

            desktopStaticMock.when(() -> Desktop.isDesktopSupported()).thenReturn(false);
            desktopStaticMock.when(() -> Desktop.getDesktop()).thenReturn(mockDesktop);

            assertFalse(instance.display(helpCtx));

            // verify mock interactions
            verify(prefs, times(1)).getBoolean(Mockito.eq(key), Mockito.anyBoolean());
            mockedHelpMapperStatic.verify(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId)), times(1));
            verify(mockDesktop, times(0)).isSupported(Mockito.eq(Desktop.Action.BROWSE));
            desktopStaticMock.verify(() -> Desktop.isDesktopSupported(), times(1));
            desktopStaticMock.verify(() -> Desktop.getDesktop(), times(0)); // lazy check within if statement never hits this
            mockedHelpDisplayerStatic.verifyNoInteractions();
        }
    }

    /**
     * Test of display method, of class ConstellationHelpDisplayer.
     */
    @Test
    public void testDisplayOffline() {
        System.out.println("display mocking offline");

        final String helpId = "helpID";
        final HelpCtx helpCtx = new HelpCtx(helpId);

        // mock some methods of the instance, call real display method
        final ConstellationHelpDisplayer instance = mock(ConstellationHelpDisplayer.class);
        when(instance.display(Mockito.any())).thenCallRealMethod();

        final String key = HelpPreferenceKeys.HELP_KEY;
        final boolean onlineReturnValue = false;
        final Preferences prefs = mock(Preferences.class);
        when(prefs.getBoolean(Mockito.eq(key), Mockito.anyBoolean())).thenReturn(onlineReturnValue);

        // Create static mock of NbPreferences to return the preferences mock
        try (final MockedStatic<NbPreferences> mockedStatic = Mockito.mockStatic(NbPreferences.class);
                final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic = Mockito.mockStatic(ConstellationHelpDisplayer.class);
                final MockedStatic<HelpMapper> mockedHelpMapperStatic = Mockito.mockStatic(HelpMapper.class);
                final MockedStatic<Generator> generatorStaticMock = Mockito.mockStatic(Generator.class);
                final MockedStatic<Desktop> desktopStaticMock = Mockito.mockStatic(Desktop.class);
                final MockedStatic<HelpWebServer> webServerStaticMock = Mockito.mockStatic(HelpWebServer.class)) {

            mockedStatic.when(() -> NbPreferences.forModule(Mockito.eq(HelpPreferenceKeys.class))).thenReturn(prefs);

            mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.browse(Mockito.any())).thenReturn(null);

            final String sep = File.separator;
            final String helpModulePath = "ext" + sep + "docs" + sep + "CoreHelp" + sep;
            mockedHelpMapperStatic.when(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId))).thenReturn(helpModulePath + "help-options.md");

            for (final File file : File.listRoots()) {
                final String expectedFileLocation = file.getPath() + "Users/anyperson";
                generatorStaticMock.when(() -> Generator.getBaseDirectory()).thenReturn(expectedFileLocation);

                final Desktop mockDesktop = mock(Desktop.class);
                when(mockDesktop.isSupported(Mockito.eq(Desktop.Action.BROWSE))).thenReturn(true);

                desktopStaticMock.when(() -> Desktop.isDesktopSupported()).thenReturn(true);
                desktopStaticMock.when(() -> Desktop.getDesktop()).thenReturn(mockDesktop);

                final int expectedPort = 8888;
                webServerStaticMock.when(() -> HelpWebServer.start()).thenReturn(expectedPort);

                assertTrue(instance.display(helpCtx));

                // verify mock interactions
                verify(prefs, times(1)).getBoolean(Mockito.eq(key), Mockito.anyBoolean());
                mockedHelpMapperStatic.verify(() -> HelpMapper.getHelpAddress(Mockito.eq(helpId)), times(1));
                verify(mockDesktop, times(1)).isSupported(Mockito.eq(Desktop.Action.BROWSE));
                desktopStaticMock.verify(() -> Desktop.isDesktopSupported(), times(1));
                desktopStaticMock.verify(() -> Desktop.getDesktop(), times(1));
                final String expectedNavigationURL = String.format("http://localhost:%d/%s", expectedPort,
                        ("file:/" + expectedFileLocation + "/ext/docs/CoreHelp/help-options.md").replace("//", "/").replace("\\", "/"));
                mockedHelpDisplayerStatic.verify(() -> ConstellationHelpDisplayer.browse(Mockito.eq(new URI(expectedNavigationURL))), times(1));
            }
        }
    }

    /**
     * Test of copy method, of class ConstellationHelpDisplayer.
     * @throws java.io.IOException
     */
    @Test
    public void testCopy() throws IOException {
        System.out.println("copy");
        
        File tempFile = null;
        File tocFile = null;
        File outputFile = null;

        try {           
            // contents of file
            final String text = "This should be written into the file.\n";
            final String text2 = "this is the second line\n";
            final String text3 = "</> this will be the final line </>";
            final List<String> fileContents = new ArrayList<>();

            fileContents.add(text);
            fileContents.add(text2);
            fileContents.add(text3);

            tempFile = File.createTempFile("testfile", ".md");
            final String filePath = tempFile.getAbsolutePath();
            
            // try with resources
            try (final FileWriter fw = new FileWriter(tempFile)) {
                fileContents.forEach(str -> {
                    try {
                        fw.write(str);
                    } catch (final IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                });
            }
            
            outputFile = new File("tempFile1.txt");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            assertTrue(outputFile.length() == 0);
            
            tocFile = File.createTempFile("toc", ".md");
            
            final OutputStream out = new FileOutputStream(outputFile);
            final String returnHTML = text + text2 + text3;
            try (final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic = Mockito.mockStatic(ConstellationHelpDisplayer.class);
                    final MockedStatic<Generator> generatorStaticMock = Mockito.mockStatic(Generator.class, Mockito.CALLS_REAL_METHODS)) {
                mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.copy(Mockito.anyString(), Mockito.any())).thenCallRealMethod();
                mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.getInputStream(Mockito.anyString())).thenCallRealMethod();
                mockedHelpDisplayerStatic.when(() -> ConstellationHelpDisplayer.generateHTMLOutput(Mockito.any(), Mockito.any())).thenReturn(returnHTML);
                generatorStaticMock.when(Generator::getTOCDirectory).thenReturn(tocFile.toString());

                ConstellationHelpDisplayer.copy(filePath, out);
                out.flush();
                out.close();
                assertTrue(outputFile.length() != 0);
            }

            // assert that output file now has the correct contents
            final BufferedReader reader = new BufferedReader(new FileReader(outputFile));
            String line;

            int linecount = 0;
            while ((line = reader.readLine()) != null) {
                assertEquals(line, fileContents.get(linecount++).replace("\n", ""));
            }
        } finally {
            // Cleanup
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
            if (outputFile != null && outputFile.exists()) {
                outputFile.delete();
            }
            if (tocFile != null && tocFile.exists()) {
                tocFile.delete();
            }
        }

    }

    /**
     * Test of copy method, of class ConstellationHelpDisplayer.
     * @throws java.io.IOException
     */
    @Test
    public void testCopyReturnEarly() throws IOException {
        System.out.println("copy Return early");

        final OutputStream os = mock(OutputStream.class);
        doNothing().when(os).write(Mockito.any());

        final byte[] arr = new byte[1];
        FileInputStream fis = mock(FileInputStream.class);
        when(fis.readAllBytes()).thenReturn(arr);

        try (final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic2 = Mockito.mockStatic(ConstellationHelpDisplayer.class)) {
            mockedHelpDisplayerStatic2.when(() -> ConstellationHelpDisplayer.copy(Mockito.anyString(), Mockito.any())).thenCallRealMethod();
            mockedHelpDisplayerStatic2.when(() -> ConstellationHelpDisplayer.getInputStream(Mockito.anyString())).thenReturn(fis);
            mockedHelpDisplayerStatic2.when(() -> ConstellationHelpDisplayer.generateHTMLOutput(Mockito.any(), Mockito.any())).thenReturn("");

            ConstellationHelpDisplayer.copy("anypath.css", os);
            mockedHelpDisplayerStatic2.verify(() -> ConstellationHelpDisplayer.getInputStream(Mockito.anyString()), times(2));
            Mockito.verify(os, times(1)).write(Mockito.eq(arr));
            Mockito.verify(fis, times(1)).readAllBytes();
        }

        try (final MockedStatic<ConstellationHelpDisplayer> mockedHelpDisplayerStatic3 = Mockito.mockStatic(ConstellationHelpDisplayer.class)) {
            mockedHelpDisplayerStatic3.when(() -> ConstellationHelpDisplayer.copy(Mockito.anyString(), Mockito.any())).thenCallRealMethod();
            mockedHelpDisplayerStatic3.when(() -> ConstellationHelpDisplayer.getInputStream(Mockito.anyString())).thenReturn(fis);
            mockedHelpDisplayerStatic3.when(() -> ConstellationHelpDisplayer.generateHTMLOutput(Mockito.any(), Mockito.any())).thenReturn("");

            ConstellationHelpDisplayer.copy("anypath.txt", os);
            mockedHelpDisplayerStatic3.verify(() -> ConstellationHelpDisplayer.getInputStream(Mockito.anyString()), times(2));
            mockedHelpDisplayerStatic3.verify(() -> ConstellationHelpDisplayer.generateHTMLOutput(Mockito.eq(fis), Mockito.eq(fis)), times(1));
            Mockito.verify(os, times(1)).write(Mockito.eq(arr));
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetInputStreamException() throws FileNotFoundException {
        System.out.println("testGetInputStreamException");
        ConstellationHelpDisplayer.getInputStream(null);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void testGetInputStreamException2() throws FileNotFoundException {
        System.out.println("testGetInputStreamException2");
        ConstellationHelpDisplayer.getInputStream("-540-yh/ g\\[gf]; ()");
    }

    @Test
    public void testGetInputStream() throws IOException {
        System.out.println("testGetInputStream");

        File tempFile = null;

        try {
            tempFile = File.createTempFile("testfile", ".xml");
            final String path = tempFile.getPath();

            try (final FileInputStream expectedfis = new FileInputStream(path)) {
                final InputStream actualfis = ConstellationHelpDisplayer.getInputStream(tempFile.getPath());
                
                assertTrue(IOUtils.contentEquals(expectedfis, actualfis));
            }
        } finally {
            // Cleanup
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Test
    public void testGetFileURLString() throws MalformedURLException {
        System.out.println("testGetFileURLString");
        
        try (final MockedStatic<Generator> generatorStaticMock = Mockito.mockStatic(Generator.class)) {
            for (final File file : File.listRoots()) {
                final String sep = File.separator;
                generatorStaticMock.when(() -> Generator.getBaseDirectory()).thenReturn(file.getPath() + "users" + sep + "default" + sep);
                final String rel = "filename.ext";
                final String expected = "file:/" + file.getPath() + "users/default/filename.ext";
                
                assertEquals(ConstellationHelpDisplayer.getFileURLString(rel), expected.replace("\\", "/").replace("//", "/"));
            }          
        }
    }
}
