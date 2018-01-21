package utils.d2o;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import protocol.network.Network;
import protocol.network.util.DofusDataReader;

public class D2oManager {

	private DofusDataReader reader;
	private JsonUnpacker unpacker;
	private Map<Integer, Integer> objectPointerTable;
	private Map<Integer, GameDataClassDefinition> classDefinitions;
	private int contentOffset = 0;
	private int classCount;

	public D2oManager(String d2oFilePath) throws Exception
	{
		this.objectPointerTable = new HashMap<Integer, Integer>();
		this.classDefinitions = new HashMap<Integer, GameDataClassDefinition>();

		byte[] bytesFile = Files.readAllBytes(Paths.get(d2oFilePath));
		this.reader = new DofusDataReader(new ByteArrayInputStream(bytesFile));

		String headerString = this.reader.ReadAscii(3);

		if (!headerString.equals("D2O"))
		{
			this.reader.setPosition(0);
			String headers = reader.readUTF();
			int formatVersion = reader.readShort();
			int len = reader.readInt();
			reader.setPosition(reader.getPosition() + len);
			contentOffset = reader.getPosition();
			int streamStartIndex = (contentOffset + 7);
			headers = reader.ReadAscii(3);
			if (!headers.equals("D2O")) { throw new Exception("Header doesn't equal the string 'D2O' : Corrupted file"); }
		}
		
		readObjectPointerTable();
		// printObjectPointerTable();
		readClassTable();
//		printClassTable();
		// readGameDataProcessor(); //TODO: implement
		unpackObjectsAsJson();
		// writeJsonFile(true);
//		printAllObjects(); // call after unpackObjectsAsJson();
//		 searchObjectById(0); //call after unpackObjectsAsJson();
	}
	
	public List<String> returnJsonString(){
		List<String> s = new ArrayList<String>();
		for (Entry<Integer, Integer> objectPointer : this.objectPointerTable.entrySet())
		{
			s.add(unpacker.getObjectJsonString(objectPointer.getKey()));
		}
		return s;
	}
	
    public String searchObjectById(int objectId)
    {
        if (objectPointerTable.containsKey(objectId))
        {
            return unpacker.getObjectJsonString(objectId);
        }
        else
        {
           System.out.println("Object of id: " + objectId + " is not present.");
        }
		return "";
    }

	private void readObjectPointerTable() throws IOException
	{
		int tablePointer = reader.readInt();
		reader.setPosition(tablePointer + contentOffset);
		int objectPointerTableLen = reader.readInt();
		int key;
		int pointer;
		for (long i = 0; i < objectPointerTableLen; i += 4 * 2)
		{
			key = reader.readInt();
			pointer = reader.readInt();

			objectPointerTable.put(key, pointer + contentOffset);
		}
	}

	private void unpackObjectsAsJson()
	{
		unpacker = new JsonUnpacker(reader, objectPointerTable, classDefinitions);
		unpacker.Unpack();
	}

	private void readClassTable() throws IOException
	{
		classCount = reader.readInt();
		int classId;

		int j = 0;
		while (j < classCount)
		{
			classId = reader.readInt();
			readClassDefinition(classId);

			j++;
		}
	}

	private void readClassDefinition(int classId) throws IOException
	{
		String className = reader.readUTF();
		String packageName = reader.readUTF();
		GameDataClassDefinition classDefinition = new GameDataClassDefinition(packageName, className);
//		this.network.append(String.format("ClassId: %s ClassMemberName: %s ClassPkgName %s", classId, className, packageName));
		int fieldsCount = reader.readInt();
		int i = 0;
		while (i < fieldsCount)
		{
			classDefinition.AddField(reader);
			i++;
		}
		classDefinitions.put(classId, classDefinition);
	}

	private void printAllObjects() throws IOException
	{
		for (Entry<Integer, Integer> objectPointer : this.objectPointerTable.entrySet())
		{
			Network.append1(String.format("Class %s, Object Id %s:", this.classDefinitions.get(getClassId(objectPointer.getKey().intValue())), objectPointer.getKey()));
			Network.append1(unpacker.getObjectJsonString(objectPointer.getKey()));
		}
	}

	private int getClassId(int objectId) throws IOException
	{
		int objectPointer = objectPointerTable.get(objectId);
		reader.setPosition(objectPointer);
		return reader.readInt();
	}

	private void printClassTable()
	{
		if (this.classDefinitions.size() > 0)
		{
			Network.append1(String.format("Printing %s class tables.", classDefinitions.size()));
			Network.append1("");

			for (Entry<Integer, GameDataClassDefinition> classDefinition : this.classDefinitions.entrySet())
			{
				Network.append1(String.format("Class id:%s - name %s", classDefinition.getKey(), classDefinition.getValue().Name));
				Network.append1("");

				for (GameDataField field : classDefinition.getValue().Fields)
				{
					printField(getFieldString(field));
				}
			}
		}
	}

	private String getFieldString(GameDataField field)
	{
		StringBuilder fieldBuilder = new StringBuilder();
		fieldBuilder.append("public").append(" ").append(getFieldTypeString(field)).append(" ").append(getFieldNameString(field));
		return fieldBuilder.toString();
	}

	private void printField(String fieldString)
	{
		Network.append1(fieldString);
	}

	private String getFieldTypeString(GameDataField field)
	{
		if (isPrimitiveFieldType(field))
		{
			return getPrimitiveFieldTypeString(field);
		}
		else
		{
			return getCompositeFieldTypeString(field);
		}
	}

	private String getCompositeFieldTypeString(GameDataField field)
	{
		StringBuilder compositeFieldTypeBuilder = new StringBuilder();
		Network.append1(field.innerField.fieldType);
		compositeFieldTypeBuilder.append("vector").append("<").append(getFieldTypeString(field.innerField)).append(">");
		return compositeFieldTypeBuilder.toString();
	}

	private String getPrimitiveFieldTypeString(GameDataField field)
	{
		return field.getValue() > 0 ? classDefinitions.get(field.getValue()).Name : field.fieldType.toString();
	}

	private String getFieldNameString(GameDataField field)
	{
		return field.fieldName;
	}

	private static boolean isPrimitiveFieldType(GameDataField field)
	{
		return field.innerField == null;
	}

	public DofusDataReader getReader()
	{
		return reader;
	}

	public void setReader(DofusDataReader reader)
	{
		this.reader = reader;
	}

	public Map<Integer, Integer> getObjectPointerTable()
	{
		return objectPointerTable;
	}

	public void setObjectPointerTable(Map<Integer, Integer> objectPointerTable)
	{
		this.objectPointerTable = objectPointerTable;
	}

	public Map<Integer, GameDataClassDefinition> getClassDefinitions()
	{
		return classDefinitions;
	}

	public void setClassDefinitions(Map<Integer, GameDataClassDefinition> classDefinitions)
	{
		this.classDefinitions = classDefinitions;
	}

	public int getContentOffset()
	{
		return contentOffset;
	}

	public void setContentOffset(int contentOffset)
	{
		this.contentOffset = contentOffset;
	}

	public int getClassCount()
	{
		return classCount;
	}

	public void setClassCount(int classCount)
	{
		this.classCount = classCount;
	}

}
