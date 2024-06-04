import java.util.List;
import java.util.ArrayList;

public class CreateTableFunction implements Function {
    public JsonValue call(Database database, List<JsonValue> arguments) {
        String name = ((JsonString) arguments.get(0)).value;

        if(database.hasTableWithName(name)) {
            return FunctionError.errorJson("Table already exists with name " + name);
        }

        List<Column> columns = new ArrayList<>();
        for(int i = 1; i < arguments.size(); i++) {
            JsonArray obj = (JsonArray) arguments.get(i);

            columns.add(Column.fromJsonArray(obj));
        }

        database.createTable(name, columns);

        return new JsonNull();
    }
}