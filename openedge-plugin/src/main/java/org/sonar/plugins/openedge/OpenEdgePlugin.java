/*
 * OpenEdge plugin for SonarQube
 * Copyright (C) 2013-2016 Riverside Software
 * contact AT riverside DASH software DOT fr
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.openedge;

import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.openedge.colorizer.OpenEdgeColorizerFormat;
import org.sonar.plugins.openedge.colorizer.OpenEdgeDBColorizerFormat;
import org.sonar.plugins.openedge.decorator.CommonDBMetricsDecorator;
import org.sonar.plugins.openedge.decorator.CommonMetricsDecorator;
import org.sonar.plugins.openedge.foundation.OpenEdge;
import org.sonar.plugins.openedge.foundation.OpenEdgeComponents;
import org.sonar.plugins.openedge.foundation.OpenEdgeDB;
import org.sonar.plugins.openedge.foundation.OpenEdgeDBProfile;
import org.sonar.plugins.openedge.foundation.OpenEdgeLicenceRegistrar;
import org.sonar.plugins.openedge.foundation.OpenEdgeMetrics;
import org.sonar.plugins.openedge.foundation.OpenEdgeProfile;
import org.sonar.plugins.openedge.foundation.OpenEdgeRulesDefinition;
import org.sonar.plugins.openedge.foundation.OpenEdgeRulesRegistrar;
import org.sonar.plugins.openedge.foundation.OpenEdgeSettings;
import org.sonar.plugins.openedge.sensor.OpenEdgeDBRulesSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeDBSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeDebugListingSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeListingSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeProparseSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeWarningsSensor;
import org.sonar.plugins.openedge.sensor.OpenEdgeXREFSensor;
import org.sonar.plugins.openedge.ui.CommonMetricsWidget;

public class OpenEdgePlugin implements Plugin {
  private static final String CATEGORY_OPENEDGE = "OpenEdge";
  private static final String SUBCATEGORY_GENERAL = "General";
  private static final String SUBCATEGORY_DEBUG = "Debug";

  public static final String SKIP_PROPARSE_PROPERTY = "sonar.oe.skipProparse";
  public static final String PROPARSE_DEBUG = "sonar.oe.proparse.debug";
  public static final String BINARIES = "sonar.oe.binaries";
  public static final String DLC = "sonar.oe.dlc";
  public static final String PROPATH = "sonar.oe.propath";
  public static final String PROPATH_DLC = "sonar.oe.propath.dlc";
  public static final String DATABASES = "sonar.oe.databases";
  public static final String ALIASES = "sonar.oe.aliases";
  public static final String CPD_DEBUG = "sonar.oe.cpd.debug";
  public static final String CPD_ANNOTATIONS = "sonar.oe.cpd.annotations";
  public static final String SUFFIXES = "sonar.oe.file.suffixes";

  @Override
  public void define(Context context) {
    // Main components
    context.addExtensions(OpenEdge.class, OpenEdgeDB.class, OpenEdgeSettings.class);

    // Profile and rules
    context.addExtensions(OpenEdgeRulesDefinition.class, OpenEdgeRulesRegistrar.class, OpenEdgeLicenceRegistrar.class,
        OpenEdgeProfile.class, OpenEdgeDBProfile.class, OpenEdgeMetrics.class, OpenEdgeComponents.class);

    // UI and code colorizer
    context.addExtensions(CommonMetricsWidget.class, OpenEdgeColorizerFormat.class, OpenEdgeDBColorizerFormat.class);

    // Sensors
    context.addExtensions(OpenEdgeSensor.class, OpenEdgeDBSensor.class, OpenEdgeDebugListingSensor.class,
        OpenEdgeListingSensor.class, OpenEdgeWarningsSensor.class, OpenEdgeXREFSensor.class,
        OpenEdgeProparseSensor.class, OpenEdgeDBRulesSensor.class);

    // Decorators
    context.addExtensions(CommonMetricsDecorator.class, CommonDBMetricsDecorator.class);

    // Properties
    context.addExtension(PropertyDefinition.builder(SKIP_PROPARSE_PROPERTY).name("Skip ProParse step").description(
        "Skip Proparse AST generation and lint rules").type(PropertyType.BOOLEAN).category(
            CATEGORY_OPENEDGE).subCategory(SUBCATEGORY_GENERAL).onQualifiers(Qualifiers.MODULE,
                Qualifiers.PROJECT).defaultValue(Boolean.FALSE.toString()).build());
    context.addExtension(PropertyDefinition.builder(PROPARSE_DEBUG).name("Proparse debug files").description(
        "Generate JPNodeLister debug file in .proparse directory").type(PropertyType.BOOLEAN).category(
            CATEGORY_OPENEDGE).subCategory(SUBCATEGORY_DEBUG).defaultValue(Boolean.FALSE.toString()).onQualifiers(
                Qualifiers.MODULE, Qualifiers.PROJECT).build());
    context.addExtension(PropertyDefinition.builder(CPD_DEBUG).name("CPD debug files").description(
        "Generate CPD tokens listing file").type(PropertyType.BOOLEAN).category(CATEGORY_OPENEDGE).subCategory(
            SUBCATEGORY_DEBUG).defaultValue(Boolean.FALSE.toString()).onQualifiers(Qualifiers.MODULE,
                Qualifiers.PROJECT).build());
    context.addExtension(PropertyDefinition.builder(SUFFIXES).name("File suffixes").description(
        "Comma-separated list of suffixes of OpenEdge files to analyze").type(PropertyType.STRING).defaultValue(
            "").category(CATEGORY_OPENEDGE).subCategory(SUBCATEGORY_GENERAL).onQualifiers(Qualifiers.MODULE,
                Qualifiers.PROJECT).build());
    context.addExtension(PropertyDefinition.builder(CPD_ANNOTATIONS).name("CPD annotations").description(
        "Comma-separated list of annotations disabling CPD").type(PropertyType.STRING).defaultValue(
            "Generated").category(CATEGORY_OPENEDGE).subCategory(SUBCATEGORY_GENERAL).onQualifiers(Qualifiers.MODULE,
                Qualifiers.PROJECT).build());
  }

}
