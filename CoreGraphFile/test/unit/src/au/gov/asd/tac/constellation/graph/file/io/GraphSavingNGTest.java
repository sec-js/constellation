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
package au.gov.asd.tac.constellation.graph.file.io;

import au.gov.asd.tac.constellation.graph.Graph;
import au.gov.asd.tac.constellation.graph.GraphElementType;
import au.gov.asd.tac.constellation.graph.ReadableGraph;
import au.gov.asd.tac.constellation.graph.WritableGraph;
import au.gov.asd.tac.constellation.graph.attribute.BooleanAttributeDescription;
import au.gov.asd.tac.constellation.graph.attribute.FloatAttributeDescription;
import au.gov.asd.tac.constellation.graph.attribute.StringAttributeDescription;
import au.gov.asd.tac.constellation.graph.locking.DualGraph;
import au.gov.asd.tac.constellation.utilities.gui.TextIoProgress;
import java.io.File;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Graph Saving Test.
 *
 * @author algol
 */
public class GraphSavingNGTest {

    private int attrX;
    private int attrY;
    
    private int vxId1;
    private int vxId2;
    private int vxId3;
    private int vxId4;
    private int vxId5;
    private int vxId6;
    private int vxId7;
    
    private int txId1;
    private int txId2;
    private int txId3;
    private int txId4;
    private int txId5;
    
    private int vNameAttr;
    private int tNameAttr;
    private int vSelAttr;
    private int tSelAttr;
    
    private Graph graph;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        graph = new DualGraph(null);
        WritableGraph wg = graph.getWritableGraph("add", true);
        try {
            attrX = wg.addAttribute(GraphElementType.VERTEX, FloatAttributeDescription.ATTRIBUTE_NAME, "x", "x", 0.0, null);
            attrY = wg.addAttribute(GraphElementType.VERTEX, FloatAttributeDescription.ATTRIBUTE_NAME, "y", "y", 0.0, null);

            vNameAttr = wg.addAttribute(GraphElementType.VERTEX, StringAttributeDescription.ATTRIBUTE_NAME, "name", "descr", "", null);
            tNameAttr = wg.addAttribute(GraphElementType.TRANSACTION, StringAttributeDescription.ATTRIBUTE_NAME, "name", "descr", "", null);
            vSelAttr = wg.addAttribute(GraphElementType.VERTEX, BooleanAttributeDescription.ATTRIBUTE_NAME, "selected", "selected", false, null);
            tSelAttr = wg.addAttribute(GraphElementType.TRANSACTION, BooleanAttributeDescription.ATTRIBUTE_NAME, "selected", "selected", false, null);

            vxId1 = wg.addVertex();
            wg.setFloatValue(attrX, vxId1, 1.0f);
            wg.setFloatValue(attrY, vxId1, 1.1f);
            wg.setBooleanValue(vSelAttr, vxId1, false);
            wg.setStringValue(vNameAttr, vxId1, "name1");
            
            vxId2 = wg.addVertex();
            wg.setFloatValue(attrX, vxId2, 2.0f);
            wg.setFloatValue(attrY, vxId2, 2.2f);
            wg.setBooleanValue(vSelAttr, vxId2, true);
            wg.setStringValue(vNameAttr, vxId2, "name2");
            
            vxId3 = wg.addVertex();
            wg.setFloatValue(attrX, vxId3, 3.0f);
            wg.setFloatValue(attrY, vxId3, 3.3f);
            wg.setBooleanValue(vSelAttr, vxId3, false);
            wg.setStringValue(vNameAttr, vxId3, "name3");
            
            vxId4 = wg.addVertex();
            wg.setFloatValue(attrX, vxId4, 4.0f);
            wg.setFloatValue(attrY, vxId4, 4.4f);
            wg.setBooleanValue(vSelAttr, vxId4, true);
            wg.setStringValue(vNameAttr, vxId4, "name4");
            
            vxId5 = wg.addVertex();
            wg.setFloatValue(attrX, vxId5, 5.0f);
            wg.setFloatValue(attrY, vxId5, 5.5f);
            wg.setBooleanValue(vSelAttr, vxId5, false);
            wg.setStringValue(vNameAttr, vxId5, "name5");
            
            vxId6 = wg.addVertex();
            wg.setFloatValue(attrX, vxId6, 6.0f);
            wg.setFloatValue(attrY, vxId6, 6.60f);
            wg.setBooleanValue(vSelAttr, vxId6, true);
            wg.setStringValue(vNameAttr, vxId6, "name6");
            
            vxId7 = wg.addVertex();
            wg.setFloatValue(attrX, vxId7, 7.0f);
            wg.setFloatValue(attrY, vxId7, 7.7f);
            wg.setBooleanValue(vSelAttr, vxId7, false);
            wg.setStringValue(vNameAttr, vxId7, "name7");

            txId1 = wg.addTransaction(vxId1, vxId2, false);
            wg.setBooleanValue(tSelAttr, txId1, false);
            wg.setStringValue(tNameAttr, txId1, "name101");
            
            txId2 = wg.addTransaction(vxId1, vxId3, false);
            wg.setBooleanValue(tSelAttr, txId2, true);
            wg.setStringValue(tNameAttr, txId2, "name102");
            
            txId3 = wg.addTransaction(vxId2, vxId4, true);
            wg.setBooleanValue(tSelAttr, txId3, false);
            wg.setStringValue(tNameAttr, txId3, "name103");
            
            txId4 = wg.addTransaction(vxId4, vxId2, true);
            wg.setBooleanValue(tSelAttr, txId4, true);
            wg.setStringValue(tNameAttr, txId4, "name104");
            
            txId5 = wg.addTransaction(vxId5, vxId6, false);
            wg.setBooleanValue(tSelAttr, txId5, false);
            wg.setStringValue(tNameAttr, txId5, "name105");
        } finally {
            wg.commit();
        }
    }

    @AfterMethod
    public void tearDownMethod() {
        graph = null;
    }

    @Test
    public void writeGraphTest() throws Exception {
        final String name = "tmp1";
        File graphFile = File.createTempFile(name, ".star");
        
        try (final ReadableGraph rg = graph.getReadableGraph()) {
            GraphJsonWriter writer = new GraphJsonWriter();
            writer.writeGraphToZip(rg, graphFile.getPath(), new TextIoProgress(false));
        }

        assertTrue("file created", graphFile.exists());
        graphFile.delete();
    }

    @Test
    public void writeReadGraphTest() throws Exception {
        final String name = "tmp1";
        File graphFile = File.createTempFile(name, ".star");
        
        try (final ReadableGraph rg = graph.getReadableGraph()) {
            assertEquals("num nodes", 7, rg.getVertexCount());
            assertEquals("num transactions", 5, rg.getTransactionCount());
            assertTrue("nd 'name1' found", nodeFound(rg, "name1"));
            assertTrue("nd 'name2' found", nodeFound(rg, "name2"));
            assertTrue("nd 'name3' found", nodeFound(rg, "name3"));
            assertTrue("nd 'name4' found", nodeFound(rg, "name4"));
            assertTrue("nd 'name5' found", nodeFound(rg, "name5"));
            assertTrue("nd 'name6' found", nodeFound(rg, "name6"));
            assertTrue("nd 'name7' found", nodeFound(rg, "name7"));
            assertTrue("tx 'name101' found", transactionFound(rg, "name101"));
            assertTrue("tx 'name102' found", transactionFound(rg, "name102"));
            assertTrue("tx 'name103' found", transactionFound(rg, "name103"));
            assertTrue("tx 'name104' found", transactionFound(rg, "name104"));
            assertTrue("tx 'name105' found", transactionFound(rg, "name105"));
        }
        
        try (final ReadableGraph rg = graph.getReadableGraph()) {
            GraphJsonWriter writer = new GraphJsonWriter();
            writer.writeGraphToZip(rg, graphFile.getPath(), new TextIoProgress(false));
        }
        assertTrue("file created", graphFile.exists());
        
        try (final ReadableGraph rg = graph.getReadableGraph()) {
            assertEquals("num nodes", 7, rg.getVertexCount());
            assertEquals("num transactions", 5, rg.getTransactionCount());
            assertTrue("nd 'name1' found", nodeFound(rg, "name1"));
            assertTrue("nd 'name2' found", nodeFound(rg, "name2"));
            assertTrue("nd 'name3' found", nodeFound(rg, "name3"));
            assertTrue("nd 'name4' found", nodeFound(rg, "name4"));
            assertTrue("nd 'name5' found", nodeFound(rg, "name5"));
            assertTrue("nd 'name6' found", nodeFound(rg, "name6"));
            assertTrue("nd 'name7' found", nodeFound(rg, "name7"));
            assertTrue("tx 'name101' found", transactionFound(rg, "name101"));
            assertTrue("tx 'name102' found", transactionFound(rg, "name102"));
            assertTrue("tx 'name103' found", transactionFound(rg, "name103"));
            assertTrue("tx 'name104' found", transactionFound(rg, "name104"));
            assertTrue("tx 'name105' found", transactionFound(rg, "name105"));
        }
    }

    // determine whether the node of the specified name exists in the graph
    private boolean nodeFound(ReadableGraph graph, String baseName) {
        int nameAttrId = graph.getAttribute(GraphElementType.VERTEX, "name");
        boolean found = false;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int id = graph.getVertex(i);
            String name = graph.getStringValue(nameAttrId, id);
            if (baseName.equals(name)) {
                found = true;
            }
        }
        return found;
    }

    // determine whether the transaction of the specified name exists in the graph
    private boolean transactionFound(ReadableGraph graph, String baseName) {
        int nameAttrId = graph.getAttribute(GraphElementType.TRANSACTION, "name");
        boolean found = false;
        for (int i = 0; i < graph.getTransactionCount(); i++) {
            int id = graph.getTransaction(i);
            String name = graph.getStringValue(nameAttrId, id);
            if (baseName.equals(name)) {
                found = true;
            }
        }
        return found;
    }
}
