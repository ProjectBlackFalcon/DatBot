package Game.Plugin;

import java.util.ArrayList;
import java.util.List;

import Game.Info;
import Game.map.Map;
import Game.map.elements.GraphicalElement;
import protocol.network.Network;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.StatedElement;

public class Farm {

	public static String farmCell;
	public static List<StatedElement> statedElements;
	public static List<InteractiveElement> interactiveElements;
	public static int lastItemHarvested;
	public static int quantityLastItemHarvested;

	public static String getFarmCell() {
		farmCell = ""; 
		int count = 0;
		for (int i = 0; i < Map.LayersCount; i++) {
			for (int j = 0; j < Map.getLayers().get(i).CellsCount; j++) {
				for (int k = 0; k < Map.getLayers().get(i).Cells.get(j).ElementsCount; k++) {
					for (StatedElement element : statedElements) {
						if (Map.Layers.get(i).getCells().get(j).CellId == element.elementCellId) {
							if (Map.Layers.get(i).getCells().get(j).Elements.get(k).Identifier == element.elementId) {
								count++;
								farmCell += "("+element.elementCellId+","+Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId+","+element.elementState+"), ";
//								System.out.println(getRessourceName(Map.Layers.get(i).getCells().get(j).Elements.get(k)) + " : " + element.elementCellId + " - Id : " + Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId +  " - State : " + element.elementState);
							}
						}
					}
				}
			}
		}
		if(farmCell.length() > 2)
			farmCell = farmCell.substring(0,farmCell.length()-2);
		return farmCell;
	}
	
	public static boolean harvestCell(int cellId) throws Exception{
		InteractiveUseRequestMessage interactiveUseRequestMessage = null; 
		for (int i = 0; i < statedElements.size() ; i++) {
			if(cellId == statedElements.get(i).elementCellId){
				if(statedElements.get(i).elementState == 0){
					for(int j = 0 ; j < interactiveElements.size() ; j++){
						if(statedElements.get(i).elementId == interactiveElements.get(j).elementId){
							interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveElements.get(j).elementId, interactiveElements.get(j).enabledSkills.get(0).skillInstanceUid);
							break;
						}
					}
				} else {
					System.out.println(1);
					return false;
				}
			}
		}
		Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Harvesting resource cell " + cellId);	
		Info.waitForHarvestFailure = false;
		Info.waitForHarvestSuccess = false;
		while(!Info.waitForHarvestFailure && !Info.waitForHarvestSuccess){
			Thread.sleep(1000);
		}
		if(Info.waitForHarvestFailure){
			System.out.println(2);
			return false;
		} else if (Info.waitForHarvestSuccess){
			return true;
		}
		System.out.println(3);
		return false;
	}

	private static String getRessourceName(GraphicalElement element) {
		
		switch ((int) element.ElementId) {

		case 29828:
			return "Bl�";
		case 34008:
			return "Bl�";
		case 34013:
			return "Avoine";
		case 67475:
			return "Ortie";
		case 69026:
			return "Bois de charme";
		case 18686:
			return "Fr�ne";
		case 30738:
			return "Orchid�e freyesque";
		case 30735:
			return "Tr�fle � 5 feuilles";
		case 30739:
			return "Menthe sauvage";
		case 18689:
			return "Bois de ch�ne";
		case 34660:
			return "Fer";
		case 33963:
			return "Goujon";
		}
		
		return null;
	}

}
