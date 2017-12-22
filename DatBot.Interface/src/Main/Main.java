package Main;

import Game.Info;
import Main.Communication.Communication;
import protocol.network.Network;

public class Main {

	public static void main(String[] args) throws Exception
	{
		boolean arg = false;
		if (args.length != 0)
		{
			if(args.length == 1){
				if ((args[0].equals("true") || args[0].equals("True")))
				{
					arg = true;
				}
			} else if (args.length == 4){
				 Info.nameAccount = args[0];
				 Info.password = args[1];
				 Info.name = args[2];
				 Info.server = args[3];
			} else if (args.length == 5){
				 Info.nameAccount = args[0];
				 Info.password = args[1];
				 Info.name = args[2];
				 Info.server = args[3];
				 if ((args[0].equals("true") || args[0].equals("True")))
				 {
					 arg = true;
				 }
			}
		}
		
		Network network = new Network(arg,"213.248.126.40",5555);
		Thread communication = new Thread(new Communication(network));
		communication.start();

		while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals(""))
		{
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
		}
		
		Thread thread = new Thread(network);
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

		network.append("Connecté !",false);
		network.append("Name : " + Info.name,false);
		network.append("Niveau : " + Info.lvl,false);
	}
}
