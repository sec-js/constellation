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
package au.gov.asd.tac.constellation.graph.interaction.plugins.io.screenshot;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.graph.file.open.RecentFiles;
import au.gov.asd.tac.constellation.graph.file.open.RecentFiles.HistoryItem;
import au.gov.asd.tac.constellation.graph.interaction.gui.VisualGraphTopComponent;
import au.gov.asd.tac.constellation.graph.manager.GraphManager;
import au.gov.asd.tac.constellation.graph.node.GraphNode;
import au.gov.asd.tac.constellation.preferences.ApplicationPreferenceKeys;
import au.gov.asd.tac.constellation.utilities.visual.VisualManager;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.stubbing.Answer;
import org.openide.util.NbPreferences;
import org.openide.windows.TopComponent;
import org.openide.windows.TopComponent.Registry;
import org.openide.windows.WindowManager;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author sol695510
 */
public class RecentGraphScreenshotUtilitiesNGTest {

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
     * Test of getScreenshotsDir method, of class RecentGraphScreenshotUtilities.
     * 
     * @throws java.util.prefs.BackingStoreException
     */
    @Test
    public void testGetScreenshotsDir() throws BackingStoreException {
        System.out.println("getScreenshotsDir");
        
        final Preferences p = Preferences.userNodeForPackage(RecentGraphScreenshotUtilitiesNGTest.class);
        File screenshotDir = null;
        
        try (final MockedStatic<NbPreferences> nbPreferencesMockedStatic = mockStatic(NbPreferences.class, Mockito.CALLS_REAL_METHODS)) {
            nbPreferencesMockedStatic.when(() -> NbPreferences.forModule(ApplicationPreferenceKeys.class)).thenReturn(p);
            
            p.put(ApplicationPreferenceKeys.USER_DIR, "userDir");
            
            screenshotDir = RecentGraphScreenshotUtilities.getScreenshotsDir();
            
            assertTrue(screenshotDir.exists());
            assertEquals(screenshotDir.getPath(), "userDir" + File.separator + "Screenshots");
        } finally {
            p.removeNode();
            if (screenshotDir != null) {
                screenshotDir.delete();
            }
        }
    }

    /**
     * Test of takeScreenshot method, of class RecentGraphScreenshotUtilities. No Graph Node
     */
    @Test
    public void testTakeScreenshotNoGraphNode() {
        System.out.println("takeScreenshotNoGraphNode");
        
        // Mocks
        final GraphManager gm = mock(GraphManager.class);
        final Graph mockGraph = mock(Graph.class);
        
        when(gm.getActiveGraph()).thenReturn(mockGraph);
        
        try (final MockedStatic<GraphManager> mockedGraphManager = Mockito.mockStatic(GraphManager.class); 
                final MockedStatic<GraphNode> mockedGraphNode = Mockito.mockStatic(GraphNode.class);
                final MockedStatic<RecentGraphScreenshotUtilities> mockedRecentGraphScreenshotUtilities = Mockito.mockStatic(RecentGraphScreenshotUtilities.class, Mockito.CALLS_REAL_METHODS)) {
            mockedGraphManager.when(GraphManager::getDefault).thenReturn(gm);
            
            mockedGraphNode.when(() -> GraphNode.getGraphNode(mockGraph)).thenReturn(null);

            RecentGraphScreenshotUtilities.takeScreenshot("");
            
            // without a graph node, no screenshot should be saved
            mockedRecentGraphScreenshotUtilities.verify(() -> RecentGraphScreenshotUtilities.resizeAndSave(any(BufferedImage.class), any(Path.class), anyInt(), anyInt()), never());
        }
    }
    
    /**
     * Test of takeScreenshot method, of class RecentGraphScreenshotUtilities.
     */
    @Test
    public void testTakeScreenshotNoVisualManager() {
        System.out.println("takeScreenshotNoVisualManager");
        
        // Mocks
        final GraphManager gm = mock(GraphManager.class);
        final Graph mockGraph = mock(Graph.class);
        final GraphNode mockGraphNode = mock(GraphNode.class);
        
        when(gm.getActiveGraph()).thenReturn(mockGraph);
        when(mockGraphNode.getVisualManager()).thenReturn(null);
        
        try (final MockedStatic<GraphManager> mockedGraphManager = Mockito.mockStatic(GraphManager.class); 
                final MockedStatic<GraphNode> mockedGraphNode = Mockito.mockStatic(GraphNode.class);
                final MockedStatic<RecentGraphScreenshotUtilities> mockedRecentGraphScreenshotUtilities = Mockito.mockStatic(RecentGraphScreenshotUtilities.class, Mockito.CALLS_REAL_METHODS)) {
            mockedGraphManager.when(GraphManager::getDefault).thenReturn(gm);
            
            mockedGraphNode.when(() -> GraphNode.getGraphNode(mockGraph)).thenReturn(mockGraphNode);

            RecentGraphScreenshotUtilities.takeScreenshot("");
            
            // without a visual manager, no screenshot should be saved
            mockedRecentGraphScreenshotUtilities.verify(() -> RecentGraphScreenshotUtilities.resizeAndSave(any(BufferedImage.class), any(Path.class), anyInt(), anyInt()), never());
        }
    }
    
    /**
     * Test of takeScreenshot method, of class RecentGraphScreenshotUtilities.
     */
    @Test
    public void testTakeScreenshot() {
        System.out.println("takeScreenshot");

        // Mocks
        final GraphManager gm = mock(GraphManager.class);
        final Graph mockGraph = mock(Graph.class);
        final GraphNode mockGraphNode = mock(GraphNode.class);
        final VisualManager vm = mock(VisualManager.class);

        when(gm.getActiveGraph()).thenReturn(mockGraph);
        when(mockGraphNode.getVisualManager()).thenReturn(vm);
        when(mockGraph.getId()).thenReturn("");
        doAnswer(invocation -> {
            // getting the semaphore argument so that it is released properly
            final Semaphore sem = invocation.getArgument(1);
            sem.release();
            
            return null;
        }).when(vm).exportToBufferedImage(any(BufferedImage[].class), any(Semaphore.class));
        
        try (final MockedStatic<GraphManager> mockedGraphManager = Mockito.mockStatic(GraphManager.class); 
                final MockedStatic<GraphNode> mockedGraphNode = Mockito.mockStatic(GraphNode.class);
                final MockedStatic<RecentGraphScreenshotUtilities> mockedRecentGraphScreenshotUtilities = Mockito.mockStatic(RecentGraphScreenshotUtilities.class)) {
            mockedGraphManager.when(GraphManager::getDefault).thenReturn(gm);
            
            mockedGraphNode.when(() -> GraphNode.getGraphNode(mockGraph)).thenReturn(mockGraphNode);
            
            mockedRecentGraphScreenshotUtilities.when(() -> RecentGraphScreenshotUtilities.takeScreenshot(anyString())).thenCallRealMethod();
            mockedRecentGraphScreenshotUtilities.when(() -> RecentGraphScreenshotUtilities.takeScreenshot(anyString(), any(Graph.class))).thenCallRealMethod();
            mockedRecentGraphScreenshotUtilities.when(() -> RecentGraphScreenshotUtilities.requestGraphActive(any(Graph.class), any(Semaphore.class))).thenCallRealMethod();
            
            RecentGraphScreenshotUtilities.takeScreenshot("");
            
            // with everything in place, the screenshot should be taken and saved
            mockedRecentGraphScreenshotUtilities.verify(() -> RecentGraphScreenshotUtilities.resizeAndSave(any(BufferedImage.class), any(Path.class), anyInt(), anyInt()));
        }
    }

    /**
     * Test of refreshScreenshotDir method, of class RecentGraphScreenshotUtilities, where getScreenShotsDir returns
     * null value.
     */
    @Test
    public void testRefreshScreenshotsDirNull() {
        System.out.println("refreshScreenshotDirNull");

        try (final MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class);
                final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class);
                final MockedStatic<RecentFiles> recentFilesMock = Mockito.mockStatic(RecentFiles.class)) {
            // getScreenshotsDir() will return null therefore there will be no files in filesInDirectory to iterate through.
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(null);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.refreshScreenshotsDir()).thenCallRealMethod();

            // Return a HistoryItem from getUniqueRecentFiles() to add to filesInHistory.
            recentFilesMock.when(() -> RecentFiles.getUniqueRecentFiles()).thenReturn(new ArrayList<>(Arrays.asList(new HistoryItem(1, "file1"))));
            filesMock.when(() -> Files.delete(Mockito.any())).thenAnswer((Answer<Void>) invocation -> null);

            RecentGraphScreenshotUtilities.refreshScreenshotsDir();

            recentGraphScreenshotUtilitiesMock.verify(() -> RecentGraphScreenshotUtilities.getScreenshotsDir(), times(1));
            recentFilesMock.verify(() -> RecentFiles.getUniqueRecentFiles(), times(1));

            // Files.delete() will never be called since filesInDirectory is empty.
            filesMock.verifyNoInteractions();
        }
    }

    /**
     * Test of refreshScreenshotDir method, of class RecentGraphScreenshotUtilities, where getScreenShotsDir returns non
     * null value.
     */
    @Test
    public void testRefreshScreenshotsDirNotNull() {
        System.out.println("refreshScreenshotDirNotNull");
        
        try (final MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class);
                final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class);
                final MockedStatic<RecentFiles> recentFilesMock = Mockito.mockStatic(RecentFiles.class)) {
            final File file1 = mock(File.class);
            when(file1.getName()).thenReturn("file1.star.png");
            when(file1.toPath()).thenReturn(Paths.get("path\'file1.star.png"));

            final File file2 = mock(File.class);
            when(file2.getName()).thenReturn("file2.star.png");
            when(file2.toPath()).thenReturn(Paths.get("path\'file2.star.png"));

            final File screenShotsDir = mock(File.class);
            when(screenShotsDir.listFiles()).thenReturn(new File[]{file1, file2});

            // getScreenshotsDir() will return a file structure with files therefore there will be files in filesInDirectory to iterate through.
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(screenShotsDir);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.refreshScreenshotsDir()).thenCallRealMethod();
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.findScreenshot(anyString(), anyString())).thenReturn(Optional.of(file1));

            // Return a HistoryItem from getUniqueRecentFiles() to add to filesInHistory.
            recentFilesMock.when(() -> RecentFiles.getUniqueRecentFiles()).thenReturn(new ArrayList<>(Arrays.asList(new HistoryItem(1, "file1.star"))));
            filesMock.when(() -> Files.delete(Mockito.any())).thenAnswer((Answer<Void>) invocation -> null);

            RecentGraphScreenshotUtilities.refreshScreenshotsDir();

            recentGraphScreenshotUtilitiesMock.verify(() -> RecentGraphScreenshotUtilities.getScreenshotsDir(), times(1));
            recentFilesMock.verify(() -> RecentFiles.getUniqueRecentFiles(), times(1));

            // Files.delete() will be called only on file2 since it is not in filesInHistory.
            filesMock.verify(() -> Files.delete(Mockito.any()), times(1));
            filesMock.verify(() -> Files.delete(Mockito.eq(Paths.get("path\'file2.star.png"))), times(1));
        }
    }

    /**
     * Test of refreshScreenshotHashed method, of class RecentGraphScreenshotUtilities, where a path should be hashed
     * path should be found in the directory
     */
    @Test
    public void testRefreshScreenshotsHashed() {
        System.out.println("refreshScreenshotsHashed");
        
        try (final MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class);
                final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class);
                final MockedStatic<RecentFiles> recentFilesMock = Mockito.mockStatic(RecentFiles.class)) {
            final File file1 = mock(File.class);
            when(file1.getName()).thenReturn("1901de09374733aff5b72e9400d18482.png");
            when(file1.toPath()).thenReturn(Paths.get("1901de09374733aff5b72e9400d18482.png"));

            final File screenShotsDir = mock(File.class);
            when(screenShotsDir.listFiles()).thenReturn(new File[]{file1});

            // getScreenshotsDir() will return a file structure with files therefore there will be files in filesInDirectory to iterate through.
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(screenShotsDir);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.refreshScreenshotsDir()).thenCallRealMethod();
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.findScreenshot(anyString(), anyString())).thenReturn(Optional.of(file1));

            // Return a HistoryItem from getUniqueRecentFiles() to add to filesInHistory.
            recentFilesMock.when(() -> RecentFiles.getUniqueRecentFiles()).thenReturn(new ArrayList<>(Arrays.asList(new HistoryItem(1, "/path/to/helloworld"))));
            filesMock.when(() -> Files.delete(Mockito.any())).thenAnswer((Answer<Void>) invocation -> null);

            RecentGraphScreenshotUtilities.refreshScreenshotsDir();

            recentGraphScreenshotUtilitiesMock.verify(() -> RecentGraphScreenshotUtilities.getScreenshotsDir(), times(1));
            recentFilesMock.verify(() -> RecentFiles.getUniqueRecentFiles(), times(1));

            // Files.delete() will be called only on file2 since it is not in filesInHistory.
            filesMock.verify(() -> Files.delete(Mockito.any()), times(1));
        }
    }

    /**
     * Test of refreshScreenshotHashed method, of class RecentGraphScreenshotUtilities, where a path should be hashed
     * path should be found in the directory
     */
    @Test
    public void testRefreshScreenshotsLegacy() {
        System.out.println("refreshScreenshotsLegacy");

        try (final MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class);
                final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class);
                final MockedStatic<RecentFiles> recentFilesMock = Mockito.mockStatic(RecentFiles.class)) {
            final File file1 = mock(File.class);
            when(file1.getName()).thenReturn("test1.star.png");
            when(file1.toPath()).thenReturn(Paths.get("path\\to\\userdir\\test1.star.png"));

            final File screenShotsDir = mock(File.class);
            when(screenShotsDir.listFiles()).thenReturn(new File[]{file1});

            // getScreenshotsDir() will return a file structure with files therefore there will be files in filesInDirectory to iterate through.
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(screenShotsDir);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.refreshScreenshotsDir()).thenCallRealMethod();

            // Return a HistoryItem from getUniqueRecentFiles() to add to filesInHistory.
            recentFilesMock.when(() -> RecentFiles.getUniqueRecentFiles()).thenReturn(new ArrayList<>(Arrays.asList(new HistoryItem(1, "path\\to\\userdir\\test1.star"))));
            filesMock.when(() -> Files.delete(Mockito.any())).thenAnswer((Answer<Void>) invocation -> null);

            RecentGraphScreenshotUtilities.refreshScreenshotsDir();

            recentGraphScreenshotUtilitiesMock.verify(() -> RecentGraphScreenshotUtilities.getScreenshotsDir(), times(1));
            recentFilesMock.verify(() -> RecentFiles.getUniqueRecentFiles(), times(1));

            // Files.delete() will be called only on file2 since it is not in filesInHistory.
            filesMock.verifyNoInteractions();
        }
    }

    /**
     * Test of hashFilePath method, of RecentGraphScreenshotUtilities.
     * 
     */
    @Test
    public void testHashFilePath() {
        System.out.println("hashFilePath");
        
        try (final MockedStatic<DatatypeConverter> dataTypeConverter = Mockito.mockStatic(DatatypeConverter.class)) {
            dataTypeConverter.when(() -> DatatypeConverter.printHexBinary(Mockito.any())).thenReturn("0c695c8bff7af91d321c237bdf969addbfb859be8095d880f1d034737fbc35d2");
            final String actual = RecentGraphScreenshotUtilities.hashFilePath("/test/path");
            final String expected = "0C695C8BFF7AF91D321C237BDF969ADDBFB859BE8095D880F1D034737FBC35D2";

            assertEquals(actual, expected);           
        }
    }

    /**
     * Test of findScreenshot method, of RecentGraphScreenshotUtilities. Has a valid legacy file
     * 
     * @throws IOException 
     */
    @Test
    public void testFindScreenshotWithValidLegacyFile() throws IOException {
        System.out.println("findScreenshotWithValidLegacyFile");
        
        final File testFile = File.createTempFile("file", ".png");

        final File screenShotsDir = mock(File.class);
        when(screenShotsDir.toString()).thenReturn(testFile.getParent());

        try (final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class)) {
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.hashFilePath(anyString())).thenReturn("hash123");
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(screenShotsDir);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.findScreenshot(anyString(), anyString())).thenCallRealMethod();

            final Optional<File> actual = RecentGraphScreenshotUtilities.findScreenshot(testFile.getParent(), testFile.getName().replace(".png", ""));
            final Optional<File> expected = Optional.of(new File(testFile.getParent() + File.separator + testFile.getName()));

            testFile.delete();
            assertEquals(actual, expected);
        }
    }

    /**
     * Test of findScreenshot method, of RecentGraphScreenshotUtilities. No screenshot found
     */
    @Test
    public void testFindScreenshotNotFound() {
        System.out.println("findScreenshotNotFound");
        
        final File screenShotsDir = mock(File.class);
        when(screenShotsDir.toString()).thenReturn("/screenshots");

        try (final MockedStatic<RecentGraphScreenshotUtilities> recentGraphScreenshotUtilitiesMock  = Mockito.mockStatic(RecentGraphScreenshotUtilities.class)) {
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.hashFilePath(anyString())).thenReturn("hash123");
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.getScreenshotsDir()).thenReturn(screenShotsDir);
            recentGraphScreenshotUtilitiesMock.when(() -> RecentGraphScreenshotUtilities.findScreenshot(anyString(), anyString())).thenCallRealMethod();

            final Optional<File> actual = RecentGraphScreenshotUtilities.findScreenshot("/not/found", "file");
            final Optional<File> expected = Optional.empty();

            assertEquals(actual, expected);          
        }
    }

    /**
     * Test of requestGraphActive method, of RecentGraphScreenshotUtilities.
     */
    @Test
    public void testRequestGraphActive() {
        System.out.println("requestGraphActive");
        
        // Set up mocks
        final Graph mockGraph = mock(Graph.class);
        when(mockGraph.getId()).thenReturn("");

        final WindowManager wm = mock(WindowManager.class);
        final Registry reg = mock(Registry.class);
        when(wm.getRegistry()).thenReturn(reg);

        final GraphNode gn = mock(GraphNode.class);
        when(gn.getGraph()).thenReturn(mockGraph);

        final Set<TopComponent> setTopC = new HashSet<>();
        final VisualGraphTopComponent tc = mock(VisualGraphTopComponent.class);
        when(tc.getGraphNode()).thenReturn(gn);
        setTopC.add(tc);
        when(reg.getOpened()).thenReturn(setTopC);
        
        // mock top component to count down on the given latch
        doAnswer(invocation -> {
            Object latch = invocation.getArgument(0);
            if (latch instanceof CountDownLatch countDownLatch) {
                countDownLatch.countDown();
            }
            return null;
        }).when(tc).requestActiveWithLatch(any(CountDownLatch.class));

        // Assert mocks work
        assertEquals(wm.getRegistry(), reg);
        assertEquals(reg.getOpened(), setTopC);

        final Semaphore semaphore = new Semaphore(1);

        // test correct functionality
        testRequestGraphActiveHelper(mockGraph, wm, reg, setTopC, semaphore);
        // Verify functions were run
        verify(tc, times(1)).getGraphNode();
        verify(gn, times(1)).getGraph();
        verify(mockGraph, times(2)).getId();

        try (final MockedStatic<EventQueue> mockedEventQueue = Mockito.mockStatic(EventQueue.class, Mockito.CALLS_REAL_METHODS)) {
            // test InvocationTargetException
            mockedEventQueue.when(() -> EventQueue.invokeAndWait(any())).thenThrow(new InvocationTargetException(new Throwable()));

            assertThrows(() -> EventQueue.invokeAndWait(() -> tc.requestActive()));

            testRequestGraphActiveHelper(mockGraph, wm, reg, setTopC, semaphore);

            // Verify functions were run (includes previous test)
            verify(tc, times(2)).getGraphNode();
            verify(gn, times(2)).getGraph();
            verify(mockGraph, times(4)).getId();
        }
    }

    private void testRequestGraphActiveHelper(final Graph mockGraph, final WindowManager mockWindowManager, final Registry mockRegistry, final Set<TopComponent> topComponents, final Semaphore semaphore) {
        try (final MockedStatic<WindowManager> mockedWindowManager = Mockito.mockStatic(WindowManager.class)) {
            mockedWindowManager.when(WindowManager::getDefault).thenReturn(mockWindowManager);
            // Assert mocks work
            assertEquals(WindowManager.getDefault(), mockWindowManager);

            // When top component is NOT null
            when(mockRegistry.getOpened()).thenReturn(topComponents);

            RecentGraphScreenshotUtilities.requestGraphActive(null, semaphore);
            RecentGraphScreenshotUtilities.requestGraphActive(mockGraph, semaphore);

            // When top component is null
            when(mockRegistry.getOpened()).thenReturn(null);

            RecentGraphScreenshotUtilities.requestGraphActive(null, semaphore);
            RecentGraphScreenshotUtilities.requestGraphActive(mockGraph, semaphore);
        }
    }
    
    /**
     * Test of resizeAndSave method, of RecentGraphScreenshotUtilities.
     * 
     * @throws java.io.IOException
     */
    @Test
    public void testResizeAndSave() throws IOException {
        System.out.println("resizeAndSave");
        
        final File resizedImageFile = new File("testResize.png");
        
        try {
            resizedImageFile.createNewFile();
            final BufferedImage originalImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            originalImage.setRGB(1, 1, 100);

            assertEquals(originalImage.getHeight(), 500);
            assertEquals(originalImage.getWidth(), 500);
            
            RecentGraphScreenshotUtilities.resizeAndSave(originalImage, resizedImageFile.toPath(), 250, 300);

            assertTrue(resizedImageFile.exists());
            
            final BufferedImage resizedImage = ImageIO.read(resizedImageFile);
            assertEquals(resizedImage.getHeight(), 250);
            assertEquals(resizedImage.getWidth(), 300);
        } finally {
            resizedImageFile.delete();           
        }      
    }
}
