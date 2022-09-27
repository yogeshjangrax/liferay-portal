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

package com.liferay.object.internal.model.listener;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.Attribute;
import com.liferay.portal.security.audit.event.generators.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(service = ModelListener.class)
public class ObjectRelationshipModelListener
	extends BaseModelListener<ObjectRelationship> {

	@Override
	public void onBeforeCreate(ObjectRelationship objectRelationship)
		throws ModelListenerException {

		_route(EventTypes.ADD, objectRelationship);
	}

	@Override
	public void onBeforeRemove(ObjectRelationship objectRelationship)
		throws ModelListenerException {

		_route(EventTypes.DELETE, objectRelationship);
	}

	@Override
	public void onBeforeUpdate(
			ObjectRelationship originalObjectRelationship,
			ObjectRelationship objectRelationship)
		throws ModelListenerException {

		try {
			_auditRouter.route(
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.UPDATE, ObjectRelationship.class.getName(),
					objectRelationship.getObjectRelationshipId(),
					_getModifiedAttributes(
						originalObjectRelationship, objectRelationship)));
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		ObjectRelationship originalObjectRelationship,
		ObjectRelationship objectRelationship) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			objectRelationship, originalObjectRelationship);

		attributesBuilder.add("deletionType");
		attributesBuilder.add("labelMap");

		return attributesBuilder.getAttributes();
	}

	private void _route(String eventType, ObjectRelationship objectRelationship)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, ObjectRelationship.class.getName(),
				objectRelationship.getObjectRelationshipId(), null);

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put(
				"deletionType", objectRelationship.getDeletionType()
			).put(
				"labelMap", objectRelationship.getLabelMap()
			);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

}