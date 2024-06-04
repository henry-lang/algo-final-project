import java.util.List;

public interface Function {
    JsonValue call(Database database, List<JsonValue> arguments);
}