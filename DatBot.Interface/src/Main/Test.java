package Main;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import Game.InfoAccount;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.util.DofusDataReader;
import utils.Astar;
import utils.JSON;

public class Test {

	public static void main(String[] args) {
//		String data = "0002611b6057050c0202ba48b7e658a62ff70ebc0791e0813667fd8e20a77c1859d682e6bf00b1aa88b6199151e06eba5b9146362b609d15124d";
//		DofusDataReader dataReader = new DofusDataReader(new ByteArrayInputStream(data.getBytes())); 
//		GameMapMovementRequestMessage gameMapMovementRequestMessage = new GameMapMovementRequestMessage();
//		gameMapMovementRequestMessage.Deserialize(dataReader);
//        new JSON("MapInfo", 84673538);
//        List<int[]> blocked = new ArrayList<int[]>();
//        for (int i = 0; i < InfoAccount.cells.size(); i++){
//        	for (int j = 0; j < InfoAccount.cells.get(0).size() ; j++){
//        		if (((Number) InfoAccount.cells.get(i).get(j)).intValue() == 1  || ((Number) InfoAccount.cells.get(i).get(j)).intValue() == 2  ){
//        			blocked.add(new int[]{j,i});
//        		}
//        	}
//        }    
//        new Astar(2, 1, 0, 39, blocked,false);
//		String packet  = "03712a41a52c08040000000020363439616534353163613333656335336262636263633333626563663135663402c0";
//		DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(packet.getBytes()));
//		CurrentMapMessage currentMapMessage = new CurrentMapMessage();
//		currentMapMessage.Deserialize(reader);
	}

}
