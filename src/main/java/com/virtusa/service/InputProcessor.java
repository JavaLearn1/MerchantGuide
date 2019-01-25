/**
 * 
 */
package com.virtusa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Vinish
 *
 */
public class InputProcessor implements DecodeGalaxyWord {
	
	private LineIdentifier conversationLine;
	private HashMap<String, String> constantAssignments;
	private HashMap<String, String> computedLiterals;
	private ArrayList<String> output;
	
	public InputProcessor(ArrayList<String> output){
		this.constantAssignments= new HashMap<String, String>();
		this.conversationLine= new LineIdentifier();
		this.computedLiterals= new HashMap<String, String>();
		this.output= output;
	}
	
	public String validate(String line) {

		String errorMessage =null ;

		LineIdentifier.Type lineType = this.conversationLine.getLineType(line);

		switch (lineType) {
		case ASSIGNED:
			processAssignmentLine(line);
			break;

		case CREDITS:
			processCreditsLine(line);
			break;

		case QUESTION_HOW_MUCH:
			processHowMuchQuestion(line);
			break;

		case QUESTION_HOW_MANY:
			processHowManyCreditsQuestion(line);
			break;

		default:
			errorMessage="I have No Idea";
			break;
		}

		return errorMessage;
	}

	public void processAssignmentLine(String line) {
		String[] splited = line.trim().split("\\s+");
		try {
			constantAssignments.put(splited[0], splited[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect Line Type");
			System.out.println(e.getMessage());
		}
	}

	public void processHowMuchQuestion(String line) {
		try {
			String formatted = line.split("\\sis\\s")[1].trim();
			formatted = formatted.replace("?", "").trim();
			String keys[] = formatted.split("\\s+");
			String romanResult = "";
			String completeResult = null;
			boolean errorOccured = false;
			for (String key : keys) {
				String romanValue = constantAssignments.get(key);
				if (romanValue == null) {
					completeResult = "No Idea";
					errorOccured = true;
					break;
				}
				romanResult += romanValue;
			}

			if (!errorOccured) {
				romanResult = NumberConversion.romanToArabic(romanResult);
				completeResult = formatted + " is " + romanResult;
			}

			output.add(completeResult);

		} catch (Exception e) {
			System.out.println("Incorrect Line Type");
			System.out.println(e.getMessage());

		}
	}

	public void processCreditsLine(String line) {
		try {
			String formatted = line.replaceAll("(is\\s+)|([c|C]redits\\s*)", "").trim();
			String[] keys = formatted.split("\\s");
			String toBeComputed = keys[keys.length - 2];
			float value = Float.parseFloat(keys[keys.length - 1]);
			String roman = "";

			for (int i = 0; i < keys.length - 2; i++) {
				roman += constantAssignments.get(keys[i]);
			}

			int romanNumber = Integer.parseInt(NumberConversion.romanToArabic(roman));
			float credit = (float) (value / romanNumber);

			computedLiterals.put(toBeComputed, credit + "");
		} catch (Exception e) {

			System.out.println("Incorrect Line Type");
			System.out.println(e.getMessage());

		}
	}

	public void processHowManyCreditsQuestion(String line) {

		try {
			String formatted = line.split("(\\sis\\s)")[1];
			formatted = formatted.replace("?", "").trim();
			String[] keys = formatted.split("\\s");

			boolean found = false;
			String roman = "";
			String outputResult = null;
			Stack<Float> cvalues = new Stack<Float>();

			for (String key : keys) {
				found = false;

				String romanValue = constantAssignments.get(key);
				if (romanValue != null) {
					roman += romanValue;
					found = true;
				}

				String computedValue = computedLiterals.get(key);
				if (!found && computedValue != null) {
					cvalues.push(Float.parseFloat(computedValue));
					found = true;
				}

				if (!found) {
					outputResult = "No Idea";
					break;
				}
			}

			if (found) {
				float res = 1;
				for (int i = 0; i < cvalues.size(); i++)
					res *= cvalues.get(i);

				int finalres = (int) res;
				if (roman.length() > 0)
					finalres = (int) (Integer.parseInt(NumberConversion.romanToArabic(roman)) * res);
				outputResult = formatted + " is " + finalres + " Credits";
			}

			this.output.add(outputResult);

		} catch (Exception e) {
			System.out.println("Incorrect line type");
			System.out.println(e.getMessage());
		}

	}
}
