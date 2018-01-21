package main.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import game.Info;
import protocol.network.Network;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
	
	private boolean displayPacket;
	private List<Network> networks;

	public Communication(boolean arg)
	{
		this.displayPacket = arg;
		this.networks = new ArrayList<>();
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
				s = s.replaceAll("'", "");
				System.out.println(s);
				String[] message = s.split(";");
				message[5] = message[5].substring(1, message[5].length() - 1);
				Object[] result = getReturn(Integer.valueOf(message[0]),message[4], message[5]);
				if(result != null){
					Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], result);
				} else {
					for (Network n : networks)
					{
						if(n.getBotInstance() == Integer.valueOf(message[0])){
							n.getReturn(message);
						}
					}
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
		
		param = param.replaceAll(" ", "");
		param = param.replaceAll("'", "");
		
		switch (cmd)
		{
			case "connect":
				String[] str = param.split(",");
				Info info = new Info(str[0],str[1],str[2],str[3]);
				Network network = new Network(this.displayPacket, info, botInstance);
				Thread threadNetwork = new Thread(network);
				threadNetwork.start();
				networks.add(network);
				while (!info.isConnected())
				{
					Thread.sleep(2000);
					int index = 0;
					index += 1;
					if (index == 30) { throw new java.lang.Error("Connection timed out"); }
				}
				network.append("Connected !");
				network.append("Name : " + info.getName());
				network.append("Level : " + info.getLvl());
				toSend = new Object[] { "True" };
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
	
	/**
	 * Test method which is equal to the while(true) that is listening to the model
	 * @param s message from model
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void getReturn(String s) throws NumberFormatException, Exception
	{
		s = s.replaceAll(" ", "");
		String[] message = s.split(";");
		message[5] = message[5].substring(1, message[5].length() - 1);
		Object[] result = getReturn(Integer.valueOf(message[0]),message[4], message[5]);
		if(result != null){
			Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], result);
		} else {
			for (Network n : networks)
			{
				if(n.getBotInstance() == Integer.valueOf(message[0])){
					n.getReturn(message);
				}
			}
		}
	}


	public List<Network> getNetworks()
	{
		return networks;
	}
}
