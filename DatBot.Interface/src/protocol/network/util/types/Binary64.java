package protocol.network.util.types;

public class Binary64 {
	
	public int high;
	public int low;
	
	public Binary64(){
		
	}
	
	public Binary64(int n, int high){
		this.low = n;
		this.high = high;
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
