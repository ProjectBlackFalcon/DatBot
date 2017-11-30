package Main;

import Game.Info;
import Game.Plugin.Farm;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.map.Map;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;

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
		
		while(true){
			index++;				
			Thread.sleep(1000);
			if(index ==15){	
//				if(Map.Id == 153880835){
//					NpcGenericActionRequestMessage actionRequestMessage = new NpcGenericActionRequestMessage(-20001,3,153880835);
//					Network.sendToServer(actionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request NPC to go to Astrub");
//					while(!NPC.dialogOver){
//						Thread.sleep(1000);
//					}
//				System.out.println(true);
//				} else {
//					System.out.println(false);
//				}
			}
		}
    }
}

