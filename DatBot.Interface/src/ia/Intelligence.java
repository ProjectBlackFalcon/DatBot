package ia;

import java.util.List;

import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.fight.FightIntelligence;
import ia.map.MapIA;
import ia.map.Position;
import ia.utils.UtilsMath;
import ia.utils.UtilsProtocol;
import protocol.network.Network;
import utils.d2o.modules.SpellLevel;

public class Intelligence {
	
	private List<Entity> entities;
	private List<Double> turnList;
	private MapIA map;
	private Network network;
	private FightIntelligence fight;
	public UtilsProtocol utils;
	public Log log;

	private boolean isInit;

	public Intelligence(Network network) {
		this.network = network;
		this.log = network.getLog();
		utils = new UtilsProtocol(network);
	}
	
	public void init(List<Entity> entities, MapIA map, FightIntelligence fight) {
		this.entities = entities;
		this.map = map;
		this.fight = fight;
	}
	
	/**
	 * Refresh all cooldowns, must be call at the start of the turn
	 */
	public void refreshCd(){
		for (SpellLevel spell : getMain().getSpells()) {
			spell.setNumberCasted(0);
			if(spell.getTurnLeftBeforeCast() > 0){
				spell.setTurnLeftBeforeCast(spell.getTurnLeftBeforeCast() - 1);
			}
		}
	}
	
	public void getBestPlacement() throws Exception{
		int bestCell = getBestCell();
		if(bestCell == getMain().getInfo().getDisposition().getCellId()){
			if(!getMain().isRdy()){
				utils.stop(0.25);
				getMain().setRdy(true);
				utils.fightReady();
			}
			Log.writeLogDebugMessage("Same cell, not moving");
			return;
		}
		Log.writeLogDebugMessage("Cell found : " + bestCell);
		if(getMain().isRdy()){
			utils.fightNotReady();
		}
		utils.stop(0.25);
		utils.setBeginingPosition(bestCell);
		utils.stop(0.25);
		getMain().setRdy(true);
		utils.fightReady();
	}

	private int getBestCell() {
		int cell = -1;
		int max = Integer.MAX_VALUE;
		for (Integer cellAvailable : this.map.getStartPosAvailable()) {
			int temp = getTotalDistance(MapIA.reshapeToIA(cellAvailable));
			if(temp < max){
				max = temp;
				cell = cellAvailable;
			}
		}
		return cell;
	}

	private int getTotalDistance(Position cellPos) {
		int total = 0;
		Entity main = getMain();
		for (Entity entity : entities) {
			if(entity.getInfo().getTeamId() != main.getInfo().getTeamId()){
				total += UtilsMath.getPath(MapIA.getCleanCells(network.getMap().getCells(), entities), entity.getPosition(), cellPos, false).size();
			}
		}
		return total;
	}


	public int entityExists(double id){
		for (int i = 0 ; i < this.entities.size() ; i++){
			if(entities.get(i).getInfo() != null && entities.get(i).getInfo().getContextualId() == id){
				return i;
			}
		}
		return -1;
	}
	
	public Entity getEntity(double id){
		for (Entity entity : entities) {
			if(entity.getInfo().getContextualId() == id)
				return entity;
		}
		return null;
	}
	
	
	public Entity getMain(){
		for (Entity entity : entities) {
			if(entity.getClass().equals(MainEntity.class))
				return entity;
		}
		return null;
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

    public void setInit(boolean init) throws Exception {
	    if(!isInit && init){
	        //TODO HANDLE BEST POSITION
            //init should only be false when the fightStarting packet is received and then be always true
	    	getBestPlacement();
	    	Log.writeLogDebugMessage("Finding best position from init");
	    	visualizeEntity("Init");
        }
        isInit = init;
    }
    
    public void setInitResume(boolean init) {
        isInit = init;
    }

	public void visualizeEntity(String whatIsTested) {
//		System.out.println("-----------------Testing "+ whatIsTested + "-----------------");
//		System.out.println("Number of entity : " +entities.size());
//		for (Entity e :  getEntities()) {
//			System.out.println(e);
//		}
//		System.out.println("");
	}

    public List<Double> getTurnList() {
        return turnList;
    }

    public void setTurnList(List<Double> turnList) {
        this.turnList = turnList;
    }

	public FightIntelligence getFight() {
		return fight;
	}

}
