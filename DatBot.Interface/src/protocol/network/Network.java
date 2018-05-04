package protocol.network;

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import game.Info;
import game.combat.Fight;
import game.movement.Movement;
import game.plugin.Bank;
import game.plugin.Dragodinde;
import game.plugin.Hunt;
import game.plugin.Interactive;
import game.plugin.Monsters;
import game.plugin.Npc;
import game.plugin.Stats;
import ia.fight.brain.Game;
import ia.fight.brain.Position;
import ia.fight.map.CreateMap;
import ia.fight.structure.Player;
import io.netty.util.internal.ThreadLocalRandom;
import main.communication.Communication;
import main.communication.DisplayInfo;
import protocol.frames.LatencyFrame;
import protocol.network.messages.connection.HelloConnectMessage;
import protocol.network.messages.connection.IdentificationMessage;
import protocol.network.messages.connection.SelectedServerDataMessage;
import protocol.network.messages.connection.SelectedServerRefusedMessage;
import protocol.network.messages.connection.ServerSelectionMessage;
import protocol.network.messages.connection.ServersListMessage;
import protocol.network.messages.game.actions.GameActionAcknowledgementMessage;
import protocol.network.messages.game.actions.fight.GameActionFightActivateGlyphTrapMessage;
import protocol.network.messages.game.actions.fight.GameActionFightCloseCombatMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDeathMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellableEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDodgePointLossMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifeAndShieldPointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsGainMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightMarkCellsMessage;
import protocol.network.messages.game.actions.fight.GameActionFightModifyEffectsDurationMessage;
import protocol.network.messages.game.actions.fight.GameActionFightNoSpellCastMessage;
import protocol.network.messages.game.actions.fight.GameActionFightPointsVariationMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSlideMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellCastMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSummonMessage;
import protocol.network.messages.game.actions.sequence.SequenceEndMessage;
import protocol.network.messages.game.approach.AuthenticationTicketMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsMessage;
import protocol.network.messages.game.basic.SequenceNumberMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedForceReadyMessage;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.character.stats.CharacterLevelUpMessage;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.context.GameContextCreateRequestMessage;
import protocol.network.messages.game.context.GameContextReadyMessage;
import protocol.network.messages.game.context.GameContextRemoveElementMessage;
import protocol.network.messages.game.context.GameEntitiesDispositionMessage;
import protocol.network.messages.game.context.GameMapMovementMessage;
import protocol.network.messages.game.context.GameMapNoMovementMessage;
import protocol.network.messages.game.context.fight.GameFightJoinMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPossiblePositionsMessage;
import protocol.network.messages.game.context.fight.GameFightSynchronizeMessage;
import protocol.network.messages.game.context.fight.GameFightTurnEndMessage;
import protocol.network.messages.game.context.fight.GameFightTurnListMessage;
import protocol.network.messages.game.context.fight.GameFightTurnReadyMessage;
import protocol.network.messages.game.context.mount.MountRidingMessage;
import protocol.network.messages.game.context.mount.MountSetMessage;
import protocol.network.messages.game.context.mount.MountXpRatioMessage;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.messages.game.context.roleplay.GameRolePlayShowActorMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataInHavenBagMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.messages.game.context.roleplay.MapInformationsRequestMessage;
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
import protocol.network.messages.game.inventory.items.InventoryContentAndPresetMessage;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;
import protocol.network.messages.game.inventory.items.InventoryWeightMessage;
import protocol.network.messages.game.inventory.items.ObjectAddedMessage;
import protocol.network.messages.game.inventory.items.ObjectDeletedMessage;
import protocol.network.messages.game.inventory.items.ObjectQuantityMessage;
import protocol.network.messages.game.inventory.items.ObjectsAddedMessage;
import protocol.network.messages.game.inventory.items.ObjectsDeletedMessage;
import protocol.network.messages.game.inventory.items.ObtainedItemMessage;
import protocol.network.messages.game.inventory.storage.StorageInventoryContentMessage;
import protocol.network.messages.game.inventory.storage.StorageKamasUpdateMessage;
import protocol.network.messages.game.inventory.storage.StorageObjectRemoveMessage;
import protocol.network.messages.game.inventory.storage.StorageObjectUpdateMessage;
import protocol.network.messages.game.inventory.storage.StorageObjectsRemoveMessage;
import protocol.network.messages.game.inventory.storage.StorageObjectsUpdateMessage;
import protocol.network.messages.security.CheckIntegrityMessage;
import protocol.network.messages.security.ClientKeyMessage;
import protocol.network.messages.server.basic.SystemMessageDisplayMessage;
import protocol.network.types.connection.GameServerInformations;
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostStateEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostWeaponDamagesEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellImmunityEffect;
import protocol.network.types.game.actions.fight.GameActionMarkedCell;
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.context.IdentifiedEntityDispositionInformations;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayTreasureHintInformations;
import protocol.network.types.game.context.roleplay.job.JobExperience;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToHint;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToPOI;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.version.VersionExtended;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.util.FlashKeyGenerator;
import protocol.network.util.MessageUtil;
import protocol.network.util.SwitchNameClass;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

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
	public int port = 443;
	private Socket socket;
	boolean packetSent = false;
	long timeout;
	private Stats stats;
	private List<Integer> Ticket;

	public Network(boolean displayPacket, Info info, int botInstance) {
		super(botInstance,displayPacket);
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
			System.out.println(e);		}
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
		try {
			Thread.sleep(4000 + new Random().nextInt(3000));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightCloseCombatMessage gameActionFightCloseCombatMessage = new GameActionFightCloseCombatMessage();
		gameActionFightCloseCombatMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightCloseCombatMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightCloseCombatMessage.getTargetId()));
		jsonObject.put("weaponGeneric", d2iManager.getText(GameData.getWeaponNameId(gameActionFightCloseCombatMessage.getWeaponGenericId())));
		jsonObject.put("critical", gameActionFightCloseCombatMessage.getCritical());
		jsonObject.put("destinationCellId", gameActionFightCloseCombatMessage.getDestinationCellId());
		jsonObject2 = new JSONObject();
		jsonObject2.put("closeCombat", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightDeathMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightDeathMessage gameActionFightDeathMessage = new GameActionFightDeathMessage();
		gameActionFightDeathMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightDeathMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightDeathMessage.getTargetId()));
		jsonObject2 = new JSONObject();
		jsonObject2.put("death", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
		for (int i = 0; i < this.getFight().getMonsters().size(); i++) {
			if (this.getFight().getMonsters().get(i).getContextualId() == gameActionFightDeathMessage.getTargetId()) {
				this.getFight().getMonsters().get(i).setAlive(false);
			}
		}
		for (int i = 0; i < this.getFight().getPlayers().size(); i++) {
			if (this.getFight().getPlayers().get(i).getContextualId() == gameActionFightDeathMessage.getTargetId()) {
				this.getFight().getPlayers().get(i).setAlive(false);
			}
		}
	}

	private void handleGameActionFightDispellableEffectMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightDispellableEffectMessage gameActionFightDispellableEffectMessage = new GameActionFightDispellableEffectMessage();
		gameActionFightDispellableEffectMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightDispellableEffectMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightDispellableEffectMessage.getEffect().getTargetId()));
		jsonObject.put("effectId", gameActionFightDispellableEffectMessage.getEffect().getEffectId());
		jsonObject.put("spellId", gameActionFightDispellableEffectMessage.getEffect().getSpellId());
		jsonObject.put("turnDuration", gameActionFightDispellableEffectMessage.getEffect().getTurnDuration());
		jsonObject.put("dispelable", gameActionFightDispellableEffectMessage.getEffect().getDispelable()); 
		if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostEffect")) {
			if(gameActionFightDispellableEffectMessage.getActionId() == 168){
				jsonObject.put("pa", ((FightTemporaryBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getDelta());
			} else if(gameActionFightDispellableEffectMessage.getActionId() == 169){
				jsonObject.put("pm", ((FightTemporaryBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getDelta());
			} else {
				jsonObject.put("amount", ((FightTemporaryBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getDelta());
			}
		}
		if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostStateEffect")) {
			jsonObject.put("stateId", ((FightTemporaryBoostStateEffect) gameActionFightDispellableEffectMessage.getEffect()).getStateId());
		}
		if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostWeaponDamagesEffect")) {
			jsonObject.put("weaponTypeId", ((FightTemporaryBoostWeaponDamagesEffect) gameActionFightDispellableEffectMessage.getEffect()).getWeaponTypeId());
		}
		if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporarySpellBoostEffect")) {
			jsonObject.put("boostedSpellId", ((FightTemporarySpellBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getBoostedSpellId());

		}
		if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporarySpellImmunityEffect")) {
			jsonObject.put("immuneSpellId", ((FightTemporarySpellImmunityEffect) gameActionFightDispellableEffectMessage.getEffect()).getImmuneSpellId());

		}
		jsonObject2 = new JSONObject();
		jsonObject2.put("dispellableEffect", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightDodgePointLossMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightDodgePointLossMessage dodgePointLossMessage = new GameActionFightDodgePointLossMessage();
		dodgePointLossMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(dodgePointLossMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(dodgePointLossMessage.getTargetId()));
		jsonObject.put("amount", dodgePointLossMessage.getAmount());
		jsonObject2 = new JSONObject();
		jsonObject2.put("dodgePointLoss", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightLifeAndShieldPointsLostMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightLifeAndShieldPointsLostMessage gameActionFightLifeAndShieldPointsLostMessage = new GameActionFightLifeAndShieldPointsLostMessage();
		gameActionFightLifeAndShieldPointsLostMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightLifeAndShieldPointsLostMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightLifeAndShieldPointsLostMessage.getTargetId()));
		jsonObject.put("shieldLoss", gameActionFightLifeAndShieldPointsLostMessage.getShieldLoss());
		jsonObject.put("lpLoss", gameActionFightLifeAndShieldPointsLostMessage.getLoss());
		jsonObject.put("lpMaxLoss", gameActionFightLifeAndShieldPointsLostMessage.getPermanentDamages());
		jsonObject2 = new JSONObject();
		jsonObject2.put("shieldLpLoss", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightLifePointsGainMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightLifePointsGainMessage gameActionFightLifePointsGainMessage = new GameActionFightLifePointsGainMessage();
		gameActionFightLifePointsGainMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightLifePointsGainMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightLifePointsGainMessage.getTargetId()));
		jsonObject.put("lpGain", gameActionFightLifePointsGainMessage.getDelta());
		jsonObject2 = new JSONObject();
		jsonObject2.put("lifePointsGain", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightLifePointsLostMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightLifePointsLostMessage gameActionFightLifePointsLostMessage = new GameActionFightLifePointsLostMessage();
		gameActionFightLifePointsLostMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightLifePointsLostMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightLifePointsLostMessage.getTargetId()));
		jsonObject.put("lpLost", gameActionFightLifePointsLostMessage.getLoss());
		jsonObject.put("lpMaxLost", gameActionFightLifePointsLostMessage.getPermanentDamages());
		jsonObject2 = new JSONObject();
		jsonObject2.put("lifePointsLost", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightMarkCellsMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightMarkCellsMessage gameActionFightMarkCellsMessage = new GameActionFightMarkCellsMessage();
		gameActionFightMarkCellsMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightMarkCellsMessage.getSourceId()));
		jsonObject.put("markSpellId", gameActionFightMarkCellsMessage.getMark().getMarkSpellId());
		jsonObject.put("markImpactCellId", gameActionFightMarkCellsMessage.getMark().getMarkimpactCell());
		JSONArray jsonArray = new JSONArray();
		for (GameActionMarkedCell object : gameActionFightMarkCellsMessage.getMark().getCells()) {
			JSONObject jsonCells = new JSONObject();
			jsonCells.put("cellId", object.getCellId());
			jsonCells.put("zoneSize", object.getZoneSize());
			jsonArray.add(jsonCells);
		}
		jsonObject.put("cells", jsonArray);
		jsonObject2 = new JSONObject();
		jsonObject2.put("markCells", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightSlideMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightSlideMessage gameActionFightSlideMessage = new GameActionFightSlideMessage();
		gameActionFightSlideMessage.Deserialize(dataReader);
		if(gameActionFightSlideMessage.getTargetId() == this.info.getActorId()){
			this.info.setCellId(gameActionFightSlideMessage.getEndCellId());
		}
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightSlideMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightSlideMessage.getTargetId()));
		
		int startCellId = gameActionFightSlideMessage.getStartCellId();
		int startX = CreateMap.rotate(new int[] { startCellId % 14, startCellId / 14 })[0];
		int startY = CreateMap.rotate(new int[] { startCellId % 14, startCellId / 14 })[1];
		JSONObject startCell = new JSONObject();
		startCell.put("x", startX);
		startCell.put("y", startY);
		
		jsonObject.put("startCell", startCell);
		
		int endCellId = gameActionFightSlideMessage.getEndCellId();
		int endX = CreateMap.rotate(new int[] { endCellId % 14, endCellId / 14 })[0];
		int endY = CreateMap.rotate(new int[] { endCellId % 14, endCellId / 14 })[1];
		JSONObject endCell = new JSONObject();
		endCell.put("x", endX);
		endCell.put("y", endY);
		
		jsonObject.put("endCell", endCell);
		
		jsonObject2 = new JSONObject();
		jsonObject2.put("slide", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
	}

	private void handleGameActionFightSpellCastMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		JSONObject jsonObject2;
		GameActionFightSpellCastMessage gameActionFightSpellCastMessage = new GameActionFightSpellCastMessage();
		gameActionFightSpellCastMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("spellId", gameActionFightSpellCastMessage.getSpellId());
		jsonObject.put("targetId", getFight().getId(gameActionFightSpellCastMessage.getTargetId()));

		int cellId = gameActionFightSpellCastMessage.getDestinationCellId();
		int x = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[0];
		int y = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[1];

		jsonObject.put("x", x);
		jsonObject.put("y", y);
		jsonObject.put("critical", gameActionFightSpellCastMessage.getCritical());
		jsonObject.put("sourceId", getFight().getId(gameActionFightSpellCastMessage.getSourceId()));
		jsonObject2 = new JSONObject();
		jsonObject2.put("spellCast", jsonObject);
		getFight().getSpellJson().add(jsonObject2);
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

	private void handleGameFightJoinMessage(DofusDataReader dataReader) throws InterruptedException {
		GameFightJoinMessage gameFightJoinMessage = new GameFightJoinMessage();
		gameFightJoinMessage.Deserialize(dataReader);
		info.setJoinedFight(true);
		info.setTurn(false);
		info.setInitFight(false);
		Communication.sendToModel(String.valueOf(getBotInstance()), String.valueOf(-1), "m", "info", "combat", new Object[] { "\"start\"" });
		JSONObject mapJSONObject = new JSONObject();
		mapJSONObject.put("mapID", (int) info.getMapId());
		mapJSONObject.put("name", info.getName());
		JSONArray tempArr = new JSONArray();
		tempArr.add(mapJSONObject);
		getFight().sendToFightAlgo("startfight", tempArr);
	}
	
	private void handleGameEntitiesDispositionMessage(DofusDataReader dataReader) throws Exception {
		getFight().gameEntitiesDispositionMessage = new GameEntitiesDispositionMessage();
		getFight().gameEntitiesDispositionMessage.Deserialize(dataReader);
		this.fight.fightToggleAdvancedRdy(false);
		this.fight.incrementMoving();
		
		List<IdentifiedEntityDispositionInformations> identifiedPositions = getFight().gameEntitiesDispositionMessage.getDispositions();
		ArrayList<Position> positions = new ArrayList<>();
		for(int i = 0; i < identifiedPositions.size(); i++) {
			if(identifiedPositions.get(i).getId() == this.info.getActorId()){
				this.info.setCellId(identifiedPositions.get(i).getCellId());
			}
			int x = CreateMap.rotate(new int[] { identifiedPositions.get(i).getCellId() % 14, identifiedPositions.get(i).getCellId() / 14 })[0];
			int y = CreateMap.rotate(new int[] { identifiedPositions.get(i).getCellId() % 14, identifiedPositions.get(i).getCellId() / 14 })[1];
			positions.add(new Position(x, y));
		}
		
		JSONArray arr = new JSONArray();
		JSONObject posJSON = new JSONObject();
		
		posJSON.put("positions", positions);
		arr.add(posJSON);
		
		String newPosition = getFight().sendToFightAlgo("startPosAltered", arr);
		int cellID = Fight.rotateToCellId(Integer.parseInt(newPosition.split(",")[0]), Integer.parseInt(newPosition.split(",")[1]));

		if(cellID != this.info.getCellId()){
			if(this.fight.isRdy()) {
				getFight().fightNotReady();
				this.fight.setRdy(false);
			}
			
			Game.setTimeout(() -> {
				try{
					if(this.fight.isMoving() <= 1) {
						System.out.println(this.fight.isMoving());
						GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellID);
						sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Fight placement to " + cellID);
						this.fight.setMovingToZero();
					}else {
						this.fight.decrementMoving();
					}
					
				}catch(Exception e) {e.printStackTrace();}
			}, 500);
		}
		if(!this.fight.isRdy()){
			this.fight.fightToggleAdvancedRdy(true);
			Game.setTimeout(() -> {
				try {
					if(this.fight.isAdvancedRdy()) {
						if(!this.fight.isRdy()) {
							getFight().fightReady();
							this.fight.setRdy(true);
						}
						
						this.fight.setMovingToZero();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}, 1000);
		}
	}

	private void handleGameFightPlacementPossiblePositionsMessage(DofusDataReader dataReader) {
		getFight().gameFightPlacementPossiblePositionsMessage = new GameFightPlacementPossiblePositionsMessage();
		getFight().gameFightPlacementPossiblePositionsMessage.Deserialize(dataReader);
		
		List<Integer> challengerCells = getFight().gameFightPlacementPossiblePositionsMessage.getPositionsForChallengers();
		List<Integer> defenderCells = getFight().gameFightPlacementPossiblePositionsMessage.getPositionsForDefenders();
		
		
		ArrayList<Position> challengerPositions = new ArrayList<>();
		
		for(int i = 0; i < challengerCells.size(); i++) {
			int x = CreateMap.rotate(new int[] { challengerCells.get(i) % 14, challengerCells.get(i) / 14 })[0];
			int y = CreateMap.rotate(new int[] { challengerCells.get(i) % 14, challengerCells.get(i) / 14 })[1];
			challengerPositions.add(new Position(x, y));
		}
		
		ArrayList<Position> defenderPositions = new ArrayList<>();
		
		for(int i = 0; i < defenderCells.size(); i++) {
			int x = CreateMap.rotate(new int[] { defenderCells.get(i) % 14, defenderCells.get(i) / 14 })[0];
			int y = CreateMap.rotate(new int[] { defenderCells.get(i) % 14, defenderCells.get(i) / 14 })[1];
			defenderPositions.add(new Position(x, y));
		}
		
		JSONArray arr = new JSONArray();
		JSONObject posJSON = new JSONObject();
		
		posJSON.put("challengerPositions", challengerPositions);
		posJSON.put("defenderPositions", defenderPositions);
		
		posJSON.put("team", getFight().gameFightPlacementPossiblePositionsMessage.getTeamNumber());
		arr.add(posJSON);
		
		getFight().sendToFightAlgo("fightPositionInitialization", arr);
	}

	private void handleGameFightSynchronizeMessage(DofusDataReader dataReader) {
		getFight().setGameFightSynchronizeMessage(new GameFightSynchronizeMessage());
		getFight().getGameFightSynchronizeMessage().Deserialize(dataReader);
		if (!info.isInitFight()) {
			JSONObject startFight = new JSONObject();
			startFight.put("misc", getFight().init());
			
			ArrayList<Player> playingEntities = getFight().getEntities();
			Player bot = null;
			
			for(int i = 0; i < playingEntities.size(); i++) {
				if(playingEntities.get(i).getName().equals(info.getName())) {
					bot = playingEntities.get(i);
				}
			}
			
			CharacterCharacteristicsInformations botStats = stats.getStats().getStats();
			
			int totalAgility = botStats.getAgility().getTotal();
			int totalIntelligence = botStats.getIntelligence().getTotal();
			int totalStrength = botStats.getStrength().getTotal();
			int totalLuck = botStats.getChance().getTotal();
			int[] stats = new int[] {totalAgility, totalStrength, totalLuck, totalIntelligence, totalStrength};
			int[] elementaryDamage = new int[] {botStats.getAirDamageBonus().getTotal(), botStats.getEarthDamageBonus().getTotal(), botStats.getWaterDamageBonus().getTotal(), botStats.getFireDamageBonus().getTotal(), botStats.getNeutralDamageBonus().getTotal()};
			
			bot.setInitiative(botStats.getInitiative().getTotal());
			bot.setProspection(botStats.getProspecting().getTotal());
			bot.setRange(botStats.getRange().getTotal());
			bot.setCriticalChance(botStats.getCriticalHit().getTotal());
			bot.setHeals(botStats.getHealBonus().getTotal());
			bot.setFixedDamages(botStats.getAllDamagesBonus().getTotal());
			bot.setPower(botStats.getDamagesBonusPercent().getTotal());
			bot.setCriticalDamage(botStats.getCriticalDamageBonus().getTotal());
			bot.setElementaryDamage(elementaryDamage);
			bot.setDamageReturn(botStats.getReflect().getTotal());
			bot.setWeaponPower(botStats.getWeaponDamagesBonusPercent().getTotal());
			bot.setTrapDamage(botStats.getTrapBonus().getTotal());
			bot.setTrapPower(botStats.getTrapBonusPercent().getTotal());
			bot.setPushbackDamage(botStats.getPushDamageBonus().getTotal());
			bot.setSpellPowerPrcnt(botStats.getSpellDamageDonePercent().getTotal());
			bot.setWeaponPowerPrcnt(botStats.getWeaponDamageDonePercent().getTotal());
			bot.setDistancePowerPrcnt(botStats.getRangedDamageDonePercent().getTotal());
			bot.setCloseCombatPowerPrcnt(botStats.getMeleeDamageDonePercent().getTotal());
			bot.setStats(stats);
			
			startFight.put("entities", playingEntities);

			JSONArray arr2 = new JSONArray();
			arr2.add(startFight);
			getFight().sendToFightAlgo("s", arr2);
			info.setInitFight(true);
		}
	}
	
	private void handleGameActionFightSummonMessage(DofusDataReader dataReader) {
		GameActionFightSummonMessage gameActionFightSummonMessage = new GameActionFightSummonMessage();
		gameActionFightSummonMessage.Deserialize(dataReader);
		
		for(int i = 0; i < gameActionFightSummonMessage.getSummons().size(); i++) {
			System.out.println();
			System.out.println();
			System.out.println(gameActionFightSummonMessage.getSummons().get(i));
			System.out.println();
		}
		
		for(int i = 0; i < getFight().getGameFightSynchronizeMessage().getFighters().size(); i++) {
			GameFightCharacterInformations p = (GameFightCharacterInformations) getFight().getGameFightSynchronizeMessage().getFighters().get(i);
			double id = p.getContextualId();
			/*
			if(id == gameActionFightSummonMessage.getSourceId()) {
				p.setSummons(gameActionFightSummonMessage.getSummons());
			}
			*/
		}
		
	}

	private void handleGameFightTurnEndMessage(DofusDataReader dataReader) {
		GameFightTurnEndMessage gameFightTurnEndMessage = new GameFightTurnEndMessage();
		gameFightTurnEndMessage.Deserialize(dataReader);
		if (gameFightTurnEndMessage.getId() == info.getActorId()) {
			info.setTurn(false);
		}
		for (int i = 0; i < this.getFight().getMonsters().size(); i++) {
			if (this.getFight().getMonsters().get(i).getContextualId() == gameFightTurnEndMessage.getId()) {
				if (this.getFight().getMonsters().get(i).isAlive()) {
					JSONObject passTurn = new JSONObject();
					passTurn.put("id", getFight().getId(gameFightTurnEndMessage.getId()));
					JSONArray arr = new JSONArray();
					arr.add(passTurn);
					getFight().sendToFightAlgo("p", arr);
				}
			}
		}
		for (int i = 0; i < this.getFight().getPlayers().size(); i++) {
			if (this.getFight().getPlayers().get(i).getContextualId() == gameFightTurnEndMessage.getId()) {
				if (this.getFight().getPlayers().get(i).isAlive()) {
					JSONObject passTurn = new JSONObject();
					passTurn.put("id", getFight().getId(gameFightTurnEndMessage.getId()));
					JSONArray arr = new JSONArray();
					arr.add(passTurn);
					getFight().sendToFightAlgo("p", arr);
				}
			}
		}
	}

	private void handleGameFightTurnListMessage(DofusDataReader dataReader) {
		GameFightTurnListMessage gameFightTurnListMessage = new GameFightTurnListMessage();
		gameFightTurnListMessage.Deserialize(dataReader);
		if (!info.isInitFight()) {
			getFight().turnListId = gameFightTurnListMessage.getIds();
		}else {
			/*
			List<Double> IDs = gameFightTurnListMessage.getIds();
			JSONArray arr = new JSONArray();
			for(int i = 0; i < getFight().getGameFightSynchronizeMessage().getFighters().size(); i++) {
				GameFightCharacterInformations p = (GameFightCharacterInformations) getFight().getGameFightSynchronizeMessage().getFighters().get(i);
				JSONObject obj = new JSONObject();
				obj.put("name", p.getName());
				obj.put("id", p.getContextualId());
				obj.put("otherId", i);
				JSONArray summons = new JSONArray();
				
				if(p.getSummons() != null) {
					for(int j = 0; j < p.getSummons().size(); j++) {
						JSONObject summon = new JSONObject();
						System.out.println();
						System.out.println(p.getSummons().get(j));
						summon.put("id", p.getSummons().get(j).getContextualId());
						
						int cell = p.getSummons().get(j).getDisposition().getCellId();
						int x = CreateMap.rotate(new int[] { cell % 14, cell / 14 })[0];
						int y = CreateMap.rotate(new int[] { cell % 14, cell / 14 })[1];
						
						summon.put("x", x);
						summon.put("y", y);
						
						summons.add(summon);
					}
				}
				
				
				obj.put("summons", summons);
				
				arr.add(obj);
			}
			
			getFight().sendToFightAlgo("updateEntities", arr);
			*/
		}
	}

	private void handleGameMapMovementMessage(DofusDataReader dataReader) {
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
		if (info.isJoinedFight()) {
			for (int i = 0; i < this.getFight().getMonsters().size(); i++) {
				if (this.getFight().getMonsters().get(i).getContextualId() == gameMapMovementMessage.getActorId()) {
					this.getFight().getMonsters().get(i).getDisposition().setCellId(gameMapMovementMessage.getKeyMovements().get(gameMapMovementMessage.getKeyMovements().size() - 1));
				}
			}
			int cellId = gameMapMovementMessage.getKeyMovements().get(gameMapMovementMessage.getKeyMovements().size() - 1);
			int x = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[0];
			int y = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[1];

			JSONObject object = new JSONObject();
			object.put("id", getFight().getId(gameMapMovementMessage.getActorId()));
			object.put("x", x);
			object.put("y", y);
			JSONArray arr = new JSONArray();
			arr.add(object);

			getFight().sendToFightAlgo("m", arr);
		}
	}

	private void handleGameRolePlayArenaSwitchToFightServerMessage(DofusDataReader dataReader) throws IOException, UnknownHostException {
		connectionToKoli = true;
		GameRolePlayArenaSwitchToFightServerMessage arenaSwitchToFightServerMessage = new GameRolePlayArenaSwitchToFightServerMessage();
		arenaSwitchToFightServerMessage.Deserialize(dataReader);
		Ticket = arenaSwitchToFightServerMessage.getTicket();
		this.socket.close();
		System.out.println("New connection to server for arena");
		this.socket = new Socket(arenaSwitchToFightServerMessage.getAddress(), 5555);
	}

	private void handleGameRolePlayArenaSwitchToGameServerMessage(DofusDataReader dataReader) throws IOException, UnknownHostException {
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

	private void handleHelloConnectMessage(DofusDataReader dataReader) throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, Exception {
		HelloConnectMessage hello = new HelloConnectMessage();
		hello.Deserialize(dataReader);
		byte[] key = new byte[hello.getKey().size()];
		for (int i = 0; i < hello.getKey().size(); i++) {
			key[i] = hello.getKey().get(i).byteValue();
		}
		VersionExtended versionExtended = new VersionExtended(2, 46, 14, 0, 0, 0, 1, 1);
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
			/*for (InteractiveElement interactiveElement : this.interactive.getInteractiveElements()) {
				if(interactiveElement.getEnabledSkills().size() > 0 && interactiveElement.isOnCurrentMap())
					System.out.println(interactiveElement + " cell : " + this.interactive.getInteractive(interactiveElement.getEnabledSkills().get(0).getSkillId())[0]);
			}*/
//			append("Map : [" + info.getCoords()[0] + ";" + info.getCoords()[1] + "]");
//			append("CellId : " + info.getCellId());
			info.setWaitForMov(true);
			info.setConnected(true);
			info.setNewMap(true);
		}
	}

	private void handleMapRequestMessage(DofusDataReader dataReader) throws Exception, IOException {
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

	private void handleSelectedServerDataMessage(DofusDataReader dataReader) throws IOException, UnknownHostException {
		SelectedServerDataMessage selectServer = new SelectedServerDataMessage();
		selectServer.Deserialize(dataReader);
		Ticket = selectServer.getTicket();
		this.socket.close();
		this.socket = new Socket(selectServer.getAddress(), selectServer.getPort());
	}

	private void handleSequenceEndMessage(DofusDataReader dataReader) throws InterruptedException, Exception {
		SequenceEndMessage sequenceEndMessage = new SequenceEndMessage();
		sequenceEndMessage.Deserialize(dataReader);
		if (sequenceEndMessage.getAuthorId() == info.getActorId()) {
			Thread.sleep(1500);
			sendToServer(new GameActionAcknowledgementMessage(true, sequenceEndMessage.getActionId()), GameActionAcknowledgementMessage.ProtocolId, "Game Action Acknowledgement Message");
			this.getInfo().setAcknowledged(true);
		}
		if (!getFight().getSpellJson().isEmpty()) {
			/**
			 * FOR LYSANDRE Use getFight().getSpellJson() in a function
			 */
			getFight().sendToFightAlgo("c", getFight().getSpellJson());
		}
		if (info.isTurn()) {
			getFight().fightTurn();
		}
	}

	private void handleSequenceNumberMessage() throws Exception {
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(latencyFrame.Sequence++);
		sendToServer(sequenceNumberMessage, SequenceNumberMessage.ProtocolId, "Sequence number");
	}

	private void handleServersListMessage(DofusDataReader dataReader) throws Exception {
		ServersListMessage servers = new ServersListMessage();
		servers.Deserialize(dataReader);
		int serverId = -1;
		
		for (GameServerInformations server : servers.getServers())
		{
			if(GameData.getNameServer(server.getId()).equals(this.info.getServer())){
				serverId = server.getId();
				if(!(server.getStatus() == 3 || server.getStatus() == 0)){
					System.out.println("Server down");
					this.socket.close();
					return;
				}
			}
		}
		if(serverId == -1){
			throw new Exception("Wrong server name");
		}
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
		} else if(treasureHuntDigRequestAnswerMessage.getResult() == 2) {
			this.getInfo().setStepSuccess(true);
			this.hunt.setRdyToFight(true);
		} else {
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
		if(treasureHuntMessage.getFlags().size() == 0){
			this.hunt.setStartMapCoords(GameData.getCoordMap((int) treasureHuntMessage.getStartMapId()));
		} else {
			this.hunt.setStartMapCoords(GameData.getCoordMap((int) treasureHuntMessage.getFlags().get(treasureHuntMessage.getFlags().size() - 1 ).getMapId()));
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
			if(socket == null){
				return;
			}
			while (!this.socket.isClosed()) {
				Thread.sleep(400);
				if(!this.socket.isClosed()){
					InputStream data = this.socket.getInputStream();
					int available = data.available();
					byte[] buffer = new byte[available];
					if(System.currentTimeMillis() - timeout > 15000 && packetSent){
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
			if(this.info.isPrintDc() && this.info.isConnected())
				Communication.sendToModel(String.valueOf(getBotInstance()), String.valueOf(-1), "m", "info", "disconnect", new Object[] { "True" });
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
	private void TreatPacket(int packet_id, byte[] packet_content) throws Exception {
		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(packet_content));
		SwitchNameClass name = new SwitchNameClass(packet_id);
		appendLog("[" + packet_id + "]\tTaille : " + packet_content.length + "  -  " + name.name);
		JSONObject jsonObject = null;
		JSONObject jsonObject2;
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
			case 6253:
				HandleRawDataMessage();
				break;
			case 6267:
				HandleCharacterListRequestMessage();
				break;
			case 151:
				handleCharacterSelectionMessage(dataReader);
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
				info.setJoinedFight(false);
				info.setTurn(false);
				getFight().sendToFightAlgo("endFight", null);
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
				getFight().fightTurn();
				break;
			case 955:
				getFight().setSpellJson(new JSONArray());
				break;
			case 5825:
				handleGameActionFightSummonMessage(dataReader);
				break;
			/**
			 * LYSANDRE FIGHT // case 1010 to case 6310 Each sequence can
			 * contain multiple jsonObject Each case contains one jsonObject
			 * jsonObject : key of the variable jsonObject : key of the packet
			 * sourceId : caster targetId : target
			 */
			case 1010:
				handleGameActionFightSpellCastMessage(dataReader);
				break;
			case 6132:
				GameActionFightNoSpellCastMessage gameActionFightNoSpellCastMessage = new GameActionFightNoSpellCastMessage();
				super.debug.println(getTiming() + "Can't send spell " + gameActionFightNoSpellCastMessage.getSpellLevelId());
				break;
			case 6312:
				handleGameActionFightLifePointsLostMessage(dataReader);
				break;
			case 5828:
				handleGameActionFightDodgePointLossMessage(dataReader);
				break;
			/**
			 * Depending on the packet received it will give a JsonObject with
			 * diferent key the first are in common for every packet
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
				if(this.npc.getItemsToSell() != null){
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
					if(b==8){
						this.getDragodinde().setFart(true);
					}
				}
				break;
			case 5683:
				EmotePlayMessage emote = new EmotePlayMessage();
				emote.Deserialize(dataReader);
				if(emote.getActorId() == this.info.getActorId()){
					this.info.setEmoteLaunched(true);
				}
				break;
			case 5960:
				TeleportDestinationsListMessage destinationsListMessage = new TeleportDestinationsListMessage();
				destinationsListMessage.Deserialize(dataReader);
				if(destinationsListMessage.getTeleporterType() == 1){
					this.interactive.setZaapiList(destinationsListMessage.getMapIds());
				}
				break;
		}
		packet_content = null;
		dataReader.bis.close();
		return;
	}

	/**
	 * A glyph has been activated
	 * key : activateGlyph
	 * Send info : cellId, actionId, sourceId
	 * TODO LYSANDRE
	 * @param dataReader
	 */
	private void handleGameActionFightActivateGlyphTrapMessage(DofusDataReader dataReader)
	{
		GameActionFightActivateGlyphTrapMessage gameActionFightActivateGlyphTrapMessage = new GameActionFightActivateGlyphTrapMessage();
		gameActionFightActivateGlyphTrapMessage.Deserialize(dataReader);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cellId", gameActionFightActivateGlyphTrapMessage.getMarkId());
		jsonObject.put("actionId", gameActionFightActivateGlyphTrapMessage.getActionId());
		jsonObject.put("sourceId", getFight().getId(gameActionFightActivateGlyphTrapMessage.getSourceId()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("activateGlyph", jsonObject);
		JSONArray arr = new JSONArray();
		arr.add(jsonObject2);
		getFight().sendToFightAlgo("i", arr);
	}

	/**
	 * Duration of the effects has been reduced
	 * key : modifyEffectDuration
	 * Send info : sourceId, targetId, actionId, amount
	 * TODO LYSANDRE
	 * @param dataReader
	 */
	private void handleGameActionFightModifyEffectsDurationMessage(DofusDataReader dataReader) {
		GameActionFightModifyEffectsDurationMessage gameActionFightModifyEffectsDurationMessage = new GameActionFightModifyEffectsDurationMessage();
		gameActionFightModifyEffectsDurationMessage.Deserialize(dataReader);
		JSONObject jsonObject;
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightModifyEffectsDurationMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightModifyEffectsDurationMessage.getTargetId()));
		jsonObject.put("actionId", gameActionFightModifyEffectsDurationMessage.getActionId());
		jsonObject.put("amount", gameActionFightModifyEffectsDurationMessage.getDelta());
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("modifyEffectDuration", jsonObject);
		JSONArray arr = new JSONArray();
		arr.add(jsonObject2);
		getFight().sendToFightAlgo("i", arr);
	}

	/**
	 * Entity lost/gain pa/pm
	 * key : pointsVariation
	 * Send info : sourceId, targetId, actionId, amount
	 * TODO LYSANDRE
	 * @param dataReader
	 */
	private void handleGameActionFightPointsVariationMessage(DofusDataReader dataReader) {
		JSONObject jsonObject;
		GameActionFightPointsVariationMessage gameActionFightPointsVariationMessage = new GameActionFightPointsVariationMessage();
		gameActionFightPointsVariationMessage.Deserialize(dataReader);
		jsonObject = new JSONObject();
		jsonObject.put("sourceId", getFight().getId(gameActionFightPointsVariationMessage.getSourceId()));
		jsonObject.put("targetId", getFight().getId(gameActionFightPointsVariationMessage.getTargetId()));
		if(gameActionFightPointsVariationMessage.getActionId() == 102){
			jsonObject.put("pa", gameActionFightPointsVariationMessage.getDelta());
		} else if (gameActionFightPointsVariationMessage.getActionId() == 129){
			jsonObject.put("pm", gameActionFightPointsVariationMessage.getDelta());
		} else if (gameActionFightPointsVariationMessage.getActionId() == 127){
            jsonObject.put("pm", gameActionFightPointsVariationMessage.getDelta());
        } else if (gameActionFightPointsVariationMessage.getActionId() == 78){
            jsonObject.put("pm", gameActionFightPointsVariationMessage.getDelta());
        }
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("pointsVariation", jsonObject);
		JSONArray arr = new JSONArray();
		arr.add(jsonObject2);
		getFight().sendToFightAlgo("i", arr);
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

	public Dragodinde getDragodinde()
	{
		return dragodinde;
	}

	public void setDragodinde(Dragodinde dragodinde)
	{
		this.dragodinde = dragodinde;
	}

	public LatencyFrame getLatencyFrame() {
		return latencyFrame;
	}

	public void setLatencyFrame(LatencyFrame latencyFrame) {
		this.latencyFrame = latencyFrame;
	}
}
