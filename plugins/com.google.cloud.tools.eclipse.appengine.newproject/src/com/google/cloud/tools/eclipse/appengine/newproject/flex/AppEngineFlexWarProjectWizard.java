/*
 * Copyright 2017 Google Inc.
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

package com.google.cloud.tools.eclipse.appengine.newproject.flex;

import com.google.cloud.tools.eclipse.appengine.libraries.repository.ILibraryRepositoryService;
import com.google.cloud.tools.eclipse.appengine.newproject.AppEngineProjectConfig;
import com.google.cloud.tools.eclipse.appengine.newproject.AppEngineProjectWizard;
import com.google.cloud.tools.eclipse.appengine.newproject.AppEngineWizardPage;
import com.google.cloud.tools.eclipse.appengine.newproject.CreateAppEngineProject;
import com.google.cloud.tools.eclipse.appengine.newproject.Messages;
import com.google.cloud.tools.eclipse.usagetracker.AnalyticsEvents;
import com.google.cloud.tools.eclipse.usagetracker.AnalyticsPingManager;
import javax.inject.Inject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class AppEngineFlexWarProjectWizard extends AppEngineProjectWizard {
  @Inject
  private ILibraryRepositoryService repositoryService;

  AppEngineFlexWarProjectWizard() {
    setWindowTitle(Messages.getString("new.app.engine.flex.war.project"));
  }

  @Override
  protected AppEngineWizardPage createWizardPage() {
    AnalyticsPingManager.getInstance().sendPing(
        AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD,
        AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD_TYPE,
        AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD_TYPE_FLEX, getShell());

    return new AppEngineFlexWarWizardPage();
  }

  @Override
  protected IStatus validateDependencies() {
    return Status.OK_STATUS;
  }

  @Override
  protected CreateAppEngineProject getAppEngineProjectCreationOperation(
      AppEngineProjectConfig config, IAdaptable uiInfoAdapter) {
    return new CreateAppEngineFlexWtpProject(config, uiInfoAdapter, repositoryService);
  }

  @Override
  public boolean performFinish() {
    boolean accepted = super.performFinish();
    if (accepted) {
      AnalyticsPingManager.getInstance().sendPing(
          AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD_COMPLETE,
          AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD_TYPE,
          AnalyticsEvents.APP_ENGINE_NEW_PROJECT_WIZARD_TYPE_FLEX);
    }
    return accepted;
  }
}