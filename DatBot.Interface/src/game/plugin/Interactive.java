package game.plugin;

import java.util.List;

import game.Info;
import main.communication.DisplayInfo;
import protocol.network.Network;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementSkill;
import protocol.network.types.game.interactive.StatedElement;
import utils.GameData;
import utils.d2p.map.Map;
import utils.d2p.map.elements.GraphicalElement;

public class Interactive {

	private Network network;
	private Map map;
	private List<Double> zaapList;
	private List<Double> zaapiList;

	public Interactive(Network network) {
		this.network = network;
		this.setMap(network.getMap());
	}

	private List<StatedElement> statedElements;
	private List<InteractiveElement> interactiveElements;

	public double getMapIdZaap(int i, int j) {
		for (Double d : zaapList) {
			int[] coord = GameData.getCoordMap(d.intValue());
			if (coord[0] == i && coord[1] == j) { return d; }
		}
		return -1;
	}

	public double getMapIdZaapi(int i, int j) {
		for (Double d : zaapiList) {
			int[] coord = GameData.getCoordMap(d.intValue());
			if (coord[0] == i && coord[1] == j) { return d; }
		}
		return -1;
	}

	/**
	 * Get the interactive informations If no interactive : null Case skillId :
	 * Zaap : 114 Statue : 302 Enclo : 175
	 * 
	 * @return CellId, elementId, skillId
	 */
	public int[] getInteractive(int skillId) {
		for (int i = 0; i < getInteractiveElements().size(); i++) {
			if (getInteractiveElements().get(i).getEnabledSkills().size() != 0) {
				for (InteractiveElementSkill skill : getInteractiveElements().get(i).getEnabledSkills()) {
					if (skill.getSkillId() == skillId) {
						for (int j = 0; j < this.getMap().getLayersCount(); j++) {
							for (int k = 0; k < this.getMap().getLayers().get(j).getCellsCount(); k++) {
								for (int l = 0; l < this.getMap().getLayers().get(j).getCells().get(k).getElementsCount(); l++) {
									if (((GraphicalElement) this.getMap().getLayers().get(j).getCells().get(k).getElements().get(l)).getIdentifier() == getInteractiveElements().get(i).getElementId()
										&& getInteractiveElements().get(i).isOnCurrentMap()) { return new int[] { this.getMap().getLayers().get(j).getCells().get(k).getCellId(), getInteractiveElements().get(i).getElementId(), skill.getSkillInstanceUid() }; }
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private List<Integer> cellsIdRosette;

	public void getRosette() {
		for (int i = 0; i < this.getMap().getLayersCount(); i++) {
			for (int j = 0; j < this.getMap().getLayers().get(i).getCellsCount(); j++) {
				for (int k = 0; k < this.getMap().getLayers().get(i).getCells().get(j).getElementsCount(); k++) {
					if (((GraphicalElement) this.getMap().getLayers().get(i).getCells().get(j).getElements().get(k)).getElementId() == 34708) {
						cellsIdRosette.add((int) this.getMap().getLayers().get(i).getCells().get(j).getCellId());
						this.network.append("Rosace : " + this.getMap().getLayers().get(i).getCells().get(j).getCellId());
					}
				}
			}
		}
	}

	/**
	 * Get all the farmCells of the map along with their states
	 * 
	 * @return (cellId,elementId,state),..
	 * @author baptiste
	 */

	public String getFarmCell() {
		farmCell = "";
		for (int i = 0; i < this.getMap().getLayersCount(); i++) {
			for (int j = 0; j < this.getMap().getLayers().get(i).getCellsCount(); j++) {
				for (int k = 0; k < this.getMap().getLayers().get(i).getCells().get(j).getElementsCount(); k++) {
					for (StatedElement element : getStatedElements()) {
						if (this.getMap().getLayers().get(i).getCells().get(j).getCellId() == element.getElementCellId()) {
							if (((GraphicalElement) this.getMap().getLayers().get(i).getCells().get(j).getElements().get(k)).getIdentifier() == element.getElementId()) {
								farmCell += "(" + element.getElementCellId() + "," + ((GraphicalElement) this.getMap().getLayers().get(i).getCells().get(j).getElements().get(k)).getElementId() + "," + element.getElementState() + "), ";
							}
						}
					}
				}
			}
		}
		if (farmCell.length() > 2) farmCell = farmCell.substring(0, farmCell.length() - 2);
		return farmCell;
	}

	private String farmCell;
	private int lastItemHarvestedId = 0;
	private int quantityLastItemHarvested = 0;

	public int[] getHarvestCell(int cellId) {
		int[] h = null;
		for (int i = 0; i < getStatedElements().size(); i++) {
			if (cellId == getStatedElements().get(i).getElementCellId()) {
				if (getStatedElements().get(i).getElementState() == 0) {
					for (int j = 0; j < getInteractiveElements().size(); j++) {
						if (getStatedElements().get(i).getElementId() == getInteractiveElements().get(j).getElementId()) {
							h = new int[] { getInteractiveElements().get(j).getElementId(), getInteractiveElements().get(j).getEnabledSkills().get(0).getSkillInstanceUid() };
							break;
						}
					}
				}
				else {
					System.out.println("State != 0");
					return null;
				}
			}
		}
		return h;
	}

	/**
	 * Get the skillUid for the interactive
	 * 
	 * @param int idInteractive
	 * @return int skillUid
	 * @return -1 else
	 */
	public int getSkill(int idInteractive) {
		for (InteractiveElement i : this.getInteractiveElements()) {
			if (i.getElementId() == idInteractive) { return i.getEnabledSkills().get(0).getSkillInstanceUid(); }
		}
		return -1;
	}

	private String getRessourceName(GraphicalElement element) {

		switch ((int) element.getElementId()) {

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

	public List<StatedElement> getStatedElements() {
		return statedElements;
	}

	public void setStatedElements(List<StatedElement> statedElements) {
		this.statedElements = statedElements;
	}

	public List<InteractiveElement> getInteractiveElements() {
		return interactiveElements;
	}

	public void setInteractiveElements(List<InteractiveElement> interactiveElements) {
		this.interactiveElements = interactiveElements;
	}

	public int getLastItemHarvestedId() {
		return lastItemHarvestedId;
	}

	public void setLastItemHarvestedId(int lastItemHarvestedId) {
		this.lastItemHarvestedId = lastItemHarvestedId;
	}

	public int getQuantityLastItemHarvested() {
		return quantityLastItemHarvested;
	}

	public void setQuantityLastItemHarvested(int quantityLastItemHarvested) {
		this.quantityLastItemHarvested = quantityLastItemHarvested;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List<Double> getZaapList() {
		return zaapList;
	}

	public void setZaapList(List<Double> zaapList) {
		this.zaapList = zaapList;
	}

	public List<Double> getZaapiList() {
		return zaapiList;
	}

	public void setZaapiList(List<Double> zaapiList) {
		this.zaapiList = zaapiList;
	}

}
