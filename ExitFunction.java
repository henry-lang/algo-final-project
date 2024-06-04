import java.util.List;

public class ExitFunction implements Function {
    public JsonValue call(Database database, List<JsonValue> arguments) {
        if(arguments.size() > 0) {
            return FunctionError.errorJson("Exit does not take any arguments");
        }

        System.exit(0);

        return new JsonNull();
    }
}