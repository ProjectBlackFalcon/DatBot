package ia;

import java.util.List;

import ia.entities.entity.Entity;
import ia.map.MapIA;
import protocol.network.NetworkMessage;

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
	public void treatPacket(NetworkMessage packet){
		switch (packet.getClass().getSimpleName()){
			case "GameFight"
		}
	}
}
