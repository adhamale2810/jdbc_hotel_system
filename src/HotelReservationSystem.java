import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {
    static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    static final String USERNAME = "root";
    static final String PASSWORD = "Roger365";
    static Connection connection = null;
    static Statement statement = null;

    static void showMenu() {
        System.out.println("1. New Reservation");
        System.out.println("2. View Reservations");
        System.out.println("3. Get Room Number");
        System.out.println("4. Update Reservation");
        System.out.println("5. Delete Reservation");
        System.out.println("6. Exit");
        System.out.println("=======================");
    }

    static void newReservation(Connection connection, Scanner sc) {
        try {
            Statement statement = connection.createStatement();

            System.out.print("Enter Guest Name: ");
            String guest_name = sc.next();
            sc.nextLine();

            System.out.print("Enter Room Number: ");
            int room_number = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Contact Number: ");
            String contact_number = sc.nextLine();

            String query = "INSERT INTO reservations(guest_name, room_number, contact_number) VALUES('" + guest_name + "', " + room_number + ", '" + contact_number + "')";
            statement.executeUpdate(query);
            System.out.println("Reservation Successful!!!");
            statement.close();
        } catch (Exception exception) {
            System.err.println("Failed to create new Reservation: " + exception.getMessage());
        }
    }

    static void viewReservations(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hotel_db.reservations");
            while (resultSet.next()) {
                System.out.println("-------------------");
                System.out.println("ID: " + resultSet.getInt("reservation_id"));
                System.out.println("Guest Name: " + resultSet.getString("guest_name"));
                System.out.println("Room Number: " + resultSet.getInt("room_number"));
                System.out.println("Contact Number: " + resultSet.getString("contact_number"));
                System.out.println("Reservation Date: " + resultSet.getTimestamp("reservation_date"));
            }
            statement.close();
        } catch (Exception exception) {
            System.err.println("Failed to fetch Reservations: " + exception.getMessage());
        }
    }

    static void getRoomNumber(Connection connection, Scanner sc) {
        try {
            Statement statement = connection.createStatement();
            System.out.print("Enter Reservation ID: ");
            int id = sc.nextInt();
            System.out.print("Enter Guest Name: ");
            String name = sc.next();
            ResultSet resultSet = statement.executeQuery("SELECT room_number FROM hotel_db.reservations WHERE reservation_id = " + id + " AND guest_name = '" + name + "';");
            if (resultSet.next()) {
                System.out.println("Room number for Reservation ID " + id + "and Guest " + name + " is " + resultSet.getInt("room_number"));
            } else {
                System.out.println("No such reservation found!!!");
            }
            statement.close();
        } catch (Exception exception) {
            System.err.println("Failed to fetch Room Number: " + exception.getMessage());
        }
    }

    static void updateReservation(Connection connection, Scanner sc) {
        try {
            Statement statement = connection.createStatement();
            System.out.print("Enter Reservation ID to update: ");
            int id = sc.nextInt();
            System.out.print("Guest Name: ");
            String guest_name = sc.next();
            System.out.print("Room Number: ");
            int room_number = sc.nextInt();
            System.out.print("Contact Number: ");
            String contact_number = sc.next();
            statement.executeUpdate("UPDATE hotel_db.reservations " + "SET guest_name = '" + guest_name + "', " + "room_number = " + room_number + ", " + "contact_number = '" + contact_number + "' " + "WHERE reservation_id = " + id + ";");
            System.out.println("Reservation Updated Successfully!!!");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hotel_db.reservations WHERE reservation_id = '" + id + "';");
            resultSet.next();
            System.out.println("Reservation ID: " + resultSet.getInt("reservation_id"));
            System.out.println("Guest Name: " + resultSet.getString("guest_name"));
            System.out.println("Room Number: " + resultSet.getInt("room_number"));
            System.out.println("Contact Number: " + resultSet.getString("contact_number"));
            System.out.println("Reservation Date: " + resultSet.getTimestamp("reservation_date"));
            statement.close();
        } catch (Exception exception) {
            System.err.println("Failed to update Reservation: " + exception.getMessage());
        }
    }

    static void deleteReservation(Connection connection, Scanner sc) {
        try {
            statement = connection.createStatement();
            System.out.print("Enter Reservation ID to delete: ");
            int id = sc.nextInt();
            statement.executeUpdate("DELETE FROM hotel_db.reservations WHERE reservation_id = '" + id + "';");
            System.out.println("Reservation Deleted Successfully!!!");
            statement.close();
        } catch (Exception exception) {
            System.err.println("Failed to Delete Reservation: " + exception.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to Database successfully!!!");
        } catch (Exception exception) {
            System.err.println("Failed to connect to Database: " + exception.getMessage());
        }

        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    newReservation(connection, sc);
                    System.out.println("=======================");
                    break;
                case 2:
                    viewReservations(connection);
                    System.out.println("=======================");
                    break;
                case 3:
                    getRoomNumber(connection, sc);
                    System.out.println("=======================");
                    break;
                case 4:
                    updateReservation(connection, sc);
                    System.out.println("=======================");
                    break;
                case 5:
                    deleteReservation(connection, sc);
                    System.out.println("=======================");
                    break;
                case 6:
                    System.out.println("Logging Out!!!");
                    System.out.println("=======================");
                    exit = true;
            }
        }
    }
}
