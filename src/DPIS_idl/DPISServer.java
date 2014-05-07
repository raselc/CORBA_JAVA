package DPIS_idl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class DPISServer {

	public static void main(String[] args) throws InvalidName,
			ServantAlreadyActive, WrongPolicy, ObjectNotActive,
			FileNotFoundException, AdapterInactive, ObjectAlreadyActive {
		// TODO Auto-generated method stub
/*		ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb
				.resolve_initial_references("RootPOA"));
		DPISImpl DPISobj = new DPISImpl();
		DPISobj.runServer();
		
		byte[] id = rootPOA.activate_object(DPISobj);
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
		String spvmior = orb.object_to_string(ref);
		PrintWriter file = new PrintWriter("ior.txt");
		file.println(spvmior);
		file.close();
		System.out.println("The Server is UP and Running");
		rootPOA.the_POAManager().activate();
		orb.run();
*/
		ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb
				.resolve_initial_references("RootPOA"));
		
		
		byte[] spvm = "1".getBytes();
   		DPISImpl montreal = new DPISImpl();
   		montreal.runSPVMServer();
		rootPOA.activate_object_with_id(spvm, montreal);
		org.omg.CORBA.Object refSPVM = rootPOA.id_to_reference(spvm);
		String ior_spvm = orb.object_to_string(refSPVM);
		PrintWriter file = new PrintWriter("spvm.txt");
		file.println(ior_spvm);
		file.close();

		byte[] spl= "2".getBytes();
		DPISImpl longuiel = new DPISImpl();
		longuiel.runSPLServer();
		rootPOA.activate_object_with_id(spl, longuiel);
		org.omg.CORBA.Object refSPL = rootPOA.id_to_reference(spl);
		String ior_spl = orb.object_to_string(refSPL);
		file = new PrintWriter("spl.txt");
		file.println(ior_spl);
		file.close();

		byte[] spb= "3".getBytes();
		DPISImpl brossard = new DPISImpl();
		brossard.runSPBServer();
		rootPOA.activate_object_with_id(spb, brossard);
		org.omg.CORBA.Object refSPB = rootPOA.id_to_reference(spb);
		String ior_spb = orb.object_to_string(refSPB);
		file = new PrintWriter("spb.txt");
		file.println(ior_spb);
		file.close();
		
		rootPOA.the_POAManager().activate();
		orb.run();
	}

}
