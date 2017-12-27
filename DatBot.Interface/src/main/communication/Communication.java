package main.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import game.Info;
import protocol.network.Network;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
	
	protected Network network;
	private ModelConnexion connexion;
	private Info info;
	
	public Communication(Network network)
	{
		this.setNetwork(network);
		this.setInfo(network.getInfo());
		connexion = new ModelConnexion(this.getNetwork(),this);
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
				info.setNewMap(false);
				s = bufferRead.readLine();
				String[] message = s.split(";");
				info.setBotInstance(Integer.parseInt(message[0]));
				message[5] = message[5].substring(1, message[5].length() - 1);

				switch (message[2])
				{
					case "i":
						info.setMsgIdModel(Integer.parseInt(message[1]));
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

	public boolean waitToSend() throws InterruptedException
	{
		while (!info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight())
		{
			Thread.sleep(50);
		}
		while (!info.isBasicNoOperationMsg())
		{
			Thread.sleep(50);
		}
		// System.out.println((!Info.newMap && !Info.Storage &&
		// !Info.StorageUpdate && !Info.leaveExchange)
		// && !Info.basicNoOperationMsg);
		// System.out.println(Info.newMap + " " + Info.Storage + " " +
		// Info.StorageUpdate + " " + Info.leaveExchange + " "
		// + Info.basicNoOperationMsg);
		if (info.isBasicNoOperationMsg() && !info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight())
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

	public Info getInfo()
	{
		return info;
	}

	public void setInfo(Info info)
	{
		this.info = info;
	}

}
