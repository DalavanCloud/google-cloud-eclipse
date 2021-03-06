/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.eclipse.appengine.localserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.cloud.tools.appengine.configuration.RunConfiguration;
import com.google.cloud.tools.eclipse.util.status.StatusUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;    
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests detection of conflicts between two {@link RunConfiguration}.
 */
public class RunConfigConflictTest {
  
  private List<Path> services = new ArrayList<>();
  
  @Test
  public void testSameConflict() {
    RunConfiguration config = RunConfiguration.builder(services).build();
    IStatus status = LocalAppEngineServerLaunchConfigurationDelegate.checkConflicts(config, config,
        StatusUtil.multi(RunConfigConflictTest.class, "Conflict"));
    assertFalse(status.isOK());
    assertThat(status, Matchers.instanceOf(MultiStatus.class));
    IStatus[] children = ((MultiStatus) status).getChildren();
    assertEquals(1, children.length);
    assertTrue(children[0].getMessage().startsWith("server port: "));
  }

  @Test
  public void testNoConflicts() {
    RunConfiguration.Builder builder = RunConfiguration.builder(services);
    builder.port(0); // random allocation
    builder.adminPort(0); // random allocation
    builder.storagePath(Paths.get("/foo/bar"));
    RunConfiguration config2 = RunConfiguration.builder(services).build();
    IStatus status = LocalAppEngineServerLaunchConfigurationDelegate.checkConflicts(builder.build(),
        config2, StatusUtil.multi(RunConfigConflictTest.class, "Conflict"));
    assertTrue(status.isOK());
  }
}
