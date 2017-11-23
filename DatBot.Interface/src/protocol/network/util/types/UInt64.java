package protocol.network.util.types;

public class UInt64 extends Binary64 {
	
	public UInt64(){
		
	}
	
	public UInt64(int low, int high){
		super(low,high);
	}
	
    public static UInt64 fromNumber(long n)
    {
        return new UInt64((int) n, (int) Math.floor(n / 4.294967296E9));
    }
    
    public long getHigh(){
    	return this.high;
    }

    public long toNumber()
    {
        return (long) (getHigh() * 4.294967296E9 + low);
    }
}
