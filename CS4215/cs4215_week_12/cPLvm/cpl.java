package cPLvm; 

import cPLcompiler.*;
import cVML.*;
import java.io.*;

class cpl {

    static public void main(String[] args) 
    {
	// read name of source file from command line
	String basename=args[0];

        // like "java", the copl VM is called on
        // the base name of the executable.
      
        String cvmlname=basename+".cvml";

        ObjectInputStream ois = null;

        try {
            // create object input stream
            ois = new ObjectInputStream(new FileInputStream(cvmlname));

        } catch (Exception ex) {
            System.out.println("cannot read "+cvmlname);
	    System.out.println("Usage: java cPLvm.cpl <program>");
	    System.exit(1);
        }

        // load serialized instruction array from input stream
        // into instructions
	CompilerResult cr;

        try {
            cr = (CompilerResult)ois.readObject();
	    INSTRUCTION instructions[] = cr.instructionArray;

            // print out instructions
	    //	                  for (int i=0; i < instructions.length; i++)
	    //	                    if (instructions[i] != null) 
	    //	                        System.out.println(i+": "+instructions[i]);

	    try {
	    // run the instructions and print out resulting value
	    // now, we pass both the instructions and p to the machine
	    VM.run(instructions,cr.divisionByZeroAddress,
		   cr.invalidRecordAccessAddress);
	    } catch (Exception ex) {
		System.out.println("exception "+ex+" during execution of "+cvmlname);
	    }
        } catch (Exception ex) {
            System.out.println("cannot read "+cvmlname);
	    System.exit(1);
        }

        try {
            ois.close();
        } catch (Exception ex) {
            System.out.println("cannot close "+cvmlname);
	    System.exit(1);
	};
    }
}
