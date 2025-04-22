package std;

public class jl_sleep {
    
    public static void jl_sleep(int t){
        try{
            //System.out.println("Jiaqi is testing Sleep...");
            Thread.sleep(t);
            //System.out.println("Awake!");
        } catch(InterruptedException e){
            System.err.println("Sleep was interrupted");
        }

	}
   
}