/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { CapabilityChannelApi } from "@kie-tools-core/backend/dist/channel-api";
import { NotificationsChannelApi } from "@kie-tools-core/notifications/dist/api";
import { I18nChannelApi } from "@kie-tools-core/i18n/dist/api";
import { WorkspaceChannelApi } from "@kie-tools-core/workspace/dist/api";
import { EditorContent } from "./EditorContent";
import { StateControlCommand } from "./StateControlCommand";
import { EditorTheme } from "./EditorTheme";
import { SharedValueProvider } from "@kie-tools-core/envelope-bus/dist/api";

export interface KogitoEditorChannelApi
  extends I18nChannelApi,
    CapabilityChannelApi,
    WorkspaceChannelApi,
    NotificationsChannelApi {
  kogitoEditor_ready(): void;
  kogitoEditor_setContentError(content: EditorContent): void;
  kogitoEditor_stateControlCommandUpdate(command: StateControlCommand): void;
  kogitoEditor_contentRequest(): Promise<EditorContent>;
  kogitoEditor_theme(): SharedValueProvider<EditorTheme>;
}
