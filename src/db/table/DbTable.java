package db.table;

import java.util.ArrayList;
import java.util.List;

public class DbTable {
    List<String> columns;
    List<List<String>> tuples;

    public DbTable () {
        this.columns = new ArrayList<>();
        this.tuples = new ArrayList<>();
    }

    public void addColumn (String column) {
        columns.add(column);
    }

    public void addTuples (List<String> valList) throws DbTableException{
        if (valList.size() == columns.size()) {
            tuples.add(valList);
        } else {
            throw new DbTableException("Value List has not the same size as the table columns.");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(DbTableConstants.SPACE).append(DbTableConstants.SPACE);
        for (String column : this.columns) {
            builder.append(column);
            int missingSpaces = DbTableConstants.COLUMN_PRINT_SIZE - column.length();
            if (missingSpaces > 0) {
                builder.append(DbTableConstants.SPACE.repeat(Math.max(0, missingSpaces)));
            } else {
                builder.append(DbTableConstants.SPACE);
            }
        }
        int tupleCounter = 1;
        for (List<String> tuple : tuples) {
            builder.append("\n");
            builder.append(tupleCounter).append(DbTableConstants.SPACE);
            for (String value : tuple) {
                builder.append(value);
                int missingSpaces = DbTableConstants.COLUMN_PRINT_SIZE - value.length();
                if (missingSpaces > 0) {
                    builder.append(DbTableConstants.SPACE.repeat(Math.max(0, missingSpaces)));
                } else {
                    builder.append(DbTableConstants.SPACE);
                }
            }
            tupleCounter++;
        }
        return builder.toString();
    }
}
