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
package au.gov.asd.tac.constellation.graph.value.types.strings;

import au.gov.asd.tac.constellation.graph.value.ValueStore;
import java.util.Arrays;

/**
 *
 * @author sirius
 */
public class StringValueStore implements ValueStore<StringValue> {

    private static final String[] EMPTY_VALUES = new String[0];

    private String[] values;

    public StringValueStore() {
        values = EMPTY_VALUES;
    }

    public StringValueStore(final String[] values) {
        this.values = values;
    }

    @Override
    public StringValue createValue() {
        return new StringValue();
    }

    @Override
    public int getCapacity() {
        return values.length;
    }

    @Override
    public void setCapacity(final int capacity) {
        values = Arrays.copyOf(values, capacity);
    }

    @Override
    public void write(final int id, final StringValue value) {
        values[id] = value.readString();
    }

    @Override
    public void read(final int id, final StringValue value) {
        value.writeString(values[id]);
    }
}
