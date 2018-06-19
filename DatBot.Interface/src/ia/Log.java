package ia;

public class Log {

    public Log()  { }

    public static void writeLogDebugMessage (String msg)
    {
        System.out.println("--- "+ msg +" ---");
    }

    public static void writeLogErrorMessage (String msg)
    {
        System.out.println("!!!!!!!!!! "+ msg +" !!!!!!!!!!");
    }

}
