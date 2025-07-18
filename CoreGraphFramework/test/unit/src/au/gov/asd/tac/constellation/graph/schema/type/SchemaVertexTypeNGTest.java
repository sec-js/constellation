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
package au.gov.asd.tac.constellation.graph.schema.type;

import au.gov.asd.tac.constellation.utilities.color.ConstellationColor;
import au.gov.asd.tac.constellation.utilities.icon.CharacterIconProvider;
import au.gov.asd.tac.constellation.utilities.icon.DefaultIconProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Schema Vertex Type Test.
 *
 * @author arcturus
 */
public class SchemaVertexTypeNGTest {
    
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

    @Test
    public void testBuildTypeObjectWithNothingSet() {
        final SchemaVertexType type = new SchemaVertexType.Builder(null).build();

        Assert.assertEquals(type.name, null);
        Assert.assertEquals(type.getName(), null);
        Assert.assertEquals(type.description, null);
        Assert.assertEquals(type.getDescription(), null);
        Assert.assertEquals(type.color, ConstellationColor.GREY);
        Assert.assertEquals(type.getColor(), ConstellationColor.GREY);
        Assert.assertEquals(type.superType, type);
        Assert.assertEquals(type.getSuperType(), type);
        Assert.assertEquals(type.properties, new HashMap<>());
        Assert.assertEquals(type.getProperties(), new HashMap<>());
        Assert.assertEquals(type.incomplete, false);
        Assert.assertEquals(type.isIncomplete(), false);
        Assert.assertEquals(type.getForegroundIcon(), DefaultIconProvider.UNKNOWN);
        Assert.assertEquals(type.getBackgroundIcon(), DefaultIconProvider.FLAT_SQUARE);
        Assert.assertEquals(type.getDetectionRegex(), null);
        Assert.assertEquals(type.getValidationRegex(), null);
        Assert.assertEquals(type.overridenType, null);
        Assert.assertEquals(type.getOverridenType(), null);
    }

    @Test
    public void testBuildTypeObjectWithAttributesSet() {
        final Pattern detectRegex = Pattern.compile("\\+?([0-9]{8,13})", Pattern.CASE_INSENSITIVE);
        final Pattern validationRegex = Pattern.compile("\\+?([0-9]{8,15})", Pattern.CASE_INSENSITIVE);

        final SchemaVertexType type = new SchemaVertexType.Builder("name")
                .setDescription("description")
                .setColor(ConstellationColor.GREEN)
                .setForegroundIcon(CharacterIconProvider.CHAR_0020)
                .setBackgroundIcon(DefaultIconProvider.FLAT_CIRCLE)
                .setDetectionRegex(detectRegex)
                .setValidationRegex(validationRegex)
                .setProperty("my key", "my value")
                .setIncomplete(true)
                .build();

        final Map<Object, Object> properties = new HashMap<>();
        properties.put("my key", "my value");

        Assert.assertEquals(type.name, "name");
        Assert.assertEquals(type.getName(), "name");
        Assert.assertEquals(type.description, "description");
        Assert.assertEquals(type.getDescription(), "description");
        Assert.assertEquals(type.color, ConstellationColor.GREEN);
        Assert.assertEquals(type.getColor(), ConstellationColor.GREEN);
        Assert.assertEquals(type.superType, type);
        Assert.assertEquals(type.getSuperType(), type);
        Assert.assertEquals(type.properties, properties);
        Assert.assertEquals(type.getProperties(), properties);
        Assert.assertEquals(type.incomplete, true);
        Assert.assertEquals(type.isIncomplete(), true);
        Assert.assertEquals(type.getForegroundIcon(), CharacterIconProvider.CHAR_0020);
        Assert.assertEquals(type.getBackgroundIcon(), DefaultIconProvider.FLAT_CIRCLE);
        Assert.assertEquals(type.getDetectionRegex(), detectRegex);
        Assert.assertEquals(type.getValidationRegex(), validationRegex);
        Assert.assertEquals(type.overridenType, null);
        Assert.assertEquals(type.getOverridenType(), null);
    }

    @Test
    public void testBuildTypeObjectWithTypeHavingParent() {
        final Pattern detectRegex = Pattern.compile("\\+?([0-9]{8,13})", Pattern.CASE_INSENSITIVE);
        final Pattern validationRegex = Pattern.compile("\\+?([0-9]{8,15})", Pattern.CASE_INSENSITIVE);

        final SchemaVertexType topLevel = new SchemaVertexType.Builder("top level")
                .build();

        final SchemaVertexType parent = new SchemaVertexType.Builder("parent")
                .setDescription("description")
                .setColor(ConstellationColor.GREEN)
                .setForegroundIcon(CharacterIconProvider.CHAR_0020)
                .setBackgroundIcon(DefaultIconProvider.FLAT_CIRCLE)
                .setDetectionRegex(detectRegex)
                .setValidationRegex(validationRegex)
                .setProperty("my key", "my value")
                .setSuperType(topLevel)
                .setIncomplete(true)
                .build();

        final SchemaVertexType child = new SchemaVertexType.Builder("child")
                .setSuperType(parent)
                .build();

        Assert.assertEquals(child.name, "child");
        Assert.assertEquals(child.getName(), "child");
        Assert.assertEquals(child.description, null);
        Assert.assertEquals(child.getDescription(), null);
        Assert.assertEquals(child.color, ConstellationColor.GREEN);
        Assert.assertEquals(child.getColor(), ConstellationColor.GREEN);
        Assert.assertEquals(child.superType, parent);
        Assert.assertEquals(child.getSuperType(), parent);
        Assert.assertEquals(child.properties, new HashMap<>()); // not inherited as expected ??
        Assert.assertEquals(child.getProperties(), new HashMap<>()); // not inherited as expected ??
        Assert.assertEquals(child.incomplete, false); // not inherited as expected
        Assert.assertEquals(child.isIncomplete(), false); // not inherited as expected
        Assert.assertEquals(child.getForegroundIcon(), CharacterIconProvider.CHAR_0020);
        Assert.assertEquals(child.getBackgroundIcon(), DefaultIconProvider.FLAT_CIRCLE);
        Assert.assertEquals(child.getDetectionRegex(), null); // not inherited as expected
        Assert.assertEquals(child.getValidationRegex(), null); // not inherited as expected
        Assert.assertEquals(child.overridenType, null);
        Assert.assertEquals(child.getOverridenType(), null);
    }
}
