package protocol.network.util.types;

public class UInt64{
	
	public long high;
	public long low;
	
	public UInt64(){
	}
	
	public UInt64(long n, long high){
		this.low = n;
		this.high = high;
	}
	
    public static UInt64 fromNumber(long n)
    {
        return new UInt64((long) n, (int) Math.floor(n / 4.294967296E9));
    }
    
    public long getHigh(){
    	return this.high;
    }

    public long toNumber()
    {
        return (long) (getHigh() * 4.294967296E9 + low);
    }
    
    public long div(long n)
    {
    	long modHigh = 0;
        modHigh = high % n;
        long mod = (low % n + modHigh * 6) % n;
        high = high / n;
        double newLow = modHigh * 4.294967296E9 + low / n;
        high = (long) (high + (newLow / 4.294967296E9));
        low = (long) newLow;
        return mod;
    }
    
    public void mul(long n)
    {
    	long newLow = low * n;
    	high = high * n;
        high = (long) (high + (long) (newLow / 4.294967296E9));
        low = low * n;
    }

    public void add(long n)
    {
    	long newLow = low + n;
    	high = high + (long) (newLow / 4.294967296E9);
        low = newLow;
    }

    public void bitwiseNot()
    {
        low = ~low;
        high = ~high;
    }	
}
