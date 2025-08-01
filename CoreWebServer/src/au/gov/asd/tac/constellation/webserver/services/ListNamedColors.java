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
package au.gov.asd.tac.constellation.webserver.services;

import au.gov.asd.tac.constellation.plugins.parameters.PluginParameters;
import au.gov.asd.tac.constellation.utilities.color.ConstellationColor;
import au.gov.asd.tac.constellation.webserver.restapi.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.openide.util.lookup.ServiceProvider;

/**
 * List the colors that CONSTELLATION knows the names of, and their values in
 * HTML format.
 *
 * @author algol
 */
@ServiceProvider(service = RestService.class)
public class ListNamedColors extends RestService {

    private static final String NAME = "list_named_colors";
    private static final String EXAMPLE_RESPONSES_PATH = "listNamedColorsExample";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "List the colors that CONSTELLATION knows the names of, and their values in HTML format.";
    }

    @Override
    public String[] getTags() {
        return new String[]{"color"};
    }

    @Override
    public void callService(final PluginParameters parameters, final InputStream in, final OutputStream out) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode root = mapper.createObjectNode();
        ConstellationColor.NAMED_COLOR_LIST
                .forEach(cocol -> root.put(cocol.getName(), cocol.getHtmlColor()));

        mapper.writeValue(out, root);
    }
    
    @Override
    public String getExampleResponsesPath() {
        return EXAMPLE_RESPONSES_PATH;
    }
}
