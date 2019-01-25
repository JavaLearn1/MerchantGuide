package com.virtusa.service;

public interface DecodeGalaxyWord {
	
		public String validate(String line);
		public void processAssignmentLine(String line);
		public void processHowMuchQuestion(String line);
		public void processCreditsLine(String line);
		public void processHowManyCreditsQuestion(String line);

}
