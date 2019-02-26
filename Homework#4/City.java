
import java.util.ArrayList;

public class City {

    int cityNumber;
    String cityCode;
    String cityName;
    long cityPopulation;
    int cityElevation;
    ArrayList<Road> cityRoads;

    public City(int id, String code, String name) {
        cityNumber = id;
        cityCode = code;
        cityName = name;
        cityRoads = new ArrayList<Road>();
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(long p) {
        this.cityPopulation = p;
    }

    public int getElevation() {
        return cityElevation;
    }

    public void setElevation(int e) {
        this.cityElevation = e;
    }

    public int getCityNumber() {
        return cityNumber;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String n) {
        cityName = n;
    }

    public void addRoad(Road r) {
        cityRoads.add(r);
    }

    public void removeRoad(Road r) {
        cityRoads.remove(r);
    }

    public String toString() {
        return cityNumber + " " + cityCode + " " + cityName + " " + cityPopulation + " " + cityElevation;
    }
}
