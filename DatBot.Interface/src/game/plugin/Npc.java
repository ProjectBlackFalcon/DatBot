package game.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogReplyMessage;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;

public class Npc {

	private boolean dialogOver;
	private List<GameRolePlayNpcInformations> npc = new ArrayList<GameRolePlayNpcInformations>();
	private Network network;

	public Npc(Network network) throws Exception
	{
		this.network = network;
	}

	public int getReplyId(int messageId)
	{
		int replyId = -1;
		switch (messageId)
		{
			case 318:
				replyId = 259;
			break;
			case 20954:
				replyId = 25209;
			break;
			case 20973:
				replyId = 25207;
			break;
		}
		return replyId;
	}

	public void reply(int id) throws Exception
	{
		Thread.sleep(new Random().nextInt(120) + 450);
		if (id != -1)
		{
			NpcDialogReplyMessage dialogReplyMessage = new NpcDialogReplyMessage(id);
			this.network.sendToServer(dialogReplyMessage, NpcDialogReplyMessage.ProtocolId, "Replying");
		}
		else
		{
			throw new Exception("Don't know what to respond");
		}
	}

	public List<GameRolePlayNpcInformations> getNpc()
	{
		return npc;
	}

	public void setNpc(List<GameRolePlayNpcInformations> npc)
	{
		this.npc = npc;
	}

	public boolean isDialogOver()
	{
		return dialogOver;
	}

	public void setDialogOver(boolean dialogOver)
	{
		this.dialogOver = dialogOver;
	}

}
