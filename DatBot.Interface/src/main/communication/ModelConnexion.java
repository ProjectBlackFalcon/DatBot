package main.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Info;
import game.map.MapMovement;
import game.movement.CellMovement;
import game.movement.Movement;
import game.plugin.Bank;
import game.plugin.Hunt;
import game.plugin.Interactive;
import game.plugin.Monsters;
import game.plugin.Npc;
import game.plugin.Stats;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayAttackMonsterRequestMessage;
import protocol.network.messages.game.context.roleplay.havenbag.EnterHavenBagRequestMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntFlagRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntGiveUpRequestMessage;
import protocol.network.messages.game.dialog.LeaveDialogRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.interactive.zaap.TeleportRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;
import utils.d2p.map.Map;

public class ModelConnexion {

	private String msg;

	private InteractiveUseRequestMessage interactiveUseRequestMessage;
	private NpcGenericActionRequestMessage npcGenericactionRequestMessage;
	private boolean bankOppened;
	private Network network;
	private Interactive interactive;
	private Bank bank;
	private Movement movement;
	private Info info;
	private Stats stats;
	private Monsters monsters;
	private Map map;
	private Npc npc;

	public ModelConnexion(Network network) throws InterruptedException {
		this.network = network;
	}

	public Object[] getReturn(String cmd, String param) throws NumberFormatException, Exception {
		this.interactive = network.getInteractive();
		this.bank = network.getBank();
		this.movement = network.getMovement();
		this.stats = network.getStats();
		this.info = network.getInfo();
		this.monsters = network.getMonsters();
		this.map = network.getMap();
		this.npc = network.getNpc();

		Object[] toSend = null;

		switch (cmd) {
			case "getMap":
				toSend = new Object[] { String.valueOf("(" + this.info.getCoords()[0]) + "," + String.valueOf(this.info.getCoords()[1]) + ")", this.info.getCellId(), this.info.getWorldmap(), Integer.valueOf((int) this.info.getMapId()) };
				break;
			case "move":
				toSend = move(Integer.parseInt(param));
				break;
			case "changeMap":
				String[] infoMov = param.split(",");
				MapMovement mapMovement = movement.ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1]);
				if (mapMovement == null) {
					toSend = new Object[] { "False" };
					this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else {
					mapMovement.PerformChangement();
					if (this.getMovement().moveOver()) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				break;
			case "changeMapTest":
				MapMovement mapMovement1 = movement.ChangeMap(param);
				if (mapMovement1 == null) {
					toSend = new Object[] { "False" };
					this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else {
					mapMovement1.PerformChangement();
					if (this.getMovement().moveOver()) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				break;
			case "getResources":
				toSend = new Object[] { interactive.getFarmCell() };
				break;
			case "harvest":
				if (interactive.harvestCell(Integer.parseInt(param))) {
					toSend = new Object[] { interactive.getLastItemHarvestedId(), interactive.getQuantityLastItemHarvested(), this.info.getWeight(), this.info.getWeigthMax() };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getStats":
				toSend = new Object[] { this.stats };
				break;
			case "goAstrub":
				if (this.map.getId() == 153880835) {
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20001, 3, 153880835);
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request Npc to go to Astrub");
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
			case "goIncarnam":
				int r = interactive.getStatue();
				if (r != -1) {
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive.getElementIdStatue(), interactive.getSkillInstanceUidStatue());
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
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
			case "getStatue":
				int statueCellId = interactive.getStatue();
				if (statueCellId != -1) {
					toSend = new Object[] { statueCellId };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getBankDoor":
				if (this.map.getId() == 144931) {
					toSend = new Object[] { bank.cellIdBrakmarIN, bank.cellIdBrakmarOUT };
				}
				else if (this.map.getId() == 84674566) {
					toSend = new Object[] { bank.cellIdAstrubIN, bank.cellIdAstrubOUT };
				}
				else if (this.map.getId() == 147254) {
					toSend = new Object[] { bank.cellIdBontaIN, bank.cellIdBontaOUT };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "goBank":
				if (this.map.getId() == 144931) { // Brakmar
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBrakmarIN, getInteractive().getSkill(bank.interactiveBrakmarIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					if (this.waitToSend("Map")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else if (this.map.getId() == 84674566) { // Astrub
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveAstrubIN, getInteractive().getSkill(bank.interactiveAstrubIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					if (this.waitToSend("Map")) {
						toSend = new Object[] { "True" };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else if (this.map.getId() == 147254) { // Bonta
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBontaIN, getInteractive().getSkill(bank.interactiveBontaIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
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
				if (this.map.getId() == 83887104 || this.map.getId() == 2884617 || this.map.getId() == 8912911) {
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) this.npc.getNpc().get(0).getContextualId(), 3, this.map.getId());
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
					if (this.waitToSend()) {
						toSend = new Object[] { bank };
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
					getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close bank");
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0]), Integer.parseInt(toBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0]), -Integer.parseInt(fromBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(exchangeObjectTransfertListFromInvMessage, ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list in bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(exchangeObjectTransfertListToInvMessage, ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId, "Drop all items in bank");
					if (this.waitToSend()) {
						toSend = new Object[] { this.stats, bank };
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
				toSend = new Object[] { this.getMonsters() };
				break;
			case "attackMonster":
				int id = (int) Double.parseDouble(param);
				boolean canAtk = false;
				for (int i = 0; i < this.getMonsters().getMonsters().size(); i++) {
					if (id == (int) this.getMonsters().getMonsters().get(i).getContextualId()) {
						if (this.info.getCellId() == this.getMonsters().getMonsters().get(i).getDisposition().getCellId()) {
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
					toSend = new Object[] { this.getMonsters() };
				}
				break;
			case "enterBag":
				EnterHavenBagRequestMessage enterHavenBagRequestMessage = new EnterHavenBagRequestMessage(this.info.getActorId());
				getNetwork().sendToServer(enterHavenBagRequestMessage, EnterHavenBagRequestMessage.ProtocolId, "Entering havenBag");
				if (this.waitToSend()) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "exitBag":
				EnterHavenBagRequestMessage enterHavenBagRequestMessage2 = new EnterHavenBagRequestMessage(this.info.getActorId());
				getNetwork().sendToServer(enterHavenBagRequestMessage2, EnterHavenBagRequestMessage.ProtocolId, "Exiting havenBag");
				if (this.waitToSend()) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getZaap":
				if (!(this.interactive.getInteractive(114) == null)) {
					toSend = new Object[] { this.interactive.getInteractive(114)[0] };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "useZaap":
				String newParam = param.replaceAll("\\(", "");
				newParam = newParam.replaceAll("\\)", "");
				String[] paramZaap = newParam.split(",");
				int[] interactive = this.interactive.getInteractive(114);
				if (!(interactive == null)) {
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive[1], interactive[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using zaap");
					if (this.waitToSend()) {
						Thread.sleep(1500 + new Random().nextInt(800));
						double mapId = this.interactive.getMapIdZaap(Integer.parseInt(paramZaap[0]), Integer.parseInt(paramZaap[1]));
						if (mapId != -1) {
							TeleportRequestMessage teleportRequestMessage = new TeleportRequestMessage(0, mapId);
							getNetwork().sendToServer(teleportRequestMessage, TeleportRequestMessage.ProtocolId, "Teleport to " + param);
							if (this.waitToSend()) {
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
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "getHuntingHallDoorCell":
				if (this.map.getId() == 142088718) {
					toSend = new Object[] { this.interactive.getInteractive(184)[0] };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Go into the first hall Then move to cell 292 to go in main hall
			 */
			case "goHuntingHall":
				if (this.map.getId() == 142088718 && this.info.getCellId() == 356) {
					int[] interactive2 = this.interactive.getInteractive(184);
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Entering hunting hall");
					if (this.waitToSend("Map")) {
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
					int[] interactive2 = this.interactive.getInteractive(184);
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Exiting hunting hall");
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
			/**
			 * Bot needs to go to cell 304 to get new hunt It will get the
			 * highest hunt for its level
			 * Return (the number of steps, number of index in first step)
			 *
			 */
			case "newHunt":
				if (this.map.getId() == 128452097 && !this.network.getInfo().isInHunt()) {
					if (!(this.info.getCellId() == 304)) {
						move(304);
					}
					int[] interactiveHunt = Hunt.getHuntFromLvl(this.info.getLvl());
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveHunt[0], interactiveHunt[1]);
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Getting new hunt");
					if (this.waitToSend("Interactive")) {
						toSend = new Object[] { this.getNetwork().getHunt().getNumberOfSteps() , this.getNetwork().getHunt().getNumberOfIndex()};
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "abandonHunt":
				TreasureHuntGiveUpRequestMessage huntGiveUpRequestMessage = new TreasureHuntGiveUpRequestMessage(0);
				getNetwork().sendToServer(huntGiveUpRequestMessage, TreasureHuntGiveUpRequestMessage.ProtocolId, "Abandon hunt");
				if (this.waitToSend("Hunt")) {
					toSend = new Object[] { "True" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			/**
			 * Get the start map of the hunt
			 */
			case "getHuntStart":
				if (this.info.isInHunt()) {
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
				if (this.info.isInHunt() && !(this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					toSend = new Object[] { this.network.getHunt().getCurrentClue() + "," + Hunt.getDirection(this.network.getHunt().getCurrentDir()) };
				} else if(this.info.isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)){
					toSend = new Object[] { "Fight" };
				}
				else {
					toSend = new Object[] { "False" };
				}
				break;
			case "validateClue":
				if (this.info.isInHunt() && !(this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
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
					toSend = new Object[] { "True" };
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
				if (this.info.isInHunt() && (this.getNetwork().getHunt().getCurrentIndex() == this.getNetwork().getHunt().getNumberOfIndex() - 1) && !(this.getNetwork().getHunt().getCurrentIndex() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
					getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Validating step");
					if (this.waitToSend("Hunt")) {
						if (this.info.isStepSuccess()) {
							toSend = new Object[] { this.getNetwork().getHunt().getNumberOfIndex() };
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
				if (this.info.isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps()-1)) {
					TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
					getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Starting hunt fight");
					if (this.waitToSend("Hunt")) {
						if (this.info.isJoinedFight()) {
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
		while (!info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight() && !info.isInteractiveUsed() && !info.isHuntAnswered()) {
			Thread.sleep(50);
		}
		while (!info.isBasicNoOperationMsg()) {
			Thread.sleep(50);
		}
		if (info.isBasicNoOperationMsg() && !info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight() && !info.isInteractiveUsed() && !info.isHuntAnswered()) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean waitToSend(String s) throws InterruptedException {
		switch (s) {
			case "Map":
				while (!info.isNewMap()) {
					Thread.sleep(50);
				}
				break;
			case "Storage":
				while (!info.isStorage()) {
					Thread.sleep(50);
				}
				break;
			case "StorageUpd":
				while (!info.isStorageUpdate()) {
					Thread.sleep(50);
				}
				break;
			case "Exchange":
				while (!info.isLeaveExchange()) {
					Thread.sleep(50);
				}
				break;
			case "Fight":
				while (!info.isJoinedFight()) {
					Thread.sleep(50);
				}
				break;
			case "Interactive":
				while (!info.isInteractiveUsed() && !info.isBasicNoOperationMsg()) {
					Thread.sleep(50);
				}
				break;
			case "Hunt":
				while (!info.isHuntAnswered() && !info.isBasicNoOperationMsg()) {
					Thread.sleep(50);
				}
				break;
		}

		while (!info.isBasicNoOperationMsg()) {
			Thread.sleep(50);
		}

		if (info.isBasicNoOperationMsg() && !info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight() && !info.isInteractiveUsed() && !info.isHuntAnswered()) {
			return false;
		}
		else {
			return true;
		}
	}

	private Object[] move(int param) {
		this.info = network.getInfo();
		this.map = network.getMap();
		CellMovement mov;
		Object[] toSend = null;
		try {
			mov = getMovement().MoveToCell(param);
			if (mov == null || mov.path == null) {
				toSend = new Object[] { "False" };
			}
			else if (this.info.getCellId() == param) {
				toSend = new Object[] { "True" };
			}
			else {
				int mapId = this.map.getId();
				mov.performMovement();
				if (this.getMovement().moveOver()) {
					if (this.info.getCellId() == param) {
						if ((mapId == 83887104 && this.info.getCellId() == 396) || (mapId == 2884617 && this.info.getCellId() == 424) || (mapId == 8912911 && this.info.getCellId() == 424) || (mapId == 128451073 && this.info.getCellId() == 292) || (mapId == 128452097 && this.info.getCellId() == 504)) {
							while (!this.info.isNewMap()) {
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

	private void sleepLong() {
		try {
			Random r = new Random();
			Thread.sleep(1500 + r.nextInt(1000));
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

	public Interactive getInteractive() {
		return interactive;
	}

	public void setInteractive(Interactive interactive) {
		this.interactive = interactive;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Monsters getMonsters() {
		return monsters;
	}

	public void setMonsters(Monsters monsters) {
		this.monsters = monsters;
	}
}
