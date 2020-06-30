// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.
package io.vlingo.xoom.annotation.initializer;

import static io.vlingo.xoom.annotation.initializer.AddressFactory.Type.BASIC;

/**
 * This test class ensures that auto-generated code, from
 * the {@link Xoom} compile-time annotation, is successfully
 * compiled.
 *
 * @author Danilo Ambrosio
 */
@Xoom(name = "annotated-boot", addressFactory = @AddressFactory(type = BASIC))
public class AnnotatedBootTest {

}
