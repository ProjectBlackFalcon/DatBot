package utils.d2o.modules;

public class MonsterDrop {

	public int dropId;
	public int monsterId;
	public int objectId;
	public Integer percentDropForGrade1;
	public Integer percentDropForGrade2;
	public Integer percentDropForGrade3;
	public Integer percentDropForGrade4;
	public Integer percentDropForGrade5;
	public int count;
	public boolean hasCriteria;
	private Monster _monster;

	public Monster getMonster() {
		if (this._monster == null) {
			this._monster = Monster.getMonsterById(this.monsterId);
		}
		return this._monster;
	}

}
