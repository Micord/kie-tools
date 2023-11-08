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

package org.kie.workbench.common.stunner.bpmn.definition.property.subProcess.execution;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.SLADueDate;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.BaseSubprocessTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.IsAsync;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "isAsync")
public class EventSubprocessExecutionSet extends BaseSubprocessTaskExecutionSet {

    public EventSubprocessExecutionSet() {
        this(new IsAsync(),
             new SLADueDate());
    }

    public EventSubprocessExecutionSet(final @MapsTo("isAsync") IsAsync isAsync,
                                       final @MapsTo("slaDueDate") SLADueDate slaDueDate) {
        super(isAsync, slaDueDate);
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EventSubprocessExecutionSet) {
            EventSubprocessExecutionSet other = (EventSubprocessExecutionSet) o;
            return super.equals(other);
        }
        return false;
    }
}