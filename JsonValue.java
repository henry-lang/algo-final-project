import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JsonValue {}
class JsonObject extends HashMap<String, JsonValue> implements JsonValue {
    public JsonObject() {
        super();
    }
}

class JsonArray extends ArrayList<JsonValue> implements JsonValue {
    public JsonArray() {
        super();
    }
}

class JsonString implements JsonValue {
    String value;

    public JsonString(String value) {
        this.value = value;
    }
}

class JsonNumber implements JsonValue {
    int value;

    public JsonNumber(int value) {
        this.value = value;
    }
}

class JsonBoolean implements JsonValue {
    boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }
}
class JsonNull implements JsonValue {}