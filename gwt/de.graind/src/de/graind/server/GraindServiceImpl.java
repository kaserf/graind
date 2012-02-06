package de.graind.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.graind.client.service.GraindService;

public class GraindServiceImpl extends RemoteServiceServlet implements GraindService {

  private static final long serialVersionUID = 1L;
  private Connection connection;

  public GraindServiceImpl() {

    try {
      Class.forName("org.postgresql.Driver");

      String url = "jdbc:postgresql:graind";
      Properties props = new Properties();
      props.setProperty("user", "graind");
      props.setProperty("password", "graind");
      props.setProperty("ssl", "true");
      this.connection = DriverManager.getConnection(url, props);

    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public List<Integer> queryDatabaseTest() {
    List<Integer> ret = new LinkedList<Integer>();

    try {
      Statement stmt = this.connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM test");
      while (rs.next()) {
        ret.add(rs.getInt(1));
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ret;
  }
}
