import java.util.*;
import java.io.File;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        try (Scanner sc = new Scanner(System.in)) {
            
            while(true){
                System.out.print("$ ");
                if (!sc.hasNextLine()) {
                    break;
                }
                String command = sc.nextLine().trim();
                if (command.isEmpty()){
                    continue;
                }
                if (command.startsWith("exit")) {
                    break;
                }
                if (command.equals("pwd")) {
                    System.out.println(currentDirectory.toAbsolutePath());
                    continue;
                }
                if (command.startsWith("echo")) {
                    System.out.println(command.substring(5));
                    continue;
                }

                if (command.startsWith("type ")) {
                    String arg = command.substring(5);

                    if (arg.equals("echo")
                        || arg.equals("exit")
                        || arg.equals("type")
                        || arg.equals("pwd")
                        || arg.equals("cd")) {
                        System.out.println(arg + " is a shell builtin");
                        continue;
                    }
                    
                    Path executable = findExecutable(arg); 

                    if (executable != null) {
                        System.out.println(arg + " is " + executable);
                    } else {
                        System.out.println(arg + ": not found");
                    }

                    continue;
                }

                if (command.startsWith("cd ")) {
                    String target = command.substring(3);

                    Path newDir = Paths.get(target);

                    if (Files.exists(newDir) && Files.isDirectory(newDir)) {
                        currentDirectory = newDir.toAbsolutePath().normalize();
                    } else {
                        System.out.println(
                            "cd: " + target + ": No such file or directory"
                        );
                    }

                    continue;
                }


                String[] parts = command.split("\\s+");
                Path executable = findExecutable(parts[0]);
                if (executable != null) {
                List<String> cmd = new ArrayList<>();
                cmd.add(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    cmd.add(parts[i]);
                }
                Process process = new ProcessBuilder(cmd)
                        .directory(currentDirectory.toFile())
                        .inheritIO()
                        .start();    

                    process.waitFor();    
                    
                    continue;
                }

                System.out.println(command + ": command not found");
            }
        }
    }

    private static Path findExecutable(String command) {
        String pathEnv = System.getenv("PATH");

        if (pathEnv == null) {
            return null;
        }

        for (String dir : pathEnv.split(File.pathSeparator)) {
            Path path = Paths.get(dir, command);

            if (Files.exists(path) && Files.isExecutable(path)) {
                return path;
            }
        }

        return null;
    }
}