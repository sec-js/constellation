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
package au.gov.asd.tac.constellation.plugins.importexport.jdbc;

import au.gov.asd.tac.constellation.plugins.importexport.ConfigurationPane;
import au.gov.asd.tac.constellation.plugins.importexport.ImportPane;
import au.gov.asd.tac.constellation.plugins.importexport.ImportTopComponent;
import javafx.application.Platform;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * The JDBC Import Top Component. This class provides the Import JDBC window and
 * handles all interactions with the graph.
 *
 * @author aldebaran30701
 */
@TopComponent.Description(
        preferredID = "JDBCImportTopComponent",
        iconBase = "au/gov/asd/tac/constellation/plugins/importexport/jdbc/resources/jdbc_import.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(
        mode = "explorer",
        openAtStartup = false)
@ActionID(
        category = "Window",
        id = "au.gov.asd.tac.constellation.plugins.importexport.jdbc.JDBCImportTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/File/Import", position = 0),
    @ActionReference(path = "Toolbars/File", position = 6)})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ImportJDBCFileAction",
        preferredID = "JDBCImportTopComponent")
@Messages({
    "CTL_ImportJDBCFileAction=From Database...",
    "HINT_ImportJDBCFile=Import from Database"})
public final class JDBCImportTopComponent extends ImportTopComponent {

    private final JDBCImportPane jdbcImportPane;
    private static final String HELP_TEXT = """
                                            1. Add the relevant JDBC Driver via 'Manage Connections' -> 'Drivers' tab.
                                            2. Add the Connection details in 'Manage Connections' -> 'Connections' tab. If the 'Username' and 'Password' are not required, leave them blank.
                                            3. Select the connection from the 'Connection' drop-down in the main Import window.
                                            4. Enter 'Username' and 'Password' if the connection requires them.
                                            5. Enter the SQL 'Query' and Click the 'Query' button to retrieve data.
                                            6. Select your 'Destination' graph.
                                            7. Drag and drop attributes onto columns.
                                            8. Right click an attribute for more options.
                                            9. Click the 'Import' button to add data to your graph.
                                            
                                            HINTS:
                                            * See all supported attributes with 'Options > Show all schema attributes'.
                                            * Filter in the Configuration Pane by adding searches of the form <column_name>=="<search text>".* E.g. first_name=="Nick"
                                            * To filter Attributes, start typing in the Attributes Filter.""";

    final JDBCImportController controller = new JDBCImportController();
    final ConfigurationPane configurationPane = new ConfigurationPane(controller, HELP_TEXT);
    final JDBCSourcePane sourcePane = new JDBCSourcePane(controller);

    public JDBCImportTopComponent() {
        super();
        setName(Bundle.CTL_ImportJDBCFileAction());
        setToolTipText(Bundle.HINT_ImportJDBCFile());
        initComponents();
        jdbcImportPane = new JDBCImportPane(this, controller, configurationPane, sourcePane);
        controller.setImportPane(jdbcImportPane);
        initContent();
    }

    @Override
    protected String createStyle() {
        return null;
    }

    @Override
    protected ImportPane createContent() {
        return jdbcImportPane;
    }

    @Override
    protected void preparePane() {
        Platform.runLater(() -> {
            jdbcImportPane.getSourcePane().updateDestinationGraphCombo();
            jdbcImportPane.updateSourcePane();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
