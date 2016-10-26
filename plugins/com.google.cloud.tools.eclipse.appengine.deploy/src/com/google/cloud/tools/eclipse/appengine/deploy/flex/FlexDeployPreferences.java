/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.eclipse.appengine.deploy.flex;

import com.google.common.annotations.VisibleForTesting;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class FlexDeployPreferences {
  public static final String PREFERENCE_STORE_QUALIFIER = "com.google.cloud.tools.eclipse.appengine.deploy.flex";
  public static final FlexDeployPreferences DEFAULT;

  static final String PREF_APP_ENGINE_DIRECTORY = "appengine.config.folder";
  static final String PREF_DOCKER_DIRECTORY = "docker.file.location";
  static final String PREF_USE_DEPLOYMENT_PREFERENCES = "use.deployment.preferences";

  private IEclipsePreferences preferenceStore;

  static {
    DEFAULT = new FlexDeployPreferences(FlexDeployPreferenceInitializer.getDefaultPreferences());
  }
  
  public FlexDeployPreferences(IProject project) {
    this(new ProjectScope(project).getNode(PREFERENCE_STORE_QUALIFIER));
  }

  @VisibleForTesting
  FlexDeployPreferences(IEclipsePreferences preferences) {
    preferenceStore = preferences;
  }

  public void save() throws BackingStoreException {
    preferenceStore.flush();
  }

  public String getAppEngineDirectory() {
    return preferenceStore.get(PREF_APP_ENGINE_DIRECTORY,
        FlexDeployPreferenceInitializer.DEFAULT_APP_ENGINE_DIRECTORY);
  }

  public void setAppEngineDirectory(String AppEngineDirectory) {
    preferenceStore.put(PREF_APP_ENGINE_DIRECTORY, AppEngineDirectory);
  }
  
  public String getDockerDirectory() {
    return preferenceStore.get(PREF_DOCKER_DIRECTORY,
        FlexDeployPreferenceInitializer.DEFAULT_DOCKER_DIRECTORY);
  }

  public void setDockerDirectory(String DockerDirectory) {
    preferenceStore.put(PREF_DOCKER_DIRECTORY, DockerDirectory);
  }

  public boolean getUseDeploymentPreferences() {
    return preferenceStore.getBoolean(PREF_USE_DEPLOYMENT_PREFERENCES,
        FlexDeployPreferenceInitializer.DEFAULT_USE_DEPLOYMENT_PREFERENCES);
  }

  public void setUseDeploymentPreferences(boolean useDeploymentPreferences) {
    preferenceStore.putBoolean(PREF_USE_DEPLOYMENT_PREFERENCES, useDeploymentPreferences);
  }

}