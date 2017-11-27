package ia.fight.brain;

import ia.fight.structure.Player;

public class PlayingEntity {

	String team;
	Player model;
	Position position;
	int ID;
	boolean npc;
	
	public PlayingEntity(int ID, boolean npc, Position position, String team, Player model) {
		this.ID = ID;
		this.position = position;
		this.team = team;
		this.model = model;
		this.npc = npc;
	}
	
	public int getID() {
		return ID;
	}
	
	public boolean isNpc() {
		return npc;
	}

	public void setNpc(boolean npc) {
		this.npc = npc;
	}

	public Position getPosition() {
		return position;
	}
	
	public Player getModel() {
		return model;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setModel(Player model) {
		this.model = model;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Object toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return model.getName()+" "+model.getLP()+"/"+model.getAP()+"/"+model.getMP();
	}

	
	
}
