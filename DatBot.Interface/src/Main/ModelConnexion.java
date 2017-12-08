package Main;

import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Game.Info;
import Game.Plugin.Bank;
import Game.Plugin.Interactive;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.map.Map;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.dialog.LeaveDialogMessage;
import protocol.network.messages.game.dialog.LeaveDialogRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectAddedMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertExistingFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListWithQuantityToInvMessage;
import utils.JSON;

public class ModelConnexion implements Runnable {
	
	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...] 
	
	InteractiveUseRequestMessage interactiveUseRequestMessage;
	NpcGenericActionRequestMessage npcGenericactionRequestMessage;
	private boolean bankOppened;

	@Override
	public void run() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s;
			while(true){
				Thread.sleep(200);
				Info.newMap = false;
				s = bufferRead.readLine();
				String [] message = s.split(";");
				message[5] = message[5].substring(1, message[5].length()-1);
				
				switch(message[4]){
				
				case "connect":
					String [] info = message[5].split(",");
					Info.nameAccount = info[0].substring(1, info[0].length()-1);
					Info.password = info[1].substring(2, info[1].length()-1);
					Info.name = info[2].substring(2, info[2].length()-1);
					Info.server = info[3].substring(2, info[3].length()-1);
					System.out.println("NameAccount : " +  Info.nameAccount + " password : " + Info.password + " name : " + Info.name + " server : " + Info.server);
					int index = 0;
					while(!Info.isConnected){
						Thread.sleep(2000);
						index += 1;
						if (index == 30) {
							throw new java.lang.Error("Connection timed out");
						}						
					}
					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					break;
				case "disconnect":
//					Info.nameAccount = "";
//					Info.password = "";
//					Info.name = "";
//					Info.server = "";
//					Network.socket.close();
//					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					break;
				case "getMap":
					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{
							String.valueOf("(" + Info.coords[0]) + "," + String.valueOf(Info.coords[1]) + ")" ,Info.cellId,Info.worldmap,Integer.valueOf((int) Info.mapId)});
					break;
				case "move":
					CellMovement mov = Movement.MoveToCell(Integer.parseInt(message[5]));
					if(mov == null || mov.path == null){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					} else if (Info.cellId == Integer.parseInt(message[5])) {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						mov.performMovement();
						if(Movement.moveOver()){
							if(Info.cellId == Integer.parseInt(message[5])){
								if((Map.Id == 83887104 && Info.cellId == 396) || (Map.Id == 2884617 && Info.cellId == 424) || (Map.Id == 8912911 && Info.cellId == 424)){
									while(!Info.newMap){
										Thread.sleep(50);
									}
									sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
								} else {
									sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
								}
							} else {
								sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
							}						
						} else {
							sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
						}							
					}
					break;
				case "changeMap":
					String [] infoMov = message[5].split(",");
					MapMovement mapMovement = Movement.ChangeMap(Integer.parseInt(infoMov[0]),infoMov[1].substring(2, infoMov[1].length()-1));
					if (mapMovement == null) {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
						Network.append("D�placement impossible ! Un obstacle bloque le chemin !");
					}
					else {
						mapMovement.PerformChangement();
						if(Movement.moveOver()){
							sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
						} else {
							sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
						}
					}
					break;
				case "getResources":
					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Interactive.getFarmCell()});
					break;
				case "harvest":
					if(Interactive.harvestCell(Integer.parseInt(message[5]))){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Interactive.lastItemHarvestedId,Interactive.quantityLastItemHarvested,Info.weight,Info.weigthMax});
					} 
//					else if (){
//						TODO AGGRO
//					}					
					else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "getStats":
					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Stats.getStats()});
					break;
				case "goAstrub":
					if(Map.Id == 153880835){
						npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20001,3,153880835);
						Network.sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request NPC to go to Astrub");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "goIncarnam":
					int r = Interactive.getStatue();
					if(r != -1){
						interactiveUseRequestMessage = new InteractiveUseRequestMessage(Interactive.elementIdStatue,Interactive.skillInstanceUidStatue);
						Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "getStatue":
					int statueCellId = Interactive.getStatue();
					if (statueCellId != -1){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{statueCellId});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "getBankDoor":
					if(Map.Id == 144931){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Bank.cellIdBrakmarIN,Bank.cellIdBrakmarOUT});
					} else if(Map.Id == 84674566){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Bank.cellIdAstrubIN,Bank.cellIdAstrubOUT});
					} else if(Map.Id == 147254){
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Bank.cellIdBontaIN,Bank.cellIdBontaOUT});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "goBank":
					if(Map.Id == 144931){ //Brakmar
						interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBrakmarIN,Bank.getSkill(Bank.interactiveBrakmarIN));
						Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else if(Map.Id == 84674566){ //Astrub
						interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveAstrubIN,Bank.getSkill(Bank.interactiveAstrubIN));
						Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else if(Map.Id == 147254){ //Bonta
						interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBontaIN,Bank.getSkill(Bank.interactiveBontaIN));
						Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "openBank":
					if(Map.Id == 83887104 || Map.Id == 2884617 || Map.Id == 8912911){
						npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) NPC.npc.get(0).contextualId,3,Map.Id);
						Network.sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
						if(waitToSend()){
							sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Bank.getBank()});
						} else {
							sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
						}
						bankOppened = true;
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});	
					}
					break;
				case "closeBank":
					if(bankOppened){
						Network.sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close bank");
						waitToSend();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					bankOppened = false;
					break;
				case "dropBank":
					if (bankOppened) {
						String[] toBank = message[5].split(",");
						Network.sendToServer(
								new ExchangeObjectMoveMessage(
										Integer.parseInt(toBank[0].substring(1, toBank[0].length() - 1)),
										Integer.parseInt(toBank[1].substring(2, toBank[0].length() - 1))),
								ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "getBank":
					if (bankOppened) {
						String[] fromBank = message[5].split(",");
						Network.sendToServer(
								new ExchangeObjectMoveMessage(
										Integer.parseInt(fromBank[0].substring(1, fromBank[0].length() - 1)),
										-Integer.parseInt(fromBank[1].substring(2, fromBank[0].length() - 1))),
								ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "dropBankList":
					if (bankOppened) {
						String[] toBankList = message[5].split(",");
						List<Integer> ids = new ArrayList<Integer>();
						for (String string : toBankList) {
							ids.add(Integer.parseInt(string.replaceAll("\\s+", "")));
						}
						ExchangeObjectTransfertListFromInvMessage exchangeObjectTransfertListFromInvMessage = new ExchangeObjectTransfertListFromInvMessage(
								ids);
						Network.sendToServer(exchangeObjectTransfertListFromInvMessage,
								ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list in bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "getBankList":
					if (bankOppened) {
						String[] fromBankList = message[5].split(",");
						List<Integer> ids1 = new ArrayList<Integer>();
						for (String string : fromBankList) {
							ids1.add(Integer.parseInt(string.replaceAll("\\s+", "")));
						}
						ExchangeObjectTransfertListToInvMessage exchangeObjectTransfertListToInvMessage = new ExchangeObjectTransfertListToInvMessage(
								ids1);
						Network.sendToServer(exchangeObjectTransfertListToInvMessage,
								ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "getBankKamas":
					if (bankOppened) {
						ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage = new ExchangeObjectMoveKamaMessage(
								-Integer.parseInt(message[5]));
						Network.sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId,
								"Get kamas from bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "dropBankKamas":
					if (bankOppened) {
						ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage1 = new ExchangeObjectMoveKamaMessage(
								Integer.parseInt(message[5]));
						Network.sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId,
								"Drop kamas in bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				case "dropBankAll":
					if (bankOppened) {
						ExchangeObjectTransfertAllFromInvMessage exchangeObjectTransfertAllFromInvMessage = new ExchangeObjectTransfertAllFromInvMessage();
						Network.sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId,
								"Drop all items in bank");
						if (waitToSend()) {
							sendToModel(message[0], message[1], "m", "rtn", message[4],
									new Object[] { Stats.getStats() + "," + Bank.getBank() });
						} else {
							sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
						} 
					} else {
						sendToModel(message[0], message[1], "m", "rtn", message[4], new Object[] { "False" });
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void sendToModel(String botInstance, String msgId,String dest, String msgType, String command, Object [] param){
		String newParam = "";
		for(int i = 0 ; i < param.length ; i++){
			if(i == param.length - 1){
				newParam += param[i];
			} else {
				newParam += param[i] + ", ";
			}
		}
		System.out.println(String.format("%s;%s;%s;%s;%s;[%s]",botInstance,msgId,dest,msgType,command,newParam));
	}
	
	public static boolean waitToSend() throws InterruptedException {
		while (!Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange) {
			Thread.sleep(50);
		}
		while (!Info.basicNoOperationMsg) {
			Thread.sleep(50);
		}
//		System.out.println((!Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange)
//				&& !Info.basicNoOperationMsg);
//		System.out.println(Info.newMap + " " + Info.Storage + " " + Info.StorageUpdate + " " + Info.leaveExchange + " "
//				+ Info.basicNoOperationMsg);
		if (Info.basicNoOperationMsg && !Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange) {
			return false;
		} else {
			return true;
		}
	}
}
