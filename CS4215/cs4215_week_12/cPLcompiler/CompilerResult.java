package cPLcompiler;

import cVML.*;
import cPL.*;

import java.util.*;

import java.io.*;

public class CompilerResult implements Serializable {
    public INSTRUCTION[] instructionArray;
    public int divisionByZeroAddress, invalidRecordAccessAddress;

    public CompilerResult(INSTRUCTION[] ia, int dza, int ira) {
	instructionArray = ia;
	divisionByZeroAddress = dza;
	invalidRecordAccessAddress = ira;
   }
}

