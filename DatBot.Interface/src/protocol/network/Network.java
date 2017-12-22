package protocol.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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

import Game.Entity;
import Game.Info;
import Game.map.Map;
import Game.movement.Movement;
import Main.Communication.Communication;
import ia.fight.map.CreateMap;
import io.netty.util.internal.ThreadLocalRandom;
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
import protocol.network.messages.game.actions.fight.GameActionFightDispellableEffectMessage;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsLostMessage;
import protocol.network.messages.game.actions.fight.GameActionFightSpellCastMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsMessage;
import protocol.network.messages.game.basic.SequenceNumberMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedForceReadyMessage;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.character.stats.CharacterLevelUpMessage;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.context.GameContextCreateRequestMessage;
import protocol.network.messages.game.context.GameContextReadyMessage;
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
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.messages.game.context.roleplay.MapInformationsRequestMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToFightServerMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToGameServerMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceMultiUpdateMessage;
import protocol.network.messages.game.context.roleplay.job.JobExperienceUpdateMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogQuestionMessage;
import protocol.network.messages.game.interactive.InteractiveElementUpdatedMessage;
import protocol.network.messages.game.interactive.StatedElementUpdatedMessage;
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
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.context.roleplay.job.JobExperience;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.version.VersionExtended;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.util.FlashKeyGenerator;
import protocol.network.util.MessageUtil;
import protocol.network.util.SwitchNameClass;
import Game.Servers;
import Game.Plugin.Bank;
import Game.Plugin.Interactive;
import Game.Plugin.Monsters;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.combat.Fight;
import utils.JSON;

public class Network implements Runnable {

	private Socket socket;
	private String ip;
	private Message message;
	private List<Integer> Ticket;
	// Log window
	public boolean displayPacket;
	private JFrame f;
	private JPanel panel;
	private JTextPane text;
	// Big packet split
	private int bigPacketLengthToFull;// Length needed to finish the packet
	private int bigPacketId;
	private byte[] bigPacketData;
	// Timing
	private Random r = new Random(); // Random for thread sleep

	// Plugin
	public boolean connectionToKoli = false;
	private Fight fight = new Fight(this);
	private Interactive interactive = new Interactive(this);
	private Bank bank = new Bank();
	private NPC npc = new NPC();
	private Movement movement = new Movement(this);

	public Network(boolean displayPacket, String ip, int port)
	{
		this.displayPacket = displayPacket;
		this.ip = ip;
		try
		{
			socket = new Socket(ip, port);
			if (socket.isConnected())
			{
				new LatencyFrame();
			}
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			append("Connection...", false);
			if (displayPacket)
			{
				initComponent();
				f.pack();
				f.setVisible(true);
			}
			reception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void initComponent()
	{
		// Jframe Creation
		f = new JFrame("Log");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(950, 25);
		f.setPreferredSize(new Dimension(900, 600));
		// JPanel creation
		panel = new JPanel();
		f.getContentPane().add(panel);
		// JtextArea creation
		text = new JTextPane();
		text.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		text.setFont(new Font("Lucida Console", text.getFont().getStyle(), 12));
		panel.add(text);
		// Scroll
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		f.add(scroll);
	}

	/**
	 * Append the text on the panel depending on the String
	 * @param JtextPane tp
	 * @param String msg
	 * @param Color c
	 */
	private void appendToPane(JTextPane tp, String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	/**
	 * Append the text eiher on the panel or System.out
	 * @param String str
	 * @param boolean b ; True:panel ; False:System.out
	 */
	public void append(String str, boolean b)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		String timing = formatter.format(time);
		if (displayPacket && b)
		{
			appendToPane(text, "[" + timing + "] ", Color.black);
			if (str.contains("Envoi"))
			{
				appendToPane(text, str + "\n", new Color(0, 0, 140));
			}
			else
			{
				appendToPane(text, str + "\n", new Color(0, 110, 0));
			}
		}
		else
		{
			String newSt = "[" + timing + "] " + str;
			System.out.println(newSt);
		}
	}

	public void reception() throws Exception
	{
		while (!this.socket.isClosed())
		{
			Thread.sleep(200);
			InputStream data = this.socket.getInputStream();
			int available = data.available();
			byte[] buffer = new byte[available];
			if (available > 0)
			{
				LatencyFrame.updateLatency();
				data.read(buffer, 0, available);
				DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(buffer));
				buildMessage(reader);
			}
		}
		this.socket.close();
	}

	/**
	 * Packet manager
	 * @param int packet_id
	 * @param byte[] packet_content
	 */
	private void TreatPacket(int packet_id, byte[] packet_content) throws Exception
	{
		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(packet_content));
		SwitchNameClass name = new SwitchNameClass(packet_id);
		append("[" + packet_id + "]\tTaille : " + packet_content.length + "  -  " + name.name, true);
		switch (packet_id)
		{
			case 1:
				ProtocolRequired protoc = new ProtocolRequired();
				protoc.Deserialize(dataReader);
			break;
			case 3:
				HelloConnectMessage hello = new HelloConnectMessage();
				hello.Deserialize(dataReader);
				byte[] key = new byte[hello.key.size()];
				for (int i = 0; i < hello.key.size(); i++)
				{
					key[i] = hello.key.get(i).byteValue();
				}
				HandleHelloConnectMessage(key, hello.salt);
			break;
			case 10:
				LoginQueueStatusMessage queue = new LoginQueueStatusMessage();
				queue.Deserialize(dataReader);
			break;
			case 20:
				IdentificationFailedMessage fail = new IdentificationFailedMessage();
				fail.Deserialize(dataReader);
			break;
			case 22:
				IdentificationSuccessMessage success = new IdentificationSuccessMessage();
				success.Deserialize(dataReader);
			break;
			case 30:
				ServersListMessage servers = new ServersListMessage();
				servers.Deserialize(dataReader);
				HandleServersListMessage(Servers.getServerId(Info.server));
			break;
			case 42:
				SelectedServerDataMessage selectServer = new SelectedServerDataMessage();
				if (dataReader.available() == 0)
				{
					this.message = null;
					return;
				}
				selectServer.Deserialize(dataReader);
				HandleSelectedDataServer(selectServer.address, selectServer.port, selectServer.ticket);
			break;
			case 101:
				HandleAuthentificationTicketMessage();
				if (connectionToKoli)
				{
					Thread.sleep(r.nextInt(100));
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
				CharactersListMessage charactersListMessage = new CharactersListMessage();
				charactersListMessage.Deserialize(dataReader);
				int j = 0;
				for (int i = 0; i < charactersListMessage.characters.size(); i++)
				{
					if (charactersListMessage.characters.get(i).name.equals(Info.name))
					{
						HandleCharacterSelectionMessage(charactersListMessage.characters.get(i).id);
						Info.actorId = charactersListMessage.characters.get(i).id;
						Info.lvl = charactersListMessage.characters.get(i).level;
						j = 1;
						break;
					}
				}
				if (j == 0) throw new Error("Wrong character name !");
			break;
			case 1301:
				HandleFriendIgnoreSpouseMessages();
			break;
			case 4002:
				HandleClientKeyMessage(FlashKeyGenerator.GetRandomFlashKey(Info.name));
				HandleGameContextCreateMessage();
			break;
			case 500:
				Info.stats = new CharacterStatsListMessage();
				Info.stats.Deserialize(dataReader);
			break;
			case 220:
				CurrentMapMessage currentMapMessage = new CurrentMapMessage();
				currentMapMessage.Deserialize(dataReader);
				Info.mapId = currentMapMessage.mapId;
				if (connectionToKoli)
				{
					sendToServer(new GameContextReadyMessage(currentMapMessage.mapId), GameContextReadyMessage.ProtocolId, "Context ready");
				}
				else
				{
					HandleMapRequestMessage(currentMapMessage.mapId);
				}
			break;
			case 176:
				Info.basicNoOperationMsg = true;
			break;
			case 226:
				MapComplementaryInformationsDataMessage complementaryInformationsDataMessage = new MapComplementaryInformationsDataMessage();
				complementaryInformationsDataMessage.Deserialize(dataReader);
				if (!connectionToKoli)
				{
					for (int i = 0; i < complementaryInformationsDataMessage.actors.size(); i++)
					{
						if (complementaryInformationsDataMessage.actors.get(i).getClass().getSimpleName().equals("GameRolePlayNpcInformations"))
						{
							npc.getNpc().add((GameRolePlayNpcInformations) complementaryInformationsDataMessage.actors.get(i));
						}
						else if (complementaryInformationsDataMessage.actors.get(i).getClass().getSimpleName().equals("GameRolePlayGroupMonsterInformations"))
						{
							Monsters.monsters.add((GameRolePlayGroupMonsterInformations) complementaryInformationsDataMessage.actors.get(i));

						}
						if (complementaryInformationsDataMessage.actors.get(i).contextualId == Info.actorId)
							Info.cellId = complementaryInformationsDataMessage.actors.get(i).disposition.cellId;
						else
							Map.Entities.add(new Entity(complementaryInformationsDataMessage.actors.get(i).disposition.cellId, complementaryInformationsDataMessage.actors.get(i).contextualId));
					}
					HandleMapComplementaryInformationsDataMessage();
					getInteractive().setStatedElements(complementaryInformationsDataMessage.statedElements);
					getInteractive().setInteractiveElements(complementaryInformationsDataMessage.interactiveElements);
					append("Map : [" + Info.coords[0] + ";" + Info.coords[1] + "]", false);
					append("CellId : " + Info.cellId, false);
					Info.waitForMov = true;
					Info.isConnected = true;
					Info.newMap = true;
				}
			break;
			case 891:
			break;
			case 951:
				GameMapMovementMessage gameMapMovementMessage = new GameMapMovementMessage();
				gameMapMovementMessage.Deserialize(dataReader);
				if (gameMapMovementMessage.actorId == Info.actorId)
				{
					Info.cellId = gameMapMovementMessage.keyMovements.get(gameMapMovementMessage.keyMovements.size() - 1);
					append("Déplacement réussi !", false);
					append("CellId : " + Info.cellId, false);
				}
				for (int i = 0; i < Monsters.monsters.size(); i++)
				{
					if (Monsters.monsters.get(i).contextualId == gameMapMovementMessage.actorId)
					{
						Monsters.monsters.get(i).disposition.cellId = gameMapMovementMessage.keyMovements.get(gameMapMovementMessage.keyMovements.size() - 1);
					}
				}
				if (Info.joinedFight)
				{
					int cellId = gameMapMovementMessage.keyMovements.get(gameMapMovementMessage.keyMovements.size() - 1);
					int x = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[0];
					int y = CreateMap.rotate(new int[] { cellId % 14, cellId / 14 })[1];
					fight.sendToFightAlgo("m", new Object[] { gameMapMovementMessage.actorId, x, y });
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
				Ticket = arenaSwitchToFightServerMessage.ticket;
				this.socket.close();
				this.socket = new Socket(arenaSwitchToFightServerMessage.address, 5555);
			break;
			case 6574:
				connectionToKoli = false;
				LatencyFrame.Sequence = 1;
				GameRolePlayArenaSwitchToGameServerMessage arenaSwitchToGameServerMessage = new GameRolePlayArenaSwitchToGameServerMessage();
				arenaSwitchToGameServerMessage.Deserialize(dataReader);
				Ticket = arenaSwitchToGameServerMessage.ticket;
				this.socket.close();
				this.socket = new Socket(ip, 5555);
			break;
			case 6068:
				sendToServer(new CharacterSelectedForceReadyMessage(), CharacterSelectedForceReadyMessage.ProtocolId, "Character force selection");
			break;
			case 6471:
				if (connectionToKoli)
				{
					sendToServer(new GameContextCreateRequestMessage(), GameContextCreateRequestMessage.ProtocolId, "Context creation request");
				}
			break;
			case 5864:
				new GameFightShowFighterMessage().Deserialize(dataReader);
			break;
			case 5709:
				if (Info.isConnected)
				{
					StatedElementUpdatedMessage elementUpdatedMessage = new StatedElementUpdatedMessage();
					elementUpdatedMessage.Deserialize(dataReader);
					for (int i = 0; i < getInteractive().getStatedElements().size(); i++)
					{
						if (elementUpdatedMessage.statedElement.elementCellId == getInteractive().getStatedElements().get(i).elementCellId)
						{
							getInteractive().getStatedElements().set(i, elementUpdatedMessage.statedElement);
						}
					}
				}
			break;
			case 5708:
				if (Info.isConnected)
				{
					InteractiveElementUpdatedMessage interactiveElementUpdatedMessage = new InteractiveElementUpdatedMessage();
					interactiveElementUpdatedMessage.Deserialize(dataReader);
					for (int i = 0; i < getInteractive().getInteractiveElements().size(); i++)
					{
						if (interactiveElementUpdatedMessage.interactiveElement.elementId == getInteractive().getInteractiveElements().get(i).elementId)
						{
							getInteractive().getInteractiveElements().set(i, interactiveElementUpdatedMessage.interactiveElement);
						}
					}
				}
			break;
			case 6112:
				Info.waitForHarvestSuccess = true;
			break;
			case 6384:
				Info.waitForHarvestFailure = true;
			break;
			case 3009:
				InventoryWeightMessage weight = new InventoryWeightMessage();
				weight.Deserialize(dataReader);
				Info.weight = weight.weight;
				Info.weigthMax = weight.weightMax;
			break;
			case 6519:
				ObtainedItemMessage itemMessage = new ObtainedItemMessage();
				itemMessage.Deserialize(dataReader);
				getInteractive().setLastItemHarvestedId(itemMessage.genericId);
				getInteractive().setQuantityLastItemHarvested(itemMessage.baseQuantity);
			break;
			case 5809:
				JobExperienceMultiUpdateMessage experienceMultiUpdateMessage = new JobExperienceMultiUpdateMessage();
				experienceMultiUpdateMessage.Deserialize(dataReader);
				for (JobExperience b : experienceMultiUpdateMessage.experiencesUpdate)
				{
					Info.job.add(new JobExperience(b.jobId, b.jobLevel, b.jobXP, b.jobXpLevelFloor, b.jobXpNextLevelFloor));
				}
			break;
			case 5654:
				JobExperienceUpdateMessage experienceUpdateMessage = new JobExperienceUpdateMessage();
				experienceUpdateMessage.Deserialize(dataReader);
				for (int i = 0; i < Info.job.size(); i++)
				{
					if (experienceUpdateMessage.experiencesUpdate.jobId == Info.job.get(i).jobId)
					{
						Info.job.set(i, experienceUpdateMessage.experiencesUpdate);
					}
				}
			break;
			case 5670:
				CharacterLevelUpMessage characterLevelUpMessage = new CharacterLevelUpMessage();
				characterLevelUpMessage.Deserialize(dataReader);
				Info.lvl = characterLevelUpMessage.newLevel;
			break;
			case 5617:
				NpcDialogQuestionMessage dialogQuestionMessage = new NpcDialogQuestionMessage();
				dialogQuestionMessage.Deserialize(dataReader);
				this.npc.reply(this.npc.getReplyId(dialogQuestionMessage.messageId));
			break;
			case 5502:
				this.npc.setDialogOver(true);
			break;
			case 5745:
				Info.interactiveUsed = true;
			break;
			case 5646:
				getBank().storage = new StorageInventoryContentMessage();
				getBank().storage.Deserialize(dataReader);
				Info.Storage = true;
			break;
			case 6162:
				Stats.inventoryContentMessage = new InventoryContentAndPresetMessage();
				Stats.inventoryContentMessage.Deserialize(dataReader);
			break;
			case 3023:
				ObjectQuantityMessage objectQuantityMessage = new ObjectQuantityMessage();
				objectQuantityMessage.Deserialize(dataReader);
				for (int i = 0; i < Stats.inventoryContentMessage.objects.size(); i++)
				{
					if (Stats.inventoryContentMessage.objects.get(i).objectUID == objectQuantityMessage.objectUID)
					{
						ObjectItem object = Stats.inventoryContentMessage.objects.get(i);
						Stats.inventoryContentMessage.objects.set(i, new ObjectItem(object.position, object.objectGID, object.effects, object.objectUID, objectQuantityMessage.quantity));
					}
				}
			break;
			case 3025:
				ObjectAddedMessage objectAddedMessage = new ObjectAddedMessage();
				objectAddedMessage.Deserialize(dataReader);
				Stats.inventoryContentMessage.objects.add(objectAddedMessage.object);
			break;
			case 3024:
				ObjectDeletedMessage objectDeletedMessage = new ObjectDeletedMessage();
				objectDeletedMessage.Deserialize(dataReader);
				for (int i = 0; i < Stats.inventoryContentMessage.objects.size(); i++)
				{
					if (Stats.inventoryContentMessage.objects.get(i).objectUID == objectDeletedMessage.objectUID)
					{
						Stats.inventoryContentMessage.objects.remove(i);
					}
				}
			break;
			case 6034:
				ObjectsDeletedMessage objectsDeletedMessage = new ObjectsDeletedMessage();
				objectsDeletedMessage.Deserialize(dataReader);
				for (int i = 0; i < objectsDeletedMessage.objectUID.size(); i++)
				{
					for (int k = 0; k < Stats.inventoryContentMessage.objects.size(); k++)
					{
						if (Stats.inventoryContentMessage.objects.get(k).objectUID == objectsDeletedMessage.objectUID.get(i))
						{
							Stats.inventoryContentMessage.objects.remove(k);
							break;
						}
					}
				}
			break;
			case 6033:
				ObjectsAddedMessage objectsAddedMessage = new ObjectsAddedMessage();
				objectsAddedMessage.Deserialize(dataReader);
				for (int i = 0; i < objectsAddedMessage.object.size(); i++)
				{
					Stats.inventoryContentMessage.objects.add(objectsAddedMessage.object.get(i));
				}
			break;
			case 3016:
				Stats.inventoryContentMessage = new InventoryContentMessage();
				Stats.inventoryContentMessage.Deserialize(dataReader);
			break;
			case 6036:
				StorageObjectsUpdateMessage storageObjectsUpdateMessage = new StorageObjectsUpdateMessage();
				storageObjectsUpdateMessage.Deserialize(dataReader);
				for (int i = 0; i < storageObjectsUpdateMessage.objectList.size(); i++)
				{
					boolean isInBank = false;
					for (int k = 0; k < getBank().storage.objects.size(); k++)
					{
						if (storageObjectsUpdateMessage.objectList.get(i).objectUID == getBank().storage.objects.get(k).objectUID)
						{
							getBank().storage.objects.set(i, storageObjectsUpdateMessage.objectList.get(i));
							isInBank = true;
						}
					}
					if (!isInBank)
					{
						getBank().storage.objects.add(storageObjectsUpdateMessage.objectList.get(i));
					}
				}
				Info.StorageUpdate = true;
			break;
			case 6035:
				StorageObjectsRemoveMessage storageObjectsRemoveMessage = new StorageObjectsRemoveMessage();
				storageObjectsRemoveMessage.Deserialize(dataReader);
				for (int i = 0; i < storageObjectsRemoveMessage.objectUIDList.size(); i++)
				{
					for (int k = 0; k < getBank().storage.objects.size(); k++)
					{
						if (storageObjectsRemoveMessage.objectUIDList.get(i) == getBank().storage.objects.get(k).objectUID)
						{
							getBank().storage.objects.remove(k);
							break;
						}
					}
				}
				Info.StorageUpdate = true;
			break;
			case 5647:
				StorageObjectUpdateMessage storageObjectUpdateMessage = new StorageObjectUpdateMessage();
				storageObjectUpdateMessage.Deserialize(dataReader);
				boolean isItem = false;
				for (int i = 0; i < getBank().storage.objects.size(); i++)
				{
					if (getBank().storage.objects.get(i).objectGID == storageObjectUpdateMessage.object.objectGID || getBank().storage.objects.get(i).objectUID == storageObjectUpdateMessage.object.objectUID)
					{
						getBank().storage.objects.set(i, storageObjectUpdateMessage.object);
						isItem = true;
					}
				}
				if (!isItem)
				{
					getBank().storage.objects.add(storageObjectUpdateMessage.object);
				}
				Info.StorageUpdate = true;
			break;
			case 5648:
				StorageObjectRemoveMessage storageObjectRemoveMessage = new StorageObjectRemoveMessage();
				storageObjectRemoveMessage.Deserialize(dataReader);
				for (int i = 0; i < getBank().storage.objects.size(); i++)
				{
					if (getBank().storage.objects.get(i).objectUID == storageObjectRemoveMessage.objectUID)
					{
						getBank().storage.objects.remove(i);
					}
				}
				Info.StorageUpdate = true;
			break;
			case 5628:
				Info.leaveExchange = true;
			break;
			case 5537:
				KamasUpdateMessage kamasUpdateMessage = new KamasUpdateMessage();
				kamasUpdateMessage.Deserialize(dataReader);
				Stats.inventoryContentMessage.kamas = kamasUpdateMessage.kamasTotal;
				Info.StorageUpdate = true;
			break;
			case 5645:
				StorageKamasUpdateMessage storageKamasUpdateMessage = new StorageKamasUpdateMessage();
				storageKamasUpdateMessage.Deserialize(dataReader);
				getBank().storage.kamas = storageKamasUpdateMessage.kamasTotal;
				Info.StorageUpdate = true;
			break;
			// case 881:
			// ChatServerMessage chatServerMessage = new ChatServerMessage();
			// chatServerMessage.Deserialize(dataReader);
			// if (chatServerMessage.channel == 0)
			// { // GÃ©nÃ©ral
			// output = new BufferedWriter(new FileWriter(Info.server +
			// "General.txt", true));
			// output.append(System.lineSeparator() + "[" +
			// chatServerMessage.senderName + "]" + "[" + (int)
			// chatServerMessage.senderAccountId + "] " +
			// chatServerMessage.content);
			// output.close();
			// }
			// else if (chatServerMessage.channel == 5)
			// { // Commerce
			// output = new BufferedWriter(new FileWriter(Info.server +
			// "Commerce.txt", true));
			// output.append(System.lineSeparator() + "[" +
			// chatServerMessage.senderName + "]" + "[" + (int)
			// chatServerMessage.senderAccountId + "] " +
			// chatServerMessage.content);
			// output.close();
			// }
			// else if (chatServerMessage.channel == 6)
			// { // Recrutement
			// output = new BufferedWriter(new FileWriter(Info.server +
			// "Recrutement.txt", true));
			// output.append(System.lineSeparator() + "[" +
			// chatServerMessage.senderName + "]" + "[" + (int)
			// chatServerMessage.senderAccountId + "] " +
			// chatServerMessage.content);
			// output.close();
			// }
			// break;
			// case 5683:
			// EmotePlayMessage emotePlayMessage = new EmotePlayMessage();
			// emotePlayMessage.Deserialize(dataReader);
			// output = new BufferedWriter(new FileWriter(Info.server +
			// "General.txt", true));
			// output.append(System.lineSeparator() + "[" + (int)
			// emotePlayMessage.accountId + "] EmoteId : " +
			// emotePlayMessage.emoteId);
			// output.close();
			// break;
			case 702:
				GameFightJoinMessage gameFightJoinMessage = new GameFightJoinMessage();
				gameFightJoinMessage.Deserialize(dataReader);
				Info.joinedFight = true;
				Info.isTurn = false;
				Info.initFight = false;
				Communication.sendToModel(String.valueOf(Info.botInstance), String.valueOf(++Info.msgIdModel), "m", "rtn", "startFight", new Object[] {});
				fight.sendToFightAlgo("startfight", new Object[] { (int) Info.mapId });
			break;
			case 956:
				SequenceEndMessage sequenceEndMessage = new SequenceEndMessage();
				sequenceEndMessage.Deserialize(dataReader);
				if (sequenceEndMessage.authorId == Info.actorId)
				{
					Thread.sleep(1000);
					sendToServer(new GameActionAcknowledgementMessage(true, sequenceEndMessage.actionId), GameActionAcknowledgementMessage.ProtocolId, "Game Action Acknowledgement Message");
				}
				if (!fight.getSpellToSend().equals(""))
				{
					fight.sendToFightAlgo("c", new Object[] { fight.getSpellToSend() });
				}
				if (Info.isTurn)
				{
					fight.fightTurn();
				}
			break;
			case 715:
				sendToServer(new GameFightTurnReadyMessage(true), GameFightTurnReadyMessage.ProtocolId, "Turn ready");
			break;
			case 719:
				GameFightTurnEndMessage gameFightTurnEndMessage = new GameFightTurnEndMessage();
				gameFightTurnEndMessage.Deserialize(dataReader);
				if (gameFightTurnEndMessage.id == Info.actorId)
				{
					Info.isTurn = false;
				}
				fight.sendToFightAlgo("p", new Object[] { fight.getId(gameFightTurnEndMessage.id) });
			break;
			case 703:
				fight.gameFightPlacementPossiblePositionsMessage = new GameFightPlacementPossiblePositionsMessage();
				fight.gameFightPlacementPossiblePositionsMessage.Deserialize(dataReader);
				// TODO LYSANDRE
				// Fight.setBeginingPosition();
				SwingUtilities.invokeLater(new Runnable() {
					public void run()
					{
						try
						{
							Thread.sleep(2500);
							fight.fightReady();
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			break;
			case 5696:
				fight.gameEntitiesDispositionMessage = new GameEntitiesDispositionMessage();
				fight.gameEntitiesDispositionMessage.Deserialize(dataReader);
			break;
			case 5921:
				fight.gameFightSynchronizeMessage = new GameFightSynchronizeMessage();
				fight.gameFightSynchronizeMessage.Deserialize(dataReader);
				if (!Info.initFight)
				{
					fight.sendToFightAlgo("s", new Object[] { fight.init() }, fight.entities);
					Info.initFight = true;
				}
			break;
			case 6465:
				Info.isTurn = true;
				fight.fightTurn();
			break;
			case 955:
				fight.spellToSend = "";
			break;
			case 1010:
				GameActionFightSpellCastMessage gameActionFightSpellCastMessage = new GameActionFightSpellCastMessage();
				gameActionFightSpellCastMessage.Deserialize(dataReader);
				fight.spellToSend += fight.getId(gameActionFightSpellCastMessage.sourceId) + "," + CreateMap.rotate(new int[] { gameActionFightSpellCastMessage.destinationCellId % 14, gameActionFightSpellCastMessage.destinationCellId / 14 })[0] + ","
					+ CreateMap.rotate(new int[] { gameActionFightSpellCastMessage.destinationCellId % 14, gameActionFightSpellCastMessage.destinationCellId / 14 })[1] + "," + gameActionFightSpellCastMessage.spellId;
			break;
			case 6312:
				GameActionFightLifePointsLostMessage gameActionFightLifePointsLostMessage = new GameActionFightLifePointsLostMessage();
				gameActionFightLifePointsLostMessage.Deserialize(dataReader);
				fight.spellToSend += ",[" + fight.getId(gameActionFightLifePointsLostMessage.targetId) + "," + gameActionFightLifePointsLostMessage.loss + "," + gameActionFightLifePointsLostMessage.permanentDamages + "]";
			break;
			case 6070:
				GameActionFightDispellableEffectMessage gameActionFightDispellableEffectMessage = new GameActionFightDispellableEffectMessage();
				gameActionFightDispellableEffectMessage.Deserialize(dataReader);
				fight.spellToSend += ",[" + fight.getId(gameActionFightDispellableEffectMessage.effect.targetId) + "," + gameActionFightDispellableEffectMessage.effect.effectId + "," + gameActionFightDispellableEffectMessage.effect.turnDuration + "," + gameActionFightDispellableEffectMessage.effect.dispelable + "]";
			break;
			case 713:
				GameFightTurnListMessage gameFightTurnListMessage = new GameFightTurnListMessage();
				gameFightTurnListMessage.Deserialize(dataReader);
				if (!Info.initFight)
				{
					fight.turnListId = gameFightTurnListMessage.ids;
				}
		}
	}

	public void buildMessage(DofusDataReader reader) throws Exception
	{
		if (reader.available() <= 0) { return; }

		// Packet split
		if (bigPacketLengthToFull != 0)
		{
			if (reader.available() <= bigPacketLengthToFull)
			{
				bigPacketLengthToFull -= reader.available();
				byte[] destination = new byte[bigPacketData.length + reader.available()];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(reader.available()), 0, destination, bigPacketData.length, reader.available());
				this.bigPacketData = destination;
			}
			else if (reader.available() > bigPacketLengthToFull)
			{
				byte[] destination = new byte[bigPacketData.length + bigPacketLengthToFull];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(bigPacketLengthToFull), 0, destination, bigPacketData.length, bigPacketLengthToFull);
				bigPacketLengthToFull = 0;
				this.bigPacketData = destination;
			}
			if (bigPacketLengthToFull == 0)
			{
				// System.out.println("\n----------------------------------");
				// System.out.println("[Reï¿½u] ID = " + bigPacketId);
				// System.out.println("[Reï¿½u] ID = " + bigPacketId + " |
				// Taille
				// du contenu = " + bigPacketData.length + "\n[Data] : " +
				// bytesToString(bigPacketData, "%02X", false));
				TreatPacket(bigPacketId, bigPacketData);
				// System.out.println("\n----------------------------------");
				bigPacketData = null;
				bigPacketId = 0;
			}
		}
		else
		{
			if (this.message == null)
			{
				this.message = new Message();
			}
			message.build(reader);
			if (message.getId() != 0 && message.bigPacketLength == 0)
			{
				//
				// System.out.println("\n----------------------------------");
				// System.out.println("[Reï¿½u] ID = " + message.getId());
				// System.out.println("[Reï¿½u] ID = " + message.getId() + " |
				// Taille du contenu = " + message.getLength() + "\n[Data] : " +
				// bytesToString(message.getData(), "%02X", false));
				TreatPacket(message.getId(), message.getData());
				// System.out.println("\n----------------------------------");
			}
			else if (message.getId() != 0 && message.bigPacketLength != 0)
			{
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
	public void sendToServer(NetworkMessage message, int id, String s) throws Exception
	{
		Info.setBooleanToFalse();
		LatencyFrame.latestSent();
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		DofusDataWriter writer = new DofusDataWriter(bous);
		message.Serialize(writer);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		byte[] wrote = WritePacket(writer, bous, id);
		dout.write(wrote);
		dout.flush();
		append("[" + id + "]	[Envoi] " + s, true);
	}

	private byte[] WritePacket(DofusDataWriter writer, ByteArrayOutputStream bous, int id) throws Exception
	{
		byte[] data = bous.toByteArray();
		writer.Clear();
		byte num = ComputeTypeLen(data.length);
		int num1 = SubComputeStaticHeader(id, num);
		writer.writeShort((short) num1);
		writer.writeInt(MessageUtil.InstanceId++);
		switch (num)
		{
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

	private byte ComputeTypeLen(int param1)
	{
		byte num;
		if (param1 > 65535)
			num = 3;
		else if (param1 <= 255)
			num = (byte) (param1 <= 0 ? 0 : 1);
		else
			num = 2;
		return num;
	}

	private int SubComputeStaticHeader(int id, byte typeLen)
	{
		return (id << 2) | typeLen;
	}

	public String bytesToString(byte[] bytes, String format, boolean spacer)
	{
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes)
		{
			sb.append(String.format(format, b));
			if (spacer) sb.append(" ");
		}
		return sb.toString();
	}

	private void HandleHelloConnectMessage(byte[] key, String salt) throws Exception
	{
		VersionExtended versionExtended = new VersionExtended(2, 45, 3, 0, 0, 0, 1, 1);
		byte[] credentials = Crypto.encrypt(key, Info.nameAccount, Info.password, salt);
		List<Integer> credentialsArray = new ArrayList<Integer>();
		for (byte b : credentials)
		{
			credentialsArray.add((int) b);
		}
		List<Integer> failedAttempts = new ArrayList<Integer>();
		IdentificationMessage identification = new IdentificationMessage(versionExtended, "fr", credentialsArray, 0, true, false, false, 0, failedAttempts);
		sendToServer(identification, IdentificationMessage.ProtocolId, "Identification...");
	}

	private void HandleServersListMessage(int i) throws Exception
	{
		ServerSelectionMessage select = new ServerSelectionMessage(i);
		sendToServer(select, ServerSelectionMessage.ProtocolId, "Selection du serveur...");
	}

	private void HandleRawDataMessage() throws Exception
	{
		List<Integer> tt = new ArrayList<>();
		for (int i = 0; i <= 255; i++)
		{
			int rand = ThreadLocalRandom.current().nextInt(-127, 127);
			tt.add(rand);
		}
		CheckIntegrityMessage RDM = new CheckIntegrityMessage(tt);
		sendToServer(RDM, CheckIntegrityMessage.ProtocolId, "Check Integrity Message...");
	}

	private void HandleSelectedDataServer(String address, int port, List<Integer> ticket) throws IOException
	{
		Ticket = ticket;
		this.socket.close();
		this.socket = new Socket(address, port);
	}

	private void HandleAuthentificationTicketMessage() throws Exception
	{
		byte[] encryptedTicket = new byte[Ticket.size()];
		for (int i = 0; i < Ticket.size(); i++)
		{
			encryptedTicket[i] = Ticket.get(i).byteValue();
		}
		String decryptedTicket = Crypto.decryptAESkey(encryptedTicket);
		AuthenticationTicketMessage authenticationTicketMessage = new AuthenticationTicketMessage("fr", decryptedTicket);
		sendToServer(authenticationTicketMessage, AuthenticationTicketMessage.ProtocolId, "Authentification du ticket...");
	}

	private void HandleCharacterListRequestMessage() throws Exception
	{
		sendToServer(new NetworkMessageEmpty(), 150, "Character list request");
	}

	private void HandleCharacterSelectionMessage(long id) throws Exception
	{
		CharacterSelectionMessage characterSelectionMessage = new CharacterSelectionMessage(id);
		sendToServer(characterSelectionMessage, CharacterSelectionMessage.ProtocolId, "Selection du personnage...");
	}

	private void HandleFriendIgnoreSpouseMessages() throws Exception
	{
		// Send friend list request
		sendToServer(new NetworkMessageEmpty(), 4001, "Friend list request");
		// Send ignored list request
		sendToServer(new NetworkMessageEmpty(), 5676, "Ignored list request");
		// Send spouse list request
		sendToServer(new NetworkMessageEmpty(), 6355, "Spouse list request");
	}

	private void HandleClientKeyMessage(String key) throws Exception
	{
		ClientKeyMessage clientKeyMessage = new ClientKeyMessage(key);
		sendToServer(clientKeyMessage, ClientKeyMessage.ProtocolId, "Client key");
	}

	private void HandleGameContextCreateMessage() throws Exception
	{
		sendToServer(new NetworkMessageEmpty(), 250, "Game context create request");
	}

	private void HandleSequenceNumberMessage() throws Exception
	{
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(LatencyFrame.Sequence++);
		sendToServer(sequenceNumberMessage, SequenceNumberMessage.ProtocolId, "Sequence number");
	}

	// private static void HandleObjectAveragePricesGetMessage() throws
	// Exception
	// {
	// // Send object average price request
	// // sendToServer(new NetworkMessageEmpty(), 6334, "Object average price
	// // request");
	// // Send Quest List Request
	// // sendToServer(new NetworkMessageEmpty(), 5623, "Quest list request");
	// // Send Channel enabling message
	// // ChannelEnablingMessage channelEnablingMessage = new
	// // ChannelEnablingMessage((byte) 7, false);
	// // sendToServer(channelEnablingMessage,
	// // ChannelEnablingMessage.ProtocolId, "Channel enabling");
	// }

	private void HandleMapRequestMessage(double mapId) throws Exception
	{
		MapInformationsRequestMessage informationsRequestMessage = new MapInformationsRequestMessage(mapId);
		sendToServer(informationsRequestMessage, MapInformationsRequestMessage.ProtocolId, "Map info request");
	}

	private void HandleMapComplementaryInformationsDataMessage() throws InterruptedException
	{
		Process p;
		try
		{
			String s = Paths.get("").toAbsolutePath().toString();
			int i = s.indexOf("DatBot");
			s = s.substring(0, i + 6);
			p = new ProcessBuilder(s + "/DatBot.Interface/utils/maps/MapManager/MapManager.exe", String.valueOf((int) Info.mapId)).start();
			p.waitFor();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		new JSON("MapInfo", Info.mapId);
		new JSON("MapInfoComplete", Info.mapId);
	}

	private void HandleLatencyMessage() throws Exception
	{
		BasicLatencyStatsMessage basicLatencyStatsMessage = new BasicLatencyStatsMessage(LatencyFrame.getLatencyAvg(), LatencyFrame.getSamplesCount(), LatencyFrame.GetSamplesMax());
		sendToServer(basicLatencyStatsMessage, BasicLatencyStatsMessage.ProtocolId, "Latency message");
	}

	public Interactive getInteractive()
	{
		return interactive;
	}

	public void setInteractive(Interactive interactive)
	{
		this.interactive = interactive;
	}

	public Bank getBank()
	{
		return bank;
	}

	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	public Movement getMovement()
	{
		return movement;
	}

	public void setMovement(Movement movement)
	{
		this.movement = movement;
	}
}
