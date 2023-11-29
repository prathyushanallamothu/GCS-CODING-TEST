package org.example;

// Import statements for required Java classes
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main class representing the Weather App
public class WeatherApp {
    // OpenWeatherMap API key and maximum number of favorite cities
    private static final String API_KEY = "c3f766938aca003d759a4766ab821a07";
    private static final int MAX_FAVORITES = 3;

    // Main method where the program execution starts
    public static void main(String[] args) {
        // Scanner for user input and a list to store favorite cities
        Scanner scanner = new Scanner(System.in);
        List<String> favoriteCities = new ArrayList<>();

        // Main loop for user interaction
        while (true) {
            // Display menu options
            System.out.println("\n===== Weather App Menu =====");
            System.out.println("1. Search for Weather Details of a City");
            System.out.println("2. Add a City to Favorites");
            System.out.println("3. List Favorite Cities");
            System.out.println("4. Update Favorite Cities");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Read user choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Switch statement to handle different menu options
            switch (choice) {
                case 1:
                    // Option 1: Search for Weather Details of a City
                    System.out.print("Enter the name of the city: ");
                    String city = scanner.nextLine();
                    getWeatherDetails(city);
                    break;

                case 2:
                    // Option 2: Add a City to Favorites
                    if (favoriteCities.size() < MAX_FAVORITES) {
                        System.out.print("Enter the name of the city to add to favorites: ");
                        String newCity = scanner.nextLine();
                        addFavoriteCity(favoriteCities, newCity);
                    } else {
                        System.out.println("You have reached the maximum limit of favorite cities (3).");
                    }
                    break;

                case 3:
                    // Option 3: List Favorite Cities
                    listFavoriteCities(favoriteCities);
                    break;

                case 4:
                    // Option 4: Update Favorite Cities
                    System.out.print("Enter the name of the city to remove from favorites: ");
                    String cityToRemove = scanner.nextLine();
                    updateFavoriteCities(favoriteCities, cityToRemove);
                    break;

                case 5:
                    // Option 5: Exit the program
                    System.out.println("Exiting the Weather App. Goodbye!");
                    System.exit(0);

                default:
                    // Handle invalid choices
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    // Method to get weather details for a given city from the OpenWeatherMap API
    private static void getWeatherDetails(String city) {
        try {
            // Construct the API URL
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
            URL url = new URL(apiUrl);

            // Open a connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and display the weather details
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                System.out.println("Weather details for " + city + ":\n" + response.toString());
            } else {
                // Handle API error response
                System.out.println("Error: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        }
    }

    // Method to add a city to the list of favorite cities
    private static void addFavoriteCity(List<String> favoriteCities, String newCity) {
        favoriteCities.add(newCity);
        System.out.println(newCity + " added to favorites.");
    }

    // Method to list the favorite cities and their weather details
    private static void listFavoriteCities(List<String> favoriteCities) {
        if (favoriteCities.isEmpty()) {
            System.out.println("No favorite cities added yet.");
        } else {
            System.out.println("Favorite Cities:");
            for (String city : favoriteCities) {
                getWeatherDetails(city);
            }
        }
    }

    // Method to update favorite cities by removing one and adding a new one
    private static void updateFavoriteCities(List<String> favoriteCities, String cityToRemove) {
        if (favoriteCities.contains(cityToRemove)) {
            // Remove the specified city from favorites
            favoriteCities.remove(cityToRemove);
            System.out.println(cityToRemove + " removed from favorites.");

            // Add a new city if the maximum limit is not reached
            if (favoriteCities.size() < MAX_FAVORITES) {
                System.out.print("Enter the name of the new city to add to favorites: ");
                Scanner scanner = new Scanner(System.in);
                String newCity = scanner.nextLine();
                addFavoriteCity(favoriteCities, newCity);
            } else {
                System.out.println("You have reached the maximum limit of favorite cities (3).");
            }
        } else {
            System.out.println(cityToRemove + " is not in your favorites.");
        }
    }
}

