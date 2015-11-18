/*
 * Copyright 2015 Cetis LLP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.uk.cetis.lts;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class ScraperUtils {
	
	/**
	 * Read HTML from specified URL and clean it up
	 * @param url
	 * @return cleaned HTML as a TagNode object
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static TagNode getCleanedHtml(String url) throws MalformedURLException, IOException{
		HtmlCleaner cleaner = new HtmlCleaner();
		InputStream in = new URL( url ).openStream();
		TagNode cleaned = null;
		try {
			cleaned = cleaner.clean( IOUtils.toString( in ) );
		} finally {
			IOUtils.closeQuietly(in);
		}
		return cleaned;
	}

}
