package ia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import main.communication.DisplayInfo;
import utils.GameData;

public class Log {
	
	private int botInstance;
	private String name;
	private FileOutputStream fightDebug;
	private FileOutputStream actionsDebug;
	File fileAction;

    public Log(int botInstance, String name)  { 
    	this.botInstance = botInstance;
    	this.name = name;
    	this.fileAction = DisplayInfo.createOrGetFile(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Actions.txt");
    }
    

	/**
	 * Inits all the logs.
	 * 
	 * @author baptiste
	 */
	public void initLogs() {
		try {
			fightDebug = new FileOutputStream(DisplayInfo.createOrGetFile(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Fight.txt"));
			actionsDebug = new FileOutputStream(DisplayInfo.createOrGetFile(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Actions.txt"));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void writeActionLogMessage(String method, String s){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File(fileAction.getAbsolutePath()),
				true));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		writer.append(LocalDateTime.now() + " : [" + method + "] : " + s + "\n");
		writer.close();
		System.out.println(LocalDateTime.now() + " : [" + method + "] : " + s);
	}
	
	
    public static void writeLogDebugMessage (String msg)
    {
        System.out.println("--- "+ msg +" ---");
    }

    public static void writeLogErrorMessage (String msg)
    {
        System.out.println("!!!!!!!!!! "+ msg +" !!!!!!!!!!");
    }

}
