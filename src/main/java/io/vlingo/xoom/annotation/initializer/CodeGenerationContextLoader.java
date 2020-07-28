// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.annotation.initializer;

import io.vlingo.xoom.codegen.CodeGenerationContext;
import io.vlingo.xoom.codegen.content.TypeBasedContentLoader;
import io.vlingo.xoom.codegen.file.InternalTargetPathLocator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerationContextLoader {

    private final String basePackage;
    private final TypeElement bootstrapClass;
    private final TypeElement persistenceSetupClass;
    private final ProcessingEnvironment environment;

    public static CodeGenerationContext from(final String basePackage,
                                             final Element bootstrapClass,
                                             final Element persistenceSetupClass,
                                             final ProcessingEnvironment environment) {
        return new CodeGenerationContextLoader(basePackage, bootstrapClass,
                persistenceSetupClass, environment).load();
    }

    public CodeGenerationContextLoader(final String basePackage,
                                       final Element bootstrapClass,
                                       final Element persistenceSetupClass,
                                       final ProcessingEnvironment environment) {
        this.environment = environment;
        this.basePackage = basePackage;
        this.bootstrapClass = (TypeElement) bootstrapClass;
        this.persistenceSetupClass = (TypeElement) persistenceSetupClass;
    }

    public CodeGenerationContext load() {
        final String targetFolder =
                InternalTargetPathLocator.find(environment);

        final CodeGenerationParameterResolver parameterResolver =
                CodeGenerationParameterResolver.from(basePackage, bootstrapClass,
                        persistenceSetupClass, environment);

        return CodeGenerationContext.generateInternallyTo(targetFolder)
                .with(resolveContentLoaders()).on(parameterResolver.resolve());
    }

    private List<TypeBasedContentLoader> resolveContentLoaders() {
        if(persistenceSetupClass == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(new AggregateProtocolContentLoader(persistenceSetupClass, environment),
                new DomainEventContentLoader(persistenceSetupClass, environment),
                new StateContentLoader(persistenceSetupClass, environment),
                new RestResourceContentLoader(bootstrapClass, environment));
    }

}