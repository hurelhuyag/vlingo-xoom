// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.
package io.vlingo.xoom.annotation.model;

import io.vlingo.lattice.model.IdentifiedDomainEvent;

import java.util.UUID;

public class OtherDummyAccepted extends IdentifiedDomainEvent {

    private final UUID eventId;
    public final String name = "";

    public OtherDummyAccepted() {
        this.eventId = UUID.randomUUID();
    }

    @Override
    public String identity() {
        return eventId.toString();
    }

}
