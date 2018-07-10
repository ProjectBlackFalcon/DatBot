package Test;

import java.io.IOException;

import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		new d2iManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/gamedata/i18n_fr.d2i");
		new MapManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/maps");
		Map map = MapManager.FromId(191102980);
		System.out.println(map.getCells().get(171));
		System.out.println(GameData.isMonsterArchi(2271));
		System.out.println(GameData.getMonsterName(2271));
	}

}
		