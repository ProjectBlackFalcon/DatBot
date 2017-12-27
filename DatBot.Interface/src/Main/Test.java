package Main;

import Game.Info;
import Main.Communication.Communication;
import protocol.network.Network;

public class Test {
	
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
		
		Info info = new Info();
		Network network = new Network(arg,info,"213.248.126.40",5555);
		Communication communication = new Communication(network);
		Thread communication2 = new Thread(communication);
		communication2.start();
		
		int index = 0;
		while (info.getNameAccount().equals("") || info.getPassword().equals("") || info.getName().equals("") || info.getServer().equals(""))
		{
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
			index++;
//			if (index == 2)
//			{
//				Info.nameAccount = "wublel7";
//				Info.password = "wubwublel7";
//				Info.name = "Dihydroquerina";
//				Info.server = "Julith";
//			}
			// if(index == 2){
			// Info.nameAccount = "Jemappellehenry2";
			// Info.password = "azerty123henry";
			// Info.name = "Baddosh";
			// Info.server = "Julith";
			// }
			 if(index == 2){
				 info.setNameAccount("ceciestuntest");
				 info.setPassword("ceciestlemdp1");
				 info.setName("Gladiatonme");
				 info.setServer("Echo");
			 }
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

		network.append("Connecté !",false);
		network.append("Name : " + info.getName(),false);
		network.append("Niveau : " + info.getLvl(),false);
		communication.getReturn("0;0;i;cmd;getMonsters;[None]");
		communication.getReturn("0;0;i;cmd;getStats;[None]");
 	}
}
