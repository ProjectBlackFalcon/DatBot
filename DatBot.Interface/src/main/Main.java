package main;

import main.communication.Communication;

public class Main {

	public static void main(String[] args) throws Exception
	{
		boolean arg = false;
		if (args.length != 0)
		{
			if ((args[0].equals("true") || args[0].equals("True")))
			{
				arg = true;
			}
		}
		
		System.out.println("Working");

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
	}
}
