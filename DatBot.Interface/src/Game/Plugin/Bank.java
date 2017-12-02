package Game.Plugin;

import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementSkill;

public class Bank {
	
	public static int cellIdAstrubIN = 317;
	public static int interactiveAstrubIN = 465440;
	public static int cellIdAstrubOUT = 396;
	
	public static int cellIdBontaIN = 353;
	public static int interactiveBontaIN = 433934;
	public static int cellIdBontaOUT = 424;
	
	public static int cellIdBrakmarIN = 246;
	public static int interactiveBrakmarIN = 415350;
	public static int cellIdBrakmarOUT = 424;
	
	public static int getSkill(int interactive){
		for (InteractiveElement i : Interactive.interactiveElements) {
			if(i.elementId == interactive){
				return i.enabledSkills.get(0).skillInstanceUid;
			}
		}
		return -1;
	}

}
