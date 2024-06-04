import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionParser {
    public static ParsedFunction parseFunctionCall(String functionCall) throws Exception {
        int openParenIndex = functionCall.indexOf('(');
        int closeParenIndex = functionCall.lastIndexOf(')');
        
        if (openParenIndex == -1 || closeParenIndex == -1 || closeParenIndex < openParenIndex) {
            throw new IllegalArgumentException("Invalid function call format.");
        }

        String functionName = functionCall.substring(0, openParenIndex).trim();
        String argsString = functionCall.substring(openParenIndex + 1, closeParenIndex).trim();
        
        List<JsonValue> arguments = parseArguments(argsString);

        return new ParsedFunction(functionName, arguments);
    }

    private static List<JsonValue> parseArguments(String argsString) throws Exception {
        List<JsonValue> arguments = new ArrayList<>();
        if (argsString.isEmpty()) {
            return arguments; // Return an empty list if there are no arguments
        }
        int length = argsString.length();
        int bracketCount = 0;
        int braceCount = 0;
        int startIndex = 0;

        for (int i = 0; i < length; i++) {
            char c = argsString.charAt(i);
            switch (c) {
                case '[':
                    bracketCount++;
                    break;
                case ']':
                    bracketCount--;
                    break;
                case '{':
                    braceCount++;
                    break;
                case '}':
                    braceCount--;
                    break;
                case ',':
                    if (bracketCount == 0 && braceCount == 0) {
                        arguments.add(parseJsonValue(argsString.substring(startIndex, i).trim()));
                        startIndex = i + 1;
                    }
                    break;
            }
        }
        arguments.add(parseJsonValue(argsString.substring(startIndex).trim()));

        return arguments;
    }

    private static JsonValue parseJsonValue(String value) throws Exception {
        if (value.startsWith("{") && value.endsWith("}")) {
            return parseJsonObject(value);
        } else if (value.startsWith("[") && value.endsWith("]")) {
            return parseJsonArray(value);
        } else if (value.startsWith("\"") && value.endsWith("\"")) {
            return new JsonString(value.substring(1, value.length() - 1));
        } else if (value.equals("true") || value.equals("false")) {
            return new JsonBoolean(Boolean.parseBoolean(value));
        } else if (value.equals("null")) {
            return new JsonNull();
        } else {
            try {
                return new JsonNumber(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid JSON value: " + value);
            }
        }
    }

    private static JsonObject parseJsonObject(String json) throws Exception {
        JsonObject jsonObject = new JsonObject();
        json = json.substring(1, json.length() - 1).trim();
        int length = json.length();
        int braceCount = 0;
        int bracketCount = 0;
        int startIndex = 0;
        String key = null;

        for (int i = 0; i < length; i++) {
            char c = json.charAt(i);
            switch (c) {
                case '[':
                    bracketCount++;
                    break;
                case ']':
                    bracketCount--;
                    break;
                case '{':
                    braceCount++;
                    break;
                case '}':
                    braceCount--;
                    break;
                case ':':
                    if (braceCount == 0 && bracketCount == 0) {
                        key = json.substring(startIndex, i).trim();
                        key = key.substring(1, key.length() - 1); // Remove quotes
                        startIndex = i + 1;
                    }
                    break;
                case ',':
                    if (braceCount == 0 && bracketCount == 0) {
                        jsonObject.put(key, parseJsonValue(json.substring(startIndex, i).trim()));
                        startIndex = i + 1;
                    }
                    break;
            }
        }
        jsonObject.put(key, parseJsonValue(json.substring(startIndex).trim()));
        return jsonObject;
    }

    private static JsonArray parseJsonArray(String json) throws Exception {
        JsonArray jsonArray = new JsonArray();
        json = json.substring(1, json.length() - 1).trim();
        int length = json.length();
        int braceCount = 0;
        int bracketCount = 0;
        int startIndex = 0;

        for (int i = 0; i < length; i++) {
            char c = json.charAt(i);
            switch (c) {
                case '[':
                    bracketCount++;
                    break;
                case ']':
                    bracketCount--;
                    break;
                case '{':
                    braceCount++;
                    break;
                case '}':
                    braceCount--;
                    break;
                case ',':
                    if (braceCount == 0 && bracketCount == 0) {
                        jsonArray.add(parseJsonValue(json.substring(startIndex, i).trim()));
                        startIndex = i + 1;
                    }
                    break;
            }
        }
        jsonArray.add(parseJsonValue(json.substring(startIndex).trim()));
        return jsonArray;
    }

    public static String jsonValueToString(JsonValue value) {
        if (value instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) value;
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            boolean first = true;
            for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append("\"").append(entry.getKey()).append("\": ").append(jsonValueToString(entry.getValue()));
                first = false;
            }
            sb.append("}");
            return sb.toString();
        } else if (value instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) value;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true;
            for (JsonValue element : jsonArray) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(jsonValueToString(element));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        } else if (value instanceof JsonString) {
            return "\"" + ((JsonString) value).value + "\"";
        } else if (value instanceof JsonNumber) {
            return "" + ((JsonNumber) value).value;
        } else if (value instanceof JsonBoolean) {
            return "" + ((JsonBoolean) value).value;
        } else if (value instanceof JsonNull) {
            return "null";
        }
        return "";
    }
}

class ParsedFunction {
    String functionName;
    List<JsonValue> arguments;

    ParsedFunction(String functionName, List<JsonValue> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }
}
