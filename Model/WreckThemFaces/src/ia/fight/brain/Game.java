package ia.fight.brain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	
	public static void initGame(int map) {
		Map mapObject = CreateMap.getMapById(map);
		Game.map = mapObject;
		los = new GameViz(mapObject);
	}
	
	public static void endGame() {
		los.dispose();
	}
	
	static public void initEntities(ArrayList<PlayingEntity> entities) {
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
		
		for(int i = 0; i < entities.size(); i++) {

			playingEntities.add(entities.get(i));
			
			Game.log.println("Created a playing entity in position "+entities.get(i).getPosition());
			Game.log.println(entities.get(i).getModel() == null ? "No model currently selected." : entities.get(i).getModel());
		}

		
		los.update(playingEntities);
		
		Game.playingEntities = playingEntities;
	}
	
	static public void refresh(String commandString) {
		log.println("R;"+commandString);
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
	
	private static PlayingEntity getPlayingEntityFromID(int id) {
		for(int i = 0; i < Game.playingEntities.size(); i++) {
			if(playingEntities.get(i).getID() == id) {
				return Game.playingEntities.get(i);
			}
		}
		
		return null;
	}
	
	private static SpellObject getSpellFromName(String name, String playerClass) {
		if(playerClass.equals("cra")) {
			return CraModel.getSpellFromName(name);
		}else if(playerClass.equals("monster")){
			SpellObject spell = new SpellObject("MonsterSpell", 0, 0);
			spell.addSpell(new Damage(Type.AIR, 0, 0, 0, 0, spell));
			return spell;
		}
		
		return null;
	}
	
	static private void executeMovementCommand(String[] command){
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		playingEntity.getModel().removeMP(Position.distance(new Position(posX, posY), playingEntity.getPosition()));
		playingEntity.setPosition(new Position(posX, posY));
		Game.log.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
	}
	
	static private void executeSpellCommand(String[] command) {
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		
		String spellname = command[4];
		int damage = Integer.parseInt(command[5]);
		boolean crit = Boolean.parseBoolean(command[6]);

		Game.log.println("Casting "+spellname+" to : ["+posX+";"+posY+"]"+". " + (crit ? "Critical hit ! " : "Not a crit."));
		SpellObject spellCast = Game.getSpellFromName(spellname, "cra");
		Game.log.println(spellCast);
		if(castingEntity.getClass().getSimpleName().equals("Monster")) {
			spellCast = Game.getSpellFromName(spellname, "monster");
			Game.log.println(spellCast);
		}else {
			spellCast = Game.getSpellFromName(spellname, "cra");
			Game.log.println(spellCast);
		}
		
		
		
		spellCast.applySpells(castingEntity, new Position(posX, posY), true, damage);
	}
	
	static private void executePassTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		castingEntity.getModel().resetAP();
		castingEntity.getModel().resetMP();
		castingEntity.getModel().removeOneBuffTurn();
		castingEntity.getModel().updateSpellsStatus();
		
		log.println("Entity "+id+" passing turn.");
	}
		
	static private String getBestTurn(String[] command) {
		long start = System.currentTimeMillis();
		int id = Integer.parseInt(command[0]);
		boolean fullTurn = Boolean.parseBoolean(command[2]);
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		ArrayList<PlayingEntity> ennemies = new ArrayList<>();
		
		for(int i = 0; i < playingEntities.size(); i++) {
			if(!playingEntities.get(i).getTeam().equals(playingEntity.getTeam())) {
				ennemies.add(playingEntities.get(i));
			}
		}

		String action = command[0]+",";
		
		ArrayList<bestEnemyAndTurn> bestPositions = new ArrayList<>();
		int maxDamage = 0;
		PlayingEntity selectedEntity;
		ArrayList<SpellObject> selectedTurn;
		
		for(int i = 0; i < ennemies.size(); i++) {
			ArrayList<SpellObject> turn = playingEntity.getOptimalTurnFrom(playingEntity.getPosition(), ennemies.get(i));
			int totalDamage = 0;
			
			ArrayList<Position> accessiblePositions = new ArrayList<>();
			accessiblePositions.add(playingEntity.getPosition());
			
			for(int k = playingEntity.getPosition().getX() - playingEntity.getModel().getMP(); k < playingEntity.getPosition().getX() + playingEntity.getModel().getMP()+1; k++) {
				for(int l = playingEntity.getPosition().getX() - playingEntity.getModel().getMP(); l < playingEntity.getPosition().getX() + playingEntity.getModel().getMP()+1; l++) {
					if(Game.map.isPositionAccessible(playingEntity.getPosition(), new Position(k,l), playingEntity.getModel().getMP())) {
						accessiblePositions.add(new Position(k, l));
					}
				}
			}
			
			
			for(int k = 0; k < accessiblePositions.size(); k++) {
				turn = playingEntity.getOptimalTurnFrom(accessiblePositions.get(k), ennemies.get(i));
				for(int j = 0; j < turn.size(); j++) {
					totalDamage += turn.get(i).getDamagePreviz(playingEntity, ennemies.get(i));
				}

				if(totalDamage > maxDamage) {
					maxDamage = totalDamage;
					bestPositions.clear();
					bestPositions.add(new bestEnemyAndTurn(ennemies.get(i), accessiblePositions.get(k), maxDamage, turn));
				}else if(totalDamage == maxDamage) {
					bestPositions.add(new bestEnemyAndTurn(ennemies.get(i), accessiblePositions.get(k), maxDamage, turn));
				}
				
				totalDamage = 0;
			}
		}
		
		int minDistanceBetweenOptimalPositionAndEntity = 1000;
		bestEnemyAndTurn selectedPosition = bestPositions.get(0);
		
		for(int i = 0; i < bestPositions.size(); i++) {
			int distance = Position.distance(bestPositions.get(i).position, playingEntity.getPosition());
			if(distance < minDistanceBetweenOptimalPositionAndEntity) {
				minDistanceBetweenOptimalPositionAndEntity = distance;
				selectedPosition = bestPositions.get(i);
			}
		}
		
		if(!selectedPosition.position.deepEquals(playingEntity.getPosition())) {
			action += "m,"+selectedPosition.position.getX()+","+selectedPosition.position.getY();
		}else {
			action += "c,"+selectedPosition.turn.get(0).getID()+","+selectedPosition.turn.get(0).getName()+","+selectedPosition.entity.getPosition().getX()+","+selectedPosition.entity.getPosition().getY();
		}
		
		
		long stop = System.currentTimeMillis();
		
		System.out.println("Total time : "+(stop-start));
		
		return action;
		
	}
	
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
	
	void castSpell(ArrayList<SpellObject> spell) {
		
	}
	
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
	 * @param s
	 * @param entities
	 * @return
	 */
	public static String executeCommand(String s, ArrayList<Player> entities) {
		String param = s.split(";")[5];
		System.out.println(param);
		
		String[] strings = param.split(Pattern.quote(",["));
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
		
		for(int i = 0; i < strings.length; i++) {
			System.out.println(strings[i]);
		}
		
		for(int i = 0; i < strings.length; i++) {
			String[] params = strings[i].replaceAll(Pattern.quote("]"), "").replaceAll(Pattern.quote("["), "").split(",");
			int ID = Integer.parseInt(params[0]);
			int posX = Integer.parseInt(params[2]);
			int posY = Integer.parseInt(params[3]);
			String team = Integer.parseInt(params[1]) == 0 ? "blue" : "red";
			PlayingEntity playingEntity = new PlayingEntity(ID, false, new Position(posX, posY), team, entities.get(i));
			playingEntities.add(playingEntity);
		}
		
		System.out.println(playingEntities);
		Game.initEntities(playingEntities);
		
		return "";
	}
	
	/**
	 * Executes any command that can be displayed in a String
	 * @param s
	 * @return
	 */
	public static String executeCommand(String s) {
		if(Game.log == null) {
			Game.initLogs();
		}
		String[] command = s.split(";");
		log.println("Received command "+command[1]);
		log.println(s);
		
		String returnInformation = "";
		
		current_command_nbr = Integer.parseInt(command[1]);
		if(command.length > 2) {
			if(!command[2].equals("f")) {
				System.out.println("Broke out of loop");
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
				String refreshMessage = parameters[0] +";" + commandType + ";" + parameters[1] + ";" + parameters[2] + ";" + parameters[3].replace("'", "") + ";" + parameters[4] + ";" + parameters[5];
				Game.refresh(refreshMessage);
				
				Game.com.println(command[0]+";"+command[1]+";"+command[2]+";rtn;[True]");
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
	
	public static void initLogs() {
		try {
			log = System.out;
			//log = new PrintStream(new FileOutputStream("fight_ia_log.txt"));
			com = new PrintStream(new FileOutputStream("fight_ia_com.txt"));
			System.setErr(log);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		Game.initLogs();
		
		Game.log.println("Started fight !");
		Game game = new Game();
		
		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			while(s.equals("x")==false) {
				Game.executeCommand(s);
				
				s = bufferRead.readLine();
			}
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
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
