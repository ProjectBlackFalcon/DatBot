package utils.d2o.modules;

public class MonsterGrade {

	public int grade;
	public int monsterId;
	public int level;
	public int vitality;
	public int paDodge;
	public int pmDodge;
	public int wisdom;
	public int earthResistance;
	public int airResistance;
	public int fireResistance;
	public int waterResistance;
	public int neutralResistance;
	public int gradeXp;
	public int lifePoints;
	public int actionPoints;
	public int movementPoints;
	public int damageReflect;
	public int hiddenLevel;
	public int strength;
	public int intelligence;
	public int chance;
	public int agility;
	private Monster _monster;

	public Monster getMonster() {
		if (this._monster == null) {
			this._monster = Monster.getMonsterById(this.monsterId);
		}
		return this._monster;
	}

}
