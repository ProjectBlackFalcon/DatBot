package main;

import game.Info;
import main.communication.Communication;
import protocol.network.Network;

public class Main {

	public static void main(String[] args) throws Exception
	{

		boolean arg = false;
		if (args.length != 0)
		{
			if (args.length == 1)
			{
				if ((args[0].equals("true") || args[0].equals("True")))
				{
					arg = true;
				}
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
	}
}
