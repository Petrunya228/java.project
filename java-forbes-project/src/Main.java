import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.nio.file.Path;


public class Main {
    public static void main(String[] яargs) throws SQLException {
        SqlDB db = new SqlDB();
        db.connect();
        operationsWithDatabase(db);
        db.disconnect();

        //1 задача
        buildChart();

        //2 задача
        db.connect();
        printYoongestBillionaire(db);
        db.disconnect();

        //3 задача
        db.connect();
        printRichestInEnergy(db);
        db.disconnect();
    }

    public static void operationsWithDatabase(SqlDB datab) throws SQLException {
        Path filepath = Paths.get("src/Forbes.csv");
        List<ForbesPeople> fp = ForbesPeople.parseCSV(filepath);
        System.out.println(fp.get(72).toString());
        datab.createForbesTable();
        datab.insertData(fp);
    }

    public static void buildChart() throws SQLException {
        ForbesGraph.main();
    }

    public static void printYoongestBillionaire(SqlDB db) throws SQLException {
        ResultSet resultSet = db.executeQuery(db.getYoongestBillionaire());
        System.out.println("Самый молодой миллиардер из франции:");
        db.printResultSet(resultSet);
    }

    public static void printRichestInEnergy(SqlDB db) throws SQLException {
        ResultSet resultSet = db.executeQuery(db.getRichestInEnergy());
        System.out.println("Бизнесмен из США, имеющий самый большой капитал в сфере Energy:");
        db.printResultSet(resultSet);
    }
}