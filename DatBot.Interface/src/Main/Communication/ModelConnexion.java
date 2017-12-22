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

public class ModelConnexion{

	private InteractiveUseRequestMessage interactiveUseRequestMessage;
	private NpcGenericActionRequestMessage npcGenericactionRequestMessage;
	private boolean bankOppened;
	private Network network;
	private Interactive interactive;
	private Bank bank;
	private Movement movement;
	
	public ModelConnexion(Network network){
		this.network = network;
		this.interactive = network.getInteractive();
		this.bank = network.getBank();
		this.movement = network.getMovement();
	}

	public Object[] getReturn(String cmd, String param) throws NumberFormatException, Exception
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
			// network.socket.close();
			// sendToModel(message[0], message[1],"m", "rtn", message[4], new
			// Object[]{"True"});
			break;
			case "getMap":
				toSend = new Object[] { String.valueOf("(" + Info.coords[0]) + "," + String.valueOf(Info.coords[1]) + ")", Info.cellId, Info.worldmap, Integer.valueOf((int) Info.mapId) };
			break;
			case "move":
				CellMovement mov = getMovement().MoveToCell(Integer.parseInt(param));
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
				MapMovement mapMovement = getMovement().ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1].substring(2, infoMov[1].length() - 1));
				if (mapMovement == null)
				{
					toSend = new Object[] { "False" };
					getNetwork().append("Déplacement impossible ! Un obstacle bloque le chemin !",false);
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
				toSend = new Object[] { interactive.getFarmCell() };
			break;
			case "harvest":
				if (interactive.harvestCell(Integer.parseInt(param)))
				{
					toSend = new Object[] { interactive.getLastItemHarvestedId(), interactive.getQuantityLastItemHarvested(), Info.weight, Info.weigthMax };
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
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request NPC to go to Astrub");
					Communication.waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "goIncarnam":
				int r = interactive.getStatue();
				if (r != -1)
				{
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(interactive.getElementIdStatue(), interactive.getSkillInstanceUidStatue());
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using statue");
					Communication.waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getStatue":
				int statueCellId = interactive.getStatue();
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
					toSend = new Object[] { bank.cellIdBrakmarIN, bank.cellIdBrakmarOUT };
				}
				else if (Map.Id == 84674566)
				{
					toSend = new Object[] { bank.cellIdAstrubIN, bank.cellIdAstrubOUT };
				}
				else if (Map.Id == 147254)
				{
					toSend = new Object[] { bank.cellIdBontaIN, bank.cellIdBontaOUT };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "goBank":
				if (Map.Id == 144931)
				{ // Brakmar
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBrakmarIN, getInteractive().getSkill(bank.interactiveBrakmarIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Communication.waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (Map.Id == 84674566)
				{ // Astrub
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveAstrubIN, getInteractive().getSkill(bank.interactiveAstrubIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Communication.waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (Map.Id == 147254)
				{ // Bonta
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBontaIN, getInteractive().getSkill(bank.interactiveBontaIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					Communication.waitToSend();
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
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) new NPC().getNpc().get(0).contextualId, 3, Map.Id);
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { bank.getBank() };
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
					getNetwork().sendToServer(new LeaveDialogRequestMessage(), LeaveDialogRequestMessage.ProtocolId, "Close bank");
					Communication.waitToSend();
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0].substring(1, toBank[0].length() - 1)), Integer.parseInt(toBank[1].substring(2, toBank[0].length() - 1))), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0].substring(1, fromBank[0].length() - 1)), -Integer.parseInt(fromBank[1].substring(2, fromBank[0].length() - 1))), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(exchangeObjectTransfertListFromInvMessage, ExchangeObjectTransfertListFromInvMessage.ProtocolId, "Drop item list in bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(exchangeObjectTransfertListToInvMessage, ExchangeObjectTransfertListToInvMessage.ProtocolId, "Get item list from bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage, ExchangeObjectMoveKamaMessage.ProtocolId, "Get kamas from bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(exchangeObjectMoveKamaMessage1, ExchangeObjectMoveKamaMessage.ProtocolId, "Drop kamas in bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(exchangeObjectTransfertAllFromInvMessage, ExchangeObjectTransfertAllFromInvMessage.ProtocolId, "Drop all items in bank");
					if (Communication.waitToSend())
					{
						toSend = new Object[] { Stats.getStats(), bank.getBank() };
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
					getNetwork().sendToServer(gameRolePlayAttackMonsterRequestMessage, GameRolePlayAttackMonsterRequestMessage.ProtocolId, "Attack monster " + id);
					if (Communication.waitToSend())
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

	public Network getNetwork()
	{
		return network;
	}

	public void setNetwork(Network network)
	{
		this.network = network;
	}

	public Interactive getInteractive()
	{
		return interactive;
	}

	public void setInteractive(Interactive interactive)
	{
		this.interactive = interactive;
	}

	public Bank getBank()
	{
		return bank;
	}

	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	public Movement getMovement()
	{
		return movement;
	}

	public void setMovement(Movement movement)
	{
		this.movement = movement;
	}

}
