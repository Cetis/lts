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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class CompleteBySubject {

	final String URL_PATTERN = "http://www.thecompleteuniversityguide.co.uk/league-tables/rankings?v=wide&y=%d&s=%s";
	
	final String DATA_ROW_XPATH = "//tr[@data-institutionid]";
	
	final String IGNORE = "ignore";
	
	final String[] attributes2010 = {"rank", IGNORE, "University Name", "Entry Standards", IGNORE,"Student Satisfaction", IGNORE, "Research Quality", IGNORE,"Graduate Prospects", IGNORE,"Overall Score"};
	// Research Intensity added 2016
	final String[] attributes2016 = {"rank", IGNORE, "University Name", "Entry Standards", IGNORE,"Student Satisfaction", IGNORE, "Research Quality", IGNORE,"Research Intensity", IGNORE, "Graduate Prospects", IGNORE, "Overall Score"};

	
	public void output(int start,int end) throws MalformedURLException, IOException, XPatherException{
		
		ArrayList<SubjectDataItem> data = new ArrayList<SubjectDataItem>();
		
		String[] subjects = getSubjects();
		
				System.out.println("subjects "+subjects.length);

		
		//
		// for each year, and each subject, get the data
		//
		for (int y = start; y <= end; y++){
			for (int s = 0; s < subjects.length; s++){
				data.addAll(getData(y, subjects[s]));
			}
		}
		File outputFile = new File("complete-subjects-"+start+"-"+end+".csv");
		String headings = data.get(0).getHeadings() + "\n";
		FileUtils.write(outputFile, headings);
		FileUtils.writeLines(outputFile, data, true);
	}
	
	public List<SubjectDataItem> getData(int year, String subject) throws MalformedURLException, IOException, XPatherException{
	
		//
		// Encode the subject query
		//
		subject = subject.replace("&amp;", "%26");
		subject = subject.replace(" ", "%20");
		
		String url = String.format(URL_PATTERN, year, subject);

		System.out.println("reading from "+url);
		
		ArrayList<SubjectDataItem> data = new ArrayList<SubjectDataItem>();

		TagNode cleaned = ScraperUtils.getCleanedHtml(url);

		Object[] rows = cleaned.evaluateXPath(DATA_ROW_XPATH);
		
		//
		// In some cases there is no subject data available. In which case we need to skip
		// Complete returns the whole institution dataset when this happens, so lets check
		// the page title
		//
		if (!cleaned.evaluateXPath("//h1/text()")[0].toString().trim().contains("Subject")){
			System.out.println("No data for this subject in this year");
			return data;
		}
		

		for (Object row: rows){

			TagNode tr = (TagNode)row;

			SubjectDataItem dataItem = new SubjectDataItem();

			String[] attributes = attributes2010;
			if (year >= 2016) attributes = attributes2016;
			
			//
			// Add in the query params as data elements
			//
			dataItem.data.put("year", String.valueOf(year));
			dataItem.data.put("subject", subject);
			
			for (int i = 0; i < attributes.length; i ++){
				
				if (!attributes[i].equals(IGNORE)){
					dataItem.data.put(attributes[i], tr.getChildTags()[i].getText().toString().trim().replace(",",""));
				}
			}
			
			data.add(dataItem);
		}

		return data;
	}
	
	public String[] getSubjects() throws IOException, XPatherException{

		//
		// Read and clean HTML
		// 
		TagNode cleaned = ScraperUtils.getCleanedHtml("http://www.thecompleteuniversityguide.co.uk/league-tables/rankings");
		
		//
		// Pull out subjects using XPath.
		//
		Object[] matches = cleaned.evaluateXPath("//div[@class='facet hidden facettable'][2]/ul[@class='links']/li/a/text()");

		String[] subjects = new String[matches.length];

		for (int i = 0; i < matches.length; i++){
			subjects[i] = matches[i].toString();
		}

		return subjects;
	}
}
