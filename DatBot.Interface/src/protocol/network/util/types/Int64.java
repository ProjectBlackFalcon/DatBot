package protocol.network.util.types;

public class Int64{
	
	public int high;
	public int low;
	
	public Int64(){
	}
	
	public Int64(int n, int high){
		this.low = n;
		this.high = high;
	}
	
    public static Int64 fromNumber(long n)
    {
        return new Int64((int) n, (int) Math.floor(n / 4.294967296E9));
    }
    
    public int getHigh(){
    	return this.high;
    }

    public long toNumber()
    {
        return (long) (getHigh() * 4.294967296E9 + low);
    }
    
    public int div(int n)
    {
    	int modHigh = 0;
        modHigh = high % n;
        int mod = (low % n + modHigh * 6) % n;
        high = high / n;
        double newLow = modHigh * 4.294967296E9 + low / n;
        high = (int) (high + (newLow / 4.294967296E9));
        low = (int) newLow;
        return mod;
    }
    
    public void mul(int n)
    {
    	int newLow = low * n;
    	high = high * n;
        high = (int) (high + (int) (newLow / 4.294967296E9));
        low = low * n;
    }

    public void add(int n)
    {
    	int newLow = low + n;
    	high = high + (int) (newLow / 4.294967296E9);
        low = newLow;
    }

    public void bitwiseNot()
    {
        low = ~low;
        high = ~high;
    }	
}
