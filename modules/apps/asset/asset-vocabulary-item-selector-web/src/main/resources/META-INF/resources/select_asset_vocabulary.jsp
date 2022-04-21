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

<%
SelectAssetVocabularyItemSelectorDisplayContext selectAssetVocabularyItemSelectorDisplayContext = (SelectAssetVocabularyItemSelectorDisplayContext)request.getAttribute(AssetVocabularyItemSelectorWebKeys.SELECT_ASSET_VOCABULARY_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new SelectAssetVocabulariesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectAssetVocabularyItemSelectorDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		id="assetVocabularies"
		searchContainer="<%= selectAssetVocabularyItemSelectorDisplayContext.getAssetVocabularySearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetVocabulary"
			keyProperty="vocabularyId"
			modelVar="assetVocabulary"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"group-id", String.valueOf(assetVocabulary.getGroupId())
				).put(
					"title", assetVocabulary.getTitle(locale)
				).put(
					"uuid", assetVocabulary.getUuid()
				).put(
					"vocabulary-id", String.valueOf(assetVocabulary.getVocabularyId())
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="vocabularies"
			>
				<clay:sticker
					cssClass="bg-light mr-3"
					displayType="dark"
					icon="vocabulary"
				/>

				<b><%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %></b>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-column-text-center"
				name="categories"
				value="<%= String.valueOf(assetVocabulary.getCategoriesCount()) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-ws-nowrap"
				name="creation-date"
				property="createDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>