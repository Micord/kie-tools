/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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
import { MiningSchema, NaiveBayesModel } from "../../model/pmml4_4";
import { LOCAL_TRANSFORMATIONS } from "./LocalTransformations";
import { MINING_SCHEMA } from "./MiningSchema";
import { MODEL_EXPLANATION } from "./ModelExplanation";
import { MODEL_STATS } from "./ModelStats";
import { MODEL_VERIFICATION } from "./ModelVerification";
import { OUTPUT } from "./Output";
import { TARGETS } from "./Targets";

/**
 * Basic unmarshalling to support https://issues.redhat.com/browse/FAI-235
 */
export const NAIVE_BAYES_MODEL: string = `
elements.elements[(name = "NaiveBayesModel")] ~> $map(function($v, $i) {
  $merge([
    $naiveBayesModelFactory(),
    $v.attributes,
    {
      "_type": $v.name
    },
    {
      ${MINING_SCHEMA}, 
      ${OUTPUT},
      ${MODEL_STATS},
      ${MODEL_EXPLANATION},
      ${MODEL_VERIFICATION},
      ${TARGETS},
      ${LOCAL_TRANSFORMATIONS}
    }
  ])
})`;

//Construction of a NaiveBayesModel data-structure can be peformed in the JSONata mapping however
//TypeScript's instanceof operator relies on the applicable constructor function having been
//called and therefore we must instantiate the object itself.
export function naiveBayesModelFactory(): NaiveBayesModel {
  return new NaiveBayesModel({
    MiningSchema: new MiningSchema({ MiningField: [] }),
    BayesInputs: { BayesInput: [] },
    BayesOutput: { TargetValueCounts: { TargetValueCount: [] }, fieldName: "" },
    threshold: 0,
    functionName: "regression",
  });
}
