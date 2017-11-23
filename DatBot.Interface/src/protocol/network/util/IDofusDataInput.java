package protocol.network.util;

import java.io.DataInput;
import java.io.IOException;

public interface IDofusDataInput extends DataInput {
    public int readVarInt() throws IOException;
    public int readVarUhInt() throws IOException;
    public int readVarShort() throws IOException;
    public int readVarUhShort() throws IOException;
    public long readVarLong() throws IOException;
    public long readVarUhLong() throws IOException;
}
