/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.client.lienzo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.StyleInjector;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.kie.workbench.common.stunner.client.lienzo.canvas.patternfly.PatternFlyBundle;

@ApplicationScoped
@EntryPoint
@Bundle("resources/i18n/StunnerLienzoConstants.properties")
public class StunnerLienzoCore {

    /**
     * It's really important to set the <code>ImageSelectionMode</code> to the
     * value <code>SELECT_BOUNDS</code> due to performance reasons (image rendering on different browsers).
     * Stunner does not use image filters neither requires the image to be drawn in the
     * selection context2D, so it uses the value <code>SELECT_BOUNDS</code> as default.
     * Also it's being used due to huge differences on the resulting performance when
     * rendering the images into the contexts.
     */
    @PostConstruct
    public void init() {

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.bootstrapcss().getText());

        ScriptInjector.fromString(PatternFlyBundle.INSTANCE.jquery().getText())
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();

        ScriptInjector.fromString(PatternFlyBundle.INSTANCE.bootstrapjs().getText())
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.bootstrapselect().getText());

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.animate().getText());

        ScriptInjector.fromString(PatternFlyBundle.INSTANCE.gwtbootstrap3().getText())
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.fontawesome().getText());

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.patternflycss().getText());

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.patternflyadditions().getText());

        ScriptInjector.fromString(PatternFlyBundle.INSTANCE.patternflyjs().getText())
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();

        StyleInjector.injectStylesheet(
                PatternFlyBundle.INSTANCE.uberfirepatternfly().getText());

        LienzoCore.get().setDefaultImageSelectionMode(ImageSelectionMode.SELECT_BOUNDS);
    }
}
