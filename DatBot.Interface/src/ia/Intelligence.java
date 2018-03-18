package ia;

import java.util.List;

import ia.entities.entity.Entity;
import ia.map.MapIA;

public class Intelligence {
	
	List<Entity> entities;
	MapIA map;

	public Intelligence() {
		
	}
	
	public void init(List<Entity> entities, MapIA map) {
		this.entities = entities;
		this.map = map;
	}
	
	//TODO gérer les paquets
}
