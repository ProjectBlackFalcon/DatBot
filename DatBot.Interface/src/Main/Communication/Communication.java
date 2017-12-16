package Main.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.fight.GameRolePlayAttackMonsterRequestMessage;
import protocol.network.messages.game.context.roleplay.npc.NpcGenericActionRequestMessage;
import protocol.network.messages.game.dialog.LeaveDialogRequestMessage;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveKamaMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertAllFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListFromInvMessage;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectTransfertListToInvMessage;

public class Communication implements Runnable {

	// <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]

	public void run()
	{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			String s;
			while (true)
			{
				Thread.sleep(200);
				Info.newMap = false;
				s = bufferRead.readLine();
				String[] message = s.split(";");
				Info.botInstance = Integer.parseInt(message[0]);
				message[5] = message[5].substring(1, message[5].length() - 1);

				switch (message[2])
				{
					case "i":
						Info.msgIdModel = Integer.parseInt(message[1]);
						sendToModel(message[0], message[1], "m", "rtn", message[4], ModelConnexion.getReturn(message[4], message[5]));
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void sendToModel(String botInstance, String msgId, String dest, String msgType, String command, Object[] param)
	{
		String newParam = "";
		for (int i = 0; i < param.length; i++)
		{
			if (i == param.length - 1)
			{
				newParam += param[i];
			}
			else
			{
				newParam += param[i] + ", ";
			}
		}
		System.out.println(String.format("%s;%s;%s;%s;%s;[%s]", botInstance, msgId, dest, msgType, command, newParam));
	}

	public static boolean waitToSend() throws InterruptedException
	{
		while (!Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange && !Info.joinedFight)
		{
			Thread.sleep(50);
		}
		while (!Info.basicNoOperationMsg)
		{
			Thread.sleep(50);
		}
		// System.out.println((!Info.newMap && !Info.Storage &&
		// !Info.StorageUpdate && !Info.leaveExchange)
		// && !Info.basicNoOperationMsg);
		// System.out.println(Info.newMap + " " + Info.Storage + " " +
		// Info.StorageUpdate + " " + Info.leaveExchange + " "
		// + Info.basicNoOperationMsg);
		if (Info.basicNoOperationMsg && !Info.newMap && !Info.Storage && !Info.StorageUpdate && !Info.leaveExchange && !Info.joinedFight)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
