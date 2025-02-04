import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellRunner {

    public static void main(String[] args) {
        // Create a BufferedReader to read input from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter a shell command to run (or type 'exit' to quit):");

        while (true) {
            try {
                // Read the command from the user
                String command = reader.readLine();

                // Exit if the user types 'exit'
                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Exiting...");
                    break;
                }

                // Run the shell command
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", command); // Use "cmd.exe" for Windows

                Process process = processBuilder.start();

                // Read the output from the command
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = outputReader.readLine()) != null) {
                    System.out.println(line);
                }

                // Read any error messages
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

                // Wait for the process to finish and get the exit value
                int exitCode = process.waitFor();
                System.out.println("\nExited with code: " + exitCode);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}