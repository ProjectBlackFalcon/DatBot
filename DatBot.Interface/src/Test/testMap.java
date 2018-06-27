package Test;

import java.io.IOException;
import utils.GameData;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
//		double test = ((double)148)/200;
//		new d2iManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
		System.out.println(GameData.getEffectIdFromSpell(161,76944).intValue());
		System.out.println(GameData.getKeyCaracs(GameData.getCharacFromEffect(GameData.getEffectIdFromSpell(161,76944).intValue())));
//		for(int i = 1; i <= 110;i++){
//			System.out.println(GameData.getKeyCaracs(i));
//		}
	}
}
		