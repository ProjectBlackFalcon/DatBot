package Test;

import java.text.Normalizer;

import protocol.enums.BoostableCharacteristicEnum;

public class TestUTF8 {

	public static void main(String[] args) {
		System.out.println(BoostableCharacteristicEnum.valueOf("Agi").getId());
		System.out.println(BoostableCharacteristicEnum.Agi);
		System.out.println(stripAccents("Tête de mort en pavés + ï"));
	}
	
	public static String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}

}