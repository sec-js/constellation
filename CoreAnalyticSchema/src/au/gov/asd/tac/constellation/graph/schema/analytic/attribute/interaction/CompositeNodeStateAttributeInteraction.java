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
package au.gov.asd.tac.constellation.graph.schema.analytic.attribute.interaction;

import au.gov.asd.tac.constellation.graph.attribute.StringAttributeDescription;
import au.gov.asd.tac.constellation.graph.attribute.interaction.AbstractAttributeInteraction;
import au.gov.asd.tac.constellation.graph.attribute.interaction.AttributeValueTranslator;
import au.gov.asd.tac.constellation.graph.attribute.interaction.ValueValidator;
import au.gov.asd.tac.constellation.graph.schema.analytic.attribute.CompositeNodeStateAttributeDescription;
import au.gov.asd.tac.constellation.graph.schema.analytic.attribute.objects.CompositeNodeState;
import au.gov.asd.tac.constellation.graph.schema.analytic.attribute.objects.CompositeStatus;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.Strings;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author twilight_sparkle
 */
@ServiceProvider(service = AbstractAttributeInteraction.class)
public class CompositeNodeStateAttributeInteraction extends AbstractAttributeInteraction<CompositeNodeState> {

    @Override
    public String getDataType() {
        return CompositeNodeStateAttributeDescription.ATTRIBUTE_NAME;
    }

    @Override
    public String getDisplayText(final Object value) {
        if (value == null) {
            return null;
        }
        final CompositeNodeState state = (CompositeNodeState) value;
        final CompositeStatus status = state.getStatus();
        return switch (status) {
            case NOT_A_COMPOSITE -> "";
            case IS_A_COMPOSITE ->
                String.format("%s comprising %d nodes.", status.getCompositeName(), state.getNumberOfNodes());
            default ->
                String.format("%s with %d other node%s.", status.getCompositeName(), state.getNumberOfNodes() - 1, state.getNumberOfNodes() == 1 ? "" : "s");
        };
    }

    @Override
    public List<String> getPreferredEditTypes() {
        return Arrays.asList(StringAttributeDescription.ATTRIBUTE_NAME);
    }

    @Override
    public ValueValidator<CompositeNodeState> fromEditValidator(final String dataType) {
        return ValueValidator.getAlwaysFailValidator("Composite Node States are uneditable.");
    }

    @Override
    public AttributeValueTranslator toEditTranslator(final String dataType) {
        if (Strings.CS.equals(dataType, StringAttributeDescription.ATTRIBUTE_NAME)) {
            return v -> v == null ? v : getDisplayText(v);
        }
        return super.toEditTranslator(dataType);
    }

    @Override
    protected Class<CompositeNodeState> getValueType() {
        return CompositeNodeState.class;
    }
}
