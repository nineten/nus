package simPL;

public class LetDefinition {

    public Type type;

    public String variable;

    public Expression rightHandExpression;

    public LetDefinition(Type t, String v, Expression rhe) {
	type = t; variable = v; rightHandExpression = rhe;
    }

    public String toString() {
	return "{"+type+"} "+variable+" = "+rightHandExpression;
    }

    public String toXML() {
	return "<simpl:letdefinition>\n"
	    + "<simpl:type>\n" + type.toXML() +"</simpl:type>\n"
	    + "<simpl:variable>" + variable + "</simpl:variable>\n"
	    + "<simpl:righthandexpression>\n" + rightHandExpression.toXML()
	    + "</simpl:righthandexpression>\n" 
	    + "</simpl:letdefinition>\n";
    }
}
