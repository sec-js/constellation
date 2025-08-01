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
package au.gov.asd.tac.constellation.graph.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Graph RecordStore
 *
 * @author sirius
 */
public class GraphRecordStore extends TabularRecordStore {

    private final Map<String, Object[][]> typedRecords = new LinkedHashMap<>();

    /**
     * Construct a GraphRecordStore.
     */
    public GraphRecordStore() {
        super(true);
    }

    /**
     * Construct a GraphRecordStore with the optional ability to cache keys and
     * values within the RecordStore for more efficient lookup.
     *
     * @param cacheStrings A flag indicating whether or not to create a cache
     * for {@link String} values to make lookup faster.
     */
    public GraphRecordStore(final boolean cacheStrings) {
        super(cacheStrings);
    }

    /**
     * Get the values associated with a column in this TabularRecordStore.
     *
     * @param key A {@link String value} representing a key in this
     * TabularRecordStore. This can be thought of as a column name.
     * @return An array of arrays of {@link Object} representing a set of values
     * from this TabularRecordStore. This data structure is such that arrays are
     * minimally created, effectively conserving memory.
     */
    @Override
    protected Object[][] getColumn(final String key) {
        final Object[][] values = typedRecords.get(key);
        return values != null ? values : records.get(key);
    }

    @Override
    protected void createColumn(final String key, final Object[][] values) {
        final String typedKey;
        final String untypedKey;
        final int typeIndex = key.indexOf('<');
        if (typeIndex == -1) {
            untypedKey = key;
            typedKey = key + "<string>";
        } else {
            typedKey = key;
            untypedKey = key.substring(0, typeIndex);
        }
        typedRecords.put(typedKey, values);
        records.put(untypedKey, values);
    }

    @Override
    public void set(final int newRecord, String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        if (newRecord < 0 || newRecord >= size) {
            throw new IllegalArgumentException("Invalid record: " + newRecord);
        }

        if (cache != null) {
            if (value != null) {
                final String unique = cache.get(value);
                if (unique != null) {
                    value = unique;
                } else {
                    cache.put(value, value);
                }
            }

            final String unique = cache.get(key);
            if (unique != null) {
                key = unique;
            } else {
                cache.put(key, key);
            }
        }

        Object[][] values = getColumn(key);

        // If this is the first time this key has been set
        if (values == null) {
            values = new Object[capacity >>> BATCH_BITS][];
            createColumn(key, values);

            // If there is not enough capacity to hold this record
        } else if (values.length <= newRecord >>> BATCH_BITS) {
            values = Arrays.copyOf(values, capacity >>> BATCH_BITS);
            createColumn(key, values);
        }

        Object[] batch = values[newRecord >>> BATCH_BITS];
        if (batch == null) {
            values[newRecord >>> BATCH_BITS] = batch = new Object[BATCH_SIZE];
        }
        batch[newRecord & BATCH_MASK] = value == null ? NULL : value;
    }

    @Override
    public List<String> values(final int newRecord) {
        final List<String> values = new ArrayList<>(typedRecords.size());
        for (final Object[][] v : typedRecords.values()) {
            values.add(TabularRecordStore.getValue(v, newRecord));
        }
        return values;
    }

    /**
     * Append another RecordStore to this GraphRecordStore.
     *
     * @param recordStore A GraphRecordStore to be appended to this
     * GraphRecordStore.
     */
    @Override
    public void add(final RecordStore recordStore) {
        if (recordStore instanceof GraphRecordStore graphRecordStore) {
            for (int records = 0; records < graphRecordStore.size(); records++) {
                final int newRecord = add();
                for (final String key : graphRecordStore.keysWithType()) {
                    final String value = graphRecordStore.get(records, key);
                    if (value != null) {
                        set(newRecord, key, value);
                    }
                }
            }
        } else {
            super.add();
        }
    }

    /**
     * Return the keys which contain the type
     * <p>
     * For example Source.Identifier&lt;String&gt;
     *
     * @return Return the keys which contain the type
     */
    public List<String> keysWithType() {
        return new ArrayList<>(typedRecords.keySet());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.typedRecords);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final GraphRecordStore other = (GraphRecordStore) obj;
        if (this.typedRecords.size() != other.typedRecords.size()) {
            return false;
        }

        // now that the size is the same, it will mean that looping through the keys will not miss anything
        for (final Map.Entry<String, Object[][]> entry : this.typedRecords.entrySet()) {
            if (!other.typedRecords.containsKey(entry.getKey())) {
                return false;
            }

            for (int records = 0; records < size; records++) {
                for (final Entry<String, Object[][]> e : this.typedRecords.entrySet()) {
                    if (!TabularRecordStore.hasValue(e.getValue(), records) || !TabularRecordStore.hasValue(other.typedRecords.get(e.getKey()), records)) {
                        return false;
                    } else if (TabularRecordStore.getValue(e.getValue(), records) == null && TabularRecordStore.getValue(other.typedRecords.get(e.getKey()), records) == null) {
                        // continue
                    } else if (TabularRecordStore.getValue(e.getValue(), records) == null && TabularRecordStore.getValue(other.typedRecords.get(e.getKey()), records) != null) {
                        return false;
                    } else if (!TabularRecordStore.getValue(e.getValue(), records).equals(TabularRecordStore.getValue(other.typedRecords.get(e.getKey()), records))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String toStringVerbose() {
        final StringBuilder out = new StringBuilder();
        for (int records = 0; records < size; records++) {
            boolean first = true;
            for (final Entry<String, Object[][]> e : typedRecords.entrySet()) {
                if (TabularRecordStore.hasValue(e.getValue(), records)) {
                    if (!first) {
                        out.append(", ");
                    } else {
                        first = false;
                    }
                    out.append(e.getKey());
                    out.append(" = ");
                    out.append(TabularRecordStore.getValue(e.getValue(), records));
                }
            }
            out.append('\n');
        }
        return out.toString();
    }
}
