package ia.fight.brain;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ia.fight.astar.AStarMap;
import ia.fight.astar.ExampleFactory;
import ia.fight.astar.ExampleNode;
import ia.fight.map.CreateMap;
import ia.fight.map.GameViz;
import ia.fight.map.LineOfSight;
import ia.fight.map.Map;
import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.SpellObject;
import ia.fight.structure.spells.Type;
import ia.fight.structure.spells.spelltypes.Damage;

public class Game {

	public static final boolean DISPLAY_GUI = true;
	
	public Map map;
	public ArrayList<PlayingEntity> playingEntities;
	private ArrayList<Position> challengerAvailableCells;
    private ArrayList<Position> defenderAvailableCells;
	static public GameViz los;
    private String botTeam = "";

	/**
	 * Initializes the game map. Creates a map and opens a JFrame.
	 * @param map_nbr number of map initialized
	 */
	public void initGame(int map_nbr, String bot_name) {
		Game.println("Starting fight on map : "+map_nbr);
		Map mapObject = CreateMap.getMapById(map_nbr);
		map = mapObject;
		los = new GameViz(mapObject, bot_name);
		log.println("Init game to map : "+map_nbr);
		ArrayList<String> commands = new ArrayList<>();
		commands.add("Init game to map : "+map_nbr);
		los.panel.updateBrainText(commands);
	}
	
	/**
	 * Ends the game by disposing of the window.
	 */
	public void endGame() {
		if(playingEntities != null) {
			playingEntities.clear();
		}else {
			playingEntities = new ArrayList<>();
		}
		
		los.dispose();
	}

	@SuppressWarnings("unchecked")
	public void initStartAvailablePositions(JSONArray command) {
	    Game.println();
	    Game.println();
		Game.println("INITIATING START POSITIONS");
		challengerAvailableCells = (ArrayList<Position>)(((JSONObject)command.get(0))).get("challengerPositions");
		defenderAvailableCells = (ArrayList<Position>)(((JSONObject)command.get(0))).get("defenderPositions");
		botTeam = (int)((JSONObject)command.get(0)).get("team") == 0 ? "red" : "blue";
		los.panel.update(challengerAvailableCells, defenderAvailableCells);
		los.panel.showStartingPositions();
	}

    @SuppressWarnings("unchecked")
	public String initStartChosenPositionsModified(JSONArray command) {
        Game.println();
        Game.println();
		Game.println("START POSITIONS HAVE BEEN ALTERED");
		ArrayList<Position> positions = (ArrayList<Position>)(((JSONObject)command.get(0))).get("positions");
		ArrayList<Position> challengers = new ArrayList<>();
		ArrayList<Position> defenders = new ArrayList<>();

        for (Position position : positions) {
            for (Position challengerAvailableCell : challengerAvailableCells) {
                if (position.deepEquals(challengerAvailableCell)) {
                    challengers.add(position);
                }
            }

            for (Position defenderAvailableCell : defenderAvailableCells) {
                if (position.deepEquals(defenderAvailableCell)) {
                    defenders.add(position);
                }
            }
        }
		
		boolean isChallenger = false;
		
		if(botTeam.equals("red")) {
			isChallenger = true;
		}
		
		ArrayList<Integer> totalDistance = new ArrayList<>();
		Position keptPosition;
		
		if(isChallenger) {
            for (Position challengerAvailableCell : challengerAvailableCells) {
                int tempDistance = 0;
                for (Position defender : defenders) {
                    tempDistance += Position.distance(challengerAvailableCell, defender);
                }
                totalDistance.add(tempDistance);
            }
			
			keptPosition = challengerAvailableCells.get(indexOfMax(totalDistance));
		}else {
            for (Position defenderAvailableCell : defenderAvailableCells) {
                int tempDistance = 0;
                for (Position challenger : challengers) {
                    tempDistance += Position.distance(defenderAvailableCell, challenger);
                }
                totalDistance.add(tempDistance);
                los.panel.showCell(defenderAvailableCell, String.valueOf(tempDistance), new Color(150, 150, 150, 100));
            }
			
			keptPosition = defenderAvailableCells.get(indexOfMin(totalDistance));
		}

		Game.println("Changing position ...");
		
		
		los.panel.updateStartingPositions(positions);
		
		return keptPosition.toString();
	}
	
	public int indexOfMax(ArrayList<Integer> arr) {
		int max = arr.get(0);
		int index = 0;
		
		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i) > max) {
				max = arr.get(i);
				index = i;
			}
		}
		
		return index;
	}
	
	public int indexOfMin(ArrayList<Integer> arr) {
		int max = arr.get(0);
		int index = 0;
		
		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i) < max) {
				max = arr.get(i);
				index = i;
			}
		}
		
		return index;
	}
	
	/**
	 * Initializes entities according to the JSONArray
	 * @param command Entities to be initialized.
	 */
	@SuppressWarnings("unchecked")
	public void initEntities(JSONArray command) {
        Game.println();
        Game.println();
		Game.println("INITIATING ENTITIES");
		ArrayList<Player> players = (ArrayList<Player>)(((JSONObject)command.get(0))).get("entities");
		ArrayList<JSONObject> commands = (ArrayList<JSONObject>)(((JSONObject)command.get(0))).get("misc");
		ArrayList<PlayingEntity> entities = new ArrayList<>();
		for(int i = 0; i < players.size(); i++) {
			JSONObject obj = commands.get(i);
			int id = (int) obj.get("id");
			String team = (int)obj.get("teamId") == 0 ? "blue" : "red";
			int x = (int)obj.get("x");
			int y = (int)obj.get("y");
			boolean npc = false;
			Player model = players.get(i);
			Position position = new Position(x,y);
			entities.add(new PlayingEntity(id, position, team, model));
		}
		
		ArrayList<PlayingEntity> playingEntities = new ArrayList<>();
        for (PlayingEntity entity : entities) {
            playingEntities.add(entity);
            Game.println(entity);
            Game.println();
        }

		los.update(playingEntities);
		this.playingEntities = playingEntities;
		los.panel.hideStartingPositions();
	}
	
	public void updatePlayingEntities(JSONArray command) {
		Game.println();
		Game.println();
		Game.println("UPDATING ENTITIES");
		for(int i = 0; i < command.size(); i++) {
			JSONObject obj = (JSONObject) command.get(i);
			JSONArray summons = (JSONArray) obj.get("summons");
			
			if(summons.size() > 0) {
				Game.println("Entity "+i+" has summons !");
				Game.println(summons);
			}
		}
		Game.println(command);
	}
	
	/**
	 * Returns the playingEntity corresponding to the ID passed as a parameter
	 * @param id ID of the playingEntity seeked after
	 * @return a PlayingEntity object
	 */
    private PlayingEntity getPlayingEntityFromID(int id) {
        for (PlayingEntity playingEntity : playingEntities) {
            if (playingEntity.getID() == id) {
                return playingEntity;
            }
        }
		
		return null;
	}
	
	/**
	 * Returns the playingEntity corresponding to the name passed as a parameter
	 * @param name Name of the playingEntity seeked after
	 * @return a PlayingEntity object
	 */
    @SuppressWarnings("unused")
	private PlayingEntity getPlayingEntityFromName(String name) {
		if(playingEntities == null) {
			return null;
		}
        for (PlayingEntity playingEntity : playingEntities) {
            if (playingEntity.getModel().getName().equals(name)) {
                return playingEntity;
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
	@SuppressWarnings("unused")
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
	
	private void executeInfoReception(JSONArray array) {
		Game.println();
		Game.println("RECEIVED INFO");
		Game.println(array);
		
		if(JSONArrayContainsObject(array, "pointsVariation"))
			executePointVariation(getJSONObjectFromJSONArray(array, "pointsVariation"));

	}
	
	private void executePointVariation(JSONObject command) {
		int sourceId = (int) command.get("sourceId");
		int targetId = (int) command.get("targetId");
		
		int APLost = 0;
		int MPLost = 0;
		
		if(command.get("pa") != null) {
			APLost = (int) command.get("pa");
		}
		
		if(command.get("pm") != null) {
			MPLost = (int) command.get("pm");
		}
		
		ArrayList<String> brainText = new ArrayList<>();
		PlayingEntity victim = getPlayingEntityFromID(targetId);
		
		if(APLost != 0) {
			if(APLost > 0) {
			    Game.println("Entity "+victim+" gained "+(APLost)+" AP.");
				brainText.add("Entity "+victim+" gained "+(APLost)+" AP.");
			}else {
                Game.println("Entity "+victim+" lost "+(-APLost)+" AP.");
				brainText.add("Entity "+victim+" lost "+(-APLost)+" AP.");
			}
			
			victim.getModel().removeAP(-APLost);
		}
		
		if(MPLost != 0) {
			if(MPLost > 0) {
                Game.println("Entity "+victim+" gained "+(MPLost)+" MP.");
				brainText.add("Entity "+victim+" gained "+(MPLost)+" MP.");
			}else {
                Game.println("Entity "+victim+" lost "+(-MPLost)+" MP.");
				brainText.add("Entity "+victim+" lost "+(-MPLost)+" MP.");
			}
			
			victim.getModel().removeMP(-MPLost);
		}
		
		if(brainText.size() > 0)
			Game.los.panel.updateBrainText(brainText);
	}
	
	/**
	 * Executes a movement command
	 * @param command
	 */
	private void executeMovementCommand(JSONArray command){
		Game.println();
		Game.println("RECEIVED MOVEMENT COMMAND");
		Game.println(command);
		
		JSONObject movementCommand = (JSONObject) command.get(0);
		
		int id = (int) movementCommand.get("id");
		int posX = (int) movementCommand.get("x");
		int posY = (int) movementCommand.get("y");
		
		PlayingEntity playingEntity = getPlayingEntityFromID(id);
		playingEntity.setPosition(new Position(posX, posY));
		Game.println("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
		
		ArrayList<String> brainText = new ArrayList<>();
		brainText.add("Moving entity "+ id +" to : ["+posX+";"+posY+"]");
		brainText.add("");
		brainText.add("More details about entity "+id+" :");
		brainText.add(playingEntity.toString());
		String strings[] = playingEntity.getModel().toString().split("\n");
        for (String string : strings) {
            brainText.add(string);
        }
		
				
		Game.los.panel.updateBrainText(brainText);
	}
	
	/**
	 * Executes a spell command
	 * @param command
	 */
	private void executeSpellCommand(JSONArray command) {
		Game.println();
		Game.println("RECEIVED SPELL COMMAND");
		Game.println(command);
		
		JSONObject spellCommand = (JSONObject) ((JSONObject) command.get(0)).get("spellCast");
		JSONObject lifePointsLost = null;
		JSONObject lifePointsGained = null;
		JSONObject death = null;
		
		boolean LPLost = false;
		boolean LPGained = false;
		boolean isDead = false;

        for (Object aCommand : command) {
            if (((JSONObject) aCommand).get("lifePointsLost") != null) {
                LPLost = true;
                lifePointsLost = (JSONObject) ((JSONObject) aCommand).get("lifePointsLost");
            }

            if (((JSONObject) aCommand).get("lifePointsGained") != null) {
                LPGained = true;
                lifePointsGained = (JSONObject) ((JSONObject) aCommand).get("lifePointsGained");
            }

            if (((JSONObject) aCommand).get("death") != null) {
                isDead = true;
                death = (JSONObject) ((JSONObject) aCommand).get("death");
            }
        }
		
		if(JSONArrayContainsObject(command, "slide"))
			executeSlide(getJSONObjectFromJSONArray(command, "slide"));
		
		if(JSONArrayContainsObject(command, "dispellableEffect"))
			executeDispellable(getJSONObjectFromJSONArray(command, "dispellableEffect"));
		
		if(LPLost) {
			Game.println("The target has lost some LP! "+lifePointsLost);
		}
		
		if(LPGained) {
			Game.println("The target has gained some LP! "+lifePointsGained);
		}
		
		if(isDead) {
			Game.println("The target has died! "+death);
		}
		
		ArrayList<String> brainText = new ArrayList<>();
		
		if(spellCommand != null) {
			int id = (int) spellCommand.get("sourceId");
			int spellId = (int) spellCommand.get("spellId");
			int critical = (int) spellCommand.get("critical");
			int targetId = (int) spellCommand.get("targetId");
			int x = (int) spellCommand.get("x");
			int y = (int) spellCommand.get("y");
			
			PlayingEntity castingEntity = getPlayingEntityFromID(id);
			PlayingEntity victim = getPlayingEntityFromID(targetId);
			
			Game.println("Casting "+Player.getSpellNameFromId(spellId)+" to : ["+x+";"+y+"]");
			SpellObject spellCast = Game.getSpellFromID(spellId);
			
			brainText.add("Casting "+Player.getSpellNameFromId(spellId)+" to : ["+x+";"+y+"]");
			brainText.add("");
			brainText.add("More details about entity "+id+" :");
			brainText.add(castingEntity.toString());
			String strings[] = castingEntity.getModel().toString().split("\n");
            for (String string : strings) {
                brainText.add(string);
            }

			if(LPLost) {
				int damage = (int) lifePointsLost.get("lpLost");
				spellCast.applySpells(castingEntity, victim, false, damage);
				brainText.add("Target lost "+damage+" LP.");
			}else if(LPGained) {
				int heals = (int) lifePointsGained.get("lpGained");
				spellCast.applySpells(castingEntity, victim, true, heals);
				brainText.add("Target got healed "+heals+" LP.");
			}
		}else {
			if(LPLost) {
				int damage = (int) lifePointsLost.get("lpLost");
				PlayingEntity victim = getPlayingEntityFromID((int)lifePointsLost.get("targetId"));
				victim.getModel().removeLP(damage);
				brainText.add("Target lost "+damage+" LP.");
			}else if(LPGained) {
				int heals = (int) lifePointsGained.get("lpGained");
				PlayingEntity victim = getPlayingEntityFromID((int)lifePointsGained.get("targetId"));
				victim.getModel().addLP(heals);
				brainText.add("Target got healed "+heals+" LP.");
			}
		}

		if(isDead) {
			brainText.add("The target is dead !");
		}
		
		Game.los.panel.updateBrainText(brainText);
		
		for(int i = playingEntities.size()-1; i >= 0; i--) {
			if(playingEntities.get(i).getModel().getLP() <= 0) {
				playingEntities.remove(i);
			}
		}
		
	}
	
	private boolean JSONArrayContainsObject(JSONArray command, String object) {
        for (Object aCommand : command) {
            if (((JSONObject) aCommand).get(object) != null) {
                return true;
            }
        }
		
		return false;
	}
	
	private JSONObject getJSONObjectFromJSONArray(JSONArray command, String object) {
        for (Object aCommand : command) {
            if (((JSONObject) aCommand).get(object) != null) {
                return (JSONObject) ((JSONObject) aCommand).get(object);
            }
        }
		
		return null;
	}
	
	private void executeSlide(JSONObject command) {
		
		int sourceId = (int) command.get("sourceId");
		int targetId = (int) command.get("targetId");
		JSONObject startCell = (JSONObject) command.get("startCell");
		JSONObject endCell = (JSONObject) command.get("endCell");
		
		int x = (int)endCell.get("x");
		int y = (int)endCell.get("y");
		
		PlayingEntity victim = getPlayingEntityFromID(targetId);
		
		victim.setPosition(new Position(x, y));
		
		ArrayList<String> brainText = new ArrayList<>();
		brainText.add("Slide received from "+sourceId+", to "+targetId+".");
		brainText.add("Moving entity to : "+endCell);
		Game.los.panel.updateBrainText(brainText);
		
		log.println("Slide received from "+sourceId+", to "+targetId+". Moving entity to : "+endCell);
	}
	
	private void executeDispellable(JSONObject command) {
		int sourceId = (int) command.get("sourceId");
		int targetId = (int) command.get("targetId");
		
		int APLost = 0;
		int MPLost = 0;
		
		if(command.get("pa") != null) {
			APLost = (int) command.get("pa");
		}
		
		if(command.get("pm") != null) {
			MPLost = (int) command.get("pm");
		}
		
		PlayingEntity victim = getPlayingEntityFromID(targetId);
		
		ArrayList<String> brainText = new ArrayList<>();
		brainText.add("Dispellable effect cast on "+victim);
		
		
		if(APLost != 0) {
			brainText.add("Lost "+APLost+" AP.");
			victim.getModel().removeAP(-APLost);
		}
		
		if(MPLost != 0) {
			brainText.add("Lost "+MPLost+" MP.");
			victim.getModel().removeMP(-MPLost);
		}
		
		Game.los.panel.updateBrainText(brainText);
	}
	
	/**
	 * Executes a pass turn command
	 * @param command
	 */
	private void executePassTurn(JSONArray command) {
		int id = (int) ((JSONObject)command.get(0)).get("id");
		PlayingEntity castingEntity = getPlayingEntityFromID(id);
		castingEntity.getModel().resetAP();
		castingEntity.getModel().resetMP();
		castingEntity.getModel().removeOneBuffTurn();
		castingEntity.getModel().updateSpellsStatus();
		
		Game.println("Entity "+id+" passing turn.");
	}
	
	private PlayingEntity getClosestEnnemy(PlayingEntity caster) {
		ArrayList<PlayingEntity> ennemies = new ArrayList<>();

        for (PlayingEntity playingEntity : playingEntities) {
            if (!playingEntity.getTeam().equals(caster.getTeam())) {
                ennemies.add(playingEntity);
            }
        }
		
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
	
	private ArrayList<Position> getPath(Position caster, Position target, boolean DIAGONAL) {
		if(DIAGONAL)
			AStarMap.CANMOVEDIAGONALY = true;
		AStarMap<ExampleNode> myMap = new AStarMap<ExampleNode>(33, 33, new ExampleFactory());
        for(int i = 0; i < 33; i++){
        	for(int j = 0; j < 33; j++){
        		myMap.setWalkable(i, j, map.isPositionWalkable(new Position(i, j)));
        	}
        }
        
        myMap.setWalkable(caster.getX(), caster.getY(), true);
        myMap.setWalkable(target.getX(), target.getY(), true);
        
        List<ExampleNode> path = myMap.findPath(caster.getX(), caster.getY(), target.getX(), target.getY());
        
        if(DIAGONAL)
			AStarMap.CANMOVEDIAGONALY = false;
        
        ArrayList<Position> positions = new ArrayList<>();

        for (ExampleNode aPath : path) {
            positions.add(new Position(aPath.getxPosition(), aPath.getyPosition()));
        }
        
        return positions;
	}
	
	private Position getBestPositionDiagOptimization(Position caster, Position target, int MP) {
		ArrayList<Position> positions = getPath(caster, target, true);
		Position kept = caster;
		Position kept_ndo = caster;
		int MPLeft = MP;
		
		Game.println("");
		Game.println("//////////////////");
		Game.println("Getting best position with diagonal optimization.");
		Game.println("The diagonally optimized path is of size : "+positions.size());
		Game.println("Caster : "+caster+", target : "+target+". MP available : "+MP);

        for (Position position1 : positions) {
            Game.println(caster + " " + position1 + " " + getPath(caster, position1, false).size());
            if (getPath(caster, position1, false).size() <= MPLeft) {
                if (position1.deepEquals(target)) {
                    break;
                }
                kept = position1;
                kept_ndo = position1;
            } else {
                break;
            }
        }
		
		Game.println("After diagonal movement, position "+kept+" was kept.");
		
		MPLeft -= Position.distance(caster, kept);
		
		Game.println(MPLeft+" MP Left.");
		try {
			if(MPLeft > 0) {
				Game.println("There are movement points remaining, but not enough for a diagonal approach.");
				positions = getPath(kept, target, false);
				Game.println("The remainder of the path is of size : "+positions.size());
                for (Position position : positions) {
                    Game.println(kept + " " + position + " " + getPath(caster, position, false).size());
                    if (getPath(caster, position, false).size() <= MPLeft) {
                        if (position.deepEquals(target)) {
                            break;
                        }
                        kept_ndo = position;
                        Game.println("NPK : " + kept_ndo);
                    } else {
                        Game.println("Broke out of the loop");
                        break;
                    }
                }
				MPLeft = MP-Position.distance(caster, kept_ndo);
				Game.println("New position kept : "+kept_ndo);
				Game.println(MPLeft+" MP Left.");
			}
		}catch(NullPointerException e) {}
		
		Game.println("Algorithm finished. Kept position : "+kept_ndo);
		Game.println("///////////////////");
        Game.println();
        Game.println();
		return kept_ndo;
	}

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
	static private Position getDeepClosestPositionFromArrayList(ArrayList<Position> positions, ArrayList<Position> nextPositions) {
		int distance = 0;
		int distance_temp = 0;
		Position selected = positions.get(0);

        for (Position nextPosition1 : nextPositions) {
            distance += Math.pow(Position.distance(selected, nextPosition1), 2);
        }
		
		for(int i = 1; i < positions.size(); i++) {
			distance_temp = 0;
            for (Position nextPosition : nextPositions) {
                distance_temp += Math.pow(Position.distance(positions.get(i), nextPosition), 2);
            }
			
			if(distance > distance_temp) {
				selected = positions.get(i);
				distance = distance_temp;
			}
		}
		
		return selected;
		
	}

    @SuppressWarnings("unused")
	static private Position getClosestDiagonalPositionFromArrayList(ArrayList<Position> positions, Position position) {
		int distance = Position.distance(positions.get(0), position);
		int diagonalDistance = 0;
		diagonalDistance += Math.abs(positions.get(0).getX() - position.getX());
		diagonalDistance -= Math.abs(positions.get(0).getY() - position.getY());
		
		Position selected = positions.get(0);
		
		for(int i = 1; i < positions.size(); i++) {
			if(distance >= Position.distance(positions.get(i), position)) {
				int diagonalDistanceTemp = 0;
				diagonalDistanceTemp += Math.abs(positions.get(i).getX() - position.getX());
				diagonalDistanceTemp -= Math.abs(positions.get(i).getY() - position.getY());
				
				if(diagonalDistance > diagonalDistanceTemp) {
					selected = positions.get(i);
					distance = Position.distance(positions.get(i), position);
					diagonalDistance = diagonalDistanceTemp;
				}
				
			}
		}
		
		return selected;
	}
		
	/**
	 * Method called to get the best turn. Returns a single action, either movement or spellcast.
	 * @param command
	 * @return
	 */
	private String getBestTurn(JSONArray command) {
		JSONObject idObj = (JSONObject) command.get(0);
		int id = (int) idObj.get("id");
		PlayingEntity caster = getPlayingEntityFromID(id);
		PlayingEntity victim = getClosestEnnemy(caster);
		
		Position desired = getBestPositionDiagOptimization(caster.getPosition(), victim.getPosition(), caster.getModel().getMP());
		
		String action = "";

		if(desired.deepEquals(caster.getPosition())) {
			ArrayList<SpellObject> turn = caster.getOptimalTurnFrom(caster.getPosition(), victim, false, map);
			if(turn.size() > 0) {
				action += id+",None";
				//action += id+",c,"+turn.get(0).getID()+","+turn.get(0).getName()+","+victim.getPosition().getX()+","+victim.getPosition().getY();
			}else {
				action += id+",None";
			}
		}else {
			action += id+",m,"+desired.getX()+","+desired.getY();
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
    @SuppressWarnings("unused")
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

		Game.println();
		Game.println();
		Game.println("Received command of type : "+s);
		
		if(s.equals("c")) {
			executeSpellCommand(command);
		}else if(s.equals("m")) {
			executeMovementCommand(command);
		}else if(s.equals("startfight")) {
			initGame((int) ((JSONObject)command.get(0)).get("mapID"), (String) ((JSONObject)command.get(0)).get("name"));
		}else if(s.equals("s")) {	
			initEntities(command);
		}else if(s.equals("g")) {
			return getBestTurn(command);
		}else if(s.equals("endFight")) {
			endGame();
		}else if(s.equals("p")) {
			executePassTurn(command);
		}else if(s.equals("i")) {
			executeInfoReception(command);
		}else if(s.equals("fightPositionInitialization")) {
			initStartAvailablePositions(command);
		}else if(s.equals("startPosAltered")) {
			return initStartChosenPositionsModified(command);
		}else if(s.equals("updateEntities")) {
			updatePlayingEntities(command);
		}
		
		los.update(playingEntities);
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
			com = new PrintStream(new FileOutputStream("fight_ia_com.txt"));
			System.setErr(log);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}
	
	/**
	 * Method to parse string to int arrays.
	 * @param arr String 
	 * @return int array
	 */
    @SuppressWarnings("unused")
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

	public static void println(Object s){
        String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
		log.println("["+timeStamp+"] "+s);
	}
	
	public static void println(){
        String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
		log.println("["+timeStamp+"] ");
	}
	
	public static void print(Object s){
        String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
		log.println("["+timeStamp+"] "+s);
	}
	
	public static void print(){
        String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
		log.println("["+timeStamp+"] ");
	}

}
