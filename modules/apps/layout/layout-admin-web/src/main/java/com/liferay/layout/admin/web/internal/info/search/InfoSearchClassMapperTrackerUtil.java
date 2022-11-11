/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.admin.web.internal.info.search;

import com.liferay.info.search.InfoSearchClassMapperRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = {})
public class InfoSearchClassMapperTrackerUtil {

	public static String getSearchClassName(String className) {
		return _infoSearchClassMapperRegistry.getSearchClassName(className);
	}

	@Reference(unbind = "-")
	protected void setInfoSearchClassMapperRegistry(
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry) {

		_infoSearchClassMapperRegistry = infoSearchClassMapperRegistry;
	}

	private static InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

}