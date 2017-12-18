package Game.combat;

import java.util.ArrayList;
import java.util.List;

import Game.Info;
import Game.map.Map;
import Game.movement.CellMovement;
import Game.movement.Movement;
import ia.fight.brain.Game;
import ia.fight.brain.classes.Cra;
import ia.fight.brain.classes.Monster;
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
	public static GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage; // Available
	public static GameEntitiesDispositionMessage gameEntitiesDispositionMessage; // Disposition
	public static GameFightSynchronizeMessage gameFightSynchronizeMessage; // Recap
	public static List<Player> entities = new ArrayList<>();
	public static String spellToSend;

	
	public static void sendToFightAlgo(String command, Object[] param)
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
		System.out.println(Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", Info.botInstance, ++Info.msgIdFight, "f", "cmd", command, newParam)));
	}
	
	public static void sendToFightAlgoInit(String command, Object[] param, List<Player> players)
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
		System.out.println(Game.executeCommand(String.format("%s;%s;%s;%s;%s;[%s]", Info.botInstance, ++Info.msgIdFight, "f", "cmd", command, newParam, players)));
	}


	public static void setBeginingPosition() throws Exception
	{
		// TODO LYSANDRE
		int cellId = 0;
		GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellId);
		Network.sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Placement position : " + cellId);
	}

	public static void fightReady() throws Exception
	{
		Network.sendToServer(new GameFightReadyMessage(true), GameFightReadyMessage.ProtocolId, "Ready");
	}

	public static void endTurn() throws Exception
	{
		Network.sendToServer(new GameFightTurnFinishMessage(false), GameFightTurnFinishMessage.ProtocolId, "End turn");
	}

	public static boolean moveTo(int cellId) throws Exception
	{
		CellMovement mov = Movement.MoveToCell(cellId);
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
	
	public static void castSpell(int id, int cellId) throws Exception{
		GameActionFightCastRequestMessage gameActionFightCastRequestMessage = new GameActionFightCastRequestMessage(id, cellId);
		Network.sendToServer(gameActionFightCastRequestMessage, GameActionFightCastRequestMessage.ProtocolId, "Cast spell");
	}
	
	
	public static String init(){
		Player player = null;
		String toSend = "";
		for(int i = 0; i < gameFightSynchronizeMessage.fighters.size() ; i++){
			if (gameFightSynchronizeMessage.fighters.get(i).getClass().getSimpleName().equals("GameFightCharacterInformations")){
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
				if(i == gameFightSynchronizeMessage.fighters.size() - 1){
					toSend += "[" + p.contextualId + "," + p.teamId + "," + p.disposition.cellId % 14 + "," + p.disposition.cellId / 14 + "]";
				} else {
					toSend += "[" + p.contextualId + "," + p.teamId + "," + p.disposition.cellId % 14 + "," + p.disposition.cellId / 14 + "],";
				}
				entities.add(player);
			} else if (gameFightSynchronizeMessage.fighters.get(i).getClass().getSimpleName().equals("GameFightMonsterInformations")){
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
				if(i == gameFightSynchronizeMessage.fighters.size() - 1){
					toSend += "[" + p.contextualId + "," + p.teamId + "," + p.disposition.cellId % 14 + "," + p.disposition.cellId / 14 + "]";
				} else {
					toSend += "[" + p.contextualId + "," + p.teamId + "," + p.disposition.cellId % 14 + "," + p.disposition.cellId / 14 + "],";
				}
				entities.add(monster);	
			}
		}
		return toSend;
	}
}
