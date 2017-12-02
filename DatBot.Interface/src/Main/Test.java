package Main;

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
import protocol.network.messages.subscription.SubscriptionUpdateMessage;

public class Test {
	
    public static void main(String[] args) throws Exception {
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
		InteractiveUseRequestMessage interactiveUseRequestMessage;
		while(true){
			index++;				
			Thread.sleep(1000);
			if(index ==15){	
//				System.out.println(Map.Cells.get(396).Arrow);
//				System.out.println(Map.Cells.get(396).Blue);
//				System.out.println(Map.Cells.get(396).FarmCell);
//				System.out.println(Map.Cells.get(396).Floor);
//				System.out.println(Map.Cells.get(396).HavenbagCell);
//				System.out.println(Map.Cells.get(396).Los);
//				System.out.println(Map.Cells.get(396).Losmov);
//				System.out.println(Map.Cells.get(396).MapChangeData);
//				System.out.println(Map.Cells.get(396).Mov);
//				System.out.println(Map.Cells.get(396).MoveZone);
//				System.out.println(Map.Cells.get(396).NonWalkableDuringFight);
//				System.out.println(Map.Cells.get(396).NonWalkableDuringRP);
//				System.out.println(Map.Cells.get(396).Red);
//				System.out.println(Map.Cells.get(396).Speed);
//				System.out.println(Map.Cells.get(396).Visible);
				Info.newMap = false;
				if(Map.Id == 144931){ //Brakmar
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBrakmarIN,Bank.getSkill(Bank.interactiveBrakmarIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Network.waitForNewMap();
					System.out.println(true);
				} else if(Map.Id == 84674566){ //Astrub
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveAstrubIN,Bank.getSkill(Bank.interactiveAstrubIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Network.waitForNewMap();
					System.out.println(true);
				} else if(Map.Id == 147254){ //Bonta
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBontaIN,Bank.getSkill(Bank.interactiveBontaIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Network.waitForNewMap();
					System.out.println(true);
				} else {
					System.out.println(false);
				}
			}
		}
    }
}

