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
package au.gov.asd.tac.constellation.plugins.algorithms.sna.global;

import au.gov.asd.tac.constellation.graph.StoreGraph;
import au.gov.asd.tac.constellation.graph.schema.Schema;
import au.gov.asd.tac.constellation.graph.schema.SchemaFactoryUtilities;
import au.gov.asd.tac.constellation.graph.schema.analytic.AnalyticSchemaFactory;
import au.gov.asd.tac.constellation.plugins.PluginExecution;
import au.gov.asd.tac.constellation.plugins.algorithms.sna.SnaConcept;
import au.gov.asd.tac.constellation.plugins.parameters.PluginParameters;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Global Average Degree Plugin Test.
 *
 * @author canis_majoris
 */
public class AverageDegreeNGTest {

    private int graphAttribute;
    
    private int vxId0;
    private int vxId1;
    private int vxId2;
    private int vxId3;
    
    private StoreGraph graph;

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
        // create an analytic graph
        final Schema schema = SchemaFactoryUtilities.getSchemaFactory(AnalyticSchemaFactory.ANALYTIC_SCHEMA_ID).createSchema();
        graph = new StoreGraph(schema);

        // add attributes
        graphAttribute = SnaConcept.GraphAttribute.AVERAGE_DEGREE.ensure(graph);

        // add vertices
        vxId0 = graph.addVertex(); // loop
        vxId1 = graph.addVertex(); // 3 - 2 - 4 
        vxId2 = graph.addVertex(); // 3 - 2 - 4 
        vxId3 = graph.addVertex(); // 3 - 2 - 4 
        graph.addVertex(); // singleton

        // add transactions
        graph.addTransaction(vxId0, vxId0, true);
        graph.addTransaction(vxId1, vxId2, true);
        graph.addTransaction(vxId1, vxId3, true);

    }

    @AfterMethod
    public void tearDownMethod() {
        graph = null;
    }

    @Test
    public void testAverageDegree() throws Exception {
        final AverageDegreePlugin instance = new AverageDegreePlugin();
        final PluginParameters parameters = instance.createParameters();
        PluginExecution.withPlugin(instance).withParameters(parameters).executeNow(graph);

        assertEquals(graph.getFloatValue(graphAttribute, 0), 1.3333334f);
    }
    
    @Test
    public void testNoDirectionAverageDegree() throws Exception {
        final AverageDegreePlugin instance = new AverageDegreePlugin();
        final PluginParameters parameters = instance.createParameters();
        parameters.setBooleanValue(AverageDegreePlugin.INCLUDE_CONNECTIONS_IN_PARAMETER_ID, false);
        parameters.setBooleanValue(AverageDegreePlugin.INCLUDE_CONNECTIONS_OUT_PARAMETER_ID, false);
        parameters.setBooleanValue(AverageDegreePlugin.TREAT_UNDIRECTED_BIDIRECTIONAL, false);
        PluginExecution.withPlugin(instance).withParameters(parameters).executeNow(graph);

        assertEquals(graph.getFloatValue(graphAttribute, 0), 0 / 0F);
    }
}
