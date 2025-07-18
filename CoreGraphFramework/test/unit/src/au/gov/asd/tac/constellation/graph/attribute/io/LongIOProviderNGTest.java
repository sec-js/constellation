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
package au.gov.asd.tac.constellation.graph.attribute.io;

import au.gov.asd.tac.constellation.graph.GraphAttribute;
import au.gov.asd.tac.constellation.graph.GraphElementType;
import au.gov.asd.tac.constellation.graph.GraphReadMethods;
import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import au.gov.asd.tac.constellation.graph.attribute.LongAttributeDescription;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author serpens24
 */
public class LongIOProviderNGTest {

    // Create object under test
    LongIOProvider instance;

    // Define mocks
    GraphReadMethods mockGraphReadMethods;
    GraphWriteMethods mockGraphWriteMethods;
    JsonNode mockJsonNode;
    JsonGenerator mockJsonGenerator;

    // Test variables
    final int attributeId = 23;
    final int elementId = 41;
    final GraphAttribute attr = new GraphAttribute(attributeId, GraphElementType.GRAPH, "attrType", "attrName", "attrDesc", null, null);
    
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
        instance = new LongIOProvider();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }
    
    /**
     * Perform reset of all mocks and argument captors to ensure clean test steps.
     */
    public void resetMocking() {
        mockGraphReadMethods = mock(GraphReadMethods.class);
        mockGraphWriteMethods = mock(GraphWriteMethods.class);
        mockJsonNode = mock(JsonNode.class);
        mockJsonGenerator = mock(JsonGenerator.class);
    }

    /**
     * Test of getName method, of class LongIOProvider.
     */
    @Test
    public void testGetName() {
        System.out.println("LongIOProviderNGTest.testGetName");
        assertEquals(instance.getName(), LongAttributeDescription.ATTRIBUTE_NAME);
    }

    /**
     * Test of readObject method, of class LongIOProvider.
     * @throws java.io.IOException
     */
    @Test
    public void testReadObject() throws IOException {
        System.out.println("LongIOProviderNGTest.testReadObject");
        
        // Call method under test with JsonNode set to return isNull = true
        resetMocking();
        when(mockJsonNode.longValue()).thenReturn((long)5);
        instance.readObject(attributeId, elementId, mockJsonNode, mockGraphWriteMethods, null, null, null, null);
        Mockito.verify(mockJsonNode, times(1)).longValue();
        Mockito.verify(mockGraphWriteMethods, times(1)).setLongValue(attributeId, elementId, (long)5);
    }

    /**
     * Test of writeObject method, of class LongIOProvider.
     * @throws java.io.IOException
     */
    @Test
    public void testWriteObject() throws IOException {
        System.out.println("LongIOProviderNGTest.testWriteObject");
        
        // Test not verbose and graph.IsDefaultValue is true
        resetMocking();
        when(mockGraphReadMethods.isDefaultValue(anyInt(), anyInt())).thenReturn(true);
        instance.writeObject(attr, elementId, mockJsonGenerator, mockGraphReadMethods, null, false);
        Mockito.verify(mockGraphReadMethods, times(0)).getLongValue(anyInt(), anyInt());
        
        // Test verbose and graph.IsDefaultValue is true
        resetMocking();
        when(mockGraphReadMethods.isDefaultValue(anyInt(), anyInt())).thenReturn(true);
        when(mockGraphReadMethods.getLongValue(anyInt(), anyInt())).thenReturn((long)5);
        instance.writeObject(attr, elementId, mockJsonGenerator, mockGraphReadMethods, null, true);
        Mockito.verify(mockJsonGenerator, times(1)).writeNumberField(attr.getName(), (long)5);
        
        // Test not verbose and graph.IsDefaultValue is false
        resetMocking();
        when(mockGraphReadMethods.isDefaultValue(anyInt(), anyInt())).thenReturn(false);
        when(mockGraphReadMethods.getLongValue(anyInt(), anyInt())).thenReturn((long)5);
        instance.writeObject(attr, elementId, mockJsonGenerator, mockGraphReadMethods, null, false);
        Mockito.verify(mockJsonGenerator, times(1)).writeNumberField(attr.getName(), (long)5);
        
        // Test verbose and graph.IsDefaultValue is false
        resetMocking();
        when(mockGraphReadMethods.isDefaultValue(anyInt(), anyInt())).thenReturn(false);
        when(mockGraphReadMethods.getLongValue(anyInt(), anyInt())).thenReturn((long)5);
        instance.writeObject(attr, elementId, mockJsonGenerator, mockGraphReadMethods, null, true);
        Mockito.verify(mockJsonGenerator, times(1)).writeNumberField(attr.getName(), (long)5);
    }
}
