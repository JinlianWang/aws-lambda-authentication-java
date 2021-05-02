package shared;

public class EnvironmentVariableMissingException extends Exception {
    public EnvironmentVariableMissingException(String variableName){
        super("No environment variable is found with name: " + variableName);
    }
}
