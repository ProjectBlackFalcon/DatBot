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
			new d2iManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
			D2oManager d2oManager = new D2oManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\Npcs.d2o");
			String s = d2oManager.searchObjectById((int) 2669);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("nameId")) { System.out.println(d2iManager.getText(Integer.parseInt(cmd2[1]))); }
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
