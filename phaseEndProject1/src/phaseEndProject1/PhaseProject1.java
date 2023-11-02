package phaseEndProject1;

import java.util.*;

// Exception classes
class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}

class ExcessiveRentException extends Exception {
	public ExcessiveRentException(String message) {
		super(message);
	}
}

class InvalidDetailsException extends Exception {
	public InvalidDetailsException(String message) {
		super(message);
	}
}

// Constants
class KeyConstraints {
	public static final String userName = "admin";
	public static final String passWord = "admin123";
}

// Two-Factor Authentication
class TwoFA {
	public void security(String username, String password) throws InvalidDetailsException {
		if (KeyConstraints.userName.equals(username) && KeyConstraints.passWord.equals(password)) {
			System.out.println("\nAccess Granted: \n");
		} else {
			throw new InvalidDetailsException("Incorrect Details");
		}
	}
}

// Camera class
class Camera {
	int id;
	private String brand;
	private String model;
	private double rentalAmount;
	private boolean available;

	public Camera(int id, String brand, String model, double rentalAmount) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.rentalAmount = rentalAmount;
		this.available = true;
	}

	public int getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public double getRentalAmount() {
		return rentalAmount;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}

// CameraRentalApp class
class CameraRentalApp {
	private List<Camera> cameraList;
	 private List<Camera> rentedCameras;
	private double walletAmount;

	public CameraRentalApp() {
		cameraList = new ArrayList<>();
		rentedCameras = new ArrayList<>();

		walletAmount = 1500.0;
	}

	public void addCamera(Camera camera) {

		cameraList.add(camera);
	}

	public void removeCamera(int id) {
	    boolean found = false;
	    Iterator<Camera> iterator = rentedCameras.iterator();
	    while (iterator.hasNext()) {
	        Camera camera = iterator.next();
	        if (camera.getId() == id) {
	            iterator.remove();
	            found = true;
	            break;
	        }
	    }
	    if (found) {
	        for (Camera camera : cameraList) {
	            if (camera.getId() == id) {
	                camera.setAvailable(true);
	                break;
	            }
	        }
	        reassignCameraIDs();
	        System.out.println("Camera removed from your rented cameras list.");
	    } else {
	        System.out.println("Camera not found in your rented cameras list.");
	    }
	}



	
	private void reassignCameraIDs() {
		for (int i = 0; i < cameraList.size(); i++) {
			cameraList.get(i).id = i + 1;
		}
	}


	public void rentCamera(int id) throws InsufficientBalanceException, ExcessiveRentException {
		Camera rentedCamera = null;
		for (Camera camera : cameraList) {
			if (camera.getId() == id) {
				rentedCamera = camera;
				break;
			}
		}
		if (rentedCamera == null) {
			System.out.println("Camera not found.");
			return;
		}
		if (!rentedCamera.isAvailable()) {
			throw new ExcessiveRentException("Camera is already rented.");
		}
		if (rentedCamera.getRentalAmount() > walletAmount) {
			throw new InsufficientBalanceException("Insufficient wallet balance.");
		}
		rentedCamera.setAvailable(false);
		walletAmount -= rentedCamera.getRentalAmount();
		 rentedCameras.add(rentedCamera);
	}
	
	public void viewMyCameras() {
		List<Camera> rentedCameras = new ArrayList<>();
		for (Camera camera : cameraList) {
			if (!camera.isAvailable()) {
				rentedCameras.add(camera);
			}
		}
		if (rentedCameras.isEmpty()) {
			System.out.println("You haven't rented any cameras yet.");
		} else {
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("RENTED CAMERA ID\tBRAND\t\tMODEL\t\tRENTAL AMOUNT");
			System.out.println("--------------------------------------------------------------------------");

			for (Camera camera : rentedCameras) {
				System.out.printf("%-15d\t%-20s\t%-20s\t%.2f\n",
						camera.getId(), camera.getBrand(),
						camera.getModel(), camera.getRentalAmount());
			}
		}
	}


	public void viewAllCameras() {
		if (cameraList.isEmpty()) {
			System.out.println("Your camera list is empty. ");
		} else {
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("CAMERA ID\tBRAND\t\tMODEL\t\tRENTAL AMOUNT\tSTATUS");
			System.out.println("--------------------------------------------------------------------------");

			for (Camera camera : cameraList) {
				System.out.printf("%-10d\t%-15s\t%-15s\t%.2f\t\t%s\n",
						camera.getId(), camera.getBrand(),
						camera.getModel(), camera.getRentalAmount(),
						camera.isAvailable() ? "Available" : "Rented");
			}
		}
	}

	public void viewWalletBalance() {
		System.out.println("Current wallet balance: " + walletAmount);
	}

	public void depositToWallet(double amount) {
		walletAmount += amount;
		System.out.println("Amount deposited successfully.");
	}
}

public class PhaseProject1 {
	public static void main(String[] args) throws InvalidDetailsException {


		CameraRentalApp app = new CameraRentalApp();

		Camera camera1 = new Camera(1, "Canon", "Mark IV", 3650);
		Camera camera2 = new Camera(2, "Nikon", "D850", 8760);
		Camera camera3 = new Camera(3, "Sony", "A7 III", 4500);
		Camera camera4 = new Camera(4, "Samsung", "DDR", 6700);
		Camera camera5 = new Camera(5, "Sony", "A8 II", 4599);
		Camera camera6 = new Camera(6, "Cannon", "IMX100", 9000);


		app.addCamera(camera1);
		app.addCamera(camera2);
		app.addCamera(camera3);
		app.addCamera(camera4);
		app.addCamera(camera5);
		app.addCamera(camera6);


		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n************************************");
		
		System.out.println("\nDeveloped by K Teja Shanti Kumar\n");
		
		System.out.println("*************************************");

		System.out.println("\nWELCOME TO CAMERA RENTAL APP\n\nPLEASE LOGIN TO CONTINUE - \n");

		System.out.println("USERNAME: ");
		String username = sc.next();

		System.out.println("PASSWORD: ");
		String password = sc.next();

		TwoFA fa = new TwoFA();

		fa.security(username, password);

		Scanner scanner = new Scanner(System.in);


		boolean running = true;

		while (running) {
			System.out.println("1. My camera");
			System.out.println("2. Rent a camera");
			System.out.println("3. View all cameras");
			System.out.println("4. My wallet");
			System.out.println("5. Exit");
			System.out.print("\nEnter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				boolean cameraMenuRunning = true;
				while (cameraMenuRunning) {
					System.out.println("1. Add a camera");
					System.out.println("2. Remove a camera");
					System.out.println("3. View my cameras");
					System.out.println("4. Go to the previous menu");
					System.out.print("\nEnter your choice: ");
					int cameraChoice = scanner.nextInt();

					switch (cameraChoice) {
					case 1:
						// Add a camera
						System.out.print("Enter the camera ID: ");
						int id = scanner.nextInt();
						System.out.print("Enter the brand: ");
						String brand = scanner.next();
						System.out.print("Enter the model: ");
						String model = scanner.next();
						System.out.print("Enter the rental amount: ");
						double rentalAmount = scanner.nextDouble();
						Camera camera = new Camera(id, brand, model, rentalAmount);
						app.addCamera(camera);
						System.out.println("Camera added successfully.");
						break;
					case 2:
						// Remove a camera
						app.viewMyCameras();
						System.out.print("Enter the camera ID to remove: ");
						int removeId = scanner.nextInt();
						app.removeCamera(removeId);
						break;
					case 3:
						// View your cameras
						app.viewMyCameras();
						break;
					case 4:
						cameraMenuRunning = false;
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
					}
				}
				break;
			case 2:
				// Rent a camera
				System.out.println("Following is the list of available cameras: ");
				app.viewAllCameras();
				System.out.print("Enter the camera ID to rent: ");
				int rentId = scanner.nextInt();
				try {
					app.rentCamera(rentId);
					System.out.println("Camera rented successfully.");
				} catch (InsufficientBalanceException e) {
					System.out.println("Failed to rent camera: " + e.getMessage());
				} catch (ExcessiveRentException e) {
					System.out.println("Failed to rent camera: " + e.getMessage());
				}
				break;
			case 3:
				// View all cameras
				app.viewAllCameras();
				break;
			case 4:
				// View wallet balance and deposit
				app.viewWalletBalance();

				Scanner ch = new Scanner(System.in);

				System.out.println("Want to deposit money? (Y/N)");

				String ans = ch.next();

				if (ans.equalsIgnoreCase("Y")) {
					System.out.println("Enter amount to deposit:");
					double amount = ch.nextDouble();
					app.depositToWallet(amount);
				} else {
					continue;
				}

				break;
			case 5:

				running = false; 
				System.out.println("Thanks for using Rental Camera App");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}

		}
	}
}

