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

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.admin.web.internal.servlet.taglib.util.LayoutUtilityPageEntryActionDropdownItemsProvider;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryVerticalCard extends BaseVerticalCard {

	public LayoutUtilityPageEntryVerticalCard(
		LayoutUtilityPageEntry layoutUtilityPageEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(null, renderRequest, null);

		_layoutUtilityPageEntry = layoutUtilityPageEntry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		LayoutUtilityPageEntryActionDropdownItemsProvider
			layoutUtilityPageEntryActionDropdownItemsProvider =
				new LayoutUtilityPageEntryActionDropdownItemsProvider(
					_layoutUtilityPageEntry, _renderRequest, _renderResponse);

		return layoutUtilityPageEntryActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getStickerIcon() {
		if (_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getStickerStyle() {
		return "primary";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutUtilityPageEntry.getName());
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}