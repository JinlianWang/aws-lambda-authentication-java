package shared;

public class QueryParameterMissingException extends Exception {
    public QueryParameterMissingException(String parameterName){
        super("No query parameter is found with name: " + parameterName);
    }
}
