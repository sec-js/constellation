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
package au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.infomap;

import au.gov.asd.tac.constellation.graph.GraphReadMethods;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.Node;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.NodeBase;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.NodeFactoryBase;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.io.Config;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.traits.FlowBase;
import au.gov.asd.tac.constellation.plugins.algorithms.clustering.infomap.traits.FlowDirectedNonDetailedBalanceWithTeleportation;

/**
 *
 * @author algol
 */
public class InfomapDirectedUnrecordedTeleportation extends InfomapGreedy {

    public InfomapDirectedUnrecordedTeleportation(final Config config, final GraphReadMethods rg) {
        super(config, new NodeFactoryDirectedUnrecordedTeleportation(), rg);
    }

    @Override
    protected boolean isDirected() {
        return true;
    }

    @Override
    protected boolean hasDetailedBalance() {
        return false;
    }

    @Override
    protected InfomapBase getNewInfomapInstance(final Config config, final GraphReadMethods rg) {
        return new InfomapDirectedUnrecordedTeleportation(config, rg);
    }

    public static class NodeFactoryDirectedUnrecordedTeleportation implements NodeFactoryBase {

        @Override
        public NodeBase createNode() {
            return createNode("", 0, 0);
        }

        @Override
        public NodeBase createNode(final String name, final double flow, final double teleportWeight) {
            final FlowDirectedNonDetailedBalanceWithTeleportation data = new FlowDirectedNonDetailedBalanceWithTeleportation(flow, teleportWeight);
            return new Node(name, data);
        }

        @Override
        public NodeBase createNode(final NodeBase node) {
            return new Node(node.getName(), (FlowDirectedNonDetailedBalanceWithTeleportation) ((Node) node).getData());
        }

        @Override
        public NodeBase createNode(final FlowBase data) {
            return new Node((FlowDirectedNonDetailedBalanceWithTeleportation) data);
        }

        @Override
        public FlowBase createFlow() {
            return new FlowDirectedNonDetailedBalanceWithTeleportation();
        }
    }
}
