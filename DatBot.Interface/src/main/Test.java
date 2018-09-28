package main;

import main.communication.Communication;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class Test {

	public static void main(String[] args) throws Exception {

		d2iManager.init(Main.D2I_PATH);
		MapManager.init(Main.D2P_PATH);
		

		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
		communication.getReturn("0;0;i;cmd;connect;[trevored ,azerty123uiop,Kylerine,Julith]");
		Thread.sleep(15000);
		communication.getReturn("0;0;i;cmd;openHdv;[None]");
		Thread.sleep(5000);
		communication.getReturn("0;0;i;cmd;getHdvItemStats;[{1: [70, 77, 84, 157, 158, 159, 160, 161, 279, 280, 323, 324, 325, 326], 2: [91, 267, 270, 272, 273, 329, 331, 332, 337], 3: [134, 165, 180, 181, 333, 334], 4: [140, 183, 188, 194, 200, 201, 204, 320, 335, 336, 436], 5: [97, 208, 214, 218, 219, 220, 340, 341], 6: [44, 49, 55, 58, 62, 65, 66, 67, 202, 327, 328, 339, 354, 357, 358], 7: [147, 224, 230, 233, 234, 235, 236, 237, 271, 342, 455], 8: [153, 241, 247, 250, 251, 296, 347], 9: [103, 112, 121, 278, 346, 355, 359], 10: [156, 203, 255, 259, 263, 356], 11: [127, 130, 298, 299, 348], 19: [456]}]");

		// TESTS FOR INPUTS

		// 0;0;i;cmd;connect;[ceciestuntest,ceciestlemdp1,Gladiatonme,Echo]
		// 1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosh,Julith]
		// 2;0;i;cmd;connect;[wublel7,wubwublel7,Dihydroquerina,Julith]
		// 5;0;i;cmd;connect;['wublel6','32407c62d2f','Pot-ator','Julith']
		// 0;0;i;cmd;getMonsters;[None]
		// 0;0;i;cmd;attackMonster;[None]
		// 0;0;i;cmd;getStats;[None]
		// 0;0;i;cmd;getMap;[None]
		// 0;0;i;cmd;move;[255]
	}
}
