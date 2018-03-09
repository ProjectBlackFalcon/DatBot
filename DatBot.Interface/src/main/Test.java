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

		new d2iManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
		new MapManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\maps");

		boolean arg = true;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();

//		communication.getReturn("0;0;i;cmd;connect;['debugthemall','azertyuiop1','Le-Gros-Veineux','Julith']");
//		communication.getReturn("0;0;i;cmd;connect;[flasheowine,mdppourlysandre,Capillotracteur,Julith]");
//		communication.getReturn("0;0;i;cmd;connect;[ceciestuntest,ceciestlemdp1,Gladiatonme,Echo]");
//		communication.getReturn("0;0;i;cmd;connect;[jikiwa221,***REMOVED***11,Faoy,Julith]");
//		communication.getReturn("1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosh,Julith]");
//		communication.getReturn("2;0;i;cmd;connect;[wublel7,wubwublel7,Dihydroquerina,Julith]");
//		communication.getReturn("3;0;i;cmd;connect;['wublel6','32407c62d2f','Pot-ator','Julith']");
//		communication.getReturn("0;0;i;cmd;connect;['wublel2','notabot0','Scalpelementaire','Julith']");
//		communication.getReturn("0;0;i;cmd;connect;['wublel10','notabot0','Gaspienura','Julith']");
//		Thread.sleep(5000);
		communication.getReturn("0;0;i;cmd;connect;['wublel5','notabot0','Ilancelet','Julith']");
//		Thread.sleep(5000);

//		communication.getReturn("0;0;i;cmd;connect;[C'wublel5','notabot0','Ilancelet','Julith']");
//		communication.getReturn("0;0;i;cmd;connect;['wublel10','notabot0','Gaspienura','Julith']");
		Thread.sleep(2000);
//		communication.getReturn("0;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosch,Julith]");
//		communication.getReturn("0;0;i;cmd;move;[383]");
//		communication.getReturn("0;0;i;cmd;getZaapiCell;[None]");
//		communication.getReturn("0;0;i;cmd;useZaapi;[(-24,40)]");

//
//		communication.getReturn("0;0;i;cmd;fart;[None]");

//		communication.getReturn("0;0;i;cmd;getHdvItemStats;[1984]");
//		Thread.sleep(2000);
//		communication.getReturn("0;0;i;cmd;getStats;[None]");
//		Thread.sleep(20000);
//		communication.getReturn("0;0;i;cmd;getStats;[None]");
//		Thread.sleep(5000);
//		communication.getReturn("0;0;i;cmd;getStats;[None]");
//		int id = 0; 
//		for(int i = 0; i < communication.getNetworks().get(0).getStats().getInventoryContentMessage().getObjects().size() ; i++){
//			if(communication.getNetworks().get(0).getStats().getInventoryContentMessage().getObjects().get(i).getObjectGID() == 1984){
//				id = communication.getNetworks().get(0).getStats().getInventoryContentMessage().getObjects().get(i).getObjectUID();
//			}
//		}

//		communication.getReturn("0;0;i;cmd;withdrawItem;[" + 1984 + ",1,1]");


		communication.getReturn("0;0;i;cmd;changeMap;[5,n]");
//		communication.getReturn("0;0;i;cmd;enterBag;[None]");
//		Thread.sleep(5000);
//		communication.getReturn("0;0;i;cmd;useZaap;[(-32,-56)]");
//		Thread.sleep(5000);
//		communication.getReturn("0;0;i;cmd;getMap;[0,n]");

//		communication.getReturn("0;0;i;cmd;abandonHunt;[None]");
		
		
		//communication.getReturn("0;0;i;cmd;huntFight;[None]");

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
