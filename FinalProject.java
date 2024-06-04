import java.util.Scanner;

public class FinalProject {
    public static final String DATA_FOLDER = "data";

    public static void main(String[] args) {
        Database database = new Database(DATA_FOLDER);
        database.loadFromFolder();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String functionCall = scanner.nextLine().trim();
            if (functionCall.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                ParsedFunction parsedFunction = FunctionParser.parseFunctionCall(functionCall);
                System.out.println("Function Name: " + parsedFunction.functionName);
                for (JsonValue arg : parsedFunction.arguments) {
                    System.out.println("Argument: " + FunctionParser.jsonValueToString(arg));
                }

                JsonValue result = FunctionExecutor.executeFunction(database, parsedFunction);
                if(!(result instanceof JsonNull)) {
                    System.out.println(FunctionParser.jsonValueToString(result));
                }
            } catch (Exception e) {
                JsonValue errorVal = FunctionError.errorJson(e.toString());
                System.out.println(FunctionParser.jsonValueToString(errorVal));
            }
        }

        scanner.close();
    }
}
