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
import utils.JSON;

public class Interactive {

	public static List<StatedElement> statedElements;
	public static List<InteractiveElement> interactiveElements;

	
	// Statue 
	
	public static int elementIdStatue = -1;
	public static int skillInstanceUidStatue = -1;
	public static int getStatue(){
		for(int i = 0 ; i < interactiveElements.size() ; i++){
			if (interactiveElements.get(i).enabledSkills.size() != 0) {
				if (interactiveElements.get(i).enabledSkills.get(0).skillId == 302 && interactiveElements.get(i).onCurrentMap) {
					for (int j = 0; j < Map.LayersCount; j++) {
						for (int k = 0; k < Map.getLayers().get(j).CellsCount; k++) {
							for (int l = 0; l < Map.getLayers().get(j).Cells.get(k).ElementsCount; l++) {
								if (Map.Layers.get(j).getCells().get(k).Elements.get(l).Identifier == interactiveElements.get(i).elementId) {
									elementIdStatue = interactiveElements.get(i).elementId;
									skillInstanceUidStatue = interactiveElements.get(i).enabledSkills.get(0).skillInstanceUid;
									return (int) Map.Layers.get(j).getCells().get(k).CellId;
								}
							}
						}
					}
				} 
			}
		}
		return -1;
	}
	
	
	public static List<Integer> cellsIdRosette;
	public void getInteractive(){
		for (int i = 0; i < Map.LayersCount; i++) {
			for (int j = 0; j < Map.getLayers().get(i).CellsCount; j++) {
				for (int k = 0; k < Map.getLayers().get(i).Cells.get(j).ElementsCount; k++) {
					if(Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId == 34708){
						cellsIdRosette.add((int) Map.Layers.get(i).getCells().get(j).CellId);
						System.out.println("Rosace : " + Map.Layers.get(i).getCells().get(j).CellId);
					}	
				}
			}
		}
	}

	public static String getFarmCell() {
		farmCell = ""; 
		for (int i = 0; i < Map.LayersCount; i++) {
			for (int j = 0; j < Map.getLayers().get(i).CellsCount; j++) {
				for (int k = 0; k < Map.getLayers().get(i).Cells.get(j).ElementsCount; k++) {
					for (StatedElement element : statedElements) {
						if (Map.Layers.get(i).getCells().get(j).CellId == element.elementCellId) {
							if (Map.Layers.get(i).getCells().get(j).Elements.get(k).Identifier == element.elementId) {
								farmCell += "("+element.elementCellId+","+Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId+","+element.elementState+"), ";
								System.out.println(getRessourceName(Map.Layers.get(i).getCells().get(j).Elements.get(k)) + " : " + element.elementCellId + " - Id : " + Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId +  " - State : " + element.elementState);
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
	
	public static String farmCell;
	public static int lastItemHarvestedId = 0;
	public static String lastItemHarvestedString = "";
	public static int quantityLastItemHarvested = 0;
	
	public static boolean harvestCell(int cellId) throws Exception{
		InteractiveUseRequestMessage interactiveUseRequestMessage = null; 
		for (int i = 0; i < statedElements.size() ; i++) {
			if(cellId == statedElements.get(i).elementCellId){
				System.out.println(1);
				if(statedElements.get(i).elementState == 0){
					System.out.println(2);
					for(int j = 0 ; j < interactiveElements.size() ; j++){
						if(statedElements.get(i).elementId == interactiveElements.get(j).elementId){
							System.out.println(3);
							interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactiveElements.get(j).elementId, interactiveElements.get(j).enabledSkills.get(0).skillInstanceUid);
							break;
						}
					}
				} else {
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
			return "Blé";
		case 34008:
			return "Blé";
		case 34013:
			return "Avoine";
		case 67475:
			return "Ortie";
		case 69026:
			return "Bois de charme";
		case 18686:
			return "Frêne";
		case 30738:
			return "Orchidée freyesque";
		case 30735:
			return "Trèfle à 5 feuilles";
		case 30739:
			return "Menthe sauvage";
		case 18689:
			return "Bois de chêne";
		case 34660:
			return "Fer";
		case 33963:
			return "Goujon";
		}
		
		return null;
	}
	
	

}