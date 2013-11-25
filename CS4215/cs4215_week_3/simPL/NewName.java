package simPL;

// generates a string that is different from
// all strings in a given Set. The generated
// name starts with "new", and has all given
// names as substrings.

public class NewName {
   
   public static String newName(StringSet varnames) {
       String s = "new";
       for (String vn : varnames) 
	   s = s + vn;
       return s;
   }

}

