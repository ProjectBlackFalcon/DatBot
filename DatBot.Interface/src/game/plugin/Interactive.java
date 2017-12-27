package game.plugin;

import java.util.List;

import game.Info;
import game.map.Map;
import game.map.elements.GraphicalElement;
import protocol.network.Network;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.StatedElement;

public class Interactive {
	
	private Network network;
	
	public Interactive(Network network){
		this.network = network;		
	}

	private List<StatedElement> statedElements;
	private List<InteractiveElement> interactiveElements;

	
	// Statue 
	
	private int elementIdStatue = -1;
	private int skillInstanceUidStatue = -1;
	public int getStatue(){
		for(int i = 0 ; i < getInteractiveElements().size() ; i++){
			if (getInteractiveElements().get(i).getEnabledSkills().size() != 0) {
				if (getInteractiveElements().get(i).getEnabledSkills().get(0).getSkillId() == 302 && getInteractiveElements().get(i).isOnCurrentMap()) {
					for (int j = 0; j < Map.LayersCount; j++) {
						for (int k = 0; k < Map.getLayers().get(j).CellsCount; k++) {
							for (int l = 0; l < Map.getLayers().get(j).Cells.get(k).ElementsCount; l++) {
								if (Map.Layers.get(j).getCells().get(k).Elements.get(l).Identifier == getInteractiveElements().get(i).getElementId()) {
									setElementIdStatue(getInteractiveElements().get(i).getElementId());
									setSkillInstanceUidStatue(getInteractiveElements().get(i).getEnabledSkills().get(0).getSkillInstanceUid());
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
	
	
	private List<Integer> cellsIdRosette;
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

	public String getFarmCell() {
		farmCell = ""; 
		for (int i = 0; i < Map.LayersCount; i++) {
			for (int j = 0; j < Map.getLayers().get(i).CellsCount; j++) {
				for (int k = 0; k < Map.getLayers().get(i).Cells.get(j).ElementsCount; k++) {
					for (StatedElement element : getStatedElements()) {
						if (Map.Layers.get(i).getCells().get(j).CellId == element.getElementCellId()) {
							if (Map.Layers.get(i).getCells().get(j).Elements.get(k).Identifier == element.getElementId()) {
								farmCell += "("+element.getElementCellId()+","+Map.Layers.get(i).getCells().get(j).Elements.get(k).ElementId+","+element.getElementState()+"), ";
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
	
	private String farmCell;
	private int lastItemHarvestedId = 0;
	private int quantityLastItemHarvested = 0;
	
	public boolean harvestCell(int cellId) throws Exception{
		InteractiveUseRequestMessage interactiveUseRequestMessage = null; 
		for (int i = 0; i < getStatedElements().size() ; i++) {
			if(cellId == getStatedElements().get(i).getElementCellId()){
				if(getStatedElements().get(i).getElementState() == 0){
					for(int j = 0 ; j < getInteractiveElements().size() ; j++){
						if(getStatedElements().get(i).getElementId() == getInteractiveElements().get(j).getElementId()){
							interactiveUseRequestMessage = new InteractiveUseRequestMessage(getInteractiveElements().get(j).getElementId(), getInteractiveElements().get(j).getEnabledSkills().get(0).getSkillInstanceUid());
							break;
						}
					}
				} else {
					return false;
				}
			}
		}
		this.network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Harvesting resource cell " + cellId);	
		this.network.getInfo().setWaitForHarvestFailure(false);
		this.network.getInfo().setWaitForHarvestSuccess(false);
		while(!this.network.getInfo().isWaitForHarvestFailure() && !this.network.getInfo().isWaitForHarvestSuccess()){
			Thread.sleep(1000);
		}
		if(this.network.getInfo().isWaitForHarvestFailure()){
			return false;
		} else if (this.network.getInfo().isWaitForHarvestSuccess()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Get the skillUid for the interactive 
	 * @param int idInteractive
	 * @return int skillUid
	 * @return -1 else 
	 */
	public int getSkill(int idInteractive) {
		for (InteractiveElement i : this.getInteractiveElements()) {
			if (i.getElementId() == idInteractive) {
				return i.getEnabledSkills().get(0).getSkillInstanceUid();
			}
		}
		return -1;
	}

	private String getRessourceName(GraphicalElement element) {
		
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

	public List<StatedElement> getStatedElements()
	{
		return statedElements;
	}

	public void setStatedElements(List<StatedElement> statedElements)
	{
		this.statedElements = statedElements;
	}

	public List<InteractiveElement> getInteractiveElements()
	{
		return interactiveElements;
	}

	public void setInteractiveElements(List<InteractiveElement> interactiveElements)
	{
		this.interactiveElements = interactiveElements;
	}

	public int getLastItemHarvestedId()
	{
		return lastItemHarvestedId;
	}

	public void setLastItemHarvestedId(int lastItemHarvestedId)
	{
		this.lastItemHarvestedId = lastItemHarvestedId;
	}

	public int getQuantityLastItemHarvested()
	{
		return quantityLastItemHarvested;
	}

	public void setQuantityLastItemHarvested(int quantityLastItemHarvested)
	{
		this.quantityLastItemHarvested = quantityLastItemHarvested;
	}

	public int getElementIdStatue()
	{
		return elementIdStatue;
	}

	public void setElementIdStatue(int elementIdStatue)
	{
		this.elementIdStatue = elementIdStatue;
	}

	public int getSkillInstanceUidStatue()
	{
		return skillInstanceUidStatue;
	}

	public void setSkillInstanceUidStatue(int skillInstanceUidStatue)
	{
		this.skillInstanceUidStatue = skillInstanceUidStatue;
	}
	
	

}
