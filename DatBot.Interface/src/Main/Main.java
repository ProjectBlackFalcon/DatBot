package Main;

import java.util.concurrent.atomic.AtomicInteger;

import Game.Info;
import Game.Plugin.Farm;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;

public class Main {
	
    public static void main(String[] args) throws Exception {
//    	Thread modelConnexion = new Thread(new ModelConnexion());
//    	modelConnexion.start();
//    	int index = 0;
    	Info.nameAccount = args[0];
    	Info.password = args[1];
    	Info.name = args[2];
    	Info.server = args[3];
    	while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals("")){
    		System.out.println("Waiting for connection...");
    		Thread.sleep(1000);
//    		index++;
//    		if(index == 2){
//    			Info.nameAccount = "wublel7";
//    			Info.password = "wubwublel7";
//    			Info.name = "Dihydroquerina";
//    			Info.server = "Julith";
//    		}
//    		if(index == 2){
//    			Info.nameAccount = "ceciestuntest";
//    			Info.password = "ceciestlemdp1";
//    			Info.name = "Gladiatonme";
//    			Info.server = "Echo";
//    		}
    	}    	
    	Thread thread = new Thread(new Network());
    	Thread thread2 = new Thread(new MainPlugin());
		thread.start();
    	thread2.start();
    	
//    	while(true){
//    		index++;
//    		Thread.sleep(1000);
//
//    		if(index == 15){
//    			if(Farm.harvestCell(Integer.parseInt("303"))){
//        			System.out.println(Farm.lastItemHarvested);
//        			System.out.println(Farm.quantityLastItemHarvested);
//        		} 
////    			else if (){
////    				TODO AGGRO
////    			}					
//    			else {
//    				System.out.println(false);
//    			}
//    		}
//    		if(index == 15){
//				MapMovement mapMovement = Movement.ChangeMap(112,"w");
//				if (mapMovement == null) {
//					System.out.println(false);
//					MainPlugin.frame.append("Déplacement impossible ! Un obstacle bloque le chemin !");
//				}
//				else {
//					mapMovement.PerformChangement();
//					if(Movement.moveOver()){
//						System.out.println(true);
//					} else {
//						System.out.println(false);
//					}
//				}
//    		}    		
//    	}
    }
}

