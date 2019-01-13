package Test;

import java.io.IOException;
import main.Main;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		d2iManager.init(Main.D2I_PATH);
		MapManager.init(Main.D2P_PATH);
		
		Map map = MapManager.FromId(191102978);
		System.out.println(map.getSubAreaId());
	}
	
	


}
