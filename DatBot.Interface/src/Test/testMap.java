package Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.communication.DisplayInfo;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		String param = "[1131,469,333]";
				
		String[] paramSplit = param.replaceAll("[\\[\\]]", "").split(",");
		for (String string : paramSplit) {
			System.out.println(string);
		}
	}
	
	


}
