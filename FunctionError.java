public class FunctionError {
    public static JsonValue errorJson(String message) {
        JsonObject obj = new JsonObject();
        obj.map.put("error", new JsonString(message));
        return obj;
    }
}