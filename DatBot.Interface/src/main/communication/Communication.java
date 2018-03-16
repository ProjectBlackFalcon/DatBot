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

	private boolean displayPacket;
	private static List<Network> networks;
	public static boolean isConnecting = false;

	public Communication(boolean arg) {
		this.displayPacket = arg;
		this.networks = new ArrayList<>();
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
					message[5] = message[5].substring(1, message[5].length() - 1);
					if (message[4].equals("connect") || message[4].equals("disconnect")) {
						while (isConnecting) {
							Thread.sleep(1000);
						}
						isConnecting = true;
						getReturn(Integer.valueOf(message[0]), Integer.valueOf(message[1]), message[4], message[5], this.displayPacket);
					}
					else {
						while (networks.size() < Integer.valueOf(message[0])) {
							Thread.sleep(1000);
						}
						while (!networks.get(getIndexOfNetwork(Integer.valueOf(message[0]))).getInfo().isConnected()) {
							Thread.sleep(1000);
						}
						networks.get(getIndexOfNetwork(Integer.valueOf(message[0]))).getReturn(message);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getReturn(int botInstance, int msgId, String cmd, String param2, boolean displayPacket) throws NumberFormatException, Exception {
		new Thread(new Runnable() {
			public void run() {
				try {
					Object[] toSend = null;

					String param = param2.replaceAll(" ", "");
					param = param.replaceAll("'", "");

					switch (cmd) {
						case "connect":
							if (getIndexOfNetwork(botInstance) != -1) {
								networks.get(getIndexOfNetwork(botInstance)).getInfo().setConnected(false);
								Network network = new Network(displayPacket, networks.get(getIndexOfNetwork(botInstance)).getInfo(), botInstance);
								network.getInfo().setMsgIdModel(msgId);
								Thread threadNetwork = new Thread(network);
								threadNetwork.start();
								networks.set(getIndexOfNetwork(botInstance), network);
								int index = 0;
								while (!networks.get(getIndexOfNetwork(botInstance)).getInfo().isConnected()) {
									Thread.sleep(2000);
									index += 1;
									if (index == 60) { 
										System.out.println("Connection timed out"); 
										Communication.sendToModel(String.valueOf(botInstance), String.valueOf(msgId), "m", "rtn", cmd, new Object[] { "False" });
										return;
									}
								}
								networks.get(getIndexOfNetwork(botInstance)).append("Connected !");
								networks.get(getIndexOfNetwork(botInstance)).append("Name : " + networks.get(getIndexOfNetwork(botInstance)).getInfo().getName());
								networks.get(getIndexOfNetwork(botInstance)).append("Level : " + networks.get(getIndexOfNetwork(botInstance)).getInfo().getLvl());
								toSend = new Object[] { "True" };
							}
							else {
								String[] str = param.split(",");
								Info info = new Info(str[0], str[1], str[2], str[3]);
								Network network = new Network(displayPacket, info, botInstance);
								Thread threadNetwork = new Thread(network);
								threadNetwork.start();
								networks.add(network);
								int index = 0;
								while (!info.isConnected()) {
									Thread.sleep(2000);
									index += 1;
									if (index == 60) { 
										System.out.println("Connection timed out"); 
										Communication.sendToModel(String.valueOf(botInstance), String.valueOf(msgId), "m", "rtn", cmd, new Object[] { "False" });
										return;
									}
								}
								network.append("Connected !");
								network.append("Name : " + info.getName());
								network.append("Level : " + info.getLvl());
								toSend = new Object[] { "True" };
							}
							break;
						case "disconnect":
							if (displayPacket) {
								networks.get(getIndexOfNetwork(botInstance)).getF().setVisible(false);
							}
							networks.get(getIndexOfNetwork(botInstance)).getInfo().setConnected(false);
							networks.get(getIndexOfNetwork(botInstance)).getSocket().close();
							toSend = new Object[] { "True" };
							break;
					}
					Communication.sendToModel(String.valueOf(botInstance), String.valueOf(msgId), "m", "rtn", cmd, toSend);
					isConnecting = false;
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return;
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
		System.out.println(String.format("%s;%s;%s;%s;%s;[%s]", botInstance, msgId, dest, msgType, command, newParam));
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
		s = s.replaceAll(" ", "");
		String[] message = s.split(";");
		message[5] = message[5].substring(1, message[5].length() - 1);
		if (message[4].equals("connect") || message[4].equals("disconnect")) {
			while (isConnecting) {
				Thread.sleep(1000);
			}
			isConnecting = true;
			getReturn(Integer.valueOf(message[0]), Integer.valueOf(message[1]), message[4], message[5], this.displayPacket);
		}
		else {
			while (getIndexOfNetwork(Integer.valueOf(message[0])) == - 1) {
				Thread.sleep(1000);
			}
			while (!networks.get(getIndexOfNetwork(Integer.valueOf(message[0]))).getInfo().isConnected()) {
				Thread.sleep(1000);
			}
			networks.get(getIndexOfNetwork(Integer.valueOf(message[0]))).getReturn(message);
		}
	}

	public int getIndexOfNetwork(int botInstance) {
		for (int i = 0; i < networks.size(); i++) {
			if (networks.get(i).getBotInstance() == botInstance) { return i; }
		}
		return -1;
	}

	public List<Network> getNetworks() {
		return networks;
	}
}
