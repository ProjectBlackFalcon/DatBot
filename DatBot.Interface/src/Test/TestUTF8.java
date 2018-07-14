package Test;

import utils.d2o.modules.MapPosition;
import utils.d2o.modules.Monster;
import utils.d2o.modules.MountBehavior;
import utils.d2o.modules.Npc;
import utils.d2o.modules.PointOfInterest;
import utils.d2o.modules.Server;
import utils.d2o.modules.Spell;

public class TestUTF8 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Spell.getSpellById(161).getSpellLevel(3));
		System.out.println(MapPosition.getMapPositionById(153880322));
		System.out.println(Monster.getMonsterById(2271));
		System.out.println(Server.getServerById(208).getName());
		System.out.println(Npc.getNpcById(23).getName());
		System.out.println(MountBehavior.getMountBehaviorById(1).getName());
		System.out.println(PointOfInterest.getPointOfInterestById(26).getName());
	}
}
