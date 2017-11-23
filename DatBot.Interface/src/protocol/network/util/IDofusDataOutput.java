package protocol.network.util;

import java.io.DataOutput;
import java.io.IOException;

public interface IDofusDataOutput extends DataOutput {
    public void writeVarInt(int param1) throws IOException;
    public void writeVarShort(int param1) throws IOException;
    public void writeVarLong(long param1) throws IOException;
}
