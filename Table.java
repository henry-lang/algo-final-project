import java.util.List;
import java.util.ArrayList;

public class Table {
    // The public name of the table
    public String name;

    // The schema of the columns of the table
    public List<Column> columns;

    // The data storage of the table
    // Example format: if we have three columns, A B and C
    // The heap will be ABCABCABCABC in memory.
    // Since we're making the assumption right now that columns will never really be added,
    // We will store it like this to maintain cache locality.
    public List<Object> heap;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
        this.heap = new ArrayList<>();
    }
}