package Main;

import java.util.concurrent.atomic.AtomicInteger;

import Game.InfoAccount;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;

public class Main {
	
    public static void main(String[] zero) throws Exception {
    	Thread modelConnexion = new Thread(new ModelConnexion());
    	modelConnexion.start();
    	int index = 0;
    	while (InfoAccount.nameAccount.equals("") || InfoAccount.password.equals("") || InfoAccount.name.equals("") || InfoAccount.server.equals("")){
    		System.out.println("Waiting for connection...");
    		Thread.sleep(1000);
//    		index++;
//    		if(index == 2){
//    			InfoAccount.nameAccount = "wublel7";
//    			InfoAccount.password = "wubwublel7";
//    			InfoAccount.name = "Dihydroquerina";
//    			InfoAccount.server = "Julith";
//    		}
    	}
    	Thread thread = new Thread(new Network());
    	Thread thread2 = new Thread(new MainPlugin());
		thread.start();
    	thread2.start();
    	
//    	while(true){
//    		index++;
//    		Thread.sleep(1000);
//    		if(index == 20){
//				CellMovement mov = Movement.MoveToCell(Integer.parseInt("32"));
//				if(mov == null || InfoAccount.cellId == Integer.parseInt("32") || mov.path == null){
//					System.out.println(false);
//				} else {
//					mov.performMovement();
//					if(InfoAccount.cellId == Integer.parseInt("32")){
//						System.out.println(true);
//					} else {
//						System.out.println(false);
//					}
//				}
//    		}
//    		
//    		if(index == 15){
//				MapMovement mapMovement = Movement.ChangeMap(504,"w");
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

