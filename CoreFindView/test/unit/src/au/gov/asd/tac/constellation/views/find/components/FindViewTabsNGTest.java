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
package au.gov.asd.tac.constellation.views.find.components;

import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import org.testfx.api.FxToolkit;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Atlas139mkm
 */
public class FindViewTabsNGTest {
    
    private BasicFindTab basicFindTab;
    private BasicFindTab spyBasicFindTab;
    private ReplaceTab replaceTab;
    private ReplaceTab spyReplaceTab;
    private FindViewPane findViewPane;
    private FindViewTabs findViewTabs;
    private FindViewTabs spyFindViewTabs;

    private static final Logger LOGGER = Logger.getLogger(FindViewTabsNGTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        if (!FxToolkit.isFXApplicationThreadRunning()) {
            FxToolkit.registerPrimaryStage();
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException ex) {
            LOGGER.log(Level.WARNING, "FxToolkit timedout trying to cleanup stages", ex);
        }
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        findViewPane = mock(FindViewPane.class);

        findViewTabs = new FindViewTabs(findViewPane);
        spyFindViewTabs = spy(findViewTabs);

        basicFindTab = mock(BasicFindTab.class);
        spyBasicFindTab = spy(basicFindTab);

        replaceTab = mock(ReplaceTab.class);
        spyReplaceTab = spy(replaceTab);

        when(spyFindViewTabs.getParentComponent()).thenReturn(findViewPane);
        when(findViewPane.getTabs()).thenReturn(spyFindViewTabs);
        when(spyFindViewTabs.getBasicFindTab()).thenReturn(spyBasicFindTab);
        when(spyFindViewTabs.getReplaceTab()).thenReturn(spyReplaceTab);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }

    /**
     * Test of getParentComponent method, of class FindViewTabs.
     */
    @Test
    public void testGetParentComponent() {
        System.out.println("getParentComponent");

        assertEquals(spyFindViewTabs.getParentComponent(), findViewPane);
    }

    /**
     * Test of getBasicFindTab method, of class FindViewTabs.
     */
    @Test
    public void testGetBasicFindTab() {
        System.out.println("getBasicFindTab");

        assertEquals(spyFindViewTabs.getBasicFindTab(), spyBasicFindTab);
    }

    /**
     * Test of getReplaceTab method, of class FindViewTabs.
     */
    @Test
    public void testGetReplaceTab() {
        System.out.println("getReplaceTab");

        assertEquals(spyFindViewTabs.getReplaceTab(), spyReplaceTab);
    }
}
