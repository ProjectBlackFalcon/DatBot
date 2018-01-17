package ia.fight.brain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import ia.fight.brain.PlayingEntity;
import ia.fight.brain.classes.Cra;
import ia.fight.map.CreateMap;
import ia.fight.map.GameViz;
import ia.fight.map.LineOfSight;
import ia.fight.map.Map;
import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.Spell;
import ia.fight.structure.spells.SpellObject;
import ia.fight.structure.spells.Type;
import ia.fight.structure.spells.spelltypes.Damage;

public class Game {

	static final int RANDOM = -1;
	static final boolean DISPLAY_GUI = true;
	
	static public Map map;
	static public ArrayList<PlayingEntity> playingEntities;
	static private GameViz los;
	private static int current_command_nbr = 0;
	
	public Game() {
		
	}
	
	public Game(int map) {
		/*
		String[] entities = new String[2];
		// Player ID / posX / posY / Player or Monster / Player type (0:cra, 1:Enu, ...) / Team / HP Max / AP Max / MP Max
		entities[0] = "0;10;12;p;0;1;2020;11;4";
		entities[1] = "1;6;12;m;0;0;200;15;5";
		ArrayList<String> refreshMessage = new ArrayList<>();

		refreshMessage.add("0;m;11;12");
		refreshMessage.add("0;p");
		refreshMessage.add("1;m;12;12");
		refreshMessage.add("1;p");
		refreshMessage.add("0;c;9;12;Magic arrow;150;false");
		//refreshMessage.add("0;c;10;12;Dispersing arrow;0;false");
		refreshMessage.add("0;p");
		//refreshMessage.add("1;c;13;12;Magic arrow;150;false");
		refreshMessage.add("1;g");
		initGame(map);
		initEntities(entities);

		try {
			Thread.sleep(500);
			Game.log.println("Starting in 1 second ...");
			Thread.sleep(1000);
			for(int i = 0; i < refreshMessage.size(); i++) {
				refresh(refreshMessage.get(i));
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Initializes the game map. Creates a map and opens a JFrame.
	 * @param map
	 */
	public static void initGame(int map) {
		Map mapObject = CreateMap.getMapById(map);
		Game.map = mapObject;
		los = new GameViz(mapObject);
	}
	
	/**
	 * Ends the game by disposing of the window.
	 */
	public static void endGame() {
		Game.playingEntities.clear();
		los.dispose();
	}
	
	/**
	 * Initializes entities according to the passed arrayList
	 * @param entities Entities to be initialized.
	 */
	static public void initEntities(ArrayList<PlayingEntity> entities) {
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
		
		for(int i = 0; i < entities.size(); i++) {

			playingEntities.add(entities.get(i));
			
			Game.log.println(entities.get(i).getModel() == null ? "No model currently selected." : entities.get(i).getModel()+" in pos "+entities.get(i).getPosition());
		}

		
		los.update(playingEntities);
		
		Game.playingEntities = playingEntities;
	}
	
	/**
	 * main method called to execute the commands.
	 * @param commandString
	 */
	static public void refresh(String commandString) {
		String[] command = commandString.split(";");
		int id = Integer.parseInt(command[0]);
		
		if(Game.getPlayingEntityFromID(id) == null) {
			System.err.println("Entity with id "+id+" is dead !");
			return;
		}
		
		String actionType = command[1];

		//RECEIVED A MOVEMENT COMMAND
			 if(actionType.equals("m")) {
			executeMovementCommand(command);
		}
		//RECEIVED A MOVEMENT COMMAND
		else if(actionType.equals("c")) {
			executeSpellCommand(command);
		}
		//RECEIVED A PASS TURN COMMAND
		else if(actionType.equals("p")) {
			executePassTurn(command);
		}
		//RECEIVED A GET TURN COMMAND
		else if(actionType.equals("g")) {
			getBestTurn(command);
		}
			 
		los.update(playingEntities);
		
		if(DISPLAY_GUI)
			los.repaint();
	}
	
	/**
	 * Returns the playingEntity corresponding to the ID passed as a parameter
	 * @param id ID of the playingEntity seeked after
	 * @return a PlayingEntity object
	 */
	private static PlayingEntity getPlayingEntityFromID(int id) {
		for(int i = 0; i < Game.playingEntities.size(); i++) {
			if(playingEntities.get(i).getID() == id) {
				return Game.playingEntities.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a spell according to the class passed as a parameter and the name of the spell.
	 * @param name Name of the spell
	 * @param playerClass Name of the class
	 * @return a SpellObject object
	 */
	private static SpellObject getSpellFromName(String name, String playerClass) {
		if(playerClass.equals("cra")) {
			return CraModel.getSpellFromName(name);
		}else if(playerClass.equals("monster")){
			SpellObject spell = new SpellObject("MonsterSpell", 0, 0);
			spell.addSpell(new Damage(Type.AIR, 0, 0, 0, 0, spell));
			System.out.println();
			return spell;
		}
		
		return null;
	}
	
	private static SpellObject getSpellFromID(int id) {
		return CraModel.getSpellFromID(id);
	}
	
	/**
	 * Executes a movement command
	 * @param command
	 */
	static private void executeMovementCommand(String[] command){
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		playingEntity.getModel().removeMP(Position.distance(new Position(posX, posY), playingEntity.getPosition()));
		playingEntity.setPosition(new Position(posX, posY));
		Game.log.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
	}
	
	/**
	 * Executes a spell command
	 * @param command
	 */
	static private void executeSpellCommand(String[] command) {
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		for(String s : command) {
			System.out.println(s);
		}
		
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		
		String spellname = command[5].replace("'", "");
		int damage = Integer.parseInt(command[6]);
		boolean crit = Boolean.parseBoolean(command[7]);

		Game.log.println("Casting "+spellname+" to : ["+posX+";"+posY+"]"+" for "+damage+" damage.");
		SpellObject spellCast = Game.getSpellFromName(spellname, "cra");

		Game.log.println(spellCast);
		Game.log.println("Printing simple name : " + castingEntity.getClass().getSimpleName());

		if(castingEntity.getModel().getType().equals("monster")) {
			spellCast = Game.getSpellFromName(spellname, "monster");
			Game.log.println(spellCast);
		}else {
			spellCast = Game.getSpellFromName(spellname, "cra");
			Game.log.println(spellCast);
		}
		if(spellCast == null) {
			spellCast = Game.getSpellFromID(Integer.parseInt(command[4]));
		}

		Game.log.println(spellCast);		
		spellCast = Game.getSpellFromName(spellname, "monster");
		Game.log.println(spellCast);
		
		if(castingEntity.getModel().getType().equals("monster")) {
			spellCast = Game.getSpellFromName(spellname, "monster");
			Game.log.println(spellCast);
		}else {
			spellCast = Game.getSpellFromName(spellname, "cra");
			Game.log.println(spellCast);
		}
		
		if(spellCast == null) {
			spellCast = Game.getSpellFromID(Integer.parseInt(command[4]));
		}
		
		spellCast.applySpells(castingEntity, new Position(posX, posY), true, damage);
	}
	
	/**
	 * Executes a pass turn command
	 * @param command
	 */
	static private void executePassTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		castingEntity.getModel().resetAP();
		castingEntity.getModel().resetMP();
		castingEntity.getModel().removeOneBuffTurn();
		castingEntity.getModel().updateSpellsStatus();
		
		log.println("Entity "+id+" passing turn.");
	}
	
	static private PlayingEntity getClosestEnnemy(PlayingEntity caster) {
		ArrayList<PlayingEntity> ennemies = new ArrayList<>();
		
		for(int i = 0; i < playingEntities.size(); i++) {
			if(!playingEntities.get(i).getTeam().equals(caster.getTeam())) {
				ennemies.add(playingEntities.get(i));
				System.out.println(playingEntities.get(i).getPosition());
			}
		}
		
		System.out.println(ennemies);
		
		PlayingEntity selectedEnnemy = ennemies.get(0);
		int distance = Position.distance(selectedEnnemy.getPosition(), caster.getPosition());
		
		for(PlayingEntity p : ennemies) {
			if(Position.distance(p.getPosition(), caster.getPosition()) < distance) {
				distance = Position.distance(p.getPosition(), caster.getPosition());
				selectedEnnemy = p;
			}
		}
		
		return selectedEnnemy;
	}
	
	static private Position getClosestPositionFromArrayList(ArrayList<Position> positions, Position position) {
		int distance = Position.distance(positions.get(0), position);
		Position selected = positions.get(0);
		
		for(int i = 1; i < positions.size(); i++) {
			if(distance > Position.distance(positions.get(i), position)) {
				selected = positions.get(i);
				distance = Position.distance(positions.get(i), position);
			}
		}
		
		return selected;
		
	}
		
	/**
	 * Method called to get the best turn. Returns a single action, either movement or spellcast.
	 * @param command
	 * @return
	 */
	static private String getBestTurn(String[] command) {
		int id = (int) Long.parseLong(command[0]);

		PlayingEntity caster = getPlayingEntityFromID(id);
		PlayingEntity victim = getClosestEnnemy(caster);
		
		System.out.println();
		
		ArrayList<Position> accessiblePositions = new ArrayList<>();
		accessiblePositions.add(caster.getPosition());
		
		for(int k = caster.getPosition().getX() - caster.getModel().getMP(); k < caster.getPosition().getX() + caster.getModel().getMP()+1; k++) {
			for(int l = caster.getPosition().getY() - caster.getModel().getMP(); l < caster.getPosition().getY() + caster.getModel().getMP()+1; l++) {
				if(Game.map.isPositionAccessible(caster.getPosition(), new Position(k,l), caster.getModel().getMP())) {
					accessiblePositions.add(new Position(k, l));
				}
			}
		}
 
		int totalDamage = 0;
		int maxDamage = 0;
		ArrayList<bestEnemyAndTurn> bestPositions = new ArrayList<>();
		
		for(int i = 0; i < accessiblePositions.size(); i++) {
			ArrayList<SpellObject> turn = caster.getOptimalTurnFrom(caster.getPosition(), victim);
			
			for(int j = 0; j < turn.size(); j++) {
				totalDamage += turn.get(j).getDamagePreviz(caster, victim);
			}

			if(totalDamage > maxDamage) {
				maxDamage = totalDamage;
				bestPositions.clear();
				bestPositions.add(new bestEnemyAndTurn(victim, accessiblePositions.get(i), maxDamage, turn));
			}else if(totalDamage == maxDamage) {
				bestPositions.add(new bestEnemyAndTurn(victim, accessiblePositions.get(i), maxDamage, turn));
			}
			
			totalDamage = 0;
		}
		
		int minDistanceBetweenOptimalPositionAndEntity = 1000;
		bestEnemyAndTurn selectedPosition = bestPositions.get(0);
		
		for(int i = 0; i < bestPositions.size(); i++) {
			int distance = Position.distance(bestPositions.get(i).position, caster.getPosition());
			if(distance < minDistanceBetweenOptimalPositionAndEntity) {
				minDistanceBetweenOptimalPositionAndEntity = distance;
				selectedPosition = bestPositions.get(i);
			}
		}
		
		ArrayList<SpellObject> turn = caster.getOptimalTurnFrom(selectedPosition.position, victim);
		System.out.println("AP remaining : "+caster.getModel().getAP()+"TURN : " +turn);
		
		String action = "";
		
		if(!selectedPosition.position.deepEquals(caster.getPosition())) {
			action += command[0]+",m,"+selectedPosition.position.getX()+","+selectedPosition.position.getY();
		}else {
			if(turn.size() < 1) {
				Position desired = getClosestPositionFromArrayList(accessiblePositions, victim.getPosition());
				System.out.println("Accessible positions : "+accessiblePositions);
				System.out.println("MP available : "+caster.getModel().getMP());
				System.out.println("Actual position : "+caster.getPosition());
				System.out.println("Ennemy position : "+victim.getPosition());
				System.out.println("Desired position : "+desired);
				if(!caster.getPosition().deepEquals(desired)) {
					action += command[0]+",m,"+desired.getX()+","+desired.getY();
				}else {
					action += command[0]+",None";
				}
			}else {
				action += command[0]+",c,"+selectedPosition.turn.get(0).getID()+","+turn.get(0).getName()+","+victim.getPosition().getX()+","+victim.getPosition().getY();
			}
			
		}
		
		return action;
	}
	
	/**
	 * Class to hold an entity, a position, damage and an arraylist of spells as a single entity.
	 * @author jikiw
	 *
	 */
	static class bestEnemyAndTurn{
		
		PlayingEntity entity;
		Position position;
		int damage;
		ArrayList<SpellObject> turn;
		
		public bestEnemyAndTurn(PlayingEntity entity, Position position, int damage, ArrayList<SpellObject> turn) {
			this.entity = entity;
			this.position = position;
			this.damage = damage;
			this.turn = turn;
		}
	}
	
	/**
	 * Returns true if the specified position is targetable by the caster given the spell. Takes into account the range, the line of sight
	 * and the straight line cast.
	 * @param caster PlayingEntity that casts the spell
	 * @param spell Spell cast
	 * @param cell Cell targeted
	 * @return boolean
	 * 
	 * @author jikiw
	 */
	boolean isCellTargetableBySpell(PlayingEntity caster, SpellObject spell, Position cell){
		
		int distance = Position.distance(caster.getPosition(), cell);
		
		if(spell.isModifiableRange()) {
			if(distance < spell.getMinimumRange() || distance > spell.getMaximumRange() + caster.getModel().getRange()) {
				return false;
			}
		}else {
			if(distance < spell.getMinimumRange() || distance > spell.getMaximumRange()) {
				return false;
			}
		}
		
		
		if(spell.isStraightLineCast() && caster.getPosition().getX() != cell.getX() && caster.getPosition().getY() != cell.getY()){
			return false;
		}
		
		if(spell.requiresLineOfSight()) {
			return LineOfSight.visibility(caster.getPosition(), cell, map.getBlocks());
		}else {
			return true;
		}
	}
	
	public static PrintStream log;
	public static PrintStream com;
	
	/**
	 * Executes any command that needs some players specified. Currently the only command available is the "init entities" command.
	 * @param s command String<br>
	 * 
	 * Commands are written as follows : bot_instance;action_number;bot_part;type_of_message;command;parameters<br>
	 * 
	 * Bot instance : Which bot is currently asking/returning the command/return. The default is 0.<br>
	 * 
	 * Action number : Number corresponding to the action being done right now. It increases as the bot executes commands.<br>
	 * 
	 * Bot part : 'f' for this bot (fight bot). Other letters can be found as 'm' for model and 'i' for interface.<br>
	 * 
	 * Type of message : "cmd" if the message is a command, "rtn" if the message is a return.<br><br>
	 * 
	 * <b>Command : Type of command to the specified bot.</b> Commands accepted : <br>
	 * 		<b>s</b> - Spawns the entities passed as parameters at the locations specified, with given ID and team.<br>
	 * 
	 * Parameters : the parameters passed as a String. These vary upon the type of the command.<br><br>
	 * 
	 * -- Examples -- <br><br>
	 * 
	 * <b>Init entities</b><br>
	 * 0;403;f;cmd;s;[[0,1,11,12],[1,0,6,12]]<br>
	 * Bot 0, command 403, fight bot receiving a command to start init two entities. Entity 0 in team 1 at position 11,12 and
	 * entity 1 in team 0 at position 6,12.<br>
	 * <b>[player_ID, team_ID, posX, posY]</b><br><br>
	 * 
	 * @param entities
	 * @return the return String<br>
	 * 
	 * The return String is written according to the command String. It returns the same bot instance, action number and bot part.<br>
	 * The type of message is switched to "rtn" as it is the return of the command. There is no command and the parameter returned is
	 * either 'true' or 'false' according to the success of the command.
	 * 
	 * @author jikiw
	 */
	public static String executeCommand(String s, ArrayList<Player> entities) {
		String param = s.split(";")[5];
		
		Game.log.println("Initiating entities. Receiving : "+s+" with entities : "+entities);
		
		String[] strings = param.split(Pattern.quote(",["));
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();

		for(int i = 0; i < strings.length; i++) {
			String[] params = strings[i].replaceAll(Pattern.quote("]"), "").replaceAll(Pattern.quote("["), "").split(",");
			int ID = Integer.parseInt(params[0]);
			int posX = Integer.parseInt(params[2]);
			int posY = Integer.parseInt(params[3]);
			String team = Integer.parseInt(params[1]) == 0 ? "blue" : "red";
			PlayingEntity playingEntity = new PlayingEntity(ID, false, new Position(posX, posY), team, entities.get(i));
			playingEntities.add(playingEntity);
			Game.log.println(playingEntity);
		}
		
		
		Collections.sort(playingEntities, new Comparator<PlayingEntity>() {
			@Override
			public int compare(PlayingEntity arg0, PlayingEntity arg1) {
				if(arg0.getID() > arg1.getID()) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		Game.initEntities(playingEntities);
		
		return "";
	}
	
	/**
	 * Executes any command that can be displayed in a String. Other commands are passed through other "executeCommand" methods 
	 * that take different parameters.<br>
	 * 
	 * @param s command String <br>
	 *
	 * Commands are written as follows : bot_instance;action_number;bot_part;type_of_message;command;parameters<br>
	 * 
	 * Bot instance : Which bot is currently asking/returning the command/return. The default is 0.<br>
	 * 
	 * Action number : Number corresponding to the action being done right now. It increases as the bot executes commands.<br>
	 * 
	 * Bot part : 'f' for this bot (fight bot). Other letters can be found as 'm' for model and 'i' for interface.<br>
	 * 
	 * Type of message : "cmd" if the message is a command, "rtn" if the message is a return.<br><br>
	 * 
	 * <b>Command : Type of command to the specified bot.</b> Commands accepted : <br>
	 * 		<b>startFight</b> - Starts the fight according to the map passed as a parameter<br>
	 * 		<b>m</b> - Moves the entity of which the ID is passed as a parameter, with the new position<br>
	 * 		<b>p</b> - Passes the turn of the entity corresponding to the ID passed<br>
	 * 		<b>c</b> - Casts a spell. Parameters are the caster's ID, the spell location, the spell ID, the spell name, the damage, and if it was a critical hit<br>
	 * 		<b>g</b> - 'get' command, requests the best possible move for the entity corresponding to the ID passed<br>
	 * 
	 * Parameters : the parameters passed as a String. These vary upon the type of the command.<br><br>
	 * 
	 * -- Examples -- <br><br>
	 * 
	 * <b>Start a fight</b><br>
	 * 0;403;f;cmd;startfight;[84675595]<br>
	 * Bot 0, command 403, fight bot receiving a command to start a fight on map 84675595.<br>
	 * <b>[map_ID]</b><br><br>
	 * 
	 * <b>Move entity</b><br>
	 * 0;405;f;cmd;m;[0,11,12]<br>
	 * Bot 0, command 405, fight bot receiving a command to move entity 0 to position 11,12<br>
	 * <b>[entity_ID, posX, posY]</b><br><br>
	 * 
	 * <b>Pass turn</b><br>
	 * 0;406;f;cmd;p;[0]<br>
	 * Bot 0, command 406, fight bot receiving a command to pass turn of entity 0<br>
	 * <b>[entity_ID]</b><br><br>
	 * 
	 * <b>Cast spell</b><br>
	 * 0;408;f;cmd;c;[0,9,12,161,'Magic arrow',150,False]<br>
	 * Bot 0, command 408, fight bot receiving a command to cast spell 'Magic arrow', ID:161. The caster is the entity 0 and
	 * the spell is cast at the position 9,12. It does 150 damage and is not a critical hit. <br>
	 * <b>[entity_ID, posX, posY, spellID, spellName, damage, crit]</b><br><br>
	 * @return the return String<br>
	 * 
	 * The return String is written according to the command String. It returns the same bot instance, action number and bot part.<br>
	 * The type of message is switched to "rtn" as it is the return of the command. There is no command and the parameter returned is
	 * either 'true' or 'false' according to the success of the command.
	 * 
	 * @author jikiw
	 */
	public static String executeCommand(String s) {
		if(Game.log == null) {
			Game.initLogs();
		}
		String[] command = s.split(";");
		log.println("Received command "+command[1]+" : "+s);
		
		String returnInformation = "";
		
		current_command_nbr = Integer.parseInt(command[1]);
		if(command.length > 2) {
			if(!command[2].equals("f")) {
				Game.log.println("Broke out of loop");
				log.println("Broke out of loop");
				return "that ain't for me boi.";
			}
			
			String commandType = command[4];
			String[] parameters = command[5].replaceAll(Pattern.quote("]"), "").replaceAll(Pattern.quote("["), "").split(",");
			
			
			if(commandType.equals("startfight")) {
				log.println("Starting fight");
				try {
					
					Game.initGame(parseStringToIntArray(parameters[0])[0]);
					Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
					returnInformation = (command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
					log.println("Successfully initiated game.");
				}catch(Exception e) {
					log.println("Failure to initiate game;"+e.getMessage());
				}
			}else if(commandType.equals("m")) {
				String refreshMessage = parameters[0] +";" + commandType + ";" + parameters[1] + ";" + parameters[2];
				Game.refresh(refreshMessage);
				
				Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
				returnInformation = command[0]+";"+command[1]+";"+command[2]+";rtn;[True]";
			}else if(commandType.equals("p")) {
				String refreshMessage = parameters[0] +";" + commandType;
				Game.refresh(refreshMessage);
				
				Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
				returnInformation = command[0]+";"+command[1]+";"+command[2]+";rtn;[True]";
			}else if(commandType.equals("c")) {
				if(parameters.length > 4){
					String refreshMessage = parameters[0] +";" + commandType + ";" + parameters[1] + ";" + parameters[2] + ";" + parameters[3].replace("'", "") + ";" + parameters[4] + ";" + parameters[5]+";"+parameters[6];
					Game.refresh(refreshMessage);
					Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
				}				
				returnInformation = command[0]+";"+command[1]+";"+command[2]+";rtn;[True]";
			}else if(commandType.equals("g")) {
				String bestTurn = Game.getBestTurn(new String[] {parameters[0], "g", "false"});
				Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;["+bestTurn+"]");
				returnInformation = command[0]+";"+command[1]+";"+command[2]+";rtn;["+bestTurn+"]";
			}else if(commandType.equals("endFight")) {
				log.println("Ending fight");
				Game.endGame();
			}
		}
		
		return returnInformation;
		
	}
	
	/**
	 * Inits all the logs.
	 * 
	 * @author jikiw
	 */
	public static void initLogs() {
		try {
			log = System.out;
			//log = new PrintStream(new FileOutputStream("fight_ia_log.txt"));
			//log = new PrintStream(new FileOutputStream("fight_ia_log.txt"));
			com = new PrintStream(new FileOutputStream("fight_ia_com.txt"));
			System.setErr(log);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Method to parse string to int arrays.
	 * @param arr String 
	 * @return int array
	 */
	static int[] parseStringToIntArray(String arr) {
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

		int[] results = new int[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		        results[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {
		        log.println("Parse string "+arr+" failed. "+nfe);
		    };
		}
		
		return results;
	}

}
