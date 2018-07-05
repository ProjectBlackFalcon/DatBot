package Test;

import java.io.IOException;
import java.util.Random;

import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		new d2iManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/gamedata/i18n_fr.d2i");

		System.out.println(GameData.getTextInfo(46));
		System.out.println(d2iManager.getText(325865));

	}
	
	private static long stop(int deviation) throws InterruptedException{
		double gauss = new Random().nextGaussian();
		return (long) (Math.abs(gauss*deviation) * 1000);
	}
}
		