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
package au.gov.asd.tac.constellation.utilities.keyboardshortcut;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Stores selected keyboard shortcut result for the workflow template
 *
 * @author spica
 */
public class KeyboardShortcutSelectionResult {

    private String keyboardShortcut;
    private boolean alreadyAssigned;
    private File exisitngTemplateWithKs;
    private String fileName;
    private Optional<Map.Entry<String, String>> assignedShortcut;

    public KeyboardShortcutSelectionResult(final String keyboardShortcut, final boolean alreadyAssigned,
            final File exisitngTemplateWithKs, final Optional<Map.Entry<String, String>> assignedShortcut) {
        this.keyboardShortcut = keyboardShortcut;
        this.alreadyAssigned = alreadyAssigned;
        this.exisitngTemplateWithKs = exisitngTemplateWithKs;
        this.assignedShortcut = assignedShortcut;
    }

    public KeyboardShortcutSelectionResult() {
    }

    public String getKeyboardShortcut() {
        return keyboardShortcut;
    }

    public boolean isAlreadyAssigned() {
        return alreadyAssigned;
    }

    public File getExisitngTemplateWithKs() {
        return exisitngTemplateWithKs;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setKeyboardShortcut(final String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
    }

    public void setAlreadyAssigned(final boolean alreadyAssigned) {
        this.alreadyAssigned = alreadyAssigned;
    }

    public void setExisitngTemplateWithKs(final File exisitngTemplateWithKs) {
        this.exisitngTemplateWithKs = exisitngTemplateWithKs;
    }

    public Optional<Map.Entry<String, String>> getAssignedShortcut() {
        return assignedShortcut;
    }

    public void setAssignedShortcut(final Optional<Map.Entry<String, String>> assignedShortcut) {
        this.assignedShortcut = assignedShortcut;
    }   
    
}