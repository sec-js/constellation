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
package au.gov.asd.tac.constellation.visual.opengl.utilities.glyphs;

import au.gov.asd.tac.constellation.utilities.graphics.FloatArray;
import au.gov.asd.tac.constellation.utilities.graphics.IntArray;
import au.gov.asd.tac.constellation.visual.opengl.utilities.SharedDrawable;

/**
 * Glyph stream used when buffering Node Labels.
 *
 * @author Nova
 */
public class NodeGlyphStream implements GlyphManager.GlyphStream {

    private final FloatArray currentFloats;
    private final IntArray currentInts;

    public NodeGlyphStream() {
        this.currentFloats = new FloatArray();
        this.currentInts = new IntArray();
    }

    @Override
    public void addGlyph(final int glyphPosition, final float x, final float y, final GlyphStreamContext streamContext) {
        if (streamContext instanceof NodeGlyphStreamContext context) {
            currentFloats.add(glyphPosition, x, y, context.visibility);
            currentInts.add(context.currentNodeID, context.totalScale, context.labelNumber, 0);
        } else {
            throw new IllegalArgumentException("Provided context lacks Node information, please use a NodeGlyphStreamContext");
        }
    }

    @Override
    public void newLine(float width, final GlyphStreamContext streamContext) {
        if (streamContext instanceof NodeGlyphStreamContext context) {
            currentFloats.add(SharedDrawable.getLabelBackgroundGlyphPosition(), -width / 2.0F - 0.2F, 0.0F, streamContext.visibility);
            currentInts.add(context.currentNodeID, streamContext.totalScale, streamContext.labelNumber, 0);
        } else {
            throw new IllegalArgumentException("Provided context lacks Node information, please use a NodeGlyphStreamContext");
        }
    }

    public FloatArray getCurrentFloats() {
        return currentFloats;
    }

    public IntArray getCurrentInts() {
        return currentInts;
    }

    public void trimToSize() {
        currentFloats.trimToSize();
        currentInts.trimToSize();
    }

}
