
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String citiesFile = "city.dat", roadsFile = "road.dat";
        try {
            Graph graph = new Graph();
            graph.initialize(citiesFile, roadsFile);
            Scanner scanner = new Scanner(System.in);
            String response;
            City city;
            boolean exit = false;
            do {
                System.out.println("Command: ");
                response = scanner.next();
                response = response.toUpperCase().trim();
                if (response.length() == 1) {
                    switch (response.charAt(0)) {
                        case 'Q': //Display city information by entering a city code.
                            System.out.println("City code: ");
                            response = scanner.next();
                            city = graph.getCity(response);
                            if (city == null) {
                                System.out.println("City with code " + response + " does not exist in graph");
                            } else {
                                System.out.println(city.toString());
                            }
                            break;
                        case 'I': //Insert a road by entering two city codes and distance.
                            System.out.println("City codes and distance :");
                            graph.addRoad(scanner.next(), scanner.next(), scanner.nextInt());
                            break;
                        case 'H': //display help
                            System.out.println("Q Query the city information by entering the city code.");
                            System.out.println("D Find the minimum distance between two cities.");
                            System.out.println("I Insert a road by entering two city codes and distance.");
                            System.out.println("R Remove an existing road by entering two city codes.");
                            System.out.println("H Display this message.");
                            System.out.println("E Exit.");
                            break;
                        case 'D': //Find the minimum distance between two cities
                            System.out.println("City codes : ");
                            graph.getShortestDistance(scanner.next(), scanner.next());
                            break;
                        case 'R': //Remove an existing road by entering two city codes.
                            System.out.println("City codes : ");
                            graph.removeRoad(scanner.next(), scanner.next());
                            break;
                        case 'E': //Exit.
                            exit = true;
                            System.out.println("Program exiting.");
                            break;
                        default:
                            ;
                    }
                }
            } while (!exit);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
