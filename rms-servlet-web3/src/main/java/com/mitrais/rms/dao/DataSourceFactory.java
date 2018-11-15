package com.mitrais.rms.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class provides MySQL datasource to be used to connect to database.
 * It implements singleton pattern See <a href="http://www.oodesign.com/singleton-pattern.html">Singleton Pattern</a>
 */
public class DataSourceFactory
{
    private final DataSource dataSource;

    DataSourceFactory()
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        // TODO: make these database setting configurable by moving to properties file
        // classloader
        
        try {
	        InputStream myInputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
	        
	        Properties props = new Properties();
	        props.load(myInputStream);
	        
	        myInputStream.close();
	        
	        dataSource.setDatabaseName(props.getProperty("jdbc.db"));
	        dataSource.setServerName(props.getProperty("jdbc.host"));
	        Integer dbPort = Integer.parseInt(props.getProperty("jdbc.port"));
	        dataSource.setPort(dbPort);
	        dataSource.setUser(props.getProperty("jdbc.username"));
	        dataSource.setPassword(props.getProperty("jdbc.password"));
        }
        catch(IOException er) {
        	System.out.print("=====================================");
        	System.out.print("test");
        	System.out.print("=====================================");
        	System.out.print("Error: " + er.getMessage());
        }
        finally {
        	this.dataSource = dataSource;
        }
       
    }

    /**
     * Get a data source to database
     *
     * @return DataSource object
     */
    public static Connection getConnection() throws SQLException
    {
        return SingletonHelper.INSTANCE.dataSource.getConnection();
    }

    private static class SingletonHelper
    {
        private static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }
}
