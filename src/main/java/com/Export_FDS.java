package com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class Export_FDS {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, DatabaseUnitException {
		        // database connection
		        Class driverClass = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        /*Connection jdbcConnection = DriverManager.getConnection(
		                "jdbc:sqlserver://localhost:1433;database=fds_local", "sa", "P@ssword1");*/
		        Connection jdbcConnection = DriverManager.getConnection(
		                "jdbc:sqlserver://SDQESSQL112:33302;databaseName=FDS_Dev", "FDS_DBO", "ka2vLF2p");
		        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		        // partial database export
		        QueryDataSet partialDataSet = new QueryDataSet(connection);
		        partialDataSet.addTable("fds_file");
		       /* partialDataSet.addTable("fds_file_attribute");
		        partialDataSet.addTable("file_group");
		        partialDataSet.addTable("file_group_attribute");
		        partialDataSet.addTable("filegroup_files");*/
		        
		        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));
/*
		        // full database export
		        IDataSet fullDataSet = connection.createDataSet();
		        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
		        
		        // dependent tables database export: export table X and all tables that
		        // have a PK which is a FK on X, in the right order for insertion
		        String[] depTableNames = 
		          TablesDependencyHelper.getAllDependentTables( connection, "X" );
		        IDataSet depDataset = connection.createDataSet( depTableNames );
		        FlatXmlDataSet.write(depDataset, new FileOutputStream("dependents.xml"));     */     
		        
	}

}
