package main.communication;

import java.util.ArrayList;
import java.util.List;
import game.Info;
import game.map.MapMovement;
import game.movement.CellMovement;
import game.movement.Movement;
import game.plugin.Bank;
import game.plugin.Interactive;
import game.plugin.Monsters;
import game.plugin.Npc;
import game.plugin.Stats;
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
import utils.d2p.map.Map;

public class ModelConnexion {

	private String msg;

	private InteractiveUseRequestMessage interactiveUseRequestMessage;
	private NpcGenericActionRequestMessage npcGenericactionRequestMessage;
	private boolean bankOppened;
	private Network network;
	private Interactive interactive;
	private Bank bank;
	private Movement movement;
	private Info info;
	private Stats stats;
	private Monsters monsters;
	private Map map;

	public ModelConnexion(Network network) throws InterruptedException
	{
		this.network = network;
	}

	public Object[] getReturn(String cmd, String param) throws NumberFormatException, Exception
	{
		this.interactive = network.getInteractive();
		this.bank = network.getBank();
		this.movement = network.getMovement();
		this.stats = network.getStats();
		this.info = network.getInfo();
		this.monsters = network.getMonsters();
		this.map = network.getMap();
		
		Object[] toSend = null;

		switch (cmd)
		{
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
				toSend = new Object[] { String.valueOf("(" + this.info.getCoords()[0]) + "," + String.valueOf(this.info.getCoords()[1]) + ")", this.info.getCellId(), this.info.getWorldmap(), Integer.valueOf((int) this.info.getMapId()) };
			break;
			case "move":
				CellMovement mov = getMovement().MoveToCell(Integer.parseInt(param));
				if (mov == null || mov.path == null)
				{
					toSend = new Object[] { "False" };
				}
				else if (this.info.getCellId() == Integer.parseInt(param))
				{
					toSend = new Object[] { "True" };
				}
				else
				{
					mov.performMovement();
					if (this.getMovement().moveOver())
					{
						if (this.info.getCellId() == Integer.parseInt(param))
						{
							if ((this.map.getId() == 83887104 && this.info.getCellId() == 396) || (this.map.getId() == 2884617 && this.info.getCellId() == 424) || (this.map.getId() == 8912911 && this.info.getCellId() == 424))
							{
								while (!this.info.isNewMap())
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
				MapMovement mapMovement = getMovement().ChangeMap(Integer.parseInt(infoMov[0]), infoMov[1]);
				if (mapMovement == null)
				{
					toSend = new Object[] { "False" };
					this.network.append("Déplacement impossible ! Un obstacle bloque le chemin !");
				}
				else
				{
					mapMovement.PerformChangement();
					if (this.getMovement().moveOver())
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
					toSend = new Object[] { interactive.getLastItemHarvestedId(), interactive.getQuantityLastItemHarvested(), this.info.getWeight(), this.info.getWeigthMax() };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "getStats":
				toSend = new Object[] { this.stats };
			break;
			case "goAstrub":
				if (this.map.getId() == 153880835)
				{
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage(-20001, 3, 153880835);
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Request Npc to go to Astrub");
					this.waitToSend();
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
					this.waitToSend();
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
				if (this.map.getId() == 144931)
				{
					toSend = new Object[] { bank.cellIdBrakmarIN, bank.cellIdBrakmarOUT };
				}
				else if (this.map.getId() == 84674566)	
				{
					toSend = new Object[] { bank.cellIdAstrubIN, bank.cellIdAstrubOUT };
				}
				else if (this.map.getId() == 147254)
				{
					toSend = new Object[] { bank.cellIdBontaIN, bank.cellIdBontaOUT };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "goBank":
				if (this.map.getId() == 144931)
				{ // Brakmar
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBrakmarIN, getInteractive().getSkill(bank.interactiveBrakmarIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					this.waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (this.map.getId() == 84674566)
				{ // Astrub
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveAstrubIN, getInteractive().getSkill(bank.interactiveAstrubIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					this.waitToSend();
					toSend = new Object[] { "True" };
				}
				else if (this.map.getId() == 147254)
				{ // Bonta
					interactiveUseRequestMessage = new InteractiveUseRequestMessage(bank.interactiveBontaIN, getInteractive().getSkill(bank.interactiveBontaIN));
					getNetwork().sendToServer(interactiveUseRequestMessage, InteractiveUseRequestMessage.ProtocolId, "Using bank door");
					this.waitToSend();
					toSend = new Object[] { "True" };
				}
				else
				{
					toSend = new Object[] { "False" };
				}
			break;
			case "openBank":
				if (this.map.getId() == 83887104 || this.map.getId() == 2884617 || this.map.getId() == 8912911)
				{
					npcGenericactionRequestMessage = new NpcGenericActionRequestMessage((int) new Npc().getNpc().get(0).getContextualId(), 3, this.map.getId());
					getNetwork().sendToServer(npcGenericactionRequestMessage, NpcGenericActionRequestMessage.ProtocolId, "Open bank");
					if (this.waitToSend())
					{
						toSend = new Object[] { bank };
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
					this.waitToSend();
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(toBank[0]), Integer.parseInt(toBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					getNetwork().sendToServer(new ExchangeObjectMoveMessage(Integer.parseInt(fromBank[0]), -Integer.parseInt(fromBank[1])), ExchangeObjectMoveMessage.ProtocolId, "Drop item in bank");
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
					if (this.waitToSend())
					{
						toSend = new Object[] { this.stats, bank };
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
				toSend = new Object[] { this.getMonsters() };
			break;
			case "attackMonster":
				int id = (int) Double.parseDouble(param);
				boolean canAtk = false;
				for (int i = 0; i < this.getMonsters().getMonsters().size(); i++)
				{
					if (id == (int) this.getMonsters().getMonsters().get(i).getContextualId())
					{
						if (this.info.getCellId() == this.getMonsters().getMonsters().get(i).getDisposition().getCellId())
						{
							canAtk = true;
						}
					}
				}
				if (canAtk)
				{
					GameRolePlayAttackMonsterRequestMessage gameRolePlayAttackMonsterRequestMessage = new GameRolePlayAttackMonsterRequestMessage(id);
					getNetwork().sendToServer(gameRolePlayAttackMonsterRequestMessage, GameRolePlayAttackMonsterRequestMessage.ProtocolId, "Attack monster " + id);
					if (this.waitToSend())
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
					toSend = new Object[] { this.getMonsters() };
				}
			break;
		}
		return toSend;
	}

	public void getReturn(String[] message) throws NumberFormatException, Exception
	{
		new Thread(new Runnable() {
			public void run()
			{
				try
				{
					Communication.sendToModel(message[0], message[1], "m", "rtn", message[4], getReturn(message[4], message[5]));
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public boolean waitToSend() throws InterruptedException
	{
		while (!info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight())
		{
			Thread.sleep(50);
		}
		while (!info.isBasicNoOperationMsg())
		{
			Thread.sleep(50);
		}
		/*
		 * this.network.append((!Info.newMap && !Info.Storage &&
		 * !Info.StorageUpdate && !Info.leaveExchange) &&
		 * !Info.basicNoOperationMsg); this.network.append(Info.newMap + " " +
		 * Info.Storage + " " + Info.StorageUpdate + " " + Info.leaveExchange +
		 * " " + Info.basicNoOperationMsg);
		 */
		if (info.isBasicNoOperationMsg() && !info.isNewMap() && !info.isStorage() && !info.isStorageUpdate() && !info.isLeaveExchange() && !info.isJoinedFight())
		{
			return false;
		}
		else
		{
			return true;
		}
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

	public Info getInfo()
	{
		return info;
	}

	public void setInfo(Info info)
	{
		this.info = info;
	}

	public Stats getStats()
	{
		return stats;
	}

	public void setStats(Stats stats)
	{
		this.stats = stats;
	}

	public Monsters getMonsters()
	{
		return monsters;
	}

	public void setMonsters(Monsters monsters)
	{
		this.monsters = monsters;
	}
}
