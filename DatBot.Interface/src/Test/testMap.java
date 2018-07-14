package Test;

import java.io.IOException;

import utils.d2o.modules.Monster;
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
		