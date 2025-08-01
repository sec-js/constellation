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
package au.gov.asd.tac.constellation.graph.value.readables;

import au.gov.asd.tac.constellation.graph.value.Updatable;

/**
 *
 * @author sirius
 */
public interface ShortReadable extends IntReadable, Updatable {

    short readShort();

    @Override
    default int readInt() {
        return readShort();
    }

    @Override
    default long readLong() {
        return readShort();
    }

    @Override
    default float readFloat() {
        return readShort();
    }

    @Override
    default double readDouble() {
        return readShort();
    }

    @Override
    default void update() {
        readShort();
    }
}
