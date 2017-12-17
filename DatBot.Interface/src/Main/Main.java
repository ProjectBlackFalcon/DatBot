package Main;

import Game.Info;
import Main.Communication.Communication;
import protocol.network.Network;

public class Main {

	public static void main(String[] args) throws Exception
	{
		Thread communication = new Thread(new Communication());
		communication.start();

		// if(args.length != 0){
		// Info.nameAccount = args[0];
		// Info.password = args[1];
		// Info.name = args[2];
		// Info.server = args[3];
		// }

		while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals(""))
		{
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
		}

		boolean arg = false;
		if (args.length == 0)
		{
			if ((args[5].equals("true") || args[5].equals("True")))
			{
				arg = true;
			}
		}
		Thread thread = new Thread(new Network(arg));
		thread.start();

		while (!Info.isConnected)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		Network.append("Connecté !");
		Network.append("Name : " + Info.name);
		Network.append("Niveau : " + Info.lvl);
	}
}
