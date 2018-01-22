package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import protocol.network.Network;
import utils.d2o.D2oManager;

public class GameData{
	
	public GameData()
	{
	}
	
	public static int getWorldMap(double mapId){
		try
		{
			D2oManager d2oManager = new D2oManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String [] cmd = s.split(",");
			for(String si : cmd){
				String [] cmd2 = si.split(":");
				if(cmd2[0].equals("worldMap")){
					return (int) Double.parseDouble(cmd2[1]);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -99;
	}
	
	public static int getSpellNameId(int id){
		try
		{
			D2oManager d2oManager = new D2oManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\Spells.d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String [] cmd = s.split(",");
			for(String si : cmd){
				String [] cmd2 = si.split(":");
				if(cmd2[0].equals("nameId")){
					return Integer.parseInt(cmd2[1]);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -99;
	}
	
	public static int getWeaponNameId(int id){
		try
		{
			D2oManager d2oManager = new D2oManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\Items.d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String [] cmd = s.split(",");
			for(String si : cmd){
				String [] cmd2 = si.split(":");
				if(cmd2[0].equals("nameId")){
					return Integer.parseInt(cmd2[1]);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -99;
	}


}
