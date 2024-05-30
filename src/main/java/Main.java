import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner inputScanner= new Scanner(System.in);
        Calculator calculator= new Calculator();
        String expression= inputScanner.next();
        System.out.println(calculator.calculate(expression));

    }

}
//
//