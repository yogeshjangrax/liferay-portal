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

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.service.builder.test.model.LVEntry;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersionModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the LVEntryVersion service. Represents a row in the &quot;LVEntryVersion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>LVEntryVersionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LVEntryVersionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersionImpl
 * @generated
 */
public class LVEntryVersionModelImpl
	extends BaseModelImpl<LVEntryVersion> implements LVEntryVersionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a lv entry version model instance should use the <code>LVEntryVersion</code> interface instead.
	 */
	public static final String TABLE_NAME = "LVEntryVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"lvEntryVersionId", Types.BIGINT}, {"version", Types.INTEGER},
		{"uuid_", Types.VARCHAR}, {"defaultLanguageId", Types.VARCHAR},
		{"lvEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"uniqueGroupKey", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("lvEntryVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("version", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("defaultLanguageId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lvEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uniqueGroupKey", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table LVEntryVersion (lvEntryVersionId LONG not null primary key,version INTEGER,uuid_ VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,lvEntryId LONG,companyId LONG,groupId LONG,uniqueGroupKey VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table LVEntryVersion";

	public static final String ORDER_BY_JPQL =
		" ORDER BY lvEntryVersion.version DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY LVEntryVersion.version DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.tools.service.builder.test.service.util.ServiceProps.
			get(
				"value.object.entity.cache.enabled.com.liferay.portal.tools.service.builder.test.model.LVEntryVersion"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.tools.service.builder.test.service.util.ServiceProps.
			get(
				"value.object.finder.cache.enabled.com.liferay.portal.tools.service.builder.test.model.LVEntryVersion"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.tools.service.builder.test.service.util.ServiceProps.
			get(
				"value.object.column.bitmask.enabled.com.liferay.portal.tools.service.builder.test.model.LVEntryVersion"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long LVENTRYID_COLUMN_BITMASK = 4L;

	public static final long UNIQUEGROUPKEY_COLUMN_BITMASK = 8L;

	public static final long UUID_COLUMN_BITMASK = 16L;

	public static final long VERSION_COLUMN_BITMASK = 32L;

	public static final String MAPPING_TABLE_BIGDECIMALENTRIES_LVENTRIES_NAME =
		"BigDecimalEntries_LVEntries";

	public static final Object[][]
		MAPPING_TABLE_BIGDECIMALENTRIES_LVENTRIES_COLUMNS = {
			{"companyId", Types.BIGINT}, {"bigDecimalEntryId", Types.BIGINT},
			{"lvEntryId", Types.BIGINT}
		};

	public static final String
		MAPPING_TABLE_BIGDECIMALENTRIES_LVENTRIES_SQL_CREATE =
			"create table BigDecimalEntries_LVEntries (companyId LONG not null,bigDecimalEntryId LONG not null,lvEntryId LONG not null,primary key (bigDecimalEntryId, lvEntryId))";

	public static final boolean
		FINDER_CACHE_ENABLED_BIGDECIMALENTRIES_LVENTRIES =
			GetterUtil.getBoolean(
				com.liferay.portal.tools.service.builder.test.service.util.
					ServiceProps.get(
						"value.object.finder.cache.enabled.BigDecimalEntries_LVEntries"),
				true);

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.tools.service.builder.test.service.util.ServiceProps.
			get(
				"lock.expiration.time.com.liferay.portal.tools.service.builder.test.model.LVEntryVersion"));

	public LVEntryVersionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _lvEntryVersionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLvEntryVersionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lvEntryVersionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LVEntryVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LVEntryVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LVEntryVersion, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LVEntryVersion, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LVEntryVersion, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((LVEntryVersion)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LVEntryVersion, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<LVEntryVersion, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(LVEntryVersion)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<LVEntryVersion, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<LVEntryVersion, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, LVEntryVersion>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			LVEntryVersion.class.getClassLoader(), LVEntryVersion.class,
			ModelWrapper.class);

		try {
			Constructor<LVEntryVersion> constructor =
				(Constructor<LVEntryVersion>)proxyClass.getConstructor(
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

	private static final Map<String, Function<LVEntryVersion, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<LVEntryVersion, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<LVEntryVersion, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<LVEntryVersion, Object>>();
		Map<String, BiConsumer<LVEntryVersion, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<LVEntryVersion, ?>>();

		attributeGetterFunctions.put(
			"lvEntryVersionId", LVEntryVersion::getLvEntryVersionId);
		attributeSetterBiConsumers.put(
			"lvEntryVersionId",
			(BiConsumer<LVEntryVersion, Long>)
				LVEntryVersion::setLvEntryVersionId);
		attributeGetterFunctions.put("version", LVEntryVersion::getVersion);
		attributeSetterBiConsumers.put(
			"version",
			(BiConsumer<LVEntryVersion, Integer>)LVEntryVersion::setVersion);
		attributeGetterFunctions.put("uuid", LVEntryVersion::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<LVEntryVersion, String>)LVEntryVersion::setUuid);
		attributeGetterFunctions.put(
			"defaultLanguageId", LVEntryVersion::getDefaultLanguageId);
		attributeSetterBiConsumers.put(
			"defaultLanguageId",
			(BiConsumer<LVEntryVersion, String>)
				LVEntryVersion::setDefaultLanguageId);
		attributeGetterFunctions.put("lvEntryId", LVEntryVersion::getLvEntryId);
		attributeSetterBiConsumers.put(
			"lvEntryId",
			(BiConsumer<LVEntryVersion, Long>)LVEntryVersion::setLvEntryId);
		attributeGetterFunctions.put("companyId", LVEntryVersion::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<LVEntryVersion, Long>)LVEntryVersion::setCompanyId);
		attributeGetterFunctions.put("groupId", LVEntryVersion::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<LVEntryVersion, Long>)LVEntryVersion::setGroupId);
		attributeGetterFunctions.put(
			"uniqueGroupKey", LVEntryVersion::getUniqueGroupKey);
		attributeSetterBiConsumers.put(
			"uniqueGroupKey",
			(BiConsumer<LVEntryVersion, String>)
				LVEntryVersion::setUniqueGroupKey);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getVersionedModelId() {
		return getLvEntryId();
	}

	@Override
	public void populateVersionedModel(LVEntry lvEntry) {
		lvEntry.setUuid(getUuid());
		lvEntry.setDefaultLanguageId(getDefaultLanguageId());
		lvEntry.setCompanyId(getCompanyId());
		lvEntry.setGroupId(getGroupId());
		lvEntry.setUniqueGroupKey(getUniqueGroupKey());
	}

	@Override
	public void setVersionedModelId(long lvEntryId) {
		setLvEntryId(lvEntryId);
	}

	@Override
	public LVEntry toVersionedModel() {
		LVEntry lvEntry = new LVEntryImpl();

		lvEntry.setPrimaryKey(getVersionedModelId());
		lvEntry.setHeadId(-getVersionedModelId());

		populateVersionedModel(lvEntry);

		return lvEntry;
	}

	@Override
	public long getLvEntryVersionId() {
		return _lvEntryVersionId;
	}

	@Override
	public void setLvEntryVersionId(long lvEntryVersionId) {
		_lvEntryVersionId = lvEntryVersionId;
	}

	@Override
	public int getVersion() {
		return _version;
	}

	@Override
	public void setVersion(int version) {
		_columnBitmask = -1L;

		if (!_setOriginalVersion) {
			_setOriginalVersion = true;

			_originalVersion = _version;
		}

		_version = version;
	}

	public int getOriginalVersion() {
		return _originalVersion;
	}

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

	@Override
	public String getDefaultLanguageId() {
		if (_defaultLanguageId == null) {
			return "";
		}
		else {
			return _defaultLanguageId;
		}
	}

	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	@Override
	public long getLvEntryId() {
		return _lvEntryId;
	}

	@Override
	public void setLvEntryId(long lvEntryId) {
		_columnBitmask |= LVENTRYID_COLUMN_BITMASK;

		if (!_setOriginalLvEntryId) {
			_setOriginalLvEntryId = true;

			_originalLvEntryId = _lvEntryId;
		}

		_lvEntryId = lvEntryId;
	}

	public long getOriginalLvEntryId() {
		return _originalLvEntryId;
	}

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

	@Override
	public String getUniqueGroupKey() {
		if (_uniqueGroupKey == null) {
			return "";
		}
		else {
			return _uniqueGroupKey;
		}
	}

	@Override
	public void setUniqueGroupKey(String uniqueGroupKey) {
		_columnBitmask |= UNIQUEGROUPKEY_COLUMN_BITMASK;

		if (_originalUniqueGroupKey == null) {
			_originalUniqueGroupKey = _uniqueGroupKey;
		}

		_uniqueGroupKey = uniqueGroupKey;
	}

	public String getOriginalUniqueGroupKey() {
		return GetterUtil.getString(_originalUniqueGroupKey);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), LVEntryVersion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LVEntryVersion toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, LVEntryVersion>
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
		LVEntryVersionImpl lvEntryVersionImpl = new LVEntryVersionImpl();

		lvEntryVersionImpl.setLvEntryVersionId(getLvEntryVersionId());
		lvEntryVersionImpl.setVersion(getVersion());
		lvEntryVersionImpl.setUuid(getUuid());
		lvEntryVersionImpl.setDefaultLanguageId(getDefaultLanguageId());
		lvEntryVersionImpl.setLvEntryId(getLvEntryId());
		lvEntryVersionImpl.setCompanyId(getCompanyId());
		lvEntryVersionImpl.setGroupId(getGroupId());
		lvEntryVersionImpl.setUniqueGroupKey(getUniqueGroupKey());

		lvEntryVersionImpl.resetOriginalValues();

		return lvEntryVersionImpl;
	}

	@Override
	public int compareTo(LVEntryVersion lvEntryVersion) {
		int value = 0;

		if (getVersion() < lvEntryVersion.getVersion()) {
			value = -1;
		}
		else if (getVersion() > lvEntryVersion.getVersion()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

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

		if (!(object instanceof LVEntryVersion)) {
			return false;
		}

		LVEntryVersion lvEntryVersion = (LVEntryVersion)object;

		long primaryKey = lvEntryVersion.getPrimaryKey();

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
		LVEntryVersionModelImpl lvEntryVersionModelImpl = this;

		lvEntryVersionModelImpl._originalVersion =
			lvEntryVersionModelImpl._version;

		lvEntryVersionModelImpl._setOriginalVersion = false;

		lvEntryVersionModelImpl._originalUuid = lvEntryVersionModelImpl._uuid;

		lvEntryVersionModelImpl._originalLvEntryId =
			lvEntryVersionModelImpl._lvEntryId;

		lvEntryVersionModelImpl._setOriginalLvEntryId = false;

		lvEntryVersionModelImpl._originalCompanyId =
			lvEntryVersionModelImpl._companyId;

		lvEntryVersionModelImpl._setOriginalCompanyId = false;

		lvEntryVersionModelImpl._originalGroupId =
			lvEntryVersionModelImpl._groupId;

		lvEntryVersionModelImpl._setOriginalGroupId = false;

		lvEntryVersionModelImpl._originalUniqueGroupKey =
			lvEntryVersionModelImpl._uniqueGroupKey;

		lvEntryVersionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<LVEntryVersion> toCacheModel() {
		LVEntryVersionCacheModel lvEntryVersionCacheModel =
			new LVEntryVersionCacheModel();

		lvEntryVersionCacheModel.lvEntryVersionId = getLvEntryVersionId();

		lvEntryVersionCacheModel.version = getVersion();

		lvEntryVersionCacheModel.uuid = getUuid();

		String uuid = lvEntryVersionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			lvEntryVersionCacheModel.uuid = null;
		}

		lvEntryVersionCacheModel.defaultLanguageId = getDefaultLanguageId();

		String defaultLanguageId = lvEntryVersionCacheModel.defaultLanguageId;

		if ((defaultLanguageId != null) && (defaultLanguageId.length() == 0)) {
			lvEntryVersionCacheModel.defaultLanguageId = null;
		}

		lvEntryVersionCacheModel.lvEntryId = getLvEntryId();

		lvEntryVersionCacheModel.companyId = getCompanyId();

		lvEntryVersionCacheModel.groupId = getGroupId();

		lvEntryVersionCacheModel.uniqueGroupKey = getUniqueGroupKey();

		String uniqueGroupKey = lvEntryVersionCacheModel.uniqueGroupKey;

		if ((uniqueGroupKey != null) && (uniqueGroupKey.length() == 0)) {
			lvEntryVersionCacheModel.uniqueGroupKey = null;
		}

		return lvEntryVersionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<LVEntryVersion, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<LVEntryVersion, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LVEntryVersion, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((LVEntryVersion)this));
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
		Map<String, Function<LVEntryVersion, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<LVEntryVersion, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LVEntryVersion, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((LVEntryVersion)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, LVEntryVersion>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _lvEntryVersionId;
	private int _version;
	private int _originalVersion;
	private boolean _setOriginalVersion;
	private String _uuid;
	private String _originalUuid;
	private String _defaultLanguageId;
	private long _lvEntryId;
	private long _originalLvEntryId;
	private boolean _setOriginalLvEntryId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private String _uniqueGroupKey;
	private String _originalUniqueGroupKey;
	private long _columnBitmask;
	private LVEntryVersion _escapedModel;

}