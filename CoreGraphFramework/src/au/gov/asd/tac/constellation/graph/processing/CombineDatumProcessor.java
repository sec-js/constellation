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
import java.util.List;

/**
 * A CombineDatumProcessor processes an input datum object through the specified
 * ordered list of {@link DatumProcessor} objects in series.
 *
 * @param <T> The type of the input datum to be processed.
 * @param <U> The type of the object used to hold any parameters used for
 * processing.
 *
 * @author capella
 */
public abstract class CombineDatumProcessor<T, U> implements DatumProcessor<T, U> {

    protected List<DatumProcessor<T, U>> processors = new ArrayList<>();

    /**
     * Construct a CombineDatumProcessor with a list of {@link DatumProcessor}
     * objects.
     *
     * @param processors An array of {@link DatumProcessor}.
     */
    protected CombineDatumProcessor(final DatumProcessor<T, U>[] processors) {
        this.processors.addAll(Arrays.asList(processors));
    }

    /**
     * Get the ordered list of {@link DatumProcessor} objects.
     *
     * @return An {@link List} of {@link DatumProcessor} objects.
     */
    public List<DatumProcessor<T, U>> getProcessors() {
        return processors;
    }

    @Override
    public void process(final U parameters, final T input, final RecordStore output) throws ProcessingException {
        for (final DatumProcessor<T, U> rowProcessor : processors) {
            rowProcessor.process(parameters, input, output);
        }
    }
}
