package main;

import main.communication.Communication;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class Main {
	
	public static final String CLIENT_PATH =  GameData.getPathDatBot() + "/DatBot.Interface/utils/";
	public static final String D2P_PATH = CLIENT_PATH + "maps/";
	public static final String D2O_PATH = CLIENT_PATH + "common/";
	public static final String D2I_PATH = CLIENT_PATH + "i18n/i18n_fr.d2i";

	public static void main(String[] args) throws Exception {

		d2iManager.init(D2I_PATH);
		MapManager.init(D2P_PATH);

		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
	}
}



























