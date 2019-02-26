
public class Road {

    City fromCity;
    City toCity;
    int cityDistance;

    public Road(City fc, City tc, int d) {
        fromCity = fc;
        toCity = tc;
        cityDistance = d;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City GrpFromCity) {
        this.fromCity = GrpFromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City GrpToCity) {
        this.toCity = GrpToCity;
    }

    public int getDistance() {
        return cityDistance;
    }

    public void setDistance(int d) {
        this.cityDistance = d;
    }

    public String toString() {
        return fromCity.getCityCode() + "->" + toCity.getCityCode() + " = " + cityDistance;
    }
}
