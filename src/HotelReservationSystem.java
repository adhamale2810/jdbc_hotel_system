import java.sql.Connection;
import java.sql.DriverManager;
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
        } catch (Exception exception) {
            System.err.println("Failed to create new Reservation: " + exception.getMessage());
        }
    }

    static void viewReservations(Connection connection) {

    }

    static void getRoomNumber(Connection connection, Scanner sc) {

    }

    static void updateReservation(Connection connection, Scanner sc) {

    }

    static void deleteReservation(Connection connection, Scanner sc) {

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
                    break;
                case 2:
                    viewReservations(connection);
                    break;
                case 3:
                    getRoomNumber(connection, sc);
                    break;
                case 4:
                    updateReservation(connection, sc);
                    break;
                case 5:
                    deleteReservation(connection, sc);
                    break;
                case 6:
                    System.out.println("Logging Out!!!");
                    exit = true;
            }
        }
    }
}
