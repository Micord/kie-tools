/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.uberfire.client.workbench.part;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import org.uberfire.client.mvp.UberView;
import org.uberfire.workbench.model.PartDefinition;

public interface WorkbenchPartPresenter {

    PartDefinition getDefinition();

    void setDefinition(PartDefinition definition);

    View getPartView();

    void setWrappedWidget(IsWidget widget);

    /**
     * Returns the text that a panel may choose to display beside this part's title.
     * @return The title of this part; never null.
     */
    String getTitle();

    /**
     * Sets the text that a panel may choose to display beside this part's title.
     * @param title The title of this part. Null is not permitted.
     */
    void setTitle(String title);

    /**
     * Returns the widget that a panel may choose to display beside this part's title.
     * @return The title decoration widget to use. Null means no title decoration.
     */
    IsWidget getTitleDecoration();

    /**
     * Sets the widget that a panel may choose to display beside this part's title.
     * <p>
     * NOTE: presently, none of the built-in panel types display a part's title decoration.
     * @param titleDecoration The title decoration widget to use. Null is permitted, and means no title decoration.
     */
    void setTitleDecoration(IsWidget titleDecoration);

    String getContextId();

    void setContextId(String contextId);

    public interface View
            extends
            UberView<WorkbenchPartPresenter>,
            RequiresResize {

        WorkbenchPartPresenter getPresenter();

        IsWidget getWrappedWidget();

        void setWrappedWidget(IsWidget widget);
    }
}
