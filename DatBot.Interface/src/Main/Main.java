package Main;

import Game.Info;
import Main.Communication.Communication;
import protocol.network.Network;

public class Main {

	public static void main(String[] args) throws Exception
	{
		Info info = new Info();

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
			else if (args.length == 4)
			{
				info.setNameAccount(args[0]);
				info.setPassword(args[1]);
				info.setName(args[2]);
				info.setServer(args[3]);
			}
			else if (args.length == 5)
			{
				info.setNameAccount(args[0]);
				info.setPassword(args[1]);
				info.setName(args[2]);
				info.setServer(args[3]);
				if ((args[4].equals("true") || args[4].equals("True")))
				{
					arg = true;
				}
			}
		}

		Network network = new Network(arg, info, "213.248.126.40", 5555);
		Communication communication = new Communication(network);
		Thread communication2 = new Thread(communication);
		communication2.start();

		while (info.getNameAccount().equals("") || info.getPassword().equals("") || info.getName().equals("") || info.getServer().equals(""))
		{
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
		}

		Thread thread = new Thread(network);
		thread.start();

		while (!info.isConnected())
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

		network.append("Connecté !", false);
		network.append("Name : " + info.getName(), false);
		network.append("Niveau : " + info.getLvl(), false);
	}
}
