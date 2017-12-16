package Main.Communication;

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

public class ModelConnexion extends Communication {

	private static InteractiveUseRequestMessage interactiveUseRequestMessage;
	private static NpcGenericActionRequestMessage npcGenericactionRequestMessage;
	private static boolean bankOppened;

	public static Object[] getReturn(String cmd, String param) throws NumberFormatException, Exception
	{
		Object[] toSend = null;

		switch (cmd)
		{
			case "connect":
				String[] info = param.split(",");
				Info.nameAccount = info[0].substring(1, info[0].length() - 1);
				Info.password = info[1].substring(2, info[1].length() - 1);
				Info.name = info[2].substring(2, info[2].length() - 1);
				Info.server = info[3].substring(2, info[3].length() - 1);
				int index = 0;
				while (!Info.isConnected)
				{
					Thread.sleep(2000);
					index += 1;
					if (index == 30) { throw new java.lang.Error("Connection timed out"); }
				}
				toSend = new Object[] { "True" };
			break;
			case "disconnect":
			// Info.nameAccount = "";
			// Info.password = "";
			// Info.name = "";
			// Info.server = "";
			// Network.socket.close();
			// sendToModel(message[0], message[1],"m", "rtn", message[4], new
			// Object[]{"True"});
			break;
			case "getMap":
				toSend = new Object[] { String.valueOf("(" + Info.coords[0]) + "," + String.valueOf(Info.coords[1]) + ")", Info.cellId, Info.worldmap, Integer.valueOf((int) Info.mapId) };
			break;
			case "move":
				CellMovement mov = Movement.MoveToCell(Integer.parseInt(param));
				if (mov == null || mov.path == null)
				{
					toSend = new Object[] { "False" };
				}
				else if (Info.cellId == Integer.parseInt(param))
				{
					toSend = new Object[] { "True" };
				}
				else
				{
					mov.performMovement();
					if (Movement.moveOver())
					{
						if (Info.cellId == Integer.parseInt(param))
						{
							if ((Map.Id == 83887104 && Info.cellId == 396) || (Map.Id == 2884617 && Info.cellId == 424) || (Map.Id == 8912911 && Info.cellId == 424))
							{
								while (!Info.newMap)
								{
									Thread.sleep(50);
								}
								toSend = new Object[] { "True" };
							}
							else
							{
								toSend = new Object[] { "True" };
							}
						}
						else
						{
							toSend = new Object[] { "False" };
						}
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
			break;
			case "changeMap":
				String[] infoMov = param.split(",");
				MapMovement mapMovement = Movement.ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1].substring(2, infoMov[1].length() - 1));
				if (mapMovement == null)
				{
					toSend = new Object[] { "False" };
					Network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else
				{
					mapMovement.PerformChangement();
					if (Movement.moveOver())
					{
						toSend = new Object[] { "True" };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
			break;
			case "getResources":
				toSend = new Object[] { Interactive.getFarmCell() };
			break;
			case "harvest":
				if (Interactive.harvestCell(Integer.parseInt(param)))
				{
					toSend = new Object[] { Interactive.lastItemHarvestedId, Interactive.quantityLastItemHarvested, Info.weight, Info.weigthMax };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getStats":
				toSend = new Object[] { Stats.getStats() };
			break;
			case "goAstrub":
				if (Map.Id == 153880835)
				{
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20001, 3, 153880835);
					Network.sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request NPC to go to Astrub");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "goIncarnam":
				int r = Interactive.getStatue();
				if (r != -1)
				{
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Interactive.elementIdStatue, Interactive.skillInstanceUidStatue);
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getStatue":
				int statueCellId = Interactive.getStatue();
				if (statueCellId != -1)
				{
					toSend = new Object[] { statueCellId };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getBankDoor":
				if (Map.Id == 144931)
				{
					toSend = new Object[] { Bank.cellIdBrakmarIN, Bank.cellIdBrakmarOUT };
				}
				else if (Map.Id == 84674566)
				{
					toSend = new Object[] { Bank.cellIdAstrubIN, Bank.cellIdAstrubOUT };
				}
				else if (Map.Id == 147254)
				{
					toSend = new Object[] { Bank.cellIdBontaIN, Bank.cellIdBontaOUT };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "goBank":
				if (Map.Id == 144931)
				{ // Brakmar
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBrakmarIN, Bank.getSkill(Bank.interactiveBrakmarIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (Map.Id == 84674566)
				{ // Astrub
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveAstrubIN, Bank.getSkill(Bank.interactiveAstrubIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (Map.Id == 147254)
				{ // Bonta
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(Bank.interactiveBontaIN, Bank.getSkill(Bank.interactiveBontaIN));
					Network.sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "openBank":
				if (Map.Id == 83887104 || Map.Id == 2884617 || Map.Id == 8912911)
				{
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) NPC.npc.get(0).contextualId, 3, Map.Id);
					Network.sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
					if (waitToSend())
					{
						toSend = new Object[] { Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
					bankOppened = true;
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "closeBank":
				if (bankOppened)
				{
					Network.sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close bank");
					waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
				bankOppened = false;
			break;
			case "dropBank":
				if (bankOppened)
				{
					String[] toBank = param.split(",");
					Network.sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0].substring(1, toBank[0].length() - 1)), Integer.parseInt(toBank[1].substring(2, toBank[0].length() - 1))), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getBank":
				if (bankOppened)
				{
					String[] fromBank = param.split(",");
					Network.sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0].substring(1, fromBank[0].length() - 1)), -Integer.parseInt(fromBank[1].substring(2, fromBank[0].length() - 1))), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "dropBankList":
				if (bankOppened)
				{
					String[] toBankList = param.split(",");
					List<Integer> ids = new ArrayList<Integer>();
					for (String string : toBankList)
					{
						ids.add(Integer.parseInt(string.replaceAll("\\s+", "")));
					}
					ExchangeObjectTransfertListFromInvMessage exchangeObjectTransfertListFromInvMessage = new ExchangeObjectTransfertListFromInvMessage(ids);
					Network.sendToServer(exchangeObjectTransfertListFromInvMessage, ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list in bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getBankList":
				if (bankOppened)
				{
					String[] fromBankList = param.split(",");
					List<Integer> ids1 = new ArrayList<Integer>();
					for (String string : fromBankList)
					{
						ids1.add(Integer.parseInt(string.replaceAll("\\s+", "")));
					}
					ExchangeObjectTransfertListToInvMessage exchangeObjectTransfertListToInvMessage = new ExchangeObjectTransfertListToInvMessage(ids1);
					Network.sendToServer(exchangeObjectTransfertListToInvMessage, ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getBankKamas":
				if (bankOppened)
				{
					ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage = new ExchangeObjectMoveKamaMessage(-Integer.parseInt(param));
					Network.sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "dropBankKamas":
				if (bankOppened)
				{
					ExchangeObjectMoveKamaMessage exchangeObjectMoveKamaMessage1 = new ExchangeObjectMoveKamaMessage(Integer.parseInt(param));
					Network.sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "dropBankAll":
				if (bankOppened)
				{
					ExchangeObjectTransfertAllFromInvMessage exchangeObjectTransfertAllFromInvMessage = new ExchangeObjectTransfertAllFromInvMessage();
					Network.sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId, "Drop all items in bank");
					if (waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), Bank.getBank() };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getMonsters":
				toSend = new Object[] { Monsters.getMonsters() };
			break;
			case "attackMonster":
				int id = (int) Double.parseDouble(param);
				boolean canAtk = false;
				for (int i = 0; i < Monsters.monsters.size(); i++)
				{
					if (id == (int) Monsters.monsters.get(i).contextualId)
					{
						if (Info.cellId == Monsters.monsters.get(i).disposition.cellId)
						{
							canAtk = true;
						}
					}
				}
				if (canAtk)
				{
					GameRolePlayAttackMonsterRequestMessage gameRolePlayAttackMonsterRequestMessage = new GameRolePlayAttackMonsterRequestMessage(id);
					Network.sendToServer(gameRolePlayAttackMonsterRequestMessage, GameRolePlayAttackMonsterRequestMessage.ProtocolId, "Attack monster " + id);
					if (waitToSend())
					{
						toSend = new Object[] { "True" };
					}
					else
					{
						toSend = new Object[] { "False" };
					}
				}
				else
				{
					toSend = new Object[] { Monsters.getMonsters() };
				}
			break;
		}

		return toSend;
	}

}
