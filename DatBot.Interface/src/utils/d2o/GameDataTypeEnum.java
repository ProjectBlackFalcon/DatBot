package utils.d2o;

public enum GameDataTypeEnum {
	Int, Bool, String, Double, I18N, UInt, Vector;

	public int getValue()
	{
		switch (this)
		{
			case Int:
				return -1;
			case Bool:
				return -2;
			case String:
				return -3;
			case Double:
				return -4;
			case I18N:
				return -5;
			case UInt:
				return -6;
			case Vector:
				return -99;
		}
		return 0;
	}
}
