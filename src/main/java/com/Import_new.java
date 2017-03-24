package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;

public class Import_new
{

  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException,
      DatabaseUnitException
  {
    args = new String[] { "-regression" };
    
     //CER
    Connection jdbcConnection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=cer_dev", "sa", "P@ssword1");
    IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
    // @SuppressWarnings("deprecation")
    // Load XML file to DB unit dataset
    FlatXmlDataSet dataSet = new FlatXmlDataSet(new FileInputStream("./src/main/resources/cer_local" + args[0] + ".xml"));
    ReplacementDataSet rDataSet = new ReplacementDataSet(dataSet);
    rDataSet.addReplacementObject("[currentDateMinus2]", DateUtils.addDays(new Date(), -2));
    InsertIdentityOperation.REFRESH.execute(connection, rDataSet);
    connection.close();
    // FDS
    jdbcConnection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=FDS", "db_user", "db_user");
    connection = new DatabaseConnection(jdbcConnection);

    // @SuppressWarnings("deprecation")
    // Load XML file to DB unit dataset    
    FlatXmlDataSet dataSet2 = new FlatXmlDataSet(new FileInputStream("./src/main/resources/fds_local" + args[0] + ".xml")); 
    ReplacementDataSet rFdsDataSet = new ReplacementDataSet(dataSet2);
    rFdsDataSet.addReplacementObject("[currentDateMinus2]", DateUtils.addDays(new Date(), -2));
    InsertIdentityOperation.REFRESH.execute(connection, rFdsDataSet);
    connection.close();
    
    // FDS
    jdbcConnection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=datawarehouse", "sa", "P@ssword1");
    connection = new DatabaseConnection(jdbcConnection, "COMMON_APP_VIEWS_01");

    // @SuppressWarnings("deprecation")
    // Load XML file to DB unit dataset    
    FlatXmlDataSet dwDataset = new FlatXmlDataSet(new FileInputStream("./src/main/resources/cer_dw_local" + args[0] + ".xml")); 
    ReplacementDataSet rDwDataSet = new ReplacementDataSet(dwDataset);
    rDwDataSet.addReplacementObject("[currentDateMinus2]", DateUtils.addDays(new Date(), -2));
    InsertIdentityOperation.REFRESH.execute(connection, rDwDataSet);
    connection.close();
    System.out.println("Done");
  }
}
