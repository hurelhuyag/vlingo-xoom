// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.codegen.template.dataobject;

import io.vlingo.xoom.codegen.content.Content;
import io.vlingo.xoom.codegen.content.ContentQuery;
import io.vlingo.xoom.codegen.parameter.CodeGenerationParameter;
import io.vlingo.xoom.codegen.template.TemplateData;
import io.vlingo.xoom.codegen.template.TemplateParameters;
import io.vlingo.xoom.codegen.template.TemplateStandard;
import io.vlingo.xoom.codegen.template.model.StateFieldDetail;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vlingo.xoom.codegen.template.TemplateParameter.*;
import static io.vlingo.xoom.codegen.template.TemplateStandard.AGGREGATE_STATE;
import static io.vlingo.xoom.codegen.template.TemplateStandard.DATA_OBJECT;
import static io.vlingo.xoom.codegen.template.model.AggregateArgumentsFormat.SIGNATURE_DECLARATION;
import static io.vlingo.xoom.codegen.template.model.AggregateFieldsFormat.MEMBER_DECLARATION;
import static io.vlingo.xoom.codegen.template.model.AggregateFieldsFormat.STATE_BASED_ASSIGNMENT;

public class DataObjectTemplateData extends TemplateData {

    private final static String PACKAGE_PATTERN = "%s.%s";
    private final static String INFRA_PACKAGE_NAME = "infrastructure";

    private final String protocolName;
    private final TemplateParameters parameters;

    public static List<TemplateData> from(final String basePackage,
                                          final Stream<CodeGenerationParameter> aggregates,
                                          final List<Content> contents) {
        final Function<CodeGenerationParameter, TemplateData> mapper =
                aggregate -> new DataObjectTemplateData(basePackage, aggregate, contents);

        return aggregates.map(mapper).collect(Collectors.toList());
    }

    private DataObjectTemplateData(final String basePackage,
                                   final CodeGenerationParameter aggregate,
                                   final List<Content> contents) {
        this.protocolName = aggregate.value;
        this.parameters = loadParameters(resolvePackage(basePackage), aggregate, contents);
    }

    private TemplateParameters loadParameters(final String packageName,
                                              final CodeGenerationParameter aggregate,
                                              final List<Content> contents) {
        final String stateName = AGGREGATE_STATE.resolveClassname(aggregate.value);

        final String stateQualifiedClassName =
                ContentQuery.findFullyQualifiedClassName(AGGREGATE_STATE, stateName, contents);

        final String dataName = DATA_OBJECT.resolveClassname(protocolName);

        return TemplateParameters.with(PACKAGE_NAME, packageName)
                .and(STATE_NAME, stateName).and(DATA_OBJECT_NAME, dataName)
                .and(MEMBERS, MEMBER_DECLARATION.format(aggregate))
                .and(MEMBERS_ASSIGNMENT, STATE_BASED_ASSIGNMENT.format(aggregate))
                .and(DATA_OBJECT_QUALIFIED_NAME, packageName.concat(".").concat(dataName))
                .and(CONSTRUCTOR_PARAMETERS, SIGNATURE_DECLARATION.format(aggregate))
                .and(STATE_QUALIFIED_CLASS_NAME, stateQualifiedClassName)
                .and(DEFAULT_ID, StateFieldDetail.resolveDefaultValue(aggregate, "id"));
    }

    private String resolvePackage(final String basePackage) {
        return String.format(PACKAGE_PATTERN, basePackage,
                INFRA_PACKAGE_NAME).toLowerCase();
    }

    @Override
    public String filename() {
        return standard().resolveFilename(protocolName, parameters);
    }

    @Override
    public TemplateParameters parameters() {
        return parameters;
    }

    @Override
    public TemplateStandard standard() {
        return DATA_OBJECT;
    }

}
