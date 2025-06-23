package ACP;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableFetcher {
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableNames;
    }
}
