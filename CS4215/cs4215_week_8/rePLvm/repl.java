package rePLvm; 

import rVML.*;
import rePLcompiler.*;
import java.io.*;

class repl {

    static public void main(String[] args) 
    {
	// read name of source file from command line
	String basename=args[0];

        // like "java", the rePL VM is called on
        // the base name of the executable.
      
        String rvmlname=basename+".rvml";

        ObjectInputStream ois = null;

        try {
            // create object input stream
            ois = new ObjectInputStream(new FileInputStream(rvmlname));

        } catch (Exception ex) {
            System.out.println("cannot read "+rvmlname);
	    System.out.println("Usage: java cs3212.rvm.repl <program>");
	    System.exit(1);
        }

        // load serialized instruction array from input stream
        // into instructions
	CompilerResult cr;

        try {
            cr = (CompilerResult)ois.readObject();
	    INSTRUCTION instructions[] = cr.instructionArray;
	    int p[][] = cr.p;

            // print out instructions
	                for (int i=0; i < instructions.length; i++)
	                   if (instructions[i] != null) 
	                       System.out.println(i+": "+instructions[i]);

	    try {
	    // run the instructions and print out resulting value
	    // now, we pass both the instructions and p to the machine
		System.out.println(VM.run(instructions,p,cr.idp,cr.divisionByZeroAddress,
					  cr.invalidRecordAccessAddress));
	    } catch (Exception ex) {
		System.out.println("exception "+ex+" during execution of "+rvmlname);
	    }
        } catch (Exception ex) {
            System.out.println("cannot read "+rvmlname);
	    System.exit(1);
        }

        try {
            ois.close();
        } catch (Exception ex) {
            System.out.println("cannot close "+rvmlname);
	    System.exit(1);
	};
    }
}
