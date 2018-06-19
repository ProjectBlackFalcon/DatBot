package ia;

import java.util.List;

import ia.entities.entity.Entity;
import ia.map.MapIA;
import protocol.network.Network;
import protocol.network.NetworkMessage;

public class Intelligence {
	
	private List<Entity> entities;
	private List<Double> turnList;
	private MapIA map;
	private Network network;

	private boolean isInit;

	public Intelligence(Network network) {
		this.network = network;
	}
	
	public void init(List<Entity> entities, MapIA map) {
		this.entities = entities;
		this.map = map;
	}

	public void getTurn(){

	}

	public int entityExists(double id){
		for (int i = 0 ; i < this.entities.size() ; i++){
			if(entities.get(i).getInfo().getContextualId() == id){
				return i;
			}
		}
		return -1;
	}

	public void addEntity(Entity entity){
		this.entities.add(entity);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public MapIA getMap() {
		return map;
	}

	public void setMap(MapIA map) {
		this.map = map;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
	    if(!init){
	        //TODO HANDLE BEST POSITION
            //init should only be false when the fightStarting packet is received and then be always true
        }
        isInit = init;
    }

    public List<Double> getTurnList() {
        return turnList;
    }

    public void setTurnList(List<Double> turnList) {
        this.turnList = turnList;
    }
}
