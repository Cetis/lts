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

public class CompleteInstitution {

	final String URL_PATTERN = "http://www.thecompleteuniversityguide.co.uk/league-tables/rankings?v=wide&y=%d";
	
	final String DATA_ROW_XPATH = "//tr[@data-institutionid]";
	
	final String IGNORE = "ignore";
	
	final String[] attributes2008 = {"rank", IGNORE, "University Name", "Entry Standards", "Student Satisfaction", "Research Quality", "Graduate Prospects", "Student-staff Ratio", "Academic Services Spend", "Facilities Spend", "Good Honours", "Degree Completion", "Overall Score"};
	// Green Score added 2010
	final String[] attributes2010 = {"rank", IGNORE, "University Name", "Entry Standards", "Student Satisfaction", "Research Quality", "Graduate Prospects", "Student-staff Ratio", "Academic Services Spend", "Facilities Spend", "Good Honours", "Degree Completion", "Green Score", "Overall Score"};
	// Research Intensity added 2016
	final String[] attributes2016 = {"rank", IGNORE, "University Name", "Entry Standards", "Student Satisfaction", "Research Quality", "Research Intensity", "Graduate Prospects", "Student-staff Ratio", "Academic Services Spend", "Facilities Spend", "Good Honours", "Degree Completion", "Green Score", "Overall Score"};
	// Green Score removed 2017
	final String[] attributes2017 = {"rank", IGNORE, "University Name", "Entry Standards", "Student Satisfaction", "Research Quality", "Research Intensity", "Graduate Prospects", "Student-staff Ratio", "Academic Services Spend", "Facilities Spend", "Good Honours", "Degree Completion", "Overall Score"};
	
	public void output(int start,int end) throws MalformedURLException, IOException, XPatherException{
		
		ArrayList<InstitutionDataItem> data = new ArrayList<InstitutionDataItem>();

		for (int i =  start; i <= end; i ++){
			data.addAll(this.getData(i));
		}
		
		File outputFile = new File("complete-institutions-"+start+"-"+end+".csv");
		String headings = data.get(0).getHeadings() + "\n";
		FileUtils.write(outputFile, headings);
		FileUtils.writeLines(outputFile, data, true);
	}
	
	public List<InstitutionDataItem> getData(int year) throws MalformedURLException, IOException, XPatherException{
		String url = String.format(URL_PATTERN, year);

		System.out.println("reading from "+url);
		
		ArrayList<InstitutionDataItem> data = new ArrayList<InstitutionDataItem>();

		TagNode cleaned = ScraperUtils.getCleanedHtml(url);

		Object[] rows = cleaned.evaluateXPath(DATA_ROW_XPATH);

		for (Object row: rows){

			TagNode tr = (TagNode)row;

			InstitutionDataItem dataItem = new InstitutionDataItem();

			String[] attributes = attributes2008;
			if (year >= 2010) attributes = attributes2010;
			if (year >= 2016) attributes = attributes2016;
			if (year >= 2017) attributes = attributes2017;
			
			dataItem.data.put("year", String.valueOf(year));
			
			for (int i = 0; i < attributes.length; i ++){
				
				if (!attributes[i].equals(IGNORE)){
					dataItem.data.put(attributes[i], tr.getChildTags()[i].getText().toString().trim().replace(",",""));
				}
			}
			
			data.add(dataItem);
		}

		return data;
	}
}
