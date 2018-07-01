package Test;

import java.io.IOException;
import java.util.Random;

import utils.GameData;
import utils.d2p.MapManager;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		new MapManager("D:/Ankama/Dofus2/app/content/gfx/world");
		MapManager.FromId(32460);

	}
	
	private static long stop(int deviation) throws InterruptedException{
		double gauss = new Random().nextGaussian();
		return (long) (Math.abs(gauss*deviation) * 1000);
	}
}
		