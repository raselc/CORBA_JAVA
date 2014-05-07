package DPIS_idl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.ORB;

public class DPISClient2 implements Runnable {

	/**
	 * @param args
	 */

	String badgeId = "";
	String lastName = "";
	String firstName = "";
	String description = "";
	String status = "";
	String address = "";
	String lastDate = "";
	String lastLocation = "";
	String recordId = "";
	String message = "";
	String remoteStation = "";
	String operation = "";
	private String[] args;

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

	// Get station from id
	public static String getStation(String id) {
		// System.out.println("get station");
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		// System.out.println("found station");
		return station;
	}

	public DPISClient2(String badgeId, String firstName, String lastName,
			String description, String address, String lastDate,
			String lastLocation, String status, String recordID,
			String remoteStation, String operation) {
		this.badgeId = badgeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.address = address;
		this.lastDate = lastDate;
		this.lastLocation = lastLocation;
		this.status = status;
		this.recordId = recordID;
		this.remoteStation = remoteStation;
		this.operation = operation;
	}

	public void run() {
		try {
			String station = getStation(badgeId);
/*			ORB orb = ORB.init(args, null);
			BufferedReader br = new BufferedReader(new FileReader("ior.txt"));
			String ior = br.readLine();
			br.close();
			org.omg.CORBA.Object o = orb.string_to_object(ior);
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
			
			
			switch (operation) {
			case "cr": {
				// System.out.println("CRecord Operation");
				Boolean result = false;
				try {
					if (station.equals("SPVM")) {
						result = spvmObj.createCRecord(firstName, lastName,
								description, status, badgeId);
					} else if (station.equals("SPL")) {
						result = splObj.createCRecord(firstName, lastName,
								description, status, badgeId);
					} else if (station.equals("SPB")) {
						result = spbObj.createCRecord(firstName, lastName,
								description, status, badgeId);
					}

					if (result) {
						System.out.println("Criminal created");
						logFile(badgeId, "Criminal created");
					} else {
						System.out.println("Criminal not created");
						logFile(badgeId, "Criminal not created");
					}
				} catch (Exception ex) {
					System.out.println("Criminal Exception: " + ex);
				}
			}
				break;
			case "mr": {
				// System.out.println("MRecord Operation");
				Boolean result = false;
				try {
					if (station.equals("SPVM")) {
						result = spvmObj.createMRecord(firstName, lastName,
								address, lastDate, lastLocation, status,
								badgeId);
					} else if (station.equals("SPL")) {
						result = splObj.createMRecord(firstName, lastName,
								address, lastDate, lastLocation, status,
								badgeId);
					} else if (station.equals("SPB")) {
						result = spbObj.createMRecord(firstName, lastName,
								address, lastDate, lastLocation, status,
								badgeId);
					}

					if (result == true) {
						System.out.println("Missing Created");
						logFile(badgeId, "Missing created");
					} else {
						System.out
								.println("Missing Not Created Due to Unexpected Error");
						logFile(badgeId, "Missing not created");
					}
				} catch (Exception ex) {
					System.out.println("Missing Exception: " + ex);
				}
			}
				break;
			case "edt": {
				// System.out.println("Edit Operation");
				Boolean result = false;
				try {
					if (station.equals("SPVM")) {
						result = spvmObj.editCRecord(lastName, recordId,
								status, badgeId);
					} else if (station.equals("SPL")) {
						result = spvmObj.editCRecord(lastName, recordId,
								status, badgeId);
					} else if (station.equals("SPB")) {
						result = spvmObj.editCRecord(lastName, recordId,
								status, badgeId);
					}

					if (result == true) {
						logFile(badgeId, "Criminal edit successful");
						System.out.println("Edit Succesful");
					} else {
						logFile(badgeId, "Edit unsuccessful");
						System.out.println("Edit unsuccessful");
					}
				} catch (Exception ex) {
					System.out.println("Edit Exception: " + ex);
				}
			}
				break;
			case "viw": {
				try {
					if (station.equals("SPVM")) {
						spvmObj.getRecordCounts(badgeId);
					} else if (station.equals("SPL")) {
						splObj.getRecordCounts(badgeId);
					} else if (station.equals("SPB")) {
						spbObj.getRecordCounts(badgeId);
					}

					logFile(badgeId, "record viewed");
					System.out.println("record viewed Succesful");

				} catch (Exception ex) {
					System.out.println("Edit Exception: " + ex);
				}
			}
				break;
			case "trans": {
				Boolean result = false;
				try {
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
						System.out.println("Record Transferred");
						logFile(badgeId, "Record transferred");
					} else {
						System.out.println("Record Not Transferred");
						logFile(badgeId, "Record Not Transferred");
					}

				} catch (Exception ex) {
					System.out.println("Edit Exception: " + ex);
				}
			}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("Servers Binding Exception: " + e);
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// create crecord
		Thread client1 = new Thread(new DPISClient2("SPVM1001", "fname1",
				"lname1", "descrip1", "", "", "", "free", "", "", "cr"));
		Thread client2 = new Thread(new DPISClient2("SPL1001", "fname2",
				"lname2", "descrip2", "", "", "", "free", "", "", "cr"));
		Thread client3 = new Thread(new DPISClient2("SPB1001", "fname3",
				"lname3", "descrip3", "", "", "", "free", "", "", "cr"));
		Thread client4 = new Thread(new DPISClient2("SPVM1002", "fname4",
				"lname4", "descrip4", "", "", "", "free", "", "", "cr"));

		// create mrecord
		Thread client5 = new Thread(new DPISClient2("SPVM1002", "fname5",
				"lname5", "", "address5", "121112", "lastloc1", "notfound", "",
				"", "mr"));
		Thread client6 = new Thread(new DPISClient2("SPL1003", "fname6",
				"lname6", "", "address6", "121112", "lastloc1", "found", "",
				"", "mr"));
		Thread client7 = new Thread(new DPISClient2("SPB1004", "fname7",
				"lname7", "", "address7", "121112", "lastloc1", "notfound", "",
				"", "mr"));
		Thread client8 = new Thread(new DPISClient2("SPVM1001", "fname8",
				"lname8", "", "address8", "121112", "lastloc1", "found", "",
				"", "mr"));

		// edit crecord
		Thread client9 = new Thread(new DPISClient2("SPVM0001", "", "lname",
				"", "", "", "", "captured", "CR00001", "", "edt"));
		Thread client10 = new Thread(new DPISClient2("SPL0002", "", "lname10",
				"", "", "", "", "captured", "CR0002", "", "edt"));
		Thread client11 = new Thread(new DPISClient2("SPB0003", "", "lname11",
				"", "", "", "", "captured", "CR00003", "", "edt"));
		Thread client12 = new Thread(new DPISClient2("SPVM0004", "", "lname12",
				"", "", "", "", "captured", "CR00004", "", "edt"));

		// view record
		Thread client13 = new Thread(new DPISClient2("SPVM999", "", "", "", "",
				"", "", "", "", "", "viw"));
		Thread client14 = new Thread(new DPISClient2("SPL0001", "", "", "", "",
				"", "", "", "", "", "viw"));
		Thread client15 = new Thread(new DPISClient2("SPB2001", "", "", "", "",
				"", "", "", "", "", "viw"));
		Thread client16 = new Thread(new DPISClient2("SPVM3001", "", "", "",
				"", "", "", "", "", "", "viw"));

		// transfer record
		Thread client17 = new Thread(new DPISClient2("SPVM1001", "", "", "",
				"", "", "", "", "CR00001", "SPL", "trans"));
		Thread client18 = new Thread(new DPISClient2("SPB1004", "", "", "", "",
				"", "", "", "CR10001", "SPB", "trans"));
		Thread client19 = new Thread(new DPISClient2("SPL1003", "", "", "", "",
				"", "", "", "CR10001", "SPL", "trans"));
		Thread client20 = new Thread(new DPISClient2("SPVM1031", "", "", "",
				"", "", "", "", "CR10001", "SPVM", "trans"));

		client1.start();
		client2.start();
		client3.start();
		client4.start();

		client5.start();
		client6.start();
		client7.start();
		client8.start();

		client9.start();
		client10.start();
		client11.start();
		client12.start();

		client13.start();
		client14.start();
		client15.start();
		client16.start();

		client17.start();
		client18.start();
		client19.start();
		client20.start();
	}

}
