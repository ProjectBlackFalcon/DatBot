package utils.d2o;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import protocol.network.Network;
import protocol.network.util.DofusDataReader;

public class JsonUnpacker {

	public StringBuilder jsonBuilder;
	// private JToken unpackedJson;
	public Map<Integer, Integer> objectPointerTable;
	public DofusDataReader reader;
	public Map<Integer, GameDataClassDefinition> classDefinitions;

	public JsonUnpacker(DofusDataReader reader, Map<Integer, Integer> objectPointerTable, Map<Integer, GameDataClassDefinition> classDefinitions)
	{
		this.reader = reader;
		this.objectPointerTable = objectPointerTable;
		this.classDefinitions = classDefinitions;
		jsonBuilder = new StringBuilder();
	}

	public void Unpack()
	{
		buildJsonString();
	}

	private void buildJsonString()
	{
		addArrayOpenBracket();
		addObjects();
		addArrayCloseBracket();
	}

	private void addArrayOpenBracket()
	{
		jsonBuilder.append("[");
	}

	private void addObjects()
	{
		Set<Integer> keys = objectPointerTable.keySet();

		int[] indexTableKeys = new int[keys.size()];
		int index = 0;
		for (Integer element : keys)
			indexTableKeys[index++] = element.intValue();

		for (int i = 0; i < indexTableKeys.length; i++)
		{
			jsonBuilder.append(getObjectJsonString(indexTableKeys[i])).append(writeCommaIfHasMore(indexTableKeys.length, i)).append("\n");
		}
	}
	
    private static String writeCommaIfHasMore(int count, int i)
    {
        if (hasMoreElement(count, i))
            return ",";
        else
            return "";
    }

    private static boolean hasMoreElement(int count, int i)
    {
        return i != count - 1;
    }

	public String getObjectJsonString(int objectId)
	{
		int objectPointer = objectPointerTable.get(objectId);
		reader.setPosition(objectPointer);
		int objectClassId = 0;
		try
		{
			objectClassId = reader.readInt();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		StringBuilder objectBuilder = new StringBuilder();
		try
		{
			objectBuilder.append(getObjectBuilder(objectClassId));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return objectBuilder.toString();
	}

	private String getObjectBuilder(int classId) throws IOException
	{
		GameDataClassDefinition classDefinition = classDefinitions.get(classId);
		return getFieldsBuilder(classDefinition);
	}
	
	private String getFieldsBuilder(GameDataClassDefinition classDefinition) throws IOException
    {
        StringBuilder fieldsBuilder = new StringBuilder();
        int numberOfFields = classDefinition.Fields.size();
        fieldsBuilder.append("\n{");
        for (int i = 0; i < numberOfFields; i++)
        {
            fieldsBuilder
                .append(getFieldBuilder(classDefinition.Fields.get(i)))
                .append(writeCommaIfHasMore(numberOfFields, i))
                .append("\n");
        }
        fieldsBuilder.append("}");

        return fieldsBuilder.toString();
    }
    private String getFieldBuilder(GameDataField field) throws IOException
    {
        StringBuilder fieldBuilder = new StringBuilder();

        fieldBuilder
            .append(field.fieldName)
            .append(": ")
            .append(getFieldValueBuilder(field));

        return fieldBuilder.toString();
    }
    private String getFieldValueBuilder(GameDataField field) throws IOException
    {
        StringBuilder fieldValueBuilder = new StringBuilder();

        switch (field.fieldType)
        {
            case "Vector":
                fieldValueBuilder.append("[");
                int vectorLength = reader.readInt();

                for (int i = 0; i < vectorLength; i++)
                {
                    fieldValueBuilder
                        .append(getFieldValueBuilder(field.innerField))
                        .append(writeCommaIfHasMore(vectorLength, i));
                }

                fieldValueBuilder.append("]");
                break;
            case "Int":
                fieldValueBuilder.append(reader.readInt());
                break;
            case "UInt":
                fieldValueBuilder.append(reader.readInt());
                break;
            case "I18N":
                fieldValueBuilder.append(reader.readInt());
                break;
            case "String":
                fieldValueBuilder.append(reader.readUTF());
                break;
            case "Bool":
                fieldValueBuilder.append(reader.readBoolean()); //in json bool is true/false not True/False
                break;
            case "Double":
                fieldValueBuilder.append(reader.readDouble()); //handling the "," vs "." problem of the culture specifics
                break;
            default:
                if (field.getValue() > 0) //if type is an object
                {
                    int classId = reader.readInt();
                    if (classDefinitions.containsKey(classId))
                    {
                        fieldValueBuilder.append(getObjectBuilder(classId));
                    }
                }
                else
                {
                	Network.append1(String.format("Error: invalid type( {0} ) for field {1}", field.fieldType, field.fieldName));
                }
                break;
        }
        return fieldValueBuilder.toString();
    }

	private void addArrayCloseBracket()
	{
		jsonBuilder.append("]");
	}

}
