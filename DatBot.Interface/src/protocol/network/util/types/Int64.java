package protocol.network.util.types;

public class Int64 extends Binary64{
	
	public Int64(){
		
	}
	
	public Int64(int n, int high){
		super(n,high);
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
	
}
