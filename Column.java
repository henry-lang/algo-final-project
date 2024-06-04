class Column {
    public String name;
    public DataType type;
    public boolean allowsNull;

    public Column(String name, DataType type, boolean allowsNull) {
        this.type = type;
        this.allowsNull = allowsNull;
    }
}

enum DataType {
    INT,
    STRING,
    BOOL
}