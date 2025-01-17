<%--
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
--%>

<%@ include file="/init.jsp" %>

<div class="form-group form-group-sm">
	<label class="control-label <%= fragmentCollectionFilterCategoryDisplayContext.isShowLabel() ? "" : "sr-only" %>">
		<%= fragmentCollectionFilterCategoryDisplayContext.getLabel() %>
	</label>

	<div>
		<button
		class="dropdown-toggle form-control form-control-select form-control-sm text-left w-100" disabled="disabled"><liferay-ui:message key="select" /></button
	>
		<react:component
			module="js/SelectCategory.es"
			props="<%= fragmentCollectionFilterCategoryDisplayContext.getProps() %>"
		/>
	</div>
</div>