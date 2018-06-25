package com.kkp.shared;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbConnection {

    public Connection getDatabaseConnection() throws Exception {
        InitialContext context = new InitialContext();
        String jndiName = "java:/jboss/AssetManagement";
        DataSource ds = (DataSource) context.lookup(jndiName);
        Connection databaseConnection = ds.getConnection();
        return databaseConnection;
    }
}
