package Test;

import java.io.IOException;

import protocol.network.Network;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2o.D2oManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) {
		try {
			D2oManager d2oManager = new D2oManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) 153879813);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			s = s.replaceAll(",", ":");
			String[] cmd2 = s.split(":");
			System.out.println("" + Integer.parseInt(cmd2[3]) + "," + Integer.parseInt(cmd2[5]));

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
