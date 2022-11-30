<ul>
	<#if entries?has_content>
		<#list entries as entry>
			<#assign entryLanguage = entry.longDisplayName?
																replace(" [beta]", "")?
																replace("(australia)", "(Australia)")?
																replace("(united states)", "(United States)")?
																cap_first />

			<#if !entry.isDisabled()>
				<li class="${(entry.isSelected())?then('selected', '')} language-nav-item">
					<@liferay_aui["a"]
						cssClass="language-entry-long-text"
						href=entry.getURL()
						label=entryLanguage
						lang=entry.getW3cLanguageId()
						rel="nofollow"
					/>
					</li>
			</#if>
		</#list>
	</#if>
</ul>