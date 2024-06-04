import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.File;

class Database {
    private String dataFolder;
    private Map<String, Table> tables = new HashMap<>();

    public boolean hasTableWithName(String name) {
        return tables.containsKey(name);
    }

    public Table getTable(String name) {
        return tables.get(name);
    }

    public void createTable(String name, List<Column> columns) {
        Table table = new Table(name, columns);
        tables.put(name, table);
    }

    public void insertRow(String tableName, Map<String, JsonValue> data) {
        if (!hasTableWithName(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist.");
        }

        Table table = getTable(tableName);
        List<Column> columns = table.columns;

        for (Column column : columns) {
            JsonValue value = data.get(column.name);

            if (value == null && !column.allowsNull) {
                throw new IllegalArgumentException("Column " + column.name + " does not allow null values.");
            }

            if (value != null && !isTypeCompatible(column.type, value)) {
                throw new IllegalArgumentException("Column " + column.name + " expects type " + column.type);
            }

            table.heap.add(value == null ? null : (Object) value);
        }
    }

    private boolean isTypeCompatible(DataType type, JsonValue value) {
        switch (type) {
            case INT:
                return value instanceof JsonNumber;
            case STRING:
                return value instanceof JsonString;
            case BOOL:
                return value instanceof JsonBoolean;
            default:
                return false;
        }
    }

    public Database(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    private void ensureFolderCreated() {
        File dataFolderFile = new File(dataFolder);
        if (!dataFolderFile.exists()) {
            dataFolderFile.mkdir();
        }
    }

    public void loadFromFolder() {
        ensureFolderCreated();
    }
}
