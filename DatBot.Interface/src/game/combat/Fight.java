package game.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import game.Info;
import game.movement.CellMovement;
import ia.fight.brain.Game;
import ia.fight.brain.classes.Cra;
import ia.fight.brain.classes.Monster;
import ia.fight.map.CreateMap;
import ia.fight.structure.Player;
import protocol.network.Network;
import protocol.network.messages.game.actions.fight.GameActionFightCastRequestMessage;
import protocol.network.messages.game.context.GameEntitiesDispositionMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPossiblePositionsMessage;
import protocol.network.messages.game.context.fight.GameFightReadyMessage;
import protocol.network.messages.game.context.fight.GameFightSynchronizeMessage;
import protocol.network.messages.game.context.fight.GameFightTurnFinishMessage;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;
import protocol.network.types.game.context.fight.GameFightMinimalStats;
import protocol.network.types.game.context.fight.GameFightMonsterInformations;

public class Fight {
	
	/*
	 * BUG FIGHT :
	 * - POUSSER
	 */

	// Init fight
	public GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage; // Available
	public GameEntitiesDispositionMessage gameEntitiesDispositionMessage; // Disposition
	private GameFightSynchronizeMessage gameFightSynchronizeMessage; // Recap
	private ArrayList<Player> entities;
	private List<GameFightMonsterInformations> monsters;
	private List<GameFightCharacterInformations> players;
	private int teamIdPlayer;
	private boolean isRdy = false;
	private boolean advancedRdy = false;
	
	public List<Double> turnListId;
	private JSONArray spellJson;
	private Network network;
	private Info info;
	private Game game;

	public Fight(Network network) {
		this.network = network;
		this.info = network.getInfo();
		game = new Game();
	}

	/**
	 * Communicate with the fight algo and the results, modified by jikiw
	 * 
	 * @param String command, Ojbect [] parameters
	 * @return String results
	 * @author baptiste & jikiw
	 */

	public String sendToFightAlgo(String command, JSONArray param) {
		return game.executeCommand(command, param);
	}

	/**
	 * Set the position of the player at the begining of the fight
	 * 
	 * @author baptiste
	 */
	public void setBeginingPosition() throws Exception {
		// TODO LYSANDRE
		int cellId = 0;
		GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellId);
		network.sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Placement position : " + cellId);
	}

	/**
	 * Start the fight
	 * 
	 * @author baptiste
	 */
	public void fightReady() throws Exception {
		network.sendToServer(new GameFightReadyMessage(true), GameFightReadyMessage.ProtocolId, "Ready");
	}
	
	/**
	 * Stop being ready
	 * 
	 * @author jikiw
	 */
	public void fightNotReady() throws Exception {
		network.sendToServer(new GameFightReadyMessage(false), GameFightReadyMessage.ProtocolId, "Ready");
	}

	/**
	 * End the turn
	 * 
	 * @author baptiste
	 */
	public void endTurn() throws Exception {
		network.append("Ending turn");
		network.sendToServer(new GameFightTurnFinishMessage(false), GameFightTurnFinishMessage.ProtocolId, "End turn");
	}

	/**
	 * Move the player during fight using MP
	 * 
	 * @param int cellId
	 * @return boolean moved
	 * @author baptiste
	 */
	public boolean moveTo(int cellId) throws Exception {
		network.append("Moving to " + cellId);
		CellMovement mov = this.getNetwork().getMovement().MoveToCell(cellId);
		if (mov == null || mov.path == null) {
			return false;
		}
		else if (this.network.getInfo().getCellId() == cellId) {
			return true;
		}
		else {
			mov.performMovement();
			if (this.network.getInfo().getCellId() == cellId) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Cast spell
	 * 
	 * @param int id, int cellId
	 * @author baptiste
	 */
	public void castSpell(int id, int cellId) throws Exception {
		network.append("Casting " + id + " to " + cellId);
		GameActionFightCastRequestMessage gameActionFightCastRequestMessage = new GameActionFightCastRequestMessage(id, cellId);
		network.sendToServer(gameActionFightCastRequestMessage, GameActionFightCastRequestMessage.ProtocolId, "Cast spell");
	}

	/**
	 * Init the entities Init List<players> entities
	 * 
	 * @return String [id,teamId,posX,posY],...
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> init() {
		this.entities = new ArrayList<>();
		this.monsters = new ArrayList<>();
		this.players = new ArrayList<>();
		Player player = null;
		ArrayList<JSONObject> arr = new ArrayList<>();

		for (int i = 0; i < gameFightSynchronizeMessage.getFighters().size(); i++) {
			JSONObject toSend = new JSONObject();
			if (this.getGameFightSynchronizeMessage().getFighters().get(i).getClass().getSimpleName().equals("GameFightCharacterInformations")) {
				GameFightCharacterInformations p = (GameFightCharacterInformations) gameFightSynchronizeMessage.getFighters().get(i);
				if(p.getContextualId() == this.getNetwork().getInfo().getActorId()){
					this.teamIdPlayer = p.getTeamId();
				}
				this.players.add(p);
				GameFightMinimalStats stats = p.getStats();
				if (p.getBreed() == 9) {
					player = new Cra(p.getName(), p.getStats().getLifePoints(), p.getStats().getActionPoints(), p.getStats().getMovementPoints(), p.getLevel());
				} else {
					player = new Cra(p.getName(), p.getStats().getLifePoints(), p.getStats().getActionPoints(), p.getStats().getMovementPoints(), p.getLevel());
				}
				if (p.getContextualId() == this.network.getInfo().getActorId()) {
					this.network.getInfo().setCellId(p.getDisposition().getCellId());
				}
				player.setLP(stats.getLifePoints());
				player.setBaseLP(stats.getBaseMaxLifePoints());
				player.setMaxLP(stats.getMaxLifePoints());
				player.setShield(stats.getShieldPoints());
				player.setAP(stats.getActionPoints());
				player.setBaseAP(stats.getMaxActionPoints());
				player.setMP(stats.getMovementPoints());
				player.setBaseMP(stats.getMaxMovementPoints());
				player.setSummons((int) stats.getSummoner());
				player.setSummoned(stats.isSummoned());
				player.setResPrcnt(new int[] { stats.getAirElementResistPercent(), stats.getEarthElementResistPercent(), stats.getWaterElementResistPercent(), stats.getFireElementResistPercent(), stats.getNeutralElementResistPercent() });
				player.setResFixed(new int[] { stats.getAirElementReduction(), stats.getEarthElementReduction(), stats.getWaterElementReduction(), stats.getFireElementReduction(), stats.getNeutralElementReduction() });
				player.setCriticalResistance(stats.getCriticalDamageFixedResist());
				player.setPushbackResistance(stats.getPushDamageFixedResist());
				player.setAPLossReduction(stats.getDodgePALostProbability());
				player.setMPLossReduction(stats.getDodgePMLostProbability());
				player.setLock(stats.getTackleBlock());
				player.setDodge(stats.getTackleEvade());
				player.setCloseCombatResistancePrcnt(100 - stats.getMeleeDamageReceivedPercent());
				player.setDistanceResistancePrcnt(100 - stats.getRangedDamageReceivedPercent());
				if (i == this.getGameFightSynchronizeMessage().getFighters().size() - 1) {
					toSend.put("id", getId(p.getContextualId()));
					toSend.put("teamId", p.getTeamId());
					toSend.put("x", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[0]);
					toSend.put("y", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[1]);
				}
				else {
					toSend.put("id", getId(p.getContextualId()));
					toSend.put("teamId", p.getTeamId());
					toSend.put("x", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[0]);
					toSend.put("y", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[1]);
				}
				this.entities.add(player);
			}
			else if (this.getGameFightSynchronizeMessage().getFighters().get(i).getClass().getSimpleName().equals("GameFightMonsterInformations")) {
				GameFightMonsterInformations p = (GameFightMonsterInformations) gameFightSynchronizeMessage.getFighters().get(i);
				this.monsters.add(p);
				GameFightMinimalStats stats = p.getStats();
				Monster monster = new Monster(String.valueOf(p.getCreatureGenericId()), p.getStats().getLifePoints(), p.getStats().getActionPoints(), p.getStats().getMovementPoints(), p.getCreatureGrade());
				monster.setLP(stats.getLifePoints());
				monster.setBaseLP(stats.getBaseMaxLifePoints());
				monster.setMaxLP(stats.getMaxLifePoints());
				monster.setShield(stats.getShieldPoints());
				monster.setAP(stats.getActionPoints());
				monster.setBaseAP(stats.getMaxActionPoints());
				monster.setMP(stats.getMovementPoints());
				monster.setBaseMP(stats.getMaxMovementPoints());
				monster.setSummons((int) stats.getSummoner());
				monster.setSummoned(stats.isSummoned());
				monster.setResPrcnt(new int[] { stats.getAirElementResistPercent(), stats.getEarthElementResistPercent(), stats.getWaterElementResistPercent(), stats.getFireElementResistPercent(), stats.getNeutralElementResistPercent() });
				monster.setResFixed(new int[] { stats.getAirElementReduction(), stats.getEarthElementReduction(), stats.getWaterElementReduction(), stats.getFireElementReduction(), stats.getNeutralElementReduction() });
				monster.setCriticalResistance(stats.getCriticalDamageFixedResist());
				monster.setPushbackResistance(stats.getPushDamageFixedResist());
				monster.setAPLossReduction(stats.getDodgePALostProbability());
				monster.setMPLossReduction(stats.getDodgePMLostProbability());
				monster.setLock(stats.getTackleBlock());
				monster.setDodge(stats.getTackleEvade());
				monster.setCloseCombatResistancePrcnt(100 - stats.getMeleeDamageReceivedPercent());
				monster.setDistanceResistancePrcnt(100 - stats.getRangedDamageReceivedPercent());
				if (i == this.getGameFightSynchronizeMessage().getFighters().size() - 1) {
					toSend.put("id", getId(p.getContextualId()));
					toSend.put("teamId", p.getTeamId());
					toSend.put("x", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[0]);
					toSend.put("y", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[1]);
				}
				else {
					toSend.put("id", getId(p.getContextualId()));
					toSend.put("teamId", p.getTeamId());
					toSend.put("x", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[0]);
					toSend.put("y", CreateMap.rotate(new int[] { p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14 })[1]);
				}
				this.entities.add(monster);
			}
			arr.add(toSend);
		}
		return arr;
	}

	/**
	 * Id position of the entity
	 * 
	 * @param Id of the entity
	 * @return Fight position of the entity
	 */
	public int getId(double id) {
		return this.turnListId.indexOf(id);
	}

	/**
	 * Rotate from
	 * 
	 * @param i : int
	 * @param j : int
	 * @return cellid : int
	 * @author baptiste
	 */
	public static int rotateToCellId(int i, int j) {
		int result = 0;
		if (i + j / 2 == 0) {
			result = i + 13 * (i + j - 13) + (i + j - 14) / 2;
		}
		else {
			result = i + 13 * (i + j - 13) + (i + j - 13) / 2;
		}
		return result;
	}

	/**
	 * Ask the fight bot the best action to do and execute it
	 * 
	 * @throws NumberFormatException
	 * @throws Exception
	 * @author baptiste
	 */
	@SuppressWarnings("unchecked")
	public void fightTurn() throws NumberFormatException, Exception {
		boolean opponentIsAlive = false;
		for (GameFightMonsterInformations m : monsters) {
			if (m.isAlive() && (m.getTeamId() != teamIdPlayer)) opponentIsAlive = true;
		}
		for (GameFightCharacterInformations m : players) {
			if (m.isAlive() && (m.getTeamId() != teamIdPlayer)) opponentIsAlive = true;
		}
		if (!opponentIsAlive) return;
		JSONObject obj = new JSONObject();
		obj.put("id", getId(this.info.getActorId()));
		JSONArray arr = new JSONArray();
		arr.add(obj);
		String s = sendToFightAlgo("g", arr);
		if (s == null) {
			endTurn();
		}

		String[] cmd = s.split(",");

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1500 + new Random().nextInt(800));
					if (cmd[1].equals("m")) {
						moveTo(rotateToCellId(Integer.parseInt(cmd[2]), (Integer.parseInt(cmd[3]))));
					}
					else if (cmd[1].equals("c")) {
						castSpell(Integer.parseInt(cmd[2]), rotateToCellId(Integer.parseInt(cmd[4]), (Integer.parseInt(cmd[5]))));
					}
					else if (cmd[1].equals("None")) {
						endTurn();
					}
					waitToSend();
					return;
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public boolean waitToSend() throws InterruptedException {
		while (!info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight()) {
			Thread.sleep(50);
		}
		while (!info.isBasicNoOperationMsg()) {
			Thread.sleep(50);
		}
		// this.network.append((!Info.newMap && !Info.Storage &&
		// !Info.StorageUpdate && !Info.leaveExchange)
		// && !Info.basicNoOperationMsg);
		// this.network.append(Info.newMap + " " + Info.Storage + " " +
		// Info.StorageUpdate + " " + Info.leaveExchange + " "
		// + Info.basicNoOperationMsg);
		if (info.isBasicNoOperationMsg() && !info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight()) {
			return false;
		}
		else {
			return true;
		}
	}

	public GameFightPlacementPossiblePositionsMessage getGameFightPlacementPossiblePositionsMessage() {
		return gameFightPlacementPossiblePositionsMessage;
	}

	public void setGameFightPlacementPossiblePositionsMessage(GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage) {
		this.gameFightPlacementPossiblePositionsMessage = gameFightPlacementPossiblePositionsMessage;
	}

	public GameEntitiesDispositionMessage getGameEntitiesDispositionMessage() {
		return gameEntitiesDispositionMessage;
	}

	public void setGameEntitiesDispositionMessage(GameEntitiesDispositionMessage gameEntitiesDispositionMessage) {
		this.gameEntitiesDispositionMessage = gameEntitiesDispositionMessage;
	}

	public GameFightSynchronizeMessage getGameFightSynchronizeMessage() {
		return gameFightSynchronizeMessage;
	}

	public void setGameFightSynchronizeMessage(GameFightSynchronizeMessage gameFightSynchronizeMessage) {
		this.gameFightSynchronizeMessage = gameFightSynchronizeMessage;
	}

	public ArrayList<Player> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Player> entities) {
		this.entities = entities;
	}

	public List<Double> getTurnListId() {
		return turnListId;
	}

	public void setTurnListId(List<Double> turnListId) {
		this.turnListId = turnListId;
	}

	private Network getNetwork() {
		return network;
	}

	public List<GameFightMonsterInformations> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<GameFightMonsterInformations> monsters) {
		this.monsters = monsters;
	}

	public JSONArray getSpellJson() {
		return spellJson;
	}

	public void setSpellJson(JSONArray jsonArray) {
		this.spellJson = jsonArray;
	}

	public List<GameFightCharacterInformations> getPlayers() {
		return players;
	}

	public void setPlayers(List<GameFightCharacterInformations> players) {
		this.players = players;
	}

	public boolean isRdy() {
		return isRdy;
	}

	public void setRdy(boolean isRdy) {
		this.isRdy = isRdy;
	}

	public boolean isAdvancedRdy() {
		return advancedRdy;
	}
	
	public void fightToggleAdvancedRdy(boolean isRdy) {
		advancedRdy = isRdy;
	}
}
