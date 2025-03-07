/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.core.graph.impl;

import java.util.Objects;
import java.util.stream.StreamSupport;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.store.GraphNodeStore;
import org.kie.workbench.common.stunner.core.graph.store.GraphNodeStoreImpl;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

@JsType
public class GraphImpl<C> extends AbstractElement<C> implements Graph<C, Node> {

    @JsIgnore
    private final GraphNodeStore<Node> nodeStore;

    public static <C> GraphImpl<C> build(final String uuid) {
        return new GraphImpl<>(uuid, new GraphNodeStoreImpl());
    }

    public GraphImpl(String uuid,
                     GraphNodeStore<Node> nodeStore) {
        super(uuid);
        this.nodeStore = Objects.requireNonNull(nodeStore, "Parameter named 'nodeStore' should be not null!");
    }

    @Override
    public Node addNode(final Node node) {
        return nodeStore.add(node);
    }

    @Override
    public Node removeNode(final String uuid) {
        return nodeStore.remove(uuid);
    }

    @Override
    public Node getNode(final String uuid) {
        return nodeStore.get(uuid);
    }

    @Override
    public Iterable<Node> nodes() {
        return nodeStore;
    }

    @SuppressWarnings("all")
    public Node[] nodesArray() {
        final Iterable<Node> nodes = nodes();
        return StreamSupport.stream(nodes.spliterator(), true)
                .toArray(Node[]::new);
    }

    @Override
    public void clear() {
        nodeStore.clear();
    }

    @Override
    public Node<C, Edge> asNode() {
        return null;
    }

    @Override
    public Edge<C, Node> asEdge() {
        return null;
    }

    @Override
    public int hashCode() {
        return GraphUtils.computeGraphHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GraphImpl) {
            GraphImpl g = (GraphImpl) o;
            return this.hashCode() == g.hashCode();
        } else {
            return false;
        }
    }
}
