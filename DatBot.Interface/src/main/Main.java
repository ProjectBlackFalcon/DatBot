package main;

import main.communication.Communication;
import protocol.network.Network;
import utils.d2i.d2iManager;

public class Main {

	public static void main(String[] args) throws Exception
	{
		new d2iManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
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
