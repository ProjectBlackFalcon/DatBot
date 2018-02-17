package protocol.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import game.Entity;
import game.Info;
import game.Servers;
import game.combat.Fight;
import game.movement.CellMovement;
import game.movement.Movement;
import game.plugin.Bank;
import game.plugin.Hunt;
import game.plugin.Interactive;
import game.plugin.Monsters;
import game.plugin.Npc;
import game.plugin.Stats;
import ia.fight.map.CreateMap;
import io.netty.util.internal.ThreadLocalRandom;
import main.communication.Communication;
import main.communication.DisplayInfo;
import main.communication.ModelConnexion;
import protocol.frames.LatencyFrame;
import protocol.network.messages.connection.HelloConnectMessage;
import protocol.network.messages.connection.IdentificationFailedMessage;
import protocol.network.messages.connection.IdentificationMessage;
import protocol.network.messages.connection.IdentificationSuccessMessage;
import protocol.network.messages.connection.SelectedServerDataMessage;
import protocol.network.messages.connection.ServerSelectionMessage;
import protocol.network.messages.connection.ServersListMessage;
import protocol.network.messages.game.actions.GameActionAcknowledgementMessage;
import protocol.network.messages.game.actions.sequence.SequenceEndMessage;
import protocol.network.messages.game.approach.AuthenticationTicketMessage;
import protocol.network.messages.game.actions.fight.GameActionFightCloseCombatMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDeathMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDispellableEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightDodgePointLossMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifeAndShieldPointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsGainMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightMarkCellsMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSlideMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellCastMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSummonMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsMessage;
import protocol.network.messages.game.basic.SequenceNumberMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedForceReadyMessage;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.character.stats.CharacterLevelUpMessage;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.chat.ChatServerMessage;
import protocol.network.messages.game.context.GameContextCreateRequestMessage;
import protocol.network.messages.game.context.GameContextReadyMessage;
import protocol.network.messages.game.context.GameContextRemoveElementMessage;
import protocol.network.messages.game.context.GameEntitiesDispositionMessage;
import protocol.network.messages.game.context.GameMapMovementMessage;
import protocol.network.messages.game.context.fight.GameFightJoinMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPossiblePositionsMessage;
import protocol.network.messages.game.context.fight.GameFightSynchronizeMessage;
import protocol.network.messages.game.context.fight.GameFightTurnEndMessage;
import protocol.network.messages.game.context.fight.GameFightTurnListMessage;
import protocol.network.messages.game.context.fight.GameFightTurnReadyMessage;
import protocol.network.messages.game.context.fight.character.GameFightShowFighterMessage;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.messages.game.context.roleplay.GameRolePlayShowActorMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataInHavenBagMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.messages.game.context.roleplay.MapInformationsRequestMessage;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayAttackMonsterRequestMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToFightServerMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToGameServerMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceMultiUpdateMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceUpdateMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogQuestionMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestAnswerMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntFinishedMessage;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntMessage;
import protocol.network.messages.game.interactive.InteractiveElementUpdatedMessage;
import protocol.network.messages.game.interactive.StatedElementUpdatedMessage;
import protocol.network.messages.game.interactive.zaap.ZaapListMessage;
import protocol.network.messages.game.inventory.KamasUpdateMessage;
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
import protocol.network.messages.handshake.ProtocolRequired;
import protocol.network.messages.queues.LoginQueueStatusMessage;
import protocol.network.messages.security.CheckIntegrityMessage;
import protocol.network.messages.security.ClientKeyMessage;
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostStateEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostWeaponDamagesEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellImmunityEffect;
import protocol.network.types.game.actions.fight.GameActionMarkedCell;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
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

public class Network extends DisplayInfo implements Runnable {

	private Socket socket;
	private String ip = "213.248.126.40";
	private int port = 5555;
	private ModelConnexion modelConnexion;
	private Message message;
	private List<Integer> Ticket;
	// Big packet split
	private int bigPacketLengthToFull;// Length needed to finish the packet
	private int bigPacketId;
	private byte[] bigPacketData;

	/*
	 * Plugin variable
	 */
	public boolean connectionToKoli = false;
	private Fight fight;
	private Interactive interactive;
	private Bank bank;
	private Npc npc;
	private Movement movement;
	private Stats stats;
	private Info info;
	private Monsters monsters;
	private Map map;
	private Hunt hunt;

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
		try {
			this.npc = new Npc(this);
			this.modelConnexion = new ModelConnexion(this);
			socket = new Socket(this.ip, this.port);
			if (socket.isConnected()) {
				new LatencyFrame();
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			append("Connection...");
			reception();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getReturn(String[] message) {
		try {
			this.modelConnexion.getReturn(message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public void reception() throws Exception {
		while (!this.socket.isClosed()) {
			Thread.sleep(200);
			if (!this.socket.isClosed()) {
				InputStream data = this.socket.getInputStream();
				int available = data.available();
				byte[] buffer = new byte[available];
				if (available > 0) {
					LatencyFrame.updateLatency();
					data.read(buffer, 0, available);
					DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(buffer));
					buildMessage(reader);
				}
				//				data.close();
			}
		}
		this.socket.close();
	}

	/**
	 * Packet manager
	 * 
	 * @param int packet_id
	 * @param byte[] packet_content
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
			case 30:
				handleServersListMessage(dataReader);
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
			case 951:
				GameMapMovementMessage gameMapMovementMessage = new GameMapMovementMessage();
				gameMapMovementMessage.Deserialize(dataReader);
				if (gameMapMovementMessage.getActorId() == info.getActorId()) {
					info.setCellId(gameMapMovementMessage.getKeyMovements().get(gameMapMovementMessage.getKeyMovements().size() - 1));
					append("Moving to cellId : " + info.getCellId());
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
				break;
			case 6316:
				HandleSequenceNumberMessage();
				break;
			case 5816:
				HandleLatencyMessage();
				break;
			case 6575:
				connectionToKoli = true;
				GameRolePlayArenaSwitchToFightServerMessage arenaSwitchToFightServerMessage = new GameRolePlayArenaSwitchToFightServerMessage();
				arenaSwitchToFightServerMessage.Deserialize(dataReader);
				Ticket = arenaSwitchToFightServerMessage.getTicket();
				this.socket.close();
				this.socket = new Socket(arenaSwitchToFightServerMessage.getAddress(), 5555);
				break;
			case 6574:
				connectionToKoli = false;
				LatencyFrame.Sequence = 1;
				GameRolePlayArenaSwitchToGameServerMessage arenaSwitchToGameServerMessage = new GameRolePlayArenaSwitchToGameServerMessage();
				arenaSwitchToGameServerMessage.Deserialize(dataReader);
				Ticket = arenaSwitchToGameServerMessage.getTicket();
				this.socket.close();
				this.socket = new Socket(ip, 5555);
				break;
			case 6068:
				sendToServer(new CharacterSelectedForceReadyMessage(), CharacterSelectedForceReadyMessage.ProtocolId, "Character force selection");
				break;
			case 6471:
				if (connectionToKoli) {
					sendToServer(new GameContextCreateRequestMessage(), GameContextCreateRequestMessage.ProtocolId, "Context creation request");
				}
				break;
			case 5864:
				new GameFightShowFighterMessage().Deserialize(dataReader);
				break;
			case 5709:
				if (info.isConnected()) {
					StatedElementUpdatedMessage elementUpdatedMessage = new StatedElementUpdatedMessage();
					elementUpdatedMessage.Deserialize(dataReader);
					for (int i = 0; i < getInteractive().getStatedElements().size(); i++) {
						if (elementUpdatedMessage.getStatedElement().getElementCellId() == getInteractive().getStatedElements().get(i).getElementCellId()) {
							getInteractive().getStatedElements().set(i, elementUpdatedMessage.getStatedElement());
						}
					}
				}
				break;
			case 5708:
				if (info.isConnected()) {
					InteractiveElementUpdatedMessage interactiveElementUpdatedMessage = new InteractiveElementUpdatedMessage();
					interactiveElementUpdatedMessage.Deserialize(dataReader);
					for (int i = 0; i < getInteractive().getInteractiveElements().size(); i++) {
						if (interactiveElementUpdatedMessage.getInteractiveElement().getElementId() == getInteractive().getInteractiveElements().get(i).getElementId()) {
							getInteractive().getInteractiveElements().set(i, interactiveElementUpdatedMessage.getInteractiveElement());
						}
					}
				}
				break;
			case 6112:
				info.setWaitForHarvestSuccess(true);
				break;
			case 6384:
				info.setWaitForHarvestFailure(true);
				break;
			case 3009:
				InventoryWeightMessage weight = new InventoryWeightMessage();
				weight.Deserialize(dataReader);
				info.setWeight(weight.getWeight());
				info.setWeigthMax(weight.getWeightMax());
				break;
			case 6519:
				ObtainedItemMessage itemMessage = new ObtainedItemMessage();
				itemMessage.Deserialize(dataReader);
				getInteractive().setLastItemHarvestedId(itemMessage.getGenericId());
				getInteractive().setQuantityLastItemHarvested(itemMessage.getBaseQuantity());
				break;
			case 5809:
				JobExperienceMultiUpdateMessage experienceMultiUpdateMessage = new JobExperienceMultiUpdateMessage();
				experienceMultiUpdateMessage.Deserialize(dataReader);
				for (JobExperience b : experienceMultiUpdateMessage.getExperiencesUpdate()) {
					stats.getJob().add(new JobExperience(b.getJobId(), b.getJobLevel(), b.getJobXP(), b.getJobXpLevelFloor(), b.getJobXpNextLevelFloor()));
				}
				break;
			case 5654:
				JobExperienceUpdateMessage experienceUpdateMessage = new JobExperienceUpdateMessage();
				experienceUpdateMessage.Deserialize(dataReader);
				for (int i = 0; i < stats.getJob().size(); i++) {
					if (experienceUpdateMessage.getExperiencesUpdate().getJobId() == stats.getJob().get(i).getJobId()) {
						stats.getJob().set(i, experienceUpdateMessage.getExperiencesUpdate());
					}
				}
				break;
			case 5670:
				CharacterLevelUpMessage characterLevelUpMessage = new CharacterLevelUpMessage();
				characterLevelUpMessage.Deserialize(dataReader);
				info.setLvl(characterLevelUpMessage.getNewLevel());
				break;
			case 5617:
				NpcDialogQuestionMessage dialogQuestionMessage = new NpcDialogQuestionMessage();
				dialogQuestionMessage.Deserialize(dataReader);
				this.npc.reply(this.npc.getReplyId(dialogQuestionMessage.getMessageId()));
				break;
			case 5502:
				this.npc.setDialogOver(true);
				break;
			case 5745:
				info.setInteractiveUsed(true);
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
				ObjectQuantityMessage objectQuantityMessage = new ObjectQuantityMessage();
				objectQuantityMessage.Deserialize(dataReader);
				for (int i = 0; i < this.stats.getInventoryContentMessage().getObjects().size(); i++) {
					if (this.stats.getInventoryContentMessage().getObjects().get(i).getObjectUID() == objectQuantityMessage.getObjectUID()) {
						ObjectItem object = this.stats.getInventoryContentMessage().getObjects().get(i);
						this.stats.getInventoryContentMessage().getObjects().set(i, new ObjectItem(object.getPosition(), object.getObjectGID(), object.getEffects(), object.getObjectUID(), objectQuantityMessage.getQuantity()));
					}
				}
				break;
			case 3025:
				ObjectAddedMessage objectAddedMessage = new ObjectAddedMessage();
				objectAddedMessage.Deserialize(dataReader);
				this.stats.getInventoryContentMessage().getObjects().add(objectAddedMessage.getObject());
				break;
			case 3024:
				ObjectDeletedMessage objectDeletedMessage = new ObjectDeletedMessage();
				objectDeletedMessage.Deserialize(dataReader);
				for (int i = 0; i < this.stats.getInventoryContentMessage().getObjects().size(); i++) {
					if (this.stats.getInventoryContentMessage().getObjects().get(i).getObjectUID() == objectDeletedMessage.getObjectUID()) {
						this.stats.getInventoryContentMessage().getObjects().remove(i);
					}
				}
				break;
			case 6034:
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
				break;
			case 6033:
				ObjectsAddedMessage objectsAddedMessage = new ObjectsAddedMessage();
				objectsAddedMessage.Deserialize(dataReader);
				for (int i = 0; i < objectsAddedMessage.getObject().size(); i++) {
					this.stats.getInventoryContentMessage().getObjects().add(objectsAddedMessage.getObject().get(i));
				}
				break;
			case 3016:
				this.stats.setInventoryContentMessage(new InventoryContentMessage());
				this.stats.getInventoryContentMessage().Deserialize(dataReader);
				break;
			case 6036:
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
				break;
			case 6035:
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
				break;
			case 5647:
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
				break;
			case 5648:
				StorageObjectRemoveMessage storageObjectRemoveMessage = new StorageObjectRemoveMessage();
				storageObjectRemoveMessage.Deserialize(dataReader);
				for (int i = 0; i < getBank().getStorage().getObjects().size(); i++) {
					if (getBank().getStorage().getObjects().get(i).getObjectUID() == storageObjectRemoveMessage.getObjectUID()) {
						getBank().getStorage().getObjects().remove(i);
					}
				}
				info.setStorageUpdate(true);
				break;
			case 5628:
				info.setLeaveExchange(true);
				break;
			case 5537:
				KamasUpdateMessage kamasUpdateMessage = new KamasUpdateMessage();
				kamasUpdateMessage.Deserialize(dataReader);
				this.stats.getInventoryContentMessage().setKamas(kamasUpdateMessage.getKamasTotal());
				info.setStorageUpdate(true);
				break;
			case 5645:
				StorageKamasUpdateMessage storageKamasUpdateMessage = new StorageKamasUpdateMessage();
				storageKamasUpdateMessage.Deserialize(dataReader);
				getBank().getStorage().setKamas(storageKamasUpdateMessage.getKamasTotal());
				info.setStorageUpdate(true);
				break;
			case 702:
				GameFightJoinMessage gameFightJoinMessage = new GameFightJoinMessage();
				gameFightJoinMessage.Deserialize(dataReader);
				info.setJoinedFight(true);
				info.setTurn(false);
				info.setInitFight(false);
				Communication.sendToModel(String.valueOf(getBotInstance()), String.valueOf(info.addAndGetMsgIdFight()), "m", "rtn", "startFight", new Object[] { "None" });
				JSONObject mapJSONObject = new JSONObject();
				mapJSONObject.put("mapID", (int) info.getMapId());
				JSONArray tempArr = new JSONArray();
				tempArr.add(mapJSONObject);
				getFight().sendToFightAlgo("startfight", tempArr);
				break;
			case 956:
				SequenceEndMessage sequenceEndMessage = new SequenceEndMessage();
				sequenceEndMessage.Deserialize(dataReader);
				if (sequenceEndMessage.getAuthorId() == info.getActorId()) {
					Thread.sleep(1000);
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
				break;
			case 715:
				sendToServer(new GameFightTurnReadyMessage(true), GameFightTurnReadyMessage.ProtocolId, "Turn ready");
				break;
			case 719:
				GameFightTurnEndMessage gameFightTurnEndMessage = new GameFightTurnEndMessage();
				gameFightTurnEndMessage.Deserialize(dataReader);
				if (gameFightTurnEndMessage.getId() == info.getActorId()) {
					info.setTurn(false);
				}
				for (int i = 0; i < this.getFight().getMonsters().size(); i++) {
					if (this.getFight().getMonsters().get(i).getContextualId() == gameFightTurnEndMessage.getId()) {
						if (this.getFight().getMonsters().get(i).isAlive()) {
							JSONObject passTurn = new JSONObject();
							passTurn.put("id", (int) getFight().getId(gameFightTurnEndMessage.getId()));
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
							passTurn.put("id", (int) getFight().getId(gameFightTurnEndMessage.getId()));
							JSONArray arr = new JSONArray();
							arr.add(passTurn);
							getFight().sendToFightAlgo("p", arr);
						}
					}
				}
				break;
			case 720:
				info.setJoinedFight(false);
				info.setTurn(false);
				getFight().sendToFightAlgo("endFight", null);
				break;
			case 703:
				getFight().gameFightPlacementPossiblePositionsMessage = new GameFightPlacementPossiblePositionsMessage();
				getFight().gameFightPlacementPossiblePositionsMessage.Deserialize(dataReader);
				// TODO LYSANDRE
				// Fight.setBeginingPosition();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							Thread.sleep(1000);
							getFight().fightReady();
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				break;
			case 5696:
				getFight().gameEntitiesDispositionMessage = new GameEntitiesDispositionMessage();
				getFight().gameEntitiesDispositionMessage.Deserialize(dataReader);
				break;
			case 5921:
				getFight().setGameFightSynchronizeMessage(new GameFightSynchronizeMessage());
				getFight().getGameFightSynchronizeMessage().Deserialize(dataReader);
				if (!info.isInitFight()) {
					JSONObject startFight = new JSONObject();
					startFight.put("misc", getFight().init());
					startFight.put("entities", getFight().getEntities());
					JSONArray arr2 = new JSONArray();
					arr2.add(startFight);
					getFight().sendToFightAlgo("s", arr2);
					info.setInitFight(true);
				}
				break;
			case 6465:
				info.setTurn(true);
				getFight().fightTurn();
				break;
			case 955:
				getFight().setSpellJson(new JSONArray());
				break;
			/**
			 * LYSANDRE FIGHT // case 1010 to case 6310 Each sequence can
			 * contain multiple jsonObject Each case contains one jsonObject
			 * jsonObject : key of the variable jsonObject : key of the packet
			 * sourceId : caster targetId : target
			 */
			case 1010:
				GameActionFightSpellCastMessage gameActionFightSpellCastMessage = new GameActionFightSpellCastMessage();
				gameActionFightSpellCastMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("spellId", (int) gameActionFightSpellCastMessage.getSpellId());
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightSpellCastMessage.getTargetId()));

				int cellId = (int) gameActionFightSpellCastMessage.getDestinationCellId();
				int x = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[0];
				int y = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[1];

				jsonObject.put("x", (int) x);
				jsonObject.put("y", (int) y);
				jsonObject.put("critical", gameActionFightSpellCastMessage.getCritical());
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightSpellCastMessage.getSourceId()));
				jsonObject2 = new JSONObject();
				jsonObject2.put("spellCast", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 6312:
				GameActionFightLifePointsLostMessage gameActionFightLifePointsLostMessage = new GameActionFightLifePointsLostMessage();
				gameActionFightLifePointsLostMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightLifePointsLostMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightLifePointsLostMessage.getTargetId()));
				jsonObject.put("lpLost", (int) gameActionFightLifePointsLostMessage.getLoss());
				jsonObject.put("lpMaxLost", (int) gameActionFightLifePointsLostMessage.getPermanentDamages());
				jsonObject2 = new JSONObject();
				jsonObject2.put("lifePointsLost", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 5828:
				GameActionFightDodgePointLossMessage dodgePointLossMessage = new GameActionFightDodgePointLossMessage();
				dodgePointLossMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(dodgePointLossMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(dodgePointLossMessage.getTargetId()));
				jsonObject.put("amount", (int) dodgePointLossMessage.getAmount());
				jsonObject2 = new JSONObject();
				jsonObject2.put("dodgePointLoss", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			/**
			 * Depending on the packet received it will give a JsonObject with
			 * diferent key the first are in common for every packet
			 */
			case 6070:
				GameActionFightDispellableEffectMessage gameActionFightDispellableEffectMessage = new GameActionFightDispellableEffectMessage();
				gameActionFightDispellableEffectMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightDispellableEffectMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightDispellableEffectMessage.getEffect().getTargetId()));
				jsonObject.put("effectId", (int) gameActionFightDispellableEffectMessage.getEffect().getEffectId());
				jsonObject.put("spellId", (int) gameActionFightDispellableEffectMessage.getEffect().getSpellId());
				jsonObject.put("turnDuration", (int) gameActionFightDispellableEffectMessage.getEffect().getTurnDuration());
				jsonObject.put("dispelable", gameActionFightDispellableEffectMessage.getEffect().getDispelable());
				if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostEffect")) {
					jsonObject.put("amount", (int) ((FightTemporaryBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getDelta());
				}
				if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostStateEffect")) {
					jsonObject.put("stateId", (int) ((FightTemporaryBoostStateEffect) gameActionFightDispellableEffectMessage.getEffect()).getStateId());
				}
				if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporaryBoostWeaponDamagesEffect")) {
					jsonObject.put("weaponTypeId", (int) ((FightTemporaryBoostWeaponDamagesEffect) gameActionFightDispellableEffectMessage.getEffect()).getWeaponTypeId());
				}
				if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporarySpellBoostEffect")) {
					jsonObject.put("boostedSpellId", (int) ((FightTemporarySpellBoostEffect) gameActionFightDispellableEffectMessage.getEffect()).getBoostedSpellId());

				}
				if (gameActionFightDispellableEffectMessage.getEffect().getClass().getSimpleName().equals("FightTemporarySpellImmunityEffect")) {
					jsonObject.put("immuneSpellId", (int) ((FightTemporarySpellImmunityEffect) gameActionFightDispellableEffectMessage.getEffect()).getImmuneSpellId());

				}
				jsonObject2 = new JSONObject();
				jsonObject2.put("dispellableEffect", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 5540:
				GameActionFightMarkCellsMessage gameActionFightMarkCellsMessage = new GameActionFightMarkCellsMessage();
				gameActionFightMarkCellsMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightMarkCellsMessage.getSourceId()));
				jsonObject.put("markSpellId", (int) gameActionFightMarkCellsMessage.getMark().getMarkSpellId());
				jsonObject.put("markImpactCellId", (int) gameActionFightMarkCellsMessage.getMark().getMarkimpactCell());
				JSONArray jsonArray = new JSONArray();
				for (GameActionMarkedCell object : gameActionFightMarkCellsMessage.getMark().getCells()) {
					JSONObject jsonCells = new JSONObject();
					jsonCells.put("cellId", (int) object.getCellId());
					jsonCells.put("zoneSize", object.getZoneSize());
					jsonArray.add(jsonCells);
				}
				jsonObject.put("cells", jsonArray);
				jsonObject2 = new JSONObject();
				jsonObject2.put("markCells", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 6311:
				GameActionFightLifePointsGainMessage gameActionFightLifePointsGainMessage = new GameActionFightLifePointsGainMessage();
				gameActionFightLifePointsGainMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightLifePointsGainMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightLifePointsGainMessage.getTargetId()));
				jsonObject.put("lpGain", (int) gameActionFightLifePointsGainMessage.getDelta());
				jsonObject2 = new JSONObject();
				jsonObject2.put("lifePointsGain", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 1099:
				GameActionFightDeathMessage gameActionFightDeathMessage = new GameActionFightDeathMessage();
				gameActionFightDeathMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightDeathMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightDeathMessage.getTargetId()));
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
				break;
			case 6116:
				GameActionFightCloseCombatMessage gameActionFightCloseCombatMessage = new GameActionFightCloseCombatMessage();
				gameActionFightCloseCombatMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightCloseCombatMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightCloseCombatMessage.getTargetId()));
				jsonObject.put("weaponGeneric", d2iManager.getText(GameData.getWeaponNameId(gameActionFightCloseCombatMessage.getWeaponGenericId())));
				jsonObject.put("critical", gameActionFightCloseCombatMessage.getCritical());
				jsonObject.put("destinationCellId", (int) gameActionFightCloseCombatMessage.getDestinationCellId());
				jsonObject2 = new JSONObject();
				jsonObject2.put("closeCombat", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 5525:
				GameActionFightSlideMessage gameActionFightSlideMessage = new GameActionFightSlideMessage();
				gameActionFightSlideMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightSlideMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightSlideMessage.getTargetId()));
				jsonObject.put("startCellId", (int) gameActionFightSlideMessage.getStartCellId());
				jsonObject.put("endCellId", (int) gameActionFightSlideMessage.getEndCellId());
				jsonObject2 = new JSONObject();
				jsonObject2.put("slide", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 6310:
				GameActionFightLifeAndShieldPointsLostMessage gameActionFightLifeAndShieldPointsLostMessage = new GameActionFightLifeAndShieldPointsLostMessage();
				gameActionFightLifeAndShieldPointsLostMessage.Deserialize(dataReader);
				jsonObject = new JSONObject();
				jsonObject.put("sourceId", (int) getFight().getId(gameActionFightLifeAndShieldPointsLostMessage.getSourceId()));
				jsonObject.put("targetId", (int) getFight().getId(gameActionFightLifeAndShieldPointsLostMessage.getTargetId()));
				jsonObject.put("shieldLoss", (int) gameActionFightLifeAndShieldPointsLostMessage.getShieldLoss());
				jsonObject.put("lpLoss", (int) gameActionFightLifeAndShieldPointsLostMessage.getLoss());
				jsonObject.put("lpMaxLoss", (int) gameActionFightLifeAndShieldPointsLostMessage.getPermanentDamages());
				jsonObject2 = new JSONObject();
				jsonObject2.put("shieldLpLoss", jsonObject);
				getFight().getSpellJson().add(jsonObject2);
				break;
			case 713:
				GameFightTurnListMessage gameFightTurnListMessage = new GameFightTurnListMessage();
				gameFightTurnListMessage.Deserialize(dataReader);
				if (!info.isInitFight()) {
					getFight().turnListId = gameFightTurnListMessage.getIds();
				}
				break;
			case 1604:
				ZaapListMessage zaapListMessage = new ZaapListMessage();
				zaapListMessage.Deserialize(dataReader);
				this.interactive.setZaapList(zaapListMessage.getMapIds());
				break;
			case 5632:
				GameRolePlayShowActorMessage gameRolePlayShowActorMessage = new GameRolePlayShowActorMessage();
				gameRolePlayShowActorMessage.Deserialize(dataReader);
				if (gameRolePlayShowActorMessage.getInformations().getClass().getSimpleName().equals("GameRolePlayGroupMonsterInformations")) {
					this.getMonsters().addMonsters((GameRolePlayGroupMonsterInformations) gameRolePlayShowActorMessage.getInformations());
				}
				break;
			case 251:
				GameContextRemoveElementMessage gameContextRemoveElementMessage = new GameContextRemoveElementMessage();
				gameContextRemoveElementMessage.Deserialize(dataReader);
				for (int i = 0; i < this.getMonsters().getMonsters().size(); i++) {
					if (this.getMonsters().getMonsters().get(i).getContextualId() == gameContextRemoveElementMessage.getId()) {
						this.getMonsters().getMonsters().remove(i);
					}
				}
				break;
			case 6483:
				this.info.setHuntAnswered(true);
				this.info.setInHunt(false);
				break;
			case 6484:
				TreasureHuntDigRequestAnswerMessage treasureHuntDigRequestAnswerMessage = new TreasureHuntDigRequestAnswerMessage();
				treasureHuntDigRequestAnswerMessage.Deserialize(dataReader);
				if (treasureHuntDigRequestAnswerMessage.getResult() == 1) {
					this.getInfo().setStepSuccess(true);
				}
				break;
			case 6486:
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
				this.hunt.setCurrentIndex(sizeStep - 1);
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
				break;
		}
		packet_content = null;
		dataReader.bis.close();
		return;
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
			append("Map : [" + info.getCoords()[0] + ";" + info.getCoords()[1] + "]");
			append("CellId : " + info.getCellId());
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

	private void handleCharacterSelectionMessage(DofusDataReader dataReader) throws Exception, Error {
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

	private void handleSelectedServerDataMessage(DofusDataReader dataReader) throws IOException, UnknownHostException {
		SelectedServerDataMessage selectServer = new SelectedServerDataMessage();
		selectServer.Deserialize(dataReader);
		Ticket = selectServer.getTicket();
		this.socket.close();
		this.socket = new Socket(selectServer.getAddress(), selectServer.getPort());
	}

	private void handleServersListMessage(DofusDataReader dataReader) throws Exception {
		ServersListMessage servers = new ServersListMessage();
		servers.Deserialize(dataReader);
		ServerSelectionMessage select = new ServerSelectionMessage(Servers.getServerId(info.getServer()));
		sendToServer(select, ServerSelectionMessage.ProtocolId, "Selection du serveur...");
	}

	private void handleHelloConnectMessage(DofusDataReader dataReader) throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, Exception {
		HelloConnectMessage hello = new HelloConnectMessage();
		hello.Deserialize(dataReader);
		byte[] key = new byte[hello.getKey().size()];
		for (int i = 0; i < hello.getKey().size(); i++) {
			key[i] = hello.getKey().get(i).byteValue();
		}
		VersionExtended versionExtended = new VersionExtended(2, 45, 15, 0, 0, 0, 1, 1);
		byte[] credentials = Crypto.encrypt(key, info.getNameAccount(), info.getPassword(), hello.getSalt());
		List<Integer> credentialsArray = new ArrayList<Integer>();
		for (byte b : credentials) {
			credentialsArray.add((int) b);
		}
		List<Integer> failedAttempts = new ArrayList<Integer>();
		IdentificationMessage identification = new IdentificationMessage(versionExtended, "fr", credentialsArray, 0, true, false, false, 0, failedAttempts);
		sendToServer(identification, IdentificationMessage.ProtocolId, "Identification...");
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

	/*
	 * Send packet to server message = type; id = id packet; String s = String
	 * displayed on log
	 */
	public void sendToServer(NetworkMessage message, int id, String s) throws Exception {
		info.setBooleanToFalse();
		LatencyFrame.latestSent();
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		DofusDataWriter writer = new DofusDataWriter(bous);
		message.Serialize(writer);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		byte[] wrote = WritePacket(writer, bous, id);
		dout.write(wrote);
		dout.flush();
		appendLog("[" + id + "]	[Envoi] " + s);
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

	private int SubComputeStaticHeader(int id, byte typeLen) {
		return (id << 2) | typeLen;
	}

	public String bytesToString(byte[] bytes, String format, boolean spacer) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format(format, b));
			if (spacer) sb.append(" ");
		}
		return sb.toString();
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

	private void HandleSelectedDataServer(String address, int port, List<Integer> ticket) throws IOException {

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

	private void HandleCharacterListRequestMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 150, "Character list request");
	}

	private void HandleCharacterSelectionMessage(long id) throws Exception {

	}

	private void HandleFriendIgnoreSpouseMessages() throws Exception {
		// Send friend list request
		sendToServer(new NetworkMessageEmpty(), 4001, "Friend list request");
		// Send ignored list request
		sendToServer(new NetworkMessageEmpty(), 5676, "Ignored list request");
		// Send spouse list request
		sendToServer(new NetworkMessageEmpty(), 6355, "Spouse list request");
	}

	private void HandleClientKeyMessage(String key) throws Exception {
		ClientKeyMessage clientKeyMessage = new ClientKeyMessage(key);
		sendToServer(clientKeyMessage, ClientKeyMessage.ProtocolId, "Client key");
	}

	private void HandleGameContextCreateMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 250, "Game context create request");
	}

	private void HandleSequenceNumberMessage() throws Exception {
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(LatencyFrame.Sequence++);
		sendToServer(sequenceNumberMessage, SequenceNumberMessage.ProtocolId, "Sequence number");
	}

	private void HandleLatencyMessage() throws Exception {
		BasicLatencyStatsMessage basicLatencyStatsMessage = new BasicLatencyStatsMessage(LatencyFrame.getLatencyAvg(), LatencyFrame.getSamplesCount(), LatencyFrame.GetSamplesMax());
		sendToServer(basicLatencyStatsMessage, BasicLatencyStatsMessage.ProtocolId, "Latency message");
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

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Fight getFight() {
		return fight;
	}

	public void setFight(Fight fight) {
		this.fight = fight;
	}

	public Npc getNpc() {
		return npc;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Hunt getHunt() {
		return hunt;
	}

	public void setHunt(Hunt hunt) {
		this.hunt = hunt;
	}
}
