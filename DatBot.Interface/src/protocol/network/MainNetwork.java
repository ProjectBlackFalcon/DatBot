package protocol.network;  


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import Game.Entity;
import Game.InfoAccount;
import Game.map.Map;
import Main.MainPlugin;
import protocol.network.messages.connection.HelloConnectMessage;
import protocol.network.messages.connection.IdentificationFailedMessage;
import protocol.network.messages.connection.IdentificationSuccessMessage;
import protocol.network.messages.connection.SelectedServerDataMessage;
import protocol.network.messages.connection.ServersListMessage;
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.messages.game.context.GameContextReadyMessage;
import protocol.network.messages.game.context.GameMapMovementMessage;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightDetailsMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightListMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToFightServerMessage;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaSwitchToGameServerMessage;
import protocol.network.messages.handshake.ProtocolRequired;
import protocol.network.messages.queues.LoginQueueStatusMessage;
import protocol.network.types.connection.GameServerInformations;
import protocol.network.util.DofusDataReader;
import protocol.network.util.FlashKeyGenerator;
import protocol.network.util.SwitchNameClass;
  

public class MainNetwork {  
	
	public static final String port = "5555";
	private Message message;
	private byte[] packetArray;
	private	DofusDataReader reader;

//	public MainNetwork(Socket socket) throws Exception{
//		this.socket = socket;
//		packetArray = new byte[socket.getInputStream().available()];
//		if(packetArray.length != 0){
//			socket.getInputStream().read(packetArray);
//			this.reader = new DofusDataReader(new ByteArrayInputStream(packetArray));
//			buildMessage(reader);
//		}
//	}
	
	public MainNetwork(byte[] packetArray) throws Exception{
		reader = new DofusDataReader(new ByteArrayInputStream(packetArray));
		buildMessage(reader);
	}
    /** 
     * Main startup method 
     *  
     * @param args 
     *          ignored 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
        StringBuilder errbuf = new StringBuilder(); // For any error msgs  
  
        /*************************************************************************** 
         * First get a list of devices on this system 
         **************************************************************************/  
        
        int r = Pcap.findAllDevs(alldevs, errbuf);  
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
            System.err.printf("Can't read list of devices, error is %s", errbuf  
                .toString());  
            return;  
        }  
  
        PcapIf device = alldevs.get(0); // We know we have atleast 1 device  
        System.out  .printf("Choosing '%s' on your behalf:\n",  
                (device.getDescription() != null) ? device.getDescription(): device.getName());  
  
        /*************************************************************************** 
         * Second we open up the selected device 
         **************************************************************************/  
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 1;           // 10 seconds in millis = 10 * 1000;  
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
  
        if (pcap == null) {  
            System.err.printf("Error while opening device for capture: "  
                + errbuf.toString());  
            return;  
        }  
        
        // Filter for port 5555 or 443 ==== port used by Dofus
      	PcapBpfProgram program = new PcapBpfProgram();  
    	String expression = "tcp port " + port;  
    	int optimize = 0;         // 0 = false  
    	int netmask = 0xFFFFFF00; // 255.255.255.0  
    	          
    	if (pcap.compile(program, expression, optimize, netmask) != Pcap.OK) {  
    	  System.err.println(pcap.getErr());  
    	  return;  
    	}  
    	          
    	if (pcap.setFilter(program) != Pcap.OK) {  
    	  System.err.println(pcap.getErr());  
    	  return;         
    	}
    	
    	
  
        /*************************************************************************** 
         * Third we create a packet handler which will receive packets from the 
         * libpcap loop. 
         **************************************************************************/  
        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {  
			public void nextPacket(PcapPacket packet, String user) {  
            	// Capturing data
            	JBuffer buffer = packet.getHeader(new Payload());  
            	if(buffer != null){
                	int size = buffer.size();
                	if (packet.getCaptureHeader().caplen() > 65) {
                		try {
							new MainNetwork(buffer.getByteArray(0, size));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
            	}
            }  
        };  
  
        /*************************************************************************** 
         * Fourth we enter the loop and tell it to capture 10 packets. The loop 
         * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which 
         * is needed by JScanner. The scanner scans the packet buffer and decodes 
         * the headers. The mapping is done automatically, although a variation on 
         * the loop method exists that allows the programmer to sepecify exactly 
         * which protocol ID to use as the data link type for this pcap interface. 
         *************************************************************************/  
        
        pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "");  
  
        /*************************************************************************** 
         * Last thing to do is close the pcap handle 
         **************************************************************************/  
        pcap.close();  
        System.out.println("Closed");
    }
    
	// Big packet split
	private int bigPacketLengthToFull;// Length needed to finish the packet
	private int bigPacketId;
	private byte[] bigPacketData;
    
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
//				System.out.println("[Reçu] ID = " + bigPacketId);
//				 System.out.println("[Reçu] ID = " + bigPacketId + " | Taille du contenu = " + bigPacketData.length + "\n[Data] : " +
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
//				System.out.println("[Reçu] ID = " + message.getId());
				// System.out.println("[Reçu] ID = " + message.getId() + " |
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
    
    private void TreatPacket(int packet_id, byte[] packet_content) throws Exception {
		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(packet_content));
		SwitchNameClass name = new SwitchNameClass(packet_id);
		// MàJ timing
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		String timing = formatter.format(time);
//		appendToPane(text, "[" + timing + "] ", Color.black);
//		appendToPane(text, "[" + packet_id + "]\tTaille : " + packet_content.length, new Color(0, 110, 0));
//		if (packet_content.length > 9) {
//			appendToPane(text, "\t" + name.name + "\n", new Color(0, 110, 0));
//		} else {
//			appendToPane(text, "\t\t" + name.name + "\n", new Color(0, 110, 0));
//		}
		System.out.println("[" + timing + "] [" + packet_id + "] - " + name.name);
		switch(packet_id){
		case 6575:
			GameRolePlayArenaSwitchToFightServerMessage arenaSwitchToFightServerMessage = new GameRolePlayArenaSwitchToFightServerMessage();
			arenaSwitchToFightServerMessage.Deserialize(dataReader);
			break;
		case 220:
			CurrentMapMessage currentMapMessage = new CurrentMapMessage();
			currentMapMessage.Deserialize(dataReader);
			System.out.println(currentMapMessage.mapId);
			break;
		case 6071:
			GameContextReadyMessage a = new GameContextReadyMessage();
			a.Deserialize(dataReader);
			System.out.println(a.mapId);
			break;
		}
	}
}  



