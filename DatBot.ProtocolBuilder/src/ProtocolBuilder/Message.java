package ProtocolBuilder;

import java.util.List;

public class Message {
	
	public String name;
	public String parents;
	public long protocolId;
	public List<Field> Fields;
	public String namespace;
	public boolean useHashFunc;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParents() {
		return parents;
	}
	public void setParents(String parents) {
		this.parents = parents;
	}
	public long getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(long l) {
		this.protocolId = l;
	}
	public List<Field> getFields() {
		return Fields;
	}
	public void setFields(List<Field> fields) {
		Fields = fields;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public boolean isUseHashFunc() {
		return useHashFunc;
	}
	public void setUseHashFunc(boolean useHashFunc) {
		this.useHashFunc = useHashFunc;
	}

}
