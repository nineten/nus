package eVM; 

import eVML.*;
import java.io.*;

class epl {

    static public void main(String[] args) 
    {
    	// read name of source file from command line
    	String basename=args[0];

        // like "java", the ePL VM is called on
        // the base name of the executable.
    	
        String evmlname=basename+".evml";

        ObjectInputStream ois = null;

        try {
            // create object input stream
            ois = new ObjectInputStream(new FileInputStream(evmlname));

        } catch (Exception ex) {
            System.out.println("cannot read "+evmlname);
	    System.out.println("Usage: java cs3212.eVM.epl [-rugged] <program>");
	    System.exit(1);
        }

        // load serialized instruction array from input stream
        // into instructions
        INSTRUCTION instructions[] = null;

        try {
            instructions = (INSTRUCTION[])ois.readObject();
        } catch (Exception ex) {
            System.out.println("cannot read "+evmlname);
	    System.exit(1);
        }

        // run the instructions and print out resulting value
	    System.out.println(VM.run(instructions));

        try {
            ois.close();
        } catch (Exception ex) {
            System.out.println("cannot close "+evmlname);
	    System.exit(1);
	};
    }
}
