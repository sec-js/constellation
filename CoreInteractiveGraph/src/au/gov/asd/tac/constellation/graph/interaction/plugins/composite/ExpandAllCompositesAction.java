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
package au.gov.asd.tac.constellation.graph.interaction.plugins.composite;

import au.gov.asd.tac.constellation.graph.interaction.InteractiveGraphPluginRegistry;
import au.gov.asd.tac.constellation.graph.node.GraphNode;
import au.gov.asd.tac.constellation.graph.node.plugins.SimplePluginAction;
import au.gov.asd.tac.constellation.utilities.icon.UserInterfaceIconProvider;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Action;
import javax.swing.JButton;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

/**
 * Action for expanding all composites. It is accessed as an icon on
 * Constellation's graph sidebar.
 *
 * @see ExpandAllCompositesPlugin
 * @author twilight_sparkle
 */
@Messages("CTL_ExpandAllCompositeAction=Expand all Composites")
public class ExpandAllCompositesAction extends SimplePluginAction implements Presenter.Toolbar {
    
    public ExpandAllCompositesAction(final GraphNode context) {
        super(context, InteractiveGraphPluginRegistry.EXPAND_ALL_COMPOSITES);
        
        putValue(Action.SMALL_ICON, UserInterfaceIconProvider.EXPAND.buildIcon(16, Color.BLACK));
        putValue(Action.SHORT_DESCRIPTION, "Expand all Composites");
        putValue(Action.SELECTED_KEY, true);
    }

    @Override
    public Component getToolbarPresenter() {
        return new JButton(this);
    }
}
