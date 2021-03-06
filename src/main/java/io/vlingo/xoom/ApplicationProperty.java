// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationProperty {

    private static final String XOOM_PREFIX = "VLINGO_XOOM";
    private static final String COMBINATION_PATTERN = "%s.%s";

    public static String readValue(final String key, final Properties properties) {
        final String propertiesValue = retrieveFromProperties(key, properties);
        return propertiesValue != null ? propertiesValue : retrieveFromEnvironment(key);
    }

    public static List<String> readMultipleValues(final String key, final String separator, final Properties properties) {
        final String value = readValue(key, properties);

        if(value == null) {
            return Collections.emptyList();
        }

        return value == null ? Collections.emptyList() : Stream.of(value.split(separator)).collect(Collectors.toList());
    }

    private static String retrieveFromProperties(final String key, final Properties properties) {
        if(!properties.containsKey(key)) {
            return null;
        }
        final String value = properties.get(key).toString().trim();
        return value.isEmpty() ? null : value;
    }

    private static String retrieveFromEnvironment(final String key) {
        final String envKey =
                resolveEnvironmentVariable(key);

        if(!System.getenv().containsKey(envKey)) {
            return null;
        }

        final String value = System.getenv(envKey);

        if(value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }

    private static String resolveEnvironmentVariable(final String key) {
        return String.format(COMBINATION_PATTERN, XOOM_PREFIX, key).replace("\\.", "_");
    }

}
