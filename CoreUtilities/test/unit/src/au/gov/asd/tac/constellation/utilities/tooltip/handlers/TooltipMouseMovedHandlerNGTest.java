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
package au.gov.asd.tac.constellation.utilities.tooltip.handlers;

import au.gov.asd.tac.constellation.utilities.tooltip.TooltipNode;
import au.gov.asd.tac.constellation.utilities.tooltip.TooltipPane;
import au.gov.asd.tac.constellation.utilities.tooltip.TooltipProvider;
import au.gov.asd.tac.constellation.utilities.tooltip.TooltipUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.HitInfo;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.stubbing.Answer;
import org.testfx.api.FxToolkit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author aldebaran30701
 */
public class TooltipMouseMovedHandlerNGTest {

    private static final Logger LOGGER = Logger.getLogger(TooltipMouseMovedHandlerNGTest.class.getName());
    
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
     * Test of handle method, of class TooltipMouseMovedHandler.
     */
    @Test
    public void testHandle() {
        System.out.println("testHandle");
        final TextArea textInputControl = spy(new TextArea());
        final TooltipPane tooltipPane = spy(new TooltipPane());
        //when(textInputControl.getSkin()).thenReturn(null);

        try (final MockedStatic<TooltipUtilities> ttuStatic = mockStatic(TooltipUtilities.class, CALLS_REAL_METHODS)) {
            ttuStatic.when(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class))).thenAnswer((Answer<Void>) invocation -> null);
            when(tooltipPane.isEnabled()).thenReturn(false);
            final TooltipMouseMovedHandler instance = new TooltipMouseMovedHandler(textInputControl, tooltipPane);
            final MouseEvent event = mock(MouseEvent.class);

            instance.handle(event);

            verify(tooltipPane, times(1)).isEnabled();
            verifyNoInteractions(textInputControl);

            // Verify that mouseEntered did not trigger calls to the below method.
            ttuStatic.verify(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class)), times(0));
        }
    }

    /**
     * Test of handle method, of class TooltipMouseMovedHandler.
     */
    @Test
    public void testHandle2() {
        System.out.println("testHandle2");
        final TextArea textInputControl = spy(new TextArea());
        final TooltipPane tooltipPane = spy(new TooltipPane());

        try (final MockedStatic<TooltipUtilities> ttuStatic = mockStatic(TooltipUtilities.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipProvider> ttpStatic = mockStatic(TooltipProvider.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipMouseMovedHandler> melStatic = mockStatic(TooltipMouseMovedHandler.class, CALLS_REAL_METHODS)) {
            // Initialise mocks
            final TooltipNode ttn = mock(TooltipNode.class);
            final HitInfo info = mock(HitInfo.class);
            final TextInputControlSkin<?> skin = mock(TextAreaSkin.class);
            final MouseEvent event = mock(MouseEvent.class);
            final TooltipProvider.TooltipDefinition ttd1 = mock(TooltipProvider.TooltipDefinition.class);
            final TooltipProvider.TooltipDefinition ttd2 = mock(TooltipProvider.TooltipDefinition.class);

            // Create list of definitions to return.
            final List<TooltipProvider.TooltipDefinition> definitions = new ArrayList<>();
            definitions.add(ttd1);
            definitions.add(ttd2);

            // Set mock behaviour
            when(tooltipPane.isEnabled()).thenReturn(true);
            when(info.getCharIndex()).thenReturn(0);
            when(((TextAreaSkin) skin).getIndex(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(info);
            when(textInputControl.getSkin()).thenReturn(((Skin) skin));
            doNothing().when(tooltipPane).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            doNothing().when(textInputControl).requestFocus();

            // Set static mock behaviour
            ttuStatic.when(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class))).thenAnswer((Answer<Void>) invocation -> null);
            melStatic.when(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class))).thenReturn(ttn);
            ttpStatic.when(() -> TooltipProvider.getTooltips(Mockito.anyString(), Mockito.anyInt())).thenReturn(definitions);

            // Create instance
            final TooltipMouseMovedHandler instance = new TooltipMouseMovedHandler(textInputControl, tooltipPane);

            // Call tested method
            instance.handle(event);

            // Verify calls are made
            verify(tooltipPane, times(1)).isEnabled();
            verify(info, times(1)).getCharIndex();
            verify(((TextAreaSkin) skin), times(1)).getIndex(Mockito.anyDouble(), Mockito.anyDouble());
            verify(textInputControl, times(2)).getSkin();
            verify(tooltipPane, times(0)).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            verify(tooltipPane, times(1)).hideTooltip();
            verify(textInputControl, times(0)).requestFocus();
            ttuStatic.verify(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class)), times(0));
            melStatic.verify(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class)), times(0));
        }
    }

    /**
     * Test of handle method, of class TooltipMouseMovedHandler.
     */
    @Test
    public void testHandle3() {
        System.out.println("testHandle3");
        final TextArea textInputControl = spy(new TextArea());
        final TooltipPane tooltipPane = spy(new TooltipPane());

        try (final MockedStatic<TooltipUtilities> ttuStatic = mockStatic(TooltipUtilities.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipProvider> ttpStatic = mockStatic(TooltipProvider.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipMouseMovedHandler> melStatic = mockStatic(TooltipMouseMovedHandler.class, CALLS_REAL_METHODS)) {
            // Initialise mocks
            final TooltipNode ttn = mock(TooltipNode.class);
            final HitInfo info = mock(HitInfo.class);
            final TextInputControlSkin<?> skin = mock(TextAreaSkin.class);
            final MouseEvent event = mock(MouseEvent.class);
            final TooltipProvider.TooltipDefinition ttd1 = mock(TooltipProvider.TooltipDefinition.class);
            final TooltipProvider.TooltipDefinition ttd2 = mock(TooltipProvider.TooltipDefinition.class);

            // Create list of definitions to return.
            final List<TooltipProvider.TooltipDefinition> definitions = new ArrayList<>();
            definitions.add(ttd1);
            definitions.add(ttd2);

            // Set mock behaviour
            when(tooltipPane.isEnabled()).thenReturn(true);
            when(info.getCharIndex()).thenReturn(4);
            when(((TextAreaSkin) skin).getIndex(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(info);
            when(textInputControl.getSkin()).thenReturn(((Skin) skin));
            doNothing().when(tooltipPane).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            doNothing().when(textInputControl).requestFocus();

            // Set static mock behaviour
            ttuStatic.when(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class))).thenAnswer((Answer<Void>) invocation -> null);
            melStatic.when(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class))).thenReturn(ttn);
            ttpStatic.when(() -> TooltipProvider.getTooltips(Mockito.anyString(), Mockito.anyInt())).thenReturn(definitions);

            // Create instance
            final TooltipMouseMovedHandler instance = new TooltipMouseMovedHandler(textInputControl, tooltipPane);

            // Call tested method
            instance.handle(event);

            // Verify calls are made
            verify(tooltipPane, times(1)).isEnabled();
            verify(info, times(2)).getCharIndex();
            verify(((TextAreaSkin) skin), times(1)).getIndex(Mockito.anyDouble(), Mockito.anyDouble());
            verify(textInputControl, times(2)).getSkin();
            verify(tooltipPane, times(1)).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            verify(tooltipPane, times(0)).hideTooltip();
            verify(textInputControl, times(0)).requestFocus();
            ttuStatic.verify(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class)), times(1));
            melStatic.verify(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class)), times(1));
        }
    }

    /**
     * Test of handle method, of class TooltipMouseMovedHandler.
     */
    @Test
    public void testHandle4() {
        System.out.println("testHandle4");
        final TextArea textInputControl = spy(new TextArea());
        final TooltipPane tooltipPane = spy(new TooltipPane());

        try (final MockedStatic<TooltipUtilities> ttuStatic = mockStatic(TooltipUtilities.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipProvider> ttpStatic = mockStatic(TooltipProvider.class, CALLS_REAL_METHODS);
                final MockedStatic<TooltipMouseMovedHandler> melStatic = mockStatic(TooltipMouseMovedHandler.class, CALLS_REAL_METHODS)) {
            // Initialise mocks
            final TooltipNode ttn = mock(TooltipNode.class);
            final HitInfo info = mock(HitInfo.class);
            final TextInputControlSkin<?> skin = mock(TextAreaSkin.class);
            final MouseEvent event = mock(MouseEvent.class);

            // Create empty list of definitions to return.
            final List<TooltipProvider.TooltipDefinition> definitions = new ArrayList<>();

            // Set mock behaviour
            when(tooltipPane.isEnabled()).thenReturn(true);
            when(info.getCharIndex()).thenReturn(4);
            when(((TextAreaSkin) skin).getIndex(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(info);
            when(textInputControl.getSkin()).thenReturn(((Skin) skin));
            doNothing().when(tooltipPane).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            doNothing().when(textInputControl).requestFocus();

            // Set static mock behaviour
            ttuStatic.when(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class))).thenAnswer((Answer<Void>) invocation -> null);
            melStatic.when(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class))).thenReturn(ttn);
            ttpStatic.when(() -> TooltipProvider.getTooltips(Mockito.anyString(), Mockito.anyInt())).thenReturn(definitions);

            // Create instance
            final TooltipMouseMovedHandler instance = new TooltipMouseMovedHandler(textInputControl, tooltipPane);

            // Call tested method
            instance.handle(event);

            // Verify calls are made
            verify(tooltipPane, times(1)).isEnabled();
            verify(info, times(2)).getCharIndex();
            verify(((TextAreaSkin) skin), times(1)).getIndex(Mockito.anyDouble(), Mockito.anyDouble());
            verify(textInputControl, times(2)).getSkin();
            verify(tooltipPane, times(0)).showTooltip(Mockito.any(TooltipNode.class), Mockito.anyDouble(), Mockito.anyDouble());
            verify(tooltipPane, times(1)).hideTooltip();
            verify(textInputControl, times(0)).requestFocus();
            ttuStatic.verify(() -> TooltipUtilities.selectActiveArea(Mockito.any(TextInputControl.class), Mockito.any(List.class)), times(1));
            melStatic.verify(() -> TooltipMouseMovedHandler.createTooltipNode(Mockito.any(List.class)), times(0));
        }
    }

    /**
     * Test of createTooltipNode method, of class TooltipMouseMovedHandler.
     */
    @Test
    public void testCreateTooltipNode() {
        System.out.println("createTooltipNode");
        // Create list of definitions to return.
        final List<TooltipProvider.TooltipDefinition> definitions = new ArrayList<>();
        final Pane p1 = spy(new Pane());
        final Pane p2 = spy(new Pane());
        final TooltipProvider.TooltipDefinition ttd1 = spy(new TooltipProvider.TooltipDefinition(p1));
        final TooltipProvider.TooltipDefinition ttd2 = spy(new TooltipProvider.TooltipDefinition(p2));

        definitions.add(ttd1);
        definitions.add(ttd2);

        final TooltipNode result = TooltipMouseMovedHandler.createTooltipNode(definitions);
        assertNotNull(result);
        assertNotNull(result.getChildren());
        assertEquals(result.getChildren().size(), definitions.size());
        assertEquals(result.getChildren().get(0), definitions.get(0).getNode());
        assertEquals(result.getChildren().get(1), definitions.get(1).getNode());
        assertNotEquals(result.getChildren().get(1), definitions.get(0).getNode());
    }
}
