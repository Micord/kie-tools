/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
 */

package org.uberfire.ext.wires.core.grids.client.widget.grid.renderers.columns.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Text;
import org.uberfire.ext.wires.core.grids.client.model.GridCell;
import org.uberfire.ext.wires.core.grids.client.widget.context.GridBodyCellRenderContext;
import org.uberfire.ext.wires.core.grids.client.widget.grid.renderers.grids.GridRenderer;
import org.uberfire.ext.wires.core.grids.client.widget.grid.renderers.themes.GridRendererTheme;

public class StringColumnRenderer extends BaseGridColumnRenderer<String> {

    @Override
    public Group renderCell(final GridCell<String> cell,
                            final GridBodyCellRenderContext context) {
        if (isToReturnNull(cell)) {
            return null;
        }

        final GridRenderer renderer = context.getRenderer();
        final GridRendererTheme theme = renderer.getTheme();

        Text text;
        String value;

        if (isPlaceHolderToBeShown(cell)) {
            text = theme.getPlaceholderText();
            value = cell.getValue().getPlaceHolder();
        } else {
            text = theme.getBodyText();
            value = cell.getValue() != null ? cell.getValue().getValue() : null;
        }

        return internalRenderCell(cell,
                                  context,
                                  text,
                                  value);
    }
}
