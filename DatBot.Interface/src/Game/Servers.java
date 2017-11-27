package Game;

public class Servers {
	public static int Merkator = 210;
	public static int Nidas = 209;
	public static int Julith = 208;
	public static int Ush = 207;
	public static int Pandore = 206;
	public static int Crocabulia = 202;
	public static int Atcham = 204;
	public static int Rubilax = 203;
	public static int Echo = 201;
	public static int Meriana = 205;
	public static int Agride = 36;
	public static int Ombre = 50;
	public static int Padgref = 5001;
	public static int Beldarion = 220;
	public static int Ganymède = 218;
	public static int Oto_Mustam = 22;
	public static int Dramak = 215;
	public static int Fraktale = 214;
	public static int Nevark = 217;
	public static int Brumen = 212;
	public static int Lacrima = 216;
	
	public static int getServerId(String nameServer){
		switch(nameServer){
		case "Julith":
			return Julith;
		case  "Echo":
			return Echo;
		}
		return 0;
	}
}
