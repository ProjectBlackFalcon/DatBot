package Main;

import java.util.concurrent.atomic.AtomicInteger;

import protocol.network.Network;

public class Main {
	
    public static void main(String[] zero) throws Exception {
    	final AtomicInteger at = new AtomicInteger(0);
    	Network network  = new Network(at);
    	Thread thread = new Thread(network);
    	Thread thread2 = new Thread(new MainPlugin(at));
		thread.start();
    	thread2.start();
    	
    }
}

