/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Zeebe Community License 1.0. You may not use this file
 * except in compliance with the Zeebe Community License 1.0.
 */
package io.zeebe.broker.system.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public final class DatabaseCfgTest {

  public final Map<String, String> environment = new HashMap<>();

  @Test
  public void shouldSetColumnFamilyOptionsConfig() {
    // when
    final BrokerCfg cfg = TestConfigReader.readConfig("database-cfg", environment);
    final var zeebeDb = cfg.getData().getDatabase();

    // then
    assertThat(zeebeDb.getColumnFamilyOptions()).containsEntry("foo_foo", "bar");
  }
}
