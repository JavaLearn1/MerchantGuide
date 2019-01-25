/**
 * 
 */
package com.virtusa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Vinish
 * 
 *
 */
public class GalaxyInput {

	private Scanner inputScanner;
	private ArrayList<String> output;
	private InputProcessor inputProcessor;

	public GalaxyInput() {
		this.inputScanner = new Scanner(System.in);
		this.output = new ArrayList<String>();
		inputProcessor= new InputProcessor(output);
	}

	// Read the Input and validate
	public ArrayList<String> readInput() {
		String line;
		int count = 0;
		String error = null;

		while (this.inputScanner.hasNextLine() && (line = this.inputScanner.nextLine()).length() > 0) {
			error = inputProcessor.validate(line);
			if(error!=null){
				this.output.add(error);
			}
			count++;
		}

		if(count==0){
			System.out.println("No Input ");
		}
		return this.output;

	}

	
	
}
