package main.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import game.Info;
import protocol.network.Network;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]

	static boolean displayPacket;
	public List<ModelConnexion> models = new ArrayList<>();
	static boolean isConnecting = false;
	
	public static String answer;

	public Communication(boolean arg) {
		Communication.displayPacket = arg;
	}

	public void run() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s;
			while (true) {
				s = bufferRead.readLine();
				if (s != null && s.length() > 0) {
					s = s.replaceAll(" ", "");
					s = s.replaceAll("'", "");
					String[] message = s.split(";");
					int botId = Integer.parseInt(message[0]);
					if (modelFromBotId(botId) == null) {
						models.add(new ModelConnexion(Integer.parseInt(message[0])));
					}
					modelFromBotId(botId).getReturn(message);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void sendToModel(String botInstance, String msgId, String dest, String msgType, String command, Object[] param) {
		String newParam = "";
		for (int i = 0; i < param.length; i++) {
			if (i == param.length - 1) {
				newParam += param[i];
			}
			else {
				newParam += param[i] + ", ";
			}
		}
		String result = String.format("%s;%s;%s;%s;%s;[%s]", botInstance, msgId, dest, msgType, command, newParam);
		System.out.println(DisplayInfo.stripAccents(result));
	}

	/**
	 * Test method which is equal to the while(true) that is listening to the
	 * model
	 * 
	 * @param s message from model
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void getReturn(String s) throws NumberFormatException, Exception {
		Thread.sleep(500);
		s = s.replaceAll(" ", "");
		String[] message = s.split(";");
		int botId = Integer.parseInt(message[0]);
		if (modelFromBotId(botId) == null) {
			models.add(new ModelConnexion(Integer.parseInt(message[0])));
		}
		modelFromBotId(botId).getReturn(message);	}

	private ModelConnexion modelFromBotId(int botId) {
		for (ModelConnexion modelConnexion : models) {
			if (modelConnexion.botInstance == botId) { return modelConnexion; }
		}
		return null;
	}

	public List<ModelConnexion> getModels() {
		return models;
	}

	public void setModels(List<ModelConnexion> models) {
		this.models = models;
	}
}
