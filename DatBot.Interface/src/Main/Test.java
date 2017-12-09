package Main;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import Game.Info;
import Game.Plugin.Bank;
import Game.Plugin.Interactive;
import Game.Plugin.Monsters;
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
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
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
			if (index == 2) {
				Info.nameAccount = "wublel7";
				Info.password = "wubwublel7";
				Info.name = "Dihydroquerina";
				Info.server = "Julith";
			}
//			 if(index == 2){
//			 Info.nameAccount = "Jemappellehenry2";
//			 Info.password = "azerty123henry";
//			 Info.name = "Baddosh";
//			 Info.server = "Julith";
//			 }
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
		System.out.println(Monsters.getMonsters());
	}
}
