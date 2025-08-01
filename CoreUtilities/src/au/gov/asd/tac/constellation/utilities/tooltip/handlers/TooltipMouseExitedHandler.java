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
package au.gov.asd.tac.constellation.utilities.tooltip.handlers;

import au.gov.asd.tac.constellation.utilities.tooltip.TooltipPane;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Creates a event handler for when the mouse exits an input control
 *
 * @author aldebaran30701
 */
public class TooltipMouseExitedHandler implements EventHandler<Event> {

    private final TooltipPane tooltipPane;
    
    public TooltipMouseExitedHandler(final TooltipPane tooltipPane) {
        this.tooltipPane = tooltipPane;
    }
    
    @Override
    public void handle(final Event event) {
        if (tooltipPane.isEnabled()) {
            tooltipPane.hideTooltip();
        }
    }
}
