package protocol.network;

import game.Info;
import game.combat.Fight;
import game.movement.Movement;
import game.plugin.*;
import ia.Intelligence;
import ia.IntelligencePacketHandler;
import ia.entities.Spell;
import main.communication.Communication;
import main.communication.DisplayInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import protocol.frames.LatencyFrame;
import protocol.network.messages.connection.*;
import protocol.network.messages.game.actions.GameActionAcknowledgementMessage;
import protocol.network.messages.game.actions.fight.*;
import protocol.network.messages.game.actions.sequence.SequenceEndMessage;
import protocol.network.messages.game.actions.sequence.SequenceStartMessage;
import protocol.network.messages.game.approach.AuthenticationTicketMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsMessage;
import protocol.network.messages.game.basic.SequenceNumberMessage;
import protocol.network.messages.game.basic.TextInformationMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedForceReadyMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedSuccessMessage;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.character.stats.CharacterLevelUpMessage;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.character.stats.FighterStatsListMessage;
import protocol.network.messages.game.context.*;
import protocol.network.messages.game.context.fight.*;
import protocol.network.messages.game.context.fight.arena.ArenaFighterLeaveMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeInfoMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeResultMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeTargetUpdateMessage;
import protocol.network.messages.game.context.fight.challenge.ChallengeTargetsListMessage;
import protocol.network.messages.game.context.fight.character.GameFightRefreshFighterMessage;
import protocol.network.messages.game.context.fight.character.GameFightShowFighterMessage;
import protocol.network.messages.game.context.mount.MountRidingMessage;
import protocol.network.messages.game.context.mount.MountSetMessage;
import protocol.network.messages.game.context.mount.MountXpRatioMessage;
import protocol.network.messages.game.context.roleplay.*;
import protocol.network.messages.game.context.roleplay.emote.EmoteListMessage;
import protocol.network.messages.game.context.roleplay.emote.EmotePlayMessage;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayPlayerFightFriendlyAnswerMessage;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayPlayerFightFriendlyRequestedMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToFightServerMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToGameServerMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceMultiUpdateMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceUpdateMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogQuestionMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestAnswerMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntMessage;
import protocol.network.messages.game.interactive.InteractiveElementUpdatedMessage;
import protocol.network.messages.game.interactive.StatedElementUpdatedMessage;
import protocol.network.messages.game.interactive.zaap.TeleportDestinationsListMessage;
import protocol.network.messages.game.interactive.zaap.ZaapListMessage;
import protocol.network.messages.game.inventory.KamasUpdateMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeBidHouseItemRemoveOkMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeBidPriceForSellerMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeStartOkMountMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeStartedBidSellerMessage;
import protocol.network.messages.game.inventory.items.*;
import protocol.network.messages.game.inventory.spells.SpellListMessage;
import protocol.network.messages.game.inventory.storage.*;
import protocol.network.messages.security.CheckIntegrityMessage;
import protocol.network.messages.security.ClientKeyMessage;
import protocol.network.messages.server.basic.SystemMessageDisplayMessage;
import protocol.network.types.connection.GameServerInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayTreasureHintInformations;
import protocol.network.types.game.context.roleplay.job.JobExperience;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToHint;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToPOI;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.data.items.SpellItem;
import protocol.network.types.version.VersionExtended;
import protocol.network.util.*;
import utils.GameData;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unchecked")
public class Network extends DisplayInfo implements Runnable {

	private Bank bank;
	private byte[] bigPacketData;
	private int bigPacketId;
	// Big packet split
	private int bigPacketLengthToFull;// Length needed to finish the packet
	/*
	 * Plugin variable
	 */
	public boolean connectionToKoli = false;
	private Fight fight;
	private Intelligence intelligence;
	private Hunt hunt;
	private Info info;
	private Interactive interactive;

	public String ip = "213.248.126.39";
	private Map map;
	private Message message;
	private LatencyFrame latencyFrame;
	private Monsters monsters;
	private Movement movement;
	private Npc npc;
	private Dragodinde dragodinde;
	private IntelligencePacketHandler iaPacket;
	public int port = 443;
	private Socket socket;
	boolean packetSent = false;
	long timeout;
	private Stats stats;
	private List<Integer> Ticket;

	public Network(boolean displayPacket, Info info, int botInstance) {
		super(botInstance, displayPacket);
		this.map = new Map();
		this.info = info;
		this.stats = new Stats(this);
		this.fight = new Fight(this);
		this.interactive = new Interactive(this);
		this.bank = new Bank();
		this.movement = new Movement(this);
		this.monsters = new Monsters();
		this.hunt = new Hunt();
		this.dragodinde = new Dragodinde();
		latencyFrame = new LatencyFrame();
		try {
			this.npc = new Npc(this);
			socket = new Socket(this.ip, this.port);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void buildMessage(DofusDataReader reader) throws Exception {
		if (reader.available() <= 0) { return; }

		// Packet split
		if (bigPacketLengthToFull != 0) {
			if (reader.available() <= bigPacketLengthToFull) {
				bigPacketLengthToFull -= reader.available();
				byte[] destination = new byte[bigPacketData.length + reader.available()];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(reader.available()), 0, destination, bigPacketData.length, reader.available());
				this.bigPacketData = destination;
			}
			else if (reader.available() > bigPacketLengthToFull) {
				byte[] destination = new byte[bigPacketData.length + bigPacketLengthToFull];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(bigPacketLengthToFull), 0, destination, bigPacketData.length, bigPacketLengthToFull);
				bigPacketLengthToFull = 0;
				this.bigPacketData = destination;
			}
			if (bigPacketLengthToFull == 0) {
				TreatPacket(bigPacketId, bigPacketData);
				bigPacketData = null;
				bigPacketId = 0;
			}
		}
		else {
			if (this.message == null) {
				this.message = new Message();
			}
			message.build(reader);
			if (message.getId() != 0 && message.bigPacketLength == 0) {
				TreatPacket(message.getId(), message.getData());
			}
			else if (message.getId() != 0 && message.bigPacketLength != 0) {
				bigPacketLengthToFull = message.bigPacketLength;
				bigPacketId = message.getId();
				bigPacketData = message.getData();
			}
		}
		this.message = null;
		buildMessage(reader);
	}

	public String bytesToString(byte[] bytes, String format, boolean spacer) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format(format, b));
			if (spacer) sb.append(" ");
		}
		return sb.toString();
	}

	private byte ComputeTypeLen(int param1) {
		byte num;
		if (param1 > 65535)
			num = 3;
		else if (param1 <= 255)
			num = (byte) (param1 <= 0 ? 0 : 1);
		else
			num = 2;
		return num;
	}

	public Bank getBank() {
		return bank;
	}

	public Fight getFight() {
		return fight;
	}

	public Hunt getHunt() {
		return hunt;
	}

	public Info getInfo() {
		return info;
	}

	public Interactive getInteractive() {
		return interactive;
	}

	public Map getMap() {
		return map;
	}

	public Monsters getMonsters() {
		return monsters;
	}

	public Movement getMovement() {
		return movement;
	}

	public Npc getNpc() {
		return npc;
	}

	public Socket getSocket() {
		return socket;
	}

	public Stats getStats() {
		return stats;
	}

	private void HandleAuthentificationTicketMessage() throws Exception {
		byte[] encryptedTicket = new byte[Ticket.size()];
		for (int i = 0; i < Ticket.size(); i++) {
			encryptedTicket[i] = Ticket.get(i).byteValue();
		}
		Ticket = null;
		String decryptedTicket = Crypto.decryptAESkey(encryptedTicket);
		AuthenticationTicketMessage authenticationTicketMessage = new AuthenticationTicketMessage("fr", decryptedTicket);
		sendToServer(authenticationTicketMessage, AuthenticationTicketMessage.ProtocolId, "Authentification du ticket...");
	}

	private void handleCharacterLevelUpMessage(DofusDataReader dataReader) {
		CharacterLevelUpMessage characterLevelUpMessage = new CharacterLevelUpMessage();
		characterLevelUpMessage.Deserialize(dataReader);
		info.setLvl(characterLevelUpMessage.getNewLevel());
	}

	private void HandleCharacterListRequestMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 150, "Character list request");
	}

	private void handleCharacterSelectionMessage(DofusDataReader dataReader) throws Exception, Error {
		double gauss = new Random().nextGaussian();
		long timeStoped = (long) (Math.abs(gauss * 3) * 1000);
		System.out.println("---- Sleeping : " + timeStoped + " ----");
		Thread.sleep(timeStoped);
		CharactersListMessage charactersListMessage = new CharactersListMessage();
		charactersListMessage.Deserialize(dataReader);
		int j = 0;
		for (int i = 0; i < charactersListMessage.getCharacters().size(); i++) {
			if (charactersListMessage.getCharacters().get(i).getName().equals(info.getName())) {
				CharacterSelectionMessage characterSelectionMessage = new CharacterSelectionMessage(charactersListMessage.getCharacters().get(i).getId());
				sendToServer(characterSelectionMessage, CharacterSelectionMessage.ProtocolId, "Selection du personnage...");
				info.setActorId(charactersListMessage.getCharacters().get(i).getId());
				info.setLvl(charactersListMessage.getCharacters().get(i).getLevel());
				j = 1;
				break;
			}
		}
		if (j == 0) throw new Error("Wrong character name !");
	}

	private void HandleClientKeyMessage(String key) throws Exception {
		ClientKeyMessage clientKeyMessage = new ClientKeyMessage(key);
		sendToServer(clientKeyMessage, ClientKeyMessage.ProtocolId, "Client key");
	}

	private void HandleFriendIgnoreSpouseMessages() throws Exception {
		// Send friend list request
		sendToServer(new NetworkMessageEmpty(), 4001, "Friend list request");
		// Send ignored list request
		sendToServer(new NetworkMessageEmpty(), 5676, "Ignored list request");
		// Send spouse list request
		sendToServer(new NetworkMessageEmpty(), 6355, "Spouse list request");
	}

	private void handleGameActionFightCloseCombatMessage(DofusDataReader dataReader) {
		GameActionFightCloseCombatMessage gameActionFightCloseCombatMessage = new GameActionFightCloseCombatMessage();
		gameActionFightCloseCombatMessage.Deserialize(dataReader);
		iaPacket.gameActionFightCloseCombat(gameActionFightCloseCombatMessage);
	}

	private void handleGameActionFightDeathMessage(DofusDataReader dataReader) {
		GameActionFightDeathMessage gameActionFightDeathMessage = new GameActionFightDeathMessage();
		gameActionFightDeathMessage.Deserialize(dataReader);
		iaPacket.gameActionFightDeath(gameActionFightDeathMessage);
	}

	private void handleGameActionFightDispellableEffectMessage(DofusDataReader dataReader) {
		GameActionFightDispellableEffectMessage gameActionFightDispellableEffectMessage = new GameActionFightDispellableEffectMessage();
		gameActionFightDispellableEffectMessage.Deserialize(dataReader);
		iaPacket.gameActionFightDispellableEffect(gameActionFightDispellableEffectMessage);
	}

	private void handleGameActionFightDodgePointLossMessage(DofusDataReader dataReader) {
		GameActionFightDodgePointLossMessage dodgePointLossMessage = new GameActionFightDodgePointLossMessage();
		dodgePointLossMessage.Deserialize(dataReader);
		iaPacket.gameActionFightDodgePointLoss(dodgePointLossMessage);
	}

	private void handleGameActionFightLifeAndShieldPointsLostMessage(DofusDataReader dataReader) {
		GameActionFightLifeAndShieldPointsLostMessage gameActionFightLifeAndShieldPointsLostMessage = new GameActionFightLifeAndShieldPointsLostMessage();
		gameActionFightLifeAndShieldPointsLostMessage.Deserialize(dataReader);
		iaPacket.gameActionFightLifeAndShieldPointsLost(gameActionFightLifeAndShieldPointsLostMessage);
	}

	private void handleGameActionFightLifePointsGainMessage(DofusDataReader dataReader) {
		GameActionFightLifePointsGainMessage gameActionFightLifePointsGainMessage = new GameActionFightLifePointsGainMessage();
		gameActionFightLifePointsGainMessage.Deserialize(dataReader);
		iaPacket.gameActionFightLifePointsGain(gameActionFightLifePointsGainMessage);
	}

	private void handleGameActionFightLifePointsLostMessage(DofusDataReader dataReader) {
		GameActionFightLifePointsLostMessage gameActionFightLifePointsLostMessage = new GameActionFightLifePointsLostMessage();
		gameActionFightLifePointsLostMessage.Deserialize(dataReader);
		iaPacket.gameActionFightLifePointsLost(gameActionFightLifePointsLostMessage);
	}

	private void handleGameActionFightMarkCellsMessage(DofusDataReader dataReader) {
		GameActionFightMarkCellsMessage gameActionFightMarkCellsMessage = new GameActionFightMarkCellsMessage();
		gameActionFightMarkCellsMessage.Deserialize(dataReader);
		iaPacket.gameActionFightMarkCells(gameActionFightMarkCellsMessage);
	}

	private void handleGameActionFightSlideMessage(DofusDataReader dataReader) {
		GameActionFightSlideMessage gameActionFightSlideMessage = new GameActionFightSlideMessage();
		gameActionFightSlideMessage.Deserialize(dataReader);
		iaPacket.gameActionFightSlide(gameActionFightSlideMessage);
	}

	private void handleGameActionFightSpellCastMessage(DofusDataReader dataReader) {
		GameActionFightSpellCastMessage gameActionFightSpellCastMessage = new GameActionFightSpellCastMessage();
		gameActionFightSpellCastMessage.Deserialize(dataReader);
		iaPacket.gameActionFightSpellCast(gameActionFightSpellCastMessage);
	}

	private void HandleGameContextCreateMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 250, "Game context create request");
	}

	private void handleGameContextRemoveElementMessage(DofusDataReader dataReader) {
		GameContextRemoveElementMessage gameContextRemoveElementMessage = new GameContextRemoveElementMessage();
		gameContextRemoveElementMessage.Deserialize(dataReader);
		for (int i = 0; i < this.getMonsters().getMonsters().size(); i++) {
			if (this.getMonsters().getMonsters().get(i).getContextualId() == gameContextRemoveElementMessage.getId()) {
				this.getMonsters().getMonsters().remove(i);
			}
		}
	}

	private void handleGameFightJoinMessage(DofusDataReader dataReader) {
		GameFightJoinMessage gameFightJoinMessage = new GameFightJoinMessage();
		gameFightJoinMessage.Deserialize(dataReader);
		iaPacket.gameFightJoin(gameFightJoinMessage);
	}

	private void handleGameEntitiesDispositionMessage(DofusDataReader dataReader) throws Exception {
		GameEntitiesDispositionMessage gameEntitiesDispositionMessage = new GameEntitiesDispositionMessage();
		gameEntitiesDispositionMessage.Deserialize(dataReader);
		iaPacket.gameEntitiesDisposition(gameEntitiesDispositionMessage);
	}

	private void handleGameFightPlacementPossiblePositionsMessage(DofusDataReader dataReader) {
		GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage = new GameFightPlacementPossiblePositionsMessage();
		gameFightPlacementPossiblePositionsMessage.Deserialize(dataReader);
		iaPacket.gameFightPlacementPossiblePositions(gameFightPlacementPossiblePositionsMessage);
	}

	private void handleGameFightSynchronizeMessage(DofusDataReader dataReader) {
		GameFightSynchronizeMessage gameFightSynchronizeMessage = new GameFightSynchronizeMessage();
		gameFightSynchronizeMessage.Deserialize(dataReader);
		iaPacket.gameFightSynchronize(gameFightSynchronizeMessage);
	}

	private void handleGameActionFightSummonMessage(DofusDataReader dataReader) {
		GameActionFightSummonMessage gameActionFightSummonMessage = new GameActionFightSummonMessage();
		gameActionFightSummonMessage.Deserialize(dataReader);
		iaPacket.gameActionFightSummon(gameActionFightSummonMessage);
	}

	private void handleGameFightTurnEndMessage(DofusDataReader dataReader) {
		GameFightTurnEndMessage gameFightTurnEndMessage = new GameFightTurnEndMessage();
		gameFightTurnEndMessage.Deserialize(dataReader);
		iaPacket.gameFightTurnEnd(gameFightTurnEndMessage);
	}

	private void handleGameFightTurnListMessage(DofusDataReader dataReader) {
		GameFightTurnListMessage gameFightTurnListMessage = new GameFightTurnListMessage();
		gameFightTurnListMessage.Deserialize(dataReader);
		iaPacket.gameFightTurnList(gameFightTurnListMessage);
	}

	private void handleGameMapMovementMessage(DofusDataReader dataReader) {
		if (this.info.isJoinedFight()) {
			GameMapMovementMessage gameMapMovementMessage = new GameMapMovementMessage();
			gameMapMovementMessage.Deserialize(dataReader);
			iaPacket.gameMapMovement(gameMapMovementMessage);
		}
		else {
			GameMapMovementMessage gameMapMovementMessage = new GameMapMovementMessage();
			gameMapMovementMessage.Deserialize(dataReader);
			if (gameMapMovementMessage.getActorId() == info.getActorId()) {
				info.setCellId(gameMapMovementMessage.getKeyMovements().get(gameMapMovementMessage.getKeyMovements().size() - 1));
			}
			for (int i = 0; i < this.getMonsters().getMonsters().size(); i++) {
				if (this.getMonsters().getMonsters().get(i).getContextualId() == gameMapMovementMessage.getActorId()) {
					this.getMonsters().getMonsters().get(i).getDisposition().setCellId(gameMapMovementMessage.getKeyMovements().get(gameMapMovementMessage.getKeyMovements().size() - 1));
				}
			}
		}
	}

	private void handleGameRolePlayArenaSwitchToFightServerMessage(DofusDataReader dataReader) throws IOException {
		connectionToKoli = true;
		GameRolePlayArenaSwitchToFightServerMessage arenaSwitchToFightServerMessage = new GameRolePlayArenaSwitchToFightServerMessage();
		arenaSwitchToFightServerMessage.Deserialize(dataReader);
		Ticket = arenaSwitchToFightServerMessage.getTicket();
		this.socket.close();
		System.out.println("New connection to server for arena");
		this.socket = new Socket(arenaSwitchToFightServerMessage.getAddress(), 5555);
	}

	private void handleGameRolePlayArenaSwitchToGameServerMessage(DofusDataReader dataReader) throws IOException {
		connectionToKoli = false;
		latencyFrame.Sequence = 1;
		GameRolePlayArenaSwitchToGameServerMessage arenaSwitchToGameServerMessage = new GameRolePlayArenaSwitchToGameServerMessage();
		arenaSwitchToGameServerMessage.Deserialize(dataReader);
		Ticket = arenaSwitchToGameServerMessage.getTicket();
		this.socket.close();
		System.out.println("New connection to server from arena");
		this.socket = new Socket(ip, 5555);
	}

	private void handleGameRolePlayShowActorMessage(DofusDataReader dataReader) {
		GameRolePlayShowActorMessage gameRolePlayShowActorMessage = new GameRolePlayShowActorMessage();
		gameRolePlayShowActorMessage.Deserialize(dataReader);
		if (gameRolePlayShowActorMessage.getInformations().getClass().getSimpleName().equals("GameRolePlayGroupMonsterInformations")) {
			this.getMonsters().addMonsters((GameRolePlayGroupMonsterInformations) gameRolePlayShowActorMessage.getInformations());
		}
	}

	private void handleHelloConnectMessage(DofusDataReader dataReader) throws Exception {
		HelloConnectMessage hello = new HelloConnectMessage();
		hello.Deserialize(dataReader);
		byte[] key = new byte[hello.getKey().size()];
		for (int i = 0; i < hello.getKey().size(); i++) {
			key[i] = hello.getKey().get(i).byteValue();
		}
		VersionExtended versionExtended = new VersionExtended(2, 47, 3, 0, 0, 0, 1, 1);
		byte[] credentials = Crypto.encrypt(key, info.getNameAccount(), info.getPassword(), hello.getSalt());
		List<Integer> credentialsArray = new ArrayList<Integer>();
		for (byte b : credentials) {
			credentialsArray.add((int) b);
		}
		List<Integer> failedAttempts = new ArrayList<Integer>();
		IdentificationMessage identification = new IdentificationMessage(versionExtended, "fr", credentialsArray, 0, true, false, false, 0, failedAttempts);
		sendToServer(identification, IdentificationMessage.ProtocolId, "Identification...");
	}

	private void handleInteractiveElementUpdatedMessage(DofusDataReader dataReader) {
		if (info.isConnected()) {
			InteractiveElementUpdatedMessage interactiveElementUpdatedMessage = new InteractiveElementUpdatedMessage();
			interactiveElementUpdatedMessage.Deserialize(dataReader);
			for (int i = 0; i < getInteractive().getInteractiveElements().size(); i++) {
				if (interactiveElementUpdatedMessage.getInteractiveElement().getElementId() == getInteractive().getInteractiveElements().get(i).getElementId()) {
					getInteractive().getInteractiveElements().set(i, interactiveElementUpdatedMessage.getInteractiveElement());
				}
			}
		}
	}

	private void handleInventoryContentMessage(DofusDataReader dataReader) {
		this.stats.setInventoryContentMessage(new InventoryContentMessage());
		this.stats.getInventoryContentMessage().Deserialize(dataReader);
	}

	private void handleInventoryWeightMessage(DofusDataReader dataReader) {
		InventoryWeightMessage weight = new InventoryWeightMessage();
		weight.Deserialize(dataReader);
		info.setWeight(weight.getWeight());
		info.setWeigthMax(weight.getWeightMax());
		info.setWaitForHarvestSuccess(true);
		info.setObjectUse(true);
	}

	private void handleJobExperienceMultiUpdateMessage(DofusDataReader dataReader) {
		JobExperienceMultiUpdateMessage experienceMultiUpdateMessage = new JobExperienceMultiUpdateMessage();
		experienceMultiUpdateMessage.Deserialize(dataReader);
		for (JobExperience b : experienceMultiUpdateMessage.getExperiencesUpdate()) {
			stats.getJob().add(new JobExperience(b.getJobId(), b.getJobLevel(), b.getJobXP(), b.getJobXpLevelFloor(), b.getJobXpNextLevelFloor()));
		}
	}

	private void handleJobExperienceUpdateMessage(DofusDataReader dataReader) {
		JobExperienceUpdateMessage experienceUpdateMessage = new JobExperienceUpdateMessage();
		experienceUpdateMessage.Deserialize(dataReader);
		for (int i = 0; i < stats.getJob().size(); i++) {
			if (experienceUpdateMessage.getExperiencesUpdate().getJobId() == stats.getJob().get(i).getJobId()) {
				stats.getJob().set(i, experienceUpdateMessage.getExperiencesUpdate());
			}
		}
	}

	private void handleKamasUpdateMessage(DofusDataReader dataReader) {
		KamasUpdateMessage kamasUpdateMessage = new KamasUpdateMessage();
		kamasUpdateMessage.Deserialize(dataReader);
		this.stats.getInventoryContentMessage().setKamas(kamasUpdateMessage.getKamasTotal());
		info.setStorageUpdate(true);
	}

	private void HandleLatencyMessage() throws Exception {
		BasicLatencyStatsMessage basicLatencyStatsMessage = new BasicLatencyStatsMessage(latencyFrame.getLatencyAvg(), latencyFrame.getSamplesCount(), latencyFrame.GetSamplesMax());
		sendToServer(basicLatencyStatsMessage, BasicLatencyStatsMessage.ProtocolId, "Latency message");
	}

	private void handleMapComplementaryInformationsDataInHavenBagMessage(DofusDataReader dataReader) {
		MapComplementaryInformationsDataInHavenBagMessage mapComplementaryInformationsDataInHavenBagMessage = new MapComplementaryInformationsDataInHavenBagMessage();
		mapComplementaryInformationsDataInHavenBagMessage.Deserialize(dataReader);
		getInteractive().setStatedElements(mapComplementaryInformationsDataInHavenBagMessage.getStatedElements());
		getInteractive().setInteractiveElements(mapComplementaryInformationsDataInHavenBagMessage.getInteractiveElements());
		append("Map : [" + info.getCoords()[0] + ";" + info.getCoords()[1] + "]");
		this.info.setCellId(mapComplementaryInformationsDataInHavenBagMessage.getActors().get(0).getDisposition().getCellId());
		append("CellId : " + info.getCellId());
		info.setWaitForMov(true);
		info.setNewMap(true);
	}

	private void handleMapComplementaryInformationsDataMessage(DofusDataReader dataReader) {
		this.monsters.setMonsters(new ArrayList<>());
		this.hunt.setPhorror(false);
		MapComplementaryInformationsDataMessage complementaryInformationsDataMessage = new MapComplementaryInformationsDataMessage();
		complementaryInformationsDataMessage.Deserialize(dataReader);
		if (!connectionToKoli) {
			for (int i = 0; i < complementaryInformationsDataMessage.getActors().size(); i++) {
				if (complementaryInformationsDataMessage.getActors().get(i).getClass().getSimpleName().equals("GameRolePlayNpcInformations")) {
					npc.getNpc().add((GameRolePlayNpcInformations) complementaryInformationsDataMessage.getActors().get(i));
				}
				else if (complementaryInformationsDataMessage.getActors().get(i).getClass().getSimpleName().equals("GameRolePlayGroupMonsterInformations")) {
					this.getMonsters().addMonsters((GameRolePlayGroupMonsterInformations) complementaryInformationsDataMessage.getActors().get(i));

				}
				else if (complementaryInformationsDataMessage.getActors().get(i).getClass().getSimpleName().equals("GameRolePlayTreasureHintInformations")) {
					this.hunt.setPhorrorName(GameData.getNpcName(((GameRolePlayTreasureHintInformations) complementaryInformationsDataMessage.getActors().get(i)).getNpcId()));
					this.hunt.setPhorror(true);
				}
				if (complementaryInformationsDataMessage.getActors().get(i).getContextualId() == info.getActorId()) info.setCellId(complementaryInformationsDataMessage.getActors().get(i).getDisposition().getCellId());
			}
			getInteractive().setStatedElements(complementaryInformationsDataMessage.getStatedElements());
			getInteractive().setInteractiveElements(complementaryInformationsDataMessage.getInteractiveElements());

			//			for (InteractiveElement interactiveElement : this.interactive.getInteractiveElements()) {
			//				System.out.println(interactiveElement.getElementId());
			//				System.out.println(interactiveElement.getEnabledSkills());
			//			}

			//			append("Map : [" + info.getCoords()[0] + ";" + info.getCoords()[1] + "]");
			//			append("CellId : " + info.getCellId());
			info.setWaitForMov(true);
			info.setConnected(true);
			info.setNewMap(true);
		}
	}

	private void handleMapRequestMessage(DofusDataReader dataReader) throws Exception {
		CurrentMapMessage currentMapMessage = new CurrentMapMessage();
		currentMapMessage.Deserialize(dataReader);
		info.setMapId(currentMapMessage.getMapId());
		if (connectionToKoli) {
			sendToServer(new GameContextReadyMessage(currentMapMessage.getMapId()), GameContextReadyMessage.ProtocolId, "Context ready");
		}
		else {
			MapInformationsRequestMessage informationsRequestMessage = new MapInformationsRequestMessage(currentMapMessage.getMapId());
			this.map = MapManager.FromId((int) currentMapMessage.getMapId());
			this.interactive.setMap(map);
			this.info.setCoords(GameData.getCoordMap((int) currentMapMessage.getMapId()));
			this.info.setWorldmap(GameData.getWorldMap((int) currentMapMessage.getMapId()));
			sendToServer(informationsRequestMessage, MapInformationsRequestMessage.ProtocolId, "Map info request");
		}
	}

	private void handleNpcDialogQuestionMessage(DofusDataReader dataReader) throws Exception {
		NpcDialogQuestionMessage dialogQuestionMessage = new NpcDialogQuestionMessage();
		dialogQuestionMessage.Deserialize(dataReader);
		this.npc.reply(this.npc.getReplyId(dialogQuestionMessage.getMessageId()));
	}

	private void handleObjectAddedMessage(DofusDataReader dataReader) {
		ObjectAddedMessage objectAddedMessage = new ObjectAddedMessage();
		objectAddedMessage.Deserialize(dataReader);
		this.stats.getInventoryContentMessage().getObjects().add(objectAddedMessage.getObject());
	}

	private void handleObjectDeletedMessage(DofusDataReader dataReader) {
		ObjectDeletedMessage objectDeletedMessage = new ObjectDeletedMessage();
		objectDeletedMessage.Deserialize(dataReader);
		for (int i = 0; i < this.stats.getInventoryContentMessage().getObjects().size(); i++) {
			if (this.stats.getInventoryContentMessage().getObjects().get(i).getObjectUID() == objectDeletedMessage.getObjectUID()) {
				this.stats.getInventoryContentMessage().getObjects().remove(i);
			}
		}
	}

	private void handleObjectQuantityMessage(DofusDataReader dataReader) {
		ObjectQuantityMessage objectQuantityMessage = new ObjectQuantityMessage();
		objectQuantityMessage.Deserialize(dataReader);
		for (int i = 0; i < this.stats.getInventoryContentMessage().getObjects().size(); i++) {
			if (this.stats.getInventoryContentMessage().getObjects().get(i).getObjectUID() == objectQuantityMessage.getObjectUID()) {
				ObjectItem object = this.stats.getInventoryContentMessage().getObjects().get(i);
				this.stats.getInventoryContentMessage().getObjects().set(i, new ObjectItem(object.getPosition(), object.getObjectGID(), object.getEffects(), object.getObjectUID(), objectQuantityMessage.getQuantity()));
			}
		}
	}

	private void handleObjectsAddedMessage(DofusDataReader dataReader) {
		ObjectsAddedMessage objectsAddedMessage = new ObjectsAddedMessage();
		objectsAddedMessage.Deserialize(dataReader);
		for (int i = 0; i < objectsAddedMessage.getObject().size(); i++) {
			this.stats.getInventoryContentMessage().getObjects().add(objectsAddedMessage.getObject().get(i));
		}
	}

	private void handleObjectsDeletedMessage(DofusDataReader dataReader) {
		ObjectsDeletedMessage objectsDeletedMessage = new ObjectsDeletedMessage();
		objectsDeletedMessage.Deserialize(dataReader);
		for (int i = 0; i < objectsDeletedMessage.getObjectUID().size(); i++) {
			for (int k = 0; k < this.stats.getInventoryContentMessage().getObjects().size(); k++) {
				if (this.stats.getInventoryContentMessage().getObjects().get(k).getObjectUID() == objectsDeletedMessage.getObjectUID().get(i)) {
					this.stats.getInventoryContentMessage().getObjects().remove(k);
					break;
				}
			}
		}
	}

	private void handleObtainedItemMessage(DofusDataReader dataReader) {
		ObtainedItemMessage itemMessage = new ObtainedItemMessage();
		itemMessage.Deserialize(dataReader);
		getInteractive().setLastItemHarvestedId(itemMessage.getGenericId());
		getInteractive().setQuantityLastItemHarvested(itemMessage.getBaseQuantity());
		info.setHarvestSuccess(true);
	}

	private void HandleRawDataMessage() throws Exception {
		List<Integer> tt = new ArrayList<>();
		for (int i = 0; i <= 255; i++) {
			int rand = ThreadLocalRandom.current().nextInt(-127, 127);
			tt.add(rand);
		}
		CheckIntegrityMessage RDM = new CheckIntegrityMessage(tt);
		sendToServer(RDM, CheckIntegrityMessage.ProtocolId, "Check Integrity Message...");
	}

	private void handleSelectedServerDataMessage(DofusDataReader dataReader) throws IOException {
		SelectedServerDataMessage selectServer = new SelectedServerDataMessage();
		selectServer.Deserialize(dataReader);
		Ticket = selectServer.getTicket();
		this.socket.close();
		this.socket = new Socket(selectServer.getAddress(), selectServer.getPort());
	}

	private void handleSequenceEndMessage(DofusDataReader dataReader) throws Exception {
		SequenceEndMessage sequenceEndMessage = new SequenceEndMessage();
		sequenceEndMessage.Deserialize(dataReader);
		if (sequenceEndMessage.getAuthorId() == info.getActorId()) {
			Thread.sleep(1000);
			sendToServer(new GameActionAcknowledgementMessage(true, sequenceEndMessage.getActionId()), GameActionAcknowledgementMessage.ProtocolId, "Game Action Acknowledgement Message");
			this.getInfo().setAcknowledged(true);
		}
		iaPacket.sequenceEndMessage(sequenceEndMessage);
	}

	private void handleSequenceNumberMessage() throws Exception {
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(latencyFrame.Sequence++);
		sendToServer(sequenceNumberMessage, SequenceNumberMessage.ProtocolId, "Sequence number");
	}

	private void handleServersListMessage(DofusDataReader dataReader) throws Exception {
		ServersListMessage servers = new ServersListMessage();
		servers.Deserialize(dataReader);
		int serverId = -1;

		for (GameServerInformations server : servers.getServers()) {
			if (GameData.getNameServer(server.getId()).equals(this.info.getServer())) {
				serverId = server.getId();
				if (!(server.getStatus() == 3 || server.getStatus() == 0)) {
					System.out.println("Server down");
					this.socket.close();
					return;
				}
				break;
			}
		}
		if (serverId == -1) { throw new Exception("Wrong server name"); }
		ServerSelectionMessage select = new ServerSelectionMessage(serverId);
		sendToServer(select, ServerSelectionMessage.ProtocolId, "Selection du serveur...");
	}

	private void handleStatedElementUpdatedMessage(DofusDataReader dataReader) {
		if (info.isConnected()) {
			StatedElementUpdatedMessage elementUpdatedMessage = new StatedElementUpdatedMessage();
			elementUpdatedMessage.Deserialize(dataReader);
			for (int i = 0; i < getInteractive().getStatedElements().size(); i++) {
				if (elementUpdatedMessage.getStatedElement().getElementCellId() == getInteractive().getStatedElements().get(i).getElementCellId()) {
					getInteractive().getStatedElements().set(i, elementUpdatedMessage.getStatedElement());
				}
			}
		}
	}

	private void handleStorageKamasUpdateMessage(DofusDataReader dataReader) {
		StorageKamasUpdateMessage storageKamasUpdateMessage = new StorageKamasUpdateMessage();
		storageKamasUpdateMessage.Deserialize(dataReader);
		getBank().getStorage().setKamas(storageKamasUpdateMessage.getKamasTotal());
		info.setStorageUpdate(true);
	}

	private void handleStorageObjectRemoveMessage(DofusDataReader dataReader) {
		StorageObjectRemoveMessage storageObjectRemoveMessage = new StorageObjectRemoveMessage();
		storageObjectRemoveMessage.Deserialize(dataReader);
		for (int i = 0; i < getBank().getStorage().getObjects().size(); i++) {
			if (getBank().getStorage().getObjects().get(i).getObjectUID() == storageObjectRemoveMessage.getObjectUID()) {
				getBank().getStorage().getObjects().remove(i);
			}
		}
		info.setStorageUpdate(true);
	}

	private void handleStorageObjectsRemoveMessage(DofusDataReader dataReader) {
		StorageObjectsRemoveMessage storageObjectsRemoveMessage = new StorageObjectsRemoveMessage();
		storageObjectsRemoveMessage.Deserialize(dataReader);
		for (int i = 0; i < storageObjectsRemoveMessage.getObjectUIDList().size(); i++) {
			for (int k = 0; k < getBank().getStorage().getObjects().size(); k++) {
				if (storageObjectsRemoveMessage.getObjectUIDList().get(i) == getBank().getStorage().getObjects().get(k).getObjectUID()) {
					getBank().getStorage().getObjects().remove(k);
					break;
				}
			}
		}
		info.setStorageUpdate(true);
	}

	private void handleStorageObjectsUpdateMessage(DofusDataReader dataReader) {
		StorageObjectsUpdateMessage storageObjectsUpdateMessage = new StorageObjectsUpdateMessage();
		storageObjectsUpdateMessage.Deserialize(dataReader);
		for (int i = 0; i < storageObjectsUpdateMessage.getObjectList().size(); i++) {
			boolean isInBank = false;
			for (int k = 0; k < getBank().getStorage().getObjects().size(); k++) {
				if (storageObjectsUpdateMessage.getObjectList().get(i).getObjectUID() == getBank().getStorage().getObjects().get(k).getObjectUID()) {
					getBank().getStorage().getObjects().set(i, storageObjectsUpdateMessage.getObjectList().get(i));
					isInBank = true;
				}
			}
			if (!isInBank) {
				getBank().getStorage().getObjects().add(storageObjectsUpdateMessage.getObjectList().get(i));
			}
		}
		info.setStorageUpdate(true);
	}

	private void handleStorageObjectUpdateMessage(DofusDataReader dataReader) {
		StorageObjectUpdateMessage storageObjectUpdateMessage = new StorageObjectUpdateMessage();
		storageObjectUpdateMessage.Deserialize(dataReader);
		boolean isItem = false;
		for (int i = 0; i < getBank().getStorage().getObjects().size(); i++) {
			if (getBank().getStorage().getObjects().get(i).getObjectGID() == storageObjectUpdateMessage.getObject().getObjectGID() || getBank().getStorage().getObjects().get(i).getObjectUID() == storageObjectUpdateMessage.getObject().getObjectUID()) {
				getBank().getStorage().getObjects().set(i, storageObjectUpdateMessage.getObject());
				isItem = true;
			}
		}
		if (!isItem) {
			getBank().getStorage().getObjects().add(storageObjectUpdateMessage.getObject());
		}
		info.setStorageUpdate(true);
	}

	private void handleTreasureHuntDigRequestAnswerMessage(DofusDataReader dataReader) {
		TreasureHuntDigRequestAnswerMessage treasureHuntDigRequestAnswerMessage = new TreasureHuntDigRequestAnswerMessage();
		treasureHuntDigRequestAnswerMessage.Deserialize(dataReader);
		if (treasureHuntDigRequestAnswerMessage.getResult() == 1) {
			this.getInfo().setStepSuccess(true);
		}
		else if (treasureHuntDigRequestAnswerMessage.getResult() == 2) {
			this.getInfo().setStepSuccess(true);
			this.hunt.setRdyToFight(true);
		}
		else {
			this.hunt.digResult = treasureHuntDigRequestAnswerMessage.getResult();
			this.getInfo().setStepFailed(true);
		}
	}

	private void handleTreasureHuntMessage(DofusDataReader dataReader) {
		TreasureHuntMessage treasureHuntMessage = new TreasureHuntMessage();
		treasureHuntMessage.Deserialize(dataReader);
		int sizeStep = treasureHuntMessage.getKnownStepsList().size();
		this.hunt.setNumberOfSteps(treasureHuntMessage.getCheckPointTotal());
		this.hunt.setCurrentStep(treasureHuntMessage.getCheckPointCurrent());
		this.hunt.setNumberOfIndex(treasureHuntMessage.getTotalStepCount());
		if (treasureHuntMessage.getFlags().size() == 0) {
			this.hunt.setStartMapCoords(GameData.getCoordMap((int) treasureHuntMessage.getStartMapId()));
		}
		else {
			this.hunt.setStartMapCoords(GameData.getCoordMap((int) treasureHuntMessage.getFlags().get(treasureHuntMessage.getFlags().size() - 1).getMapId()));
		}
		this.hunt.setCurrentIndex(treasureHuntMessage.getFlags().size());
		if (treasureHuntMessage.getKnownStepsList().get(sizeStep - 1).getClass().getSimpleName().equals("TreasureHuntStepFollowDirectionToPOI")) {
			this.hunt.setCurrentClue(GameData.getClueName(((TreasureHuntStepFollowDirectionToPOI) treasureHuntMessage.getKnownStepsList().get(sizeStep - 1)).getPoiLabelId()));
			this.hunt.setCurrentDir(((TreasureHuntStepFollowDirectionToPOI) treasureHuntMessage.getKnownStepsList().get(sizeStep - 1)).getDirection());
		}
		else if (treasureHuntMessage.getKnownStepsList().get(sizeStep - 1).getClass().getSimpleName().equals("TreasureHuntStepFollowDirectionToHint")) {
			this.hunt.setCurrentClue(GameData.getNpcName(((TreasureHuntStepFollowDirectionToHint) treasureHuntMessage.getKnownStepsList().get(sizeStep - 1)).getNpcId()));
			this.hunt.setCurrentDir(((TreasureHuntStepFollowDirectionToHint) treasureHuntMessage.getKnownStepsList().get(sizeStep - 1)).getDirection());
		}
		this.info.setHuntAnswered(true);
		this.info.setInHunt(true);
	}

	private void handleZaapListMessage(DofusDataReader dataReader) {
		ZaapListMessage zaapListMessage = new ZaapListMessage();
		zaapListMessage.Deserialize(dataReader);
		this.interactive.setZaapList(zaapListMessage.getMapIds());
	}

	@Override
	public void run() {
		try {
			if (socket == null) { return; }
			while (!this.socket.isClosed()) {
				Thread.sleep(400);
				if (!this.socket.isClosed()) {
					InputStream data = this.socket.getInputStream();
					int available = data.available();
					byte[] buffer = new byte[available];
					if (System.currentTimeMillis() - timeout > 15000 && packetSent) {
						System.out.println("Disconnected from server");
						this.socket.close();
						break;
					}
					if (available > 0) {
						packetSent = false;
						latencyFrame.updateLatency();
						try {
							data.read(buffer, 0, available);
							DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(buffer));
							buildMessage(reader);
						}
						catch (Exception e) {
							System.out.println("Socket error");
							e.printStackTrace();
						}
					}
				}

			}
			System.out.println("Socket is closed");
			if (this.info.isPrintDc() && this.info.isConnected()) Communication.sendToModel(String.valueOf(getBotInstance()), String.valueOf(-1), "m", "info", "disconnect", new Object[] { "True" });
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Send packet to server message = type; id = id packet; String s = String
	 * displayed on log
	 */
	public void sendToServer(NetworkMessage message, int id, String s) throws Exception {
		info.setBooleanToFalse();
		latencyFrame.latestSent();
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		DofusDataWriter writer = new DofusDataWriter(bous);
		message.Serialize(writer);
		try {
			byte[] wrote = WritePacket(writer, bous, id);
			socket.getOutputStream().write(wrote);
			socket.getOutputStream().flush();
			timeout = System.currentTimeMillis();
			packetSent = true;
		}
		catch (SocketException e) {
			System.out.print(e);
			System.out.println(" " + message);
		}
		appendLog("[" + id + "]	[Envoi] " + s);
	}

	public void setFight(Fight fight) {
		this.fight = fight;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public void setInteractive(Interactive interactive) {
		this.interactive = interactive;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	private int SubComputeStaticHeader(int id, byte typeLen) {
		return (id << 2) | typeLen;
	}

	/**
	 * Packet manager
	 */
	@SuppressWarnings("unchecked")
	private void TreatPacket(int packet_id, byte[] packet_content) {
		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(packet_content));
		SwitchNameClass name = new SwitchNameClass(packet_id);
		appendLog("[" + packet_id + "]\tTaille : " + packet_content.length + "  -  " + name.name);
		JSONObject jsonObject = null;
		JSONObject jsonObject2;
		try {
			switch (packet_id) {
				case 3:
					handleHelloConnectMessage(dataReader);
					break;
				case 189:
					SystemMessageDisplayMessage systemMessageDisplayMessage = new SystemMessageDisplayMessage();
					systemMessageDisplayMessage.Deserialize(dataReader);
					System.out.println(systemMessageDisplayMessage.getMsgId());
					break;
				case 30:
					handleServersListMessage(dataReader);
					break;
				case 41:
					SelectedServerRefusedMessage selectedServerRefusedMessage = new SelectedServerRefusedMessage();
					selectedServerRefusedMessage.Deserialize(dataReader);
					System.out.println(selectedServerRefusedMessage.getError());
					System.out.println(selectedServerRefusedMessage.getServerId());
					System.out.println(selectedServerRefusedMessage.getServerStatus());
					break;
				case 42:
					handleSelectedServerDataMessage(dataReader);
					break;
				case 101:
					HandleAuthentificationTicketMessage();
					if (connectionToKoli) {
						HandleCharacterListRequestMessage();
					}
					break;
				case 5609:
					info.setCaracsAffected(true);
				case 6072:
					sendToServer(new CharacterSelectedForceReadyMessage(), CharacterSelectedForceReadyMessage.ProtocolId, "Logging back into combat");
					break;
				case 6253:
					HandleRawDataMessage();
					break;
				case 6267:
					HandleCharacterListRequestMessage();
					break;
				case 151:
					handleCharacterSelectionMessage(dataReader);
					break;
				case 153:
					CharacterSelectedSuccessMessage selectedSuccessMessage = new CharacterSelectedSuccessMessage();
					selectedSuccessMessage.Deserialize(dataReader);
					this.info.setActorId(selectedSuccessMessage.getInfos().getId());
					info.setLvl(selectedSuccessMessage.getInfos().getLevel());
					break;
				case 1301:
					HandleFriendIgnoreSpouseMessages();
					break;
				case 4002:
					HandleClientKeyMessage(FlashKeyGenerator.GetRandomFlashKey(info.getName()));
					HandleGameContextCreateMessage();
					break;
				case 500:
					stats.setStats(new CharacterStatsListMessage());
					stats.getStats().Deserialize(dataReader);
					stats.setTimePacketRecv(System.currentTimeMillis() / 1000);
					break;
				case 220:
					handleMapRequestMessage(dataReader);
					break;
				case 176:
					if (this.getInfo().isJoinedFight()) this.intelligence.setInit(true);
					info.setBasicNoOperationMsg(true);
					break;
				case 226:
					handleMapComplementaryInformationsDataMessage(dataReader);
					break;
				case 6622:
					handleMapComplementaryInformationsDataInHavenBagMessage(dataReader);
					break;
				case 950:
					GameMapNoMovementMessage gameMapNoMovementMessage = new GameMapNoMovementMessage();
					super.debug.println(getTiming() + "Can't move from " + this.info.getCellId() + " to cell " + (gameMapNoMovementMessage.getCellX() + gameMapNoMovementMessage.getCellY() * 14) + " on map " + this.map.getId());
					break;
				case 951:
					handleGameMapMovementMessage(dataReader);
					break;
				case 6316:
					handleSequenceNumberMessage();
					break;
				case 5816:
					HandleLatencyMessage();
					break;
				case 6575:
					handleGameRolePlayArenaSwitchToFightServerMessage(dataReader);
					break;
				case 6574:
					handleGameRolePlayArenaSwitchToGameServerMessage(dataReader);
					break;
				case 6068:
					sendToServer(new CharacterSelectedForceReadyMessage(), CharacterSelectedForceReadyMessage.ProtocolId, "Character force selection");
					break;
				case 6471:
					if (connectionToKoli) {
						sendToServer(new GameContextCreateRequestMessage(), GameContextCreateRequestMessage.ProtocolId, "Context creation request");
					}
					break;
				case 5709:
					handleStatedElementUpdatedMessage(dataReader);
					break;
				case 5708:
					handleInteractiveElementUpdatedMessage(dataReader);
					break;
				case 6112:
					break;
				case 6384:
					info.setHarvestFailure(true);
					break;
				case 3009:
					handleInventoryWeightMessage(dataReader);
					break;
				case 6519:
					handleObtainedItemMessage(dataReader);
					break;
				case 5809:
					handleJobExperienceMultiUpdateMessage(dataReader);
					break;
				case 5654:
					handleJobExperienceUpdateMessage(dataReader);
					break;
				case 5670:
					handleCharacterLevelUpMessage(dataReader);
					break;
				case 5617:
					handleNpcDialogQuestionMessage(dataReader);
					break;
				case 5502:
					this.npc.setDialogOver(true);
					break;
				case 5745:
					info.setInteractiveUsed(true);
					break;
				case 780:
					TextInformationMessage informationMessage = new TextInformationMessage();
					informationMessage.Deserialize(dataReader);
					if (informationMessage.getMsgId() == 436) {
						this.hunt.setAbTimeLeft(Integer.parseInt(informationMessage.getParameters().get(0)));
					}
					info.setTextMessage(true);
					break;
				case 5646:
					getBank().setStorage(new StorageInventoryContentMessage());
					getBank().getStorage().Deserialize(dataReader);
					info.setStorage(true);
					break;
				case 6162:
					this.stats.setInventoryContentMessage(new InventoryContentAndPresetMessage());
					this.stats.getInventoryContentMessage().Deserialize(dataReader);
					break;
				case 3023:
					handleObjectQuantityMessage(dataReader);
					break;
				case 3025:
					handleObjectAddedMessage(dataReader);
					break;
				case 3024:
					handleObjectDeletedMessage(dataReader);
					break;
				case 6034:
					handleObjectsDeletedMessage(dataReader);
					break;
				case 6033:
					handleObjectsAddedMessage(dataReader);
					break;
				case 3016:
					handleInventoryContentMessage(dataReader);
					break;
				case 6036:
					handleStorageObjectsUpdateMessage(dataReader);
					break;
				case 6035:
					handleStorageObjectsRemoveMessage(dataReader);
					break;
				case 5647:
					handleStorageObjectUpdateMessage(dataReader);
					break;
				case 5648:
					handleStorageObjectRemoveMessage(dataReader);
					break;
				case 5628:
					info.setLeaveExchange(true);
					break;
				case 5537:
					handleKamasUpdateMessage(dataReader);
					break;
				case 5645:
					handleStorageKamasUpdateMessage(dataReader);
					break;
				case 702:
					handleGameFightJoinMessage(dataReader);
					break;
				case 956:
					handleSequenceEndMessage(dataReader);
					break;
				case 715:
					sendToServer(new GameFightTurnReadyMessage(true), GameFightTurnReadyMessage.ProtocolId, "Turn ready");
					break;
				case 719:
					handleGameFightTurnEndMessage(dataReader);
					break;
				case 720:
					this.getInfo().setJoinedFight(false);
					GameFightEndMessage gameFightEndMessage = new GameFightEndMessage();
					gameFightEndMessage.Deserialize(dataReader);
					iaPacket.gameFightEnd(gameFightEndMessage);
					Communication.sendToModel(String.valueOf(getBotInstance()), String.valueOf(info.addAndGetMsgIdFight()), "m", "info", "combat", new Object[] { "\"end\"" });
					break;
				case 703:
					handleGameFightPlacementPossiblePositionsMessage(dataReader);
					break;
				case 5696:
					handleGameEntitiesDispositionMessage(dataReader);
					break;
				case 5921:
					handleGameFightSynchronizeMessage(dataReader);
					break;
				case 6465:
					info.setTurn(true);
					iaPacket.gameFightTurnStartPlaying();
					break;
				case 955:
					SequenceStartMessage sequenceStartMessage = new SequenceStartMessage();
					sequenceStartMessage.Deserialize(dataReader);
					iaPacket.sequenceStartMessage(sequenceStartMessage);
					break;
				case 5825:
					handleGameActionFightSummonMessage(dataReader);
					break;
				/**
				 * LYSANDRE FIGHT // case 1010 to case 6310 Each sequence can
				 * contain multiple jsonObject Each case contains one jsonObject
				 * jsonObject : key of the variable jsonObject : key of the
				 * packet sourceId : caster targetId : target
				 */
				case 1010:
					handleGameActionFightSpellCastMessage(dataReader);
					break;
				case 6132:
					GameActionFightNoSpellCastMessage gameActionFightNoSpellCastMessage = new GameActionFightNoSpellCastMessage();
					gameActionFightNoSpellCastMessage.Deserialize(dataReader);
					iaPacket.gameActionFightNoSpellCast(gameActionFightNoSpellCastMessage);
					break;
				case 6312:
					handleGameActionFightLifePointsLostMessage(dataReader);
					break;
				case 5828:
					handleGameActionFightDodgePointLossMessage(dataReader);
					break;
				/**
				 * Depending on the packet received it will give a JsonObject
				 * with diferent key the first are in common for every packet
				 */
				case 6070:
					handleGameActionFightDispellableEffectMessage(dataReader);
					break;
				case 5540:
					handleGameActionFightMarkCellsMessage(dataReader);
					break;
				case 6311:
					handleGameActionFightLifePointsGainMessage(dataReader);
					break;
				case 1099:
					handleGameActionFightDeathMessage(dataReader);
					break;
				case 6116:
					handleGameActionFightCloseCombatMessage(dataReader);
					break;
				case 5525:
					handleGameActionFightSlideMessage(dataReader);
					break;
				case 6310:
					handleGameActionFightLifeAndShieldPointsLostMessage(dataReader);
					break;
				case 713:
					handleGameFightTurnListMessage(dataReader);
					break;
				case 1604:
					handleZaapListMessage(dataReader);
					break;
				case 5632:
					handleGameRolePlayShowActorMessage(dataReader);
					break;
				case 251:
					handleGameContextRemoveElementMessage(dataReader);
					break;
				case 6483:
					this.info.setHuntAnswered(true);
					this.info.setInHunt(false);
					this.hunt.setRdyToFight(false);
					break;
				case 6484:
					handleTreasureHuntDigRequestAnswerMessage(dataReader);
					break;
				case 6486:
					handleTreasureHuntMessage(dataReader);
					break;
				case 5937:
					handleGameRolePlayPlayerFightFriendlyRequestedMessage(dataReader);
					break;
				case 1030:
					handleGameActionFightPointsVariationMessage(dataReader);
					break;
				case 5905:
					ExchangeStartedBidSellerMessage exchangeStartedBidSellerMessage = new ExchangeStartedBidSellerMessage();
					exchangeStartedBidSellerMessage.Deserialize(dataReader);
					this.getNpc().setItemsToSell(exchangeStartedBidSellerMessage.getObjectsInfos());
					this.npc.setCanSell(exchangeStartedBidSellerMessage.getSellerDescriptor().getTypes());
					this.info.setExchangeBidSeller(true);
					break;
				case 6464:
					ExchangeBidPriceForSellerMessage exchangeBidPriceForSellerMessage = new ExchangeBidPriceForSellerMessage();
					exchangeBidPriceForSellerMessage.Deserialize(dataReader);
					this.npc.setCurrentPrice(exchangeBidPriceForSellerMessage.getMinimalPrices());
					this.info.setExchangeBidSeller(true);
					break;
				case 5945:
					this.info.setExchangeBidSeller(true);
					break;
				case 5765:
					this.info.setExchangeBidSeller(true);
					break;
				case 5904:
					this.info.setExchangeBidSeller(true);
					break;
				case 5946:
					ExchangeBidHouseItemRemoveOkMessage exchangeBidHouseItemRemoveOkMessage = new ExchangeBidHouseItemRemoveOkMessage();
					exchangeBidHouseItemRemoveOkMessage.Deserialize(dataReader);
					if (this.npc.getItemsToSell() != null) {
						this.npc.removeItemToSell(exchangeBidHouseItemRemoveOkMessage.getSellerId());
					}
					this.info.setExchangeBidSeller(true);
					break;
				case 6304:
					handleGameActionFightModifyEffectsDurationMessage(dataReader);
					break;
				case 6545:
					handleGameActionFightActivateGlyphTrapMessage(dataReader);
					break;
				case 5967:
					MountRidingMessage mountRidingMessage = new MountRidingMessage();
					mountRidingMessage.Deserialize(dataReader);
					this.info.setMountRiding(true);
					this.info.setRiding(mountRidingMessage.isIsRiding());
					break;
				case 5968:
					MountSetMessage mountSetMessage = new MountSetMessage();
					mountSetMessage.Deserialize(dataReader);
					this.dragodinde.setHavingDd(true);
					this.dragodinde.setId((int) mountSetMessage.getMountData().getId());
					this.dragodinde.setEnergy(mountSetMessage.getMountData().getEnergy());
					this.dragodinde.setLevelEquipedDD(mountSetMessage.getMountData().getLevel());
					this.info.setMountSet(true);
					break;
				case 5970:
					MountXpRatioMessage mountXpRatioMessage = new MountXpRatioMessage();
					mountXpRatioMessage.Deserialize(dataReader);
					this.dragodinde.setRatioXp(mountXpRatioMessage.getRatio());
					this.info.setMountxpmsg(true);
					break;
				case 5979:
					ExchangeStartOkMountMessage exchangeStartOkMountMessage = new ExchangeStartOkMountMessage();
					exchangeStartOkMountMessage.Deserialize(dataReader);
					this.dragodinde.setPaddock(exchangeStartOkMountMessage.getPaddockedMountsDescription());
					this.dragodinde.setStable(exchangeStartOkMountMessage.getStabledMountsDescription());
					this.dragodinde.setInStable(true);
					break;
				case 6555:
					this.info.setExchangeDD(true);
					break;
				case 6559:
					this.info.setExchangeDD(true);
					break;
				case 5982:
					this.dragodinde.setHavingDd(false);
					this.info.setExchangeDD(true);
					break;
				case 2970:
					this.info.setExchangeDD(true);
					break;
				case 5689:
					EmoteListMessage emoteListMessage = new EmoteListMessage();
					emoteListMessage.Deserialize(dataReader);
					for (Integer b : emoteListMessage.getEmoteIds()) {
						if (b == 8) {
							this.getDragodinde().setFart(true);
						}
					}
					break;
				case 5683:
					EmotePlayMessage emote = new EmotePlayMessage();
					emote.Deserialize(dataReader);
					if (emote.getActorId() == this.info.getActorId()) {
						this.info.setEmoteLaunched(true);
					}
					break;
				case 5960:
					TeleportDestinationsListMessage destinationsListMessage = new TeleportDestinationsListMessage();
					destinationsListMessage.Deserialize(dataReader);
					if (destinationsListMessage.getTeleporterType() == 1) {
						this.interactive.setZaapiList(destinationsListMessage.getMapIds());
					}
					break;
				case 5830:
					GameActionFightCarryCharacterMessage gameActionFightCarryCharacterMessage = new GameActionFightCarryCharacterMessage();
					gameActionFightCarryCharacterMessage.Deserialize(dataReader);
					iaPacket.gameActionFightCarryCharacter(gameActionFightCarryCharacterMessage);
					break;
				case 6113:
					GameActionFightDispellEffectMessage gameActionFightDispellEffectMessage = new GameActionFightDispellEffectMessage();
					gameActionFightDispellEffectMessage.Deserialize(dataReader);
					iaPacket.gameActionFightDispellEffect(gameActionFightDispellEffectMessage);
					break;
				case 5533:
					GameActionFightDispellMessage gameActionFightDispellMessage = new GameActionFightDispellMessage();
					gameActionFightDispellMessage.Deserialize(dataReader);
					iaPacket.gameActionFightDispell(gameActionFightDispellMessage);
					break;
				case 6176:
					GameActionFightDispellSpellMessage gameActionFightDispellSpellMessage = new GameActionFightDispellSpellMessage();
					gameActionFightDispellSpellMessage.Deserialize(dataReader);
					iaPacket.gameActionFightDispellSpell(gameActionFightDispellSpellMessage);
					break;
				case 5826:
					GameActionFightDropCharacterMessage gameActionFightDropCharacterMessage = new GameActionFightDropCharacterMessage();
					gameActionFightDropCharacterMessage.Deserialize(dataReader);
					iaPacket.gameActionFightDropCharacter(gameActionFightDropCharacterMessage);
					break;
				case 5527:
					GameActionFightExchangePositionsMessage gameActionFightExchangePositionsMessage = new GameActionFightExchangePositionsMessage();
					gameActionFightExchangePositionsMessage.Deserialize(dataReader);
					iaPacket.gameActionFightExchangePositions(gameActionFightExchangePositionsMessage);
					break;
				case 5821:
					GameActionFightInvisibilityMessage gameActionFightInvisibilityMessage = new GameActionFightInvisibilityMessage();
					gameActionFightInvisibilityMessage.Deserialize(dataReader);
					iaPacket.gameActionFightInvisibility(gameActionFightInvisibilityMessage);
					break;
				case 6320:
					GameActionFightInvisibleDetectedMessage gameActionFightInvisibleDetectedMessage = new GameActionFightInvisibleDetectedMessage();
					gameActionFightInvisibleDetectedMessage.Deserialize(dataReader);
					iaPacket.gameActionFightInvisibleDetected(gameActionFightInvisibleDetectedMessage);
					break;
				case 5571:
					GameActionFightKillMessage gameActionFightKillMessage = new GameActionFightKillMessage();
					gameActionFightKillMessage.Deserialize(dataReader);
					iaPacket.gameActionFightKill(gameActionFightKillMessage);
					break;
				case 5526:
					GameActionFightReduceDamagesMessage gameActionFightReduceDamagesMessage = new GameActionFightReduceDamagesMessage();
					gameActionFightReduceDamagesMessage.Deserialize(dataReader);
					iaPacket.gameActionFightReduceDamages(gameActionFightReduceDamagesMessage);
					break;
				case 5530:
					GameActionFightReflectDamagesMessage gameActionFightReflectDamagesMessage = new GameActionFightReflectDamagesMessage();
					gameActionFightReflectDamagesMessage.Deserialize(dataReader);
					iaPacket.gameActionFightReflectDamages(gameActionFightReflectDamagesMessage);
					break;
				case 5531:
					GameActionFightReflectSpellMessage gameActionFightReflectSpellMessage = new GameActionFightReflectSpellMessage();
					gameActionFightReflectSpellMessage.Deserialize(dataReader);
					iaPacket.gameActionFightReflectSpell(gameActionFightReflectSpellMessage);
					break;
				case 6219:
					GameActionFightSpellCooldownVariationMessage gameActionFightSpellCooldownVariationMessage = new GameActionFightSpellCooldownVariationMessage();
					gameActionFightSpellCooldownVariationMessage.Deserialize(dataReader);
					iaPacket.gameActionFightSpellCooldownVariation(gameActionFightSpellCooldownVariationMessage);
					break;
				case 6221:
					GameActionFightSpellImmunityMessage gameActionFightSpellImmunityMessage = new GameActionFightSpellImmunityMessage();
					gameActionFightSpellImmunityMessage.Deserialize(dataReader);
					iaPacket.gameActionFightSpellImmunity(gameActionFightSpellImmunityMessage);
					break;
				case 5535:
					GameActionFightStealKamaMessage gameActionFightStealKamaMessage = new GameActionFightStealKamaMessage();
					gameActionFightStealKamaMessage.Deserialize(dataReader);
					iaPacket.gameActionFightStealKama(gameActionFightStealKamaMessage);
					break;
				case 1004:
					GameActionFightTackledMessage gameActionFightTackledMessage = new GameActionFightTackledMessage();
					gameActionFightTackledMessage.Deserialize(dataReader);
					iaPacket.gameActionFightTackled(gameActionFightTackledMessage);
					break;
				case 5528:
					GameActionFightTeleportOnSameMapMessage gameActionFightTeleportOnSameMapMessage = new GameActionFightTeleportOnSameMapMessage();
					gameActionFightTeleportOnSameMapMessage.Deserialize(dataReader);
					iaPacket.gameActionFightTeleportOnSameMap(gameActionFightTeleportOnSameMapMessage);
					break;
				case 5829:
					GameActionFightThrowCharacterMessage gameActionFightThrowCharacterMessage = new GameActionFightThrowCharacterMessage();
					gameActionFightThrowCharacterMessage.Deserialize(dataReader);
					iaPacket.gameActionFightThrowCharacter(gameActionFightThrowCharacterMessage);
					break;
				case 6147:
					GameActionFightTriggerEffectMessage gameActionFightTriggerEffectMessage = new GameActionFightTriggerEffectMessage();
					gameActionFightTriggerEffectMessage.Deserialize(dataReader);
					iaPacket.gameActionFightTriggerEffect(gameActionFightTriggerEffectMessage);
					break;
				case 5741:
					GameActionFightTriggerGlyphTrapMessage gameActionFightTriggerGlyphTrapMessage = new GameActionFightTriggerGlyphTrapMessage();
					gameActionFightTriggerGlyphTrapMessage.Deserialize(dataReader);
					iaPacket.gameActionFightTriggerGlyphTrap(gameActionFightTriggerGlyphTrapMessage);
					break;
				case 5570:
					GameActionFightUnmarkCellsMessage gameActionFightUnmarkCellsMessage = new GameActionFightUnmarkCellsMessage();
					gameActionFightUnmarkCellsMessage.Deserialize(dataReader);
					iaPacket.gameActionFightUnmarkCells(gameActionFightUnmarkCellsMessage);
					break;
				case 6217:
					GameActionFightVanishMessage gameActionFightVanishMessage = new GameActionFightVanishMessage();
					gameActionFightVanishMessage.Deserialize(dataReader);
					iaPacket.gameActionFightVanish(gameActionFightVanishMessage);
					break;
				case 6700:
					ArenaFighterLeaveMessage arenaFighterLeaveMessage = new ArenaFighterLeaveMessage();
					arenaFighterLeaveMessage.Deserialize(dataReader);
					iaPacket.arenaFighterLeave(arenaFighterLeaveMessage);
					break;
				case 6022:
					ChallengeInfoMessage challengeInfoMessage = new ChallengeInfoMessage();
					challengeInfoMessage.Deserialize(dataReader);
					iaPacket.challengeInfo(challengeInfoMessage);
					break;
				case 6019:
					ChallengeResultMessage challengeResultMessage = new ChallengeResultMessage();
					challengeResultMessage.Deserialize(dataReader);
					iaPacket.challengeResult(challengeResultMessage);
					break;
				case 5613:
					ChallengeTargetsListMessage challengeTargetsListMessage = new ChallengeTargetsListMessage();
					challengeTargetsListMessage.Deserialize(dataReader);
					iaPacket.challengeTargetsList(challengeTargetsListMessage);
					break;
				case 6123:
					ChallengeTargetUpdateMessage challengeTargetUpdateMessage = new ChallengeTargetUpdateMessage();
					challengeTargetUpdateMessage.Deserialize(dataReader);
					iaPacket.challengeTargetUpdate(challengeTargetUpdateMessage);
					break;
				case 6309:
					GameFightRefreshFighterMessage gameFightRefreshFighterMessage = new GameFightRefreshFighterMessage();
					gameFightRefreshFighterMessage.Deserialize(dataReader);
					iaPacket.gameFightRefreshFighter(gameFightRefreshFighterMessage);
					break;
				case 740:
					GameFightHumanReadyStateMessage gameFightHumanReadyStateMessage = new GameFightHumanReadyStateMessage();
					gameFightHumanReadyStateMessage.Deserialize(dataReader);
					iaPacket.gameFightHumanReadyState(gameFightHumanReadyStateMessage);
					break;
				case 721:
					GameFightLeaveMessage gameFightLeaveMessage = new GameFightLeaveMessage();
					gameFightLeaveMessage.Deserialize(dataReader);
					iaPacket.gameFightLeave(gameFightLeaveMessage);
					break;
				case 6239:
					GameFightNewRoundMessage gameFightNewRoundMessage = new GameFightNewRoundMessage();
					gameFightNewRoundMessage.Deserialize(dataReader);
					iaPacket.gameFightNewRound(gameFightNewRoundMessage);
					break;
				case 6490:
					GameFightNewWaveMessage gameFightNewWaveMessage = new GameFightNewWaveMessage();
					gameFightNewWaveMessage.Deserialize(dataReader);
					iaPacket.gameFightNewWave(gameFightNewWaveMessage);
					break;
				case 5927:
					GameFightOptionStateUpdateMessage gameFightOptionStateUpdateMessage = new GameFightOptionStateUpdateMessage();
					gameFightOptionStateUpdateMessage.Deserialize(dataReader);
					iaPacket.gameFightOptionStateUpdate(gameFightOptionStateUpdateMessage);
					break;
				case 707:
					GameFightOptionToggleMessage gameFightOptionToggleMessage = new GameFightOptionToggleMessage();
					gameFightOptionToggleMessage.Deserialize(dataReader);
					iaPacket.gameFightOptionToggle(gameFightOptionToggleMessage);
					break;
				case 6754:
					GameFightPauseMessage gameFightPauseMessage = new GameFightPauseMessage();
					gameFightPauseMessage.Deserialize(dataReader);
					iaPacket.gameFightPause(gameFightPauseMessage);
					break;
				case 6547:
					GameFightPlacementSwapPositionsAcceptMessage gameFightPlacementSwapPositionsAcceptMessage = new GameFightPlacementSwapPositionsAcceptMessage();
					gameFightPlacementSwapPositionsAcceptMessage.Deserialize(dataReader);
					iaPacket.gameFightPlacementSwapPositionsAccept(gameFightPlacementSwapPositionsAcceptMessage);
					break;
				case 6546:
					GameFightPlacementSwapPositionsCancelledMessage gameFightPlacementSwapPositionsCancelledMessage = new GameFightPlacementSwapPositionsCancelledMessage();
					gameFightPlacementSwapPositionsCancelledMessage.Deserialize(dataReader);
					iaPacket.gameFightPlacementSwapPositionsCancelled(gameFightPlacementSwapPositionsCancelledMessage);
					break;
				case 6543:
					GameFightPlacementSwapPositionsCancelMessage gameFightPlacementSwapPositionsCancelMessage = new GameFightPlacementSwapPositionsCancelMessage();
					gameFightPlacementSwapPositionsCancelMessage.Deserialize(dataReader);
					iaPacket.gameFightPlacementSwapPositionsCancel(gameFightPlacementSwapPositionsCancelMessage);
					break;
				case 6544:
					GameFightPlacementSwapPositionsMessage gameFightPlacementSwapPositionsMessage = new GameFightPlacementSwapPositionsMessage();
					gameFightPlacementSwapPositionsMessage.Deserialize(dataReader);
					iaPacket.gameFightPlacementSwapPositions(gameFightPlacementSwapPositionsMessage);
					break;
				case 6542:
					GameFightPlacementSwapPositionsOfferMessage gameFightPlacementSwapPositionsOfferMessage = new GameFightPlacementSwapPositionsOfferMessage();
					gameFightPlacementSwapPositionsOfferMessage.Deserialize(dataReader);
					iaPacket.gameFightPlacementSwapPositionsOffer(gameFightPlacementSwapPositionsOfferMessage);
					break;
				case 711:
					GameFightRemoveTeamMemberMessage gameFightRemoveTeamMemberMessage = new GameFightRemoveTeamMemberMessage();
					gameFightRemoveTeamMemberMessage.Deserialize(dataReader);
					iaPacket.gameFightRemoveTeamMember(gameFightRemoveTeamMemberMessage);
					break;
				case 6067:
					GameFightResumeMessage gameFightResumeMessage = new GameFightResumeMessage();
					gameFightResumeMessage.Deserialize(dataReader);
					iaPacket.gameFightResume(gameFightResumeMessage);
					break;
				case 700:
					this.intelligence = new Intelligence(this);
					this.iaPacket = new IntelligencePacketHandler(this.intelligence);
					this.getInfo().setJoinedFight(true);
					GameFightStartingMessage gameFightStartingMessage = new GameFightStartingMessage();
					gameFightStartingMessage.Deserialize(dataReader);
					iaPacket.gameFightStarting(gameFightStartingMessage);
					break;
				case 712:
					GameFightStartMessage gameFightStartMessage = new GameFightStartMessage();
					gameFightStartMessage.Deserialize(dataReader);
					iaPacket.gameFightStart(gameFightStartMessage);
					break;
				case 718:
					GameFightTurnFinishMessage gameFightTurnFinishMessage = new GameFightTurnFinishMessage();
					gameFightTurnFinishMessage.Deserialize(dataReader);
					iaPacket.gameFightTurnFinish(gameFightTurnFinishMessage);
					break;
				case 716:
					GameFightTurnReadyMessage gameFightTurnReadyMessage = new GameFightTurnReadyMessage();
					gameFightTurnReadyMessage.Deserialize(dataReader);
					iaPacket.gameFightTurnReady(gameFightTurnReadyMessage);
					break;
				case 6307:
					GameFightTurnResumeMessage gameFightTurnResumeMessage = new GameFightTurnResumeMessage();
					gameFightTurnResumeMessage.Deserialize(dataReader);
					iaPacket.gameFightTurnResume(gameFightTurnResumeMessage);
					break;
				case 714:
					GameFightTurnStartMessage gameFightTurnStartMessage = new GameFightTurnStartMessage();
					gameFightTurnStartMessage.Deserialize(dataReader);
					iaPacket.gameFightTurnStart(gameFightTurnStartMessage);
					break;
				case 5572:
					GameFightUpdateTeamMessage gameFightUpdateTeamMessage = new GameFightUpdateTeamMessage();
					gameFightUpdateTeamMessage.Deserialize(dataReader);
					iaPacket.gameFightUpdateTeam(gameFightUpdateTeamMessage);
					break;
				case 6699:
					RefreshCharacterStatsMessage refreshCharacterStatsMessage = new RefreshCharacterStatsMessage();
					refreshCharacterStatsMessage.Deserialize(dataReader);
					iaPacket.refreshCharacterStats(refreshCharacterStatsMessage);
					break;
				case 1200:
					SpellListMessage spellListMessage = new SpellListMessage();
					spellListMessage.Deserialize(dataReader);
					List<Spell> spells = new ArrayList<>();
					for (SpellItem si : spellListMessage.getSpells()) {
						spells.add(GameData.getSpell(si.getSpellId(), si.getSpellLevel()));
					}
					info.setSpells(spells);
					break;
				case 5864:
					GameFightShowFighterMessage gameFightShowFighterMessage = new GameFightShowFighterMessage();
					gameFightShowFighterMessage.Deserialize(dataReader);
					iaPacket.gameFightShowFighter(gameFightShowFighterMessage);
					break;
				case 6322:
					FighterStatsListMessage fighterStatsListMessage = new FighterStatsListMessage();
					fighterStatsListMessage.Deserialize(dataReader);
					iaPacket.fighterStatsList(fighterStatsListMessage);
					break;
			}
			dataReader.bis.close();
		}
		catch (Exception e) {
			System.out.println("Id : " + packet_id);
			e.printStackTrace();
		}
	}

	/**
	 * A glyph has been activated key : activateGlyph Send info : cellId,
	 * actionId, sourceId TODO LYSANDRE
	 * 
	 * @param dataReader
	 */
	private void handleGameActionFightActivateGlyphTrapMessage(DofusDataReader dataReader) {
		GameActionFightActivateGlyphTrapMessage gameActionFightActivateGlyphTrapMessage = new GameActionFightActivateGlyphTrapMessage();
		gameActionFightActivateGlyphTrapMessage.Deserialize(dataReader);
		iaPacket.gameActionFightActivateGlyphTrap(gameActionFightActivateGlyphTrapMessage);
	}

	/**
	 * Duration of the effects has been reduced key : modifyEffectDuration Send
	 * info : sourceId, targetId, actionId, amount TODO LYSANDRE
	 * 
	 * @param dataReader
	 */
	private void handleGameActionFightModifyEffectsDurationMessage(DofusDataReader dataReader) {
		GameActionFightModifyEffectsDurationMessage gameActionFightModifyEffectsDurationMessage = new GameActionFightModifyEffectsDurationMessage();
		gameActionFightModifyEffectsDurationMessage.Deserialize(dataReader);
		iaPacket.gameActionFightModifyEffectsDuration(gameActionFightModifyEffectsDurationMessage);
	}

	/**
	 * Entity lost/gain pa/pm key : pointsVariation Send info : sourceId,
	 * targetId, actionId, amount TODO LYSANDRE
	 * 
	 * @param dataReader
	 */
	private void handleGameActionFightPointsVariationMessage(DofusDataReader dataReader) {
		GameActionFightPointsVariationMessage gameActionFightPointsVariationMessage = new GameActionFightPointsVariationMessage();
		gameActionFightPointsVariationMessage.Deserialize(dataReader);
		iaPacket.gameActionFightPointsVariation(gameActionFightPointsVariationMessage);
	}

	private void handleGameRolePlayPlayerFightFriendlyRequestedMessage(DofusDataReader dataReader) throws Exception {
		GameRolePlayPlayerFightFriendlyRequestedMessage gameRolePlayPlayerFightFriendlyRequestedMessage = new GameRolePlayPlayerFightFriendlyRequestedMessage();
		gameRolePlayPlayerFightFriendlyRequestedMessage.Deserialize(dataReader);
		GameRolePlayPlayerFightFriendlyAnswerMessage gameRolePlayPlayerFightFriendlyAnswerMessage = new GameRolePlayPlayerFightFriendlyAnswerMessage(gameRolePlayPlayerFightFriendlyRequestedMessage.getFightId(), true);
		sendToServer(gameRolePlayPlayerFightFriendlyAnswerMessage, GameRolePlayPlayerFightFriendlyAnswerMessage.ProtocolId, "Accept duel");
	}

	private byte[] WritePacket(DofusDataWriter writer, ByteArrayOutputStream bous, int id) throws Exception {
		byte[] data = bous.toByteArray();
		writer.Clear();
		byte num = ComputeTypeLen(data.length);
		int num1 = SubComputeStaticHeader(id, num);
		writer.writeShort((short) num1);
		writer.writeInt(MessageUtil.InstanceId++);
		switch (num) {
			case 0:
				break;
			case 1:
				writer.writeByte((byte) data.length);
				break;
			case 2:
				writer.writeShort((short) data.length);
				break;
			case 3:
				writer.writeByte((byte) ((data.length >> 16) & 255));
				writer.writeShort((short) (data.length & 65535));
				break;
			default:
				throw new Exception("Packet's length can't be encoded on 4 or more bytes");
		}
		writer.write(data);
		return writer.bous.toByteArray();
	}

	public Dragodinde getDragodinde() {
		return dragodinde;
	}

	public void setDragodinde(Dragodinde dragodinde) {
		this.dragodinde = dragodinde;
	}

	public LatencyFrame getLatencyFrame() {
		return latencyFrame;
	}

	public void setLatencyFrame(LatencyFrame latencyFrame) {
		this.latencyFrame = latencyFrame;
	}

	public Intelligence getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(Intelligence intelligence) {
		this.intelligence = intelligence;
	}
}
