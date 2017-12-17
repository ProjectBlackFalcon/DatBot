package Main;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import Game.Info;
import Game.Plugin.Bank;
import Game.Plugin.Interactive;
import Game.Plugin.Monsters;
import Game.Plugin.NPC;
import Game.Plugin.Stats;
import Game.map.Map;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import Main.Communication.Communication;
import Main.Communication.ModelConnexion;
import ia.fight.brain.Game;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;
import protocol.network.messages.subscription.SubscriptionUpdateMessage;
import protocol.network.util.DofusDataWriter;

public class Test {

	public static void main(String[] args) throws Exception
	{
		Thread communication = new Thread(new Communication());
		communication.start();
		int index = 0;
		while (Info.nameAccount.equals("") || Info.password.equals("") || Info.name.equals("") || Info.server.equals(""))
		{
			System.out.println("Waiting for connection...");
			Thread.sleep(1000);
			index++;
			if (index == 2)
			{
				Info.nameAccount = "wublel7";
				Info.password = "wubwublel7";
				Info.name = "Dihydroquerina";
				Info.server = "Julith";
			}
			// if(index == 2){
			// Info.nameAccount = "Jemappellehenry2";
			// Info.password = "azerty123henry";
			// Info.name = "Baddosh";
			// Info.server = "Julith";
			// }
		}
		boolean arg = false;
		if (args.length != 0)
		{
			if ((args[0].equals("true") || args[0].equals("True")))
			{
				arg = true;
			}
		}
		Thread thread = new Thread(new Network(arg));
		thread.start();

		while (!Info.isConnected)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		Network.append("Connecté !");
		Network.append("Name : " + Info.name);
		Network.append("Niveau : " + Info.lvl);
		Thread.sleep(1000);
		getReturn("0;0;i;cmd;getMonsters;[None]");
		getReturn("0;0;i;cmd;move;[" + Monsters.monsters.get(0).disposition.cellId + "]");
		getReturn("0;0;i;cmd;attackMonster;[" + Monsters.monsters.get(0).contextualId + "]");
	}
	
	private static void getReturn(String s) throws NumberFormatException, Exception{
		String[] message = s.split(";");
		message[5] = message[5].substring(1, message[5].length() - 1);
		Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], ModelConnexion.getReturn(message[4], message[5]));
		}
}
