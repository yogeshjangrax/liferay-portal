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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleModel;
import com.liferay.portal.kernel.model.RoleSoap;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

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
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the Role service. Represents a row in the &quot;Role_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>RoleModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RoleImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RoleImpl
 * @generated
 */
@JSON(strict = true)
public class RoleModelImpl extends BaseModelImpl<Role> implements RoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a role model instance should use the <code>Role</code> interface instead.
	 */
	public static final String TABLE_NAME = "Role_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"roleId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"name", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"type_", Types.INTEGER}, {"subtype", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("subtype", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Role_ (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,roleId LONG not null,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,name VARCHAR(75) null,title STRING null,description STRING null,type_ INTEGER,subtype VARCHAR(75) null,primary key (roleId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table Role_";

	public static final String ORDER_BY_JPQL = " ORDER BY role_.name ASC";

	public static final String ORDER_BY_SQL = " ORDER BY Role_.name ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.Role"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.Role"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.Role"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long NAME_COLUMN_BITMASK = 8L;

	public static final long SUBTYPE_COLUMN_BITMASK = 16L;

	public static final long TYPE_COLUMN_BITMASK = 32L;

	public static final long UUID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Role toModel(RoleSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Role model = new RoleImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setUuid(soapModel.getUuid());
		model.setRoleId(soapModel.getRoleId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setType(soapModel.getType());
		model.setSubtype(soapModel.getSubtype());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Role> toModels(RoleSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Role> models = new ArrayList<Role>(soapModels.length);

		for (RoleSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_GROUPS_ROLES_NAME = "Groups_Roles";

	public static final Object[][] MAPPING_TABLE_GROUPS_ROLES_COLUMNS = {
		{"companyId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"roleId", Types.BIGINT}
	};

	public static final String MAPPING_TABLE_GROUPS_ROLES_SQL_CREATE =
		"create table Groups_Roles (companyId LONG not null,groupId LONG not null,roleId LONG not null,ctCollectionId LONG default 0 not null,ctChangeType BOOLEAN,primary key (groupId, roleId, ctCollectionId))";

	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ROLES =
		GetterUtil.getBoolean(
			com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_Roles"),
			true);

	public static final String MAPPING_TABLE_USERS_ROLES_NAME = "Users_Roles";

	public static final Object[][] MAPPING_TABLE_USERS_ROLES_COLUMNS = {
		{"companyId", Types.BIGINT}, {"roleId", Types.BIGINT},
		{"userId", Types.BIGINT}
	};

	public static final String MAPPING_TABLE_USERS_ROLES_SQL_CREATE =
		"create table Users_Roles (companyId LONG not null,roleId LONG not null,userId LONG not null,ctCollectionId LONG default 0 not null,ctChangeType BOOLEAN,primary key (roleId, userId, ctCollectionId))";

	public static final boolean FINDER_CACHE_ENABLED_USERS_ROLES =
		GetterUtil.getBoolean(
			com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Roles"),
			true);

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.Role"));

	public RoleModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _roleId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRoleId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _roleId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Role.class;
	}

	@Override
	public String getModelClassName() {
		return Role.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Role, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Role, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Role, Object> attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Role)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Role, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Role, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((Role)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Role, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Role, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Role>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Role.class.getClassLoader(), Role.class, ModelWrapper.class);

		try {
			Constructor<Role> constructor =
				(Constructor<Role>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Role, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Role, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Role, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Role, Object>>();
		Map<String, BiConsumer<Role, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Role, ?>>();

		attributeGetterFunctions.put("mvccVersion", Role::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion", (BiConsumer<Role, Long>)Role::setMvccVersion);
		attributeGetterFunctions.put("ctCollectionId", Role::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId", (BiConsumer<Role, Long>)Role::setCtCollectionId);
		attributeGetterFunctions.put("uuid", Role::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<Role, String>)Role::setUuid);
		attributeGetterFunctions.put("roleId", Role::getRoleId);
		attributeSetterBiConsumers.put(
			"roleId", (BiConsumer<Role, Long>)Role::setRoleId);
		attributeGetterFunctions.put("companyId", Role::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Role, Long>)Role::setCompanyId);
		attributeGetterFunctions.put("userId", Role::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Role, Long>)Role::setUserId);
		attributeGetterFunctions.put("userName", Role::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<Role, String>)Role::setUserName);
		attributeGetterFunctions.put("createDate", Role::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<Role, Date>)Role::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", Role::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate", (BiConsumer<Role, Date>)Role::setModifiedDate);
		attributeGetterFunctions.put("classNameId", Role::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId", (BiConsumer<Role, Long>)Role::setClassNameId);
		attributeGetterFunctions.put("classPK", Role::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK", (BiConsumer<Role, Long>)Role::setClassPK);
		attributeGetterFunctions.put("name", Role::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<Role, String>)Role::setName);
		attributeGetterFunctions.put("title", Role::getTitle);
		attributeSetterBiConsumers.put(
			"title", (BiConsumer<Role, String>)Role::setTitle);
		attributeGetterFunctions.put("description", Role::getDescription);
		attributeSetterBiConsumers.put(
			"description", (BiConsumer<Role, String>)Role::setDescription);
		attributeGetterFunctions.put("type", Role::getType);
		attributeSetterBiConsumers.put(
			"type", (BiConsumer<Role, Integer>)Role::setType);
		attributeGetterFunctions.put("subtype", Role::getSubtype);
		attributeSetterBiConsumers.put(
			"subtype", (BiConsumer<Role, String>)Role::setSubtype);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getRoleId() {
		return _roleId;
	}

	@Override
	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
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

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask = -1L;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
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
	public String getTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId);
	}

	@Override
	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	@Override
	public String getTitle(String languageId) {
		return LocalizationUtil.getLocalization(getTitle(), languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getTitle(), languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _titleCurrentLanguageId;
	}

	@JSON
	@Override
	public String getTitleCurrentValue() {
		Locale locale = getLocale(_titleCurrentLanguageId);

		return getTitle(locale);
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		return LocalizationUtil.getLocalizationMap(getTitle());
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@Override
	public void setTitle(String title, Locale locale) {
		setTitle(title, locale, LocaleUtil.getDefault());
	}

	@Override
	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(title)) {
			setTitle(
				LocalizationUtil.updateLocalization(
					getTitle(), "Title", title, languageId, defaultLanguageId));
		}
		else {
			setTitle(
				LocalizationUtil.removeLocalization(
					getTitle(), "Title", languageId));
		}
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_titleCurrentLanguageId = languageId;
	}

	@Override
	public void setTitleMap(Map<Locale, String> titleMap) {
		setTitleMap(titleMap, LocaleUtil.getDefault());
	}

	@Override
	public void setTitleMap(
		Map<Locale, String> titleMap, Locale defaultLocale) {

		if (titleMap == null) {
			return;
		}

		setTitle(
			LocalizationUtil.updateLocalization(
				titleMap, getTitle(), "Title",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getDescription(), languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	@Override
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getDefault());
	}

	@Override
	public void setDescription(
		String description, Locale locale, Locale defaultLocale) {

		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(
				LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(
				LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getDefault());
	}

	@Override
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale) {

		if (descriptionMap == null) {
			return;
		}

		setDescription(
			LocalizationUtil.updateLocalization(
				descriptionMap, getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
	}

	@JSON
	@Override
	public String getSubtype() {
		if (_subtype == null) {
			return "";
		}
		else {
			return _subtype;
		}
	}

	@Override
	public void setSubtype(String subtype) {
		_columnBitmask |= SUBTYPE_COLUMN_BITMASK;

		if (_originalSubtype == null) {
			_originalSubtype = _subtype;
		}

		_subtype = subtype;
	}

	public String getOriginalSubtype() {
		return GetterUtil.getString(_originalSubtype);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(Role.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Role.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> titleMap = getTitleMap();

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		Map<Locale, String> descriptionMap = getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getTitle();

		if (xml == null) {
			return "";
		}

		Locale defaultLocale = LocaleUtil.getDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(
			getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			Role.class.getName(), getPrimaryKey(), defaultLocale,
			availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		Locale defaultLocale = LocaleUtil.getDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String title = getTitle(defaultLocale);

		if (Validator.isNull(title)) {
			setTitle(getTitle(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setTitle(getTitle(defaultLocale), defaultLocale, defaultLocale);
		}

		String description = getDescription(defaultLocale);

		if (Validator.isNull(description)) {
			setDescription(
				getDescription(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setDescription(
				getDescription(defaultLocale), defaultLocale, defaultLocale);
		}
	}

	@Override
	public Role toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Role>
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
		RoleImpl roleImpl = new RoleImpl();

		roleImpl.setMvccVersion(getMvccVersion());
		roleImpl.setCtCollectionId(getCtCollectionId());
		roleImpl.setUuid(getUuid());
		roleImpl.setRoleId(getRoleId());
		roleImpl.setCompanyId(getCompanyId());
		roleImpl.setUserId(getUserId());
		roleImpl.setUserName(getUserName());
		roleImpl.setCreateDate(getCreateDate());
		roleImpl.setModifiedDate(getModifiedDate());
		roleImpl.setClassNameId(getClassNameId());
		roleImpl.setClassPK(getClassPK());
		roleImpl.setName(getName());
		roleImpl.setTitle(getTitle());
		roleImpl.setDescription(getDescription());
		roleImpl.setType(getType());
		roleImpl.setSubtype(getSubtype());

		roleImpl.resetOriginalValues();

		return roleImpl;
	}

	@Override
	public int compareTo(Role role) {
		int value = 0;

		value = getName().compareTo(role.getName());

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

		if (!(object instanceof Role)) {
			return false;
		}

		Role role = (Role)object;

		long primaryKey = role.getPrimaryKey();

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
		RoleModelImpl roleModelImpl = this;

		roleModelImpl._originalUuid = roleModelImpl._uuid;

		roleModelImpl._originalCompanyId = roleModelImpl._companyId;

		roleModelImpl._setOriginalCompanyId = false;

		roleModelImpl._setModifiedDate = false;

		roleModelImpl._originalClassNameId = roleModelImpl._classNameId;

		roleModelImpl._setOriginalClassNameId = false;

		roleModelImpl._originalClassPK = roleModelImpl._classPK;

		roleModelImpl._setOriginalClassPK = false;

		roleModelImpl._originalName = roleModelImpl._name;

		roleModelImpl._originalType = roleModelImpl._type;

		roleModelImpl._setOriginalType = false;

		roleModelImpl._originalSubtype = roleModelImpl._subtype;

		roleModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Role> toCacheModel() {
		RoleCacheModel roleCacheModel = new RoleCacheModel();

		roleCacheModel.mvccVersion = getMvccVersion();

		roleCacheModel.ctCollectionId = getCtCollectionId();

		roleCacheModel.uuid = getUuid();

		String uuid = roleCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			roleCacheModel.uuid = null;
		}

		roleCacheModel.roleId = getRoleId();

		roleCacheModel.companyId = getCompanyId();

		roleCacheModel.userId = getUserId();

		roleCacheModel.userName = getUserName();

		String userName = roleCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			roleCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			roleCacheModel.createDate = createDate.getTime();
		}
		else {
			roleCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			roleCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			roleCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		roleCacheModel.classNameId = getClassNameId();

		roleCacheModel.classPK = getClassPK();

		roleCacheModel.name = getName();

		String name = roleCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			roleCacheModel.name = null;
		}

		roleCacheModel.title = getTitle();

		String title = roleCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			roleCacheModel.title = null;
		}

		roleCacheModel.description = getDescription();

		String description = roleCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			roleCacheModel.description = null;
		}

		roleCacheModel.type = getType();

		roleCacheModel.subtype = getSubtype();

		String subtype = roleCacheModel.subtype;

		if ((subtype != null) && (subtype.length() == 0)) {
			roleCacheModel.subtype = null;
		}

		return roleCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Role, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Role, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Role, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Role)this));
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
		Map<String, Function<Role, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Role, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Role, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Role)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Role>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private String _originalUuid;
	private long _roleId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _name;
	private String _originalName;
	private String _title;
	private String _titleCurrentLanguageId;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private String _subtype;
	private String _originalSubtype;
	private long _columnBitmask;
	private Role _escapedModel;

}