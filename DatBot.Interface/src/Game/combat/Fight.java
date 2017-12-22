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
		return Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", Info.botInstance, ++Info.msgIdFight, "f", "cmd", command, newParam));
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
		return Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", Info.botInstance, ++Info.msgIdFight, "f", "cmd", command, newParam), players);
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
		else if (Info.cellId == cellId)
		{
			return true;
		}
		else
		{
			mov.performMovement();
			if (Info.cellId == cellId)
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
		for(int i = 0; i < gameFightSynchronizeMessage.fighters.size() ; i++){
			if (this.getGameFightSynchronizeMessage().fighters.get(i).getClass().getSimpleName().equals("GameFightCharacterInformations")){
				GameFightCharacterInformations p = (GameFightCharacterInformations) gameFightSynchronizeMessage.fighters.get(i);
				GameFightMinimalStats stats = p.stats;
				if(p.breed == 9){
					player = new Cra(p.name,p.stats.lifePoints,p.stats.actionPoints,p.stats.movementPoints,p.level);
				}
				player.setLP(stats.lifePoints);
				player.setBaseLP(stats.baseMaxLifePoints);
				player.setMaxLP(stats.maxLifePoints);
				player.setShield(stats.shieldPoints);
				player.setAP(stats.actionPoints);
				player.setBaseAP(stats.maxActionPoints);
				player.setMP(stats.movementPoints);
				player.setBaseMP(stats.maxMovementPoints);
				player.setSummons((int) stats.summoner);
				player.setSummoned(stats.summoned);
				player.setResPrcnt(new int[]{stats.airElementResistPercent,stats.earthElementResistPercent,stats.waterElementResistPercent,stats.fireElementResistPercent,stats.neutralElementResistPercent});
				player.setResFixed(new int[]{stats.airElementReduction,stats.earthElementReduction,stats.waterElementReduction,stats.fireElementReduction,stats.neutralElementReduction});
				player.setCriticalResistance(stats.criticalDamageFixedResist);
				player.setPushbackResistance(stats.pushDamageFixedResist);
				player.setAPLossReduction(stats.dodgePALostProbability);
				player.setMPLossReduction(stats.dodgePMLostProbability);
				player.setLock(stats.tackleBlock);
				player.setDodge(stats.tackleEvade);
				player.setCloseCombatResistancePrcnt(100 - stats.meleeDamageReceivedPercent);
				player.setDistanceResistancePrcnt(100 - stats.rangedDamageReceivedPercent);
				if(i == this.getGameFightSynchronizeMessage().fighters.size() - 1){
					toSend += "[" + getId(p.contextualId) + "," + p.teamId + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[0] + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[1] + "]";
				} else {
					toSend += "[" + getId(p.contextualId) + "," + p.teamId + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[0] + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[1] + "],";
				}
				this.entities.add(player);
			} else if (this.getGameFightSynchronizeMessage().fighters.get(i).getClass().getSimpleName().equals("GameFightMonsterInformations")){
				GameFightMonsterInformations p = (GameFightMonsterInformations) gameFightSynchronizeMessage.fighters.get(i);
				GameFightMinimalStats stats = p.stats;
				Monster monster = new Monster(String.valueOf(p.creatureGenericId),p.stats.lifePoints,p.stats.actionPoints,p.stats.movementPoints,p.creatureGrade);
				monster.setLP(stats.lifePoints);
				monster.setBaseLP(stats.baseMaxLifePoints);
				monster.setMaxLP(stats.maxLifePoints);
				monster.setShield(stats.shieldPoints);
				monster.setAP(stats.actionPoints);
				monster.setBaseAP(stats.maxActionPoints);
				monster.setMP(stats.movementPoints);
				monster.setBaseMP(stats.maxMovementPoints);
				monster.setSummons((int) stats.summoner);
				monster.setSummoned(stats.summoned);
				monster.setResPrcnt(new int[]{stats.airElementResistPercent,stats.earthElementResistPercent,stats.waterElementResistPercent,stats.fireElementResistPercent,stats.neutralElementResistPercent});
				monster.setResFixed(new int[]{stats.airElementReduction,stats.earthElementReduction,stats.waterElementReduction,stats.fireElementReduction,stats.neutralElementReduction});
				monster.setCriticalResistance(stats.criticalDamageFixedResist);
				monster.setPushbackResistance(stats.pushDamageFixedResist);
				monster.setAPLossReduction(stats.dodgePALostProbability);
				monster.setMPLossReduction(stats.dodgePMLostProbability);
				monster.setLock(stats.tackleBlock);
				monster.setDodge(stats.tackleEvade);
				monster.setCloseCombatResistancePrcnt(100 - stats.meleeDamageReceivedPercent);
				monster.setDistanceResistancePrcnt(100 - stats.rangedDamageReceivedPercent);
				if(i == this.getGameFightSynchronizeMessage().fighters.size() - 1){
					toSend += "[" + getId(p.contextualId) + "," + p.teamId + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[0] + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[1] + "]";
				} else {
					toSend += "[" + getId(p.contextualId) + "," + p.teamId + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[0] + "," + CreateMap.rotate(new int[]{ p.disposition.cellId % 14, p.disposition.cellId / 14})[1] + "],";
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
		String s = sendToFightAlgo("g", new Object[] { getId(Info.actorId) });
		String[] message = s.split(";");
		Info.msgIdFight = Integer.parseInt(message[1]);
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
	
	public static int[] rotateToCellId(int [] val){
		int input_i = val[0];
		int input_j = val[1];
		int output_i, output_j;

		if (input_j % 2 == 0)
		{
			output_i = input_i + input_j / 2;
			output_j = 13 + (input_j / 2) - input_i;
		}
		else
		{
			output_i = 1 + input_i + input_j / 2;
			output_j = 13 + input_j / 2 - input_i;
		}
		
		return new int[] { output_i, output_j };
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
