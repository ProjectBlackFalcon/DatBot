package Game.combat;

import java.util.ArrayList;
import java.util.List;

import Game.Info;
import Game.movement.CellMovement;
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
	
	// Init fight
	public GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage; // Available
	public GameEntitiesDispositionMessage gameEntitiesDispositionMessage; // Disposition
	public GameFightSynchronizeMessage gameFightSynchronizeMessage; // Recap
	public ArrayList<Player> entities = new ArrayList<>();
	public List<Double> turnListId;
	public String spellToSend;
	private Network network;
	
	public Fight(Network network){
		this.network = network;
	}

	/**
	 * For spell casted
	 * @return [idCaster,posX,posY,spellId,[targetId,LPlost,LPmaxLost],...,[targetId,effectId,turnDuration,dispelable],...]
	 */
	
	/**
	 * Communicate with the fight algo and the results 
	 * @param String command, Ojbect [] parameters
	 * @return String results 
	 * @author baptiste
	 */
	public String sendToFightAlgo(String command, Object[] param)
	{
		String newParam = "";
		for (int i = 0; i < param.length; i++)
		{
			if (i == param.length - 1)
			{
				newParam += param[i];
			}
			else
			{
				newParam += param[i] + ", ";
			}
		}
		return Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", this.network.getInfo().getBotInstance(), this.network.getInfo().addAndGetMsgIdFight(), "f", "cmd", command, newParam));
	}
	
	/**
	 * Init the entities to the fight algo
	 * @param String command, Ojbect [] parameters, ArrayList<Players> players
	 * @return String results 
	 * @author baptiste
	 */
	public String sendToFightAlgo(String command, Object[] param, ArrayList<Player> players)
	{
		String newParam = "";
		for (int i = 0; i < param.length; i++)
		{
			if (i == param.length - 1)
			{
				newParam += param[i];
			}
			else
			{
				newParam += param[i] + ", ";
			}
		}
		return Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", this.network.getInfo().getBotInstance(), this.network.getInfo().addAndGetMsgIdFight(), "f", "cmd", command, newParam), players);
	}

	/**
	 * Set the position of the player at the begining of the fight
	 * @author baptiste
	 */
	public void setBeginingPosition() throws Exception
	{
		// TODO LYSANDRE
		int cellId = 0;
		GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellId);
		network.sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Placement position : " + cellId);
	}

	/**
	 * Start the fight
	 * @author baptiste
	 */
	public void fightReady() throws Exception
	{
		network.sendToServer(new GameFightReadyMessage(true), GameFightReadyMessage.ProtocolId, "Ready");
	}

	/**
	 * End the turn
	 * @author baptiste
	 */
	public void endTurn() throws Exception
	{
		network.sendToServer(new GameFightTurnFinishMessage(false), GameFightTurnFinishMessage.ProtocolId, "End turn");
	}

	/**
	 * Move the player during fight using MP
	 * @param int cellId
	 * @return boolean moved
	 * @author baptiste
	 */
	public boolean moveTo(int cellId) throws Exception
	{
		CellMovement mov = this.getNetwork().getMovement().MoveToCell(cellId);
		if (mov == null || mov.path == null)
		{
			return false;
		}
		else if (this.network.getInfo().getCellId() == cellId)
		{
			return true;
		}
		else
		{
			mov.performMovement();
			if (this.network.getInfo().getCellId() == cellId)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	/**
	 * Cast spell
	 * @param int id, int cellId
	 * @author baptiste
	 */
	public void castSpell(int id, int cellId) throws Exception{
		GameActionFightCastRequestMessage gameActionFightCastRequestMessage = new GameActionFightCastRequestMessage(id, cellId);
		network.sendToServer(gameActionFightCastRequestMessage, GameActionFightCastRequestMessage.ProtocolId, "Cast spell");
	}
	
	/**
	 * Init the entities
	 * Init List<players> entities
	 * @return String [id,teamId,posX,posY],...
	 */
	public String init(){
		Player player = null;
		String toSend = "";
		for(int i = 0; i < gameFightSynchronizeMessage.getFighters().size() ; i++){
			if (this.getGameFightSynchronizeMessage().getFighters().get(i).getClass().getSimpleName().equals("GameFightCharacterInformations")){
				GameFightCharacterInformations p = (GameFightCharacterInformations) gameFightSynchronizeMessage.getFighters().get(i);
				GameFightMinimalStats stats = p.getStats();
				if(p.getBreed() == 9){
					player = new Cra(p.getName(),p.getStats().getLifePoints(),p.getStats().getActionPoints(),p.getStats().getMovementPoints(),p.getLevel());
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
				player.setResPrcnt(new int[]{stats.getAirElementResistPercent(),stats.getEarthElementResistPercent(),stats.getWaterElementResistPercent(),stats.getFireElementResistPercent(),stats.getNeutralElementResistPercent()});
				player.setResFixed(new int[]{stats.getAirElementReduction(),stats.getEarthElementReduction(),stats.getWaterElementReduction(),stats.getFireElementReduction(),stats.getNeutralElementReduction()});
				player.setCriticalResistance(stats.getCriticalDamageFixedResist());
				player.setPushbackResistance(stats.getPushDamageFixedResist());
				player.setAPLossReduction(stats.getDodgePALostProbability());
				player.setMPLossReduction(stats.getDodgePMLostProbability());
				player.setLock(stats.getTackleBlock());
				player.setDodge(stats.getTackleEvade());
				player.setCloseCombatResistancePrcnt(100 - stats.getMeleeDamageReceivedPercent());
				player.setDistanceResistancePrcnt(100 - stats.getRangedDamageReceivedPercent());
				if(i == this.getGameFightSynchronizeMessage().getFighters().size() - 1){
					toSend += "[" + getId(p.getContextualId()) + "," + p.getTeamId() + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[0] + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[1] + "]";
				} else {
					toSend += "[" + getId(p.getContextualId()) + "," + p.getTeamId() + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[0] + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[1] + "],";
				}
				this.entities.add(player);
			} else if (this.getGameFightSynchronizeMessage().getFighters().get(i).getClass().getSimpleName().equals("GameFightMonsterInformations")){
				GameFightMonsterInformations p = (GameFightMonsterInformations) gameFightSynchronizeMessage.getFighters().get(i);
				GameFightMinimalStats stats = p.getStats();
				Monster monster = new Monster(String.valueOf(p.getCreatureGenericId()),p.getStats().getLifePoints(),p.getStats().getActionPoints(),p.getStats().getMovementPoints(),p.getCreatureGrade());
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
				monster.setResPrcnt(new int[]{stats.getAirElementResistPercent(),stats.getEarthElementResistPercent(),stats.getWaterElementResistPercent(),stats.getFireElementResistPercent(),stats.getNeutralElementResistPercent()});
				monster.setResFixed(new int[]{stats.getAirElementReduction(),stats.getEarthElementReduction(),stats.getWaterElementReduction(),stats.getFireElementReduction(),stats.getNeutralElementReduction()});
				monster.setCriticalResistance(stats.getCriticalDamageFixedResist());
				monster.setPushbackResistance(stats.getPushDamageFixedResist());
				monster.setAPLossReduction(stats.getDodgePALostProbability());
				monster.setMPLossReduction(stats.getDodgePMLostProbability());
				monster.setLock(stats.getTackleBlock());
				monster.setDodge(stats.getTackleEvade());
				monster.setCloseCombatResistancePrcnt(100 - stats.getMeleeDamageReceivedPercent());
				monster.setDistanceResistancePrcnt(100 - stats.getRangedDamageReceivedPercent());
				if(i == this.getGameFightSynchronizeMessage().getFighters().size() - 1){
					toSend += "[" + getId(p.getContextualId()) + "," + p.getTeamId() + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[0] + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[1] + "]";
				} else {
					toSend += "[" + getId(p.getContextualId()) + "," + p.getTeamId() + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[0] + "," + CreateMap.rotate(new int[]{ p.getDisposition().getCellId() % 14, p.getDisposition().getCellId() / 14})[1] + "],";
				}
				this.entities.add(monster);	
			}
		}
		return toSend;
	}
	
	
	/**
	 * Id position of the entity
	 * @param Id of the entity
	 * @return Fight position of the entity
	 */
	public int getId(double id){
		return this.turnListId.indexOf(id);
	}
	
	public void fightTurn() throws NumberFormatException, Exception{
		String s = sendToFightAlgo("g", new Object[] { getId(this.network.getInfo().getActorId()) });
		String[] message = s.split(";");
		this.network.getInfo().setMsgIdFight(Integer.parseInt(message[1]));
		message[5] = message[5].substring(1, message[5].length() - 1);
		if(message[5] == null){
			endTurn();
		}
		String[] cmd = message[5].split(",");
		if(cmd[0].equals("m")){
			moveTo(Integer.parseInt(cmd[1]) + (Integer.parseInt(cmd[2])*14));
		} else if (cmd[0].equals("c")){
			castSpell(Integer.parseInt(cmd[0]),Integer.parseInt(cmd[2]) + (Integer.parseInt(cmd[3])*14));
		}		
	}

	public GameFightPlacementPossiblePositionsMessage getGameFightPlacementPossiblePositionsMessage()
	{
		return gameFightPlacementPossiblePositionsMessage;
	}

	public void setGameFightPlacementPossiblePositionsMessage(GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage)
	{
		this.gameFightPlacementPossiblePositionsMessage = gameFightPlacementPossiblePositionsMessage;
	}

	public GameEntitiesDispositionMessage getGameEntitiesDispositionMessage()
	{
		return gameEntitiesDispositionMessage;
	}

	public void setGameEntitiesDispositionMessage(GameEntitiesDispositionMessage gameEntitiesDispositionMessage)
	{
		this.gameEntitiesDispositionMessage = gameEntitiesDispositionMessage;
	}

	public GameFightSynchronizeMessage getGameFightSynchronizeMessage()
	{
		return gameFightSynchronizeMessage;
	}

	public void setGameFightSynchronizeMessage(GameFightSynchronizeMessage gameFightSynchronizeMessage)
	{
		this.gameFightSynchronizeMessage = gameFightSynchronizeMessage;
	}

	public ArrayList<Player> getEntities()
	{
		return entities;
	}

	public void setEntities(ArrayList<Player> entities)
	{
		this.entities = entities;
	}

	public List<Double> getTurnListId()
	{
		return turnListId;
	}

	public void setTurnListId(List<Double> turnListId)
	{
		this.turnListId = turnListId;
	}

	public String getSpellToSend()
	{
		return spellToSend;
	}

	public void setSpellToSend(String spellToSend)
	{
		this.spellToSend = spellToSend;
	}

	protected Network getNetwork()
	{
		return network;
	}
}
