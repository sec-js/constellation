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
package au.gov.asd.tac.constellation.utilities.datastructure;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A data-structure for storing a pair of related values.
 *
 * @author cygnus_x-1
 *
 * @param <F> the type of the first object in the tuple
 * @param <S> the type of the second object in the tuple
 */
public class Tuple<F, S> implements Serializable, Comparable<Tuple<F, S>> {

    private static final long serialVersionUID = 1L;

    private F first;
    private S second;

    public Tuple(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Convenience method for creating an appropriately typed tuple.
     *
     * @param <A> the type of the first object in the tuple
     * @param <B> the type of the second object in the tuple
     * @param first the first object in the tuple
     * @param second the second object in the tuple
     * @return a tuple that is templated with the types of first and second
     */
    public static <A, B> Tuple<A, B> create(final A first, final B second) {
        return new Tuple<>(first, second);
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(final F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(final S second) {
        this.second = second;
    }

    public Stream<Object> stream() {
        return Stream.of(first, second);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        return Objects.equals(other.first, first)
                && Objects.equals(other.second, second);
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode())
                ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)",
                first == null ? "[null]" : first.toString(), second == null ? "[null]" : second.toString());
    }

    /**
     * Compare the Tuple similar to in Python, by comparing the first followed
     * by second. If the object is an instance of {@link Comparable} then use
     * the built in compareTo, else convert them to a {@link String} and compare
     * them.
     */
    @Override
    /**
     * We can suppress unchecked warnings for this method. They are generated by
     * the {Comparable} casts, however we manually check these are comparable
     * prior to the cast.
     */
    @SuppressWarnings("unchecked")
    public int compareTo(final Tuple<F, S> o) {
        int compare;

        if (first instanceof Comparable compFirst && o.first instanceof Comparable compOFirst) {
            // compare the first using Comparable
            compare = compFirst.compareTo(compOFirst);
            if (compare != 0) {
                return compare;
            }
        } else {
            // compare the first using strings
            compare = first.toString().compareTo(o.first.toString());
            if (compare != 0) {
                return compare;
            }
        }

        if (second instanceof Comparable compSecond && o.second instanceof Comparable compOSecond) {
            // compare the second using Comparable
            compare = compSecond.compareTo(compOSecond);
            if (compare != 0) {
                return compare;
            }
        }

        // compare the second using strings
        return second.toString().compareTo(o.second.toString());
    }
}
