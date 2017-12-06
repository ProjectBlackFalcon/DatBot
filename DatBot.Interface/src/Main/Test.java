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
//		ByteArrayOutputStream bous = new ByteArrayOutputStream();
//		DofusDataWriter writer = new DofusDataWriter(bous);
//		writer.writeVarLong(-1);
    	Thread modelConnexion = new Thread(new ModelConnexion());
    	modelConnexion.start();
    	int index = 0;
    	while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals("")){
    		System.out.println("Waiting for connection...");
    		Thread.sleep(1000);
    		index++;
//    		if(index == 2){
//    			Info.nameAccount = "wublel7";
//    			Info.password = "wubwublel7";
//    			Info.name = "Dihydroquerina";
//    			Info.server = "Julith";
//    		}
    		if(index == 2){
    			Info.nameAccount = "ceciestuntest";
    			Info.password = "ceciestlemdp1";
    			Info.name = "Gladiatonme";
    			Info.server = "Echo";
    		}
    	}  
    	boolean arg = false;
    	if(args.length != 0){
    		if((args[0].equals("true") || args[0].equals("True"))){
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
		Info.newMap = false;
		System.out.println(Stats.getStats());
		
		NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) NPC.npc.get(0).contextualId,3,Map.Id);
		Network.sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
		Network.waitToSend();
		Thread.sleep(2000);
		ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage = new ExchangeObjectMoveKamaMessage(-1);
		Network.sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from bank");
		System.out.println(Network.waitToSend());
		System.out.println(Stats.getStats()+","+Bank.getBank());
		Thread.sleep(5000);
		ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage1 = new ExchangeObjectMoveKamaMessage(5);
		Network.sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in bank");
		System.out.println(Network.waitToSend());
		System.out.println(Stats.getStats()+","+Bank.getBank());
    }
}

