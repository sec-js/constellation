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
package au.gov.asd.tac.constellation.graph.interaction.plugins.io;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.graph.ReadableGraph;
import au.gov.asd.tac.constellation.graph.WritableGraph;
import au.gov.asd.tac.constellation.graph.file.GraphDataObject;
import au.gov.asd.tac.constellation.graph.file.GraphObjectUtilities;
import au.gov.asd.tac.constellation.graph.file.io.GraphJsonReader;
import au.gov.asd.tac.constellation.graph.file.save.AutosaveUtilities;
import au.gov.asd.tac.constellation.graph.locking.DualGraph;
import au.gov.asd.tac.constellation.graph.node.GraphNode;
import au.gov.asd.tac.constellation.graph.schema.Schema;
import au.gov.asd.tac.constellation.graph.schema.SchemaFactoryUtilities;
import au.gov.asd.tac.constellation.graph.schema.analytic.AnalyticSchemaFactory;
import au.gov.asd.tac.constellation.graph.schema.visual.concept.VisualConcept;
import au.gov.asd.tac.constellation.plugins.PluginExecution;
import au.gov.asd.tac.constellation.utilities.file.FileExtensionConstants;
import au.gov.asd.tac.constellation.utilities.gui.TextIoProgress;
import java.io.File;
import org.openide.windows.TopComponent;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Autosave Graph Plugin Test
 *
 * @author Delphinus8821
 */
public class AutosaveGraphPluginNGTest {

    private int attrX;
    private int attrY;
    
    private int vxId1;
    private int vxId2;
    private int vxId3;
    private int vxId4;
    private int vxId5;
    private int vxId6;
    private int vxId7;
    
    private int vAttrId;
    
    private Graph graph;
    
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
        final Schema schema = SchemaFactoryUtilities.getSchemaFactory(AnalyticSchemaFactory.ANALYTIC_SCHEMA_ID).createSchema();
        graph = new DualGraph(schema);

        final WritableGraph wg = graph.getWritableGraph("Autosave", true);
        try {
            attrX = VisualConcept.VertexAttribute.X.ensure(wg);
            attrY = VisualConcept.VertexAttribute.Y.ensure(wg);
            vAttrId = VisualConcept.VertexAttribute.SELECTED.ensure(wg);

            vxId1 = wg.addVertex();
            wg.setFloatValue(attrX, vxId1, 1.0f);
            wg.setFloatValue(attrY, vxId1, 1.0f);
            wg.setBooleanValue(vAttrId, vxId1, false);
            
            vxId2 = wg.addVertex();
            wg.setFloatValue(attrX, vxId2, 5.0f);
            wg.setFloatValue(attrY, vxId2, 1.0f);
            wg.setBooleanValue(vAttrId, vxId2, true);
            
            vxId3 = wg.addVertex();
            wg.setFloatValue(attrX, vxId3, 1.0f);
            wg.setFloatValue(attrY, vxId3, 5.0f);
            wg.setBooleanValue(vAttrId, vxId3, false);
            
            vxId4 = wg.addVertex();
            wg.setFloatValue(attrX, vxId4, 5.0f);
            wg.setFloatValue(attrY, vxId4, 5.0f);
            wg.setBooleanValue(vAttrId, vxId4, false);
            
            vxId5 = wg.addVertex();
            wg.setFloatValue(attrX, vxId5, 10.0f);
            wg.setFloatValue(attrY, vxId5, 10.0f);
            wg.setBooleanValue(vAttrId, vxId5, true);
            
            vxId6 = wg.addVertex();
            wg.setFloatValue(attrX, vxId6, 15.0f);
            wg.setFloatValue(attrY, vxId6, 15.0f);
            
            vxId7 = wg.addVertex();
            wg.setFloatValue(attrX, vxId7, 100.0f);
            wg.setFloatValue(attrY, vxId7, 100.0f);

            wg.addTransaction(vxId1, vxId2, false);
            wg.addTransaction(vxId1, vxId3, false);
            wg.addTransaction(vxId2, vxId4, true);
            wg.addTransaction(vxId4, vxId2, true);
            wg.addTransaction(vxId5, vxId6, false);
        } finally {
            wg.commit();
        }
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }

    /**
     * Test of execute method, of class AutosaveGraphPlugin.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testExecute() throws Exception {
        final File saveDir = AutosaveUtilities.getAutosaveDir();
        final File saveFile = new File(saveDir, graph.getId() + FileExtensionConstants.STAR);

        // check the autosave file doesn't exist before running the plugin
        assertEquals(saveFile.exists(), false);

        final TopComponent tc = new TopComponent();
        tc.setName("TestName");
        final GraphDataObject gdo = GraphObjectUtilities.createMemoryDataObject("graph", true);
        final GraphNode graphNode = new GraphNode(graph, gdo, tc, null);
        
        final AutosaveGraphPlugin instance = new AutosaveGraphPlugin();
        PluginExecution.withPlugin(instance).executeNow(graph);

        // check that the autosave file does now exist
        assertEquals(saveFile.exists(), true);

        final Graph openSavedGraph = new GraphJsonReader().readGraphZip(saveFile, new TextIoProgress(false));
        try (final ReadableGraph rg = openSavedGraph.getReadableGraph()) {
            // check that the graph from the autosave matches the original graph
            assertEquals(rg.getVertexCount(), 7);
            assertEquals(rg.getStringValue(vAttrId, vxId1), "false");
            assertEquals(rg.getStringValue(vAttrId, vxId2), "true");
            assertEquals(rg.getStringValue(vAttrId, vxId3), "false");
            assertEquals(rg.getStringValue(vAttrId, vxId4), "false");
            assertEquals(rg.getStringValue(vAttrId, vxId5), "true");
            assertEquals(rg.getTransactionCount(), 5);
        } finally {
            // deleting the file afterwards
            AutosaveUtilities.deleteAutosave(saveFile);
            graphNode.destroy();
        }
    }
}
