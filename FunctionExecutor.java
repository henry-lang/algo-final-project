import java.util.Map;
import java.util.HashMap;

public class FunctionExecutor {
    private static final Map<String, Function> FUNCTIONS = new HashMap<>();
    static {
        FUNCTIONS.put("exit", new ExitFunction());
        FUNCTIONS.put("create_table", new CreateTableFunction());
        FUNCTIONS.put("insert", new InsertFunction());
    }

    public static JsonValue executeFunction(Database database, ParsedFunction parsedFunction) {
        String name = parsedFunction.functionName;

        if(FUNCTIONS.containsKey(name)) {
            return FUNCTIONS.get(name).call(database, parsedFunction.arguments);
        } else {
            return FunctionError.errorJson("Unknown function " + name);
        }
    }
}