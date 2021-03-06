// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.codegen.template.storage;

import io.vlingo.xoom.codegen.template.TemplateData;
import io.vlingo.xoom.codegen.template.TemplateParameters;
import io.vlingo.xoom.codegen.template.TemplateStandard;

import static io.vlingo.xoom.codegen.template.TemplateParameter.QUERIES_NAME;
import static io.vlingo.xoom.codegen.template.TemplateStandard.QUERIES;

public class QueriesTemplateData extends TemplateData {

    private final String aggregateProtocol;
    private final TemplateParameters parameters;

    protected QueriesTemplateData(final String aggregateProtocol,
                                  final TemplateParameters parameters) {
        this.aggregateProtocol = aggregateProtocol;
        this.parameters =
                parameters.and(QUERIES_NAME,
                        QUERIES.resolveClassname(aggregateProtocol));
    }

    @Override
    public TemplateParameters parameters() {
        return parameters;
    }

    @Override
    public TemplateStandard standard() {
        return QUERIES;
    }

    @Override
    public String filename() {
        return standard().resolveFilename(aggregateProtocol, parameters);
    }

}
