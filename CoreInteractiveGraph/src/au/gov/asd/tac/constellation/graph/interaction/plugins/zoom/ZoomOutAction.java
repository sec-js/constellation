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
import au.gov.asd.tac.constellation.graph.node.plugins.SimplePluginAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 * Zoom the camera outwards
 *
 * @author Quasar985
 */
@ActionID(category = "Display", id = "au.gov.asd.tac.constellation.functionality.zoom.ZoomOut")
@ActionRegistration(displayName = "#CTL_ZoomOut", iconBase = "au/gov/asd/tac/constellation/graph/interaction/plugins/zoom/resources/zoom_out.png", surviveFocusChange = true)
@ActionReferences({
    @ActionReference(path = "Menu/Display", position = 201, separatorBefore = 199),
    @ActionReference(path = "Toolbars/Display", position = 101),
    @ActionReference(path = "Shortcuts", name = "D-MINUS"),
    @ActionReference(path = "Shortcuts", name = "D-SUBTRACT")
})
@Messages("CTL_ZoomOut=Zoom Out")
public final class ZoomOutAction extends SimplePluginAction {
    
    public ZoomOutAction(final GraphNode context) {
        super(context, InteractiveGraphPluginRegistry.ZOOM_OUT);
    }
}
