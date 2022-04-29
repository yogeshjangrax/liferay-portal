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

package com.liferay.reading.time.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ReadingTimeEntry service. Represents a row in the &quot;ReadingTimeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.reading.time.model.impl.ReadingTimeEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntry
 * @generated
 */
@ProviderType
public interface ReadingTimeEntryModel
	extends AttachedModel, BaseModel<ReadingTimeEntry>,
			CTModel<ReadingTimeEntry>, MVCCModel, ShardedModel, StagedModel,
			TrashedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a reading time entry model instance should use the {@link ReadingTimeEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this reading time entry.
	 *
	 * @return the primary key of this reading time entry
	 */
	@Override
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this reading time entry.
	 *
	 * @param primaryKey the primary key of this reading time entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this reading time entry.
	 *
	 * @return the mvcc version of this reading time entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this reading time entry.
	 *
	 * @param mvccVersion the mvcc version of this reading time entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the ct collection ID of this reading time entry.
	 *
	 * @return the ct collection ID of this reading time entry
	 */
	@Override
	public long getCtCollectionId();

	/**
	 * Sets the ct collection ID of this reading time entry.
	 *
	 * @param ctCollectionId the ct collection ID of this reading time entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId);

	/**
	 * Returns the uuid of this reading time entry.
	 *
	 * @return the uuid of this reading time entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this reading time entry.
	 *
	 * @param uuid the uuid of this reading time entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the reading time entry ID of this reading time entry.
	 *
	 * @return the reading time entry ID of this reading time entry
	 */
	public long getReadingTimeEntryId();

	/**
	 * Sets the reading time entry ID of this reading time entry.
	 *
	 * @param readingTimeEntryId the reading time entry ID of this reading time entry
	 */
	public void setReadingTimeEntryId(long readingTimeEntryId);

	/**
	 * Returns the group ID of this reading time entry.
	 *
	 * @return the group ID of this reading time entry
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this reading time entry.
	 *
	 * @param groupId the group ID of this reading time entry
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this reading time entry.
	 *
	 * @return the company ID of this reading time entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this reading time entry.
	 *
	 * @param companyId the company ID of this reading time entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this reading time entry.
	 *
	 * @return the create date of this reading time entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this reading time entry.
	 *
	 * @param createDate the create date of this reading time entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this reading time entry.
	 *
	 * @return the modified date of this reading time entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this reading time entry.
	 *
	 * @param modifiedDate the modified date of this reading time entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this reading time entry.
	 *
	 * @return the fully qualified class name of this reading time entry
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this reading time entry.
	 *
	 * @return the class name ID of this reading time entry
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this reading time entry.
	 *
	 * @param classNameId the class name ID of this reading time entry
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class pk of this reading time entry.
	 *
	 * @return the class pk of this reading time entry
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class pk of this reading time entry.
	 *
	 * @param classPK the class pk of this reading time entry
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the reading time of this reading time entry.
	 *
	 * @return the reading time of this reading time entry
	 */
	public long getReadingTime();

	/**
	 * Sets the reading time of this reading time entry.
	 *
	 * @param readingTime the reading time of this reading time entry
	 */
	public void setReadingTime(long readingTime);

	/**
	 * Returns the status of this reading time entry.
	 *
	 * @return the status of this reading time entry
	 */
	@Override
	public int getStatus();

	/**
	 * Returns the class primary key of the trash entry for this reading time entry.
	 *
	 * @return the class primary key of the trash entry for this reading time entry
	 */
	@Override
	public long getTrashEntryClassPK();

	/**
	 * Returns <code>true</code> if this reading time entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this reading time entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash();

	@Override
	public ReadingTimeEntry cloneWithOriginalValues();

}