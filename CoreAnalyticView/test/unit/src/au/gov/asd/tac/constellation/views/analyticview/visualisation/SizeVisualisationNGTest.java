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
package au.gov.asd.tac.constellation.views.analyticview.visualisation;

import au.gov.asd.tac.constellation.graph.schema.attribute.SchemaAttribute;
import au.gov.asd.tac.constellation.graph.schema.visual.concept.VisualConcept;
import au.gov.asd.tac.constellation.views.analyticview.AnalyticViewController;
import au.gov.asd.tac.constellation.views.analyticview.results.ScoreResult.ElementScore;
import au.gov.asd.tac.constellation.views.analyticview.translators.AbstractSizeTranslator;
import au.gov.asd.tac.constellation.views.analyticview.translators.ScoreToSizeTranslator;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.testfx.api.FxToolkit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for SizeVisualisation
 * 
 * @author Delphinus8821
 */
public class SizeVisualisationNGTest {

    private static final Logger LOGGER = Logger.getLogger(SizeVisualisationNGTest.class.getName());
    
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
        } catch (final TimeoutException ex) {
            LOGGER.log(Level.WARNING, "FxToolkit timedout trying to cleanup stages", ex);
        }
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
     * Test of deactivate method, of class SizeVisualisation.
     */
    @Test
    public void testDeactivate() {
        System.out.println("deactivate");
        
        try (final MockedStatic<AnalyticViewController> controllerStatic = Mockito.mockStatic(AnalyticViewController.class)) {
            final AnalyticViewController controller = spy(AnalyticViewController.class);
            controllerStatic.when(AnalyticViewController::getDefault).thenReturn(controller);
            final boolean reset = true;
            final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
            final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
            instance.deactivate(reset);
            
            final boolean isActive = instance.isActive();
            assertFalse(isActive);
            verify(controller).updateGraphVisualisations(Mockito.any(), Mockito.anyBoolean());
        }
    }

    /**
     * Test of getName method, of class SizeVisualisation.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
        final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
        final String expResult = "Size Elements";
        final String result = instance.getName();
        assertEquals(result, expResult);
    }

    /**
     * Test of getTranslator method, of class SizeVisualisation.
     */
    @Test
    public void testGetTranslator() {
        System.out.println("getTranslator");
        
        final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
        final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
        final ScoreToSizeTranslator result = (ScoreToSizeTranslator) instance.getTranslator();
        assertEquals(result, translator);
    }

    /**
     * Test of getAffectedAttributes method, of class SizeVisualisation.
     */
    @Test
    public void testGetAffectedAttributes() {
        System.out.println("getAffectedAttributes");
        
        final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
        final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
        final List<SchemaAttribute> expResult = Arrays.asList(VisualConcept.VertexAttribute.NODE_RADIUS, VisualConcept.TransactionAttribute.WIDTH);
        final List<SchemaAttribute> result = instance.getAffectedAttributes();
        assertEquals(result, expResult);
    }

    /**
     * Test of isActive method, of class SizeVisualisation.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        
        final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
        final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
        final boolean expResult = false;
        final boolean result = instance.isActive();
        assertEquals(result, expResult);
    }

    /**
     * Test of setSelected method, of class SizeVisualisation.
     */
    @Test
    public void testSetSelected() {
        System.out.println("setSelected");
        
        final boolean selected = false;
        final ScoreToSizeTranslator translator = new ScoreToSizeTranslator();
        final SizeVisualisation<ElementScore> instance = new SizeVisualisation<>(translator);
        instance.setSelected(selected);
        final boolean result = instance.isActive();
        assertEquals(result, selected);
    }    
}
