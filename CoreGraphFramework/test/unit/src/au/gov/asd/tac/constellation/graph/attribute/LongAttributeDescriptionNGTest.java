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
package au.gov.asd.tac.constellation.graph.attribute;

import au.gov.asd.tac.constellation.graph.GraphReadMethods;
import au.gov.asd.tac.constellation.graph.NativeAttributeType;
import au.gov.asd.tac.constellation.graph.StoreGraph;
import au.gov.asd.tac.constellation.graph.locking.ParameterReadAccess;
import au.gov.asd.tac.constellation.graph.locking.ParameterWriteAccess;
import au.gov.asd.tac.constellation.graph.utilities.MultiValueStore;
import java.time.LocalDate;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author arcturus
 */
public class LongAttributeDescriptionNGTest {

    LongAttributeDescription instance;
    
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
        instance = new LongAttributeDescription();
        instance.setCapacity(1);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        // Not currently required
    }

    /**
     * Test of getName method, of class LongAttributeDescription.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        String expResult = "long";
        String result = instance.getName();
        assertEquals(result, expResult);
    }

    /**
     * Test of getNativeClass method, of class LongAttributeDescription.
     */
    @Test
    public void testGetNativeClass() {
        System.out.println("getNativeClass");
        
        Class<?> result = instance.getNativeClass();
        assertEquals(result, long.class);
    }

    /**
     * Test of getNativeType method, of class LongAttributeDescription.
     */
    @Test
    public void testGetNativeType() {
        System.out.println("getNativeType");
        
        NativeAttributeType expResult = NativeAttributeType.LONG;
        NativeAttributeType result = instance.getNativeType();
        assertEquals(result, expResult);
    }

    /**
     * Test of getDefault method, of class LongAttributeDescription.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        
        Object expResult = (long) 0;
        Object result = instance.getDefault();
        assertEquals(result, expResult);
    }

    /**
     * Test of setDefault method, of class LongAttributeDescription.
     */
    @Test
    public void testSetDefault() {
        System.out.println("setDefault");
        
        Object value = null;
        instance.setDefault(value);
        assertEquals(instance.getDefault(), LongAttributeDescription.DEFAULT_VALUE);
    }

    /**
     * Test of getCapacity method, of class LongAttributeDescription.
     */
    @Test
    public void testGetCapacity() {
        System.out.println("getCapacity");
        
        int expResult = 1;
        int result = instance.getCapacity();
        assertEquals(result, expResult);
    }

    /**
     * Test of setCapacity method, of class LongAttributeDescription.
     */
    @Test
    public void testSetCapacity() {
        System.out.println("setCapacity");
        
        int capacity = 0;
        instance.setCapacity(capacity);
        assertEquals(instance.getCapacity(), capacity);
    }

    /**
     * Test of getByte method, of class LongAttributeDescription.
     */
    @Test
    public void testGetByte() {
        System.out.println("getByte");
        
        int id = 0;
        byte expResult = 0;
        byte result = instance.getByte(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setByte method, of class LongAttributeDescription.
     */
    @Test
    public void testSetByte() {
        System.out.println("setByte");
        
        int id = 0;
        byte value = 0;
        instance.setByte(id, value);
        assertEquals(instance.getByte(id), value);
    }

    /**
     * Test of getShort method, of class LongAttributeDescription.
     */
    @Test
    public void testGetShort() {
        System.out.println("getShort");
        
        int id = 0;
        short expResult = 0;
        short result = instance.getShort(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setShort method, of class LongAttributeDescription.
     */
    @Test
    public void testSetShort() {
        System.out.println("setShort");
        
        int id = 0;
        short value = 0;
        instance.setShort(id, value);
        assertEquals(instance.getShort(id), value);
    }

    /**
     * Test of getInt method, of class LongAttributeDescription.
     */
    @Test
    public void testGetInt() {
        System.out.println("getInt");
        
        int id = 0;
        int expResult = 0;
        int result = instance.getInt(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setInt method, of class LongAttributeDescription.
     */
    @Test
    public void testSetInt() {
        System.out.println("setInt");
        
        int id = 0;
        int value = 0;
        instance.setInt(id, value);
        assertEquals(instance.getInt(id), value);
    }

    /**
     * Test of getLong method, of class LongAttributeDescription.
     */
    @Test
    public void testGetLong() {
        System.out.println("getLong");
        
        int id = 0;
        long expResult = 0L;
        long result = instance.getLong(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setLong method, of class LongAttributeDescription.
     */
    @Test
    public void testSetLong() {
        System.out.println("setLong");
        
        int id = 0;
        long value = 0L;
        instance.setLong(id, value);
        assertEquals(instance.getLong(id), value);
    }

    /**
     * Test of getFloat method, of class LongAttributeDescription.
     */
    @Test
    public void testGetFloat() {
        System.out.println("getFloat");
        
        int id = 0;
        float expResult = 0.0F;
        float result = instance.getFloat(id);
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of setFloat method, of class LongAttributeDescription.
     */
    @Test
    public void testSetFloat() {
        System.out.println("setFloat");
        
        int id = 0;
        float value = 0.0F;
        instance.setFloat(id, value);
        assertEquals(instance.getFloat(id), value);
    }

    /**
     * Test of getDouble method, of class LongAttributeDescription.
     */
    @Test
    public void testGetDouble() {
        System.out.println("getDouble");
        
        int id = 0;
        double expResult = 0.0;
        double result = instance.getDouble(id);
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of setDouble method, of class LongAttributeDescription.
     */
    @Test
    public void testSetDouble() {
        System.out.println("setDouble");
        
        int id = 0;
        double value = 0.0;
        instance.setDouble(id, value);
        assertEquals(instance.getDouble(id), value);
    }

    /**
     * Test of getBoolean method, of class LongAttributeDescription.
     */
    @Test
    public void testGetBoolean() {
        System.out.println("getBoolean");
        
        int id = 0;
        boolean expResult = false;
        boolean result = instance.getBoolean(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setBoolean method, of class LongAttributeDescription.
     */
    @Test
    public void testSetBoolean() {
        System.out.println("setBoolean");
        
        int id = 0;
        instance.setBoolean(id, true);
        assertTrue(instance.getBoolean(id));
        instance.setBoolean(id, false);
        assertFalse(instance.getBoolean(id));
    }

    /**
     * Test of getChar method, of class LongAttributeDescription.
     */
    @Test
    public void testGetChar() {
        System.out.println("getChar");
        
        int id = 0;
        char expResult = 0;
        char result = instance.getChar(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setChar method, of class LongAttributeDescription.
     */
    @Test
    public void testSetChar() {
        System.out.println("setChar");
        
        int id = 0;
        char value = (char) 0;
        instance.setChar(id, value);
        assertEquals(instance.getChar(id), value);
    }

    /**
     * Test of getString method, of class LongAttributeDescription.
     */
    @Test
    public void testGetString() {
        System.out.println("getString");
        
        int id = 0;
        String expResult = "0";
        String result = instance.getString(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setString method, of class LongAttributeDescription.
     */
    @Test
    public void testSetString() {
        System.out.println("setString");
        
        int id = 0;
        instance.setString(id, "");
        assertEquals(instance.getString(id), String.valueOf(LongAttributeDescription.DEFAULT_VALUE));
        instance.setString(id, "42");
        assertEquals(instance.getString(id), "42");
    }
    
    /**
     * Test of setString method, of class LongAttributeDescription. Trying to convert a non-long string to long
     */
    @Test(expectedExceptions = IllegalArgumentException.class, 
            expectedExceptionsMessageRegExp = "Error converting String \'not a long\' to long")
    public void testSetStringCantConvert() {
        System.out.println("setStringCantConvert");
        
        int id = 0;
        instance.setString(id, "not a long");
    }

    /**
     * Test of acceptsString method, of class LongAttributeDescription.
     */
    @Test
    public void testAcceptsString() {
        System.out.println("acceptsString");
        
        assertNull(instance.acceptsString(""));
        assertEquals(instance.acceptsString("not a long"), "Error converting String \'not a long\' to long");
    }

    /**
     * Test of getObject method, of class LongAttributeDescription.
     */
    @Test
    public void testGetObject() {
        System.out.println("getObject");
        
        int id = 0;
        Object expResult = (long) 0;
        Object result = instance.getObject(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of setObject method, of class LongAttributeDescription.
     */
    @Test
    public void testSetObject() {
        System.out.println("setObject");
        
        int id = 0;
        instance.setObject(id, null);
        assertEquals(instance.getObject(id), LongAttributeDescription.DEFAULT_VALUE);
        instance.setObject(id, 42);
        assertEquals(instance.getObject(id), 42L);
        instance.setObject(id, Boolean.TRUE);
        assertEquals(instance.getObject(id), 1L);
        instance.setObject(id, Boolean.FALSE);
        assertEquals(instance.getObject(id), 0L);
        instance.setObject(id, (char) 7);
        assertEquals(instance.getObject(id), 7L);
        instance.setObject(id, "7");
        assertEquals(instance.getObject(id), 7L);
    }
    
    /**
     * Test of setObject method, of class LongAttributeDescription. Trying to convert an incompatible type to long
     */
    @Test(expectedExceptions = IllegalArgumentException.class, 
            expectedExceptionsMessageRegExp = "Error converting Object \'class java.time.LocalDate\' to long")
    public void testSetObjectCantConvert() {
        System.out.println("setObjectCantConvert");
        
        int id = 0;
        instance.setObject(id, LocalDate.of(1999, 12, 31));
    }

    /**
     * Test of isClear method, of class LongAttributeDescription.
     */
    @Test
    public void testIsClear() {
        System.out.println("isClear");
        
        int id = 0;
        assertTrue(instance.isClear(id));
        instance.setLong(0, 1L);
        assertFalse(instance.isClear(id));
    }

    /**
     * Test of clear method, of class LongAttributeDescription.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        
        int id = 0;
        instance.clear(id);
        assertTrue(instance.isClear(id));
    }

    /**
     * Test of copy method, of class LongAttributeDescription.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        
        GraphReadMethods graph = new StoreGraph();
        AttributeDescription expResult = instance;
        AttributeDescription result = instance.copy(graph);
        assertEquals(result.getLong(0), expResult.getLong(0));
    }

    /**
     * Test of hashCode method, of class LongAttributeDescription.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        
        int id = 0;
        int expResult = 0;
        int result = instance.hashCode(id);
        assertEquals(result, expResult);
    }

    /**
     * Test of equals method, of class LongAttributeDescription.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        instance.setCapacity(3);
        instance.setLong(2, 1L);
        assertTrue(instance.equals(0, 1));
        assertFalse(instance.equals(0, 2));
    }

    /**
     * Test of save method, of class LongAttributeDescription.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        
        int id = 0;
        ParameterWriteAccess access = new MultiValueStore();
        instance.save(id, access);
    }

    /**
     * Test of restore method, of class LongAttributeDescription.
     */
    @Test
    public void testRestore() {
        System.out.println("restore");
        
        int id = 0;
        ParameterReadAccess access = new MultiValueStore();
        instance.restore(id, access);
    }

    /**
     * Test of saveData method, of class LongAttributeDescription.
     */
    @Test
    public void testSaveData() {
        System.out.println("saveData");
        
        Object expResult = new long[1];
        Object result = instance.saveData();
        assertEquals(result, expResult);
    }

    /**
     * Test of restoreData method, of class LongAttributeDescription.
     */
    @Test
    public void testRestoreData() {
        System.out.println("restoreData");
        
        Object savedData = new long[1];
        instance.restoreData(savedData);
    }
}
