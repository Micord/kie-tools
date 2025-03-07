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

import { setDiagnosticsOptions } from "monaco-yaml";
import { JSONSchema7 } from "json-schema";
import { YARD_SCHEMA } from "@kie-tools/yard-language-service/dist/assets/schemas";

export function initYamlSchemaDiagnostics() {
  setDiagnosticsOptions({
    hover: true,
    completion: true,
    validate: true,
    format: true,
    schemas: [
      {
        uri: "https://start.kubesmarts.org/schemas/yard/draft-07/YaRD-schema.json",
        fileMatch: ["*"],
        schema: YARD_SCHEMA as JSONSchema7,
      },
    ],
  });
}
