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
package au.gov.asd.tac.constellation.views.namedselection.state;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.graph.GraphElementType;
import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import au.gov.asd.tac.constellation.plugins.PluginInfo;
import au.gov.asd.tac.constellation.plugins.PluginInteraction;
import au.gov.asd.tac.constellation.plugins.PluginType;
import au.gov.asd.tac.constellation.plugins.parameters.PluginParameters;
import au.gov.asd.tac.constellation.plugins.templates.PluginTags;
import au.gov.asd.tac.constellation.plugins.templates.SimpleEditPlugin;
import org.openide.util.NbBundle.Messages;

/**
 * This class provides read/write access to the <code>NamedSelectionState</code>
 * items stored on the given graph.
 *
 * @see SimpleEditPlugin
 *
 * @author betelgeuse
 */
@Messages("NamedSelectionStatePlugin=Named Selection: Update State")
@PluginInfo(pluginType = PluginType.UPDATE, tags = {PluginTags.LOW_LEVEL})
public class NamedSelectionStatePlugin extends SimpleEditPlugin {

    private NamedSelectionState state = null;

    /**
     * Creates a new <code>NamedSelectionStatePlugin</code> to be used to set a
     * given <code>NamedSelectionState</code> to the active graph.
     *
     * @param state The <code>NamedSelectionState</code> to write to the active
     * graph.
     *
     * @see NamedSelectionState
     * @see Graph
     */
    public NamedSelectionStatePlugin(final NamedSelectionState state) {
        this.state = state;
    }

    @Override
    public void edit(final GraphWriteMethods graph, final PluginInteraction interaction, final PluginParameters parameters) throws InterruptedException {
        writeStateToGraph(graph);
    }

    /**
     * Returns a <code>NamedSelectionState</code> that has been retrieved from
     * the graph.
     *
     * @return A <code>NamedSelectionState</code> retrieved from the graph, or
     * if no state is present, returns <code>null</code>.
     *
     * @see NamedSelectionState
     */
    public NamedSelectionState getState() {
        return state;
    }

    /**
     * Performs a write operation of the currently held
     * <code>NamedSelectionState</code> to the given <code>Graph</code>.
     *
     * @param graph The <code>Graph</code> to perform the write operation on.
     *
     * @see NamedSelectionState
     * @see Graph
     */
    private void writeStateToGraph(final GraphWriteMethods graph) {
        int attrID = graph.getAttribute(GraphElementType.META, NamedSelectionState.ATTRIBUTE_NAME);

        if (attrID == Graph.NOT_FOUND) {
            attrID = graph.addAttribute(GraphElementType.META, NamedSelectionState.ATTRIBUTE_NAME,
                    NamedSelectionState.ATTRIBUTE_NAME, NamedSelectionState.ATTR_DESC, null, null);
        }

        graph.setObjectValue(attrID, 0, state);
    }
}
