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

package com.liferay.layout.utility.page.validator;

import com.liferay.layout.utility.page.exception.UtilityPageTemplateValidatorException;
import com.liferay.portal.json.validator.JSONValidator;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Bárbara Cabrera
 */
public class UtilityPageTemplateValidator {

	public static void validateUtilityPageTemplate(
			String utilityPageTemplateJSON)
		throws UtilityPageTemplateValidatorException {

		if (Validator.isNull(utilityPageTemplateJSON)) {
			return;
		}

		try {
			_jsonValidator.validate(utilityPageTemplateJSON);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new UtilityPageTemplateValidatorException(
				jsonValidatorException.getMessage(), jsonValidatorException);
		}
	}

	private static final JSONValidator _jsonValidator = new JSONValidator(
		UtilityPageTemplateValidator.class.getResourceAsStream(
			"dependencies/utility_page_template_json_schema.json"));

}