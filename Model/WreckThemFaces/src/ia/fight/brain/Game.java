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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	static public GameViz los;
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
	public void initGame(int map) {
		Map mapObject = CreateMap.getMapById(map);
		Game.map = mapObject;
		los = new GameViz(mapObject);
		log.println("Init game to map : "+map);
		ArrayList<String> commands = new ArrayList<>();
		commands.add("Init game to map : "+map);
		los.panel.updateBrainText(commands);
	}
	
	/**
	 * Ends the game by disposing of the window.
	 */
	public void endGame() {
		Game.playingEntities.clear();
		los.dispose();
	}
	
	/**
	 * Initializes entities according to the passed arrayList
	 * @param entities Entities to be initialized.
	 */
	public void initEntities(ArrayList<PlayingEntity> entities) {
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
		
		for(int i = 0; i < entities.size(); i++) {

			playingEntities.add(entities.get(i));
			
			Game.log.println(entities.get(i).getModel() == null ? "No model currently selected." : entities.get(i).getModel()+" in pos "+entities.get(i).getPosition());
		}

		
		los.update(playingEntities);
		
		Game.playingEntities = playingEntities;
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
	static private void executeMovementCommand(JSONArray command){
		System.out.println("RECEIVED MOVEMENT COMMAND");
		System.out.println(command);
		
		JSONObject movementCommand = (JSONObject) command.get(0);
		
		int id = (int) movementCommand.get("id");
		int posX = (int) movementCommand.get("x");
		int posY = (int) movementCommand.get("y");
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		playingEntity.getModel().removeMP(Position.distance(new Position(posX, posY), playingEntity.getPosition()));
		playingEntity.setPosition(new Position(posX, posY));
		Game.log.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
		
		ArrayList<String> brainText = new ArrayList<>();
		brainText.add("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
		brainText.add("");
		brainText.add("More details about entity "+id+" :");
		brainText.add(playingEntity.toString());
		String strings[] = playingEntity.getModel().toString().split("\n");
		for(int i = 0; i < strings.length; i++) {
			brainText.add(strings[i]);
		}
		
				
		Game.los.panel.updateBrainText(brainText);
	}
	
	/**
	 * Executes a spell command
	 * @param command
	 */
	static private void executeSpellCommand(JSONArray command) {
		System.out.println("RECEIVED SPELL COMMAND");
		System.out.println(command);
		
		JSONObject spellCommand = (JSONObject) ((JSONObject) command.get(0)).get("spellCast");
		JSONObject lifePointsLost = null;
		JSONObject lifePointsGained = null;
		JSONObject death = null;
		
		boolean LPLost = false;
		boolean LPGained = false;
		boolean isDead = false;
		
		for(int i = 0; i < command.size(); i++) {
			if(((JSONObject)command.get(i)).get("lifePointsLost") != null) {
				LPLost = true;
				lifePointsLost = (JSONObject)command.get(i);
			}
			
			if(((JSONObject)command.get(i)).get("lifePointsGained") != null) {
				LPGained = true;
				lifePointsGained = (JSONObject)command.get(i);
			}
			
			if(((JSONObject)command.get(i)).get("death") != null) {
				isDead = true;
				death = (JSONObject)command.get(i);
			}
		}
		
		if(LPLost) {
			System.out.println("The target has lost some LP! "+lifePointsLost);
		}
		
		if(LPGained) {
			System.out.println("The target has gained some LP! "+lifePointsGained);
		}
		
		if(isDead) {
			System.out.println("The target has died! "+death);
		}
		
		int id = (int) spellCommand.get("sourceId");
		int spellId = (int) spellCommand.get("spellId");
		int critical = (int) spellCommand.get("critical");
		int targetId = (int) spellCommand.get("targetId");
		int x = (int) spellCommand.get("x");
		int y = (int) spellCommand.get("y");
		
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		PlayingEntity victim = getPlayingEntityFromID(targetId);
		
		Game.log.println("Casting "+spellId+" to : ["+x+";"+y+"]");
		SpellObject spellCast = Game.getSpellFromID(spellId);
		
		ArrayList<String> brainText = new ArrayList<>();
		brainText.add("Casting "+spellCast+" to : ["+x+";"+y+"]");
		brainText.add("");
		brainText.add("More details about entity "+id+" :");
		brainText.add(castingEntity.toString());
		String strings[] = castingEntity.getModel().toString().split("\n");
		for(int i = 0; i < strings.length; i++) {
			brainText.add(strings[i]);
		}
		
		
		Game.los.panel.updateBrainText(brainText);

		Game.log.println(spellCast);
		Game.log.println("Printing simple name : " + castingEntity.getClass().getSimpleName());
		
		if(LPLost) {
			int damage = (int) lifePointsGained.get("lpLost");
			spellCast.applySpells(castingEntity, victim, false, damage);
		}else if(LPGained) {
			int heals = (int) lifePointsGained.get("lpGained");
			spellCast.applySpells(castingEntity, victim, true, heals);
		}
		
	}
	
	/**
	 * Executes a pass turn command
	 * @param command
	 */
	static private void executePassTurn(JSONArray command) {

	}
	
	static private PlayingEntity getClosestEnnemy(PlayingEntity caster) {
		ArrayList<PlayingEntity> ennemies = new ArrayList<>();
		
		for(int i = 0; i < playingEntities.size(); i++) {
			if(!playingEntities.get(i).getTeam().equals(caster.getTeam())) {
				ennemies.add(playingEntities.get(i));
				log.println(playingEntities.get(i).getPosition());
			}
		}
		
		log.println(ennemies);
		
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
	static private String getBestTurn(JSONArray command) {
		
		JSONObject idObj = (JSONObject) command.get(0);
		int id = (int) idObj.get("id");

		PlayingEntity caster = getPlayingEntityFromID(id);
		PlayingEntity victim = getClosestEnnemy(caster);
		
		log.println();
		
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
		log.println("AP remaining : "+caster.getModel().getAP()+"TURN : " +turn);
		
		String action = "";
		
		if(!selectedPosition.position.deepEquals(caster.getPosition())) {
			action += id+",m,"+selectedPosition.position.getX()+","+selectedPosition.position.getY();
		}else {
			if(turn.size() < 1) {
				Position desired = getClosestPositionFromArrayList(accessiblePositions, victim.getPosition());
				log.println("Accessible positions : "+accessiblePositions);
				log.println("MP available : "+caster.getModel().getMP());
				log.println("Actual position : "+caster.getPosition());
				log.println("Ennemy position : "+victim.getPosition());
				log.println("Desired position : "+desired);
				if(!caster.getPosition().deepEquals(desired)) {
					action += id+",m,"+desired.getX()+","+desired.getY();
				}else {
					action += id+",None";
				}
			}else {
				action += id+",c,"+selectedPosition.turn.get(0).getID()+","+turn.get(0).getName()+","+victim.getPosition().getX()+","+victim.getPosition().getY();
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
	
	public String executeCommand(String s, JSONArray command) {
		if(log == null) {
			initLogs();
		}
		
		System.out.println("RECEIVED COMMAND "+s);
		System.out.println(command);
		if(s.equals("c")) {
			executeSpellCommand(command);
		}else if(s.equals("m")) {
			executeMovementCommand(command);
		}else if(s.equals("startfight")) {
			log.println("Starting fight");
			initGame((int) ((JSONObject)command.get(0)).get("mapID"));
		}else if(s.equals("s")) {
			log.println("Initiating entities");
			ArrayList<Player> players = (ArrayList<Player>)(((JSONObject)command.get(0))).get("entities");
			ArrayList<JSONObject> commands = (ArrayList<JSONObject>)(((JSONObject)command.get(0))).get("misc");
			ArrayList<PlayingEntity> entities = new ArrayList<>();
			for(int i = 0; i < players.size(); i++) {
				JSONObject obj = (JSONObject) commands.get(i);
				int id = (int) obj.get("id");
				String team = (int)obj.get("teamId") == 0 ? "blue" : "red";
				int x = (int)obj.get("x");
				int y = (int)obj.get("y");
				boolean npc = false;
				Player model = players.get(i);
				Position position = new Position(x,y);
				entities.add(new PlayingEntity(id, npc, position, team, model));
			}
			
			initEntities(entities);
		}else if(s.equals("g")) {
			return getBestTurn(command);
		}
		
		//los.update(playingEntities);
		
		if(DISPLAY_GUI)
			los.repaint();
		
		return null;
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
