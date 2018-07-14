package ia.fight;

import java.util.List;

import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.map.MapIA;
import ia.map.Position;
import ia.map.TransformedCell;
import ia.utils.UtilsMath;
import ia.utils.UtilsProtocol;
import utils.d2o.modules.SpellLevel;
import utils.d2p.map.CellData;

public class ChestIntelligence extends FightIntelligence {

	public ChestIntelligence(UtilsProtocol protocol) {
		super(protocol);
	}

	@Override
	public void getBestTurn(Entity roxxor, List<Entity> entities, List<CellData> cells) throws Exception {
		Entity victim = null;
		System.out.println("Entity size : " + entities.size());
		for (Entity e : entities) {
			if (e != roxxor) victim = e;
		}
		if (!victim.getInfo().isAlive()) {
			System.out.println(victim.getLvl() + " is dead");
			return;
		}
		Position optPos = getOptimizedMovement(MapIA.getCleanCells(cells, entities), (MainEntity) roxxor, victim);
		int[] optSpell = getOptimizedSpell(MapIA.getCleanCells(cells, entities), (MainEntity) roxxor, victim);

		if (optPos != null) {
			System.out.println("Moving to opt : " + optPos);
			this.protocol.moveTo(MapIA.reshapeToNetwork(optPos));
			this.protocol.stop(0.2);
		}
		else if (optSpell != null) {
			System.out.println("Casting spell : " + optSpell[0] + " to " + optSpell[1]);
			this.protocol.castSpell(optSpell[0], optSpell[1]);
			this.protocol.stop(0.2);
		}
		else {
			System.out.println("Ending turn");
			this.protocol.endTurn();
		}
	}

	private int[] getOptimizedSpell(TransformedCell[][] cells, MainEntity roxxor, Entity victim) {
		List<SpellLevel> spells = roxxor.getSpells();
		SpellLevel magicArrow = null;
		SpellLevel harcelante = null;
		SpellLevel tirPuissant = null;

		for (SpellLevel spell : spells) {
			if (spell.getSpellId() == 161) {
				magicArrow = spell;
			}
			if (spell.getSpellId() == 173) {
				harcelante = spell;
			}
			if (spell.getSpellId() == 166) {
				tirPuissant = spell;
			}
		}
		
		System.out.println("Spell available :  tirPuissant : " + tirPuissant + " magicArrow : " + magicArrow + " harcelante : " + harcelante);

		if (isCellTargetableBySpell(roxxor, tirPuissant, roxxor.getPosition(), cells) && canCastSpell(roxxor, tirPuissant) && isCac(roxxor, victim, cells)) {
			tirPuissant.setNumberCasted(tirPuissant.getNumberCasted() + 1);
			tirPuissant.setTurnLeftBeforeCast(tirPuissant.getMinCastInterval());
			return new int[] { tirPuissant.getSpellId(), roxxor.getInfo().getDisposition().getCellId() };
		} else if (isCellTargetableBySpell(roxxor, magicArrow, victim.getPosition(), cells) && canCastSpell(roxxor, magicArrow)) {
			return new int[] { magicArrow.getSpellId(), victim.getInfo().getDisposition().getCellId() };
		}
		else if (isCellTargetableBySpell(roxxor, harcelante, victim.getPosition(), cells) && canCastSpell(roxxor, harcelante)) {
			return new int[] { harcelante.getSpellId(), victim.getInfo().getDisposition().getCellId() };
		}
		else {
			return null;
		}

	}

	private Position getOptimizedMovement(TransformedCell[][] cells, MainEntity roxxor, Entity victim) {

		if (roxxor.getInfo().getStats().getMovementPoints() == 0) { return null; }

		System.out.println("Getting optimized movement. Initial positions : roxxor (" + roxxor.getPosition() + "), victim (" + victim.getPosition() + ")");
		List<Position> positions = UtilsMath.getPath(cells, roxxor.getPosition(), victim.getPosition(), true);

		System.out.println("Path : " + positions);

		if (positions.size() == 1 && UtilsMath.getPath(cells, roxxor.getPosition(), positions.get(0), false).size() == 2) { return UtilsMath.getPath(cells, roxxor.getPosition(), positions.get(0), false).get(0); }

		Position previousPosition;

		for (int i = 0; i < positions.size(); i++) {
			if (i > 0) {
				previousPosition = positions.get(i - 1);
			}
			else {
				previousPosition = roxxor.getPosition();
			}

			Position actualPosition = positions.get(i);

			System.out.println(positions.get(i));

			int distancePrevious = UtilsMath.getPath(cells, previousPosition, victim.getPosition(), false).size();
			int distanceActual = UtilsMath.getPath(cells, actualPosition, victim.getPosition(), false).size();

			if (distanceActual >= distancePrevious) {

				System.out.println("Previous distance was smaller or equal ! " + distanceActual + " " + distancePrevious);

				for (int j = positions.size() - 1; j >= i; j--) {
					positions.remove(j);
				}
			}
		}

		System.out.println("Better path : " + positions);
		Position kept = roxxor.getPosition();
		Position kept_ndo = null;
		int MPLeft = roxxor.getInfo().getStats().getMovementPoints();
		System.out.println("MP Left : " + MPLeft);

		for (Position position1 : positions) {
			System.out.println("Trying path from " + position1);
			if (UtilsMath.getPath(cells, roxxor.getPosition(), position1, false).size() <= MPLeft) {
				System.out.println("Found path : " + UtilsMath.getPath(cells, roxxor.getPosition(), position1, false));
				if (position1.deepEquals(victim.getPosition())) {
					System.out.println("Equals victim !!!!");
					break;
				}
				kept = position1;
				kept_ndo = position1;
				System.out.println("Updated positions.");
			}
			else {
				break;
			}
		}

		System.out.println("Kept : " + kept);

		MPLeft -= UtilsMath.getPath(cells, roxxor.getPosition(), kept, false).size();

		System.out.println("MP Left : " + MPLeft);

		if (MPLeft > 0) {
			positions = UtilsMath.getPath(cells, kept, victim.getPosition(), false);
			System.out.println("Path : " + positions);
			for (Position position : positions) {
				System.out.println("Trying path from " + position);
				if (UtilsMath.getPath(cells, kept, position, false).size() <= MPLeft) {
					System.out.println("Found path : " + UtilsMath.getPath(cells, roxxor.getPosition(), position, false));
					if (position.deepEquals(victim.getPosition())) {
						System.out.println("Equals victim !!!!");
						break;
					}
					kept_ndo = position;
					System.out.println("Updated positions.");
				}
				else {
					break;
				}
			}
		}

		return kept_ndo;
	}
}
