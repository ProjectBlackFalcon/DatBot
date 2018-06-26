package main;

import main.communication.Communication;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class Test {

	public static void main(String[] args) throws Exception {

		new d2iManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/gamedata/i18n_fr.d2i");
		new MapManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/maps");

		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
//		communication.getReturn("0;0;i;cmd;connect;[ceciestuntest2,ceciestlemdp2,Eusei,Écho]");
//		communication.getReturn("2;0;i;cmd;connect;[wublel9,notabot0,Sayerses,Julith]");
//		communication.getReturn("3;0;i;cmd;connect;['wublel6','32407c62d2f','Pot-ator','Julith']");
//		communication.getReturn("0;0;i;cmd;connect;['democraticamnesiac','answerflash2','Maxitreur','Julith']");
		communication.getReturn("0;0;i;cmd;connect;['wublel2','notabot0','Gradopr','Julith']");
		Thread.sleep(15000);
		communication.getReturn("0;0;i;cmd;openBank;[None]");
//		communication.getReturn("1;0;i;cmd;connect;[ceciestuntest2,ceciestlemdp2,Eusei,Écho]");

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
