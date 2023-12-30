import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForbesPeople {
    private int rank;
    private String name;
    private double networth;
    private int age;
    private String country;
    private String source;
    private String industry;

    public ForbesPeople(int rank, String name, double networth, int age, String country, String source, String industry) {
        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.country = country;
        this.source = source;
        this.industry = industry;
    }

    //Парсинг всех строк
    public static List<ForbesPeople> parseCSV(Path path) {
        try (Stream<String> csvLines = Files.lines(path)) {
            return csvLines.skip(1)
                    .map(ForbesPeople::parseLine)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Парсинг одной строки
    private static ForbesPeople parseLine(String line) {
        String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        int rank = Integer.parseInt(values[0].trim());
        String name = values[1].trim().replaceAll("\"", "");
        double networth = Double.parseDouble(values[2].trim());
        int age = Integer.parseInt(values[3].trim());
        String country = values[4].trim();
        String source = values[5].trim().replaceAll("\"", "");
        String industry = values[6].trim();

        return new ForbesPeople(rank, name, networth, age, country, source, industry);
    }


    // Геттеры и сеттеры

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNetworth() {
        return networth;
    }

    public void setNetworth(double networth) {
        this.networth = networth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    // Переопределение метода toString для удобного вывода объекта в строку
    @Override
    public String toString() {
        return "Person{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", networth=" + networth +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", source='" + source + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}