/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.dmn.client.editors.expressions.commands;

import java.util.Optional;

import com.google.gwtmockito.GwtMockitoTestRunner;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.dmn.api.definition.HasExpression;
import org.kie.workbench.common.dmn.api.definition.model.Expression;
import org.kie.workbench.common.dmn.api.definition.model.List;
import org.kie.workbench.common.dmn.client.editors.expressions.jsinterop.props.ListProps;
import org.kie.workbench.common.dmn.client.editors.types.common.ItemDefinitionUtils;
import org.kie.workbench.common.dmn.client.widgets.grid.model.ExpressionEditorChanged;
import org.mockito.Mock;
import org.uberfire.mocks.EventSourceMock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class FillListExpressionCommandTest extends TestCase {

    private FillListExpressionCommand command;

    @Mock
    private HasExpression hasExpression;

    @Mock
    private ListProps expressionProps;

    @Mock
    private EventSourceMock<ExpressionEditorChanged> editorSelectedEvent;

    @Mock
    private List existingExpression;

    @Mock
    private ItemDefinitionUtils itemDefinitionUtils;

    @Before
    public void setup() {

        when(hasExpression.getExpression()).thenReturn(existingExpression);

        command = spy(new FillListExpressionCommand(hasExpression,
                                                    expressionProps,
                                                    editorSelectedEvent,
                                                    "nodeUUID",
                                                    itemDefinitionUtils,
                                                    Optional.empty()));
        doNothing().when(command).fill(any(), any());
    }

    @Test
    public void testFill() {
        command.fill();
        verify(command).fill(existingExpression, expressionProps);
    }

    @Test
    public void testGetNewExpression() {
        final Expression newExpression = command.getNewExpression();
        assertTrue(newExpression instanceof List);
    }

    @Test
    public void testIsCurrentExpressionOfTheSameType_WhenItIs() {
        assertTrue(command.isCurrentExpressionOfTheSameType());
    }

    @Test
    public void testIsCurrentExpressionOfTheSameType_WhenItIsNot() {
        when(hasExpression.getExpression()).thenReturn(mock(Expression.class));
        assertFalse(command.isCurrentExpressionOfTheSameType());
    }
}
