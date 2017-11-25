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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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
import Game.InfoAccount;
import Game.map.Map;
import Game.movement.Movement;
import Main.MainPlugin;
import io.netty.util.internal.ThreadLocalRandom;
import protocol.frames.LatencyFrame;
import protocol.network.messages.connection.HelloConnectMessage;
import protocol.network.messages.connection.IdentificationFailedMessage;
import protocol.network.messages.connection.IdentificationMessage;
import protocol.network.messages.connection.IdentificationSuccessMessage;
import protocol.network.messages.connection.SelectedServerDataMessage;
import protocol.network.messages.connection.ServerSelectionMessage;
import protocol.network.messages.connection.ServersListMessage;
import protocol.network.messages.game.approach.AuthenticationTicketMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsMessage;
import protocol.network.messages.game.basic.BasicLatencyStatsRequestMessage;
import protocol.network.messages.game.basic.SequenceNumberMessage;
import protocol.network.messages.game.character.choice.CharacterSelectedForceReadyMessage;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.chat.channel.ChannelEnablingMessage;
import protocol.network.messages.game.context.GameContextCreateRequestMessage;
import protocol.network.messages.game.context.GameContextReadyMessage;
import protocol.network.messages.game.context.GameMapMovementMessage;
import protocol.network.messages.game.context.GameMapMovementRequestMessage;
import protocol.network.messages.game.context.fight.character.GameFightShowFighterMessage;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.messages.game.context.roleplay.MapInformationsRequestMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightDetailsMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightListMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToFightServerMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToGameServerMessage;
import protocol.network.messages.handshake.ProtocolRequired;
import protocol.network.messages.queues.LoginQueueStatusMessage;
import protocol.network.messages.security.CheckIntegrityMessage;
import protocol.network.messages.security.ClientKeyMessage;
import protocol.network.types.connection.GameServerInformations;
import protocol.network.types.game.character.choice.CharacterBaseInformations;
import protocol.network.types.version.Version;
import protocol.network.types.version.VersionExtended;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.util.FlashKeyGenerator;
import protocol.network.util.MessageUtil;
import protocol.network.util.SwitchNameClass;
import utils.JSON;

public class Network implements Runnable {

	AtomicInteger at;
	public static Socket socket;
	private static String ip;
	private Message message;
	private static List<Integer> Ticket;
	// Log window
	private JFrame f;
	private JPanel panel;
	private static JTextPane text;
	// Big packet split
	private int bigPacketLengthToFull;// Length needed to finish the packet
	private int bigPacketId;
	private byte[] bigPacketData;
	// Timing
	private static String timing;
    private Random r = new Random(); //Random for thread sleep

	
	// Plugin
	public static boolean isPacketArrived = false;
	public static boolean connectionToKoli = false;
	public static MapRunningFightListMessage fight;
	public static MapRunningFightDetailsMessage fightDetail;

	public Network(AtomicInteger at) {
		this.at = at;
		ip = "213.248.126.40";
		int port = 5555;
		try {
			socket = new Socket(ip, port);
			if (socket.isConnected()) {
				System.out.println("La connexion au serveur d'authentification est r�ussie.");
				new LatencyFrame();
			} else {
				System.out.println("La connexion au serveur d'authentification a �chou�e.");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			initComponent();
			f.pack();
			f.setVisible(true);
			reception();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initComponent() {
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

	private static void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
	
	public static void appendDebug(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		timing = formatter.format(time);
		String newSt = "[" + timing + "] " + str + "\n";
//		appendToPane(text, newSt, Color.BLACK);
		MainPlugin.frame.appendDebug(str);
	}

	public void reception() throws Exception {
		while (!Network.socket.isClosed()) {

			InputStream data = socket.getInputStream();
			int available = data.available();
			byte[] buffer = new byte[available];
			
			if (available > 0) {
				//Latency
				LatencyFrame.updateLatency();
				// System.out.println("Available : " + available);
				data.read(buffer, 0, available);
				// Sometime there is so many pc that the PC can't keep up
				// Need to try with a better one
				// Packet seems to be split if to fast
				Thread.sleep(200);
				DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(buffer));
				buildMessage(reader);
			}
		}
		socket.close();
	}
	
	public static void waitForPacket() throws InterruptedException{
		while(!isPacketArrived){
			Thread.sleep(500);
		}
		isPacketArrived = true;
	}

	private void TreatPacket(int packet_id, byte[] packet_content) throws Exception {
		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(packet_content));
		SwitchNameClass name = new SwitchNameClass(packet_id);
		// M�J timing
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		timing = formatter.format(time);
		appendToPane(text, "[" + timing + "] ", Color.black);
		appendToPane(text, "[" + packet_id + "]\tTaille : " + packet_content.length, new Color(0, 110, 0));
		if (packet_content.length > 9) {
			appendToPane(text, "\t" + name.name + "\n", new Color(0, 110, 0));
		} else {
			appendToPane(text, "\t\t" + name.name + "\n", new Color(0, 110, 0));
		}
		MainPlugin.frame.appendDebug("[" + timing + "] [" + packet_id + "] - " + name.name);
		switch (packet_id) {
		case 1:
			ProtocolRequired protoc = new ProtocolRequired();
			protoc.Deserialize(dataReader);
			break;
		case 3:
			HelloConnectMessage hello = new HelloConnectMessage();
			hello.Deserialize(dataReader);
    		byte [] key = new byte[hello.key.size()];
    		for(int i = 0; i < hello.key.size(); i++) {
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
			int serverId = 0;
			for (GameServerInformations b : servers.servers) {
				if(b.charactersCount > 0)
					serverId = b.id;
			}
			HandleServersListMessage(serverId);
			break;
		case 42:
			SelectedServerDataMessage selectServer = new SelectedServerDataMessage();
			if (dataReader.available() == 0) {
				this.message = null;
				return;
			}
			selectServer.Deserialize(dataReader);
			HandleSelectedDataServer(selectServer.address, selectServer.port, selectServer.ticket);
			break;
		case 101:
			HandleAuthentificationTicketMessage();
			if(connectionToKoli){
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
//			int j = 0;
//			for (int i = 0 ; i < charactersListMessage.characters.size() ; i++) {
//				if(charactersListMessage.characters.get(i).name.equals(InfoAccount.name)){
//					HandleCharacterSelectionMessage(charactersListMessage.characters.get(i).id);
//					InfoAccount.actorId = charactersListMessage.characters.get(i).id;
//					InfoAccount.lvl = charactersListMessage.characters.get(i).level;
//					j = 1;
//					break;
//				} 
//			}
//			if(j==0) throw new Error("Wrong character name !");
			HandleCharacterSelectionMessage(charactersListMessage.characters.get(0).id);
			InfoAccount.name = charactersListMessage.characters.get(0).name;
			InfoAccount.actorId = charactersListMessage.characters.get(0).id;
			InfoAccount.lvl = charactersListMessage.characters.get(0).level;
			break;
		case 1301:
			HandleFriendIgnoreSpouseMessages();
			break;
		case 4002:
			HandleClientKeyMessage(FlashKeyGenerator.GetRandomFlashKey(InfoAccount.name));
			HandleGameContextCreateMessage();
			break;
		case 500:
			HandleObjectAveragePricesGetMessage();
			break;
		case 220:
			CurrentMapMessage currentMapMessage = new CurrentMapMessage();
			currentMapMessage.Deserialize(dataReader);
			InfoAccount.mapId = currentMapMessage.mapId;
			if(connectionToKoli){
				sendToServer(new GameContextReadyMessage(currentMapMessage.mapId), GameContextReadyMessage.ProtocolId, "Context ready");
			} else {
				HandleMapRequestMessage(currentMapMessage.mapId);
			}
			break;
		case 226:
			at.set(1);
			MapComplementaryInformationsDataMessage complementaryInformationsDataMessage = new MapComplementaryInformationsDataMessage();
			complementaryInformationsDataMessage.Deserialize(dataReader);
			if(!connectionToKoli){
				for (int i = 0; i < complementaryInformationsDataMessage.actors.size(); i++)
					if (complementaryInformationsDataMessage.actors.get(i).contextualId == InfoAccount.actorId)
						InfoAccount.cellId = complementaryInformationsDataMessage.actors.get(i).disposition.cellId;
					else
						Map.Entities.add(new Entity(complementaryInformationsDataMessage.actors.get(i).disposition.cellId, complementaryInformationsDataMessage.actors.get(i).contextualId));
				HandleMapComplementaryInformationsDataMessage();
			}
			break;
		case 891:
			at.set(1);
			break;
		case 951:
			GameMapMovementMessage gameMapMovementMessage = new GameMapMovementMessage();
			gameMapMovementMessage.Deserialize(dataReader);
			if(gameMapMovementMessage.actorId == InfoAccount.actorId){
				InfoAccount.cellId = gameMapMovementMessage.keyMovements.get(gameMapMovementMessage.keyMovements.size() - 1);
				MainPlugin.frame.append("D�placement r�ussi !");
				MainPlugin.frame.append("CellId : " + InfoAccount.cellId);
			}
			break;
		case 6316 :
			HandleSequenceNumberMessage();
			break;
		case 5816:
			HandleLatencyMessage();
			break;
		case 5743:
			fight = new MapRunningFightListMessage();
			fight.Deserialize(dataReader);
			isPacketArrived = true;
			break;
		case 5751:
			fightDetail = new MapRunningFightDetailsMessage();
			fightDetail.Deserialize(dataReader);
			isPacketArrived = true;
			break;
		case 6575:
			connectionToKoli = true;
			GameRolePlayArenaSwitchToFightServerMessage arenaSwitchToFightServerMessage = new GameRolePlayArenaSwitchToFightServerMessage();
			arenaSwitchToFightServerMessage.Deserialize(dataReader);
			Ticket = arenaSwitchToFightServerMessage.ticket;
			Network.socket.close();
			Network.socket = new Socket(arenaSwitchToFightServerMessage.address,5555);
			break;
		case 6574:
			connectionToKoli = false;
			LatencyFrame.Sequence = 1;
			GameRolePlayArenaSwitchToGameServerMessage arenaSwitchToGameServerMessage = new GameRolePlayArenaSwitchToGameServerMessage();
			arenaSwitchToGameServerMessage.Deserialize(dataReader);
			Ticket = arenaSwitchToGameServerMessage.ticket;
			Network.socket.close();
			Network.socket = new Socket(ip,5555);
			break;
		case 6068:
			sendToServer(new CharacterSelectedForceReadyMessage(), CharacterSelectedForceReadyMessage.ProtocolId, "Character force selection");
			break;
		case 6471:
			if(connectionToKoli){
				sendToServer(new GameContextCreateRequestMessage(), GameContextCreateRequestMessage.ProtocolId, "Context creation request");
			}
			break;
		case 5864:
			new GameFightShowFighterMessage().Deserialize(dataReader);
			break;
		}
	}

	public void buildMessage(DofusDataReader reader) throws Exception {
		if (reader.available() <= 0) {
			return;
		}

		// Packet split
		if (bigPacketLengthToFull != 0) {
			if (reader.available() <= bigPacketLengthToFull) {
				bigPacketLengthToFull -= reader.available();
				byte[] destination = new byte[bigPacketData.length + reader.available()];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(reader.available()), 0, destination, bigPacketData.length,
						reader.available());
				this.bigPacketData = destination;
			} else if (reader.available() > bigPacketLengthToFull) {
				byte[] destination = new byte[bigPacketData.length + bigPacketLengthToFull];
				System.arraycopy(bigPacketData, 0, destination, 0, bigPacketData.length);
				System.arraycopy(reader.readBytes(bigPacketLengthToFull), 0, destination, bigPacketData.length,
						bigPacketLengthToFull);
				bigPacketLengthToFull = 0;
				this.bigPacketData = destination;
			}
			if (bigPacketLengthToFull == 0) {
				// System.out.println("\n----------------------------------");
//				System.out.println("[Re�u] ID = " + bigPacketId);
				// System.out.println("[Re�u] ID = " + bigPacketId + " | Taille
				// du contenu = " + bigPacketData.length + "\n[Data] : " +
				// bytesToString(bigPacketData, "%02X", false));
				TreatPacket(bigPacketId, bigPacketData);
				// System.out.println("\n----------------------------------");
				bigPacketData = null;
				bigPacketId = 0;
			}
		} else {
			if (this.message == null) {
				this.message = new Message();
			}
			message.build(reader);
			if (message.getId() != 0 && message.bigPacketLength == 0) {
				//
				// System.out.println("\n----------------------------------");
//				System.out.println("[Re�u] ID = " + message.getId());
				// System.out.println("[Re�u] ID = " + message.getId() + " |
				// Taille du contenu = " + message.getLength() + "\n[Data] : " +
				// bytesToString(message.getData(), "%02X", false));
				TreatPacket(message.getId(), message.getData());
				// System.out.println("\n----------------------------------");
			} else if (message.getId() != 0 && message.bigPacketLength != 0) {
				bigPacketLengthToFull = message.bigPacketLength;
				bigPacketId = message.getId();
				bigPacketData = message.getData();
			}
		}
		this.message = null;
		buildMessage(reader);
	}
	
	/*
	 * Send packet to server
	 * message = type;
	 * id = id packet;
	 * String s = String displayed on log
	 */
	public static void sendToServer(NetworkMessage message, int id, String s) throws Exception {
		LatencyFrame.latestSent();
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		DofusDataWriter writer = new DofusDataWriter(bous);
		message.Serialize(writer);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		byte[] wrote = WritePacket(writer, bous, id);
		dout.write(wrote);
		dout.flush();
		appendToPane(text, "[" + timing + "] ", Color.black);
		appendToPane(text, "[" + id + "]	[Envoi] " + s + " \n", new Color(0, 0, 140));
	}

	private static byte[] WritePacket(DofusDataWriter writer, ByteArrayOutputStream bous, int id) throws Exception {
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

	private static byte ComputeTypeLen(int param1) {
		byte num;
		if (param1 > 65535)
			num = 3;
		else if (param1 <= 255)
			num = (byte) (param1 <= 0 ? 0 : 1);
		else
			num = 2;
		return num;
	}

	private static int SubComputeStaticHeader(int id, byte typeLen) {
		return (id << 2) | typeLen;
	}

	public static void print(byte[] byteArray) {
		System.out.println(bytesToString(byteArray, "%X", false));
	}

	public static String bytesToString(byte[] bytes, String format, boolean spacer) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format(format, b));
			if (spacer)
				sb.append(" ");
		}
		return sb.toString();
	}

	private static void HandleHelloConnectMessage(byte[] key, String salt) throws Exception {
		VersionExtended versionExtended = new VersionExtended(2, 44, 7, 3, 0, 0, 1, 1);
		String login = "ceciestuntest";
		String password = "ceciestlemdp1";
		byte[] credentials = Crypto.encrypt(key, login, password, salt);
		List<Integer> credentialsArray = new ArrayList<Integer>();
		for (byte b : credentials) {
			credentialsArray.add((int) b);
		}
		List<Integer> failedAttempts = new ArrayList<Integer>();
		IdentificationMessage identification = new IdentificationMessage(versionExtended, "fr", credentialsArray, 0, true, false, false, 0, failedAttempts);
		sendToServer(identification, IdentificationMessage.ProtocolId, "Identification...");
	}

	private static void HandleServersListMessage(int i) throws Exception {
		ServerSelectionMessage select = new ServerSelectionMessage(i);
		sendToServer(select, ServerSelectionMessage.ProtocolId, "Selection du serveur...");
	}

	private static void HandleRawDataMessage() throws Exception {
		List<Integer> tt = new ArrayList<>();
		for (int i = 0; i <= 255; i++) {
			int rand = ThreadLocalRandom.current().nextInt(-127, 127);
			tt.add(rand);
		}
		CheckIntegrityMessage RDM = new CheckIntegrityMessage(tt);
		sendToServer(RDM, CheckIntegrityMessage.ProtocolId, "Check Integrity Message...");
	}

	private static void HandleSelectedDataServer(String address, int port, List<Integer> ticket) throws IOException {
		Ticket = ticket;
//		ip = address;
		Network.socket.close();
		Network.socket = new Socket(address, port);
	}

	private static void HandleAuthentificationTicketMessage() throws Exception {
		byte[] encryptedTicket = new byte[Ticket.size()];
		for (int i = 0; i < Ticket.size(); i++) {
			encryptedTicket[i] = Ticket.get(i).byteValue();
		}
		String decryptedTicket = Crypto.decryptAESkey(encryptedTicket);
		AuthenticationTicketMessage authenticationTicketMessage = new AuthenticationTicketMessage("fr",decryptedTicket);
		sendToServer(authenticationTicketMessage, AuthenticationTicketMessage.ProtocolId,
				"Authentification du ticket...");
	}

	private static void HandleCharacterListRequestMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 150, "Character list request");
	}

	private static void HandleCharacterSelectionMessage(long id) throws Exception {
		CharacterSelectionMessage characterSelectionMessage = new CharacterSelectionMessage(id);
		sendToServer(characterSelectionMessage, CharacterSelectionMessage.ProtocolId, "Selection du personnage...");
	}

	private static void HandleFriendIgnoreSpouseMessages() throws Exception {
		// Send friend list request
		sendToServer(new NetworkMessageEmpty(), 4001, "Friend list request");
		// Send ignored list request
		sendToServer(new NetworkMessageEmpty(), 5676, "Ignored list request");
		// Send spouse list request
		sendToServer(new NetworkMessageEmpty(), 6355, "Spouse list request");
	}

	private static void HandleClientKeyMessage(String key) throws Exception {
		ClientKeyMessage clientKeyMessage = new ClientKeyMessage(key);
		sendToServer(clientKeyMessage, ClientKeyMessage.ProtocolId, "Client key");
	}

	private static void HandleGameContextCreateMessage() throws Exception {
		sendToServer(new NetworkMessageEmpty(), 250, "Game context create request");
	}

	private static void HandleSequenceNumberMessage() throws Exception {
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(LatencyFrame.Sequence++);
		sendToServer(sequenceNumberMessage, SequenceNumberMessage.ProtocolId, "Sequence number");
	}

	private static void HandleObjectAveragePricesGetMessage() throws Exception {
		// Send object average price request
//		sendToServer(new NetworkMessageEmpty(), 6334, "Object average price request");
		// Send Quest List Request
		sendToServer(new NetworkMessageEmpty(), 5623, "Quest list request");
		// Send Channel enabling message
//		ChannelEnablingMessage channelEnablingMessage = new ChannelEnablingMessage((byte) 7, false);
//		sendToServer(channelEnablingMessage, ChannelEnablingMessage.ProtocolId, "Channel enabling");
	}

	private static void HandleMapRequestMessage(double mapId) throws Exception {
		MapInformationsRequestMessage informationsRequestMessage = new MapInformationsRequestMessage(mapId);
		sendToServer(informationsRequestMessage, MapInformationsRequestMessage.ProtocolId, "Map info request");
	}

	private static void HandleMapComplementaryInformationsDataMessage() {
		try {
			Process p = new ProcessBuilder("C:\\Users\\baptiste\\Documents\\Dofus Bot\\CookieMapManager\\Cookie\\bin\\Debug\\Cookie.exe",String.valueOf((int) InfoAccount.mapId)).start();
			p.waitFor();
			new JSON("MapInfo",InfoAccount.mapId);
			new JSON("MapInfo2", InfoAccount.mapId);
			MainPlugin.frame.append("Map : [" + InfoAccount.coords[0] + ";" + InfoAccount.coords[1] +  "]");	
			MainPlugin.frame.append("CellId : " + InfoAccount.cellId);
			InfoAccount.waitForMov = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void HandleLatencyMessage() throws Exception {
		BasicLatencyStatsMessage basicLatencyStatsMessage = new BasicLatencyStatsMessage(LatencyFrame.getLatencyAvg(),LatencyFrame.getSamplesCount(),LatencyFrame.GetSamplesMax());
		sendToServer(basicLatencyStatsMessage, BasicLatencyStatsMessage.ProtocolId, "Latency message");
	}

}