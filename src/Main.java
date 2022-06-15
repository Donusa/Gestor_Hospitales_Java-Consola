
public class Main {
    public static void main(String[] args){
		Sistema thread1 = new Sistema();
		TimeControl thread2 = new TimeControl();

		thread1.start();
		thread2.start();
 
    }
}
