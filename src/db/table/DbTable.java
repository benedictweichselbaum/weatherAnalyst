package db.table;

import java.util.ArrayList;
import java.util.List;

public class DbTable {
    List<String> columns;
    List<List<String>> values;

    public DbTable () {
        this.columns = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public void addColumn (String column) {
        columns.add(column);
    }

    public void addValues (List<String> valList) throws DbTableException{
        if (valList.size() == columns.size()) {
            values.add(valList);
        } else {
            throw new DbTableException("Value List has not the same size as the table columns.");
        }
    }
}
