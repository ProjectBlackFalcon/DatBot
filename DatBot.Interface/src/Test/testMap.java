package Test;

import java.io.IOException;
import java.sql.Timestamp;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp.getTime());
		System.out.println(1531038532000L);
		
		System.out.println(((long) 1531038532000.0 - timestamp.getTime())/1000);

	}

}
		