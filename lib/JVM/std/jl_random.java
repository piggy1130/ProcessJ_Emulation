package std;

public class jl_random {
    // return a random number between 0 and 1
    public static double jl_random_1(){
        double randomnumber = Math.random();
        return randomnumber;
    }

    // return a INT random number between 0 and 100
    public static int jl_random_100(){
        int randomnumber = (int) (Math.random()*101);
        return randomnumber;
    }

    // return a number between min and max (inclusive)
    public static int getRandomNumber(int min, int max){
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}


