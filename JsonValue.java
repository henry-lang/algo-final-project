import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonValue {}
class JsonObject extends JsonValue {
    Map<String, JsonValue> map = new HashMap<>();
}
class JsonArray extends JsonValue {
    List<JsonValue> list = new ArrayList<>();
}
class JsonString extends JsonValue {
    String value;
    JsonString(String value) { this.value = value; }
}
class JsonNumber extends JsonValue {
    Number value;
    JsonNumber(Number value) { this.value = value; }
}
class JsonBoolean extends JsonValue {
    Boolean value;
    JsonBoolean(Boolean value) { this.value = value; }
}
class JsonNull extends JsonValue {}