/*******************************************************************************
 * Copyright (c) 2015 Gilles Querret
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Gilles Querret - initial API and implementation and/or initial documentation
 *******************************************************************************/ 
package org.prorefactor.core.unittest.util;

import org.prorefactor.refactor.settings.ProparseSettings;

public class UnitTestProparseSettings extends ProparseSettings {
  public UnitTestProparseSettings() {
    super(true, false, false, true, 3, "", "");
  }
}
