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

describe("Both BPMN DMN.", () => {
  before("Visit page", () => {
    cy.visit("/both-bpmn-dmn");
    cy.loadEditors(["both-bpmn", "both-dmn"]);
  });

  it("Test Load File And View", () => {
    cy.uploadFile("call centre drd.dmn", "both-dmn");
    cy.viewFile("call centre drd.dmn", "both-dmn");

    cy.uploadFile("process-string.bpmn", "both-bpmn");
    cy.viewFile("process-string.bpmn", "both-bpmn");

    // Check dmn file loaded correctly
    cy.editor("both-dmn").find("[data-field='kie-palette']").should("be.visible");

    cy.editor("both-dmn")
      .ouiaId("collapsed-docks-bar", "collapsed-docks-bar-E", { timeout: 10000 })
      .should("be.visible");

    cy.editor("both-dmn")
      .ouiaId("collapsed-docks-bar", "collapsed-docks-bar-E")
      .find("button")
      .first()
      .should("be.visible")
      .click(); // open DecisionNavigator

    cy.editor("both-dmn")
      .ouiaId("expanded-docks-bar", "expanded-docks-bar-E")
      .should("be.visible")
      .within(($navigator) => {
        cy.get("[data-field='item'][title='DRG']")
          .should("be.visible")
          .siblings("[data-field='item']")
          .should("have.length", 4)
          .then(($items) => {
            expect($items.eq(0)).to.have.attr("title", "call centre drd");
            expect($items.eq(0)).not.to.have.class("editable");
            expect($items.eq(1)).to.have.attr("title", "DRDs");
            expect($items.eq(1)).not.to.have.class("editable");
            expect($items.eq(2)).to.have.attr("title", "call centre");
            expect($items.eq(2)).to.have.class("editable");
            expect($items.eq(3)).to.have.attr("title", "preconditions");
            expect($items.eq(3)).to.have.class("editable");
          });
      });

    // Check BPMN loaded correctly
    cy.editor("both-bpmn").find("[data-title='Explore Diagram']").should("be.visible").click();

    cy.editor("both-bpmn")
      .find("[data-field='explorerPanelBody']")
      .wait(1000)
      .scrollIntoView()
      .should("be.visible")
      .find("a.gwt-Anchor")
      .should("have.length", 7)
      .then(($links) => {
        expect($links.eq(0)).to.contain.text("Process string");
        expect($links.eq(1)).to.contain.text("Start");
        expect($links.eq(2)).to.contain.text("Exclusive");
        expect($links.eq(3)).to.contain.text("Process the String");
        expect($links.eq(4)).to.contain.text("Log Error");
        expect($links.eq(5)).to.contain.text("End");
        expect($links.eq(6)).to.contain.text("End Error");
      });
  });
});
