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

package com.liferay.adaptive.media.image.content.transformer.backwards.compatibility.internal;

import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMBackwardsCompatibilityHtmlContentTransformerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_amImageHTMLTagFactory.create(
				Mockito.anyString(), Mockito.any(FileEntry.class))
		).thenReturn(
			"[REPLACED]"
		);

		ReflectionTestUtil.setFieldValue(
			_contentTransformer, "_amImageHTMLTagFactory",
			_amImageHTMLTagFactory);

		Mockito.when(
			_dlAppLocalService.getFileEntryByUuidAndGroupId(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntryFriendlyURLResolver.resolveFriendlyURL(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_groupLocalService.fetchFriendlyURLGroup(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_group
		);

		ReflectionTestUtil.setFieldValue(
			_contentTransformer, "_dlAppLocalService", _dlAppLocalService);
		ReflectionTestUtil.setFieldValue(
			_contentTransformer, "_fileEntryFriendlyURLResolver",
			_fileEntryFriendlyURLResolver);
		ReflectionTestUtil.setFieldValue(
			_contentTransformer, "_groupLocalService", _groupLocalService);
	}

	@Test
	public void testContentTransformerContentTypeIsHTML() throws Exception {
		Assert.assertEquals(
			ContentTransformerContentTypes.HTML,
			_contentTransformer.getContentTransformerContentType());
	}

	@Test
	public void testReplacesFriendlyURLImageTagsWithDoubleQuotes()
		throws Exception {

		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(_CONTENT_WITH_IMAGE_FRIENDLY_URL));
	}

	@Test
	public void testReplacesImageTagsWithDoubleQuotes() throws Exception {
		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(
				_CONTENT_WITH_IMAGE_AND_DOUBLE_QUOTES));
	}

	@Test
	public void testReplacesImageTagsWithLegacyContent() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(20138, 0, "sample.jpg")
		).thenReturn(
			_fileEntry
		);

		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(
				_LEGACY_CONTENT_WITH_IMAGE_AND_SINGLE_QUOTES));
	}

	@Test(timeout = 1000)
	public void testReplacesImageTagsWithLongTitleWithSpaces()
		throws Exception {

		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(
				_CONTENT_WITH_IMAGE_AND_LONG_TITLE_WITH_SPACES));
	}

	@Test
	public void testReplacesImageTagsWithSingleQuotes() throws Exception {
		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(
				_CONTENT_WITH_IMAGE_AND_SINGLE_QUOTES));
	}

	@Test
	public void testReturnsBlankForBlankContent() throws Exception {
		Assert.assertEquals(
			StringPool.BLANK, _contentTransformer.transform(StringPool.BLANK));
	}

	@Test
	public void testReturnsNullForNullContent() throws Exception {
		Assert.assertNull(_contentTransformer.transform(null));
	}

	@Test
	public void testReturnsTheSameHTMLIfNoImagesArePresent() throws Exception {
		String content = RandomTestUtil.randomString();

		Assert.assertEquals(content, _contentTransformer.transform(content));
	}

	@Test
	public void testSupportsImageTagsWithNewLineCharacters() throws Exception {
		Assert.assertEquals(
			_CONTENT_PREFIX + "[REPLACED]" + _CONTENT_SUFFIX,
			_contentTransformer.transform(_CONTENT_WITH_IMAGE_AND_NEWLINES));
	}

	private static final String _CONTENT_PREFIX = "<p>Prefix";

	private static final String _CONTENT_SUFFIX = "Suffix</p>";

	private static final String _CONTENT_WITH_IMAGE_AND_DOUBLE_QUOTES =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img src=\"/documents/20138/0/sample.jpg",
			"/1710bfe2-2b7c-1f69-f8b7-23ff6bd5dd4b?t=1506075653544\" />",
			_CONTENT_SUFFIX);

	private static final String _CONTENT_WITH_IMAGE_AND_LONG_TITLE_WITH_SPACES =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img src=\"/documents/20138/0/sample.jpg",
			"/1710bfe2-2b7c-1f69-f8b7-23ff6bd5dd4b?t=1506075653544\" ",
			"title=\"1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 ",
			"9 0 1 2 3 4 5 \" />", _CONTENT_SUFFIX);

	private static final String _CONTENT_WITH_IMAGE_AND_NEWLINES =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img\nsrc=\"/documents/20138/0/sample.jpg",
			"/1710bfe2-2b7c-1f69-f8b7-23ff6bd5dd4b?t=1506075653544\"\n />",
			_CONTENT_SUFFIX);

	private static final String _CONTENT_WITH_IMAGE_AND_SINGLE_QUOTES =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img src='/documents/20138/0/sample.jpg",
			"/1710bfe2-2b7c-1f69-f8b7-23ff6bd5dd4b?t=1506075653544' />",
			_CONTENT_SUFFIX);

	private static final String _CONTENT_WITH_IMAGE_FRIENDLY_URL =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img src=\"/documents/d/site_name/sample\" />",
			_CONTENT_SUFFIX);

	private static final String _LEGACY_CONTENT_WITH_IMAGE_AND_SINGLE_QUOTES =
		StringBundler.concat(
			_CONTENT_PREFIX, "<img src='/documents/20138/0/sample.jpg?t=",
			"1506075653544' />", _CONTENT_SUFFIX);

	private final AMImageHTMLTagFactory _amImageHTMLTagFactory = Mockito.mock(
		AMImageHTMLTagFactory.class);
	private final AMBackwardsCompatibilityHtmlContentTransformer
		_contentTransformer =
			new AMBackwardsCompatibilityHtmlContentTransformer();
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileEntryFriendlyURLResolver _fileEntryFriendlyURLResolver =
		Mockito.mock(FileEntryFriendlyURLResolver.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);

}