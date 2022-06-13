import java.util.Scanner;

public class ScannerSingleton{
    private static Scanner sc = null;

    private ScannerSingleton() {
    	
    }

    public static synchronized Scanner getInstance() {
        if (sc == null) {
        	sc = new Scanner(System.in);
        }
        return sc;
    }
}