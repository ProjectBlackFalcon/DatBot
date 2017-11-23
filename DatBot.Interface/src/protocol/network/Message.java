package protocol.network;

import protocol.network.util.DofusDataReader;

public class Message {
	
	private int header;
	private int length;
	private byte [] data;
	private int id;
	private int lengthBytesCount;
	public int bigPacketLength;
	
	public Message(){
		bigPacketLength = 0;
	}
	
	private int getHeader(){
		return this.header;
	}
	
	public int getId(){
		return this.id;
	}
	
	private int getLengthBytesCount(){
		return this.lengthBytesCount;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public byte [] getData(){
		return this.data;
	}
	
	
	public void build(DofusDataReader reader) throws Exception{
        if (reader.available() >= 2)
            this.header = reader.readShort();
        
		this.id = getHeader() >> 2; 
		this.lengthBytesCount = getHeader() & 0x3;
		

        if (reader.available() >= getLengthBytesCount())
        {
            if (getLengthBytesCount() < 0 || getLengthBytesCount() > 3)
                throw new Exception("Malformated Message Header, invalid bytes number to read message length (inferior to 0 or superior to 3)");

            switch(getLengthBytesCount()){
            	case 0 :
            		this.length = 0;
            		break;
            	case 1 :
            		this.length = reader.readUnsignedByte();
            		break;
            	case 2 :
            		this.length = reader.readUnsignedByte() << 8;
            		this.length |= reader.readUnsignedByte();
            		break;
            	case 3 :
            		this.length = reader.readUnsignedByte() << 16;
            		this.length |= reader.readUnsignedByte() << 8;
            		this.length |= reader.readUnsignedByte();
            		break;
            }
        }        
        if (getLength() == 0){
        	this.data = new byte[0];
        } else if (reader.available() >= getLength()) {
        	this.data = new byte[getLength()];
        	reader.readFully(data, 0, getLength());
        } else if (reader.available() < getLength()){
        	this.bigPacketLength = getLength() - reader.available();
        	this.data = reader.readBytes((int) reader.available());
        }
	}
}
