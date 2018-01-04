package main;

import java.util.Scanner;

import main.communication.Communication;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class Test {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception
	{
		boolean arg = false;
		if (args.length != 0)
		{
			if ((args[0].equals("true") || args[0].equals("True")))
			{
				arg = true;
			}
		}
		
		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
		
		while(true){
			Thread.sleep(1000);
			new Scanner(System.in);
		}
		
//		TESTS FOR INPUTS
		
//		0;0;i;cmd;connect;[ceciestuntest,ceciestlemdp1,Gladiatonme,Echo]
//		1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosh,Julith]
//		2;0;i;cmd;connect;[wublel7,wubwublel7,Dihydroquerina,Julith]
//		0;0;i;cmd;getMonsters;[None]
//		0;0;i;cmd;getStats;[None]
//		0;0;i;cmd;getMap;[None]
//		0;0;i;cmd;move;[255]
 	}
}
