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
package au.gov.asd.tac.constellation.graph.mergers;

import au.gov.asd.tac.constellation.graph.GraphAttributeMerger;
import au.gov.asd.tac.constellation.graph.GraphElementType;
import au.gov.asd.tac.constellation.graph.GraphWriteMethods;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author sirius
 */
@ServiceProvider(service = GraphAttributeMerger.class)
public class ConcatenatedSetGraphAttributeMerger extends GraphAttributeMerger {

    public static final String ID = "au.gov.asd.tac.constellation.graph.mergers.ConcatinatedSetGraphAttributeMerger";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean mergeAttribute(final GraphWriteMethods graph, final GraphElementType elementType, final int survivingElement, final int mergedElement, final int attribute) {
        final Set<String> elements = new TreeSet<>();

        final String survivingValue = graph.getStringValue(attribute, survivingElement);
        if (survivingValue != null) {
            elements.addAll(Arrays.asList(survivingValue.split(",", -1)));
        }

        final String mergedValue = graph.getStringValue(attribute, mergedElement);
        if (mergedValue != null) {
            elements.addAll(Arrays.asList(mergedValue.split(",", -1)));
        }

        final StringBuilder result = new StringBuilder();
        String separator = "";
        for (final String element : elements) {
            result.append(separator);
            separator = ",";
            result.append(element);
        }

        graph.setStringValue(attribute, survivingElement, result.toString());

        return true;
    }
}
