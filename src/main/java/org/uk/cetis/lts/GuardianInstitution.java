package org.uk.cetis.lts;

public class GuardianInstitution {
	
	final String IGNORE = "ignore";
	
	public final String[] attributes2015 = {"Rank", IGNORE, "University Name", "Guardian Score", "Satisfied with Course", "Satisfied with Teaching", "Satisfied with Feedback", "Student to staff ratio", "Spend per student/10", "Average entry tariff", "Value added score/10", "Career after 6 months"};
	
	public static final String guardian2010 = "http://www.theguardian.com/education/table/2009/may/12/university-league-table";
	public static final String guardian2011 = "http://www.theguardian.com/education/table/2010/jun/04/university-league-table";
	public static final String guardian2012 = "http://www.theguardian.com/education/table/2011/may/17/university-league-table-2012";
	public static final String guardian2013 = "http://www.theguardian.com/education/table/2012/may/21/university-league-table-2013";
	public static final String guardian2014 = "http://www.theguardian.com/education/table/2013/jun/03/university-league-table-2014";
	public static final String guardian2015 = "http://www.theguardian.com/education/ng-interactive/2014/jun/02/university-league-tables-2015-the-complete-list";
	public static final String guardian2016 = "http://www.theguardian.com/education/ng-interactive/2015/may/25/university-league-tables-2016";


	
	/*
	 * JSON for 2015 guide
	 *
	 * http://interactive.guim.co.uk/embed/2014/may/university-guide/v/50/data/data_none.json
	 */
	public static final String DATA_ROW_XPATH = "//div[contains(@class,'dataRow')]";
}
