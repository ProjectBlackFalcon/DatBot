package fr.main.sniffer.tools;

public class Log {

    public Log()  { }

    public static void writeLogDebugMessage (String msg)
    {
        System.out.println("--- "+ msg +" ---");
    }

    public static void writeLogIdDebugMessage (String msg)
    {
        System.out.println("\n-------- "+ msg +" --------\n");
    }

    public static void writePacketMessage (String msg)
    {
        System.out.println("Packet : "+ msg);
    }

}
