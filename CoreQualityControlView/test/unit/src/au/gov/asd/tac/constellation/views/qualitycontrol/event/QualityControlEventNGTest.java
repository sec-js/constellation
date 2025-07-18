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
package au.gov.asd.tac.constellation.views.qualitycontrol.event;

import au.gov.asd.tac.constellation.views.qualitycontrol.QualityControlEvent;
import au.gov.asd.tac.constellation.views.qualitycontrol.rules.IdentifierInconsistentWithTypeRule;
import au.gov.asd.tac.constellation.views.qualitycontrol.rules.MissingTypeRule;
import au.gov.asd.tac.constellation.views.qualitycontrol.rules.UnknownTypeRule;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * QualityControlEvent Test.
 *
 * @author aldebaran30701
 */
public class QualityControlEventNGTest {

    private static final QualityControlEvent.QualityCategory OK = QualityControlEvent.QualityCategory.OK;
    private static final QualityControlEvent.QualityCategory MINOR = QualityControlEvent.QualityCategory.MINOR;
    private static final QualityControlEvent.QualityCategory MEDIUM = QualityControlEvent.QualityCategory.MEDIUM;
    private static final QualityControlEvent.QualityCategory MAJOR = QualityControlEvent.QualityCategory.MAJOR;
    private static final QualityControlEvent.QualityCategory SEVERE = QualityControlEvent.QualityCategory.SEVERE;
    private static final QualityControlEvent.QualityCategory CRITICAL = QualityControlEvent.QualityCategory.CRITICAL;
    
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
     * Test of getCategoryFromString method, of class QualityControlEvent.
     */
    @Test
    public void testGetCategoryFromString() {
        String categoryString = "ok";

        // OK test
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "OK";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "OKA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "AOK";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);

        // MINOR test
        categoryString = "minor";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MINOR);
        categoryString = "MINOR";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MINOR);
        categoryString = "MINORA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "AMINOR";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);

        // MEDIUM test
        categoryString = "medium";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MEDIUM);
        categoryString = "MEDIUM";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MEDIUM);
        categoryString = "MEDIUMA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "AMEDIUM";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);

        // MAJOR test
        categoryString = "major";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MAJOR);
        categoryString = "MAJOR";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), MAJOR);
        categoryString = "MAJORA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "AMAJOR";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);

        // SEVERE test
        categoryString = "severe";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), SEVERE);
        categoryString = "SEVERE";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), SEVERE);
        categoryString = "SEVEREA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "ASEVERE";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);

        // CRITICAL test
        categoryString = "critical";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), CRITICAL);
        categoryString = "CRITICAL";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), CRITICAL);
        categoryString = "CRITICALA";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
        categoryString = "ACRITICAL";
        assertEquals(QualityControlEvent.getCategoryFromString(categoryString), OK);
    }

    /**
     * Test of getRuleByString method, of class QualityControlRule.
     *
     */
    @Test
    public void testGetRuleByString() {
        // Test missing type rule
        MissingTypeRule missingRule = new MissingTypeRule();
        String ruleName = missingRule.getName();
        assertEquals(QualityControlEvent.getRuleByString(ruleName).getClass(), missingRule.getClass());

        // Test unknown type rule
        UnknownTypeRule unknownRule = new UnknownTypeRule();
        ruleName = unknownRule.getName();
        assertEquals(QualityControlEvent.getRuleByString(ruleName).getClass(), unknownRule.getClass());

        // Test IdentifierInconsistentWithTypeRule
        IdentifierInconsistentWithTypeRule inconsistentRule = new IdentifierInconsistentWithTypeRule();
        ruleName = inconsistentRule.getName();
        assertEquals(QualityControlEvent.getRuleByString(ruleName).getClass(), inconsistentRule.getClass());

        // Test empty string
        assertNull(QualityControlEvent.getRuleByString(""));

        // Test blank string
        assertNull(QualityControlEvent.getRuleByString(" "));
    }
}
