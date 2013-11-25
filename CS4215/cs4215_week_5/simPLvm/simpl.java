package simPLvm;

import sVML.*;
import java.io.*;

class simpl {

	static public void main(String[] args) 
	{
		// read name of source file from command line
		String basename=args[0];

		// like "java", the simPL VM is called on
		// the base name of the executable.

		String svmlname=basename+".svml";

		ObjectInputStream ois = null;

		try {
			// create object input stream
			ois = new ObjectInputStream(new FileInputStream(svmlname));

		} catch (Exception ex) {
			System.out.println("cannot read "+svmlname);
			System.out.println("Usage: java simPLvm.simpl <program>");
			System.exit(1);
		}

		// load serialized instruction array from input stream
		// into instructions
		INSTRUCTION instructions[] = null;

		try {
			instructions = (INSTRUCTION[])ois.readObject();
		} catch (Exception ex) {
			System.out.println("cannot read "+svmlname);
			System.exit(1);
		}

		// run the instructions and print out resulting value
		System.out.println(VM.run(instructions));

		try {
			ois.close();
		} catch (Exception ex) {
			System.out.println("cannot close "+svmlname);
			System.exit(1);
		};
	}
}
