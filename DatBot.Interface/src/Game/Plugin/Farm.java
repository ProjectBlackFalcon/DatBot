package Game.Plugin;

import java.util.ArrayList;
import java.util.List;

import Game.map.Map;
import Game.map.elements.GraphicalElement;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.StatedElement;

public class Farm {

	public static String farmCell;
	public static List<StatedElement> statedElements;
	public static List<InteractiveElement> interactiveElements;

	public static void getFarmCell() {
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
								System.out.println(getRessourceName(Map.Layers.get(i).getCells().get(j).Elements.get(k)) + " : " + element.elementCellId + " - Id : " + Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId +  " - State : " + element.elementState);
							}
						}
					}
				}
			}
		}
		farmCell = farmCell.substring(0,farmCell.length()-2);
		System.out.println(farmCell);
		System.out.println("Compteur : "+ count);
	}
	
	public static void harvestCell(int cellId){
//		InteractiveUseRequestMessage interactiveUseRequestMessage = new InteractiveUseRequestMessage(elemId, skillInstanceUid);
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
