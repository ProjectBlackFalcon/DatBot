package Test;

import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.entities.entity.OtherEntity;
import ia.fight.FightIntelligence;
import ia.map.MapIA;
import ia.map.Position;
import ia.utils.UtilsMath;
import jdk.nashorn.internal.ir.annotations.Ignore;
import main.Main;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import gamedata.d2o.modules.Spell;
import gamedata.d2o.modules.SpellLevel;
import protocol.network.types.game.character.characteristic.CharacterBaseCharacteristic;
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
import protocol.network.types.game.context.fight.GameFightMinimalStats;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FightTest {

	@BeforeClass
	public static void before() throws IOException {
		d2iManager.init(Main.D2I_PATH);
		MapManager.init(Main.D2P_PATH);
	}

	@Test
	public void getSpellIdTest() {
		Assert.assertEquals(803, Spell.getSpellById(161).getSpellLevel(3).getId());
	}

	@Test
	public void getSpellTest() {
		SpellLevel spell = Spell.getSpellById(161).getSpellLevel(3);
		Assert.assertEquals(803, spell.getId());
		Assert.assertEquals(12, spell.getRange());
		Assert.assertEquals(15, spell.getCriticalHitProbability());
		Assert.assertEquals(3, spell.getGrade());
		Assert.assertEquals(2, spell.getCriticalEffect().size());
		Assert.assertEquals(2, spell.getEffects().size());
		Assert.assertEquals(22, spell.getCriticalEffect().get(0).getDiceNum());
		Assert.assertEquals(320, spell.getCriticalEffect().get(1).getEffectId());
		Assert.assertEquals("Flèche Magique", spell.getSpell().getName());
	}

	@Test
	@Ignore
	public void getMonsterSpellsTest() {
//		List<Spell> spells = GameData.getMonsterSpells(489, 1);
//		System.out.println(spells.get(0));
//		Assert.assertEquals(2, spells.size());
//		Assert.assertEquals("Picore", spells.get(0).getName());
//		Assert.assertEquals(121145, spells.get(1).getCriticalEffect()[0].getEffectUid());
	}

	@Test
	@Ignore
	public void getMonsterLevelTest() {
//		Assert.assertEquals(11, GameData.getMonsterLvl(489, 1));
	}

	@Test
	public void testTransformedCell() throws IOException {
		Map map = MapManager.FromId(153880322);
		Assert.assertTrue(!map.getCells().get(0).isMov() && !map.getCells().get(0).isLos());
		Assert.assertTrue(!map.getCells().get(309).isMov() && map.getCells().get(309).isLos());
	}
	
	@Test
	public void testLos() throws IOException {
		Map map = MapManager.FromId(153880322);

		Assert.assertTrue(FightIntelligence.visibility(MapIA.reshapeToIA(385), MapIA.reshapeToIA(313), MapIA.getCleanCells(map.getCells(), new ArrayList<>())));
		Assert.assertTrue(FightIntelligence.visibility(MapIA.reshapeToIA(385), MapIA.reshapeToIA(201), MapIA.getCleanCells(map.getCells(), new ArrayList<>())));
		Assert.assertFalse(FightIntelligence.visibility(MapIA.reshapeToIA(385), MapIA.reshapeToIA(269), MapIA.getCleanCells(map.getCells(), new ArrayList<>())));
		Assert.assertFalse(FightIntelligence.visibility(MapIA.reshapeToIA(269), MapIA.reshapeToIA(313), MapIA.getCleanCells(map.getCells(), new ArrayList<>())));

	}
	
	@Test
	public void testLosWithEntity() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		Entity e = new OtherEntity();
		e.setPosition(MapIA.reshapeToIA(342));
		entities.add(e);
		Entity eMain = new MainEntity();
		eMain.setPosition(MapIA.reshapeToIA(385));
		entities.add(eMain);
		Assert.assertFalse(FightIntelligence.visibility(MapIA.reshapeToIA(385), MapIA.reshapeToIA(313), MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertTrue(FightIntelligence.visibility(MapIA.reshapeToIA(385), MapIA.reshapeToIA(201), MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpell() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(161).getSpellLevel(3);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(0, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(296),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpellMaxRange() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(161).getSpellLevel(3);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(0, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(282),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpellMinRange() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(163).getSpellLevel(1);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(0, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(441),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(427),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpellWithModifier() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(161).getSpellLevel(3);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(1, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(296),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(282),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(268),  MapIA.getCleanCells(map.getCells(), entities)));
		spell = Spell.getSpellById(163).getSpellLevel(1);
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(441),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(427),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpellObstacle() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(161).getSpellLevel(3);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(0, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		OtherEntity e2 = new OtherEntity();
		e2.setPosition(MapIA.reshapeToIA(427));
		entities.add(e2);
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(398),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e,  Spell.getSpellById(173).getSpellLevel(1), MapIA.reshapeToIA(398),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testLosSpellCastInLine() throws IOException {
		Map map = MapManager.FromId(153880322);
		SpellLevel spell = Spell.getSpellById(169).getSpellLevel(1);
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		e.setAdditionalInfo(new CharacterCharacteristicsInformations());
		e.getAdditionalInfo().setRange(new CharacterBaseCharacteristic(0, 0, 0, 0, 0));
		e.setPosition(MapIA.reshapeToIA(470));
		entities.add(e);
		Assert.assertTrue(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(441),  MapIA.getCleanCells(map.getCells(), entities)));
		Assert.assertFalse(FightIntelligence.isCellTargetableBySpell(e, spell, MapIA.reshapeToIA(440),  MapIA.getCleanCells(map.getCells(), entities)));
	}
	
	@Test
	public void testGetPathMPAvailability() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(1);
		e.setInfo(info);

		e.setPosition(MapIA.reshapeToIA(470));
		System.out.println(e.getPosition());
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities), MapIA.reshapeToIA(470), MapIA.reshapeToIA(471), false);
		System.out.println(path);
		Assert.assertFalse(UtilsMath.isPositionAccessible(path, e));
		
		info.getStats().setMovementPoints(2);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
	}
	
	@Test
	public void testGetPathObstacleNoDiag() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(5);
		e.setInfo(info);

		e.setPosition(MapIA.reshapeToIA(313));
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(269), false);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
		
		info.getStats().setMovementPoints(7);
		e.setPosition(MapIA.reshapeToIA(299));
		path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(256), false);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
	}
	
	@Test
	public void testGetPathEntityObstacleNoDiag() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(4);
		e.setInfo(info);
		e.setPosition(MapIA.reshapeToIA(369));
		
		OtherEntity e2 = new OtherEntity();
		e2.setPosition(MapIA.reshapeToIA(354));
		entities.add(e2);

		
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(340), false);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
	}
	
	@Test
	public void testGetPathObstacleDiag() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(3);
		e.setInfo(info);

		e.setPosition(MapIA.reshapeToIA(313));
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(269), true);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
		
		info.getStats().setMovementPoints(5);
		e.setPosition(MapIA.reshapeToIA(299));
		path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(256), true);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
	}
	
	@Test
	public void testGetPathEntityObstacleDiag() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(2);
		e.setInfo(info);
		e.setPosition(MapIA.reshapeToIA(369));
		
		OtherEntity e2 = new OtherEntity();
		e2.setPosition(MapIA.reshapeToIA(354));
		entities.add(e2);

		
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(340), true);
		Assert.assertTrue(UtilsMath.isPositionAccessible(path, e));
	}
	
	@Test
	public void testGetPathUnaccessibleCell() throws IOException {
		Map map = MapManager.FromId(153880322);
		
		List<Entity> entities = new ArrayList<>();
		MainEntity e = new MainEntity();
		GameFightFighterInformations info = new GameFightFighterInformations();
		info.setStats(new GameFightMinimalStats());
		info.getStats().setMovementPoints(4);
		e.setInfo(info);
		e.setPosition(MapIA.reshapeToIA(369));
		entities.add(e);
		
		OtherEntity e2 = new OtherEntity();
		e2.setPosition(MapIA.reshapeToIA(354));
		entities.add(e2);

		
		List<Position> path = UtilsMath.getPath(MapIA.getCleanCells(map.getCells(), entities),e.getPosition(), MapIA.reshapeToIA(354), false);
		System.out.println(path);
		Assert.assertFalse(UtilsMath.isPositionAccessible(path, e));
	}
	
	
	
	
	
}
