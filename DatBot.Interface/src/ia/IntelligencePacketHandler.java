package ia;

import java.util.ArrayList;
import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.entities.entity.OtherEntity;
import ia.fight.ChestIntelligence;
import ia.map.MapIA;
import ia.map.Position;
import protocol.network.messages.game.actions.fight.GameActionFightActivateGlyphTrapMessage;
import protocol.network.messages.game.actions.fight.GameActionFightCarryCharacterMessage;
import protocol.network.messages.game.actions.fight.GameActionFightCloseCombatMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDeathMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellSpellMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellableEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDodgePointLossMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDropCharacterMessage;
import protocol.network.messages.game.actions.fight.GameActionFightExchangePositionsMessage;
import protocol.network.messages.game.actions.fight.GameActionFightInvisibilityMessage;
import protocol.network.messages.game.actions.fight.GameActionFightInvisibleDetectedMessage;
import protocol.network.messages.game.actions.fight.GameActionFightKillMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifeAndShieldPointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsGainMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightMarkCellsMessage;
import protocol.network.messages.game.actions.fight.GameActionFightModifyEffectsDurationMessage;
import protocol.network.messages.game.actions.fight.GameActionFightNoSpellCastMessage;
import protocol.network.messages.game.actions.fight.GameActionFightPointsVariationMessage;
import protocol.network.messages.game.actions.fight.GameActionFightReduceDamagesMessage;
import protocol.network.messages.game.actions.fight.GameActionFightReflectDamagesMessage;
import protocol.network.messages.game.actions.fight.GameActionFightReflectSpellMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSlideMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellCastMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellCooldownVariationMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellImmunityMessage;
import protocol.network.messages.game.actions.fight.GameActionFightStealKamaMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSummonMessage;
import protocol.network.messages.game.actions.fight.GameActionFightTackledMessage;
import protocol.network.messages.game.actions.fight.GameActionFightTeleportOnSameMapMessage;
import protocol.network.messages.game.actions.fight.GameActionFightThrowCharacterMessage;
import protocol.network.messages.game.actions.fight.GameActionFightTriggerEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightTriggerGlyphTrapMessage;
import protocol.network.messages.game.actions.fight.GameActionFightUnmarkCellsMessage;
import protocol.network.messages.game.actions.fight.GameActionFightVanishMessage;
import protocol.network.messages.game.actions.sequence.SequenceEndMessage;
import protocol.network.messages.game.actions.sequence.SequenceStartMessage;
import protocol.network.messages.game.character.stats.FighterStatsListMessage;
import protocol.network.messages.game.context.GameEntitiesDispositionMessage;
import protocol.network.messages.game.context.GameMapMovementMessage;
import protocol.network.messages.game.context.fight.GameFightEndMessage;
import protocol.network.messages.game.context.fight.GameFightHumanReadyStateMessage;
import protocol.network.messages.game.context.fight.GameFightJoinMessage;
import protocol.network.messages.game.context.fight.GameFightLeaveMessage;
import protocol.network.messages.game.context.fight.GameFightNewRoundMessage;
import protocol.network.messages.game.context.fight.GameFightNewWaveMessage;
import protocol.network.messages.game.context.fight.GameFightOptionStateUpdateMessage;
import protocol.network.messages.game.context.fight.GameFightOptionToggleMessage;
import protocol.network.messages.game.context.fight.GameFightPauseMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPossiblePositionsMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementSwapPositionsAcceptMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementSwapPositionsCancelMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementSwapPositionsCancelledMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementSwapPositionsMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementSwapPositionsOfferMessage;
import protocol.network.messages.game.context.fight.GameFightRemoveTeamMemberMessage;
import protocol.network.messages.game.context.fight.GameFightResumeMessage;
import protocol.network.messages.game.context.fight.GameFightStartMessage;
import protocol.network.messages.game.context.fight.GameFightStartingMessage;
import protocol.network.messages.game.context.fight.GameFightSynchronizeMessage;
import protocol.network.messages.game.context.fight.GameFightTurnEndMessage;
import protocol.network.messages.game.context.fight.GameFightTurnFinishMessage;
import protocol.network.messages.game.context.fight.GameFightTurnListMessage;
import protocol.network.messages.game.context.fight.GameFightTurnReadyMessage;
import protocol.network.messages.game.context.fight.GameFightTurnResumeMessage;
import protocol.network.messages.game.context.fight.GameFightTurnStartMessage;
import protocol.network.messages.game.context.fight.GameFightTurnStartPlayingMessage;
import protocol.network.messages.game.context.fight.GameFightUpdateTeamMessage;
import protocol.network.messages.game.context.fight.RefreshCharacterStatsMessage;
import protocol.network.messages.game.context.fight.arena.ArenaFighterLeaveMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeInfoMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeResultMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeTargetUpdateMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeTargetsListMessage;
import protocol.network.messages.game.context.fight.character.GameFightRefreshFighterMessage;
import protocol.network.messages.game.context.fight.character.GameFightShowFighterMessage;
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostStateEffect;
import protocol.network.types.game.context.IdentifiedEntityDispositionInformations;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
import protocol.network.types.game.context.fight.GameFightMonsterInformations;
import utils.GameData;

public class IntelligencePacketHandler {

	private Intelligence ia;

	public IntelligencePacketHandler(Intelligence ia) {
		this.ia = ia;
	}

	public void gameActionFightActivateGlyphTrap(GameActionFightActivateGlyphTrapMessage message) {

	}

	public void gameActionFightCarryCharacter(GameActionFightCarryCharacterMessage message) {

	}

	public void gameActionFightCloseCombat(GameActionFightCloseCombatMessage message) {

	}

	public void gameActionFightDeath(GameActionFightDeathMessage message) {
		Entity e = this.ia.getEntity(message.getTargetId());
		e.getInfo().setAlive(false);
	}

	public void gameActionFightDispellableEffect(GameActionFightDispellableEffectMessage message) {
		Entity e = this.ia.getEntity(message.getEffect().getTargetId());
		if (message.getEffect().getClass().equals(FightTemporaryBoostStateEffect.class)) {

		}
		else if (message.getEffect().getClass().equals(FightTemporaryBoostEffect.class)) {
			FightTemporaryBoostEffect effect = (FightTemporaryBoostEffect) message.getEffect();
			switch (message.getActionId()) {
				//Action points
				case 111:
					if (e == ia.getMain()) {
						((MainEntity) e).getAdditionalInfo().setActionPointsCurrent(((MainEntity) e).getAdditionalInfo().getActionPointsCurrent() + effect.getDelta());
					}
					e.getInfo().getStats().setActionPoints(e.getInfo().getStats().getActionPoints() + effect.getDelta());
					break;
				case 168:
					if (e == ia.getMain()) {
						((MainEntity) e).getAdditionalInfo().setActionPointsCurrent(((MainEntity) e).getAdditionalInfo().getActionPointsCurrent() - effect.getDelta());
					}
					e.getInfo().getStats().setActionPoints(e.getInfo().getStats().getActionPoints() - effect.getDelta());
					break;
				//Mov points
				case 128:
					if (e == ia.getMain()) {
						((MainEntity) e).getAdditionalInfo().setMovementPointsCurrent(((MainEntity) e).getAdditionalInfo().getMovementPointsCurrent() + effect.getDelta());
					}
					e.getInfo().getStats().setMovementPoints(e.getInfo().getStats().getMovementPoints() + effect.getDelta());
					break;
				case 169:
					if (e == ia.getMain()) {
						((MainEntity) e).getAdditionalInfo().setMovementPointsCurrent(((MainEntity) e).getAdditionalInfo().getMovementPointsCurrent() - effect.getDelta());
					}
					e.getInfo().getStats().setMovementPoints(e.getInfo().getStats().getMovementPoints() - effect.getDelta());
					break;
			}
		}
		this.ia.visualizeEntity("Dispellable effect");
	}

	public void gameActionFightDispellEffect(GameActionFightDispellEffectMessage message) {

	}

	public void gameActionFightDispell(GameActionFightDispellMessage message) {

	}

	public void gameActionFightDispellSpell(GameActionFightDispellSpellMessage message) {

	}

	public void gameActionFightDodgePointLoss(GameActionFightDodgePointLossMessage message) {

	}

	public void gameActionFightDropCharacter(GameActionFightDropCharacterMessage message) {

	}

	public void gameActionFightExchangePositions(GameActionFightExchangePositionsMessage message) {

	}

	public void gameActionFightInvisibility(GameActionFightInvisibilityMessage message) {

	}

	public void gameActionFightInvisibleDetected(GameActionFightInvisibleDetectedMessage messsage) {

	}

	public void gameActionFightKill(GameActionFightKillMessage message) {

	}

	public void gameActionFightLifeAndShieldPointsLost(GameActionFightLifeAndShieldPointsLostMessage message) {

	}

	public void gameActionFightLifePointsGain(GameActionFightLifePointsGainMessage message) {

	}

	public void gameActionFightLifePointsLost(GameActionFightLifePointsLostMessage message) {
	}

	public void gameActionFightMarkCells(GameActionFightMarkCellsMessage message) {

	}

	public void gameActionFightModifyEffectsDuration(GameActionFightModifyEffectsDurationMessage message) {

	}

	public void gameActionFightNoSpellCast(GameActionFightNoSpellCastMessage message) {

	}

	public void gameActionFightPointsVariation(GameActionFightPointsVariationMessage message) {
		Entity e = this.ia.getEntity(message.getTargetId());
		switch (message.getActionId()) {
			case 101:
			case 102:
			case 120:
				if (e == ia.getMain()) {
					((MainEntity) e).getAdditionalInfo().setActionPointsCurrent(((MainEntity) e).getAdditionalInfo().getActionPointsCurrent() + message.getDelta());
				}
				e.getInfo().getStats().setActionPoints(e.getInfo().getStats().getActionPoints() + message.getDelta());
				break;
			case 78:
			case 127:
			case 129:
				if (e == ia.getMain()) {
					((MainEntity) e).getAdditionalInfo().setMovementPointsCurrent(((MainEntity) e).getAdditionalInfo().getMovementPointsCurrent() + message.getDelta());
				}
				e.getInfo().getStats().setMovementPoints(e.getInfo().getStats().getMovementPoints() + message.getDelta());
				break;
		}
		this.ia.visualizeEntity("Points Variation");
	}

	public void gameActionFightReduceDamages(GameActionFightReduceDamagesMessage message) {

	}

	public void gameActionFightReflectDamages(GameActionFightReflectDamagesMessage message) {

	}

	public void gameActionFightReflectSpell(GameActionFightReflectSpellMessage message) {

	}

	public void gameActionFightSlide(GameActionFightSlideMessage message) {
		Entity e = this.ia.getEntity(message.getTargetId());
		e.setPosition(MapIA.reshapeToIA(message.getEndCellId()));
		e.getInfo().getDisposition().setCellId(message.getEndCellId());
		this.ia.visualizeEntity("Slide");
	}

	public void gameActionFightSpellCast(GameActionFightSpellCastMessage message) {

	}

	public void gameActionFightSpellCooldownVariation(GameActionFightSpellCooldownVariationMessage message) {

	}

	public void gameActionFightSpellImmunity(GameActionFightSpellImmunityMessage message) {

	}

	public void gameActionFightStealKama(GameActionFightStealKamaMessage message) {

	}

	public void gameActionFightSummon(GameActionFightSummonMessage message) {

	}

	public void gameActionFightTackled(GameActionFightTackledMessage message) {

	}

	public void gameActionFightTeleportOnSameMap(GameActionFightTeleportOnSameMapMessage message) {

	}

	public void gameActionFightThrowCharacter(GameActionFightThrowCharacterMessage message) {

	}

	public void gameActionFightTriggerEffect(GameActionFightTriggerEffectMessage message) {

	}

	public void gameActionFightTriggerGlyphTrap(GameActionFightTriggerGlyphTrapMessage message) {

	}

	public void gameActionFightUnmarkCells(GameActionFightUnmarkCellsMessage message) {

	}

	public void gameActionFightVanish(GameActionFightVanishMessage message) {

	}

	public void arenaFighterLeave(ArenaFighterLeaveMessage message) {

	}

	public void challengeInfo(ChallengeInfoMessage message) {

	}

	public void challengeResult(ChallengeResultMessage message) {

	}

	public void challengeTargetsList(ChallengeTargetsListMessage message) {

	}

	public void challengeTargetUpdate(ChallengeTargetUpdateMessage message) {

	}

	public void gameFightRefreshFighter(GameFightRefreshFighterMessage message) {

	}

	public void gameFightEnd(GameFightEndMessage message) {

	}

	//Tested
	public void gameFightHumanReadyState(GameFightHumanReadyStateMessage message) {
		if (message.getCharacterId() == this.ia.getNetwork().getInfo().getActorId()) {
			this.ia.getMain().setRdy(true);
			Log.writeLogDebugMessage("Setting rdy");
		}
	}

	public void gameFightJoin(GameFightJoinMessage message) {

	}

	public void gameFightLeave(GameFightLeaveMessage message) {

	}

	public void gameFightNewRound(GameFightNewRoundMessage message) {

	}

	public void gameFightNewWave(GameFightNewWaveMessage message) {

	}

	public void gameFightOptionStateUpdate(GameFightOptionStateUpdateMessage message) {

	}

	public void gameFightOptionToggle(GameFightOptionToggleMessage message) {

	}

	public void gameFightPause(GameFightPauseMessage message) {

	}

	//Tested
	public void gameFightPlacementPossiblePositions(GameFightPlacementPossiblePositionsMessage message) {
		if (message.getTeamNumber() == 0) {
			this.ia.getMap().setStartPosAvailable(message.getPositionsForChallengers());
		}
		else {
			this.ia.getMap().setStartPosAvailable(message.getPositionsForDefenders());
		}
	}

	public void gameFightPlacementSwapPositionsAccept(GameFightPlacementSwapPositionsAcceptMessage message) {

	}

	public void gameFightPlacementSwapPositionsCancelled(GameFightPlacementSwapPositionsCancelledMessage message) {

	}

	public void gameFightPlacementSwapPositionsCancel(GameFightPlacementSwapPositionsCancelMessage message) {

	}

	//Tested
	public void gameFightShowFighter(GameFightShowFighterMessage message) {
		int index = ia.entityExists(message.getInformations().getContextualId());
		Entity entity;
		if (index != -1) {
			entity = ia.getEntities().get(index);
		}
		else {
			if (message.getInformations().getContextualId() == this.ia.getNetwork().getInfo().getActorId()) {
				entity = new MainEntity();
			}
			else {
				entity = new OtherEntity();
			}
		}
		entity.setInfo(message.getInformations());
		entity.setPosition(MapIA.reshapeToIA(message.getInformations().getDisposition().getCellId()));
		if (message.getInformations().getClass().getSimpleName().equals("GameFightCharacterInformations")) {
			entity.setLvl(((GameFightCharacterInformations) message.getInformations()).getLevel());
			entity.setBreed(((GameFightCharacterInformations) message.getInformations()).getBreed());
		}
		else {
			entity.setLvl(GameData.getMonsterLvl(((GameFightMonsterInformations) message.getInformations()).getCreatureGenericId(), ((GameFightMonsterInformations) message.getInformations()).getCreatureGrade()));
			entity.setBreed(-1);
		}
		if (index == -1) {
			this.ia.addEntity(entity);
		}
		Log.writeLogDebugMessage("Entity updating");
	}

	public void gameFightPlacementSwapPositions(GameFightPlacementSwapPositionsMessage message) {

	}

	public void gameFightPlacementSwapPositionsOffer(GameFightPlacementSwapPositionsOfferMessage message) {

	}

	public void gameFightRemoveTeamMember(GameFightRemoveTeamMemberMessage message) {

	}

	//Tested
	public void gameFightResume(GameFightResumeMessage message) {
		this.ia.setInitResume(true);
	}

	//Tested
	public void gameFightStarting(GameFightStartingMessage message) throws Exception {
		this.ia.init(new ArrayList<>(), new MapIA(this.ia.getNetwork().getMap().getCells()), new ChestIntelligence(this.ia.utils));
		this.ia.setInit(false); //All init packets will arrived after, until BasioNoOperationMessage
	}

	public void gameFightStart(GameFightStartMessage message) {

	}

	//Tested
	public void gameFightSynchronize(GameFightSynchronizeMessage message) {
		for (int i = 0; i < this.ia.getEntities().size(); i++) {
			for (int j = 0; j < message.getFighters().size(); j++) {
				if (message.getFighters().get(j).getContextualId() == this.ia.getEntities().get(i).getInfo().getContextualId()) {
					this.ia.getEntities().get(i).setInfo(message.getFighters().get(j));
					this.ia.getEntities().get(i).setPosition(MapIA.reshapeToIA(message.getFighters().get(j).getDisposition().getCellId()));
				}
			}
		}
		this.ia.visualizeEntity("Synchronize");
	}

	//Tested
	public void fighterStatsList(FighterStatsListMessage message) {
		for (int i = 0; i < this.ia.getEntities().size(); i++) {
			if (this.ia.getEntities().get(i).getInfo().getContextualId() == this.ia.getNetwork().getInfo().getActorId()) {
				((MainEntity) this.ia.getEntities().get(i)).setAdditionalInfo(message.getStats());
			}
		}
	}

	public void gameFightTurnEnd(GameFightTurnEndMessage message) {

	}

	public void gameFightTurnFinish(GameFightTurnFinishMessage message) {

	}

	public void gameFightTurnList(GameFightTurnListMessage message) {
		this.ia.setTurnList(message.getIds());
	}

	public void gameFightTurnReady(GameFightTurnReadyMessage message) {

	}

	public void gameFightTurnResume(GameFightTurnResumeMessage message) throws Exception {
		//TODO TURN STARTING
		if(this.ia.getMain().getSpells() == null){
			this.ia.getMain().setSpells(this.ia.getNetwork().getInfo().getSpells());
		}
		this.ia.utils.stop(0.56);
		this.ia.getFight().getBestTurn(this.ia.getMain(), this.ia.getEntities(), this.ia.getNetwork().getMap().getCells());
	}

	public void gameFightTurnStart(GameFightTurnStartMessage message) {
	}

	public void gameFightTurnStartPlaying() throws Exception {
		if(this.ia.getMain().getSpells() == null){
			this.ia.getMain().setSpells(this.ia.getNetwork().getInfo().getSpells());
		}
		//TODO TURN STARTING
		this.ia.utils.stop(0.56);
		this.ia.getFight().getBestTurn(this.ia.getMain(), this.ia.getEntities(), this.ia.getNetwork().getMap().getCells());
	}

	public void gameFightUpdateTeam(GameFightUpdateTeamMessage message) {

	}

	public void refreshCharacterStats(RefreshCharacterStatsMessage message) {

	}

	//Tested
	public void gameEntitiesDisposition(GameEntitiesDispositionMessage message) throws Exception {		
		boolean samePos = true; // Permet de savoir si le packet est utile ou non, si quelqu'un a changé de place
		for (IdentifiedEntityDispositionInformations e : message.getDispositions()) {
			int index = ia.entityExists(e.getId());
			if(e.getCellId() < 0 | e.getCellId() > 599){
				Log.writeLogDebugMessage("Spectator detected");
				continue;
			}
			System.out.println("Updating entity");
			Position position = MapIA.reshapeToIA(e.getCellId());
			if (index != -1) {
				if (!position.deepEquals(ia.getEntities().get(index).getPosition())) {
					ia.getEntities().get(index).setPosition(position);
					ia.getEntities().get(index).getInfo().getDisposition().setCellId(e.getCellId());
					if (e.getId() != this.ia.getNetwork().getInfo().getActorId()) samePos = false;
				}
			}
			else {
				Entity entity;
				if (e.getId() == this.ia.getNetwork().getInfo().getActorId()) {
					entity = new MainEntity();
				}
				else {
					entity = new OtherEntity();
				}
				GameFightFighterInformations info = new GameFightFighterInformations();
				info.setContextualId(e.getId());
				entity.setInfo(info);
				entity.setPosition(position);
				this.ia.addEntity(entity);
			}
		}

		if (this.ia.isInit() && !samePos) {
			Log.writeLogDebugMessage("Finding best position from gameEntitiesDispositions");
			this.ia.getBestPlacement();
		}
		this.ia.visualizeEntity("Entities Disposition");
	}

	public void gameMapMovement(GameMapMovementMessage message) {
		Entity e = this.ia.getEntity(message.getActorId());
		
		if(e != null){
			e.getInfo().getDisposition().setCellId(message.getKeyMovements().get(message.getKeyMovements().size() - 1));
			e.setPosition(MapIA.reshapeToIA((message.getKeyMovements().get(message.getKeyMovements().size() - 1))));
		}
	}

	public void sequenceEndMessage(SequenceEndMessage message) throws Exception {
		if((message.getSequenceType() == 1 || message.getSequenceType() == 5) && message.getAuthorId() == this.ia.getNetwork().getInfo().getActorId()){
			this.ia.utils.stop(0.56);
			this.ia.getFight().getBestTurn(this.ia.getMain(), this.ia.getEntities(), this.ia.getNetwork().getMap().getCells());
		}
	}

	public void sequenceStartMessage(SequenceStartMessage message) {

	}
}
