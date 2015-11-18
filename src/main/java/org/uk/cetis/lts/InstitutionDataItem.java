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

public class InstitutionDataItem extends AbstractDataItem {
		
	public InstitutionDataItem(){
		data = new TreeMap<String, String>();
	}
	
	/* (non-Javadoc)
	 * @see org.uk.cetis.lts.AbstractDataItem#normalise()
	 */
	@Override
	public void normalise(){
		super.normalise();;
		if (!data.containsKey("Green Score")){
			data.put("Green Score", "");
		}
		if (!data.containsKey("Research Intensity")){
			data.put("Research Intensity", "");
		}
	}
}
