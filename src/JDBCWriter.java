import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class JDBCWriter {

    private Connection connection = null;

    public boolean setConnection() {
        final String url = "jdbc:mysql://localhost:3306/urlread3?serverTimezone=UTC";
        boolean bres = false;
        try {
            connection = DriverManager.getConnection(url, "Jeffrey", "Jeffermus1234");
            bres = true;
        } catch (SQLException ioerr) {
            System.out.println("Vi fik IKKE connection=" + ioerr.getMessage());
        }
        return bres;
    }

    public Vector<String> getLines(String aUrl, String aWord) {
        String searchStr = "SELECT left(line, 20) as line FROM urlreads where url like ? and line like ? LIMIT 10";
        PreparedStatement preparedStatement;
        Vector<String> v1 = new Vector<>();
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            preparedStatement.setString(1, "%" + aUrl + "%");
            preparedStatement.setString(2, "%" + aWord + "%");
            System.out.println(preparedStatement.toString());
            ResultSet resset = preparedStatement.executeQuery();
            String str;
            while (resset.next()) {
                str = "" + resset.getObject("line");
                v1.add(str);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Fejl i getlines=" + sqlerr.getMessage());
        }
        return v1;
    }

    public int searchDB(String aUrl, String aWord) {
        //String searchStr = "SELECT count(*) FROM urlreads where url like " + '"' + "%" + aUrl + "%" + '"' + " and line like " + '"' + "%" + aWord + "%" + '"';
        String searchStr = "SELECT count(*) FROM urlreads where url like ? and line like ?";
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            System.out.println(searchStr);
            preparedStatement.setString(1, "%" + aUrl + "%" );
            preparedStatement.setString(2, "%" + aWord + "%");
            ResultSet resset =  preparedStatement.executeQuery();
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
                System.out.println("Fundet antal=" + res);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Fejl i search=" + sqlerr.getMessage());
        }
        return res;
    }

    public int deleteLines(String aUrl, String aWord) {
        String delstr = "DELETE FROM urlreads where url like ? and line like ?";
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(delstr);
            System.out.println(delstr);
            preparedStatement.setString(1, "%" + aUrl + "%" );
            preparedStatement.setString(2, "%" + aWord + "%");
            res = preparedStatement.executeUpdate();
        } catch (SQLException sqlerr) {
            System.out.println("Fejl i search=" + sqlerr.getMessage());
        }
        return res;
    }

    public int writeLines(String aUlr, ArrayList<String> aLst) {
        String insstr = "INSERT INTO urlreads(url, line, linelen, medtext) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        int res = 0;
        String lineln = "";
        for (String line : aLst) {
            try {
                preparedStatement = connection.prepareStatement(insstr);
                preparedStatement.setString(1, aUlr);
                if (line.length() > 0) {
                    lineln = "" + line.length();
                    if ((line.length() < 15000) && (line.length() > 0)) {
                        preparedStatement.setString(2, line);
                        preparedStatement.setString(3, "" + lineln);
                        preparedStatement.setString(4, " ");
                    } else {
                        preparedStatement.setString(2, " ");
                        preparedStatement.setString(3, "" + lineln);
                        preparedStatement.setString(4, line);
                    }
                    int rowcount = preparedStatement.executeUpdate();
                    //System.out.println("Indsat række=" + rowcount);
                    res = res + rowcount;
                    if (res % 100 == 0) {
                        System.out.println("Har saved rowcount=" + res + " url=" + aUlr);
                    }
                } //line.length > 0
            } catch (SQLException sqlerr) {
                String errmsg = sqlerr.getMessage();
                System.out.println("Fejl i INSERT=" + errmsg + " len=" + lineln);
            }
        }
        return res;
    }

}
