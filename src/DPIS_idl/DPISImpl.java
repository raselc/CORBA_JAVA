package DPIS_idl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DPISImpl extends DPISPOA {
	public static Map<String, String> SPVMRecord = Collections
			.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> SPLRecord = Collections
			.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> SPBRecord = Collections
			.synchronizedMap(new HashMap<String, String>(1000));
	int SPLserverPort = 3000;
	int SBPserverPort = 4000;
	int SPVMserverPort = 5000;
	int SPVMtransferPort = 6000;
	int SPLtransferPort = 7000;
	int SPBtransferPort = 8000;

	/** Generates universal record ID **/
	public synchronized String generateId() {
		// System.out.println("entered id");
		String number = "";
		String id = "";
		String message1 = "";
		String message2 = "";
		String message3 = "";

		int size = 1;
		int length;

		DatagramSocket DPISSocket = null;

		byte[] buffer = new byte[10];
		String send = "send count";
		try {
			DPISSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			byte[] m = send.getBytes();

			DatagramPacket request1 = new DatagramPacket(m, send.length(),
					aHost, SPVMserverPort);
			DPISSocket.send(request1);
			DatagramPacket reply1 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply1);
			message1 = new String(reply1.getData());

			DatagramPacket request2 = new DatagramPacket(m, send.length(),
					aHost, SPLserverPort);
			DPISSocket.send(request2);
			DatagramPacket reply2 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply2);
			message2 = new String(reply2.getData());

			DatagramPacket request3 = new DatagramPacket(m, send.length(),
					aHost, SBPserverPort);
			DPISSocket.send(request3);
			DatagramPacket reply3 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply3);
			message3 = new String(reply3.getData());

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (DPISSocket != null)
				DPISSocket.close();
		}

		size += Integer.parseInt(message1.replaceFirst(".*?(\\d+).*", "$1"));
		size += Integer.parseInt(message2.replaceFirst(".*?(\\d+).*", "$1"));
		size += Integer.parseInt(message3.replaceFirst(".*?(\\d+).*", "$1"));
		// System.out.println(size);

		/*
		 * size += SPVMRecord.size(); size += SPLRecord.size(); size +=
		 * SBPRecord.size();
		 */

		id = String.valueOf(size);
		length = 5 - id.length();
		for (int i = 0; i < length; i++) {
			number = number + "0";
		}
		number = number + id;
		// System.out.println("id generated");
		return number;
	}

	/** Finds the station from id **/
	public static String getStation(String id) {
		// System.out.println("get station");
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		// System.out.println("found station");
		return station;
	}

	/** Finds the hash key from value **/
	static String getKey(Map<String, String> record, String value) {
		// System.out.println("find key");
		String key = null;
		String hashValue = null;
		for (Map.Entry<String, String> entry : record.entrySet()) {
			StringTokenizer tokens = new StringTokenizer(entry.getValue());
			hashValue = tokens.nextToken();
			if ((value == null && entry.getValue() == null)
					|| (value != null && value.equals(hashValue))) {
				key = entry.getKey();
				break;
			}
		}
		// System.out.println("found key");
		return key;
	}

	/** Creates Logs **/
	public static void logFile(String fileName, String Operation, String badgeId)
			throws SecurityException {
		// System.out.println("loggin file");
		fileName = fileName + "ServerLog.txt";
		File log = new File(fileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		try {
			if (!log.exists()) {
			}
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(badgeId + " " + Operation + " "
					+ dateFormat.format(date));
			bufferedWriter.newLine();
			bufferedWriter.close();
			// System.out.println("logged file");
		} catch (IOException e) {
			System.out.println("COULD NOT LOG!!");
		}
	}

	/** SPVM UDP server **/
	public class SPVMUdpServer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("SPVM UDP Server Started");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPVMserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPVM: "
							+ String.valueOf(SPVMRecord.size()) + " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	/** SPL UDP server **/
	public class SPLUdpServer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("SPL UDP Server Started");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPLserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPL: " + String.valueOf(SPLRecord.size())
							+ " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	/** SBP UDP server **/
	public class SBPUdpServer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("SBP UDP Server Started");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SBPserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPB: " + String.valueOf(SPBRecord.size())
							+ " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	/** SPVM transfer **/
	public class SPVMtransferServer implements Runnable {
		public synchronized void run() {
			// TODO Auto-generated method stub
			System.out.println("transferServer");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPVMtransferPort);
				byte[] buffer = new byte[100];
				while (true) {
					String message = "";
					String key = "";
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					System.out.println(new String(request.getData()));
					StringTokenizer tokens = new StringTokenizer(new String(request.getData()));
					key = tokens.nextToken();
					System.out.println("key:" + key);
					while (tokens.hasMoreElements()) {
						message = message +" "+ tokens.nextToken();
					}
					System.out.println("record:"+message);
					SPVMRecord.put(key, message.trim());
					message = "transferred";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
					System.out.println(SPVMRecord.values());
					
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	/** SPL transfer **/
	public class SPLtransferServer implements Runnable {
		
		public synchronized void run() {
			// TODO Auto-generated method stub
			System.out.println("transferServer");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPLtransferPort);
				byte[] buffer = new byte[100];
				while (true) {
					String message = "";
					String key = "";
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					System.out.println(new String(request.getData()));
					StringTokenizer tokens = new StringTokenizer(new String(request.getData()));
					key = tokens.nextToken();
					System.out.println("key:" + key);
					while (tokens.hasMoreElements()) {
						message = message +" "+ tokens.nextToken();
					}
					System.out.println("record:"+message);
					SPLRecord.put(key, message.trim());
					message = "transferred";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
					System.out.println(SPLRecord.values());
				}
			} catch (SocketException e) {
//				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}
	}

	/** SBP transfer **/
	public class SBPtransferServer implements Runnable {

		public synchronized void run() {
			// TODO Auto-generated method stub
			System.out.println("transferServer");
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPBtransferPort);
				byte[] buffer = new byte[100];
				while (true) {
					String message = "";
					String key = "";
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					System.out.println(new String(request.getData()));
					StringTokenizer tokens = new StringTokenizer(new String(request.getData()));
					key = tokens.nextToken();
					System.out.println("key:" + key);
					while (tokens.hasMoreElements()) {
						message = message +" "+ tokens.nextToken();
					}
					System.out.println("record:"+message);
					SPBRecord.put(key, message.trim());
					message = "transferred";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
					System.out.println(SPBRecord.values());
					
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}
	}

	
	/** starts the UDP servers **/
	public void runSPVMServer() {
		Thread t1 = new Thread(new SPVMUdpServer());
		Thread t4 = new Thread(new SPVMtransferServer());
		
		t1.start();
		t4.start();
		
	}
	
	public void runSPLServer() {
		
		Thread t2 = new Thread(new SPLUdpServer());
		Thread t5 = new Thread(new SPLtransferServer());
		
		t2.start();
		t5.start();
	}
	
	public void runSPBServer() {
		
		Thread t3 = new Thread(new SBPUdpServer());
		Thread t6 = new Thread(new SBPtransferServer());

		t3.start();
		t6.start();
	}

	/** Create Criminal record **/
	@Override
	public boolean createCRecord(String firstName, String lastName,
			String description, String status, String badgeId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// System.out.println("creating C record");
		Boolean CreateRecordFlag = false;
		String recordId = "";
		String station;
		station = getStation(badgeId);
		switch (station) {
		case "SPVM": {
			// synchronized(SPVMRecord)
			// {
			// recordId = "CR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "CR" + generateId();
			SPVMRecord.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + description + " " + status);
			CreateRecordFlag = true; // successful.
			logFile("SPVM", "Criminal created", badgeId);
			System.out.println(SPVMRecord.values());
			// }
		}
			break;
		case "SPL": { // Longueuil
			// synchronized(SPLRecord)
			// {
			// recordId = "CR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "CR" + generateId();
			SPLRecord.put(lastName, recordId + " " + firstName + " " + lastName
					+ " " + description + " " + status);
			CreateRecordFlag = true; // successful
			logFile("SPL", "Criminal created", badgeId);
			System.out.println(SPLRecord.values());
			// }
		}
			break;
		case "SPB": { // Brossard
			// synchronized(SBPRecord)
			// {
			// recordId = "CR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "CR" + generateId();
			SPBRecord.put(lastName, recordId + " " + firstName + " " + lastName
					+ " " + description + " " + status);
			CreateRecordFlag = true; // successful
			logFile("SPB", "Criminal created", badgeId);
			System.out.println(SPBRecord.values());
			// }
		}
			break;

		default: {

		}
			break;
		}
		// System.out.println("created c record");
		return CreateRecordFlag; // return value
	}

	/** Create Missing record **/
	@Override
	public boolean createMRecord(String firstName, String lastName,
			String address, String lastdate, String lastaddress, String status,
			String badgeId) {
		// TODO Auto-generated method stub
		// System.out.println("creating m record");
		Boolean CreateRecordFlag = false;
		String recordId = "";
		String station;
		station = getStation(badgeId);
		switch (station) {
		case "SPVM": { // Montreal
			// synchronized(SPVMRecord)
			// {
			// recordId = "MR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "MR" + generateId();
			SPVMRecord.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + address + " " + " " + lastdate + " "
					+ lastaddress + " " + status);
			CreateRecordFlag = true; // successful.
			logFile("SPVM", "Missing created", badgeId);
			System.out.println(SPVMRecord.values());
			// }
		}
			break;
		case "SPL": { // Longueuil
			// synchronized(SPLRecord)
			// {
			// recordId = "MR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "MR" + generateId();
			SPLRecord.put(lastName, recordId + " " + firstName + " " + lastName
					+ " " + address + " " + " " + lastdate + " " + lastaddress
					+ " " + status);
			CreateRecordFlag = true; // successful
			logFile("SPL", "Missing created", badgeId);
			System.out.println(SPLRecord.values());
			// }
		}
			break;
		case "SPB": { // Brossard
			// synchronized(SBPRecord)
			// {
			// recordId = "MR" +
			// generateId(String.valueOf(SPVMRecord.size()+1));
			recordId = "MR" + generateId();
			SPBRecord.put(lastName, recordId + " " + firstName + " " + lastName
					+ " " + address + " " + " " + lastdate + " " + lastaddress
					+ " " + status);
			CreateRecordFlag = true; // successful
			logFile("SPB", "Missing created", badgeId);
			System.out.println(SPBRecord.values());
			// }
		}
			break;

		default: {

		}
			break;
		}
		// System.out.println("created m record");
		return CreateRecordFlag; // return value
	}

	/** Get count of stations **/
	@Override
	public String getRecordCounts(String badgeId) {
		// TODO Auto-generated method stub
		// System.out.println("getting count");
		DatagramSocket DPISSocket = null;

		String message = "";
		byte[] buffer = new byte[10];
		String send = "send count";
		try {
			DPISSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			byte[] m = send.getBytes();

			DatagramPacket request1 = new DatagramPacket(m, send.length(),
					aHost, SPVMserverPort);
			DPISSocket.send(request1);
			DatagramPacket reply1 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply1);
			message += new String(reply1.getData());

			DatagramPacket request2 = new DatagramPacket(m, send.length(),
					aHost, SPLserverPort);
			DPISSocket.send(request2);
			DatagramPacket reply2 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply2);
			message += new String(reply2.getData());

			DatagramPacket request3 = new DatagramPacket(m, send.length(),
					aHost, SBPserverPort);
			DPISSocket.send(request3);
			DatagramPacket reply3 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply3);
			message += new String(reply3.getData());

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (DPISSocket != null)
				DPISSocket.close();
		}
		// System.out.println("sent count");
		return message;
	}

	/** Edit Criminal record **/
	@Override
	public boolean editCRecord(String lastName, String recordID,
			String newStatus, String badgeId) {
		// TODO Auto-generated method stub
		// System.out.println("editing record");
		String station;
		station = getStation(badgeId);
		Boolean GetRecordFlag = false;

		switch (station) {
		case "SPVM": { // Montreal
			// synchronized(SPVMRecord)
			// {
			if (SPVMRecord.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						SPVMRecord.get(lastName));
				int i = 0;
				int length = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == length) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				SPVMRecord.remove(lastName);
				SPVMRecord
						.put(lastName, mod_rec.substring(0, mod_rec.length()));
				System.out.println(SPVMRecord.values());
				GetRecordFlag = true; // successful.
				logFile("SPVM", "Record Edited", badgeId);
			} else {
				GetRecordFlag = false;
			}
			// }
		}
			break;
		case "SPL": { // Longueuil
			// synchronized(SPLRecord)
			// {
			if (SPLRecord.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						SPLRecord.get(lastName));
				int i = 0;
				int lngth = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == lngth) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				SPLRecord.remove(lastName);
				SPLRecord.put(lastName, mod_rec.substring(0, mod_rec.length()));
				System.out.println(SPLRecord.values());
				GetRecordFlag = true; // successful.
				logFile("SPL", "Record Edited", badgeId);
			} else {
				GetRecordFlag = false;
			}
			// }
		}
			break;
		case "SPB": { // Brossard
			// synchronized(SBPRecord)
			// {
			if (SPBRecord.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						SPBRecord.get(lastName));
				int i = 0;
				int lngth = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == lngth) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				SPBRecord.remove(lastName);
				SPBRecord.put(lastName, mod_rec.substring(0, mod_rec.length()));
//				System.out.println(SBPRecord.values());
				logFile("SBP", "Record Edited", badgeId);
				GetRecordFlag = true; // successful.
			} else {
				GetRecordFlag = false;
			}
			// }
		}
			break;

		default: {

		}
			break;
		}
		System.out.println("edited record");
		return GetRecordFlag;
	}

	/** Transfer record **/
	@Override
	public boolean transferRecord(String recordID, String badgeId,String remoteStation) {
		
		// System.out.println("transferred record");
		return true; // return value
	}

}
