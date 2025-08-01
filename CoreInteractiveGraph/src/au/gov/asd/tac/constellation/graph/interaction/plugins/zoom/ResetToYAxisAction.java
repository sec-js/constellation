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
package au.gov.asd.tac.constellation.graph.interaction.plugins.zoom;

import au.gov.asd.tac.constellation.graph.interaction.InteractiveGraphPluginRegistry;
import au.gov.asd.tac.constellation.graph.node.GraphNode;
import au.gov.asd.tac.constellation.plugins.PluginExecution;
import au.gov.asd.tac.constellation.utilities.icon.UserInterfaceIconProvider;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author algol
 */
@ActionID(category = "Display", id = "au.gov.asd.tac.constellation.functionality.zoom.ResetToYAxisAction")
@ActionRegistration(displayName = "#CTL_ResetToYAxisAction", iconBase = "au/gov/asd/tac/constellation/graph/interaction/plugins/zoom/resources/axis_y.png", surviveFocusChange = true)
@ActionReference(path = "Menu/Display/Reset View by Axis", position = 300)
@Messages("CTL_ResetToYAxisAction=Y Axis")
public class ResetToYAxisAction extends AbstractAction {

    private final GraphNode context;
    private static final Icon AXIS_Y_ICON = UserInterfaceIconProvider.AXIS_Y.buildIcon(16);

    public ResetToYAxisAction(final GraphNode context) {
        this.context = context;
        putValue(Action.SMALL_ICON, AXIS_Y_ICON);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        PluginExecution.withPlugin(InteractiveGraphPluginRegistry.RESET_VIEW)
                .withParameter(ResetViewPlugin.AXIS_PARAMETER_ID, "y")
                .withParameter(ResetViewPlugin.SIGNIFICANT_PARAMETER_ID, true)
                .executeLater(context.getGraph());
    }
}
