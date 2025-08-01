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
package au.gov.asd.tac.constellation.utilities;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Version Utilities Test.
 *
 * @author aquila
 */
public class VersionUtilitiesNGTest {
    
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
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version2_3_a() {
        String minimumVersion = "3.3";
        String current = "2.3.a";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version2_3() {
        String minimumVersion = "3.3";
        String current = "2.3";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version2_4() {
        String minimumVersion = "3.3";
        String current = "2.4";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3() {
        String minimumVersion = "3.3";
        String current = "3";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version_3_mesa_10_1_3() {
        String minimumVersion = "3.3";
        String current = "3 (Core Profile) Mesa 10.1.3"; // replaced 3.3 with 3 which should not be supported
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3_1() {
        String minimumVersion = "3.3";
        String current = "3.1";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3_2() {
        String minimumVersion = "3.3";
        String current = "3.2";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3_2_9() {
        String minimumVersion = "3.3";
        String current = "3.2.9";
        boolean expResult = false;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3_3() {
        String minimum_Version = "3.3";
        String current = "3.3";
        boolean expResult = true;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimum_Version);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version_mesa_10_1_3() {
        String minimumVersion = "3.3";
        String current = "3.3 (Core Profile) Mesa 10.1.3"; // have seen this on a Linux VM
        boolean expResult = true;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version3_3_0() {
        String minimumVersion = "3.3";
        String current = "3.3.0";
        boolean expResult = true;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version4_2() {
        String minimumVersion = "3.3";
        String current = "4.2";
        boolean expResult = true;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }

    /**
     * Test of meets_minimum_version method, of class MeetsMinimum.
     */
    @Test
    public void testMeets_minimum_version4_2_1() {
        String minimumVersion = "3.3";
        String current = "4.2.1";
        boolean expResult = true;
        boolean result = VersionUtilities.doesVersionMeetMinimum(current, minimumVersion);
        assertEquals(result, expResult);
    }
}
