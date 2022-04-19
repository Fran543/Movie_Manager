/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal.sql;

import javax.sql.DataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 *
 * @author fran
 */
public class DataSourceSingleton {
    
    private static final String SERVER_NAME = "LocalHost";
    private static final String DATABASE_NAME = "MovieDatabase";
    private static final String USER = "sa";
    private static final String PASSWORD = "SQL";

    private DataSourceSingleton() {
    }
    
    private static DataSource instance;

    public static DataSource getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static DataSource createInstance() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(SERVER_NAME);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
