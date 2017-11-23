package ProtocolBuilder;

public class Field {
	
	public String name;
	public String type;
	public String writeMethod;
	public String method;
	public boolean isVector;
	public boolean isDynamicLength;
	public long length;
	public String writeLengthMethod;
	public boolean useTypeManager;
	public boolean useBBW;
	public long bbwPosition;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWriteMethod() {
		return writeMethod;
	}
	public void setWriteMethod(String writeMethod) {
		this.writeMethod = writeMethod;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public boolean isVector() {
		return isVector;
	}
	public void setIsVector(boolean isVector) {
		this.isVector = isVector;
	}
	public boolean isDynamicLength() {
		return isDynamicLength;
	}
	public void setDynamicLength(boolean isDynamicLength) {
		this.isDynamicLength = isDynamicLength;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public String getWriteLengthMethod() {
		return writeLengthMethod;
	}
	public void setWriteLengthMethod(String writeLengthMethod) {
		this.writeLengthMethod = writeLengthMethod;
	}
	public boolean isUseTypeManager() {
		return useTypeManager;
	}
	public void setUseTypeManager(boolean useTypeManager) {
		this.useTypeManager = useTypeManager;
	}
	public boolean isUseBBW() {
		return useBBW;
	}
	public void setUseBBW(boolean useBBW) {
		this.useBBW = useBBW;
	}
	public long getBbwPosition() {
		return bbwPosition;
	}
	public void setBbwPosition(long bbwPosition) {
		this.bbwPosition = bbwPosition;
	}
	
	

}
