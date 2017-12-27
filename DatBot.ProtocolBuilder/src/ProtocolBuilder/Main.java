package ProtocolBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import Utilitaires.JSON;

public class Main {

	private static String pathStr = "";
	private static String pathExe = "";
	private static String pathInvoker = "E:\\Ankama\\Dofus\\app\\DofusInvoker.swf";

	public static void main(String[] args) throws IOException, InterruptedException
	{
		// Redirection does not work
		String s = Paths.get("").toAbsolutePath().toString();
		int newPath = s.indexOf("DatBot");
		s = s.substring(0, newPath + 6);

		pathStr = s + "\\DatBot.ProtocolBuilder\\Utils";
		pathExe = s + "\\DatBot.ProtocolBuilder\\Utils\\d2json.exe";

		System.out.println("Processing...");
		File output = new File(pathStr + "\\d2jsonOutput.json");
		Process p = new ProcessBuilder(pathExe, pathInvoker).redirectOutput(output).start();
		p.waitFor();
		JSON json = new JSON(pathStr + "\\d2jsonOutput.json");

		for (int i = 0; i < json.Messages.size(); i++)
		{
			List<String> data = new ArrayList<String>();
			for (int k = 0; k < 500; k++)
			{
				data.add("");
			}

			data.set(2, "import java.io.IOException;");
			data.set(3, "import java.util.ArrayList;");
			data.set(4, "import java.util.List;");
			data.set(5, "import protocol.utils.ProtocolTypeManager;");
			data.set(6, "import protocol.network.util.types.BooleanByteWrapper;");
			data.set(8, "import protocol.network.NetworkMessage;");
			data.set(9, "import protocol.network.util.DofusDataReader;");
			data.set(10, "import protocol.network.util.DofusDataWriter;");
			data.set(11, "import protocol.network.Network;");

			Message msg = json.Messages.get(i);
			// add package
			String pckage = "package " + msg.getNamespace() + ";";
			pckage = pckage.replace("com.ankamagames.dofus", "protocol");
			data.set(0, pckage);

			List<String> impString = new ArrayList<String>();
			List<String> importString = new ArrayList<String>();
			impString.add(pckage.replace("package", "import"));
			importString.add(pckage.replace("package", "import"));

			// add class
			String cls;
			boolean extend = false;
			if (msg.getParents().equals(""))
			{
				cls = "public class " + msg.getName() + " extends NetworkMessage {";
				data.set(12, "import protocol.network.NetworkMessage;");
				extend = true;
			}
			else
			{
				cls = "public class " + msg.getName() + " extends " + msg.getParents() + " {";
				for (Message message : json.Messages)
				{
					if (message.getName().equals(msg.getParents()))
					{
						String imp = "import " + message.getNamespace() + "." + msg.getParents() + ";";
						imp = imp.replace("com.ankamagames.dofus", "protocol");
						data.set(12, imp);
					}
				}
				for (Message message : json.Types)
				{
					if (message.getName().equals(msg.getParents()))
					{
						String imp = "import " + message.getNamespace() + "." + msg.getParents() + ";";
						imp = imp.replace("com.ankamagames.dofus", "protocol");
						data.set(12, imp);
					}
				}
			}

			int indexClass = 14;
			int nbImport = 0;
			if (msg.getFields() != null)
			{
				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					if (field.getWriteMethod().equals("writeFloat"))
					{
						field.setWriteMethod("writeDouble");
					}
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
					}
					else if (field.getType().equals("string"))
					{
					}
					else if (field.getType().equals("bool"))
					{
					}
					else
					{
						for (Message message : json.Messages)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!impString.contains(imp))
								{
									impString.add(imp);
									nbImport++;
								}
							}
						}
						for (Message message : json.Types)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!impString.contains(imp))
								{
									impString.add(imp);
									nbImport++;
								}
							}
						}
					}
				}
			}

			indexClass += nbImport;

			int indexImport = 13;
			data.set(indexClass++, "@SuppressWarnings(\"unused\")");
			data.set(indexClass++, cls);

			// add protocolId
			String id = "\tpublic static final int ProtocolId = " + msg.getProtocolId() + ";";
			data.set(indexClass++, id);

			int index = indexClass + 1;
			if (msg.getFields() != null)
			{
				String varCons = "";
				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					String var = "private ";
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
						if (field.isVector)
						{
							if (field.getType().contains("int"))
							{
								if (field.getType().equals("int64"))
								{
									var += "List<Long>" + " " + field.getName();
									varCons += "List<Long>" + " " + field.getName();
								}
								else
								{
									var += "List<Integer>" + " " + field.getName();
									varCons += "List<Integer>" + " " + field.getName();
								}
							}

						}
						else
						{
							if (field.getType().equals("int64"))
							{
								var += "long" + " " + field.getName();
								varCons += "long" + " " + field.getName();
							}
							else
							{
								var += "int" + " " + field.getName();
								varCons += "int" + " " + field.getName();
							}
						}
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
						if (field.isVector)
						{
							var += "List<Double>" + " " + field.getName();
							varCons += "List<Double>" + " " + field.getName();
						}
						else
						{
							var += "double" + " " + field.getName();
							varCons += "double" + " " + field.getName();
						}

					}
					else if (field.getType().equals("string"))
					{
						if (field.isVector)
						{
							var += "List<String>" + " " + field.getName();
							varCons += "List<String>" + " " + field.getName();
						}
						else
						{
							var += "String" + " " + field.getName();
							varCons += "String" + " " + field.getName();
						}

					}
					else if (field.getType().equals("bool"))
					{
						if (field.isVector)
						{
							var += "List<Boolean>" + " " + field.getName();
							varCons += "List<Boolean>" + " " + field.getName();
						}
						else
						{
							var += "boolean" + " " + field.getName();
							varCons += "boolean" + " " + field.getName();
						}
					}
					else
					{
						if (field.isVector)
						{
							var += "List<" + field.getType() + ">" + " " + field.getName();
							varCons += "List<" + field.getType() + ">" + " " + field.getName();
						}
						else
						{
							var += field.getType() + " " + field.getName();
							varCons += field.getType() + " " + field.getName();
						}
						for (Message message : json.Messages)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!importString.contains(imp))
								{
									importString.add(imp);
									data.set(indexImport++, imp);
								}

							}
						}
						for (Message message : json.Types)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!importString.contains(imp))
								{
									importString.add(imp);
									data.set(indexImport++, imp);
								}
							}
						}
					}
					var += ";";
					if (j != msg.getFields().size() - 1) varCons += ", ";
					data.set(index, "\t" + var);
					index++;
				}
				
				index ++;


				// Getter + Setter

				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					String varGetter = "public ";
					String varSetter = "public ";
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
						if (field.isVector)
						{
							if (field.getType().contains("int")) 
							{
								if (field.getType().equals("int64"))
								{
									varGetter += "List<Long>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }" ;
									varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Long> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
								}
								else
								{
									varGetter += "List<Integer>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
									varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Integer> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
								}
							}

						}
						else
						{
							if (field.getType().equals("int64"))
							{
								varGetter += "long" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
								varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(long " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
							}
							else
							{
								varGetter += "int" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
								varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(int " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
							}
						}
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
						if (field.isVector)
						{
							varGetter += "List<Double>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Double> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "double" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(double " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}

					}
					else if (field.getType().equals("string"))
					{
						if (field.isVector)
						{
							varGetter += "List<String>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<String> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "String" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(String " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}

					}
					else if (field.getType().equals("bool"))
					{
						if (field.isVector)
						{
							varGetter += "List<Boolean>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Boolean> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "boolean" + " is" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(boolean " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
					}
					else
					{
						if (field.isVector)
						{
							varGetter += "List<" + field.getType() + ">" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<" + field.getType() + "> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += field.getType() + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(" + field.getType() + " " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
					}
					varGetter += ";";
					data.set(index, "\t" + varGetter);
					index++;
					data.set(index, "\t" + varSetter);
					index++;
				}

				// add constructors
				index++;
				data.set(index++, "\tpublic " + msg.getName() + "(){");
				data.set(index++, "\t}");

				index++;
				data.set(index++, "\tpublic " + msg.getName() + "(" + varCons + "){");
				for (Field f : msg.getFields())
				{
					data.set(index++, "\t\tthis." + f.getName() + " = " + f.getName() + ";");
				}
				data.set(index++, "\t}");

				// add Serialise + deserialise
				index++;
				data.set(index++, "\t@Override");
				data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
				data.set(index++, "\t\ttry {");

				if (!extend)
				{
					data.set(index++, "\t\t\tsuper.Serialize(writer);");
				}

				int indexBbw = 0;
				boolean bbw = false;
				for (Field f : msg.getFields())
				{
					if (f.isUseBBW())
					{
						bbw = true;
						if (indexBbw == 0)
						{
							data.set(index++, "\t\t\tbyte flag = 0;");
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(0, flag, " + f.getName() + ");");
						}
						else if (indexBbw < 8)
						{
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(" + indexBbw + ", flag, " + f.getName() + ");");
						}
						else
						{
							indexBbw = 0;
							data.set(index++, "\t\t\twriter.writeByte(flag);");
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(" + indexBbw + ", flag, " + f.getName() + ");");
						}
						indexBbw++;
					}
				}

				if (bbw) data.set(index++, "\t\t\twriter.writeByte(flag);");

				int loc = 2;
				for (Field f : msg.getFields())
				{
					if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ");");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\t" + f.getName() + ".Serialize(writer);");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(" + f.getType() + ".ProtocolId);");
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\twriter.writeShort(" + f.getType() + ".ProtocolId" + ");");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals("") && !f.getWriteLengthMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter." + f.getWriteLengthMethod() + "(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ".get(_loc" + loc + "_));");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 5)
					{
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ".get(_loc" + loc + "_));");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 2)
					{
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && !f.isVector && f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
					}

					else
					{
						System.out.println(f.getName());
						System.out.println("isDynamicLength : " + f.isDynamicLength);
						System.out.println("isVector : " + f.isVector);
						System.out.println("useBBW : " + f.useBBW);
						System.out.println("useTypeManager : " + f.useTypeManager);
						System.out.println("getWriteMethod : " + f.getWriteMethod().equals(""));
						System.out.println("----------------");
					}

				}
				data.set(index++, "\t\t} catch (Exception e){");
				data.set(index++, "\t\t\te.printStackTrace();");
				data.set(index++, "\t\t}");
				data.set(index++, "\t}");
				data.set(index++, "");
				data.set(index++, "\t@Override");
				data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
				data.set(index++, "\t\ttry {");

				if (!extend)
				{
					data.set(index++, "\t\t\tsuper.Deserialize(reader);");
				}

				int indexBbw1 = 0;
				boolean bbw1 = false;
				for (Field f : msg.getFields())
				{
					if (f.isUseBBW())
					{
						bbw1 = true;
						if (indexBbw1 == 0)
						{
							data.set(index++, "\t\t\tbyte flag;");
							data.set(index++, "\t\t\tflag = (byte) reader.readUnsignedByte();");
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) 0);");
						}
						else if (indexBbw1 < 8)
						{
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) " + indexBbw1 + ");");
						}
						else
						{
							indexBbw1 = 0;
							data.set(index++, "\t\t\tflag = (byte) reader.readUnsignedByte();");
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) " + indexBbw1 + ");");
						}
						indexBbw1++;
					}
				}

				int loc1 = 2;
				int loc2 = 3;
				int loc3 = 15;
				for (Field f : msg.getFields())
				{
					String type = "int";
					if (f.getType().contains("int"))
					{
						if (f.getType().equals("int64"))
							type = "long";
						else
							type = "int";
					}
					else if (f.getType().contains("float"))
						type = "double";
					else if (f.getType().contains("string"))
						type = "String";
					else if (f.getType().contains("bool"))
						type = "boolean";
					else
						type = f.getType();

					String typeList = "Integer";
					if (f.getType().contains("int"))
					{
						if (f.getType().equals("int64"))
							typeList = "Long";
						else
							typeList = "Integer";
					}
					else if (f.getType().contains("float"))
						typeList = "Double";
					else if (f.getType().contains("string"))
						typeList = "String";
					else if (f.getType().contains("bool"))
						typeList = "Boolean";
					else
						typeList = f.getType();

					if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = reader." + getReadMethod(f.getWriteMethod()) + ";");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = new " + f.getType() + "();");
						data.set(index++, "\t\t\tthis." + f.getName() + ".Deserialize(reader);");

					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = (" + f.getType() + ") ProtocolTypeManager.getInstance(reader.readShort());");
						data.set(index++, "\t\t\tthis." + f.getName() + ".Deserialize(reader);");
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader.readShort();");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + f.getType() + " _loc" + loc3 + "_ = (" + f.getType() + ") ProtocolTypeManager.getInstance(reader.readShort());");
						data.set(index++, "\t\t\t\t_loc" + loc3 + "_.Deserialize(reader);");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader.readShort();");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + f.getType() + " _loc" + loc3 + "_ = new " + f.getType() + "();");
						data.set(index++, "\t\t\t\t_loc" + loc3 + "_.Deserialize(reader);");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals("") && !f.getWriteLengthMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader." + getReadMethod(f.getWriteLengthMethod()) + ";");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + type + " _loc" + loc3 + "_ = reader." + getReadMethod(f.getWriteMethod()) + ";");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 5)
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc1 + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(reader." + getReadMethod(f.getWriteMethod()) + ");");
						data.set(index++, "\t\t\t\t_loc" + loc1 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 2)
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc1 + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(new " + f.getType() + "());");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get( _loc" + loc1 + "_).Deserialize(reader);");
						data.set(index++, "\t\t\t\t_loc" + loc1 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1++;
					}

					else if (!f.isDynamicLength && !f.isVector && f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
					}

					else
					{
						System.out.println(f.getName());
						System.out.println("isDynamicLength : " + f.isDynamicLength);
						System.out.println("isVector : " + f.isVector);
						System.out.println("useBBW : " + f.useBBW);
						System.out.println("useTypeManager : " + f.useTypeManager);
						System.out.println("getWriteMethod : " + f.getWriteMethod().equals(""));
						System.out.println("----------------");
					}

				}

				data.set(index++, "\t\t} catch (Exception e){");
				data.set(index++, "\t\t\te.printStackTrace();");
				data.set(index++, "\t\t}");
				data.set(index++, "\t}");
				index++;
			}
			else
			{
				if (extend)
				{
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
					data.set(index++, "\t}");
					data.set(index++, "");
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
					data.set(index++, "\t}");
				}
				else
				{
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
					data.set(index++, "\t\tsuper.Serialize(writer);");
					data.set(index++, "\t}");
					data.set(index++, "");
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
					data.set(index++, "\t\tsuper.Deserialize(reader);");
					data.set(index++, "\t}");
				}
			}
			data.set(index, "}");
			if (index < data.size())
			{
				for (int l = index + 1; l < 500; l++)
				{
					data.remove(index + 1);
				}
			}
			createBuilderFiles(msg.getNamespace(), msg.getName(), data);
		}

		for (int i = 0; i < json.Types.size(); i++)
		{
			List<String> data = new ArrayList<String>();
			for (int k = 0; k < 1000; k++)
			{
				data.add("");
			}

			data.set(2, "import java.io.IOException;");
			data.set(3, "import java.util.ArrayList;");
			data.set(4, "import java.util.List;");
			data.set(5, "import protocol.utils.ProtocolTypeManager;");
			data.set(6, "import protocol.network.util.types.BooleanByteWrapper;");
			data.set(8, "import protocol.network.NetworkMessage;");
			data.set(9, "import protocol.network.util.DofusDataReader;");
			data.set(10, "import protocol.network.util.DofusDataWriter;");
			data.set(11, "import protocol.network.Network;");

			Message msg = json.Types.get(i);

			if (msg.getName().equals("VersionExtended") || msg.getName().equals("AbstractCharacterInformation"))
			{
				continue;
			}

			// add package
			String pckage = "package " + msg.getNamespace() + ";";
			pckage = pckage.replace("com.ankamagames.dofus", "protocol");
			data.set(0, pckage);

			List<String> impString = new ArrayList<String>();
			List<String> importString = new ArrayList<String>();
			impString.add(pckage.replace("package", "import"));
			importString.add(pckage.replace("package", "import"));

			// add class
			String cls;
			boolean extend = false;
			if (msg.getParents().equals(""))
			{
				cls = "public class " + msg.getName() + " extends NetworkMessage {";
				data.set(12, "import protocol.network.NetworkMessage;");
				extend = true;
			}
			else
			{
				cls = "public class " + msg.getName() + " extends " + msg.getParents() + " {";
				for (Message message : json.Messages)
				{
					if (message.getName().equals(msg.getParents()))
					{
						String imp = "import " + message.getNamespace() + "." + msg.getParents() + ";";
						imp = imp.replace("com.ankamagames.dofus", "protocol");
						data.set(12, imp);
					}
				}
				for (Message message : json.Types)
				{
					if (message.getName().equals(msg.getParents()))
					{
						String imp = "import " + message.getNamespace() + "." + msg.getParents() + ";";
						imp = imp.replace("com.ankamagames.dofus", "protocol");
						data.set(12, imp);
					}
				}
			}

			int indexClass = 14;
			int nbImport = 0;
			if (msg.getFields() != null)
			{
				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					if (field.getWriteMethod().equals("writeFloat"))
					{
						field.setWriteMethod("writeDouble");
					}
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
					}
					else if (field.getType().equals("string"))
					{
					}
					else if (field.getType().equals("bool"))
					{
					}
					else
					{
						for (Message message : json.Messages)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!impString.contains(imp))
								{
									impString.add(imp);
									nbImport++;
								}
							}
						}
						for (Message message : json.Types)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!impString.contains(imp))
								{
									impString.add(imp);
									nbImport++;
								}
							}
						}
					}
				}
			}

			indexClass += nbImport;

			int indexImport = 13;
			data.set(indexClass++, "@SuppressWarnings(\"unused\")");
			data.set(indexClass++, cls);

			// add protocolId
			String id = "\tpublic static final int ProtocolId = " + msg.getProtocolId() + ";";
			data.set(indexClass++, id);

			int index = indexClass + 1;
			if (msg.getFields() != null)
			{
				String varCons = "";
				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					String var = "private ";
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
						if (field.isVector)
						{
							if (field.getType().contains("int"))
							{
								if (field.getType().equals("int64"))
								{
									var += "List<Long>" + " " + field.getName();
									varCons += "List<Long>" + " " + field.getName();
								}
								else
								{
									var += "List<Integer>" + " " + field.getName();
									varCons += "List<Integer>" + " " + field.getName();
								}
							}

						}
						else
						{
							if (field.getType().equals("int64"))
							{
								var += "long" + " " + field.getName();
								varCons += "long" + " " + field.getName();
							}
							else
							{
								var += "int" + " " + field.getName();
								varCons += "int" + " " + field.getName();
							}
						}
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
						if (field.isVector)
						{
							var += "List<Double>" + " " + field.getName();
							varCons += "List<Double>" + " " + field.getName();
						}
						else
						{
							var += "double" + " " + field.getName();
							varCons += "double" + " " + field.getName();
						}

					}
					else if (field.getType().equals("string"))
					{
						if (field.isVector)
						{
							var += "List<String>" + " " + field.getName();
							varCons += "List<String>" + " " + field.getName();
						}
						else
						{
							var += "String" + " " + field.getName();
							varCons += "String" + " " + field.getName();
						}

					}
					else if (field.getType().equals("bool"))
					{
						if (field.isVector)
						{
							var += "List<Boolean>" + " " + field.getName();
							varCons += "List<Boolean>" + " " + field.getName();
						}
						else
						{
							var += "boolean" + " " + field.getName();
							varCons += "boolean" + " " + field.getName();
						}
					}
					else
					{
						if (field.isVector)
						{
							var += "List<" + field.getType() + ">" + " " + field.getName();
							varCons += "List<" + field.getType() + ">" + " " + field.getName();
						}
						else
						{
							var += field.getType() + " " + field.getName();
							varCons += field.getType() + " " + field.getName();
						}
						for (Message message : json.Messages)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!importString.contains(imp))
								{
									importString.add(imp);
									data.set(indexImport++, imp);
								}

							}
						}
						for (Message message : json.Types)
						{
							if (message.getName().equals(field.getType()))
							{
								String imp = "import " + message.getNamespace() + "." + field.getType() + ";";
								imp = imp.replace("com.ankamagames.dofus", "protocol");
								if (!importString.contains(imp))
								{
									importString.add(imp);
									data.set(indexImport++, imp);
								}
							}
						}
					}
					var += ";";
					if (j != msg.getFields().size() - 1) varCons += ", ";
					data.set(index, "\t" + var);
					index++;
				}
				
				index ++;
				// add getter + setter
				
				for (int j = 0; j < msg.getFields().size(); j++)
				{
					Field field = msg.getFields().get(j);
					String varGetter = "public ";
					String varSetter = "public ";
					if (field.getType().equals("int8") || field.getType().equals("int16") || field.getType().equals("int32") || field.getType().equals("int64") || field.getType().equals("uint8") || field.getType().equals("uint16") || field.getType().equals("uint32") || field.getType().equals("uint64"))
					{
						if (field.isVector)
						{
							if (field.getType().contains("int")) 
							{
								if (field.getType().equals("int64"))
								{
									varGetter += "List<Long>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }" ;
									varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Long> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
								}
								else
								{
									varGetter += "List<Integer>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
									varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Integer> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
								}
							}

						}
						else
						{
							if (field.getType().equals("int64"))
							{
								varGetter += "long" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
								varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(long " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
							}
							else
							{
								varGetter += "int" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
								varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(int " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
							}
						}
					}
					else if (field.getType().equals("float8") || field.getType().equals("float16") || field.getType().equals("float32") || field.getType().equals("float64"))
					{
						if (field.isVector)
						{
							varGetter += "List<Double>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Double> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "double" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(double " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}

					}
					else if (field.getType().equals("string"))
					{
						if (field.isVector)
						{
							varGetter += "List<String>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<String> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "String" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(String " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}

					}
					else if (field.getType().equals("bool"))
					{
						if (field.isVector)
						{
							varGetter += "List<Boolean>" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<Boolean> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += "boolean" + " is" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(boolean " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
					}
					else
					{
						if (field.isVector)
						{
							varGetter += "List<" + field.getType() + ">" + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(List<" + field.getType() + "> " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
						else
						{
							varGetter += field.getType() + " get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "() { return this." + field.getName() + "; }";
							varSetter += "void" + " set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(" + field.getType() + " " + field.getName() + ") { this." + field.getName() + " = " + field.getName() + "; };";
						}
					}
					varGetter += ";";
					data.set(index, "\t" + varGetter);
					index++;
					data.set(index, "\t" + varSetter);
					index++;
				}

				// add constructors
				index++;
				data.set(index++, "\tpublic " + msg.getName() + "(){");
				data.set(index++, "\t}");

				index++;
				data.set(index++, "\tpublic " + msg.getName() + "(" + varCons + "){");
				for (Field f : msg.getFields())
				{
					data.set(index++, "\t\tthis." + f.getName() + " = " + f.getName() + ";");
				}
				data.set(index++, "\t}");

				// add Serialise + deserialise
				index++;
				data.set(index++, "\t@Override");
				data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
				data.set(index++, "\t\ttry {");

				if (!extend)
				{
					data.set(index++, "\t\t\tsuper.Serialize(writer);");
				}

				int indexBbw = 0;
				boolean bbw = false;
				for (Field f : msg.getFields())
				{
					if (f.isUseBBW())
					{
						bbw = true;
						if (indexBbw == 0)
						{
							data.set(index++, "\t\t\tbyte flag = 0;");
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(0, flag, " + f.getName() + ");");
						}
						else if (indexBbw < 8)
						{
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(" + indexBbw + ", flag, " + f.getName() + ");");
						}
						else
						{
							indexBbw = 0;
							data.set(index++, "\t\t\twriter.writeByte(flag);");
							data.set(index++, "\t\t\tflag = BooleanByteWrapper.SetFlag(" + indexBbw + ", flag, " + f.getName() + ");");
						}
						indexBbw++;
					}
				}

				if (bbw) data.set(index++, "\t\t\twriter.writeByte(flag);");

				int loc = 2;
				for (Field f : msg.getFields())
				{
					if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ");");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\t" + f.getName() + ".Serialize(writer);");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(" + f.getType() + ".ProtocolId);");
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\twriter.writeShort(" + f.getType() + ".ProtocolId" + ");");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter.writeShort(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals("") && !f.getWriteLengthMethod().equals(""))
					{
						data.set(index++, "\t\t\twriter." + f.getWriteLengthMethod() + "(this." + f.getName() + ".size());");
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < this." + f.getName() + ".size()){");
						data.set(index++, "\t\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ".get(_loc" + loc + "_));");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 5)
					{
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\twriter." + f.getWriteMethod() + "(this." + f.getName() + ".get(_loc" + loc + "_));");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 2)
					{
						data.set(index++, "\t\t\tint _loc" + loc + "_ = 0;");
						data.set(index++, "\t\t\twhile( _loc" + loc + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get(_loc" + loc + "_).Serialize(writer);");
						data.set(index++, "\t\t\t\t_loc" + loc + "_++;");
						data.set(index++, "\t\t\t}");
						loc++;
					}

					else if (!f.isDynamicLength && !f.isVector && f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
					}

					else
					{
						System.out.println(f.getName());
						System.out.println("isDynamicLength : " + f.isDynamicLength);
						System.out.println("isVector : " + f.isVector);
						System.out.println("useBBW : " + f.useBBW);
						System.out.println("useTypeManager : " + f.useTypeManager);
						System.out.println("getWriteMethod : " + f.getWriteMethod().equals(""));
						System.out.println("----------------");
					}

				}
				data.set(index++, "\t\t} catch (Exception e){");
				data.set(index++, "\t\t\te.printStackTrace();");
				data.set(index++, "\t\t}");
				data.set(index++, "\t}");
				data.set(index++, "");
				data.set(index++, "\t@Override");
				data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
				data.set(index++, "\t\ttry {");

				if (!extend)
				{
					data.set(index++, "\t\t\tsuper.Deserialize(reader);");
				}

				int indexBbw1 = 0;
				boolean bbw1 = false;
				for (Field f : msg.getFields())
				{
					if (f.isUseBBW())
					{
						bbw1 = true;
						if (indexBbw1 == 0)
						{
							data.set(index++, "\t\t\tbyte flag;");
							data.set(index++, "\t\t\tflag = (byte) reader.readUnsignedByte();");
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) 0);");
						}
						else if (indexBbw1 < 8)
						{
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) " + indexBbw1 + ");");
						}
						else
						{
							indexBbw1 = 0;
							data.set(index++, "\t\t\tflag = (byte) reader.readUnsignedByte();");
							data.set(index++, "\t\t\tthis." + f.getName() + " = BooleanByteWrapper.GetFlag(flag, (byte) " + indexBbw1 + ");");
						}
						indexBbw1++;
					}
				}

				int loc1 = 2;
				int loc2 = 3;
				int loc3 = 15;
				for (Field f : msg.getFields())
				{

					String type = "int";
					if (f.getType().contains("int"))
					{
						if (f.getType().equals("int64"))
							type = "long";
						else
							type = "int";
					}
					else if (f.getType().contains("float"))
						type = "double";
					else if (f.getType().contains("string"))
						type = "String";
					else if (f.getType().contains("bool"))
						type = "boolean";
					else
						type = f.getType();

					String typeList = "Integer";
					if (f.getType().contains("int"))
					{
						if (f.getType().equals("int64"))
							typeList = "Long";
						else
							typeList = "Integer";
					}
					else if (f.getType().contains("float"))
						typeList = "Double";
					else if (f.getType().contains("string"))
						typeList = "String";
					else if (f.getType().contains("bool"))
						typeList = "Boolean";
					else
						typeList = f.getType();

					if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = reader." + getReadMethod(f.getWriteMethod()) + ";");
					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = new " + f.getType() + "();");
						data.set(index++, "\t\t\tthis." + f.getName() + ".Deserialize(reader);");

					}

					else if (!f.isDynamicLength && !f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tthis." + f.getName() + " = (" + f.getType() + ") ProtocolTypeManager.getInstance(reader.readShort());");
						data.set(index++, "\t\t\tthis." + f.getName() + ".Deserialize(reader);");
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader.readShort();");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + f.getType() + " _loc" + loc3 + "_ = (" + f.getType() + ") ProtocolTypeManager.getInstance(reader.readShort());");
						data.set(index++, "\t\t\t\t_loc" + loc3 + "_.Deserialize(reader);");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader.readShort();");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + f.getType() + " _loc" + loc3 + "_ = new " + f.getType() + "();");
						data.set(index++, "\t\t\t\t_loc" + loc3 + "_.Deserialize(reader);");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && !f.getWriteMethod().equals("") && !f.getWriteLengthMethod().equals(""))
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = reader." + getReadMethod(f.getWriteLengthMethod()) + ";");
						data.set(index++, "\t\t\tint _loc" + loc2 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc2 + "_ <  _loc" + loc1 + "_){");
						data.set(index++, "\t\t\t\t" + type + " _loc" + loc3 + "_ = reader." + getReadMethod(f.getWriteMethod()) + ";");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(_loc" + loc3 + "_);");
						data.set(index++, "\t\t\t\t_loc" + loc2 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1 += 2;
						loc2 += 2;
						loc3++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 5)
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc1 + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(reader." + getReadMethod(f.getWriteMethod()) + ");");
						data.set(index++, "\t\t\t\t_loc" + loc1 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1++;
					}

					else if (!f.isDynamicLength && f.isVector && !f.useBBW && !f.useTypeManager && f.getLength() == 2)
					{
						data.set(index++, "\t\t\tint _loc" + loc1 + "_  = 0;");
						data.set(index++, "\t\t\tthis." + f.getName() + " = new ArrayList<" + typeList + ">();");
						data.set(index++, "\t\t\twhile( _loc" + loc1 + "_ < " + f.getLength() + "){");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".add(new " + f.getType() + "());");
						data.set(index++, "\t\t\t\tthis." + f.getName() + ".get( _loc" + loc1 + "_).Deserialize(reader);");
						data.set(index++, "\t\t\t\t_loc" + loc1 + "_++;");
						data.set(index++, "\t\t\t}");
						loc1++;
					}

					else if (!f.isDynamicLength && !f.isVector && f.useBBW && !f.useTypeManager && f.getWriteMethod().equals(""))
					{
					}

					else
					{
						System.out.println(f.getName());
						System.out.println("isDynamicLength : " + f.isDynamicLength);
						System.out.println("isVector : " + f.isVector);
						System.out.println("useBBW : " + f.useBBW);
						System.out.println("useTypeManager : " + f.useTypeManager);
						System.out.println("getWriteMethod : " + f.getWriteMethod().equals(""));
						System.out.println("----------------");
					}

				}

				data.set(index++, "\t\t} catch (Exception e){");
				data.set(index++, "\t\t\te.printStackTrace();");
				data.set(index++, "\t\t}");
				data.set(index++, "\t}");
				index++;
			}
			else
			{
				if (extend)
				{
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
					data.set(index++, "\t}");
					data.set(index++, "");
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
					data.set(index++, "\t}");
				}
				else
				{
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Serialize(DofusDataWriter writer) {");
					data.set(index++, "\t\tsuper.Serialize(writer);");
					data.set(index++, "\t}");
					data.set(index++, "");
					data.set(index++, "\t@Override");
					data.set(index++, "\tpublic void Deserialize(DofusDataReader reader) {");
					data.set(index++, "\t\tsuper.Deserialize(reader);");
					data.set(index++, "\t}");
				}
			}
			data.set(index, "}");
			if (index < data.size())
			{
				for (int l = index + 1; l < 1000; l++)
				{
					data.remove(index + 1);
				}
			}
			createBuilderFiles(msg.getNamespace(), msg.getName(), data);
		}
		System.out.println("done");
	}

	private static void createBuilderFiles(String s, String s2, List<String> data) throws IOException
	{
		String[] newString = s.substring(30).split("\\.");
		String pathTemp = pathStr;
		for (int i = 0; i < newString.length; i++)
		{
			pathTemp += "\\" + newString[i];
			if (!Files.exists(Paths.get(pathTemp), LinkOption.NOFOLLOW_LINKS))
			{
				new File(pathTemp).mkdirs();
			}
		}
		String pathFile = pathStr;
		for (String string : newString)
		{
			pathFile += "\\" + string;
		}
		pathFile += "\\" + s2 + ".java";
		File f = new File(pathFile);
		f.delete();
		f.createNewFile();
		Files.write(Paths.get(pathFile), data, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
	}

	private static String getReadMethod(String s)
	{
		String var = "";
		switch (s)
		{
			case "writeUTF":
				var = "readUTF()";
			break;
			case "writeByte":
				var = "readByte()";
			break;
			case "writeVarShort":
				var = "readVarShort()";
			break;
			case "writeDouble":
				var = "readDouble()";
			break;
			case "writeShort":
				var = "readShort()";
			break;
			case "writeInt":
				var = "readInt()";
			break;
			case "writeVarInt":
				var = "readVarInt()";
			break;
			case "writeVarLong":
				var = "readVarLong()";
			break;
			case "writeBoolean":
				var = "readBoolean()";
			break;
			case "writeFloat":
				var = "readDouble()";
			break;
			case "writeUnsignedInt":
				var = "readUnsignedByte()";
			break;
		}
		return var;
	}
}
