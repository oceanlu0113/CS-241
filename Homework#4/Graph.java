
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import GraphPackage.DirectedGraph;
import GraphPackage.GraphInterface;

public class Graph {

    ArrayList<City> Cities;
    HashMap<String, Road> Roads;
    HashMap<String, City> cityMap;

    public Graph() {
        GraphInterface<String> map = new DirectedGraph<>();
        Cities = new ArrayList<City>();
        cityMap = new HashMap<String, City>();
        Roads = new HashMap<String, Road>();
    }

    public void addRoad(int id1, int id2, int distance) {
        addRoad(Cities.get(id1 - 1), Cities.get(id2 - 1), distance);
    }

    public void addRoad(String source, String destination, int distance) {
        Road road;
        City city1 = cityMap.get(source), city2 = cityMap.get(destination);
        if (city1 == null || city2 == null) {
            System.out.println("WARNING: " + (city1 == null ? source : destination) + " does not exist ");
            return;
        }
        if ((road = Roads.get(source + destination)) != null) {
            System.out.println("WARNING: There is already a road with distance " + road.getDistance() + " between " + source + " and " + destination);
            return;
        }
        addRoad(city1, city2, distance);
        System.out.println("You have inserted a road from " + city1.getCityName() + " to " + city2.getCityName() + " with a distance of " + distance);
    }

    public void addRoad(City city1, City city2, int distance) {
        Road road = new Road(city1, city2, distance);
        city1.addRoad(road);
        Roads.put(city1.getCityCode() + city2.getCityCode(), road);
        //System.out.println(road);
    }

    public void removeRoad(String source, String destination) {
        Road road;
        City city1 = cityMap.get(source), city2 = cityMap.get(destination);
        if (city1 == null || city2 == null) {
            System.out.println("WARNING: " + (city1 == null ? source : destination) + " does not exist ");
            return;
        }
        if ((road = Roads.get(source + destination)) == null) {
            System.out.println("WARNING: The road from " + city1.getCityName() + " to " + city2.getCityName() + " does not exist");
            return;
        }
        city1.removeRoad(road);
        Roads.remove(source + destination);
        System.out.println("You have removed the road from " + city1.getCityName() + " to " + city2.getCityName() + " with a distance of " + road.getDistance());
    }

    public void initialize(String citiesFile, String roadsFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(citiesFile));
        City city;
        String name, str;
        while (scanner.hasNext()) {
            city = new City(scanner.nextInt(), scanner.next(), scanner.next());
            name = city.getCityName();
            while (true) {
                str = scanner.next();
                try {
                    city.setCityPopulation(Long.parseLong(str));
                    break;
                } catch (NumberFormatException e) {
                    name += " " + str;
                }
            }
            city.setCityName(name);
            city.setElevation(scanner.nextInt());
            cityMap.put(city.getCityCode(), city);
            Cities.add(city);
        }
        scanner.close();
        scanner = new Scanner(new File(roadsFile));
        while (scanner.hasNext()) {
            addRoad(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }
        scanner.close();
    }

    public City getCity(String shortCode) {
        return cityMap.get(shortCode);
    }

    // Dijkstra's algorithm
    public int getShortestDistance(String source, String destination) {
        City start = cityMap.get(source), target = cityMap.get(destination);
        if (start == null || target == null) {
            System.out.println("WARNING: " + (start == null ? source : destination) + " does not exist ");
            return 0;
        }
        HashMap<City, Integer> distance = new HashMap<City, Integer>();
        HashMap<City, City> previous = new HashMap<City, City>();
        City u = null, v = null;
        int newDistance;
        Integer min = null, d;
        ArrayList<City> queue = new ArrayList<City>();
        for (int i = 0; i < Cities.size(); i++) {
            v = Cities.get(i);
            queue.add(v);
        }
        distance.put(start, 0);
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            min = null;
            for (int i = 0; i < queue.size(); i++) {
                d = distance.get(v = queue.get(i));
                if (d != null) {
                    if (min == null || d < min) {
                        min = d;
                        u = v;
                    }
                }
            }
            queue.remove(u);
            for (Road r : u.cityRoads) {
                v = r.getToCity();
                newDistance = distance.get(u) + r.getDistance();
                if (distance.get(v) == null || newDistance < distance.get(v)) {
                    distance.put(v, newDistance);
                    previous.put(v, u);
                }
                if (v == target) {
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            String path = v.getCityCode();
            while (v != start) {
                v = previous.get(v);
                path = v.getCityCode() + " " + path;
            }
            System.out.println("\nThe minimum distance between " + start.getCityName() + " and " + target.getCityName() + " is " + distance.get(target) + " through the route :\n " + path);
            return distance.get(target);
        } else {
            System.out.println("\nCould not find path between " + start.getCityName() + " and " + target.getCityName());
            return -1;
        }
    }
}
