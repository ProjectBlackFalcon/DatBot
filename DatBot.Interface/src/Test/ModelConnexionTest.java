package Test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Main;
import main.communication.Communication;
import protocol.network.Network;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class ModelConnexionTest {
	
	private static Network network;
	private static Communication communication;
	/**
	 * Connect the bot and verify that the connection works
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		d2iManager.init(Main.D2I_PATH);
		MapManager.init(Main.D2P_PATH);
		communication = new Communication(true);
		Thread communication2 = new Thread(communication);
		communication2.start();
		communication.getReturn("0;0;i;cmd;connect;[leplandetest,notabot0,Leplandetest,Julith]");
		assertEquals("True", readAnswer());
		communication.getReturn("0;0;i;cmd;getMap;[None]");
		String [] resultMap = readAnswer().split(",");
		assertEquals("191106048", resultMap[4]);
		assertEquals("353", resultMap[2]);
	}
	
	private static String readAnswer() throws InterruptedException{
		while(!Communication.answer.split(";")[3].equals("rtn")){
		}
		String result =  Communication.answer.split(";")[5];
		return result.substring(1, result.length() - 1).replaceAll(" ", "");
		
	}

	@Test
	public void testMoveMovFalse() throws NumberFormatException, Exception {
		communication.getReturn("0;0;i;cmd;move;[383]");
		assertEquals("False", readAnswer());
	}

}
