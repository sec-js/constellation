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
package au.gov.asd.tac.constellation.graph.schema.visual.attribute;

import au.gov.asd.tac.constellation.graph.attribute.AbstractObjectAttributeDescription;
import au.gov.asd.tac.constellation.graph.attribute.AttributeDescription;
import au.gov.asd.tac.constellation.graph.schema.visual.attribute.objects.Blaze;
import org.apache.commons.lang3.StringUtils;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author algol
 */
@ServiceProvider(service = AttributeDescription.class)
public final class BlazeAttributeDescription extends AbstractObjectAttributeDescription<Blaze> {

    private static final int DESCRIPTION_VERSION = 1;
    public static final String ATTRIBUTE_NAME = "blaze";
    public static final Class<Blaze> NATIVE_CLASS = Blaze.class;
    public static final Blaze DEFAULT_VALUE = null;

    public BlazeAttributeDescription() {
        super(ATTRIBUTE_NAME, NATIVE_CLASS, DEFAULT_VALUE);
    }

    @Override
    protected Blaze convertFromString(final String string) {
        return StringUtils.isBlank(string) ? getDefault() : Blaze.valueOf(string);
    }

    @Override
    public int getVersion() {
        return DESCRIPTION_VERSION;
    }
}
