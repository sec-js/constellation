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
package au.gov.asd.tac.constellation.graph.visual.plugins.dim;

import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import au.gov.asd.tac.constellation.graph.schema.visual.concept.VisualConcept;
import au.gov.asd.tac.constellation.plugins.Plugin;
import au.gov.asd.tac.constellation.plugins.PluginInfo;
import au.gov.asd.tac.constellation.plugins.PluginInteraction;
import au.gov.asd.tac.constellation.plugins.PluginType;
import au.gov.asd.tac.constellation.plugins.parameters.PluginParameters;
import au.gov.asd.tac.constellation.plugins.templates.PluginTags;
import au.gov.asd.tac.constellation.plugins.templates.SimpleEditPlugin;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;

/**
 * plugin framework for undim selected elements
 *
 * @author algol
 */
@ServiceProvider(service = Plugin.class)
@Messages("UndimSelectedPlugin=Undim Selected")
@PluginInfo(pluginType = PluginType.DISPLAY, tags = {PluginTags.MODIFY})
public class UndimSelectedPlugin extends SimpleEditPlugin {

    @Override
    public void edit(final GraphWriteMethods graph, final PluginInteraction interaction, final PluginParameters parameters) throws InterruptedException {
        final int vxDimAttr = VisualConcept.VertexAttribute.DIMMED.ensure(graph);
        final int txDimAttr = VisualConcept.TransactionAttribute.DIMMED.ensure(graph);
        final int vxSelectedAttr = VisualConcept.VertexAttribute.SELECTED.ensure(graph);
        final int txSelectedAttr = VisualConcept.TransactionAttribute.SELECTED.ensure(graph);

        final int vxCount = graph.getVertexCount();
        for (int position = 0; position < vxCount; position++) {
            final int vxId = graph.getVertex(position);
            final boolean isSelected = graph.getBooleanValue(vxSelectedAttr, vxId);
            if (isSelected) {
                graph.setBooleanValue(vxDimAttr, vxId, false);
            }
        }

        final int txCount = graph.getTransactionCount();
        for (int position = 0; position < txCount; position++) {
            final int txId = graph.getTransaction(position);
            final boolean isSelected = graph.getBooleanValue(txSelectedAttr, txId);
            if (isSelected) {
                graph.setBooleanValue(txDimAttr, txId, false);
            }
        }
    }
}
