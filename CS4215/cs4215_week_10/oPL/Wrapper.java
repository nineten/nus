package oPL;

public class Wrapper {
	private static String topCode = "let new = fun theClass -> [Class: theClass] end access = fun r p -> r.p end addProperty = fun r p v -> r.p := v end in ";
	private static String topCodeEnd = " end";
	
	private static String lookupCode = 
			"let lookupInClass = " +
				"recfun lookupInClass theClass methodname -> " +
					"if theClass hasproperty methodname " +
					"then theClass.methodname " +
					"else (lookupInClass theClass.Parent methodname) " +
					"end " +
				"end " +
			"in " +
				"let lookup = fun object methodname -> (lookupInClass object.Class methodname) end " +
				"in ";
	private static String lookupCodeEnd = " end end";
	
	public static String prologue = topCode + lookupCode;
	public static String epilogue = lookupCodeEnd + topCodeEnd;
	
}
