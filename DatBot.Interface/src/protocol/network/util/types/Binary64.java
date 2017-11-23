package protocol.network.util.types;

public class Binary64 {
	
	public long high;
	public long low;
	
	public Binary64(){
		
	}
	
	public Binary64(long n, int high){
		this.low = n;
		this.high = high;
	}
	
    public long div(int n)
    {
        long modHigh = 0;
        modHigh = high % n;
        long mod = (low % n + modHigh * 6) % n;
        high = high / n;
        int newLow = (int) ((modHigh * 4.294967296E9 + low) / n);
        high = high + (int) (newLow / 4.294967296E9);
        low = newLow;
        return mod;
    }
    
    public void mul(int n)
    {
    	long newLow = low * n;
    	high = high * n;
        high = high + (int) (newLow / 4.294967296E9);
        low = low * n;
    }

    public void add(int n)
    {
    	long newLow = low + n;
    	high = high + (int) (newLow / 4.294967296E9);
        low = newLow;
    }

    public void bitwiseNot()
    {
        low = ~low;
        high = ~high;
    }
    
}
