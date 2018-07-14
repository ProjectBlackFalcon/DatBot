package Test;

import java.io.IOException;

import gamedata.d2o.modules.Monster;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		Map map = MapManager.FromId(191102980);
		System.out.println(map.getCells().get(171));
		System.out.println(Monster.getMonsterById(2271).isMiniBoss);
		System.out.println(Monster.getMonsterById(2271).getName());
		System.out.println("œ".replaceAll("œ", "oe"));
	}

}
		