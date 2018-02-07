package main;

import java.util.Scanner;

import main.communication.Communication;
import protocol.network.Network;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2o.D2oManager;
import utils.d2p.MapManager;

public class Test {

	public static void main(String[] args) throws Exception {

		new d2iManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
		new MapManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\maps");

		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
		communication.getReturn("0;0;i;cmd;connect;[ceciestuntest,ceciestlemdp1,Gladiatonme,Echo]");
		// communication.getReturn("1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosh,Julith]");
		// communication.getReturn("2;0;i;cmd;connect;[wublel7,wubwublel7,Dihydroquerina,Julith]");
		// communication.getReturn("3;0;i;cmd;connect;['wublel6','32407c62d2f','Pot-ator','Julith']");
		// communication.getReturn("1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosch,Julith]");

		// /**
		// * ChangeMapTest method Input : North, South, East, West
		// */
		// communication.getReturn("0;0;i;cmd;changeMapTest;[West]");

//		while (true) {
//			Thread.sleep(2000);
//			if (!communication.getNetworks().get(0).getInfo().isJoinedFight() && communication.getNetworks().get(0).getInfo().isWaitForMov() && communication.getNetworks().get(0).getMonsters().getMonsters().size() > 0) {
//				double idMonster = communication.getNetworks().get(0).getMonsters().getMonsters().get(0).getContextualId();
//				int cellIdMonster = communication.getNetworks().get(0).getMonsters().getMonsters().get(0).getDisposition().getCellId();
//				communication.getNetworks().get(0).append("Trying to start a fight...");
//				communication.getReturn("0;0;i;cmd;getMonsters;[None]");
//				communication.getReturn("0;0;i;cmd;move;[" + cellIdMonster + "]");
//				communication.getReturn("0;0;i;cmd;attackMonster;[" + idMonster + "]");
//			}
//		}

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
