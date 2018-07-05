package utils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import ia.entities.Effect;
import ia.entities.Spell;
import org.json.simple.JSONArray;

import utils.d2i.d2iManager;
import utils.d2o.D2oManager;

public class GameData {

	public GameData() {
	}
	
	public static String getPathDatBot() {
		JSONArray a;
		String s = Paths.get("").toAbsolutePath().toString();
		int i = s.indexOf("DatBot");
		if(i == -1){
            s = Paths.get("").toAbsolutePath().toString()+"/DatBot";
        }else{
            s = s.substring(0, i + 6);
        }
		return s;
	}

	public static int getWorldMap(double mapId) {
		try {
			D2oManager d2oManager = new D2oManager(GameData.getPathDatBot() + "/DatBot.Interface/utils/gamedata/MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("worldMap")) { return (int) Double.parseDouble(cmd2[1]); }
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -99;
	}
	
	public static int[] getCoordMap(int mapId){
		try {
			D2oManager d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			s = s.replaceAll(",", ":");
			String[] cmd2 = s.split(":");
			return new int[] {Integer.parseInt(cmd2[3]),Integer.parseInt(cmd2[5])};
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getCoordMapString(int mapId){
		try {
			D2oManager d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			s = s.replaceAll(",", ":");
			String[] cmd2 = s.split(":");
			return "[" + Integer.parseInt(cmd2[3]) + "," + Integer.parseInt(cmd2[5]) + "]";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getSpellNameId(int id) {
		return getDataFromFile(id,"Spells");
	}

	public static int getWeaponNameId(int id) {
		return getDataFromFile(id,"Items");
	}
	
	public static String getClueName(int id){
		return d2iManager.getText(getDataFromFile(id,"PointOfInterest"));
	}
	
	public static String getItemName(int id){
		return d2iManager.getText(getDataFromFile(id,"Items"));
	}
	
	public static String getMountBehaviorName(int id){
		return d2iManager.getText(getDataFromFile(id,"MountBehaviors"));
	}
	
	public static String getNpcName(int id){
		return d2iManager.getText(getDataFromFile(id,"Npcs"));
	}
	
	public static String getTextInfo(int id){
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/InfoMessages.d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("textId")) { return d2iManager.getText(Integer.parseInt(cmd2[1])); }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPodsFromItem(int id){
		return getDataFromFile(id,"Items","realWeight");
	}
	
	public static String getKeyCaracs(int id){
		return getDataFromFile(id,"Characteristics","keyword");
	}
	
	public static Integer getEffectIdFromSpell(int id, int uid){
		List<Spell> spells = new ArrayList<>();
		Spell s1 = getSpell(id, 1); if (s1 != null) spells.add(s1);
		Spell s2 = getSpell(id, 2); if (s2 != null) spells.add(s2);
		Spell s3 = getSpell(id, 3); if (s3 != null) spells.add(s3);
		for (Spell spell : spells)
		{
			Effect[] e = spell.getEffects();
			for (Effect effect : e)
			{
				if(effect.getEffectUid() == uid){
					return effect.getEffectId();
				}
			}
			Effect[] ce = spell.getCriticalEffect();
			for (Effect effect : ce)
			{
				if(effect.getEffectUid() == uid){
					return effect.getEffectId();
				}
			}
		}
		return null;
	}
	
	public static int getCharacFromEffect(int id){
		return Integer.parseInt(getDataFromFile(id,"Effects","characteristic"));
	}
	
	public static String getNameServer(int id){
		return d2iManager.getText(getDataFromFile(id,"Servers"));
	}

	public static int getMonsterLvl(int id, int grade){
        D2oManager d2oManager;
        List<Spell> spellList = new ArrayList<>();
        boolean isGrade = false;
        try {
            d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/Monsters.d2o");
            String s = d2oManager.searchObjectById(id);
            s = s.replace("{", "");
            s = s.replace(" ", "");
            s = s.replace("}", "");
            s = s.replaceAll("\n", "");
            String[] cmd = s.split(",(?![^\\(\\[]*[\\]\\)])");
            for (String si : cmd) {
                String[] cmd2 = si.split(":(?![^\\(\\[]*[\\]\\)])");
                if (cmd2[0].equals("grades")) {
                    String[] grades = cmd2[1].substring(1,cmd2[1].length()-1).split(",grade:");
                    for(int i = 0 ; i < grades.length ; i++){
                        if(i>0) grades[i] = "grade:" + grades[i];

                        System.out.println(grades[i]);
                        String[] gradesSplit = grades[i].split(",");

                        for (String sgs : gradesSplit) {
                            System.out.println(sgs);
                            String[] sgs2 = sgs.split(":(?![^\\(\\[]*[\\]\\)])");
                            if (sgs2[0].equals("level")) {
                                if(isGrade)
                                    return Integer.parseInt(sgs2[1]);
                            }
                            if (sgs2[0].equals("grade") && sgs2[1].equals(String.valueOf(grade))) {
                                isGrade = true;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

	public static List<Spell> getMonsterSpells(int id, int lvl){
		D2oManager d2oManager;
		List<Spell> spellList = new ArrayList<>();
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/Monsters.d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",(?![^\\(\\[]*[\\]\\)])");
			for (String si : cmd) {
				String[] cmd2 = si.split(":(?![^\\(\\[]*[\\]\\)])");
				if (cmd2[0].equals("spells")) {
					String[] spells = cmd2[1].substring(1,cmd2[1].length()-1).split(",");
					for(String idSpell : spells){
                        spellList.add(getSpell(Integer.parseInt(idSpell),lvl));
                    }
				}
			}
			return spellList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Spell getSpell(int id, int lvl) {
		D2oManager d2oManager;
		Spell spell = new Spell();
		spell.setName(d2iManager.getText(getDataFromFile(id,"Spells")));
		int idSpell = getSpellId(id,lvl);
		spell.setId(idSpell);
		spell.setGrade(lvl);
		spell.setSpellId(id);
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/SpellLevels.d2o");
			String s = d2oManager.searchObjectById(idSpell);
			if(s == "") return null;
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",(?![^\\(\\[]*[\\]\\)])");
			for (String si : cmd) {
				String[] cmd2 = si.split(":(?![^\\(\\[]*[\\]\\)])");
				if (cmd2[0].equals("spellBreed")) { spell.setSpellBreed(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("apCost")) { spell.setApCost(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("minRange")) { spell.setMinRange(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("range")) { spell.setRange(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("castInLine")) { spell.setCastInLine(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("castInDiagonal")) { spell.setCastInDiagonal(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("castTestLos")) { spell.setCastTestLos(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("needFreeCell")) { spell.setNeedFreeCell(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("needFreeTrapCell")) { spell.setNeedFreeTrapCell(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("criticalHitProbability")) { spell.setCriticalHitProbability(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("needTakenCell")) { spell.setNeedTakenCell(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("rangeCanBeBoosted")) { spell.setRangeCanBeBoosted(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("hideEffects")) { spell.setHideEffects(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("hidden")) { spell.setHidden(Boolean.parseBoolean(cmd2[1])); }
				else if (cmd2[0].equals("maxStack")) { spell.setMaxStack(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("maxCastPerTurn")) { spell.setMaxCastPerTurn(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("maxCastPerTarget")) { spell.setMaxCastPerTarget(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("minCastInterval")) { spell.setMinCastInterval(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("initialCooldown")) { spell.setInitialCooldown(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("globalCooldown")) { spell.setGlobalCooldown(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("minPlayerLevel")) { spell.setMinPlayerLevel(Integer.parseInt(cmd2[1])); }
				else if (cmd2[0].equals("effects")) {
					setEffects(spell, cmd2[1], false);
				}
				else if (cmd2[0].equals("criticalEffect")) {
					setEffects(spell, cmd2[1], true);
				}
			}
			return spell;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setEffects(Spell spell, String s, boolean isCrit) {
		String[] effects = s.substring(1, s.length()-1).split(",targetMask");
		Effect[] effectsArray = new Effect[effects.length];
		for(int i = 0 ; i < effects.length ; i++){
			if(i>0) effects[i] = "targetMask" + effects[i];
			Effect effect = new Effect();
			String[] cmdEff = effects[i].split(",");
			for(String se2 : cmdEff){
				String[] se3 = se2.split(":");
				if (se3[0].equals("visibleInTooltip")) { effect.setVisibleInTooltip(Boolean.parseBoolean(se3[1])); }
				else if (se3[0].equals("visibleInFightLog")) { effect.setVisibleInFightLog(Boolean.parseBoolean(se3[1])); }
				else if (se3[0].equals("visibleInBuffUi")) { effect.setVisibleInBuffUi(Boolean.parseBoolean(se3[1])); }
				else if (se3[0].equals("random")) { effect.setRandom(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("targetId")) { effect.setTargetId(Long.parseLong(se3[1])); }
				else if (se3[0].equals("effectId")) { effect.setEffectId(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("diceNum")) { effect.setDiceNum(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("duration")) { effect.setDuration(Long.parseLong(se3[1])); }
				else if (se3[0].equals("effectUid")) { effect.setEffectUid(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("diceSide")) { effect.setDiceSide(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("value")) { effect.setValue(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("effectElement")) { effect.setEffectElement(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("delay")) { effect.setDelay(Long.parseLong(se3[1])); }
				else if (se3[0].equals("group")) { effect.setGroup(Integer.parseInt(se3[1])); }
				else if (se3[0].equals("rawZone")) { effect.setRawZone(se3[1]); }
				else if (se3[0].equals("targetMask")) { effect.setTargetMask(se3[1]); }
				else if (se3[0].equals("triggers")) { effect.setTriggers(se3[1]); }
			}
			effectsArray[i] = effect;
		}
		if(isCrit){
			spell.setCriticalEffect(effectsArray);
		} else {
			spell.setEffects(effectsArray);
		}
	}

	public static int getSpellId(int id, int lvl) {
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/Spells.d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",(?![^\\(\\[]*[\\]\\)])");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("spellLevels")) {
					String[] levels = cmd2[1].substring(1,cmd2[1].length()-1).split(",");
					return Integer.parseInt(levels[lvl-1]);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	private static String getDataFromFile(int id, String file, String value) {
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/" + file + ".d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals(value)) { return cmd2[1]; }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int getDataFromFile(int id, String file) {
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "/DatBot.Interface/utils/gamedata/" + file + ".d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("nameId")) { return Integer.parseInt(cmd2[1]); }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -9999;
	}

}
