package Game.Plugin;

import java.util.ArrayList;
import java.util.List;

import Game.map.Map;
import Game.map.elements.GraphicalElement;
import protocol.network.types.game.interactive.StatedElement;

public class Farm {

	public static List<Integer> farmCell = new ArrayList<>();
	public static List<StatedElement> statedElements;

	public static List<Integer> getFarmCell() {
		for (int i = 0; i < Map.LayersCount; i++) {
			for (int j = 0; j < Map.getLayers().get(i).CellsCount; j++) {
				for (int k = 0; k < Map.getLayers().get(i).Cells.get(j).ElementsCount; k++) {
					for (StatedElement element : statedElements) {
						if (Map.Layers.get(i).getCells().get(j).CellId == element.elementCellId) {
							if (Map.Layers.get(i).getCells().get(j).Elements.get(k).Identifier == element.elementId) {
								System.out.println(getRessourceName(Map.Layers.get(i).getCells().get(j).Elements.get(k)) + " : " + element.elementCellId + " - State : " + element.elementState);
							}
						}
					}
				}
			}
		}
		return farmCell;
	}

	private static String getRessourceName(GraphicalElement element) {
		
		switch ((int) element.ElementId) {

		case 29828:
			if (element.OffsetX == 0.0 && element.OffsetY == 0.0 && element.PixelOffsetX == 0.0	&& element.PixelOffsetY == 0.0)
				return "Blé";
			break;
		case 67475:
			return "Ortie";
		}
		
		return null;
	}

}
