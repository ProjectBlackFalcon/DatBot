package Test;

import java.io.IOException;
import java.util.Random;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		for (int i = 0; i < 200 ; i++){
			System.out.println(stop(1));
		}
	}
	
	private static long stop(int deviation) throws InterruptedException{
		double gauss = new Random().nextGaussian();
		return (long) (Math.abs(gauss*deviation) * 1000);
	}
}
		