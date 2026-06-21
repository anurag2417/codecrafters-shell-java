import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            while(true) {
                System.out.print("$ ");
                if(!sc.hasNextLine()) {
                    break;
                }
                String command = sc.nextLine().trim();
                
                if(command.isEmpty()){
                    continue;
                }
                if(command.startsWith("exit")) {
                    break;
                }
                System.out.println(command + ": command not found");
            }
        }
    }
}