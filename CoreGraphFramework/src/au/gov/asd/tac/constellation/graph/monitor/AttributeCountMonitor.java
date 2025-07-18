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
package au.gov.asd.tac.constellation.graph.monitor;

import au.gov.asd.tac.constellation.graph.GraphReadMethods;

/**
 * A AttributeMonitor monitors the addition and removal of attributes on the
 * graph.
 *
 * @author sirius
 */
public class AttributeCountMonitor extends GlobalMonitor {

    public AttributeCountMonitor() {
    }

    public AttributeCountMonitor(final GraphReadMethods graph) {
        update(graph);
    }

    @Override
    protected long readModificationCounter(final GraphReadMethods graph) {
        return graph.getAttributeModificationCounter();
    }

    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder();
        out.append("AttributeCountMonitor[");
        out.append(",transition=").append(transition);
        out.append(",modificationCounter=").append(modificationCounter);
        out.append("]");
        return out.toString();
    }
}
