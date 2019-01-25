package com.virtusa.service;

import java.util.ArrayList;

/**
 * @author Vinish
 *
 */
public class ApplicationLauncher {

	public static void main(String[] args) {
		System.out.println(
				"Merchant's Guide to the Galaxy launched ," + " Please enter the input and enter blank line to break");
		GalaxyInput input = new GalaxyInput();
		ArrayList<String> output = input.readInput();
		for (int i = 0; i < output.size(); i++) {
			System.out.println(output.get(i));
		}

	}

}
