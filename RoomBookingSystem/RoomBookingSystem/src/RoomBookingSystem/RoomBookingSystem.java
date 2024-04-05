package RoomBookingSystem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Class representing a conference room
class ConferenceRoom {
    int id;
    String name;
    int capacity;
    int floor;
    boolean status; // true for free, false for busy

    // Constructor
    public ConferenceRoom(int id, String name, int capacity, int floor, boolean status) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.floor = floor;
        this.status = status;
    }

    // Getters and setters
    // Add as needed
}

// Class representing a booking
class Booking {
    int roomId;
    String roomName;
    String date;
    String startTime;
    String endTime;
    String meetingTitle;

    // Constructor
    public Booking(int roomId, String roomName, String date, String startTime, String endTime, String meetingTitle) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingTitle = meetingTitle;
    }

    // Getters and setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }
}

// Class representing the Room Booking System
public class RoomBookingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static TreeMap<Integer, ConferenceRoom> conferenceRooms = new TreeMap<>();
    private static HashMap<Integer, Booking> bookings = new HashMap<>();
    private static int roomIdCounter = 1;
    private static int bookingIdCounter = 1;
    private static boolean isAdminLoggedIn = false;
    private static boolean isUserLoggedIn = false;

    // Method to authenticate admin
    private static boolean authenticateAdmin() {
        System.out.println("Enter username:");
        String adminUsername = scanner.nextLine();
        System.out.println("Enter password:");
        String adminPassword = scanner.nextLine();
        if (adminUsername.equals("college") && adminPassword.equals("college@123")) {
            System.out.println("Successfully logged in as admin.");
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    // Method to authenticate user
    private static boolean authenticateUser() {
        System.out.println("Enter username:");
        String userUsername = scanner.nextLine();
        System.out.println("Enter password:");
        String userPassword = scanner.nextLine();
        if (userUsername.equals("abc") && userPassword.equals("abc124")) {
            System.out.println("Successfully logged in as user.");
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    // Method to add a new conference room
    private static void addConferenceRoom() {
        System.out.println("Enter name of the conference room:");
        String name = scanner.nextLine();
        System.out.println("Enter capacity of the conference room (5-50):");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter location (floor) of the conference room (1-10):");
        int floor = Integer.parseInt(scanner.nextLine());
        conferenceRooms.put(roomIdCounter++, new ConferenceRoom(roomIdCounter, name, capacity, floor, true));
        System.out.println("Conference room added successfully.");
    }

    // Method to list all conference rooms
    private static void listConferenceRooms() {
        if (conferenceRooms.isEmpty()) {
            System.out.println("No conference rooms available yet.");
            return;
        }
        System.out.println("+----+------------------+-------+----------+----------+");
        System.out.println("| ID |       Name       | Floor | Capacity |  Status  |");
        System.out.println("+----+------------------+-------+----------+----------+");
        for (Map.Entry<Integer, ConferenceRoom> entry : conferenceRooms.entrySet()) {
            ConferenceRoom room = entry.getValue();
            if (room.status) { // Check if the room is free
                System.out.printf("| %-2d | %-16s |   %-2d  |   %-6d | %-7s |%n", room.id, room.name, room.floor,
                        room.capacity, "Free");
            }
        }
        System.out.println("+----+------------------+-------+----------+----------+");
    }

    private static void adminListConferenceRooms() {
        if (conferenceRooms.isEmpty()) {
            System.out.println("No conference rooms available yet.");
            return;
        }
        System.out.println("+----+------------------+-------+----------+----------+");
        System.out.println("| ID |       Name       | Floor | Capacity |  Status  |");
        System.out.println("+----+------------------+-------+----------+----------+");
        for (Map.Entry<Integer, ConferenceRoom> entry : conferenceRooms.entrySet()) {
            ConferenceRoom room = entry.getValue();
            System.out.printf("| %-2d | %-16s |   %-2d  |   %-6d | %-7s |%n", room.id, room.name, room.floor,
                    room.capacity, room.status ? "Free" : "Busy");
        }
        System.out.println("+----+------------------+-------+----------+----------+");
    }


    // Method to book a slot
    private static void bookSlot() {
        System.out.println("Enter conference room ID:");
        int roomId = Integer.parseInt(scanner.nextLine());
        ConferenceRoom room = conferenceRooms.get(roomId);
        if (room == null) {
            System.out.println("Invalid conference room ID.");
            return;
        }
        if (!room.status) {
            System.out.println("Sorry, the room is currently busy.");
            return;
        }
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        // Validate and get the start time in HH:MM AM/PM format
        String startTime = getValidTime("Enter start time (HH:MM AM/PM):");
        if (startTime == null) {
            System.out.println("Invalid time format.");
            return;
        }

        // Validate and get the end time in HH:MM AM/PM format
        String endTime = getValidTime("Enter end time (HH:MM AM/PM):");
        if (endTime == null) {
            System.out.println("Invalid time format.");
            return;
        }

        System.out.println("Enter meeting title:");
        String meetingTitle = scanner.nextLine();
        System.out.println("Confirm booking for " + room.name + " on " + date + " from " + startTime + " to " + endTime + "? (Y/N)");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("Y")) {
            room.status = false;
            bookings.put(bookingIdCounter++, new Booking(roomId, room.name, date, startTime, endTime, meetingTitle));
            System.out.println("Booking successful.");
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    // Method to validate and get the time in HH:MM AM/PM format
    private static String getValidTime(String message) {
        while (true) {
            System.out.println(message);
            String timeInput = scanner.nextLine().trim();
            try {
                // Parse the input time
                SimpleDateFormat sdfInput = new SimpleDateFormat("hh:mma", Locale.ENGLISH);
                Date time = sdfInput.parse(timeInput.toUpperCase());

                // Format the time in HH:MM AM/PM format
                SimpleDateFormat sdfOutput = new SimpleDateFormat("hh:mma", Locale.ENGLISH);
                return sdfOutput.format(time);
            } catch (ParseException e) {
                // Invalid time format
                System.out.println("Invalid time format. Please enter the time in HH:MM AM/PM format.");
            }
        }
    }

    // Method to display all booked conference rooms
    private static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No rooms booked yet.");
            return;
        }
        System.out.println("Booked Conference Rooms:");
        for (Map.Entry<Integer, Booking> entry : bookings.entrySet()) {
            Booking booking = entry.getValue();
            System.out.println("Room ID: " + booking.roomId + ", Room Name: " + booking.roomName +
                    ", Date: " + booking.date + ", Start Time: " + booking.startTime + ", End Time: " + booking.endTime +
                    ", Meeting Title: " + booking.meetingTitle);
        }
    }

    // Method to automatically free conference rooms whose bookings have ended
    private static void freeExpiredRooms() {
        Date currentDate = new Date(); // Get the current date and time

        // Iterate through the bookings
        Iterator<Map.Entry<Integer, Booking>> iterator = bookings.entrySet().iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next().getValue();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.ENGLISH);
                Date endTime = sdf.parse(booking.getDate() + " " + booking.getEndTime().toUpperCase());

                // If the end time of the booking is before the current date and time, free the room
                if (endTime.before(currentDate)) {
                    ConferenceRoom room = conferenceRooms.get(booking.getRoomId());
                    if (room != null) {
                        room.status = true; // Set the status of the room to free
                        iterator.remove(); // Remove the booking from the bookings map
                    }
                }
            } catch (ParseException e) {
                // Handle parsing exception
                System.out.println("An error occurred while parsing the end time of a booking.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("+--------------------------------------+");
            System.out.println("|   Welcome to Room Booking System!    |");
            System.out.println("+--------------------------------------+");
            if (!isAdminLoggedIn && !isUserLoggedIn) {
                System.out.println("| 1. Admin                               |");
                System.out.println("| 2. User                                |");
                System.out.println("| 3. Exit                                |");
                System.out.println("+--------------------------------------+");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        if (authenticateAdmin()) {
                            isAdminLoggedIn = true;
                        }
                        break;
                    case 2:
                        if (authenticateUser()) {
                            isUserLoggedIn = true;
                        }
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                }
            } else if (isAdminLoggedIn) {
                // Admin options
                System.out.println("+--------------------------------------+");
                System.out.println("|              Admin Options           |");
                System.out.println("+--------------------------------------+");
                System.out.println("| 1. Add New Conference Rooms          |");
                System.out.println("| 2. List All Conference Rooms         |");
                System.out.println("| 3. View Bookings                     |");
                System.out.println("| 4. Logout                            |");
                System.out.println("+--------------------------------------+");
                System.out.print("Enter your choice: ");
                int adminOption = Integer.parseInt(scanner.nextLine());
                switch (adminOption) {
                    case 1:
                        addConferenceRoom();
                        break;
                    case 2:
                        adminListConferenceRooms();
                        break;
                    case 3:
                        viewBookings();
                        break;
                    case 4:
                        isAdminLoggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } else if (isUserLoggedIn) {
                // User options
                System.out.println("+--------------------------------------+");
                System.out.println("|              User Options            |");
                System.out.println("+--------------------------------------+");
                System.out.println("| 1. List Conference Rooms             |");
                System.out.println("| 2. Book a Slot                       |");
                System.out.println("| 3. Logout                            |");
                System.out.println("+--------------------------------------+");
                System.out.print("Enter your choice: ");
                int userOption = Integer.parseInt(scanner.nextLine());
                switch (userOption) {
                    case 1:
                        listConferenceRooms();
                        break;
                    case 2:
                        bookSlot();
                        break;
                    case 3:
                        isUserLoggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }

            // Free expired conference rooms at the end of each iteration
            freeExpiredRooms();
        }
    }
}
