class Column {
    public String name;
    public DataType type;
    public boolean allowsNull;
    public boolean indexed;

    public Column(String name, DataType type, boolean allowsNull, boolean indexed) {
        this.name = name;
        this.type = type;
        this.allowsNull = allowsNull;
        this.indexed = indexed;
    }

    public static Column fromJsonArray(JsonArray array) {
        String name = ((JsonString) array.get(0)).value;
        String typeStr = ((JsonString) array.get(1)).value;
        boolean allowsNull = typeStr.endsWith("?");
        DataType type;
        
        switch (typeStr.replace("?", "")) {
            case "int":
                type = DataType.INT;
                break;
            case "string":
                type = DataType.STRING;
                break;
            case "bool":
                type = DataType.BOOL;
                break;
            default:
                throw new IllegalArgumentException("Unknown data type: " + typeStr);
        }

        boolean indexed = array.size() > 2 ? ((JsonBoolean) array.get(2)).value : false;
        
        return new Column(name, type, allowsNull, indexed);
    }
}

enum DataType {
    INT,
    STRING,
    BOOL;
}
