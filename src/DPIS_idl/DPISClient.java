package DPIS_idl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

public class DPISClient {

	/**
	 * @param args
	 */
	/** displays Menu **/
	public static void showMenu() {
		System.out.println("Please select an option (1-6)");
		System.out.println("1. Create a new Criminal Record.");
		System.out.println("2. Create a new Missing Record.");
		System.out.println("3. Edit an existing Criminal Record.");
		System.out.println("4. View Total Records.");
		System.out.println("5. Transfer Record");
		System.out.println("6. Exit");
	}

	/** LOGing is done here **/
	public static void logFile(String fileName, String Operation)
			throws SecurityException {
		fileName = fileName + "Log.txt";
		File log = new File(fileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Operation + " " + dateFormat.format(date));
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("COULD NOT LOG!!");
		}
	}

	/** Verifies badgeID **/
	public static boolean verifyId(String id) {
		Pattern p1 = Pattern.compile("SPL[0-9]{4}");
		Pattern p2 = Pattern.compile("SPB[0-9]{4}");
		Pattern p3 = Pattern.compile("SPVM[0-9]{4}");
		Matcher m1 = p1.matcher(id);
		Matcher m2 = p2.matcher(id);
		Matcher m3 = p3.matcher(id);
		if (m1.matches() || m2.matches() || m3.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/** Verifies Criminal Record id **/
	public static boolean verifyRecordId(String id) {
		Pattern p1 = Pattern.compile("CR[0-9]{5}");
		Matcher m1 = p1.matcher(id);
		if (m1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	// get station from id
	public static String getStation(String id) {
		// System.out.println("get station");
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		// System.out.println("found station");
		return station;
	}

	/** Main function **/
	public static void main(String[] args) throws IOException {
/*		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader("ior.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("splior.txt"));
		BufferedReader br3 = new BufferedReader(new FileReader("spbior.txt"));
		String spvmior = br.readLine();
		br.close();
		br2.close();
		br3.close();

		org.omg.CORBA.Object o = orb.string_to_object(spvmior);

		DPIS spvmObj = DPISHelper.narrow(o);
		DPIS splObj = DPISHelper.narrow(o);
		DPIS spbObj = DPISHelper.narrow(o);
*/
		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader("spvm.txt"));
		String spvm = br.readLine();
		br.close();
		org.omg.CORBA.Object o = orb.string_to_object(spvm);
		DPIS spvmObj = DPISHelper.narrow(o);
		
		BufferedReader br1 = new BufferedReader(new FileReader("spl.txt"));
		String spl = br1.readLine();
		br1.close();
		org.omg.CORBA.Object oA = orb.string_to_object(spl);
		DPIS splObj = DPISHelper.narrow(oA);
		
		BufferedReader br2 = new BufferedReader(new FileReader("spb.txt"));
		String spb = br2.readLine();
		br2.close();
		org.omg.CORBA.Object oB = orb.string_to_object(spb);
		DPIS spbObj = DPISHelper.narrow(oB);
		
		String badgeId, lastName, firstName, description, status, address, lastDate, lastLocation, recordId, message = null, remoteStation;
		int choice = 0;
		boolean result;

		Scanner input = new Scanner(System.in);
		System.out.println("\n****Welcome to Police Information System****\n");
		while (true) {
			System.out.println("Enter Your Badge ID:");
			badgeId = input.next();
			if (verifyId(badgeId) == true) {
				showMenu();
				while (true) {
					Boolean valid = false;
					result = false;
					while (!valid) {
						try {
							choice = input.nextInt();
							valid = true;
						} catch (Exception e) {
							System.out
									.println("Invalid Input, please enter an Integer");
							valid = false;
							input.nextLine();
						}
					}
					String station = "";
					station = getStation(badgeId);

					switch (choice) {
					case 1: // creates CRecord
						System.out.println("Enter First name");
						firstName = input.next();
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter Description");
						description = input.next();
						System.out.println("Enter Status");
						status = input.next();

						if (status.equals("captured") || status.equals("free")) {
							if (station.equals("SPVM")) {
								result = spvmObj.createCRecord(firstName,
										lastName, description, status, badgeId);
							} else if (station.equals("SPL")) {
								result = splObj.createCRecord(firstName,
										lastName, description, status, badgeId);
							} else if (station.equals("SPB")) {
								result = spbObj.createCRecord(firstName,
										lastName, description, status, badgeId);
							}
							if (result == true)
								logFile(badgeId, "Created criminal successful");
							else
								logFile(badgeId,
										"Created criminal unsuccessful");
							showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 2: // Creates MRecord
						System.out.println("Enter First name");
						firstName = input.next();
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter address");
						address = input.next();
						System.out.println("Enter last date seen");
						lastDate = input.next();
						System.out.println("Enter Last Location");
						lastLocation = input.next();
						System.out.println("Enter Status");
						status = input.next();
						if (status.equals("found") || status.equals("notfound")) {
							if (station.equals("SPVM")) {
								result = spvmObj.createMRecord(firstName,
										lastName, address, lastDate,
										lastLocation, status, badgeId); // MR
																		// create
							} else if (station.equals("SPL")) {
								result = splObj.createMRecord(firstName,
										lastName, address, lastDate,
										lastLocation, status, badgeId); // MR
																		// create
							} else if (station.equals("SPB")) {
								result = spbObj.createMRecord(firstName,
										lastName, address, lastDate,
										lastLocation, status, badgeId); // MR
																		// create
							}

							if (result == true)
								logFile(badgeId, "Created missing successful");
							else
								logFile(badgeId, "Created missing unsuccessful");
							showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 3: // Edit Crecord
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter Record ID");
						recordId = input.next();
						System.out.println("Enter Status");
						status = input.next();
						if (verifyRecordId(recordId) == false) {
							System.out.println("Invalid ID format");
							showMenu();
							break;
						}
						if (status.equals("captured") || status.equals("free")) {
							if (station.equals("SPVM")) {
								result = spvmObj.editCRecord(lastName,
										recordId, status, badgeId);
							} else if (station.equals("SPL")) {
								result = spvmObj.editCRecord(lastName,
										recordId, status, badgeId);
							} else if (station.equals("SPB")) {
								result = spvmObj.editCRecord(lastName,
										recordId, status, badgeId);
							}

							if (result == true)
								logFile(badgeId, "Edit criminal successful");
							else
								logFile(badgeId, "Edit criminal unsuccessful");
							showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 4: // Displays record count
						if (station.equals("SPVM")) {
							message = spvmObj.getRecordCounts(badgeId);
						} else if (station.equals("SPL")) {
							message = splObj.getRecordCounts(badgeId);
						} else if (station.equals("SPB")) {
							message = spbObj.getRecordCounts(badgeId);
						}

						System.out.println(message);
						logFile(badgeId, "viewd record successful");
						showMenu();
						break;
					case 5: // transfers record
						System.out.println("Enter Record ID:");
						recordId = input.next();
						System.out.println("Enter Remote Station:");
						remoteStation = input.next();
						if (verifyRecordId(recordId) == false) {
							System.out.println("Invalid ID format");
							showMenu();
							break;
						}
						if (station.equals("SPVM")) {
							result = spvmObj.transferRecord(recordId, badgeId,
									remoteStation);
						} else if (station.equals("SPL")) {
							result = spvmObj.transferRecord(recordId, badgeId,
									remoteStation);
						} else if (station.equals("SPB")) {
							result = spvmObj.transferRecord(recordId, badgeId,
									remoteStation);
						}

						if (result == true) {
							// System.out.println("Transfer Succesfull");
							logFile(badgeId, "Transfer Succesfull");
						} else {
							// System.out.println("Transfer Failed");
							logFile(badgeId, "Transfer Failed");
						}
						showMenu();
						break;
					case 6: // exit
						System.exit(0);
					default:
						System.out.println("Invalid Input, please try again.");
					}
				}
			} else
				System.out.println("invalid ID");
		}
	}
}
