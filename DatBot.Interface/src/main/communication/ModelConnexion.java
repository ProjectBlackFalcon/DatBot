package main.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Info;
import game.map.MapMovement;
import game.movement.CellMovement;
import game.plugin.Dragodinde;
import game.plugin.Hunt;
import protocol.enums.BoostableCharacteristicEnum;
import protocol.network.Network;
import protocol.network.messages.game.context.mount.MountFeedRequestMessage;
import protocol.network.messages.game.context.mount.MountSetXpRatioRequestMessage;
import protocol.network.messages.game.context.mount.MountToggleRidingRequestMessage;
import protocol.network.messages.game.context.roleplay.emote.EmotePlayRequestMessage;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayAttackMonsterRequestMessage;
import protocol.network.messages.game.context.roleplay.havenbag.EnterHavenBagRequestMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.context.roleplay.stats.StatsUpgradeRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntFlagRequestMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntGiveUpRequestMessage;
import protocol.network.messages.game.dialog.LeaveDialogRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.interactive.zaap.TeleportRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeBidHousePriceMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeBidHouseTypeMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeHandleMountsStableMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectModifyPricedMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMovePricedMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;
import protocol.network.messages.game.inventory.items.ObjectUseMessage;
import utils.GameData;

public class ModelConnexion {

	private boolean bankOpened;
	int botInstance;

	private Network network;

	public ModelConnexion(int botInstance) {
		this.botInstance = botInstance;
	}

	public static boolean isCloseToCell(int cellid, int cellToUse) {
		return (cellToUse - 14) == cellid || (cellToUse - 13) == cellid || (cellToUse + 14) == cellid || (cellToUse + 15) == cellid;
	}

	private Object[] abandonHunt() throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInHunt()) {
			TreasureHuntGiveUpRequestMessage huntGiveUpRequestMessage = new TreasureHuntGiveUpRequestMessage(0);
			getNetwork().sendToServer(huntGiveUpRequestMessage, TreasureHuntGiveUpRequestMessage.ProtocolId, "Abandon hunt");
			if (this.waitToSendAbandonHunt()) {
				toSend = new Object[] { "True" };
			}
			else {
				int timeLeft = this.getNetwork().getHunt().getAbTimeLeft();
				if (timeLeft > 0) {
					toSend = new Object[] { timeLeft };
				}
				else {
					toSend = new Object[] { "False" };
				}
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] attackMonster(String param) throws Exception {
		Object[] toSend;
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
			if (this.waitToSendFight()) {
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("attackMonster error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("attackMonster error", "CellId invalid : " + this.network.getInfo().getCellId() + " Monsters : " + this.network.getMonsters());
			toSend = getMonsters();
		}
		return toSend;
	}
	
	private Object[] assignCaracPoints(String param) throws Exception {
		Object[] toSend;
		String[] infoCaracs = param.split(",");
		int statId = BoostableCharacteristicEnum.valueOf(infoCaracs[0]).getId();
		int boostPoint = Integer.parseInt(infoCaracs[1]);
		StatsUpgradeRequestMessage statsUpgradeRequestMessage = new StatsUpgradeRequestMessage(false, statId, boostPoint);
		getNetwork().sendToServer(statsUpgradeRequestMessage, StatsUpgradeRequestMessage.ProtocolId, String.format("Put %s points in %s",boostPoint,infoCaracs[0]));
		if (this.waitForCaracs()) {
			toSend = new Object[] { "True" };
		} else {
			DisplayInfo.appendDebugLog("assignCaracPoints error, server returned false", param);
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] changeMap(String param) throws Exception {
		Object[] toSend;
		String[] infoMov = param.split(",");
		MapMovement mapMovement = this.network.getMovement().ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1]);
		if (mapMovement == null) {
			toSend = new Object[] { "False" };
			DisplayInfo.appendDebugLog("ChangeMap error, no possible cell to changeMap", param);
			this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
		}
		else {
			mapMovement.PerformChangement();
			if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
				if (Integer.parseInt(infoMov[0]) != this.network.getInfo().getCellId()) {
					stop(0.5);
					toSend = new Object[] { "True" };
				}
				else {
					DisplayInfo.appendDebugLog("ChangeMap error after enterBag tactic", param);
					toSend = new Object[] { "False" };
				}
			}
			else {
				DisplayInfo.appendDebugLog("ChangeMap error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		return toSend;
	}

	private Object[] changeMapTest(String param) throws Exception {
		Object[] toSend;
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
		return toSend;
	}

	private Object[] checkPhorror() {
		Object[] toSend;
		if (this.network.getHunt().isPhorror()) {
			toSend = new Object[] { "\"" + this.getNetwork().getHunt().getPhorrorName() + "\"" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] closeBank() throws Exception {
		Object[] toSend;
		if (bankOpened) {
			getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close bank");
			if (this.waitToSendLeaveExchange()) {
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("Open bank error, server returned false", "Bank oppened : " + bankOpened);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("Open bank error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		bankOpened = false;
		return toSend;
	}

	private Object[] closeDD() throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isInStable()) {
			LeaveDialogRequestMessage leaveDialogRequestMessage = new LeaveDialogRequestMessage();
			getNetwork().sendToServer(leaveDialogRequestMessage, LeaveDialogRequestMessage.ProtocolId, "Leave stable");
			if (this.waitToSendLeaveExchange()) {
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("closeDD error, server returned false", "No response");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] closeHdv() throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInExchange()) {
			LeaveDialogRequestMessage leaveDialogRequestMessage = new LeaveDialogRequestMessage();
			getNetwork().sendToServer(leaveDialogRequestMessage, LeaveDialogRequestMessage.ProtocolId, "Leave hdv");
			if (this.waitToSendLeaveExchange()) {
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("closeHdv error, server returned false", "No response");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		this.network.getInfo().setInExchange(false);
		return toSend;
	}

	private Object[] connect(String param) throws InterruptedException {
		Object[] toSend;
		String[] str = param.split(",");
		Info info = new Info(str[0], str[1], str[2], str[3]);
		if (this.network != null) {
			this.network.getInfo().setPrintDc(false);
			if (this.network.displayPacket) {
				this.network.getF().setVisible(false);
			}
		}
		this.network = new Network(Communication.displayPacket, info, botInstance);
		Thread threadNetwork = new Thread(network);
		threadNetwork.start();
		int index = 0;
		while (!info.isConnected()) {
			index += 1;
			Thread.sleep(1000);
			if (index == 60) {
				System.out.println("Connection timed out");
				DisplayInfo.appendDebugLog("Connection error", param);
				break;
			}
		}
		if (info.isConnected()) {
			stop(2);
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		Communication.isConnecting = false;
		return toSend;
	}

	private Object[] disconnect() throws IOException {
		Object[] toSend;
		if (network != null) {
			this.network.getInfo().setPrintDc(false);
			if (this.network.displayPacket) this.network.getF().setVisible(false);
			this.network.getSocket().close();
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "True" };
		}
		return toSend;
	}

	private Object[] dismountDD() throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isHavingDd()) {
			if (!this.network.getInfo().isRiding()) {
				toSend = new Object[] { "True" };
			}
			else {
				MountToggleRidingRequestMessage mountFeedRequestMessage = new MountToggleRidingRequestMessage();
				getNetwork().sendToServer(mountFeedRequestMessage, MountToggleRidingRequestMessage.ProtocolId, "dismount dd");
				if (this.waitToSendMount("ride")) {
					stop(0.5);
					toSend = new Object[] { "True" };
				}
				else {
					DisplayInfo.appendDebugLog("dismountDD error, server returned false", "No response");
					toSend = new Object[] { "False" };
				}
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] dropBank(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			String[] toBank = param.split(",");
			getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0]), Integer.parseInt(toBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item bank");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("Drop bank error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("Drop bank error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] dropBankAll() throws Exception {
		Object[] toSend;
		if (bankOpened) {
			ExchangeObjectTransfertAllFromInvMessage exchangeObjectTransfertAllFromInvMessage = new ExchangeObjectTransfertAllFromInvMessage();
			getNetwork().sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId, "Drop all items in this.network.getBank()");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("dropBankAll error, server returned false", "Bank oppened : " + bankOpened);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("dropBankAll error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] dropBankKamas(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage1 = new ExchangeObjectMoveKamaMessage(Integer.parseInt(param));
			getNetwork().sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in this.network.getBank()");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("dropBankKamas error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("dropBankKamas error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] dropBankList(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			String[] toBankList = param.split(",");
			List<Integer> ids = new ArrayList<Integer>();
			for (String string : toBankList) {
				ids.add(Integer.parseInt(string.replaceAll("\\s+", "")));
			}
			ExchangeObjectTransfertListFromInvMessage exchangeObjectTransfertListFromInvMessage = new ExchangeObjectTransfertListFromInvMessage(ids);
			getNetwork().sendToServer(exchangeObjectTransfertListFromInvMessage, ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list bank");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("dropBankList error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("dropBankList error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] enterBag() throws Exception {
		Object[] toSend;
		if(this.network.getInfo().isHavenBag())
			return new Object[] { "True" };
		EnterHavenBagRequestMessage enterHavenBagRequestMessage = new EnterHavenBagRequestMessage(this.network.getInfo().getActorId());
		getNetwork().sendToServer(enterHavenBagRequestMessage, EnterHavenBagRequestMessage.ProtocolId, "Entering havenBag");
		if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
			stop(1);
			this.network.getInfo().setHavenBag(true);
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] enterBwork() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 88212751 && this.network.getInfo().getCellId() == 383) {
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(473020, 35416892);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Enter bwork");
			if (this.waitToSendMap(this.network.getMap().getId())) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("enterBwork error, server returned false", "ChangeMap error");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] exitBag() throws Exception {
		Object[] toSend;
		if(!this.network.getInfo().isHavenBag())
			return new Object[] { "True" };
		EnterHavenBagRequestMessage enterHavenBagRequestMessage2 = new EnterHavenBagRequestMessage(this.network.getInfo().getActorId());
		getNetwork().sendToServer(enterHavenBagRequestMessage2, EnterHavenBagRequestMessage.ProtocolId, "Exiting havenBag");
		if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
			stop(1);
			this.network.getInfo().setHavenBag(false);
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] exitBwork() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 104073218 && this.network.getInfo().getCellId() == 260) {
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(477555, 35423772);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Exit bwork");
			if (this.waitToSendMap(this.network.getMap().getId())) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("exitBwork error, server returned false", "ChangeMap error");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] exitHuntingHall() throws Exception {
		Object[] toSend;
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
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("exitHuntingHall error, server returned false", "Map : " + GameData.getCoordMap(this.network.getMap().getId()) + " cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] fart() throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isFart()) {
			EmotePlayRequestMessage emotePlayRequestMessage = new EmotePlayRequestMessage(8);
			getNetwork().sendToServer(emotePlayRequestMessage, EmotePlayRequestMessage.ProtocolId, "Fart");
			if (this.waitToSendEmote()) {
				stop(0.25);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("fart error, server returned false", "No fart");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] feedDD(String param) throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isHavingDd()) {
			String[] param2 = param.split(",");
			MountFeedRequestMessage mountFeedRequestMessage = new MountFeedRequestMessage(this.network.getDragodinde().getId(), 1, Integer.parseInt(param2[0]), Integer.parseInt(param2[1]));
			getNetwork().sendToServer(mountFeedRequestMessage, MountFeedRequestMessage.ProtocolId, "Feed dd");
			if (this.waitToSendMount("set")) {
				stop(0.25);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("feedDD error, server returned false", "No response");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getBank(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			String[] fromBank = param.split(",");
			getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0]), -Integer.parseInt(fromBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Get item bank");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("Get bank error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("Get bank error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getBankDoor() {
		Object[] toSend;
		if (this.network.getMap().getId() == 144931) {
			toSend = new Object[] { this.network.getBank().cellIdBrakmarIN, this.network.getBank().cellIdBrakmarOUT };
		}
		else if (this.network.getMap().getId() == 191104002) {
			toSend = new Object[] { this.network.getBank().cellIdAstrubIN, this.network.getBank().cellIdAstrubOUT };
		}
		else if (this.network.getMap().getId() == 147254) {
			toSend = new Object[] { this.network.getBank().cellIdBontaIN, this.network.getBank().cellIdBontaOUT };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getBankKamas(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage = new ExchangeObjectMoveKamaMessage(-Integer.parseInt(param));
			getNetwork().sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from this.network.getBank()");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("getBankKamas error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("getBankKamas error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getBankList(String param) throws Exception {
		Object[] toSend;
		if (bankOpened) {
			String[] fromBankList = param.split(",");
			List<Integer> ids1 = new ArrayList<Integer>();
			for (String string : fromBankList) {
				ids1.add(Integer.parseInt(string.replaceAll("\\s+", "")));
			}
			ExchangeObjectTransfertListToInvMessage exchangeObjectTransfertListToInvMessage = new ExchangeObjectTransfertListToInvMessage(ids1);
			getNetwork().sendToServer(exchangeObjectTransfertListToInvMessage, ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from this.network.getBank()");
			if (this.waitToSendBank("move")) {
				stop(1);
				toSend = new Object[] { this.network.getStats(), this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("getBankList error, server returned false", "Bank oppened : " + bankOpened + " param : " + param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("getBankList error", "Bank was not open");
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getClue() {
		Object[] toSend;
		if (this.network.getInfo().isInHunt() && this.getNetwork().getHunt().getCurrentStep() != this.getNetwork().getHunt().getNumberOfSteps() - 1) {
			toSend = new Object[] { "\"" + this.network.getHunt().getCurrentClue() + "\", \"" + Hunt.getDirection(this.network.getHunt().getCurrentDir()) + "\"" };
		}
		else if (this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps() - 1)) {
			toSend = new Object[] { "Fight" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getCluesLeft() {
		Object[] toSend;
		if (this.network.getInfo().isInHunt()) {
			toSend = new Object[] { (this.getNetwork().getHunt().getNumberOfIndex() - this.getNetwork().getHunt().getCurrentIndex()) };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getDDPenDoor() {
		Object[] toSend;
		if (this.network.getInteractive().getInteractive(175) != null) {
			toSend = new Object[] { this.network.getInteractive().getInteractive(175)[0] };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getDDStat() {
		Object[] toSend;
		if (this.network.getDragodinde().isHavingDd()) {
			toSend = new Object[] { this.network.getDragodinde().getLevelEquipedDD(), this.network.getDragodinde().getEnergy(), this.network.getDragodinde().getId() };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getHdvItemStats(String param) throws Exception {
		Object[] toSend;
		if (Integer.parseInt(param) > 0 && this.network.getInfo().isInExchange()) {
			ExchangeBidHousePriceMessage exchangeBidHousePriceMessage = new ExchangeBidHousePriceMessage(Integer.parseInt(param));
			getNetwork().sendToServer(exchangeBidHousePriceMessage, ExchangeBidHousePriceMessage.ProtocolId, "Request price item");
			if (this.waitToSendHdv()) {
				stop(0.25);
				toSend = new Object[] { this.getNetwork().getNpc().getMinimalPrices() };
			}
			else {
				DisplayInfo.appendDebugLog("getHdvItemStats error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getHuntingHallDoorCell() {
		Object[] toSend;
		if (this.network.getMap().getId() == 142088718) {
			toSend = new Object[] { this.network.getInteractive().getInteractive(184)[0] };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getHuntStart() {
		Object[] toSend;
		if (this.network.getInfo().isInHunt()) {
			toSend = new Object[] { "(" + this.getNetwork().getHunt().getStartMapCoords()[0] + "," + this.getNetwork().getHunt().getStartMapCoords()[1] + ")" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getMap() {
		if (this.network == null) { return new Object[] { "False" }; }
		if (this.network.getInfo().isConnected()) {
			return new Object[] { String.valueOf("(" + this.network.getInfo().getCoords()[0]) + "," + String.valueOf(this.network.getInfo().getCoords()[1]) + ")", this.network.getInfo().getCellId(), this.network.getInfo().getWorldmap(), Integer.valueOf((int) this.network.getInfo().getMapId()) };
		}
		else {
			return new Object[] { "False" };
		}
	}

	private Object[] getMonsters() {
		Object[] toSend;
		toSend = new Object[] { this.network.getMonsters() };
		return toSend;
	}

	public Network getNetwork() {
		return network;
	}

	private Object[] getRessources() {
		Object[] toSend;
		toSend = new Object[] { this.network.getInteractive().getFarmCell() };
		return toSend;
	}

	public Object[] getReturn(String cmd, String param) throws Exception {

		Object[] toSend = null;

		param = param.substring(1, param.length() - 1);
		param = param.replaceAll("'", "");
		param = param.replaceAll(" ", "");
		param = param.replaceAll("\\(", "");
		param = param.replaceAll("\\)", "");

		switch (cmd) {
			case "connect":
				toSend = connect(param);
				break;
			case "disconnect":
				toSend = disconnect();
				break;
			case "getMap":
				toSend = getMap();
				break;
			case "move":
				toSend = move(Integer.parseInt(param));
				break;
			case "changeMap":
				toSend = changeMap(param);
				break;
			case "changeMapTest":
				toSend = changeMapTest(param);
				break;
			case "getResources":
				toSend = getRessources();
				break;
			case "harvest":
				toSend = harvest(param);
				break;
			case "getStats":
				toSend = getStats();
				break;
			case "goAstrub":
				toSend = goAstrub();
				break;
			case "goIncarnam":
				toSend = goIncarnam();
				break;
			case "enterGate":
				toSend = enterGate();
				break;
			case "getBankDoor":
				toSend = getBankDoor();
				break;
			case "goBank":
				toSend = goBank(param);
				break;
			case "openBank":
				toSend = openBank();
				break;
			case "closeBank":
				toSend = closeBank();
				break;
			case "dropBank":
				toSend = dropBank(param);
				break;
			case "getBank":
				toSend = getBank(param);
				break;
			case "dropBankList":
				toSend = dropBankList(param);
				break;
			case "getBankList":
				toSend = getBankList(param);
				break;
			case "getBankKamas":
				toSend = getBankKamas(param);
				break;
			case "dropBankKamas":
				toSend = dropBankKamas(param);
				break;
			case "dropBankAll":
				toSend = dropBankAll();
				break;
			case "getMonsters":
				toSend = getMonsters();
				break;
			case "attackMonster":
				toSend = attackMonster(param);
				break;
			case "enterBag":
				toSend = enterBag();
				break;
			case "exitBag":
				toSend = exitBag();
				break;
			case "getZaap":
				toSend = getZaap();
				break;
			case "useZaap":
				toSend = useZaap(param);
				break;
			case "getHuntingHallDoorCell":
				toSend = getHuntingHallDoorCell();
				break;
			case "goHuntingHall":
				toSend = goHuntingHall();
				break;
			case "exitHuntingHall":
				toSend = exitHuntingHall();
				break;
			case "newHunt":
				toSend = newHunt(param);
				break;
			case "abandonHunt":
				toSend = abandonHunt();
				break;
			case "getHuntStart":
				toSend = getHuntStart();
				break;
			case "getClue":
				toSend = getClue();
				break;
			case "validateClue":
				toSend = validateClue();
				break;
			case "checkPhorror":
				toSend = checkPhorror();
				break;
			case "validateStep":
				toSend = validateStep();
				break;
			case "huntFight":
				toSend = huntFight();
				break;
			case "getCluesLeft":
				toSend = getCluesLeft();
				break;
			case "getStepsLeft":
				toSend = getStepsLeft();
				break;
			case "huntActive":
				toSend = huntActive();
				break;
			case "openHdv":
				toSend = openHdv();
				break;
			case "getHdvItemStats":
				toSend = getHdvItemStats(param);
				break;
			case "sellItem":
				toSend = sellItem(param);
				break;
			case "modifyPrice":
				toSend = modifyPrice(param);
				break;
			case "withdrawItem":
				toSend = withdrawItem(param);
				break;
			case "closeHdv":
				toSend = closeHdv();
				break;
			case "enterBwork":
				toSend = enterBwork();
				break;
			case "exitBwork":
				toSend = exitBwork();
				break;
			case "getDDPenDoor":
				toSend = getDDPenDoor();
				break;
			case "openDD":
				toSend = openDD();
				break;
			case "closeDD":
				toSend = closeDD();
				break;
			case "putInStable":
				toSend = manageDD("stable", param);
				break;
			case "putInPaddock":
				toSend = manageDD("paddock", param);
				break;
			case "putInInventory":
				toSend = manageDD("inventory", param);
				break;
			case "equipDD":
				toSend = manageDD("equip", param);
				break;
			case "fart":
				toSend = fart();
				break;
			case "getDDStat":
				toSend = getDDStat();
				break;
			case "setXpDD":
				toSend = setXpDD(param);
				break;
			case "feedDD":
				toSend = feedDD(param);
				break;
			case "mountDD":
				toSend = mountDD();
				break;
			case "dismountDD":
				toSend = dismountDD();
				break;
			case "getZaapiCell":
				toSend = getZaapiCell();
				break;
			case "useZaapi":
				toSend = useZaapi(param);
				break;
			case "useItem":
				toSend = useItem(param);
				break;
			case "assignCaracPoints":
				toSend = assignCaracPoints(param);
				break;
		}
		return toSend;
	}

	/**
	 * Returns the cell id of the current map class statue, or False if there is
	 * none
	 * 
	 * @return [cell] or [False]
	 * @throws Exception
	 */
	private Object[] enterGate() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 191106048) {
			if (this.network.getInfo().getCellId() != 397) {
				move(397);
			}
			int[] interactive2 = this.network.getInteractive().getInteractive(184);
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Entering hunting hall");
			if (this.waitToSendMap(this.network.getMap().getId())) {
				stop(1);
				toSend = new Object[] { "true" };
			}
			else {
				DisplayInfo.appendDebugLog("enterGate error, server returned false", "Map : " + GameData.getCoordMap(this.network.getMap().getId()) + " cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	public void getReturn(String[] message) throws InterruptedException {
		Thread.sleep(100);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (message[4].equals("connect")) {
						while (Communication.isConnecting) {
							Thread.sleep(1000);
						}
						Communication.isConnecting = true;
					}
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

	private Object[] getStats() {
		return new Object[] { this.network.getStats() };
	}

	private Object[] getStepsLeft() {
		Object[] toSend;
		if (this.network.getInfo().isInHunt()) {
			toSend = new Object[] { (this.getNetwork().getHunt().getNumberOfSteps() - this.getNetwork().getHunt().getCurrentStep() - 1) };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getZaap() {
		Object[] toSend;
		if (this.network.getInteractive().getInteractive(114) != null) {
			toSend = new Object[] { this.network.getInteractive().getInteractive(114)[0] };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] getZaapiCell() {
		Object[] toSend;
		if (this.network.getInteractive().getInteractive(157) != null) {
			toSend = new Object[] { this.network.getInteractive().getInteractive(157)[0] };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] goAstrub() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 153880835) {
			if (this.network.getInfo().getCellId() != 300) {
				move(300);
			}
			NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20000, 3, 153880835);
			getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request gate to go to Astrub");
			if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
				stop(1);
				return changeMap("511,s");
			}
			else {
				DisplayInfo.appendDebugLog("Astrub change error, server returned false", "MapId : " + this.network.getMap().getId() + " cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("Astrub change error", "Wrong map : " + GameData.getCoordMap(this.network.getMap().getId()));
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] goBank(String param) throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 144931) { // Brakmar
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveBrakmarIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveBrakmarIN, 184));
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
			if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("Bank error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else if (this.network.getMap().getId() == 191104002) { // Astrub
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveAstrubIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveAstrubIN, 184));
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
			if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("Bank error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else if (this.network.getMap().getId() == 147254) { // Bonta
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(this.network.getBank().interactiveBontaIN, this.network.getInteractive().getSkill(this.network.getBank().interactiveBontaIN, 184));
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
			if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("Bank error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			DisplayInfo.appendDebugLog("Bank error", "Wrong map : " + GameData.getCoordMap(this.network.getMap().getId()));
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] goHuntingHall() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 142088718 && this.network.getInfo().getCellId() == 356) {
			int[] interactive2 = this.network.getInteractive().getInteractive(184);
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Entering hunting hall");
			if (this.waitToSendMap(this.network.getMap().getId())) {
				toSend = move(292);
			}
			else {
				DisplayInfo.appendDebugLog("goHuntingHall error, server returned false", "Map : " + GameData.getCoordMap(this.network.getMap().getId()) + " cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] goIncarnam() throws Exception {
		Object[] toSend;
		if(!onMapAndCell(191106048, 397)){
			return new Object[] { "False" };		
		} 
		if(useInteractive(184)[0].equals("False"))
			return new Object[] { "False" };		
		if (this.network.getMap().getId() == 192416776) {
			if (this.network.getInfo().getCellId() != 468) {
				move(468);
			}
			toSend = useInteractive(184);
		}
		else {
			DisplayInfo.appendDebugLog("Astrub change error", "Wrong map : " + GameData.getCoordMap(this.network.getMap().getId()));
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] harvest(String param) throws Exception {
		Object[] toSend;
		int[] harvestCell = this.network.getInteractive().getHarvestCell(Integer.parseInt(param));
		if (harvestCell != null) {
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(harvestCell[0], harvestCell[1]);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Harvesting " + param);
			if (this.waitToSendHarvest(Integer.parseInt(param))) {
				stop(0.2);
				toSend = new Object[] { this.network.getInteractive().getLastItemHarvestedId(), this.network.getInteractive().getQuantityLastItemHarvested(), this.network.getInfo().getWeight(), this.network.getInfo().getWeigthMax() };
			}
			else {
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] huntActive() {
		Object[] toSend;
		if (this.getNetwork().getInfo().isInHunt()) {
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] huntFight() throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentStep() == this.getNetwork().getHunt().getNumberOfSteps() - 1)) {
			TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
			getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Starting hunt fight");
			if (this.waitToSendFight()) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("huntFight error, server returned false", "Was not able to launch fight - rdyToFight : " + this.network.getHunt().isRdyToFight());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] manageDD(String to, String param) throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isInStable()) {
			String paramdd = param.replaceAll("\\(", "");
			paramdd = paramdd.replaceAll("\\)", "");
			String[] newParamdd = paramdd.split(",");
			int actionId = Dragodinde.getActionId(to, newParamdd[1]);
			List<Integer> listId = new ArrayList<>();
			listId.add(Integer.parseInt(newParamdd[0]));
			ExchangeHandleMountsStableMessage exchangeHandleMountsStableMessage = new ExchangeHandleMountsStableMessage(actionId, listId);
			getNetwork().sendToServer(exchangeHandleMountsStableMessage, ExchangeHandleMountsStableMessage.ProtocolId, "Put in " + to);
			if (this.waitToSendMount("exchange")) {
				stop(0.5);
				toSend = new Object[] { "True" };
			}
			else {
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] modifyPrice(String param) throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInExchange()) {
			String[] paramItems1 = param.split(",");
			List<Integer> uid = this.getNetwork().getNpc().getUidFromSeller(Integer.parseInt(paramItems1[0]), Integer.parseInt(paramItems1[1]));
			for (int i = 0; i < uid.size(); i++) {
				if (!this.getNetwork().getNpc().isSelling(uid.get(i))) {
					continue;
				}
				stop(2);
				ExchangeObjectModifyPricedMessage exchangeObjectMovePricedMessage = new ExchangeObjectModifyPricedMessage();
				exchangeObjectMovePricedMessage.setObjectUID(uid.get(i));
				exchangeObjectMovePricedMessage.setQuantity(Integer.parseInt(paramItems1[1]));
				exchangeObjectMovePricedMessage.setPrice(Integer.parseInt(paramItems1[2]));
				getNetwork().sendToServer(exchangeObjectMovePricedMessage, ExchangeObjectModifyPricedMessage.ProtocolId, "Modify item");
				if (i != uid.size() - 1 && !this.waitToSendHdv()) {
					DisplayInfo.appendDebugLog("modifyPrice error, server returned false", param);
				}
			}
			if (this.waitToSendHdv()) {
				stop(2);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("modifyPrice error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] mountDD() throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isHavingDd()) {
			if (this.network.getInfo().isRiding()) {
				toSend = new Object[] { "True" };
			}
			else {
				MountToggleRidingRequestMessage mountFeedRequestMessage = new MountToggleRidingRequestMessage();
				getNetwork().sendToServer(mountFeedRequestMessage, MountToggleRidingRequestMessage.ProtocolId, "Mount dd");
				if (this.waitToSendMount("ride")) {
					stop(0.5);
					toSend = new Object[] { "True" };
				}
				else {
					DisplayInfo.appendDebugLog("mountDD error, server returned false", "No response");
					toSend = new Object[] { "False" };
				}
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] move(int param) {
		CellMovement mov;
		Object[] toSend = null;
		try {
			mov = this.network.getMovement().MoveToCell(param);
			if (mov == null || mov.path == null) {
				toSend = new Object[] { "False" };
				DisplayInfo.appendDebugLog("Move error, this cell is not accessible", String.valueOf(param));
			}
			else if (this.network.getInfo().getCellId() == param) {
				toSend = new Object[] { "True" };
			}
			else {
				int mapId = this.network.getMap().getId();
				mov.performMovement();
				if (this.network.getMovement().moveOver()) {
					if (this.network.getInfo().getCellId() == param) {
						if ((mapId == 83887104 && this.network.getInfo().getCellId() == 396) || (mapId == 2884617 && this.network.getInfo().getCellId() == 424) || (mapId == 8912911 && this.network.getInfo().getCellId() == 424) || (mapId == 128451073 && this.network.getInfo().getCellId() == 292)
							|| (mapId == 128452097 && this.network.getInfo().getCellId() == 504)) {
							if (waitToSendMap(mapId)) {
								stop(1);
								toSend = new Object[] { "True" };
							}
							else {
								toSend = new Object[] { "False" };
							}
						}
						else {
							stop(1);
							toSend = new Object[] { "True" };
						}
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					DisplayInfo.appendDebugLog("Move error, server returned false", String.valueOf(param));
					toSend = new Object[] { "False" };
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return toSend;
	}

	private Object[] newHunt(String param) throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 128452097 && !this.network.getInfo().isInHunt()) {
			if (this.network.getInfo().getCellId() != 304) {
				move(304);
			}
			int[] interactiveHunt = Hunt.getHuntFromLvl(Integer.parseInt(param));
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveHunt[0], this.network.getInteractive().getSkill(interactiveHunt[0],248));
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Getting new hunt");
			if (this.waitToSendHunt()) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("newHunt error, server returned false", "Interactive error - cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] openBank() throws Exception {
		Object[] toSend;
		if (this.network.getMap().getId() == 83887104 || this.network.getMap().getId() == 2884617 || this.network.getMap().getId() == 8912911 || this.network.getMap().getId() == 192415750) {
			NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) this.network.getNpc().getNpc().get(0).getContextualId(), 3, this.network.getMap().getId());
			getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
			if (this.waitToSendBank("open")) {
				stop(1);
				toSend = new Object[] { this.network.getBank() };
			}
			else {
				DisplayInfo.appendDebugLog("Open bank error, server returned false", "Wrong map : " + GameData.getCoordMap(this.network.getMap().getId()));
				toSend = new Object[] { "False" };
			}
			bankOpened = true;
		}
		else {
			DisplayInfo.appendDebugLog("Open bank error", "Wrong map : " + GameData.getCoordMap(this.network.getMap().getId()));
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] openDD() throws Exception {
		Object[] toSend;
		int[] interactive1 = this.network.getInteractive().getInteractive(175);
		if (interactive1 != null) {
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive1[1], interactive1[2]);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Open DD");
			if (this.waitToSendMount("exchange")) {
				stop(1);
				toSend = new Object[] { this.getNetwork().getDragodinde() };
			}
			else {
				DisplayInfo.appendDebugLog("closeDD error, server returned false", "Map : " + GameData.getCoordMap(this.network.getMap().getId()) + " cellId : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] openHdv() throws Exception {
		Object[] toSend;
		this.network.getInfo().setInExchange(true);
		int[] interactive1 = this.network.getInteractive().getInteractive(355);
		if (interactive1 != null) {
			InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive1[1], interactive1[2]);
			getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Open hdv");
			if (this.waitToSendHdv()) {
				ExchangeBidHouseTypeMessage bidHouseTypeMessage = new ExchangeBidHouseTypeMessage(1);
				getNetwork().sendToServer(bidHouseTypeMessage, ExchangeBidHouseTypeMessage.ProtocolId, "Set hdv type to 1");
				if (this.waitToSendHdv()) {
					stop(1);
					NpcGenericActionRequestMessage npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-1, 5, this.getNetwork().getMap().getId());
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request seller");
					if (this.waitToSendHdv()) {
						stop(1);
						toSend = new Object[] { this.getNetwork().getNpc().getToSell() };
					}
					else {
						toSend = new Object[] { "False" };
					}
				}
				else {
					DisplayInfo.appendDebugLog("openHdv error, server returned false", "No response from server");
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
		return toSend;
	}

	private Object[] sellItem(String param) throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInExchange()) {
			String[] paramItems = param.split(",");
			for (int i = 0; i < Integer.parseInt(paramItems[2]); i++) {
				stop(0.5);
				ExchangeObjectMovePricedMessage exchangeObjectMovePricedMessage = new ExchangeObjectMovePricedMessage(Integer.parseInt(paramItems[3]));
				exchangeObjectMovePricedMessage.setObjectUID(Integer.parseInt(paramItems[0]));
				exchangeObjectMovePricedMessage.setQuantity(Integer.parseInt(paramItems[1]));
				getNetwork().sendToServer(exchangeObjectMovePricedMessage, ExchangeObjectMovePricedMessage.ProtocolId, "Sell item");
				if (i != Integer.parseInt(paramItems[2]) - 1 && !this.waitToSendHdv()) {
					DisplayInfo.appendDebugLog("sellItem error, server returned false", param);
				}
			}
			if (this.waitToSendHdv()) {
				stop(1);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("sellItem error, server returned false", param);
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	private Object[] setXpDD(String param) throws Exception {
		Object[] toSend;
		if (this.network.getDragodinde().isHavingDd()) {
			if (Integer.parseInt(param) != this.network.getDragodinde().getRatioXp()) {
				MountSetXpRatioRequestMessage mountSetXpRatioRequestMessage = new MountSetXpRatioRequestMessage(Integer.parseInt(param));
				getNetwork().sendToServer(mountSetXpRatioRequestMessage, MountSetXpRatioRequestMessage.ProtocolId, "Set xp dd");
				if (this.waitToSendMount("xp")) {
					stop(0.5);
					toSend = new Object[] { "True" };
				}
				else {
					DisplayInfo.appendDebugLog("setXpDD error, server returned false", "No response");
					toSend = new Object[] { "False" };
				}
			}
			else {
				toSend = new Object[] { "True" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
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

	private void sleepShort() {
		try {
			Random r = new Random();
			Thread.sleep(500 + r.nextInt(700));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Object[] useItem(String param) throws Exception {
		Object[] toSend;
		ObjectUseMessage objectUseMessage = new ObjectUseMessage(Integer.parseInt(param));
		getNetwork().sendToServer(objectUseMessage, ObjectUseMessage.ProtocolId, "Using item");
		if (this.waitToSendObjectUse()) {
			stop(0.25);
			toSend = new Object[] { "True" };
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] useZaap(String param) throws Exception {
		Object[] toSend;
		int[] interactive = this.network.getInteractive().getInteractive(114);
		if (interactive != null) {
			if (isCloseToCell(this.network.getInfo().getCellId(), interactive[0])) {
				String newParam = param.replaceAll("\\(", "");
				newParam = newParam.replaceAll("\\)", "");
				String[] paramZaap = newParam.split(",");
				InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive[1], interactive[2]);
				getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using zaap");
				if (this.waitToInteractive()) {
					stop(1.5);
					double mapId = this.network.getInteractive().getMapIdZaap(Integer.parseInt(paramZaap[0]), Integer.parseInt(paramZaap[1]));
					if (mapId != -1) {
						TeleportRequestMessage teleportRequestMessage = new TeleportRequestMessage(0, mapId);
						getNetwork().sendToServer(teleportRequestMessage, TeleportRequestMessage.ProtocolId, "Teleport to " + param);
						if (this.waitToSendMap(this.network.getMap().getId())) {
							this.network.getInfo().setHavenBag(false);
							stop(1);
							toSend = getMap();
						}
						else {
							DisplayInfo.appendDebugLog("useZaap error, server returned false", "Teleportation problème\n" + param);
							toSend = new Object[] { "False" };
						}
					}
					else {
						DisplayInfo.appendDebugLog("useZaap error", "Map not found in the list");
						getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Leaving zaap list");
						toSend = new Object[] { "False" };
					}
				}
				else {
					DisplayInfo.appendDebugLog("useZaap error, server returned false", "Interactive problème\n" + param);
					toSend = new Object[] { "False" };
				}
			}
			else {
				DisplayInfo.appendDebugLog("useZaap error", "Wrong cell Id : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] useZaapi(String param) throws Exception {
		Object[] toSend;
		int[] interactiveZaapi = this.network.getInteractive().getInteractive(157);
		if (interactiveZaapi != null) {
			if (isCloseToCell(this.network.getInfo().getCellId(), interactiveZaapi[0])) {
				String[] paramZaap = param.split(",");
				InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveZaapi[1], interactiveZaapi[2]);
				getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using zaapi");
				if (this.waitToInteractive()) {
					stop(1.5);
					double mapId = this.network.getInteractive().getMapIdZaapi(Integer.parseInt(paramZaap[0]), Integer.parseInt(paramZaap[1]));
					if (mapId != -1) {
						TeleportRequestMessage teleportRequestMessage = new TeleportRequestMessage(1, mapId);
						getNetwork().sendToServer(teleportRequestMessage, TeleportRequestMessage.ProtocolId, "Teleport to " + param);
						if (this.waitToSendMap(this.network.getMap().getId())) {
							stop(1);
							toSend = getMap();
						}
						else {
							DisplayInfo.appendDebugLog("useZaapi error, server returned false", "Teleportation problème\n" + param);
							toSend = new Object[] { "False" };
						}
					}
					else {
						getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Leaving zaap list");
						DisplayInfo.appendDebugLog("useZaapi error", "Map not found in the list");
						toSend = new Object[] { "False" };
					}
				}
				else {
					DisplayInfo.appendDebugLog("useZaapi error, server returned false", "Interactive probleme\n" + param);
					toSend = new Object[] { "False" };
				}
			}
			else {
				DisplayInfo.appendDebugLog("useZaapi error", "Wrong cell Id : " + this.network.getInfo().getCellId());
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] validateClue() throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInHunt() && this.getNetwork().getHunt().getCurrentStep() != this.getNetwork().getHunt().getNumberOfSteps() - 1) {
			TreasureHuntFlagRequestMessage treasureHuntFlagRequestMessage = new TreasureHuntFlagRequestMessage(0, this.network.getHunt().getCurrentIndex());
			getNetwork().sendToServer(treasureHuntFlagRequestMessage, TreasureHuntFlagRequestMessage.ProtocolId, "Validating clue");
			if (this.waitToSendHunt()) {
				stop(0.25);
				toSend = new Object[] { "True" };
			}
			else {
				DisplayInfo.appendDebugLog("validateClue error, server returned false", "");
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private Object[] validateStep() throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInHunt() && (this.getNetwork().getHunt().getCurrentIndex() == this.getNetwork().getHunt().getNumberOfIndex()) && this.getNetwork().getHunt().getCurrentStep() != this.getNetwork().getHunt().getNumberOfSteps() - 1) {
			TreasureHuntDigRequestMessage treasureHuntdigRequestMessage = new TreasureHuntDigRequestMessage(0);
			getNetwork().sendToServer(treasureHuntdigRequestMessage, TreasureHuntDigRequestMessage.ProtocolId, "Validating step");
			if (this.waitToSendValidateStep()) {
				stop(0.25);
				toSend = new Object[] { "True" };
			}
			else {
				toSend = new Object[] { "False" };
			}
		}
		else {
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	public boolean waitToInteractive() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isInteractiveUsed()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendAbandonHunt() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isTextMessage() && !this.network.getInfo().isHuntAnswered()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) {
				DisplayInfo.appendDebugLog("abandonHunt error, server returned false", "No response error");
				return false;
			}
		}
		return this.network.getInfo().isHuntAnswered();
	}

	public boolean waitToSendHarvest(int cellId) throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isHarvestSuccess()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 10000) {
				DisplayInfo.appendDebugLog("Harvesting error, server returned false", "CellId : " + this.network.getInfo().getCellId() + ", cellIdToHarvest : " + cellId);
				return false;
			}
		}
		return true;
	}

	public boolean waitToSendBank(String s) throws InterruptedException {
		long index = System.currentTimeMillis();
		switch (s) {
			case "open":
				while (!this.network.getInfo().isStorage()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			case "move":
				while (!this.network.getInfo().isStorageUpdate()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			default:
				return false;
		}
		return true;
	}

	public boolean waitToSendEmote() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isEmoteLaunched()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendFight() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isJoinedFight()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}
	
	public boolean waitForCaracs() {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isCaracsAffected()) {
			if (System.currentTimeMillis() - index > 2000) {
				System.out.println("Timed out");
				return false; }
		}
		return true;
	}

	public boolean waitToSendHdv() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isExchangeBidSeller()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendHunt() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isHuntAnswered()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendLeaveExchange() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isLeaveExchange()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendMap(int actualMapId) throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isNewMap()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 10000) {
				if (this.network.getMap().getId() != actualMapId) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return true;
	}

	public boolean waitToSendMount(String s) throws InterruptedException {
		long index = System.currentTimeMillis();
		switch (s) {
			case "xp":
				while (!this.network.getInfo().isMountxpmsg()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			case "set":
				while (!this.network.getInfo().isMountSet()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			case "ride":
				while (!this.network.getInfo().isMountRiding()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			case "exchange":
				while (!this.network.getInfo().isExchangeDD()) {
					Thread.sleep(50);
					if (System.currentTimeMillis() - index > 2000) { return false; }
				}
				break;
			default:
				return false;
		}
		return true;
	}

	public boolean waitToSendObjectUse() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isObjectUse()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) { return false; }
		}
		return true;
	}

	public boolean waitToSendValidateStep() throws InterruptedException {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isStepSuccess() && !this.network.getInfo().isStepFailed()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 2000) {
				DisplayInfo.appendDebugLog("validateStep error, server returned false", "Dig result : " + this.network.getHunt().digResult);
				return false;
			}
		}
		return this.network.getInfo().isStepSuccess();
	}

	private Object[] withdrawItem(String param) throws Exception {
		Object[] toSend;
		if (this.network.getInfo().isInExchange()) {
			String[] paramItems11 = param.split(",");
			List<Integer> uid1 = this.getNetwork().getNpc().getUidFromSeller(Integer.parseInt(paramItems11[0]), Integer.parseInt(paramItems11[1]));

			if (Integer.parseInt(paramItems11[2]) <= uid1.size()) {
				for (int i = 0; i < Integer.parseInt(paramItems11[2]); i++) {
					if (!this.getNetwork().getNpc().isSelling(uid1.get(i))) {
						continue;
					}
					stop(0.5);
					ExchangeObjectMoveMessage exchangeObjectMoveMessage = new ExchangeObjectMoveMessage(uid1.get(i), -Integer.parseInt(paramItems11[1]));
					getNetwork().sendToServer(exchangeObjectMoveMessage, ExchangeObjectMoveMessage.ProtocolId, "Withdraw item");
					if (i != uid1.size() - 1 && !!this.waitToSendHdv()) {
						DisplayInfo.appendDebugLog("withdrawItem error, server returned false", param);
					}
				}
				if (this.waitToSendHdv()) {
					stop(1);
					toSend = new Object[] { "True" };
				}
				else {
					DisplayInfo.appendDebugLog("withdrawItem error, server returned false", param);
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
		return toSend;
	}
	
	public boolean onMapAndCell(int mapId, int cellId){
		if(this.network.getInfo().getMapId() == mapId){
			if(this.network.getInfo().getCellId() == cellId){
				return true;
			} else {
				return move(cellId)[0].equals("True");				
			}
		}
		return false;
	}
	
	private Object[] useInteractive(int id) throws Exception, InterruptedException {
		Object[] toSend;
		int[] interactive2 = this.network.getInteractive().getInteractive(id);
		InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive2[1], interactive2[2]);
		getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "using interactive : " + interactive2[1] + " - " + interactive2[2]);
		if (this.waitToSendMap(this.getNetwork().getMap().getId())) {
			stop(1);
			toSend = new Object[] { "True" };
		}
		else {
			DisplayInfo.appendDebugLog("Astrub change error, server returned false", "MapId : " + this.network.getMap().getId() + " cellId : " + this.network.getInfo().getCellId());
			toSend = new Object[] { "False" };
		}
		return toSend;
	}

	private void stop(double deviation) throws InterruptedException {
		double gauss = new Random().nextGaussian();
		long timeStoped = (long) (Math.abs(gauss * deviation) * 1000);
		System.out.println("---- Sleeping : " + timeStoped + " ----");
		Thread.sleep(timeStoped);
	}
}
