/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.sw.definition;

import jsinterop.annotations.JsType;
import org.kie.workbench.common.stunner.client.yaml.mapper.api.annotation.YamlPropertyOrder;

@JsType
@YamlPropertyOrder({"eventDataFilter", "eventRef", "actions"})
public class OnEvent {

    public String[] eventRefs;

    public ActionNode[] actions;

    public EventDataFilter eventDataFilter;

    public OnEvent() {
    }

    public String[] getEventRefs() {
        return eventRefs;
    }

    public void setEventRefs(String[] eventRefs) {
        this.eventRefs = eventRefs;
    }

    public ActionNode[] getActions() {
        return actions;
    }

    public void setActions(ActionNode[] actions) {
        this.actions = actions;
    }

    public EventDataFilter getEventDataFilter() {
        return eventDataFilter;
    }

    public void setEventDataFilter(EventDataFilter eventDataFilter) {
        this.eventDataFilter = eventDataFilter;
    }
}
