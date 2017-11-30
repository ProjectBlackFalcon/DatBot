package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Game.Info;
import Game.Plugin.Interactive;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.map.Map;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import utils.JSON;

public class ModelConnexion implements Runnable {
	
	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...] 
	

	@Override
	public void run() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s;
			while(true){
				Thread.sleep(200);
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
								sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
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
					Info.newMap = false;
					if(Map.Id == 153880835){
						NpcGenericActionRequestMessage actionRequestMessage = new NpcGenericActionRequestMessage(-20001,3,153880835);
						Network.sendToServer(actionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request NPC to go to Astrub");
						Network.waitForNewMap();
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"True"});
					} else {
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{"False"});
					}
					break;
				case "goIncarnam":
					Info.newMap = false;
					int r = Interactive.getStatue();
					if(r != -1){
						InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(Interactive.elementIdStatue,Interactive.skillInstanceUidStatue);
						Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
						Network.waitForNewMap();
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
}
