package utils.d2o;

import java.io.IOException;

import protocol.network.util.DofusDataReader;

public class GameDataField {
	
    public String fieldName;   
    public String fieldType;  
    public GameDataField innerField;

    public GameDataField(DofusDataReader reader) throws IOException
    {
        fieldName = reader.readUTF();
        readType(reader);
    }

    public void readType(DofusDataReader reader) throws IOException
    {
        int num = reader.readInt();
    	String fieldType = String.valueOf(num);

        switch(num){
        	case -1:
        		fieldType = "Int";
        		break;
        	case -2:
        		fieldType = "Bool";
        		break;
        	case -3:
        		fieldType = "String";
        		break;
        	case -4:
        		fieldType = "Double";
        		break;
        	case -5:
        		fieldType = "I18N";
        		break;
        	case -6:
        		fieldType = "UInt";
        		break;
        	case -99:
        		fieldType = "Vector";
        		break;
        }
        this.fieldType = fieldType;

        if (fieldType.equals("Vector"))
        {
            innerField = new GameDataField(reader);
        }
    }
    
	public int getValue()
	{
		switch (this.fieldType)
		{
			case "Int":
				return -1;
			case "Bool":
				return -2;
			case "String":
				return -3;
			case "Double":
				return -4;
			case "I18N":
				return -5;
			case "UInt":
				return -6;
			case "Vector":
				return -99;
		}
		return Integer.parseInt(this.fieldType);
	}

}
