/**
 * 
 */
package com.virtusa.service;

/**
 * @author Vinish
 *
 */
public class LineIdentifier{

	
	public static enum Type{
		
		 
		 ASSIGNED, 
		 
		 CREDITS, 
		 
		 QUESTION_HOW_MUCH, 
		 
		 QUESTION_HOW_MANY, 
		 
		 NOMATCH
		 
	}
	
	
	public class LineFilter
	{
		private LineIdentifier.Type type;
		private String pattern;
		public LineFilter(LineIdentifier.Type type,String pattern)
		{
			this.type = type;
			this.pattern = pattern;
		}
		
		public String getPattern()
		{
			return this.pattern;
					
		}
		
		public LineIdentifier.Type getType()
		{
			return this.type;
		}
	}
	
	
	
	public static String patternAssigned = "^([A-Za-z]+) is ([I|V|X|L|C|D|M])$";
	public static String patternCredits = "^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$";
	public static String patternHowMuch = "^how much is (([A-Za-z\\s])+)\\?$";
	public static String patternHowMany= "^how many [c|C]redits is (([A-Za-z\\s])+)\\?$";
	private LineFilter[] linefilter;

	
	
	
	public LineIdentifier()
	{
		// Since we have have four type of lines
		this.linefilter = new LineFilter[4];
		this.linefilter[0] = new LineFilter(LineIdentifier.Type.ASSIGNED, patternAssigned);
		this.linefilter[1] = new LineFilter(LineIdentifier.Type.CREDITS, patternCredits);
		this.linefilter[2] = new LineFilter(LineIdentifier.Type.QUESTION_HOW_MUCH, patternHowMuch);
		this.linefilter[3] = new LineFilter(LineIdentifier.Type.QUESTION_HOW_MANY, patternHowMany);
		
	}
		
	
	public LineIdentifier.Type getLineType(String line)
	{
		line = line.trim();
		LineIdentifier.Type result = Type.NOMATCH;
		
		boolean matched = false;
			
		for(int i =0;i<linefilter.length && !matched ;i++)
		{
			if( line.matches(linefilter[i].getPattern()) )
			{
				matched = true;
				result = linefilter[i].getType();
			}
			
		}
		
		return result;
		
	}
	
}
