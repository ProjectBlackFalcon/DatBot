package main.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import game.Info;
import protocol.network.Network;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
	
	private boolean displayPacket;

	public Communication(boolean arg)
	{
		this.displayPacket = arg;
	}
	

	public void run()
	{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			String s;
			while (true)
			{
				s = bufferRead.readLine();
				s = s.replaceAll(" ", "");
				String[] message = s.split(";");
				message[5] = message[5].substring(1, message[5].length() - 1);
				Object[] result = getReturn(Integer.valueOf(message[0]),message[4], message[5]);
				if(result != null){
					Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], result);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public Object[] getReturn(int botInstance, String cmd, String param) throws NumberFormatException, Exception
	{
		Object[] toSend = null;

		switch (cmd)
		{
			case "connect":
				String[] str = param.split(",");
				Info info = new Info(str[0],str[1],str[2],str[3]);
				Network network = new Network(this.displayPacket, info);
				ModelConnexion modelConnexion = new ModelConnexion(network,botInstance);
				Thread threadNetwork = new Thread(network);
				threadNetwork.start();
				Thread threadModel = new Thread(modelConnexion);
				threadModel.start();
				while (!info.isConnected())
				{
					Thread.sleep(2000);
					int index = 0;
					index += 1;
					if (index == 30) { throw new java.lang.Error("Connection timed out"); }
				}
				network.append("Connected !", false);
				network.append("Name : " + info.getName(), false);
				network.append("Level : " + info.getLvl(), false);
				toSend = new Object[] { "true" };
				//Tests
				modelConnexion.getReturn("0;0;i;cmd;getStats;[None]");
				modelConnexion.getReturn("0;0;i;cmd;getResources;[None]");
				modelConnexion.getReturn("0;0;i;cmd;getMonsters;[None]");
				modelConnexion.getReturn("0;0;i;cmd;move;[" + network.getMonsters().getMonsters().get(0).getDisposition().getCellId() +"]");
				modelConnexion.getReturn("0;0;i;cmd;attackMonster;[" + network.getMonsters().getMonsters().get(0).getContextualId() +"]");
//				while(true){
//					Thread.sleep(2000);
//					if(network.getMonsters().getMonsters().size() > 0 && !network.getInfo().isJoinedFight()){
//						modelConnexion.getReturn("0;0;i;cmd;getMonsters;[None]");
//						modelConnexion.getReturn("0;0;i;cmd;move;[" + network.getMonsters().getMonsters().get(0).getDisposition().getCellId() +"]");
//						modelConnexion.getReturn("0;0;i;cmd;attackMonster;[" + network.getMonsters().getMonsters().get(0).getContextualId() +"]");
//					}
//				}
				break;
		}
		return toSend;
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
	
	public void getReturn(String s) throws NumberFormatException, Exception
	{
		String[] message = s.split(";");
		message[5] = message[5].substring(1, message[5].length() - 1);
		Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], this.getReturn(Integer.valueOf(message[0]),message[4], message[5]));
	}
}
