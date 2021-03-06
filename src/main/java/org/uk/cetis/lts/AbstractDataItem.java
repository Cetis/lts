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

import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractDataItem {
	
	protected TreeMap <String, String> data;

	protected String replaceEntities(String value){
		
		value = value.replace("%20", " ");
		value = value.replace("&#39;", "'");
		value = value.replace("%26", "&");
		
		return value;
	}
	
	public void normalise(){
		for (Entry<String,String> entry : data.entrySet()){
			entry.setValue(replaceEntities(entry.getValue()));
			
			if (entry.getValue().contains(",")){
				entry.setValue("\"" + entry.getValue() + "\"");
			}
		}
		
	}
	
	public String getHeadings() {
		normalise();
		return StringUtils.join(data.keySet(), ",");
	}
	
	@Override
	public String toString() {
		normalise();
		return StringUtils.join(data.values(), ",");
	}
	
}
