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

package org.kie.workbench.common.stunner.core.client.session.command.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.ait.lienzo.client.core.shape.wires.SelectionManager;
import org.kie.workbench.common.stunner.core.client.api.SessionManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.ClipboardControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.SelectionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.keyboard.KeyboardControl.KogitoKeyPress;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasElementsClearEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasClearSelectionEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasSelectionEvent;
import org.kie.workbench.common.stunner.core.client.event.keyboard.KeyboardEvent.Key;
import org.kie.workbench.common.stunner.core.client.session.ClientSession;
import org.kie.workbench.common.stunner.core.client.session.impl.EditorSession;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.Connection;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnector;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

import static org.kie.workbench.common.stunner.core.client.canvas.controls.keyboard.KeysMatcher.doKeysMatch;
import static org.kie.workbench.common.stunner.core.client.event.keyboard.KeyboardEvent.Key.C;
import static org.kie.workbench.common.stunner.core.client.event.keyboard.KeyboardEvent.Key.CONTROL;

/**
 * This session command obtains the selected elements on session and copy the elements to a clipboard.
 */
@Dependent
@Default
public class CopySelectionSessionCommand extends AbstractSelectionAwareSessionCommand<EditorSession> {

    private static Logger LOGGER = Logger.getLogger(CopySelectionSessionCommand.class.getName());

    private final Event<CopySelectionSessionCommandExecutedEvent> commandExecutedEvent;

    private ClipboardControl<Element, AbstractCanvas, ClientSession> clipboardControl;

    public CopySelectionSessionCommand() {
        this(null, null);
    }

    @Inject
    public CopySelectionSessionCommand(final Event<CopySelectionSessionCommandExecutedEvent> commandExecutedEvent, final SessionManager sessionManager) {
        super(true);
        this.commandExecutedEvent = commandExecutedEvent;
        SessionSingletonCommandsFactory.createOrPut(this, sessionManager);
    }

    public static CopySelectionSessionCommand getInstance(SessionManager sessionManager) {

        return SessionSingletonCommandsFactory.getInstanceCopy(null, sessionManager);
    }

    public static CopySelectionSessionCommand getInstance(final Event<CopySelectionSessionCommandExecutedEvent> commandExecutedEvent, SessionManager sessionManager) {
        return SessionSingletonCommandsFactory.getInstanceCopy(commandExecutedEvent, sessionManager);
    }

    @Override
    public void bind(final EditorSession session) {
        super.bind(session);
        session.getKeyboardControl().addKeyShortcutCallback(new KogitoKeyPress(new Key[]{CONTROL, C}, "Edit | Copy selection", () -> {
            if (isEnabled()) {
                execute();
            }
        }));
        session.getKeyboardControl().addKeyShortcutCallback(this::onKeyDownEvent);
        this.clipboardControl = session.getClipboardControl();
    }

    @Override
    public boolean accepts(final ClientSession session) {
        return session instanceof EditorSession;
    }

    protected void onKeyDownEvent(final Key... keys) {
        if (isEnabled()) {
            handleCtrlC(keys);
        }
    }

    private void handleCtrlC(final Key[] keys) {
        if (doKeysMatch(keys, CONTROL, C)) {
            this.execute();
        }
    }

    @Override
    public <V> void execute(final Callback<V> callback) {
        if (getSession() != null && null != getSession().getSelectionControl()) {
            try {
                //for now just copy Nodes not Edges
                final SelectionControl<AbstractCanvasHandler, Element> selectionControl = getSession().getSelectionControl();

                //for now just copy Nodes not Edges
                clipboardControl.set(selectionControl.getSelectedItems().stream()
                                             .map(this::getElement)
                                             .toArray(Element[]::new));
                final Set<String> clipboardNodes =
                        clipboardControl.getElements().stream()
                                .filter(element -> element instanceof Node)
                                .map(Element::getUUID)
                                .collect(Collectors.toSet());

                clipboardControl.getEdgeMap().clear();
                clipboardControl.getEdgeMap().putAll(clipboardControl.getElements().stream()
                                                             .filter(element -> element instanceof Edge)
                                                             .map(edge -> (Edge) edge)
                                                             .collect(Collectors.toList()).stream()
                                                             .filter(edge -> clipboardNodes.contains(edge.getSourceNode().getUUID()) && clipboardNodes.contains(edge.getTargetNode().getUUID()))
                                                             .collect(Collectors.toMap(Edge::getUUID, edge ->
                                                                     clipboardControl.buildNewEdgeClipboard(edge.getSourceNode().getUUID(),
                                                                                                            (Connection) ((ViewConnector) edge.getContent()).getSourceConnection().orElse(null),
                                                                                                            edge.getTargetNode().getUUID(),
                                                                                                            (Connection) ((ViewConnector) edge.getContent()).getTargetConnection().orElse(null))
                                                             )));

                commandExecutedEvent.fire(new CopySelectionSessionCommandExecutedEvent(this,
                                                                                       getSession()));
                callback.onSuccess();

                List<Element> elements = clipboardControl.getElements().stream().filter((el) -> {
                    return GraphUtils.getComputedPosition(el.asNode()).getX() != 0
                            && GraphUtils.getComputedPosition(el.asNode()).getY() != 0;
                }).collect(Collectors.toList());
                //get max and min coordinates to create selection area
                double[] minCoordinates = getCoordinates(elements, false);
                double[] maxCoordinates = getCoordinates(elements, true);
                double width = maxCoordinates[0] - minCoordinates[0];
                double height = maxCoordinates[1] - minCoordinates[1];
                getSelectionManagerInstance().selectionClipboardControlElements(
                        minCoordinates[0], minCoordinates[1], width, height);
            } catch (Exception e) {
                LOGGER.severe("Error on copy selection." + e.getMessage());
                return;
            }
        }
    }

    public static native SelectionManager getSelectionManagerInstance() /*-{
        return window.selectionManagerInstance;
    }-*/;

    private double[] getCoordinates(List<Element> elements, boolean max) {
        List<Double> xCoordinates = elements.stream().map((element) ->
                GraphUtils.getComputedPosition(element.asNode()).getX()).collect(Collectors.toList());
        List<Double> yCoordinates = elements.stream().map((element) ->
                GraphUtils.getComputedPosition(element.asNode()).getY()).collect(Collectors.toList());
        return max ? new double[]{Collections.max(xCoordinates), Collections.max(yCoordinates)}
                : new double[]{Collections.min(xCoordinates), Collections.min(yCoordinates)};
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        clipboardControl = null;
    }

    @Override
    protected void handleCanvasSelectionEvent(final CanvasSelectionEvent event) {
        if (event.getIdentifiers().isEmpty() || onlyCanvasRootSelected(event)) {
            enable(false);
        } else {
            enable(true);
        }
    }

    @Override
    protected void handleCanvasClearSelectionEvent(final CanvasClearSelectionEvent event) {
        enable(false);
    }

    @Override
    protected void handleCanvasElementsClearEvent(final CanvasElementsClearEvent event) {
        enable(false);
    }
}