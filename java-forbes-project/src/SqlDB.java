import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.PreparedStatement;

public class SqlDB {
    private Connection connection;
    // Константа с названием бд
    private final String DB = "database.db";

    // Подключение к бд
    public void connect() throws SQLException {
        String url = "jdbc:sqlite:" + this.DB;
        connection = DriverManager.getConnection(url);
        System.out.println("Подключение к базе данных успешно!\n");
    }

    // Отключение от бд
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("\nУспешное отключение!");
        }
    }

    // Создаём таблицу если она не существует
    public void createForbesTable() throws SQLException {
        Statement statement = connection.createStatement();
        String createSchoolTableQuery = "CREATE TABLE IF NOT EXISTS Forbes (" +
                "rank INTEGER, " +
                "name TEXT, " +
                "networth DOUBLE, " +
                "age INTEGER, " +
                "country TEXT, " +
                "source TEXT, " +
                "industry TEXT)";
        statement.execute(createSchoolTableQuery);
        System.out.println("Таблица создана");
    }

    // Вставка данных в бд
    public void insertData(List<ForbesPeople> forbes) throws SQLException {
        String insertDataQuery = "INSERT INTO Forbes (rank, name, networth, age, country, source, industry) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataQuery);
        for (ForbesPeople forb : forbes) {
            preparedStatement.setInt(1, forb.getRank());
            preparedStatement.setString(2, forb.getName());
            preparedStatement.setDouble(3, forb.getNetworth());
            preparedStatement.setInt(4, forb.getAge());
            preparedStatement.setString(5, forb.getCountry());
            preparedStatement.setString(6, forb.getSource());
            preparedStatement.setString(7, forb.getIndustry());
            preparedStatement.execute();
        }
        System.out.println("Поля добавлены");
    }

    //Метод для выполнения запросов
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }


    //Вывод столбцов и строк после выполнения
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Вывод заголовков столбцов
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        // Вывод данных строк
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    // Sql запрос для получения самого молодого миллиардера из франции
    public String getYoongestBillionaire() {
        return "SELECT * FROM Forbes " +
                "WHERE country = 'France' AND " +
                "networth > 10 " +
                "ORDER BY age ASC " +
                "LIMIT 1";
    }

    // Sql запрос для получения бизнесмена из США, имеющего самый большой капитал в сфере Energy
    public String getRichestInEnergy() {
        return "SELECT name AS 'Имя', source AS 'Компания' " +
                "FROM Forbes " +
                "WHERE country = 'United States' " +
                "AND industry = 'Energy' " +
                "ORDER BY networth DESC " +
                "LIMIT 1";
    }
}
