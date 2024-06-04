import java.util.Map;
import java.util.HashMap;
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