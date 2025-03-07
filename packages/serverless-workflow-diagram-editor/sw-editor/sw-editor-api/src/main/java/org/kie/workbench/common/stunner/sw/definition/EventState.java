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
import org.kie.workbench.common.stunner.client.json.mapper.annotation.JSONMapper;
import org.kie.workbench.common.stunner.client.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.workbench.common.stunner.client.yaml.mapper.api.annotation.YamlPropertyOrder;

@JSONMapper
@YAMLMapper
@JsType
@YamlPropertyOrder({"name", "type", "compensatedBy", "stateDataFilter", "timeouts", "end","exclusive", "onEvents", "onErrors", "eventTimeout", "transition", "metadata"})
public class EventState extends State {

    public static final String TYPE_EVENT = "event";

    public Boolean exclusive;

    public OnEvent[] onEvents;

    public EventState() {
        this.type = TYPE_EVENT;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public OnEvent[] getOnEvents() {
        return onEvents;
    }

    public void setOnEvents(OnEvent[] onEvents) {
        this.onEvents = onEvents;
    }
}
