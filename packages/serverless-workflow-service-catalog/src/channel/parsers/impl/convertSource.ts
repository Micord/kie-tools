/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
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

import { SwfCatalogSourceType, SwfServiceCatalogFunctionSource, SwfServiceCatalogServiceSource } from "../../../api";

export function convertSource(catalogSource: SwfServiceCatalogServiceSource): SwfServiceCatalogFunctionSource {
  if (catalogSource?.type === SwfCatalogSourceType.LOCAL_FS) {
    return {
      type: catalogSource.type,
      serviceFileAbsolutePath: catalogSource.absoluteFilePath,
    };
  }

  return {
    type: catalogSource.type,
    registry: catalogSource.registry,
    serviceId: catalogSource.id,
  };
}
