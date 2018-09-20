package protocol.network.messages.game.approach;

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
public class ServerSettingsMessage extends NetworkMessage {
	public static final int ProtocolId = 6340;

	private String lang;
	private int community;
	private int gameType;
	private boolean isMonoAccount;
	private int arenaLeaveBanTime;
	private int itemMaxLevel;
	private boolean hasFreeAutopilot;

	public String getLang() { return this.lang; }
	public void setLang(String lang) { this.lang = lang; };
	public int getCommunity() { return this.community; }
	public void setCommunity(int community) { this.community = community; };
	public int getGameType() { return this.gameType; }
	public void setGameType(int gameType) { this.gameType = gameType; };
	public boolean isIsMonoAccount() { return this.isMonoAccount; }
	public void setIsMonoAccount(boolean isMonoAccount) { this.isMonoAccount = isMonoAccount; };
	public int getArenaLeaveBanTime() { return this.arenaLeaveBanTime; }
	public void setArenaLeaveBanTime(int arenaLeaveBanTime) { this.arenaLeaveBanTime = arenaLeaveBanTime; };
	public int getItemMaxLevel() { return this.itemMaxLevel; }
	public void setItemMaxLevel(int itemMaxLevel) { this.itemMaxLevel = itemMaxLevel; };
	public boolean isHasFreeAutopilot() { return this.hasFreeAutopilot; }
	public void setHasFreeAutopilot(boolean hasFreeAutopilot) { this.hasFreeAutopilot = hasFreeAutopilot; };

	public ServerSettingsMessage(){
	}

	public ServerSettingsMessage(String lang, int community, int gameType, boolean isMonoAccount, int arenaLeaveBanTime, int itemMaxLevel, boolean hasFreeAutopilot){
		this.lang = lang;
		this.community = community;
		this.gameType = gameType;
		this.isMonoAccount = isMonoAccount;
		this.arenaLeaveBanTime = arenaLeaveBanTime;
		this.itemMaxLevel = itemMaxLevel;
		this.hasFreeAutopilot = hasFreeAutopilot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isMonoAccount);
			flag = BooleanByteWrapper.SetFlag(1, flag, hasFreeAutopilot);
			writer.writeByte(flag);
			writer.writeUTF(this.lang);
			writer.writeByte(this.community);
			writer.writeByte(this.gameType);
			writer.writeVarShort(this.arenaLeaveBanTime);
			writer.writeInt(this.itemMaxLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isMonoAccount = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.hasFreeAutopilot = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.lang = reader.readUTF();
			this.community = reader.readByte();
			this.gameType = reader.readByte();
			this.arenaLeaveBanTime = reader.readVarShort();
			this.itemMaxLevel = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
