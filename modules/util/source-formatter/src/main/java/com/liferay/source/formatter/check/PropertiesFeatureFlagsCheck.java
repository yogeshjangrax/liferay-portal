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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesFeatureFlagsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("portal.properties")) {
			return content;
		}

		return _generateFeatureFlags(content);
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	private List<String> _allFileNames;

	private String _generateFeatureFlags(String content) throws IOException {

		List<String> featureFlags = new ArrayList<>();
		
		List<String> fileNames = SourceFormatterUtil.filterFileNames(
				_allFileNames, new String[0],
				new String[] {"**/bnd.bnd", "**/*.java"},
				getSourceFormatterExcludes(), true);

		Matcher matcher = null;
		
		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
					fileName, CharPool.BACK_SLASH, CharPool.SLASH);
			
			String fileContent = FileUtil.read(new File(fileName));
			
			if (fileName.endsWith("bnd.bnd")) {
				String liferaySiteInitializerFeatureFlag =
						BNDSourceUtil.getDefinitionValue(
						fileContent, "Liferay-Site-Initializer-Feature-Flag");

				if (liferaySiteInitializerFeatureFlag == null) {
					continue;
				}
	
				featureFlags.add(
					"feature.flag." + liferaySiteInitializerFeatureFlag);

			}
			else  {
				if (!fileContent.contains("feature.flag")) {
					continue;
				}
				
				matcher = _featureFlagPattern1.matcher(fileContent);
	
				while (matcher.find()) {
					featureFlags.add("feature.flag." + matcher.group(1));
				}
	
				matcher = _featureFlagPattern2.matcher(fileContent);
	
				while (matcher.find()) {
					featureFlags.add("feature.flag." + matcher.group(1));
				}

			}
		}

		ListUtil.distinct(featureFlags, new NaturalOrderStringComparator());

		matcher = _featureFlagsPattern.matcher(content);

		if (matcher.find()) {
			String matchedFeatureFlags = matcher.group(2);

			if (featureFlags.isEmpty()) {
				if (matchedFeatureFlags.contains("feature.flag.")) {
					return StringUtil.replaceFirst(
						content, matchedFeatureFlags, StringPool.BLANK,
						matcher.start(2));
				}

				return content;
			}

			StringBundler sb = new StringBundler(featureFlags.size() * 14);

			for (String featureFlag : featureFlags) {
				String environmentVariable =
					ToolsUtil.encodeEnvironmentProperty(featureFlag);

				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append("    # Env: ");
				sb.append(environmentVariable);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(featureFlag);
				sb.append("=false");
			}

			if (matchedFeatureFlags.contains("feature.flag.")) {
				content = StringUtil.replaceFirst(
					content, matchedFeatureFlags, sb.toString(),
					matcher.start(2));
			}
			else {
				content = StringUtil.insert(
					content, sb.toString(), matcher.start(2));
			}
		}

		return content;
	}

	private static final Pattern _featureFlagPattern1 = Pattern.compile(
		"\"feature\\.flag\\.(.+?)\"");
	private static final Pattern _featureFlagPattern2 = Pattern.compile(
		"\\.feature\\.flag=(.+?)\"");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

}