package Game.Plugin;

import java.util.Random;

import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogReplyMessage;

public class NPC {
	
	public static boolean dialogOver = false;
	public int messageId;
	public static int replyId;
	
	public NPC(int messageId) throws Exception{
		this.messageId = messageId;
		this.replyId = 0;
		setReplyId(messageId);
		reply();
	}
	
	public void setReplyId(int messageId){
		switch (messageId){
		case 20954 :
			replyId = 25209;
			break;
		case 20973:
			replyId = 25207;
			break;
		}
	}
	
	public static void reply() throws Exception{
		Thread.sleep(new Random().nextInt(600) + 450);
		if(replyId != 0){
			NpcDialogReplyMessage dialogReplyMessage = new NpcDialogReplyMessage(replyId);
			Network.sendToServer(dialogReplyMessage, NpcDialogReplyMessage.ProtocolId, "Replying");
		} else {
			throw new Exception("Don't know what to respond");
		}
	}
	
}
