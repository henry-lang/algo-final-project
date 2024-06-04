import java.util.List;
import java.util.Map;

public class InsertFunction implements Function {
    public JsonValue call(Database database, List<JsonValue> arguments) {
        if(arguments.size() < 2) {
            return FunctionError.errorJson("insert() expects two or more arguments: name of the table followed by the rows");
        }

        String tableName = ((JsonString) arguments.get(0)).value;

        for(int i = 1; i < arguments.size(); i++) {
            JsonObject data = (JsonObject) arguments.get(i);
            database.insertRow(tableName, data);
        }

        return new JsonNull();
    }
}