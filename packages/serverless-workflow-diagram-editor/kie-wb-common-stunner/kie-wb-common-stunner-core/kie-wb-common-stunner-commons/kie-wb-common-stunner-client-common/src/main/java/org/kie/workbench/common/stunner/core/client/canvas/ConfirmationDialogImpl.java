/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.core.client.canvas;

import javax.enterprise.context.ApplicationScoped;

import elemental2.dom.DomGlobal;
import org.uberfire.mvp.Command;

@ApplicationScoped
public class ConfirmationDialogImpl implements ConfirmationDialog {

    @Override
    public void show(String title, String boldDescription, String question, Command onYesAction, Command onNoAction) {
        DomGlobal.console.log("title " + title + " boldDescription" + boldDescription + " question" + question);
    }
}
