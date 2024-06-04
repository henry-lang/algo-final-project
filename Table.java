import java.util.List;

public class Table {
    public String name;
    public List<Column> columns;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }
}