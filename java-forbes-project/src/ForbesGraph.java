import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class ForbesGraph {
    public static void main() throws SQLException{
        SqlDB db = new SqlDB();
        db.connect();

        // SQL-запрос для получения общего капитала по странам
        String query = "SELECT country, SUM(networth) AS TotalCapital FROM Forbes GROUP BY country";
        ResultSet resultSet = db.executeQuery(query);

        // Набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        while (resultSet.next()) {
            String country = resultSet.getString("country");
            double totalCapital = resultSet.getDouble("TotalCapital");
            dataset.addValue(totalCapital, country, "Общий капитал");
        }

        // Создание графика
        JFreeChart chart = ChartFactory.createBarChart(
                "Общий капитал по странам в списке Forbes",
                "Страна",
                "Общий капитал",
                dataset);

        // Отображение графика в окне
        ChartFrame frame = new ChartFrame("Forbes Capital Graph", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
