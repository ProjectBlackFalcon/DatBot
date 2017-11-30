package ia.fight.brain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import ia.fight.brain.classes.Cra;
import ia.fight.map.CreateMap;
import ia.fight.map.GameViz;
import ia.fight.map.LineOfSight;
import ia.fight.map.Map;
import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.SpellObject;

public class Game {

	static final int RANDOM = -1;
	static final boolean DISPLAY_GUI = true;
	
	static public Map map;
	static public ArrayList<PlayingEntity> playingEntities;
	private GameViz los;
	
	public Game() {
		
	}
	
	public Game(int map) {
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
		}
	}
	
	public void initGame(int map) {
		Map mapObject = CreateMap.getMapById(map);
		this.map = mapObject;
		los = new GameViz(mapObject);
		
		com.println("S;success");
	}
	
	public void initEntities(String[] entities) {
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
		
		for(int i = 0; i < entities.length; i++) {
			String[] command = entities[i].split(";");
	
			int id = Integer.parseInt(command[0]);
			int posX = Integer.parseInt(command[1]);
			int posY = Integer.parseInt(command[2]);
			boolean npc = !command[3].equals("p");
			int playerType = Integer.parseInt(command[4]);
			
			String team = Integer.parseInt(command[5]) == 0 ? "red" : "blue";
			Player player = null;
			
			int baseLP = Integer.parseInt(command[6]);
			int baseAP = Integer.parseInt(command[7]);
			int baseMP = Integer.parseInt(command[8]);
			
			if(!npc && playerType == 0) {
				player = new Cra("Player "+id, baseLP, baseAP, baseMP);
			}else {
				player = new Cra("Player "+id, baseLP, baseAP, baseMP);
			}
			
			PlayingEntity playingEntity = new PlayingEntity(id, npc, new Position(posX, posY), team, player);
			playingEntities.add(playingEntity);
			
			Game.log.println("Created a playing entity in position "+playingEntity.getPosition());
			Game.log.println(playingEntity.getModel() == null ? "No model currently selected." : playingEntity.getModel());
		}

		
		los.update(playingEntities);
		
		this.playingEntities = playingEntities;
		
		com.println("S;success");
	}
	
	public void refresh(String commandString) {
		log.println("R;"+commandString);
		String[] command = commandString.split(";");
		int id = Integer.parseInt(command[0]);
		
		if(this.getPlayingEntityFromID(id) == null) {
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
		
		com.println("S;success");
	}
	
	private PlayingEntity getPlayingEntityFromID(int id) {
		for(int i = 0; i < this.playingEntities.size(); i++) {
			if(playingEntities.get(i).getID() == id) {
				return this.playingEntities.get(i);
			}
		}
		
		return null;
	}
	
	private SpellObject getSpellFromName(String name, String playerClass) {
		if(playerClass.equals("cra")) {
			return CraModel.getSpellFromName(name);
		}
		
		return null;
	}
	
	private void executeMovementCommand(String[] command){
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		playingEntity.getModel().removeMP(Position.distance(new Position(posX, posY), playingEntity.getPosition()));
		playingEntity.setPosition(new Position(posX, posY));
		Game.log.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
	}
	
	private void executeSpellCommand(String[] command) {
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		
		String spellname = command[4];
		int damage = Integer.parseInt(command[5]);
		boolean crit = Boolean.parseBoolean(command[6]);

		Game.log.println("Casting "+spellname+" to : ["+posX+";"+posY+"]"+". " + (crit ? "Critical hit ! " : "Not a crit."));
		SpellObject spellCast = this.getSpellFromName(spellname, "cra");
		
		Game.log.println(spellCast);
		
		spellCast.applySpells(castingEntity, new Position(posX, posY), true, damage);
	}
	
	private void executePassTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		castingEntity.getModel().resetAP();
		castingEntity.getModel().resetMP();
		castingEntity.getModel().removeOneBuffTurn();
		castingEntity.getModel().updateSpellsStatus();
		
		log.println("Entity "+id+" passing turn.");
	}
		
	private String getBestTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		boolean fullTurn = Boolean.parseBoolean(command[2]);
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		
		ArrayList<PlayingEntity> ennemies = new ArrayList<>();
		
		for(int i = 0; i < playingEntities.size(); i++) {
			if(!playingEntities.get(i).getTeam().equals(playingEntity.getTeam())) {
				ennemies.add(playingEntities.get(i));
			}
		}

		
		String action = command[0]+";";
		
		int range[] = playingEntity.getOptimalRangeForMaximumDamageOutput(ennemies.get(0));
		ArrayList<Position> path = map.getShortestPath(ennemies.get(0).getTeam(), playingEntity.getPosition(), ennemies.get(0).getPosition());
		boolean startCellIsOk = false;
		boolean foundPath = false;
		boolean castFromStartCell = false;
		int damage = 0;
		int selected = 0;
		
		Game.log.println(Position.distance(playingEntity.getPosition(), ennemies.get(0).getPosition()));
		
		if(Position.distance(playingEntity.getPosition(), ennemies.get(0).getPosition()) <= 2) {
			Game.log.println("Done from close combat");
			ArrayList<SpellObject> spells = playingEntity.getOptimalTurnFrom(playingEntity.getPosition(), ennemies.get(0));
			
			if(fullTurn) {
				for(int i = 0; i < spells.size()-1; i++) {
					action += "s;"+spells.get(i).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY()+";";
				}
				
				action += "s;"+spells.get(spells.size()-1).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY();
			}else {
				action += "s;"+spells.get(0).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY()+";";
			}
		}else{
			Game.log.println("Done from far away");
			for(int i = 0; i < path.size(); i++) {
				ArrayList<SpellObject> turn = playingEntity.getOptimalTurnFrom(path.get(i), ennemies.get(0));
				int tempDamage = 0;
				for(int j = 0; j < turn.size(); j++) {
					tempDamage += turn.get(j).getDamagePreviz(playingEntity, ennemies.get(0));
				}
				
				if(tempDamage > damage) {
					damage = tempDamage;
					selected = i;
				}
			}
			
			Game.log.println(path.get(selected));
			Game.log.println(ennemies.get(0));
			ArrayList<SpellObject> turn = playingEntity.getOptimalTurnFrom(path.get(selected), ennemies.get(0));
			
			if(fullTurn) {
				for(int i = 0; i < turn.size()-1; i++) {
					action += "s;"+turn.get(i).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY()+";";
				}
				action += "s;"+turn.get(turn.size()-1).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY();
			
			}else {
				action += "s;"+turn.get(0).getName()+";"+ennemies.get(0).getPosition().getX()+";"+ennemies.get(0).getPosition().getY()+";";
			}
		}
		return action;
		
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
	
	public static void main(String[] args) {

		
		
		try {
			log = new PrintStream(new FileOutputStream("fight_ia_log.txt"));
			com = new PrintStream(new FileOutputStream("fight_ia_com.txt"));
			System.setErr(log);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Game.log.println("Started fight !");
		Game game = new Game();
		
		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			while(s.equals("x")==false) {
				com.println("R;"+s);
				
				String[] command = s.split(";");
				if(command.length > 2) {
					if(!command[2].equals("f")) {
						log.println("Broke out of loop");
						break;
					}
					
					if(command[4].equals("startfight")) {
						log.println("Starting fight");
						try {
							
							game.initGame(parseStringToIntArray(command[5])[0]);
							com.println("S;success");
						}catch(Exception e) {
							com.println("S;failure;"+e.getMessage());
						}
					}else if(command[4].equals("s")){
						String entities[] = command[5].split(":");
						for(int i = 0; i < entities.length; i++) {
							entities[i] = entities[i].replace("[", "").
									replace("]", "").
									replace("'", "").
									replace(","	, ";");
						}
						
						game.initEntities(entities);
					}else if(command[4].equals("m")) {
						String refreshMessage = "";
						refreshMessage += command[5] +";" + command[4] + ";" + command[6] + ";" + command[7];
						game.refresh(refreshMessage);
					}else if(command[4].equals("p")) {
						String refreshMessage = "";
						refreshMessage += command[5] +";" + command[4];
						game.refresh(refreshMessage);
					}else if(command[4].equals("c")) {
						String refreshMessage = "";
						refreshMessage += command[5] +";" + command[4] + ";" + command[6] + ";" + command[7] + 
								";" + command[8].replace("'", "") + ";" + command[9] + ";" + command[10];
						game.refresh(refreshMessage);
					}else if(command[4].equals("g")) {
						com.println("S;"+game.getBestTurn(new String[] {command[5], "g", "false"}));
					}
				}
				
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
