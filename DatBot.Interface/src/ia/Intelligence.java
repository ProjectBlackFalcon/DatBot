package ia;

import java.util.List;

import ia.entities.entity.Entity;
import ia.map.MapIA;
import protocol.network.Network;
import protocol.network.NetworkMessage;

public class Intelligence {
	
	List<Entity> entities;
	MapIA map;
	Network network;

	public Intelligence(Network network) {
		this.network = network;
	}
	
	public void init(List<Entity> entities, MapIA map) {
		this.entities = entities;
		this.map = map;
	}
	
	//TODO gérer les paquets
	public void treatPacket(NetworkMessage packet){
		switch (packet.getClass().getSimpleName()){

		}
	}

	public void getTurn(){

	}
}
