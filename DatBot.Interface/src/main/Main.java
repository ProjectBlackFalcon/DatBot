package main;

import main.communication.Communication;
import utils.GameData;

public class Main {

	public static void main(String[] args) throws Exception
	{
		new GameData();
		boolean arg = false;
		if (args.length != 0)
		{
			if ((args[0].equals("true") || args[0].equals("True")))
			{
				arg = true;
			}
		}
		
		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
	}
}
