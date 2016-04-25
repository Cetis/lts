package org.uk.cetis.lts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.htmlcleaner.XPatherException;
import org.junit.Test;

public class CompleteSubjectsTest {

	@Test
	public void output() throws MalformedURLException, IOException, XPatherException{
		CompleteBySubject complete = new CompleteBySubject();
		complete.output(2010, 2017);
	}

}
