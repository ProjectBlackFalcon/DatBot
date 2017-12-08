package Main;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import Game.Info;
import Game.Plugin.Bank;
import Game.Plugin.Interactive;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.map.Map;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;
import protocol.network.messages.subscription.SubscriptionUpdateMessage;
import protocol.network.util.DofusDataWriter;

public class Test {

	public static void main(String[] args) throws Exception {
		// ByteArrayOutputStream bous = new ByteArrayOutputStream();
		// DofusDataWriter writer = new DofusDataWriter(bous);
		// writer.writeVarLong(-1);
		Thread modelConnexion = new Thread(new ModelConnexion());
		modelConnexion.start();
		int index = 0;
		while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("")
				|| Info.server.equals("")) {
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
			index++;
//			if (index == 2) {
//				Info.nameAccount = "wublel7";
//				Info.password = "wubwublel7";
//				Info.name = "Dihydroquerina";
//				Info.server = "Julith";
//			}
			 if(index == 2){
			 Info.nameAccount = "ceciestuntest";
			 Info.password = "ceciestlemdp1";
			 Info.name = "Gladiatonme";
			 Info.server = "Echo";
			 }
		}
		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}
		Thread thread = new Thread(new Network(arg));
		thread.start();

		while (!Info.isConnected) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Network.append("Connecté !");
		Network.append("Name : " + Info.name);
		Network.append("Niveau : " + Info.lvl);
		Thread.sleep(1000);
		
//		InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveAstrubIN,Bank.getSkill(Bank.interactiveAstrubIN));
//		Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
//		ModelConnexion.waitToSend();
		
		Thread.sleep(5000);

		CellMovement mov = Movement.MoveToCell(Integer.parseInt("396"));
		if(mov == null || mov.path == null){
			System.out.println(1);
		} else if (Info.cellId == Integer.parseInt("396")) {
			System.out.println(2);
		} else {
			mov.performMovement();
			if(Movement.moveOver()){
				if(Info.cellId == 396){
					if((Map.Id == 83887104 && Info.cellId == 396) || (Map.Id == 2884617 && Info.cellId == 424) || (Map.Id == 8912911 && Info.cellId == 424)){
						while(!Info.newMap){
							Thread.sleep(50);
						}
						System.out.println(3);
					} else {
						System.out.println(4);
					}
				} else {
					System.out.println(5);
				}						
			} else {
				System.out.println(6);
			}							
		}
	}
}
