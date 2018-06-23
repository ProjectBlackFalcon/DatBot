package protocol.enums;

public enum BoostableCharacteristicEnum {

	Fo(10), Vi(11), Sa(12), Cha(13), Agi(14), Int(15);

	BoostableCharacteristicEnum(int id) {
		this.id = id;
	}

	private final int id;

	public int getId() {
		return id;
	}
	
	

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

}
