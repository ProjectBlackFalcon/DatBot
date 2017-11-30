package Main;

import Game.Info;
import Game.Plugin.Interactive;
import Game.Plugin.Stats;
import protocol.network.Network;

public class Main {
	
    public static void main(String[] args) throws Exception {
    	Thread modelConnexion = new Thread(new ModelConnexion());
    	modelConnexion.start();
    	while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals("")){
    		System.out.println("Waiting for connection...");
    		Thread.sleep(1000);
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
    }
}

