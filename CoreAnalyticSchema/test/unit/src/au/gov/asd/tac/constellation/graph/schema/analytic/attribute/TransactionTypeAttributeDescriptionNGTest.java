/*
 * Copyright 2010-2024 Australian Signals Directorate
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
package au.gov.asd.tac.constellation.graph.schema.analytic.attribute;

import au.gov.asd.tac.constellation.graph.schema.analytic.concept.AnalyticConcept;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author antares
 */
public class TransactionTypeAttributeDescriptionNGTest {
    
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
        // Not currently required
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }

    /**
     * Test of convertFromString method, of class TransactionTypeAttributeDescription.
     */
    @Test
    public void testConvertFromString() {
        System.out.println("convertFromString");
        
        final TransactionTypeAttributeDescription instance = new TransactionTypeAttributeDescription();
        assertNull(instance.convertFromString(""));
        assertEquals(instance.convertFromString("Correlation"), AnalyticConcept.TransactionType.CORRELATION);
    }

    /**
     * Test of setDefault method, of class TransactionTypeAttributeDescription.
     */
    @Test
    public void testSetDefault() {
        System.out.println("setDefault");
        
        final TransactionTypeAttributeDescription instance = new TransactionTypeAttributeDescription();
        
        assertNull(instance.getDefault());
        
        instance.setDefault(AnalyticConcept.TransactionType.CORRELATION);       
        assertEquals(instance.getDefault(), AnalyticConcept.TransactionType.CORRELATION);
        
        instance.setDefault("Made.Up.Type");       
        // It will create an incomplete type which in turn should trigger a reset to the default value
        assertNull(instance.getDefault());
    }
}