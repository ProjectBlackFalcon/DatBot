package Main;

import java.util.concurrent.atomic.AtomicInteger;

import Game.InfoAccount;
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
    }
}

