package Main.Communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import Game.Info;
import protocol.network.Network;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
	
	protected Network network;
	private ModelConnexion connexion;
	
	public Communication(Network network)
	{
		this.setNetwork(network);
		connexion = new ModelConnexion(this.getNetwork());
	}

	public void run()
	{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			String s;
			while (true)
			{
				Thread.sleep(200);
				Info.newMap = false;
				s = bufferRead.readLine();
				String[] message = s.split(";");
				Info.botInstance = Integer.parseInt(message[0]);
				message[5] = message[5].substring(1, message[5].length() - 1);

				switch (message[2])
				{
					case "i":
						Info.msgIdModel = Integer.parseInt(message[1]);
						sendToModel(message[0], message[1], "m", "rtn", message[4], this.connexion.getReturn(message[4], message[5]));
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void sendToModel(String botInstance, String msgId, String dest, String msgType, String command, Object[] param)
	{
		String newParam = "";
		for (int i = 0; i < param.length; i++)
		{
			if (i == param.length - 1)
			{
				newParam += param[i];
			}
			else
			{
				newParam += param[i] + ", ";
			}
		}
		System.out.println(String.format("%s;%s;%s;%s;%s;[%s]", botInstance, msgId, dest, msgType, command, newParam));
	}

	public static boolean waitToSend() throws InterruptedException
	{
		while (!Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange && !Info.joinedFight)
		{
			Thread.sleep(50);
		}
		while (!Info.basicNoOperationMsg)
		{
			Thread.sleep(50);
		}
		// System.out.println((!Info.newMap && !Info.Storage &&
		// !Info.StorageUpdate && !Info.leaveExchange)
		// && !Info.basicNoOperationMsg);
		// System.out.println(Info.newMap + " " + Info.Storage + " " +
		// Info.StorageUpdate + " " + Info.leaveExchange + " "
		// + Info.basicNoOperationMsg);
		if (Info.basicNoOperationMsg && !Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange && !Info.joinedFight)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void getReturn(String s) throws NumberFormatException, Exception
	{
		String[] message = s.split(";");
		message[5] = message[5].substring(1, message[5].length() - 1);
		Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], connexion.getReturn(message[4], message[5]));
	}

	public Network getNetwork()
	{
		return network;
	}

	public void setNetwork(Network network)
	{
		this.network = network;
	}

}
