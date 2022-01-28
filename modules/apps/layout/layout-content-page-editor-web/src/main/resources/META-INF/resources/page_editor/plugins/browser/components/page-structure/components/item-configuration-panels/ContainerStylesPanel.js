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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../../../../../app/config/index';
import {useSelector} from '../../../../../../app/contexts/StoreContext';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop-types/index';
import {CommonStyles} from './CommonStyles';

export function ContainerStylesPanel({item}) {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const {availableViewportSizes} = config;

	const containerConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const viewportSize = availableViewportSizes[selectedViewportSize];

	return (
		<>
			<p className="page-editor__row-styles-panel__viewport-label">
				<ClayIcon className="mr-2" symbol={viewportSize.icon} />

				{viewportSize.label}
			</p>

			<CommonStyles
				commonStylesValues={containerConfig.styles}
				item={item}
			/>
		</>
	);
}

ContainerStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			styles: PropTypes.object,
		}),
	}),
};
