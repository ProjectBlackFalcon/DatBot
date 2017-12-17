package ia.fight.test;

import java.util.ArrayList;

import ia.fight.brain.Game;
import ia.fight.brain.classes.Cra;
import ia.fight.brain.classes.Monster;
import ia.fight.structure.Player;

public class FightTest {

	public static void main(String[] args) {
		//STARTING GAME WITH MAP ID 84675595
		System.out.println(Game.executeCommand("0;403;f;cmd;[startfight,84675595]"));
		
		//Init entity 0 in cell[10,12], it's a monster with ID 0 (gobball), in team 1.
		//Stats : level 10, 400, 8PA, 4PM (max)
		Player entity0 = new Monster("gobball", 400, 8, 4, 10);
		
		//Init entity 1 in cell[6,12], it's a player with ID 0 (Cra), in team 0.
		//Stats : level 20, 1000HP, 12PA, 6PM (max)
		Player entity1 = new Cra("Faoy", 1000, 12, 6, 20);
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(entity0);
		players.add(entity1);
		
		//Init entities
		System.out.println(Game.executeCommand("0;404;f;cmd;[s,[0,0,11,12],[1,1,6,12]]", players));

		//Moving entity 0 to cell[11,12]
		System.out.println(Game.executeCommand("0;405;f;cmd;[m,0,11,12]"));
		//Entity 0 passing turn
		System.out.println(Game.executeCommand("0;406;f;cmd;[p,0]"));
		//Moving entity 1 to cell [12, 12]
		System.out.println(Game.executeCommand("0;407;f;cmd;[m,1,12,12]"));
		//Entity 1 passing turn
		System.out.println(Game.executeCommand("0;406;f;cmd;[p,1]"));
		//Entity 0 casting magic arrow to cell[9,12]. 150 damage, not a crit.
		System.out.println(Game.executeCommand("0;408;f;cmd;[c,0,9,12,'Magic arrow',150,False]"));
		//Entity 0 passing turn
		System.out.println(Game.executeCommand("0;409;f;cmd;[p,0]"));
		//Getturn request for entity 1
		System.out.println(Game.executeCommand("0;410;f;cmd;[g,1]"));
	}

}
