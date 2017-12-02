package Game.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogReplyMessage;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;

public class NPC {
	
	public static boolean dialogOver;
	public int messageId;
	public static int replyId;
	public static List<GameRolePlayNpcInformations> npc = new ArrayList<GameRolePlayNpcInformations>();
	
	public NPC(int messageId) throws Exception{
		this.messageId = messageId;
		this.replyId = 0;
		setReplyId(messageId);
		reply();
	}
	
	public void setReplyId(int messageId){
		switch (messageId){
		case 318 :
			replyId = 259;
			break;
		case 20954 :
			replyId = 25209;
			break;
		case 20973:
			replyId = 25207;
			break;
		}
	}
	
	public static void reply() throws Exception{
		Thread.sleep(new Random().nextInt(120) + 450);
		if(replyId != 0){
			NpcDialogReplyMessage dialogReplyMessage = new NpcDialogReplyMessage(replyId);
			Network.sendToServer(dialogReplyMessage, NpcDialogReplyMessage.ProtocolId, "Replying");
		} else {
			throw new Exception("Don't know what to respond");
		}
	}
	
}
