package ia.fight.brain;

import java.util.ArrayList;

import ia.fight.brain.classes.Cra;
import ia.fight.map.CreateMap;
import ia.fight.map.GameViz;
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
	
	public Game(int map) {
		String[] entities = new String[2];
		// Player ID / posX / posY / Player or Monster / Player type (0:cra, 1:Enu, ...) / Team / HP Max / AP Max / MP Max
		entities[0] = "0;10;12;p;0;1;2020;11;4";
		entities[1] = "1;6;12;m;0;0;200;8;5";
		ArrayList<String> refreshMessage = new ArrayList<>();

		refreshMessage.add("0;m;11;12");
		refreshMessage.add("0;p");
		refreshMessage.add("1;m;9;12");
		refreshMessage.add("1;p");
		refreshMessage.add("0;s;9;12;Magic arrow;300;false");
		refreshMessage.add("0;s;10;12;Dispersing arrow;0;false");
		refreshMessage.add("0;p");
		refreshMessage.add("1;g");
		//refreshMessage.add("1;m;10;12");
		//refreshMessage.add("0;m;13;12");
		//refreshMessage.add("1;m;11;12");
		
		initGame(map);
		initEntities(entities);

		try {
			Thread.sleep(500);
			System.out.println("Starting in 1 second ...");
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
			
			System.out.println("Created a playing entity in position "+playingEntity.getPosition());
			System.out.println(playingEntity.getModel() == null ? "No model currently selected." : playingEntity.getModel());
		}
		
		los.update(playingEntities);
		
		this.playingEntities = playingEntities;
	}
	
	public void refresh(String commandString) {
		String[] command = commandString.split(";");
		String actionType = command[1];

		//RECEIVED A MOVEMENT COMMAND
			 if(actionType.equals("m")) {
			executeMovementCommand(command);
		}
		//RECEIVED A MOVEMENT COMMAND
		else if(actionType.equals("s")) {
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
		System.out.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
	}
	
	private void executeSpellCommand(String[] command) {
		int id = Integer.parseInt(command[0]);
		int posX = Integer.parseInt(command[2]);
		int posY = Integer.parseInt(command[3]);
		
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		
		String spellname = command[4];
		int damage = Integer.parseInt(command[5]);
		boolean crit = Boolean.parseBoolean(command[6]);

		System.out.println("Casting "+spellname+" to : ["+posX+";"+posY+"]"+". " + (crit ? "Critical hit ! " : "Not a crit."));
		SpellObject spellCast = this.getSpellFromName(spellname, "cra");
		
		spellCast.applySpells(castingEntity, new Position(posX, posY), true, damage);
	}
	
	private void executePassTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		castingEntity.getModel().resetAP();
		castingEntity.getModel().resetMP();
		castingEntity.getModel().removeOneBuffTurn();
	}
	
	private void getBestTurn(String[] command) {
		int id = Integer.parseInt(command[0]);
		System.out.println("Getting best turn for "+getPlayingEntityFromID(id));
		System.out.println("Testing github webhook");
		System.out.println("Pls work");
	}
	
	
	public static void main(String[] args) {
		new Game(84675595);
	}

}
