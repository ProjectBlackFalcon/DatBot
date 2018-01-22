package Test;

import java.io.IOException;

import protocol.network.Network;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args)
	{
		try
		{
			MapManager manager = new MapManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\maps");
			Map map = manager.FromId(164364813);
//			System.out.println("Size cell : " + map.getCells().size());
			new d2iManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
			System.out.println(d2iManager.getText(GameData.getWeaponNameId(1364)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
