/* Programmer : Dickens Odhiambo
 * BSE-05-0206/2024
 */

package carrentalsystem;

import java.util.ArrayList;
import java.util.List;

public class RentalAgency {
    private List<Car> availableCars;      // Cars available for rent
    private List<Car> rentedCars;         // Cars currently rented out
    private List<Client> clients;         // Registered clients
    private List<RentalTransaction> transactions; // Rental transaction history

    // Constructs a new RentalAgency with empty lists
    public RentalAgency() {
        availableCars = new ArrayList<>();
        rentedCars = new ArrayList<>();
        clients = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    // Adds a car to the list of available cars
    public void addCar(Car car) {
        availableCars.add(car);
    }

    // Adds a new client to the agency
    public void addClient(Client client) {
        clients.add(client);
    }

    // Rents a car to a client and records the transaction
    public boolean rentCar(String clientId, String carId, int rentalDuration) {
        Client client = findClient(clientId);
        Car car = findCar(carId);
        if (client != null && car != null && !car.isRented()) {
            car.setRented(true);
            availableCars.remove(car);
            rentedCars.add(car);
            double rentalCost = calculateRentalCost(car, rentalDuration);
            RentalTransaction transaction = new RentalTransaction(client, car, rentalDuration, rentalCost);
            transactions.add(transaction);
            System.out.println("Car rented successfully to " + client.getName() + ". Cost: Ksh " + rentalCost);
            return true;
        } else {
            System.out.println("Rental failed: Check client ID, car availability, or rental status.");
            return false;
        }
    }

    // Returns a rented car and updates the transaction
    public boolean returnCar(String carId) {
        Car car = findCarInRented(carId);
        if (car != null && car.isRented()) {
            car.setRented(false);
            rentedCars.remove(car);
            availableCars.add(car);
            RentalTransaction transaction = findTransactionByCarId(carId);
            if (transaction != null) {
                transaction.setReturned(true);
                System.out.println("Car " + car.getModel() + " returned successfully. Transaction completed.");
            }
            return true;
        } else {
            System.out.println("Return failed: Car not rented or not found.");
            return false;
        }
    }

    // Calculates the rental cost based on a fixed daily rate
    private double calculateRentalCost(Car car, int rentalDuration) {
        double baseRate = 3500.0; // Fixed daily rental rate in Ksh
        return baseRate * rentalDuration;
    }

    // Finds a client by their ID
    private Client findClient(String clientId) {
        return clients.stream().filter(c -> c.getClientId().equals(clientId)).findFirst().orElse(null);
    }

    // Finds a car by ID from the available or rented cars
    private Car findCar(String carId) {
        return availableCars.stream()
                .filter(c -> c.getCarId().equals(carId))
                .findFirst()
                .orElse(null);
    }

    // Finds a car in the list of rented cars
    private Car findCarInRented(String carId) {
        return rentedCars.stream()
                .filter(c -> c.getCarId().equals(carId))
                .findFirst()
                .orElse(null);
    }

    // Finds a transaction by car ID that has not yet been marked returned
    private RentalTransaction findTransactionByCarId(String carId) {
        return transactions.stream()
                .filter(t -> t.getCar().getCarId().equals(carId) && !t.isReturned())
                .findFirst()
                .orElse(null);
    }

    // Displays all rental transactions
    public void displayTransactions() {
        System.out.println("Rental Transactions:");
        for (RentalTransaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    @Override
    public String toString() {
        return "RentalAgency [Available Cars=" + availableCars.size() +
               ", Rented Cars=" + rentedCars.size() +
               ", Clients=" + clients.size() +
               ", Transactions=" + transactions.size() + "]";
    }
}

/**
 * Represents a rental transaction between a client and a car.
 */
class RentalTransaction {
    private Client client;
    private Car car;
    private int rentalDuration;
    private double cost;
    private boolean returned;

    public RentalTransaction(Client client, Car car, int rentalDuration, double cost) {
        this.client = client;
        this.car = car;
        this.rentalDuration = rentalDuration;
        this.cost = cost;
        this.returned = false;
    }

    public Client getClient() { return client; }
    public Car getCar() { return car; }
    public int getRentalDuration() { return rentalDuration; }
    public double getCost() { return cost; }
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

    @Override
    public String toString() {
        return "Transaction [Client=" + client.getName() +
               ", Car=" + car.getModel() +
               ", Duration=" + rentalDuration + " days" +
               ", Cost=Ksh " + cost +
               ", Returned=" + returned + "]";
    }
}
