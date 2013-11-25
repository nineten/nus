package rePLcompiler;

import rVML.*;
import rePL.*;

import java.util.*;

import java.io.*;

public class CompilerResult implements Serializable {
    public INSTRUCTION[] instructionArray;
    public int[][] p;
    public Idp idp;
    public int divisionByZeroAddress, invalidRecordAccessAddress;

    public CompilerResult(INSTRUCTION[] ia, int[][] pp, Idp i, int dza, int ira) {
	instructionArray = ia;
	p = pp;
	idp = i;
	divisionByZeroAddress = dza;
	invalidRecordAccessAddress = ira;
   }
}

