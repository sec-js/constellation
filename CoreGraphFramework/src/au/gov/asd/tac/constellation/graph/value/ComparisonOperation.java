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
package au.gov.asd.tac.constellation.graph.value;

import au.gov.asd.tac.constellation.graph.value.constants.StringConstant;
import au.gov.asd.tac.constellation.graph.value.readables.BooleanReadable;
import au.gov.asd.tac.constellation.graph.value.readables.DoubleReadable;
import au.gov.asd.tac.constellation.graph.value.readables.FloatReadable;
import au.gov.asd.tac.constellation.graph.value.readables.IntReadable;
import au.gov.asd.tac.constellation.graph.value.readables.LongReadable;
import au.gov.asd.tac.constellation.graph.value.readables.StringReadable;

/**
 *
 * @author sirius
 */
public interface ComparisonOperation {

    boolean execute(final double p1, final double p2);

    boolean execute(final float p1, final float p2);

    boolean execute(final long p1, final long p2);

    boolean execute(final int p1, final int p2);

    boolean execute(final String p1, final String p2);

    default void register(final OperatorRegistry registry) {
        registry.register(DoubleReadable.class, DoubleReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readDouble(), p2.readDouble()));
        registry.register(FloatReadable.class, FloatReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readFloat(), p2.readFloat()));
        registry.register(LongReadable.class, LongReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readLong(), p2.readLong()));
        registry.register(IntReadable.class, IntReadable.class, BooleanReadable.class, (p1, p2) -> () -> execute(p1.readInt(), p2.readInt()));
        registry.register(StringReadable.class, StringReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readString(), p2.readString()));

        registry.register(DoubleReadable.class, StringConstant.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readDouble(), Double.parseDouble(p2.readString())));
        registry.register(FloatReadable.class, StringConstant.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readFloat(), Float.parseFloat(p2.readString())));
        registry.register(LongReadable.class, StringConstant.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readLong(), Long.parseLong(p2.readString())));
        registry.register(IntReadable.class, StringConstant.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readInt(), Integer.parseInt(p2.readString())));
        registry.register(StringReadable.class, StringConstant.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readString(), p2.readString()));

        registry.register(StringConstant.class, DoubleReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(Double.parseDouble(p1.readString()), p2.readDouble()));
        registry.register(StringConstant.class, FloatReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(Float.parseFloat(p1.readString()), p2.readFloat()));
        registry.register(StringConstant.class, LongReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(Long.parseLong(p1.readString()), p2.readLong()));
        registry.register(StringConstant.class, IntReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(Integer.parseInt(p1.readString()), p2.readInt()));
        registry.register(StringConstant.class, StringReadable.class, BooleanReadable.class, (p1, p2)
                -> () -> execute(p1.readString(), p2.readString()));
    }
}
