package main.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.map.MapMovement;
import game.movement.CellMovement;
import game.plugin.Dragodinde;
import game.plugin.Hunt;
import protocol.network.Network;
import protocol.network.messages.game.context.mount.MountFeedRequestMessage;
import protocol.network.messages.game.context.mount.MountSetXpRatioRequestMessage;
import protocol.network.messages.game.context.mount.MountToggleRidingRequestMessage;
import protocol.network.messages.game.context.roleplay.emote.EmotePlayRequestMessage;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayAttackMonsterRequestMessage;
import protocol.network.messages.game.context.roleplay.havenbag.EnterHavenBagRequestMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntFlagRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntGiveUpRequestMessage;
import protocol.network.messages.game.dialog.LeaveDialogRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.interactive.zaap.TeleportRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeBidHousePriceMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeHandleMountsStableMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectModifyPricedMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMovePricedMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;

public class ModelConnexion {

	private boolean bankOppened;
	private Network network;

	public ModelConnexion(Network network) throws InterruptedException {
		this.network = network;
	}

	public Object[] getReturn(String cmd, String param) throws NumberFormatException, Exception {
		Object[] toSend = null;
		
		param = param.replaceAll(" ", "");
		param = param.replaceAll("\\(", "");
		param = param.replaceAll("\\)", "");
		
		switch (cmd) {
			case "getMap":
				toSend = new Object[] { String.valueOf("(" + this.network.getInfo().getCoords()[0]) + "," + String.valueOf(this.network.getInfo().getCoords()[1]) + ")", this.network.getInfo().getCellId(), this.network.getInfo().getWorldmap(), Integer.valueOf((int) this.network.getInfo().getMapId()) };
				break;
			case "move":
				toSend = move(Integer.parseInt(param));
				break;
			case "changeMap":
				String[] infoMov = param.split(",");
				MapMovement mapMovement = this.network.getMovement().ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1]);
				if (mapMovement == null || mapMovement.getCellMovement() == null) {
					toSend = new Object[] { "False" };
					this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else {
					mapMovement.PerformChangement();
					if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				break;
			case "changeMapTest":
				MapMovement mapMovement1 = this.network.getMovement().ChangeMap(param);
				if (mapMovement1 == null) {
					toSend = new Object[] { "False" };
					this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else {
					mapMovement1.PerformChangement();
					if (this.network.getMovement().moveOver()) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				break;
			case "getResources":
				toSend = new Object[] { this.network.getInteractive().getFarmCell() };
				break;
			case "harvest":
				if (this.network.getInteractive().harvestCell(Integer.parseInt(param))) {
					toSend = new Object[] { this.network.getInteractive().getLastItemHarvestedId(), this.network.getInteractive().getQuantityLastItemHarvested(), this.network.getInfo().getWeight(), this.network.getInfo().getWeigthMax() };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getStats":
				toSend = new Object[] { this.network.getStats() };
				break;
			case "goAstrub":
				if (this.network.getMap().getId() == 153880835) {
					NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20001, 3, 153880835);
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request Npc to go to Astrub");
					if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "goIncarnam":
				int r = this.network.getInteractive().getStatue();
				if (r != -1) {
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getInteractive().getElementIdStatue(), this.network.getInteractive().getSkillInstanceUidStatue());
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
					if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getStatue":
				int statueCellId = this.network.getInteractive().getStatue();
				if (statueCellId != -1) {
					toSend = new Object[] { statueCellId };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getBankDoor":
				if (this.network.getMap().getId() == 144931) {
					toSend = new Object[] { this.network.getBank().cellIdBrakmarIN, this.network.getBank().cellIdBrakmarOUT };
				}
				else if (this.network.getMap().getId() == 84674566) {
					toSend = new Object[] { this.network.getBank().cellIdAstrubIN, this.network.getBank().cellIdAstrubOUT };
				}
				else if (this.network.getMap().getId() == 147254) {
					toSend = new Object[] { this.network.getBank().cellIdBontaIN, this.network.getBank().cellIdBontaOUT };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "goBank":
				if (this.network.getMap().getId() == 144931) { // Brakmar
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveBrakmarIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveBrakmarIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using this.network.getBank() door");
					if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else if (this.network.getMap().getId() == 84674566) { // Astrub
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveAstrubIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveAstrubIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using this.network.getBank() door");
					if (this.waitToSend("Map")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else if (this.network.getMap().getId() == 147254) { // Bonta
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveBontaIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveBontaIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using this.network.getBank() door");
					if (this.waitToSend("Map")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "openBank":
				if (this.network.getMap().getId() == 83887104 || this.network.getMap().getId() == 2884617 || this.network.getMap().getId() == 8912911) {
					NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) this.network.getNpc().getNpc().get(0).getContextualId(), 3, this.network.getMap().getId());
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
					bankOppened = true;
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "closeBank":
				if (bankOppened) {
					getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close this.network.getBank()");
					this.waitToSend();
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				bankOppened = false;
				break;
			case "dropBank":
				if (bankOppened) {
					String[] toBank = param.split(",");
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0]), Integer.parseInt(toBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getBank":
				if (bankOppened) {
					String[] fromBank = param.split(",");
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0]), -Integer.parseInt(fromBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "dropBankList":
				if (bankOppened) {
					String[] toBankList = param.split(",");
					List<Integer> ids = new ArrayList<Integer>();
					for (String string : toBankList) {
						ids.add(Integer.parseInt(string.replaceAll("\\s+", "")));
					}
					ExchangeObjectTransfertListFromInvMessage exchangeObjectTransfertListFromInvMessage = new ExchangeObjectTransfertListFromInvMessage(ids);
					getNetwork().sendToServer(exchangeObjectTransfertListFromInvMessage, ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list in this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getBankList":
				if (bankOppened) {
					String[] fromBankList = param.split(",");
					List<Integer> ids1 = new ArrayList<Integer>();
					for (String string : fromBankList) {
						ids1.add(Integer.parseInt(string.replaceAll("\\s+", "")));
					}
					ExchangeObjectTransfertListToInvMessage exchangeObjectTransfertListToInvMessage = new ExchangeObjectTransfertListToInvMessage(ids1);
					getNetwork().sendToServer(exchangeObjectTransfertListToInvMessage, ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getBankKamas":
				if (bankOppened) {
					ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage = new ExchangeObjectMoveKamaMessage(-Integer.parseInt(param));
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "dropBankKamas":
				if (bankOppened) {
					ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage1 = new ExchangeObjectMoveKamaMessage(Integer.parseInt(param));
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "dropBankAll":
				if (bankOppened) {
					ExchangeObjectTransfertAllFromInvMessage exchangeObjectTransfertAllFromInvMessage = new ExchangeObjectTransfertAllFromInvMessage();
					getNetwork().sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId, "Drop all items in this.network.getBank()");
					if (this.waitToSend()) {
						toSend = new Object[] { this.network.getStats(), this.network.getBank() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getMonsters":
				toSend = new Object[] { this.network.getMonsters() };
				break;
			case "attackMonster":
				int id = (int) Double.parseDouble(param);
				boolean canAtk = false;
				for (int i = 0; i < this.network.getMonsters().getMonsters().size(); i++) {
					if (id == (int) this.network.getMonsters().getMonsters().get(i).getContextualId()) {
						if (this.network.getInfo().getCellId() == this.network.getMonsters().getMonsters().get(i).getDisposition().getCellId()) {
							canAtk = true;
						}
					}
				}
				if (canAtk) {
					GameRolePlayAttackMonsterRequestMessage gameRolePlayAttackMonsterRequestMessage = new GameRolePlayAttackMonsterRequestMessage(id);
					getNetwork().sendToServer(gameRolePlayAttackMonsterRequestMessage, GameRolePlayAttackMonsterRequestMessage.ProtocolId, "Attack monster " + id);
					if (this.waitToSend()) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { this.network.getMonsters() };
				}
				break;
			case "enterBag":
				EnterHavenBagRequestMessage enterHavenBagRequestMessage = new EnterHavenBagRequestMessage(this.network.getInfo().getActorId());
				getNetwork().sendToServer(enterHavenBagRequestMessage, EnterHavenBagRequestMessage.ProtocolId, "Entering havenBag");
				if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "exitBag":
				EnterHavenBagRequestMessage enterHavenBagRequestMessage2 = new EnterHavenBagRequestMessage(this.network.getInfo().getActorId());
				getNetwork().sendToServer(enterHavenBagRequestMessage2, EnterHavenBagRequestMessage.ProtocolId, "Exiting havenBag");
				if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getZaap":
				if (!(this.network.getInteractive().getInteractive(114) == null)) {
					toSend = new Object[] { this.network.getInteractive().getInteractive(114)[0] };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "useZaap":
				String newParam = param.replaceAll("\\(", "");
				newParam = newParam.replaceAll("\\)", "");
				String[] paramZaap = newParam.split(",");
				int[] interactive = this.network.getInteractive().getInteractive(114);
				if (!(interactive == null)) {
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive[1], interactive[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using zaap");
					if (this.waitToSend()) {
						Thread.sleep(1500 + new Random().nextInt(800));
						double mapId = this.network.getInteractive().getMapIdZaap(Integer.parseInt(paramZaap[0]), Integer.parseInt(paramZaap[1]));
						if (mapId != -1) {
							TeleportRequestMessage teleportRequestMessage = new TeleportRequestMessage(0, mapId);
							getNetwork().sendToServer(teleportRequestMessage, TeleportRequestMessage.ProtocolId, "Teleport to " + param);
							if (this.waitToSendMap(this.network.getMap().getId())) {
								toSend = new Object[] { String.valueOf("(" + this.network.getInfo().getCoords()[0]) + "," + String.valueOf(this.network.getInfo().getCoords()[1]) + ")", this.network.getInfo().getCellId(), this.network.getInfo().getWorldmap(), Integer.valueOf((int) this.network.getInfo().getMapId()) };
							}
							else {
								toSend = new Object[] { "False" };
							}
						}
						else {
							toSend = new Object[] { "False" };
						}
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getHuntingHallDoorCell":
				if (this.network.getMap().getId() == 142088718) {
					toSend = new Object[] { this.network.getInteractive().getInteractive(184)[0] };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Go into the first hall Then move to cell 292 to go in main hall
			 */
			case "goHuntingHall":
				if (this.network.getMap().getId() == 142088718 && this.network.getInfo().getCellId() == 356) {
					int[] interactive2 = this.network.getInteractive().getInteractive(184);
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Entering hunting hall");
					if (this.waitToSendMap(this.network.getMap().getId())) {
						sleepShort();
						toSend = move(292);
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Go to use cell 504 to go to first hall Then exit the building
			 */
			case "exitHuntingHall":
				if (this.network.getMap().getId() == 128452097) {
					move(504);
					sleepShort();
				}
				if (this.network.getMap().getId() == 128451073) {
					move(536);
					int[] interactive2 = this.network.getInteractive().getInteractive(184);
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Exiting hunting hall");
					if (this.waitToSendMap(this.network.getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Bot needs to go to cell 304 to get new hunt It will get the
			 * highest hunt for its level
			 * Return (the number of steps, number of index in first step)
			 *
			 */
			case "newHunt":
				if (this.network.getMap().getId() == 128452097 && !this.network.getInfo().isInHunt()) {
					if (!(this.network.getInfo().getCellId() == 304)) {
						move(304);
					}
					int[] interactiveHunt = Hunt.getHuntFromLvl(Integer.parseInt(param));
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveHunt[0], interactiveHunt[1]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Getting new hunt");
					while (!this.network.getInfo().isHuntAnswered()) {
						Thread.sleep(50);
					}
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "abandonHunt":
				if (this.network.getInfo().isInHunt()) {
					TreasureHuntGiveUpRequestMessage huntGiveUpRequestMessage = new TreasureHuntGiveUpRequestMessage(0);
					getNetwork().sendToServer(huntGiveUpRequestMessage, TreasureHuntGiveUpRequestMessage.ProtocolId, "Abandon hunt");
					if (this.waitToSend("Hunt")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Get the start map of the hunt
			 */
			case "getHuntStart":
				if (this.network.getInfo().isInHunt()) {
					toSend = new Object[] { "(" + this.getNetwork().getHunt().getStartMapCoords()[0] + "," + this.getNetwork().getHunt().getStartMapCoords()[1] + ")" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Return the current clue
			 * Return fight if its the last step
			 */
			case "getClue":
				if (this.network.getInfo().isInHunt() && !(this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					toSend = new Object[] { "\"" + this.network.getHunt().getCurrentClue() + "\", \"" + Hunt.getDirection(this.network.getHunt().getCurrentDir()) + "\""};
				} else if(this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)){
					toSend = new Object[] { "Fight" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "validateClue":
				if (this.network.getInfo().isInHunt() && !(this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					TreasureHuntFlagRequestMessage treasureHuntFlagRequestMessage = new TreasureHuntFlagRequestMessage(0, this.network.getHunt().getCurrentIndex());
					getNetwork().sendToServer(treasureHuntFlagRequestMessage, TreasureHuntFlagRequestMessage.ProtocolId, "Validating clue");
					if (this.waitToSend("Hunt")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "checkPhorror":
				if (this.network.getHunt().isPhorror()) {
					toSend = new Object[] { "\"" + this.getNetwork().getHunt().getPhorrorName() + "\"" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Validate the step 
			 * Return the number of index for the next step
			 */
			case "validateStep":
				if (this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentIndex() == this.getNetwork().getHunt().getNumberOfIndex()) && !(this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
					getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Validating step");
					if (this.waitToSend("Hunt")) {
						if (this.network.getInfo().isStepSuccess()) {
							toSend = new Object[] { "True" };
						}
						else {
							toSend = new Object[] { "False" };
						}
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "huntFight":
				if (this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps() - 1)) {
					TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
					getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Starting hunt fight");
					if (this.waitToSend("Hunt")) {
						if (this.network.getInfo().isJoinedFight()) {
							toSend = new Object[] { "True" };
						}
						else {
							toSend = new Object[] { "False" };
						}
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getCluesLeft":
				if (this.network.getInfo().isInHunt()) {
					toSend = new Object[] {(this.getNetwork().getHunt().getNumberOfIndex() - this.getNetwork().getHunt().getCurrentIndex())};
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getStepsLeft":
				if (this.network.getInfo().isInHunt()) {
					toSend = new Object[] {(this.getNetwork().getHunt().getNumberOfSteps() - this.getNetwork().getHunt().getCurrentStep() - 1)};
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "huntActive":
				if (this.getNetwork().getInfo().isInHunt()) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "openHdv":
				this.network.getInfo().setInExchange(true);
				int idSeller = (int) this.getNetwork().getNpc().getSeller();
				if(idSeller != -1){
					NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(idSeller, 5, this.getNetwork().getMap().getId());
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request seller");
					if (this.waitToSend("exchangeBigSeller")) {
						toSend = new Object[] { this.getNetwork().getNpc().getToSell() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getHdvItemStats":
				if(Integer.parseInt(param) > 0  && this.network.getInfo().isInExchange()){
					ExchangeBidHousePriceMessage exchangeBidHousePriceMessage = new ExchangeBidHousePriceMessage(Integer.parseInt(param));
					getNetwork().sendToServer(exchangeBidHousePriceMessage, ExchangeBidHousePriceMessage.ProtocolId, "Request price item");
					if (this.waitToSend("exchangeBigSeller")) {
						toSend = new Object[] { this.getNetwork().getNpc().getMinimalPrices() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "sellItem":
				if(this.network.getInfo().isInExchange()){
					String[] paramItems = param.split(",");
					for(int i = 0 ; i < Integer.parseInt(paramItems[2]) ; i++){
						Thread.sleep(80);
						ExchangeObjectMovePricedMessage exchangeObjectMovePricedMessage = new ExchangeObjectMovePricedMessage(Integer.parseInt(paramItems[3]));
						exchangeObjectMovePricedMessage.setObjectUID(Integer.parseInt(paramItems[0]));
						exchangeObjectMovePricedMessage.setQuantity(Integer.parseInt(paramItems[1]));
						getNetwork().sendToServer(exchangeObjectMovePricedMessage, ExchangeObjectMovePricedMessage.ProtocolId, "Sell item");
						if(!(i == Integer.parseInt(paramItems[2]) - 1)){
							this.waitToSend("exchangeBigSeller");
						}
					}
					if (this.waitToSend("exchangeBigSeller")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "modifyPrice":
				if(this.network.getInfo().isInExchange()){
					String[] paramItems1 = param.split(",");
					List<Integer> uid = this.getNetwork().getNpc().getUidFromSeller(Integer.parseInt(paramItems1[0]), Integer.parseInt(paramItems1[1]));
					for(int i = 0 ; i < uid.size() ; i++){
						if(!this.getNetwork().getNpc().isSelling(uid.get(i))){
							continue;
						}
						Thread.sleep(80);
						ExchangeObjectModifyPricedMessage exchangeObjectMovePricedMessage = new ExchangeObjectModifyPricedMessage();
						exchangeObjectMovePricedMessage.setObjectUID(uid.get(i));
						exchangeObjectMovePricedMessage.setQuantity(Integer.parseInt(paramItems1[1]));
						exchangeObjectMovePricedMessage.setPrice(Integer.parseInt(paramItems1[2]));
						getNetwork().sendToServer(exchangeObjectMovePricedMessage, ExchangeObjectModifyPricedMessage.ProtocolId, "Modify item");
						if(!(i == uid.size() - 1)){
							this.waitToSend("exchangeBigSeller");
						}
					}
					if (this.waitToSend("exchangeBigSeller")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "withdrawItem":
				if(this.network.getInfo().isInExchange()){
					String[] paramItems11 = param.split(",");
					List<Integer> uid1 = this.getNetwork().getNpc().getUidFromSeller(Integer.parseInt(paramItems11[0]), Integer.parseInt(paramItems11[1]));
		
					if (Integer.parseInt(paramItems11[2]) <= uid1.size()) {
						for (int i = 0; i < Integer.parseInt(paramItems11[2]); i++) {
							if(!this.getNetwork().getNpc().isSelling(uid1.get(i))){
								continue;
							}
							Thread.sleep(80);
							ExchangeObjectMoveMessage exchangeObjectMoveMessage = new ExchangeObjectMoveMessage(uid1.get(i), -Integer.parseInt(paramItems11[1]));
							getNetwork().sendToServer(exchangeObjectMoveMessage, ExchangeObjectMoveMessage.ProtocolId, "Withdraw item");
							if (!(i == uid1.size() - 1)) {
								this.waitToSend("exchangeBigSeller");
							}
						}
						if (this.waitToSend("exchangeBigSeller")) {
							toSend = new Object[] { "True" };
						}
						else {
							toSend = new Object[] { "False" };
						} 
					} else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "closeHdv":
				if(this.network.getInfo().isInExchange()){
					LeaveDialogRequestMessage leaveDialogRequestMessage = new LeaveDialogRequestMessage();
					getNetwork().sendToServer(leaveDialogRequestMessage, LeaveDialogRequestMessage.ProtocolId, "Leave hdv");
					if (this.waitToSend("Exchange")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					} 
				} else {
					toSend = new Object[] { "False" };
				}
				this.network.getInfo().setInExchange(false);
				break;
			case "enterBwork":
				if(this.network.getMap().getId() == 88212751 && this.network.getInfo().getCellId() == 383){
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(473020,35416892);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Enter bwork");
					if (this.waitToSendMap(this.network.getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "exitBwork":
				if(this.network.getMap().getId() == 104073218 && this.network.getInfo().getCellId() == 260){
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(477555,35423772);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Exit bwork");
					if (this.waitToSendMap(this.network.getMap().getId())) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getDDPenDoor":
				if (!(this.network.getInteractive().getInteractive(175) == null)) {
					toSend = new Object[] { this.network.getInteractive().getInteractive(175)[0] };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "openDD":
				int[] interactive1 = this.network.getInteractive().getInteractive(175);
				if (!(interactive1 == null)) {
					InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive1[1], interactive1[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Open DD");
					if (this.waitToSend("inStable")) {
						toSend = new Object[] { this.getNetwork().getDragodinde() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "closeDD":
				if(this.network.getDragodinde().isInStable()){
					LeaveDialogRequestMessage leaveDialogRequestMessage = new LeaveDialogRequestMessage();
					getNetwork().sendToServer(leaveDialogRequestMessage, LeaveDialogRequestMessage.ProtocolId, "Leave stable");
					if (this.waitToSend("Exchange")) {
						this.network.getDragodinde().setInStable(false);
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					} 
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "putInStable":
				toSend = manageDD("stable",param);
				break;
			case "putInPaddock":
				toSend = manageDD("paddock",param);
				break;
			case "putInInventory":
				toSend = manageDD("inventory",param);
				break;
			case "equipDD":
				toSend = manageDD("equip",param);
				break;
			case "fart":
				if(this.network.getDragodinde().isFart()){
					EmotePlayRequestMessage emotePlayRequestMessage = new EmotePlayRequestMessage(8);
					getNetwork().sendToServer(emotePlayRequestMessage, EmotePlayRequestMessage.ProtocolId, "Fart");
					if (this.waitToSendEmote()) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					} 
				} else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getDDStat":
				if (this.network.getDragodinde().isHavingDd()) {
					toSend = new Object[] { this.network.getDragodinde().getLevelEquipedDD(), this.network.getDragodinde().getEnergy() };
				}
				else {
					toSend = new Object[] { "False" };
				} 
				break;
			case "setXpDD":
				if (this.network.getDragodinde().isHavingDd()) {
					if(Integer.parseInt(param) != this.network.getDragodinde().getRatioXp()){
						MountSetXpRatioRequestMessage mountSetXpRatioRequestMessage = new MountSetXpRatioRequestMessage(Integer.parseInt(param));
						getNetwork().sendToServer(mountSetXpRatioRequestMessage, MountSetXpRatioRequestMessage.ProtocolId, "Set xp dd");
						if (this.waitToSendMount("xp")) {
							toSend = new Object[] { "True" };
						} else {
							toSend = new Object[] { "False" };
						}
					} else {
						toSend = new Object[] { "True" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				} 
				break;
			case "feedDD": 
				if (this.network.getDragodinde().isHavingDd()) {
					String[] param2 = param.split(",");
					MountFeedRequestMessage mountFeedRequestMessage = new MountFeedRequestMessage(this.network.getDragodinde().getId(),1,Integer.parseInt(param2[0]),Integer.parseInt(param2[1]));
					getNetwork().sendToServer(mountFeedRequestMessage, MountFeedRequestMessage.ProtocolId, "Feed dd");
					if (this.waitToSendMount("set")) {
						toSend = new Object[] { "True" };
					} else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				} 
				break;
			case "mountDD":
				if (this.network.getDragodinde().isHavingDd()) {
					if(this.network.getInfo().isRiding()){
						toSend = new Object[] { "True" };
					} else {
						MountToggleRidingRequestMessage mountFeedRequestMessage = new MountToggleRidingRequestMessage();
						getNetwork().sendToServer(mountFeedRequestMessage, MountToggleRidingRequestMessage.ProtocolId, "Mount dd");
						if (this.waitToSendMount("ride")) {
							toSend = new Object[] { "True" };
						} else {
							toSend = new Object[] { "False" };
						}
					}
				}
				else {
					toSend = new Object[] { "False" };
				} 
				break;
			case "dismountDD":
				if (this.network.getDragodinde().isHavingDd()) {
					if(!this.network.getInfo().isRiding()){
						toSend = new Object[] { "True" };
					} else {
						MountToggleRidingRequestMessage mountFeedRequestMessage = new MountToggleRidingRequestMessage();
						getNetwork().sendToServer(mountFeedRequestMessage, MountToggleRidingRequestMessage.ProtocolId, "dismount dd");
						if (this.waitToSendMount("ride")) {
							toSend = new Object[] { "True" };
						} else {
							toSend = new Object[] { "False" };
						}
					}
				}
				else {
					toSend = new Object[] { "False" };
				} 
				break;
		}
		return toSend;
	}

	private Object[] manageDD(String to, String param) throws Exception, InterruptedException {
		Object[] toSend;
		if(this.network.getDragodinde().isInStable()){
			String paramdd = param.replaceAll("\\(", "");
			paramdd = paramdd.replaceAll("\\)", "");
			String[] newParamdd = paramdd.split(",");
			int actionId = Dragodinde.getActionId(to, newParamdd[1]);
			List<Integer> listId = new ArrayList<>();
			listId.add(Integer.parseInt(newParamdd[0]));
			ExchangeHandleMountsStableMessage exchangeHandleMountsStableMessage = new ExchangeHandleMountsStableMessage(actionId,listId);
			getNetwork().sendToServer(exchangeHandleMountsStableMessage, ExchangeHandleMountsStableMessage.ProtocolId, "Put in " + to);
			if (this.waitToSend("ExchangeDD")) {
				toSend = new Object[] { "True" };
			} else {
				toSend = new Object[] { "False" };
			} 
		} else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	public void getReturn(String[] message) throws NumberFormatException, Exception {
		Thread.sleep(100);
		new Thread(new Runnable() {
			public void run() {
				try {
					Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], getReturn(message[4], message[5]));
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return;
	}

	public boolean waitToSend() throws InterruptedException {
		while (!this.network.getInfo().isNewMap() && !this.network.getInfo().isStorage() && !this.network.getInfo().isStorageUpdate() && !this.network.getInfo().isLeaveExchange() && !this.network.getInfo().isJoinedFight() && !this.network.getInfo().isInteractiveUsed() && !this.network.getInfo().isHuntAnswered()) {
			Thread.sleep(50);
		}
		while (!this.network.getInfo().isBasicNoOperationMsg()) {
			Thread.sleep(50);
		}
		if (this.network.getInfo().isBasicNoOperationMsg() && !this.network.getInfo().isNewMap() && !this.network.getInfo().isStorage() && !this.network.getInfo().isStorageUpdate() && !this.network.getInfo().isLeaveExchange() && !this.network.getInfo().isJoinedFight() && !this.network.getInfo().isInteractiveUsed() && !this.network.getInfo().isHuntAnswered()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean waitToSendMap(int actualMapId) throws InterruptedException{
		long index =  System.currentTimeMillis();
		while (!this.network.getInfo().isNewMap()) {
			Thread.sleep(50);
			if(System.currentTimeMillis() - index > 10000){
				if(this.network.getMap().getId() != actualMapId){
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean waitToSendEmote() throws InterruptedException{
		long index =  System.currentTimeMillis();
		while (!this.network.getInfo().isEmoteLaunched()) {
			Thread.sleep(50);
			if(System.currentTimeMillis() - index > 2000){
				return false; 
			}
		}
		return true;
	}
	
	public boolean waitToSendMount(String s) throws InterruptedException{
		long index =  System.currentTimeMillis();
		switch (s) {
			case "xp":
				while (!this.network.getInfo().isMountxpmsg()) {
					Thread.sleep(50);
					if(System.currentTimeMillis() - index > 2000){
						return false; 
					}
				}
				break;
			case "set":
				while (!this.network.getInfo().isMountSet()) {
					Thread.sleep(50);
					if(System.currentTimeMillis() - index > 2000){
						return false; 
					}
				}
				break;
			case "ride":
				while (!this.network.getInfo().isMountRiding()) {
					Thread.sleep(50);
					if(System.currentTimeMillis() - index > 2000){
						return false; 
					}
				}
				break;
		}
		return true;
	}

	public boolean waitToSend(String s) throws InterruptedException {
		switch (s) {
			case "Map":
				while (!this.network.getInfo().isNewMap()) {
					Thread.sleep(50);
				}
				break;
			case "Storage":
				while (!this.network.getInfo().isStorage()) {
					Thread.sleep(50);
				}
				break;
			case "StorageUpd":
				while (!this.network.getInfo().isStorageUpdate()) {
					Thread.sleep(50);
				}
				break;
			case "Exchange":
				while (!this.network.getInfo().isLeaveExchange()) {
					Thread.sleep(50);
				}
				break;
			case "Fight":
				while (!this.network.getInfo().isJoinedFight()) {
					Thread.sleep(50);
				}
				break;
			case "Interactive":
				while (!this.network.getInfo().isInteractiveUsed() && !this.network.getInfo().isBasicNoOperationMsg()) {
					Thread.sleep(50);
				}
				break;
			case "Hunt":
				while (!this.network.getInfo().isHuntAnswered() && !this.network.getInfo().isBasicNoOperationMsg()) {
					Thread.sleep(50);
				}
				break;
			case "exchangeBigSeller":
				while (!this.network.getInfo().isExchangeBidSeller()) {
					Thread.sleep(50);
				}
				break;
			case "inStable":
				while (!this.network.getDragodinde().isInStable()) {
					Thread.sleep(50);
				}
				break;
			case "ExchangeDD":
				while (!this.network.getInfo().isExchangeDD() && !this.network.getInfo().isBasicNoOperationMsg()) {
					Thread.sleep(50);
				}
				break;
			case "MountXp":
				while (!this.network.getInfo().isExchangeDD()) {
					Thread.sleep(50);
				}
				break;
		}
		
		Thread.sleep(250);

		while (!this.network.getInfo().isBasicNoOperationMsg()) {
			Thread.sleep(50);
		}

		if (this.network.getInfo().isBasicNoOperationMsg() && !this.network.getInfo().isNewMap() && !this.network.getInfo().isStorage() && !this.network.getInfo().isStorageUpdate() && !this.network.getInfo().isLeaveExchange() && !this.network.getInfo().isJoinedFight() && !this.network.getInfo().isInteractiveUsed() && !this.network.getInfo().isHuntAnswered() && !this.network.getInfo().isExchangeBidSeller() && !this.network.getInfo().isExchangeDD() && !this.network.getDragodinde().isInStable()) {
			return false;
		}
		else {
			return true;
		}
	}

	private Object[] move(int param) {
		CellMovement mov;
		Object[] toSend = null;
		try {
			mov = this.network.getMovement().MoveToCell(param);
			if (mov == null || mov.path == null) {
				toSend = new Object[] { "False" };
			}
			else if (this.network.getInfo().getCellId() == param) {
				toSend = new Object[] { "True" };
			}
			else {
				int mapId = this.network.getMap().getId();
				mov.performMovement();
				if (this.network.getMovement().moveOver()) {
					if (this.network.getInfo().getCellId() == param) {
						if ((mapId == 83887104 && this.network.getInfo().getCellId() == 396) || (mapId == 2884617 && this.network.getInfo().getCellId() == 424) || (mapId == 8912911 && this.network.getInfo().getCellId() == 424) || (mapId == 128451073 && this.network.getInfo().getCellId() == 292) || (mapId == 128452097 && this.network.getInfo().getCellId() == 504)) {
							while (!this.network.getInfo().isNewMap()) {
								Thread.sleep(50);
							}
							toSend = new Object[] { "True" };
						}
						else {
							toSend = new Object[] { "True" };
						}
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return toSend;
	}

	private void sleepShort() {
		try {
			Random r = new Random();
			Thread.sleep(500 + r.nextInt(700));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void sleepLong() {
		try {
			Thread.sleep(1500 + new Random().nextInt(1000));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
}
