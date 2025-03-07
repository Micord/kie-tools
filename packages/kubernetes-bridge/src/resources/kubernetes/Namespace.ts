/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
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

import { HttpMethod } from "../../fetch/FetchConstants";
import { ResourceFetch, ResourceFetchArgs } from "../../fetch/ResourceFetch";
import { Namespace } from "kubernetes-models/v1";

export class GetNamespace extends ResourceFetch {
  constructor(protected args: ResourceFetchArgs) {
    super(args);
  }

  public method(): HttpMethod {
    return HttpMethod.GET;
  }

  public endpoint(): string {
    return `/api/${Namespace.apiVersion}/namespaces/${this.args.namespace}`;
  }
}
