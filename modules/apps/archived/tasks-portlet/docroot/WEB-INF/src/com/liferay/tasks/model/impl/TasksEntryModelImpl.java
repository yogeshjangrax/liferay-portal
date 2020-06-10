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

package com.liferay.tasks.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.tasks.model.TasksEntry;
import com.liferay.tasks.model.TasksEntryModel;
import com.liferay.tasks.model.TasksEntrySoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the TasksEntry service. Represents a row in the &quot;TMS_TasksEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>TasksEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link TasksEntryImpl}.
 * </p>
 *
 * @author Ryan Park
 * @see TasksEntryImpl
 * @generated
 */
@JSON(strict = true)
public class TasksEntryModelImpl
	extends BaseModelImpl<TasksEntry> implements TasksEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a tasks entry model instance should use the <code>TasksEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "TMS_TasksEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"tasksEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"title", Types.VARCHAR},
		{"priority", Types.INTEGER}, {"assigneeUserId", Types.BIGINT},
		{"resolverUserId", Types.BIGINT}, {"dueDate", Types.TIMESTAMP},
		{"finishDate", Types.TIMESTAMP}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("tasksEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("assigneeUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("resolverUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("dueDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("finishDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table TMS_TasksEntry (tasksEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,priority INTEGER,assigneeUserId LONG,resolverUserId LONG,dueDate DATE null,finishDate DATE null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table TMS_TasksEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY tasksEntry.priority ASC, tasksEntry.dueDate ASC, tasksEntry.createDate ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY TMS_TasksEntry.priority ASC, TMS_TasksEntry.dueDate ASC, TMS_TasksEntry.createDate ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.util.service.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.tasks.model.TasksEntry"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.util.service.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.tasks.model.TasksEntry"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.util.service.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.tasks.model.TasksEntry"),
		true);

	public static final long ASSIGNEEUSERID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long RESOLVERUSERID_COLUMN_BITMASK = 4L;

	public static final long STATUS_COLUMN_BITMASK = 8L;

	public static final long USERID_COLUMN_BITMASK = 16L;

	public static final long PRIORITY_COLUMN_BITMASK = 32L;

	public static final long DUEDATE_COLUMN_BITMASK = 64L;

	public static final long CREATEDATE_COLUMN_BITMASK = 128L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static TasksEntry toModel(TasksEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		TasksEntry model = new TasksEntryImpl();

		model.setTasksEntryId(soapModel.getTasksEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTitle(soapModel.getTitle());
		model.setPriority(soapModel.getPriority());
		model.setAssigneeUserId(soapModel.getAssigneeUserId());
		model.setResolverUserId(soapModel.getResolverUserId());
		model.setDueDate(soapModel.getDueDate());
		model.setFinishDate(soapModel.getFinishDate());
		model.setStatus(soapModel.getStatus());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<TasksEntry> toModels(TasksEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<TasksEntry> models = new ArrayList<TasksEntry>(soapModels.length);

		for (TasksEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.util.service.ServiceProps.get(
			"lock.expiration.time.com.liferay.tasks.model.TasksEntry"));

	public TasksEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _tasksEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setTasksEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _tasksEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return TasksEntry.class;
	}

	@Override
	public String getModelClassName() {
		return TasksEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<TasksEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<TasksEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TasksEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((TasksEntry)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<TasksEntry, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<TasksEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(TasksEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<TasksEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<TasksEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, TasksEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			TasksEntry.class.getClassLoader(), TasksEntry.class,
			ModelWrapper.class);

		try {
			Constructor<TasksEntry> constructor =
				(Constructor<TasksEntry>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<TasksEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<TasksEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<TasksEntry, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<TasksEntry, Object>>();
		Map<String, BiConsumer<TasksEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<TasksEntry, ?>>();

		attributeGetterFunctions.put(
			"tasksEntryId", TasksEntry::getTasksEntryId);
		attributeSetterBiConsumers.put(
			"tasksEntryId",
			(BiConsumer<TasksEntry, Long>)TasksEntry::setTasksEntryId);
		attributeGetterFunctions.put("groupId", TasksEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<TasksEntry, Long>)TasksEntry::setGroupId);
		attributeGetterFunctions.put("companyId", TasksEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<TasksEntry, Long>)TasksEntry::setCompanyId);
		attributeGetterFunctions.put("userId", TasksEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<TasksEntry, Long>)TasksEntry::setUserId);
		attributeGetterFunctions.put("userName", TasksEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<TasksEntry, String>)TasksEntry::setUserName);
		attributeGetterFunctions.put("createDate", TasksEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<TasksEntry, Date>)TasksEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", TasksEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<TasksEntry, Date>)TasksEntry::setModifiedDate);
		attributeGetterFunctions.put("title", TasksEntry::getTitle);
		attributeSetterBiConsumers.put(
			"title", (BiConsumer<TasksEntry, String>)TasksEntry::setTitle);
		attributeGetterFunctions.put("priority", TasksEntry::getPriority);
		attributeSetterBiConsumers.put(
			"priority",
			(BiConsumer<TasksEntry, Integer>)TasksEntry::setPriority);
		attributeGetterFunctions.put(
			"assigneeUserId", TasksEntry::getAssigneeUserId);
		attributeSetterBiConsumers.put(
			"assigneeUserId",
			(BiConsumer<TasksEntry, Long>)TasksEntry::setAssigneeUserId);
		attributeGetterFunctions.put(
			"resolverUserId", TasksEntry::getResolverUserId);
		attributeSetterBiConsumers.put(
			"resolverUserId",
			(BiConsumer<TasksEntry, Long>)TasksEntry::setResolverUserId);
		attributeGetterFunctions.put("dueDate", TasksEntry::getDueDate);
		attributeSetterBiConsumers.put(
			"dueDate", (BiConsumer<TasksEntry, Date>)TasksEntry::setDueDate);
		attributeGetterFunctions.put("finishDate", TasksEntry::getFinishDate);
		attributeSetterBiConsumers.put(
			"finishDate",
			(BiConsumer<TasksEntry, Date>)TasksEntry::setFinishDate);
		attributeGetterFunctions.put("status", TasksEntry::getStatus);
		attributeSetterBiConsumers.put(
			"status", (BiConsumer<TasksEntry, Integer>)TasksEntry::setStatus);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getTasksEntryId() {
		return _tasksEntryId;
	}

	@Override
	public void setTasksEntryId(long tasksEntryId) {
		_tasksEntryId = tasksEntryId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getTitle() {
		if (_title == null) {
			return "";
		}
		else {
			return _title;
		}
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@JSON
	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		_priority = priority;
	}

	@JSON
	@Override
	public long getAssigneeUserId() {
		return _assigneeUserId;
	}

	@Override
	public void setAssigneeUserId(long assigneeUserId) {
		_columnBitmask |= ASSIGNEEUSERID_COLUMN_BITMASK;

		if (!_setOriginalAssigneeUserId) {
			_setOriginalAssigneeUserId = true;

			_originalAssigneeUserId = _assigneeUserId;
		}

		_assigneeUserId = assigneeUserId;
	}

	@Override
	public String getAssigneeUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getAssigneeUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setAssigneeUserUuid(String assigneeUserUuid) {
	}

	public long getOriginalAssigneeUserId() {
		return _originalAssigneeUserId;
	}

	@JSON
	@Override
	public long getResolverUserId() {
		return _resolverUserId;
	}

	@Override
	public void setResolverUserId(long resolverUserId) {
		_columnBitmask |= RESOLVERUSERID_COLUMN_BITMASK;

		if (!_setOriginalResolverUserId) {
			_setOriginalResolverUserId = true;

			_originalResolverUserId = _resolverUserId;
		}

		_resolverUserId = resolverUserId;
	}

	@Override
	public String getResolverUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getResolverUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setResolverUserUuid(String resolverUserUuid) {
	}

	public long getOriginalResolverUserId() {
		return _originalResolverUserId;
	}

	@JSON
	@Override
	public Date getDueDate() {
		return _dueDate;
	}

	@Override
	public void setDueDate(Date dueDate) {
		_columnBitmask = -1L;

		_dueDate = dueDate;
	}

	@JSON
	@Override
	public Date getFinishDate() {
		return _finishDate;
	}

	@Override
	public void setFinishDate(Date finishDate) {
		_finishDate = finishDate;
	}

	@JSON
	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), TasksEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public TasksEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, TasksEntry>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		TasksEntryImpl tasksEntryImpl = new TasksEntryImpl();

		tasksEntryImpl.setTasksEntryId(getTasksEntryId());
		tasksEntryImpl.setGroupId(getGroupId());
		tasksEntryImpl.setCompanyId(getCompanyId());
		tasksEntryImpl.setUserId(getUserId());
		tasksEntryImpl.setUserName(getUserName());
		tasksEntryImpl.setCreateDate(getCreateDate());
		tasksEntryImpl.setModifiedDate(getModifiedDate());
		tasksEntryImpl.setTitle(getTitle());
		tasksEntryImpl.setPriority(getPriority());
		tasksEntryImpl.setAssigneeUserId(getAssigneeUserId());
		tasksEntryImpl.setResolverUserId(getResolverUserId());
		tasksEntryImpl.setDueDate(getDueDate());
		tasksEntryImpl.setFinishDate(getFinishDate());
		tasksEntryImpl.setStatus(getStatus());

		tasksEntryImpl.resetOriginalValues();

		return tasksEntryImpl;
	}

	@Override
	public int compareTo(TasksEntry tasksEntry) {
		int value = 0;

		if (getPriority() < tasksEntry.getPriority()) {
			value = -1;
		}
		else if (getPriority() > tasksEntry.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getDueDate(), tasksEntry.getDueDate());

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(), tasksEntry.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TasksEntry)) {
			return false;
		}

		TasksEntry tasksEntry = (TasksEntry)object;

		long primaryKey = tasksEntry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		TasksEntryModelImpl tasksEntryModelImpl = this;

		tasksEntryModelImpl._originalGroupId = tasksEntryModelImpl._groupId;

		tasksEntryModelImpl._setOriginalGroupId = false;

		tasksEntryModelImpl._originalUserId = tasksEntryModelImpl._userId;

		tasksEntryModelImpl._setOriginalUserId = false;

		tasksEntryModelImpl._setModifiedDate = false;

		tasksEntryModelImpl._originalAssigneeUserId =
			tasksEntryModelImpl._assigneeUserId;

		tasksEntryModelImpl._setOriginalAssigneeUserId = false;

		tasksEntryModelImpl._originalResolverUserId =
			tasksEntryModelImpl._resolverUserId;

		tasksEntryModelImpl._setOriginalResolverUserId = false;

		tasksEntryModelImpl._originalStatus = tasksEntryModelImpl._status;

		tasksEntryModelImpl._setOriginalStatus = false;

		tasksEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<TasksEntry> toCacheModel() {
		TasksEntryCacheModel tasksEntryCacheModel = new TasksEntryCacheModel();

		tasksEntryCacheModel.tasksEntryId = getTasksEntryId();

		tasksEntryCacheModel.groupId = getGroupId();

		tasksEntryCacheModel.companyId = getCompanyId();

		tasksEntryCacheModel.userId = getUserId();

		tasksEntryCacheModel.userName = getUserName();

		String userName = tasksEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			tasksEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			tasksEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			tasksEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			tasksEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			tasksEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		tasksEntryCacheModel.title = getTitle();

		String title = tasksEntryCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			tasksEntryCacheModel.title = null;
		}

		tasksEntryCacheModel.priority = getPriority();

		tasksEntryCacheModel.assigneeUserId = getAssigneeUserId();

		tasksEntryCacheModel.resolverUserId = getResolverUserId();

		Date dueDate = getDueDate();

		if (dueDate != null) {
			tasksEntryCacheModel.dueDate = dueDate.getTime();
		}
		else {
			tasksEntryCacheModel.dueDate = Long.MIN_VALUE;
		}

		Date finishDate = getFinishDate();

		if (finishDate != null) {
			tasksEntryCacheModel.finishDate = finishDate.getTime();
		}
		else {
			tasksEntryCacheModel.finishDate = Long.MIN_VALUE;
		}

		tasksEntryCacheModel.status = getStatus();

		return tasksEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<TasksEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<TasksEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TasksEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((TasksEntry)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<TasksEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<TasksEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TasksEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((TasksEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, TasksEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _tasksEntryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _title;
	private int _priority;
	private long _assigneeUserId;
	private long _originalAssigneeUserId;
	private boolean _setOriginalAssigneeUserId;
	private long _resolverUserId;
	private long _originalResolverUserId;
	private boolean _setOriginalResolverUserId;
	private Date _dueDate;
	private Date _finishDate;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _columnBitmask;
	private TasksEntry _escapedModel;

}