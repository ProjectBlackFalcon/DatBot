package main;

import main.communication.Communication;
import protocol.network.Network;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class Main {

	public static void main(String[] args) throws Exception {

		new d2iManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/gamedata/i18n_fr.d2i");
		new MapManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/maps");

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
