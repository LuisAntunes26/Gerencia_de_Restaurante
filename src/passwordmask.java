import java.io.Console;

public class passwordmask {

    public static void main(String[] args){

        Console passWord;

        if((passWord = System.console())!= null){
            char[] passWordChecker = passWord.readPassword("Introduza a password: ");
            System.out.println("Your password is " + new String (passWordChecker));
        }else{
            System.out.println("No console found");
        }
    }
}
