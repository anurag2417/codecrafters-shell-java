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
                if(command.startsWith("echo")) {
                    System.out.println(command.substring(5));
                    continue;
                }

                if(command.startsWith("type ")) {
                    String arg = command.substring(5);

                    if(arg.equals("echo") ||
                        arg.equals("exit") ||
                        arg.equals("type")
                    ) {
                        System.out.println(arg + " is a shell builtin");
                    } else {
                        System.out.println(arg + ": not found ");
                    }
                    continue;
                }

                System.out.println(command + ": command not found");
            }
        }
    }
}