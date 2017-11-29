package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Game.Info;
import Game.Plugin.Farm;
import Game.Plugin.Stats;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.Network;

public class ModelConnexion implements Runnable {
	
	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...] 
	

	@Override
	public void run() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s;
			while(true){
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
						Network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
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
					sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Farm.getFarmCell()});
					break;
				case "harvest":
					System.out.println("Cellid : " + Integer.parseInt(message[5]));
					if(Farm.harvestCell(Integer.parseInt(message[5]))){
						
						sendToModel(message[0], message[1],"m", "rtn", message[4], new Object[]{Farm.lastItemHarvestedId,Farm.lastItemHarvestedString,Farm.quantityLastItemHarvested,Info.weight,Info.weigthMax});
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
