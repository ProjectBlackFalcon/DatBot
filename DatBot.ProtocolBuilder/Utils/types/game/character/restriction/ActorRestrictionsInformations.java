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

	private boolean cantBeAggressed;
	private boolean cantBeChallenged;
	private boolean cantTrade;
	private boolean cantBeAttackedByMutant;
	private boolean cantRun;
	private boolean forceSlowWalk;
	private boolean cantMinimize;
	private boolean cantMove;
	private boolean cantAggress;
	private boolean cantChallenge;
	private boolean cantExchange;
	private boolean cantAttack;
	private boolean cantChat;
	private boolean cantBeMerchant;
	private boolean cantUseObject;
	private boolean cantUseTaxCollector;
	private boolean cantUseInteractive;
	private boolean cantSpeakToNPC;
	private boolean cantChangeZone;
	private boolean cantAttackMonster;
	private boolean cantWalk8Directions;

	public boolean isCantBeAggressed() { return this.cantBeAggressed; }
	public void setCantBeAggressed(boolean cantBeAggressed) { this.cantBeAggressed = cantBeAggressed; };
	public boolean isCantBeChallenged() { return this.cantBeChallenged; }
	public void setCantBeChallenged(boolean cantBeChallenged) { this.cantBeChallenged = cantBeChallenged; };
	public boolean isCantTrade() { return this.cantTrade; }
	public void setCantTrade(boolean cantTrade) { this.cantTrade = cantTrade; };
	public boolean isCantBeAttackedByMutant() { return this.cantBeAttackedByMutant; }
	public void setCantBeAttackedByMutant(boolean cantBeAttackedByMutant) { this.cantBeAttackedByMutant = cantBeAttackedByMutant; };
	public boolean isCantRun() { return this.cantRun; }
	public void setCantRun(boolean cantRun) { this.cantRun = cantRun; };
	public boolean isForceSlowWalk() { return this.forceSlowWalk; }
	public void setForceSlowWalk(boolean forceSlowWalk) { this.forceSlowWalk = forceSlowWalk; };
	public boolean isCantMinimize() { return this.cantMinimize; }
	public void setCantMinimize(boolean cantMinimize) { this.cantMinimize = cantMinimize; };
	public boolean isCantMove() { return this.cantMove; }
	public void setCantMove(boolean cantMove) { this.cantMove = cantMove; };
	public boolean isCantAggress() { return this.cantAggress; }
	public void setCantAggress(boolean cantAggress) { this.cantAggress = cantAggress; };
	public boolean isCantChallenge() { return this.cantChallenge; }
	public void setCantChallenge(boolean cantChallenge) { this.cantChallenge = cantChallenge; };
	public boolean isCantExchange() { return this.cantExchange; }
	public void setCantExchange(boolean cantExchange) { this.cantExchange = cantExchange; };
	public boolean isCantAttack() { return this.cantAttack; }
	public void setCantAttack(boolean cantAttack) { this.cantAttack = cantAttack; };
	public boolean isCantChat() { return this.cantChat; }
	public void setCantChat(boolean cantChat) { this.cantChat = cantChat; };
	public boolean isCantBeMerchant() { return this.cantBeMerchant; }
	public void setCantBeMerchant(boolean cantBeMerchant) { this.cantBeMerchant = cantBeMerchant; };
	public boolean isCantUseObject() { return this.cantUseObject; }
	public void setCantUseObject(boolean cantUseObject) { this.cantUseObject = cantUseObject; };
	public boolean isCantUseTaxCollector() { return this.cantUseTaxCollector; }
	public void setCantUseTaxCollector(boolean cantUseTaxCollector) { this.cantUseTaxCollector = cantUseTaxCollector; };
	public boolean isCantUseInteractive() { return this.cantUseInteractive; }
	public void setCantUseInteractive(boolean cantUseInteractive) { this.cantUseInteractive = cantUseInteractive; };
	public boolean isCantSpeakToNPC() { return this.cantSpeakToNPC; }
	public void setCantSpeakToNPC(boolean cantSpeakToNPC) { this.cantSpeakToNPC = cantSpeakToNPC; };
	public boolean isCantChangeZone() { return this.cantChangeZone; }
	public void setCantChangeZone(boolean cantChangeZone) { this.cantChangeZone = cantChangeZone; };
	public boolean isCantAttackMonster() { return this.cantAttackMonster; }
	public void setCantAttackMonster(boolean cantAttackMonster) { this.cantAttackMonster = cantAttackMonster; };
	public boolean isCantWalk8Directions() { return this.cantWalk8Directions; }
	public void setCantWalk8Directions(boolean cantWalk8Directions) { this.cantWalk8Directions = cantWalk8Directions; };

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
	}

}
