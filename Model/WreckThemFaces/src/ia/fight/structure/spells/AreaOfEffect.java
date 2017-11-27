package ia.fight.structure.spells;


import java.util.ArrayList;

import ia.fight.brain.Position;

public class AreaOfEffect {

	String type;
	int width;
	
	public AreaOfEffect(String string, int i) {
		this.type = string;
		this.width = i;
	}
	
	
	
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	@Override
	public String toString() {
		return type+" - size : "+width;
	}

	public ArrayList<Position> getPositions(Position castPosition, Position finalPosition){
		ArrayList<Position> zone = new ArrayList<>();
		
		if(this.getType() == "cross"){
			zone.add(finalPosition);
			for(int i = 1; i <= this.getWidth(); i++){
				zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
			}
		}else if(this.getType() == "cell"){
			zone.add(finalPosition);
		}else if(this.getType() == "centerless cross"){
			for(int i = 1; i <= this.getWidth(); i++){
				zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
			}
			
		}else if(this.getType() == "line"){
			
			int dx = finalPosition.getX() - castPosition.getX();
			int dy = finalPosition.getY() - castPosition.getY();
			
			if(dx < 0){
				for(int i = 0; i < this.getWidth(); i++){
					zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				}
			}else if(dx > 0){
				for(int i = 0; i < this.getWidth(); i++){
					zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				}
			}
			
			if(dy < 0){
				for(int i = 0; i < this.getWidth(); i++){
					zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				}
			}else if(dy > 0){
				for(int i = 0; i < this.getWidth(); i++){
					zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
				}
			}
		}else if(this.getType() == "circle"){
			for(int i = -this.getWidth(); i <= this.getWidth(); i++){
				for(int j = -this.getWidth(); j <= this.getWidth(); j++){
					if(Math.abs(i)+Math.abs(j) < 4){
						zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()+j));
					}
				}
			}
		}
		
		else{
			throw new Error("Unsupported spell zone type : "+this.getType());
		}
		return zone;
	}

	public static ArrayList<Position> createAoE(AreaOfEffect aoe, Position castPosition, Position finalPosition) {
		ArrayList<Position> zone = new ArrayList<>();
		
		if(aoe.getType() == "cross"){
			zone.add(finalPosition);
			for(int i = 1; i <= aoe.getWidth(); i++){
				zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
			}
		}else if(aoe.getType() == "cell"){
			zone.add(finalPosition);
		}else if(aoe.getType() == "centerless cross"){
			for(int i = 1; i <= aoe.getWidth(); i++){
				zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
			}
		}else if(aoe.getType() == "line"){
			
			int dx = finalPosition.getX() - castPosition.getX();
			int dy = finalPosition.getY() - castPosition.getY();
			
			if(dx < 0){
				for(int i = 0; i < aoe.getWidth(); i++){
					zone.add(new Position(finalPosition.getX()-i, finalPosition.getY()));
				}
			}else if(dx > 0){
				for(int i = 0; i < aoe.getWidth(); i++){
					zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()));
				}
			}
			
			if(dy < 0){
				for(int i = 0; i < aoe.getWidth(); i++){
					zone.add(new Position(finalPosition.getX(), finalPosition.getY()-i));
				}
			}else if(dy > 0){
				for(int i = 0; i < aoe.getWidth(); i++){
					zone.add(new Position(finalPosition.getX(), finalPosition.getY()+i));
				}
			}
		}else if(aoe.getType() == "circle"){
			for(int i = -aoe.getWidth(); i <= aoe.getWidth(); i++){
				for(int j = -aoe.getWidth(); j <= aoe.getWidth(); j++){
					if(Math.abs(i)+Math.abs(j) < 4){
						zone.add(new Position(finalPosition.getX()+i, finalPosition.getY()+j));
					}
				}
			}
		}
		
		else{
			throw new Error("Unsupported spell zone type : "+aoe.getType());
		}
		
		return zone;
	}

}
