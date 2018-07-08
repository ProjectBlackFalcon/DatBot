package Test;

import java.io.IOException;

import utils.GameData;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		new MapManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/maps");
		Map map = MapManager.FromId(191102980);
		System.out.println(map.getCells().get(171));
		System.out.println(map.getCells().get(156));
	}

}
		