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
package au.gov.asd.tac.constellation.views.analyticview.translators;

import au.gov.asd.tac.constellation.graph.GraphElementType;
import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import au.gov.asd.tac.constellation.graph.manager.GraphManager;
import au.gov.asd.tac.constellation.graph.schema.visual.concept.VisualConcept;
import au.gov.asd.tac.constellation.graph.visual.graphics.BBoxf;
import au.gov.asd.tac.constellation.plugins.PluginException;
import au.gov.asd.tac.constellation.plugins.PluginExecution;
import au.gov.asd.tac.constellation.plugins.PluginInfo;
import au.gov.asd.tac.constellation.plugins.PluginInteraction;
import au.gov.asd.tac.constellation.plugins.parameters.PluginParameter;
import au.gov.asd.tac.constellation.plugins.parameters.PluginParameters;
import au.gov.asd.tac.constellation.plugins.parameters.types.BooleanParameterType;
import au.gov.asd.tac.constellation.plugins.templates.PluginTags;
import au.gov.asd.tac.constellation.plugins.templates.SimpleEditPlugin;
import au.gov.asd.tac.constellation.views.analyticview.results.AnalyticResult;
import au.gov.asd.tac.constellation.views.analyticview.results.FactResult;
import au.gov.asd.tac.constellation.views.analyticview.results.FactResult.ElementFact;
import au.gov.asd.tac.constellation.views.analyticview.utilities.AnalyticTranslatorUtilities;
import au.gov.asd.tac.constellation.views.analyticview.visualisation.SizeVisualisation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author cygnus_x-1
 */
@ServiceProvider(service = GraphVisualisationTranslator.class)
public class FactToSizeTranslator extends AbstractSizeTranslator<FactResult, ElementFact> {
    
    // Maps of the sizes of the vertices and transactions before the plugin is run
    private Map<Integer, Float> vertexSizes = new HashMap<>();
    private Map<Integer, Float> transactionSizes = new HashMap<>();

    @Override
    public String getName() {
        return "Fact -> Size Visualisation";
    }

    @Override
    public Class<? extends AnalyticResult<?>> getResultType() {
        return FactResult.class;
    }

    @Override
    public SizeVisualisation<ElementFact> buildControl() {
        return new SizeVisualisation<>(this);
    }

    @Override
    public void executePlugin(final boolean reset) {
        PluginExecution.withPlugin(new SizeElementsPlugin())
                .withParameter(SizeElementsPlugin.RESET_PARAMETER_ID, reset)
                .executeLater(GraphManager.getDefault().getActiveGraph());
    }

    @Override
    public Map<Integer, Float> getVertexSizes() {
        return Collections.unmodifiableMap(vertexSizes);
    }

    @Override
    public void setVertexSizes(final Map<Integer, Float> sizes) {
        vertexSizes = new HashMap<>(sizes);
    }

    @Override
    public Map<Integer, Float> getTransactionSizes() {
        return Collections.unmodifiableMap(transactionSizes);
    }

    @Override
    public void setTransactionSizes(final Map<Integer, Float> sizes) {
        transactionSizes = new HashMap<>(sizes);
    }

    @PluginInfo(tags = {PluginTags.MODIFY})
    private class SizeElementsPlugin extends SimpleEditPlugin {

        protected static final String RESET_PARAMETER_ID = "SizeElementsPlugin.reset";

        @Override
        public PluginParameters createParameters() {
            final PluginParameters parameters = new PluginParameters();

            final PluginParameter<BooleanParameterType.BooleanParameterValue> resetParameter = BooleanParameterType.build(RESET_PARAMETER_ID);
            resetParameter.setBooleanValue(false);
            parameters.addParameter(resetParameter);

            return parameters;
        }

        @Override
        public void edit(final GraphWriteMethods graph, final PluginInteraction interaction, final PluginParameters parameters) throws InterruptedException, PluginException {
            // get parameter values
            final boolean reset = parameters.getBooleanValue(RESET_PARAMETER_ID);
            
            final String currentGraphKey = GraphManager.getDefault().getActiveGraph().getId();

            // When a new instance of this class is created, it will not know if the current sizes are at their original values
            // This means it won't have valid data to use for the reset function ... in a new instance (ie. a new "Run") it will be empty
            // Using a static cache gets around the issue. We can retrieve and initialise size data from the cache if available.
            
            if (AnalyticTranslatorUtilities.getVertexSizeCache().containsKey(currentGraphKey)) {
                vertexSizes = AnalyticTranslatorUtilities.getVertexSizeCache().get(currentGraphKey);
            } else {
                vertexSizes = new HashMap<>();
            }
            if (AnalyticTranslatorUtilities.getTransactionSizeCache().containsKey(currentGraphKey)) {
                transactionSizes = AnalyticTranslatorUtilities.getTransactionSizeCache().get(currentGraphKey);
            } else {
                transactionSizes = new HashMap<>();
            }

            // ensure attributes
            final int vertexSizeAttribute = VisualConcept.VertexAttribute.NODE_RADIUS.ensure(graph);
            final int transactionSizeAttribute = VisualConcept.TransactionAttribute.WIDTH.ensure(graph);

            if (result == null) {
                return;
            }

            final FactResult factResults = result;
            
            vertexSizes.keySet().forEach(vertexKey -> 
                graph.setFloatValue(vertexSizeAttribute, vertexKey, vertexSizes.get(vertexKey)));
            
            transactionSizes.keySet().forEach(transactionKey -> 
                graph.setFloatValue(transactionSizeAttribute, transactionKey, transactionSizes.get(transactionKey)));
            
            vertexSizes.clear();
            transactionSizes.clear();

            if (!reset) {
                // estimate size of graph
                final BBoxf graphBoundingBox = BBoxf.getGraphBoundingBox(graph);
                float graphEstimatedDiameter = Math.max(graphBoundingBox.getMax()[BBoxf.X] - graphBoundingBox.getMin()[BBoxf.X],
                        graphBoundingBox.getMax()[BBoxf.Y] - graphBoundingBox.getMin()[BBoxf.Y]);
                graphEstimatedDiameter = Math.max(graphEstimatedDiameter,
                        graphBoundingBox.getMax()[BBoxf.Z] - graphBoundingBox.getMin()[BBoxf.Z]);

                // size graph elements based on their value normalised by the estimated diameter of the graph
                for (final ElementFact factResult : factResults.get()) {
                    final GraphElementType elementType = factResult.getElementType();
                    final int elementId = factResult.getElementId();
                    final float elementValue = factResult.getFactValue() ? 1F : 0F;
                    final float sizeIntensity = (float) Math.log(elementValue * graphEstimatedDiameter);
                    switch (elementType) {
                        case VERTEX -> {
                            final float vertexSize = graph.getFloatValue(vertexSizeAttribute, elementId);
                            vertexSizes.put(elementId, vertexSize);
                            graph.setFloatValue(vertexSizeAttribute, elementId, sizeIntensity > 1.0F ? sizeIntensity : 1.0F);
                        }
                        case TRANSACTION -> {
                            final float transactionSize = graph.getFloatValue(transactionSizeAttribute, elementId);
                            transactionSizes.put(elementId, transactionSize);
                            graph.setFloatValue(transactionSizeAttribute, elementId, sizeIntensity > 1.0F ? sizeIntensity : 1.0F);
                        }
                        default -> throw new InvalidElementTypeException("'Size Elements' is not supported "
                                    + "for the element type associated with this analytic question.");
                    }
                }
            }
            
            AnalyticTranslatorUtilities.addToVertexSizeCache(currentGraphKey, vertexSizes);
            AnalyticTranslatorUtilities.addToTransactionSizeCache(currentGraphKey, transactionSizes);
        }

        @Override
        protected boolean isSignificant() {
            return false;
        }

        @Override
        public String getName() {
            return "Analytic View: Size Elements";
        }
    }
}
