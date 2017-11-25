package protocol.network.types.game.character.restriction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ActorRestrictionsInformations extends NetworkMessage {
	public static final int ProtocolId = 204;

	public boolean cantBeAggressed;
	public boolean cantBeChallenged;
	public boolean cantTrade;
	public boolean cantBeAttackedByMutant;
	public boolean cantRun;
	public boolean forceSlowWalk;
	public boolean cantMinimize;
	public boolean cantMove;
	public boolean cantAggress;
	public boolean cantChallenge;
	public boolean cantExchange;
	public boolean cantAttack;
	public boolean cantChat;
	public boolean cantBeMerchant;
	public boolean cantUseObject;
	public boolean cantUseTaxCollector;
	public boolean cantUseInteractive;
	public boolean cantSpeakToNPC;
	public boolean cantChangeZone;
	public boolean cantAttackMonster;
	public boolean cantWalk8Directions;

	public ActorRestrictionsInformations(){
	}

	public ActorRestrictionsInformations(boolean cantBeAggressed, boolean cantBeChallenged, boolean cantTrade, boolean cantBeAttackedByMutant, boolean cantRun, boolean forceSlowWalk, boolean cantMinimize, boolean cantMove, boolean cantAggress, boolean cantChallenge, boolean cantExchange, boolean cantAttack, boolean cantChat, boolean cantBeMerchant, boolean cantUseObject, boolean cantUseTaxCollector, boolean cantUseInteractive, boolean cantSpeakToNPC, boolean cantChangeZone, boolean cantAttackMonster, boolean cantWalk8Directions){
		this.cantBeAggressed = cantBeAggressed;
		this.cantBeChallenged = cantBeChallenged;
		this.cantTrade = cantTrade;
		this.cantBeAttackedByMutant = cantBeAttackedByMutant;
		this.cantRun = cantRun;
		this.forceSlowWalk = forceSlowWalk;
		this.cantMinimize = cantMinimize;
		this.cantMove = cantMove;
		this.cantAggress = cantAggress;
		this.cantChallenge = cantChallenge;
		this.cantExchange = cantExchange;
		this.cantAttack = cantAttack;
		this.cantChat = cantChat;
		this.cantBeMerchant = cantBeMerchant;
		this.cantUseObject = cantUseObject;
		this.cantUseTaxCollector = cantUseTaxCollector;
		this.cantUseInteractive = cantUseInteractive;
		this.cantSpeakToNPC = cantSpeakToNPC;
		this.cantChangeZone = cantChangeZone;
		this.cantAttackMonster = cantAttackMonster;
		this.cantWalk8Directions = cantWalk8Directions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, cantBeAggressed);
			flag = BooleanByteWrapper.SetFlag(1, flag, cantBeChallenged);
			flag = BooleanByteWrapper.SetFlag(2, flag, cantTrade);
			flag = BooleanByteWrapper.SetFlag(3, flag, cantBeAttackedByMutant);
			flag = BooleanByteWrapper.SetFlag(4, flag, cantRun);
			flag = BooleanByteWrapper.SetFlag(5, flag, forceSlowWalk);
			flag = BooleanByteWrapper.SetFlag(6, flag, cantMinimize);
			flag = BooleanByteWrapper.SetFlag(7, flag, cantMove);
			writer.writeByte(flag);
			flag = BooleanByteWrapper.SetFlag(0, flag, cantAggress);
			flag = BooleanByteWrapper.SetFlag(1, flag, cantChallenge);
			flag = BooleanByteWrapper.SetFlag(2, flag, cantExchange);
			flag = BooleanByteWrapper.SetFlag(3, flag, cantAttack);
			flag = BooleanByteWrapper.SetFlag(4, flag, cantChat);
			flag = BooleanByteWrapper.SetFlag(5, flag, cantBeMerchant);
			flag = BooleanByteWrapper.SetFlag(6, flag, cantUseObject);
			flag = BooleanByteWrapper.SetFlag(7, flag, cantUseTaxCollector);
			writer.writeByte(flag);
			flag = BooleanByteWrapper.SetFlag(0, flag, cantUseInteractive);
			flag = BooleanByteWrapper.SetFlag(1, flag, cantSpeakToNPC);
			flag = BooleanByteWrapper.SetFlag(2, flag, cantChangeZone);
			flag = BooleanByteWrapper.SetFlag(3, flag, cantAttackMonster);
			flag = BooleanByteWrapper.SetFlag(4, flag, cantWalk8Directions);
			writer.writeByte(flag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.cantBeAggressed = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.cantBeChallenged = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.cantTrade = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.cantBeAttackedByMutant = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.cantRun = BooleanByteWrapper.GetFlag(flag, (byte) 4);
			this.forceSlowWalk = BooleanByteWrapper.GetFlag(flag, (byte) 5);
			this.cantMinimize = BooleanByteWrapper.GetFlag(flag, (byte) 6);
			this.cantMove = BooleanByteWrapper.GetFlag(flag, (byte) 7);
			flag = (byte) reader.readUnsignedByte();
			this.cantAggress = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.cantChallenge = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.cantExchange = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.cantAttack = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.cantChat = BooleanByteWrapper.GetFlag(flag, (byte) 4);
			this.cantBeMerchant = BooleanByteWrapper.GetFlag(flag, (byte) 5);
			this.cantUseObject = BooleanByteWrapper.GetFlag(flag, (byte) 6);
			this.cantUseTaxCollector = BooleanByteWrapper.GetFlag(flag, (byte) 7);
			flag = (byte) reader.readUnsignedByte();
			this.cantUseInteractive = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.cantSpeakToNPC = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.cantChangeZone = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.cantAttackMonster = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.cantWalk8Directions = BooleanByteWrapper.GetFlag(flag, (byte) 4);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("cantBeAggressed : " + this.cantBeAggressed);
		//Network.appendDebug("cantBeChallenged : " + this.cantBeChallenged);
		//Network.appendDebug("cantTrade : " + this.cantTrade);
		//Network.appendDebug("cantBeAttackedByMutant : " + this.cantBeAttackedByMutant);
		//Network.appendDebug("cantRun : " + this.cantRun);
		//Network.appendDebug("forceSlowWalk : " + this.forceSlowWalk);
		//Network.appendDebug("cantMinimize : " + this.cantMinimize);
		//Network.appendDebug("cantMove : " + this.cantMove);
		//Network.appendDebug("cantAggress : " + this.cantAggress);
		//Network.appendDebug("cantChallenge : " + this.cantChallenge);
		//Network.appendDebug("cantExchange : " + this.cantExchange);
		//Network.appendDebug("cantAttack : " + this.cantAttack);
		//Network.appendDebug("cantChat : " + this.cantChat);
		//Network.appendDebug("cantBeMerchant : " + this.cantBeMerchant);
		//Network.appendDebug("cantUseObject : " + this.cantUseObject);
		//Network.appendDebug("cantUseTaxCollector : " + this.cantUseTaxCollector);
		//Network.appendDebug("cantUseInteractive : " + this.cantUseInteractive);
		//Network.appendDebug("cantSpeakToNPC : " + this.cantSpeakToNPC);
		//Network.appendDebug("cantChangeZone : " + this.cantChangeZone);
		//Network.appendDebug("cantAttackMonster : " + this.cantAttackMonster);
		//Network.appendDebug("cantWalk8Directions : " + this.cantWalk8Directions);
	//}
}