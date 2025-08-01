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

import au.gov.asd.tac.constellation.utilities.SystemUtilities;
import au.gov.asd.tac.constellation.utilities.icon.UserInterfaceIconProvider;
import java.io.File;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import au.gov.asd.tac.constellation.utilities.color.ConstellationColor;
import au.gov.asd.tac.constellation.utilities.javafx.JavafxStyleManager;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Customised TextInputDialog to read pressed keyboard shortcut with options
 * buttons
 *
 * @author spica
 */
public class TextInputDialogWithKeybordShortcut extends Dialog<String> {

    /* ************************************************************************
     *
     * Fields
     *
     **************************************************************************/
    private final DialogPane dialogPane;    
    private final String title;
    private final String headerText;
    private boolean isFirstTime = true;
    private Window parent = null;
    private Stage stage = null;
    private final TextField textField;
    private final Label keyboardShortcutLabel;
    private final Button keyboardShortcutButton;
    private final Label shorcutWarningLabel;
    private final Label shorcutWarningIconLabel;
    private final KeyboardShortcutSelectionResult keyboardShortcutSelectionResult;
    private final Label label;
    private final GridPane grid;
    private final Button addButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private static final PseudoClass HEADER_PSEUDO_CLASS = PseudoClass.getPseudoClass("header"); 
    private static final Logger LOGGER = Logger.getLogger(TextInputDialogWithKeybordShortcut.class.getName());



    /* ************************************************************************
     *
     * Constructors
     *
     **************************************************************************/
    /**
     * Creates a new TextInputDialog without a default value entered into the
     * dialog {@link TextField}.
     */
    public TextInputDialogWithKeybordShortcut(final File preferenceDirectory, final Optional<String> ks, final Optional<Window> parentWindow) {
        this("",  "", "", preferenceDirectory, ks, parentWindow);
    }

    /**
     * Creates a new TextInputDialog with the default value entered into the
     * dialog {@link TextField}.
     *
     * @param defaultValue the default value entered into the dialog
     */
    public TextInputDialogWithKeybordShortcut(@NamedArg("defaultValue") final String defaultValue, final String title, final String headerText, 
            final File preferenceDirectory, final Optional<String> ks, final Optional<Window> parentWindow) {
        this.title =  title;
        this.headerText = headerText;
        parent =  parentWindow.orElse(null);
        
        dialogPane = new DialogPane();
        dialogPane.pseudoClassStateChanged(HEADER_PSEUDO_CLASS, true);
        dialogPane.getStylesheets().addAll(JavafxStyleManager.getMainStyleSheet());
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        // -- label
        label = createContentLabel(dialogPane.getContentText());
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        label.textProperty().bind(dialogPane.contentTextProperty());
        
        // -- textfield
        this.textField = new TextField(defaultValue);
        this.textField.setMaxWidth(Double.MAX_VALUE);
        this.textField.setMinWidth(250);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        GridPane.setFillWidth(textField, true);
        
        // Leyboard shortcut label. Showing existing/ptoposed shortcut assigned to the template
        keyboardShortcutLabel = createLabel();
        keyboardShortcutLabel.setMinWidth(Control.USE_PREF_SIZE);
        keyboardShortcutLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        if (ks.isPresent()) {
            keyboardShortcutLabel.setStyle(" -fx-text-alignment: center; -fx-font-size: 13px; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: #909090;");
        } else {
            keyboardShortcutLabel.setStyle(" -fx-text-alignment: center; -fx-font-size: 13px;");
        }
        keyboardShortcutLabel.setPadding(new Insets(2, 10, 2, 10));
        keyboardShortcutLabel.setGraphic(null);
        keyboardShortcutLabel.setTooltip(null);
        keyboardShortcutLabel.setContentDisplay(ContentDisplay.RIGHT);
        
        keyboardShortcutButton = new Button("Shortcut");
        
        shorcutWarningLabel = createLabel();
        shorcutWarningLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        shorcutWarningLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        shorcutWarningLabel.setMinHeight(Region.USE_PREF_SIZE);
        shorcutWarningLabel.setStyle(" -fx-text-alignment: center; -fx-font-size: 11.5px; -fx-text-fill: " + ConstellationColor.DARK_ORANGE.getHtmlColor() + ";");        
        shorcutWarningLabel.setPadding(new Insets(5, 2, 5, 2));
        
        shorcutWarningIconLabel = createLabel();
        shorcutWarningIconLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);        

        final ImageView warningImage = new ImageView(UserInterfaceIconProvider.WARNING.buildImage(20, new java.awt.Color(255, 128, 0)));
        final Tooltip warningToolTip = new Tooltip("This shortcut is currently assigned to another template");
                
        final ImageView errorImage = new ImageView(UserInterfaceIconProvider.ERROR.buildImage(20, ConstellationColor.CHERRY.getJavaColor()));
        final Tooltip errorToolTip = new Tooltip();
        
        
        keyboardShortcutSelectionResult = new KeyboardShortcutSelectionResult();
        
        if (ks.isPresent()) {
            keyboardShortcutLabel.setText(ks.get());
            keyboardShortcutSelectionResult.setKeyboardShortcut(ks.get());
        }
        
        keyboardShortcutButton.setOnAction(e -> {//NOSONAR            
            stage.setAlwaysOnTop(false);
            final RecordKeyboardShortcut rk = new RecordKeyboardShortcut(stage.getScene().getWindow());
            final Optional<KeyboardShortcutSelectionResult> keyboardShortcut = rk.start(preferenceDirectory);
            stage.setAlwaysOnTop(true);
            
            if (keyboardShortcut.isPresent()) {
                final KeyboardShortcutSelectionResult ksResult = keyboardShortcut.get();
                keyboardShortcutLabel.setStyle(" -fx-text-alignment: center; -fx-font-size: 13px; -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: #909090;");
                keyboardShortcutLabel.setText(ksResult.getKeyboardShortcut());
                keyboardShortcutSelectionResult.setKeyboardShortcut(ksResult.getKeyboardShortcut());
                
                if (ksResult.getAssignedShortcut().isPresent()) {                    
                    shorcutWarningLabel.setStyle(" -fx-text-alignment: left; -fx-font-size: 11.5px; -fx-text-fill: " + ConstellationColor.CHERRY.getHtmlColor() + ";");
                    shorcutWarningLabel.setText(String.format(RecordKeyboardShortcut.KEYBOARD_SHORTCUT_EXISTS_WITHIN_APP_ALERT_ERROR_MSG_FORMAT, ksResult.getKeyboardShortcut()));
                    errorToolTip.setText(String.format(RecordKeyboardShortcut.KEYBOARD_SHORTCUT_EXISTS_WITHIN_APP_ALERT_TOOLTIP_MSG_FORMAT, ksResult.getKeyboardShortcut(), 
                            ksResult.getAssignedShortcut().get().getValue()));
                    keyboardShortcutSelectionResult.setAssignedShortcut(ksResult.getAssignedShortcut());                    
                    shorcutWarningIconLabel.setGraphic(errorImage);
                    shorcutWarningIconLabel.setTooltip(errorToolTip);
                    
                    //disable OK button
                    dialogPane.lookupButton(ButtonType.OK).setDisable(true);

                } else if (ksResult.isAlreadyAssigned() && ksResult.getExisitngTemplateWithKs() != null) {
                    shorcutWarningLabel.setStyle(" -fx-text-alignment: center; -fx-font-size: 11.5px; -fx-text-fill: " + ConstellationColor.DARK_ORANGE.getHtmlColor() + ";");
                    shorcutWarningLabel.setText(String.format(RecordKeyboardShortcut.KEYBOARD_SHORTCUT_EXISTS_ALERT_ERROR_MSG_FORMAT, ksResult.getKeyboardShortcut()));
                    keyboardShortcutSelectionResult.setAlreadyAssigned(true);
                    keyboardShortcutSelectionResult.setExisitngTemplateWithKs(ksResult.getExisitngTemplateWithKs());
                    shorcutWarningIconLabel.setGraphic(warningImage);
                    shorcutWarningIconLabel.setTooltip(warningToolTip);
                    dialogPane.lookupButton(ButtonType.OK).setDisable(false);                    
                } else {
                    shorcutWarningLabel.setText(null);
                    keyboardShortcutSelectionResult.setAlreadyAssigned(false);
                    keyboardShortcutSelectionResult.setExisitngTemplateWithKs(null);
                    shorcutWarningIconLabel.setGraphic(null);
                    shorcutWarningIconLabel.setTooltip(null);
                    dialogPane.lookupButton(ButtonType.OK).setDisable(false);                    
                }
                
            }

        });       
               
        
        ((Button) dialogPane.lookupButton(ButtonType.CANCEL)).setOnAction(e -> {
            if (stage != null) {
                stage.close();
            }
        });
        
        ((Button) dialogPane.lookupButton(ButtonType.OK)).setOnAction(e -> {
            if (stage != null) {
                keyboardShortcutSelectionResult.setFileName(textField.getText());
                stage.close();
            }
        });
        
        this.grid = new GridPane();
        this.grid.setHgap(20);
        this.grid.setVgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);
        
        dialogPane.contentTextProperty().addListener(o -> updateGrid());

        dialogPane.getStyleClass().add("text-input-dialog");
        
        dialogPane.setMinHeight(270);
        dialogPane.setMinWidth(320);
        
        updateGrid();
    }

    private static Label createLabel() {
        final Label label = new Label();
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(360);
        return label;
    }
    
    private static Label createContentLabel(final String text) {
        final Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(360);
        return label;
    }
    
    /**
     * For unittest
     */
    public void showPopUp() {
        showPopUp(null);
    }
    
    /**
     * Instantiate stage for the pop up and set event handler to close it when
     * consty closes
     */
    public void showPopUp(final JDialog hiddenDialog) {
        if (isFirstTime) {
            if (parent != null) {
                parent.setOnCloseRequest(event -> {
                    addButton.setDisable(true);
                    cancelButton.setDisable(true);
                    closePopUp();
                });
            }
            
            stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parent);
            stage.setAlwaysOnTop(true);
            stage.setTitle(title);
            
            dialogPane.setHeaderText(headerText);

            final Scene s = new Scene(dialogPane);
            s.getStylesheets().addAll(JavafxStyleManager.getMainStyleSheet());
            stage.setScene(s);

            isFirstTime = false;
        }
        
        if(Objects.nonNull(hiddenDialog)) {
            hiddenDialog.setModal(true);
            hiddenDialog.setUndecorated(true);
            hiddenDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
        
        stage.setTitle(title);
        dialogPane.setHeaderText(headerText);

        final double xOffset = SystemUtilities.getMainframeWidth() / 2 - 60;
        final double yOffset = SystemUtilities.getMainframeHeight() / 2 - 60;
        stage.setX(SystemUtilities.getMainframeXPos() + xOffset);
        stage.setY(SystemUtilities.getMainframeYPos() + yOffset);

        try {
            if(Objects.nonNull(hiddenDialog)) {
                SwingUtilities.invokeLater(() -> hiddenDialog.setVisible(true));   
            }
            
            if (!stage.isShowing()) {
                stage.showAndWait();
            }
        } catch (final IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "Error opening popup", e);
        } finally {
            if(Objects.nonNull(hiddenDialog)) {
                hiddenDialog.dispose();
            }
        }

    }
    public void closePopUp() {
        if (stage != null) {
            stage.close();
        }
    }
    
    public KeyboardShortcutSelectionResult getKeyboardShortcutSelectionResult() {
        return keyboardShortcutSelectionResult;
    }
    
    /* ************************************************************************
     *
     * Private Implementation
     *
     **************************************************************************/
    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(textField, 0, 0, 7, 1);
        grid.add(keyboardShortcutButton, 0, 1, 2, 1);
        grid.add(keyboardShortcutLabel, 2, 1, 4, 1);
        grid.add(shorcutWarningIconLabel, 6, 1, 1, 1);
        grid.add(shorcutWarningLabel, 0, 2, 7, 1);
              
        dialogPane.setContent(grid);

        Platform.runLater(() -> textField.requestFocus());
    }
}
