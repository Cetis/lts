package org.uk.cetis.lts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.htmlcleaner.XPatherException;
import org.junit.Test;

public class CompleteInstitutionsTest {
	
	@Test
	public void getInstitutionData() throws MalformedURLException, IOException, XPatherException{
		CompleteInstitution complete = new CompleteInstitution();
		List<InstitutionDataItem> data = complete.getData(2010);

		for (InstitutionDataItem item : data){
			System.out.println(item.getHeadings());
			System.out.println(item.toString());
		}
	}
	
	@Test
	public void output() throws MalformedURLException, IOException, XPatherException{
		CompleteInstitution complete = new CompleteInstitution();
		complete.output(2008, 2016);
	}

}
