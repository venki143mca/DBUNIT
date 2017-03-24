package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;

public class Import_FDS {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, DatabaseUnitException {
		        // database connection
		        Class driverClass = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        Connection jdbcConnection = DriverManager.getConnection(
		                "jdbc:sqlserver://localhost:1433;database=cer_dev", "sa", "P@ssword1");
		        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

	        @SuppressWarnings("deprecation")
			FlatXmlDataSet dataSet = new FlatXmlDataSet(new FileInputStream("C:\\Users\\c_ksuvarna\\Perforce\\QDC_SAMPLES\\DBUNIT\\DBUNIT\\src\\main\\resources\\cer_local.xml")); // Load XML file to DB unit dataset 
        	InsertIdentityOperation.REFRESH.execute(connection, dataSet);
        	connection.close();
	        	
	        	 driverClass = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		         jdbcConnection = DriverManager.getConnection(
		                "jdbc:sqlserver://localhost:1433;database=FDS", "sa", "P@ssword1");
		         connection = new DatabaseConnection(jdbcConnection);

		        @SuppressWarnings("deprecation")
				FlatXmlDataSet dataSet2 = new FlatXmlDataSet(new FileInputStream("C:\\Users\\c_ksuvarna\\Perforce\\QDC_SAMPLES\\DBUNIT\\DBUNIT\\src\\main\\resources\\fds_local.xml")); // Load XML file to DB unit dataset 
	        	InsertIdentityOperation.REFRESH.execute(connection, dataSet2);
	        	connection.close(); 
	        	System.out.println("Done");
		        
	}

}
