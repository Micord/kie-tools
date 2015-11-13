/*
 * Copyright 2015 JBoss, by Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.ext.widgets.core.client.editors.texteditor;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import org.uberfire.ext.widgets.common.client.ace.AceEditor;
import org.uberfire.ext.widgets.common.client.ace.AceEditorMode;
import org.uberfire.ext.widgets.common.client.ace.AceEditorTheme;
import org.uberfire.ext.widgets.common.client.common.ResizableTextArea;

/**
 * A Text editor
 */
public class TextEditorView extends Composite
        implements
        RequiresResize,
        TextEditorPresenter.View {

    interface TextEditorViewBinder
            extends
            UiBinder<ResizeLayoutPanel, TextEditorView> {

    }

    private static TextEditorViewBinder uiBinder = GWT.create( TextEditorViewBinder.class );

    @UiField
    public AceEditor editor;

    private boolean isDirty = false;

    @PostConstruct
    public void init() {
        initWidget( uiBinder.createAndBindUi( this ) );

        editor.startEditor();
        editor.setTheme( AceEditorTheme.CHROME );
    }

    @Override
    public void setContent( final String content,
                            final AceEditorMode mode) {
        editor.setMode( mode);
        editor.setText( content );
        editor.redisplay();
    }

    @Override
    public String getContent() {
        return editor.getText();
    }

    @Override
    public void setFocus() {
        editor.setFocus();
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public void setReadOnly( final boolean isReadOnly ) {
        editor.setReadOnly( isReadOnly );
    }

    @Override
    public void setDirty( boolean dirty ) {
        isDirty = dirty;
    }

    @Override
    public void onResize() {
        int height = getParent().getOffsetHeight();
        int width = getParent().getOffsetWidth();
        setPixelSize( width,
                      height );
        editor.onResize();
    }

}