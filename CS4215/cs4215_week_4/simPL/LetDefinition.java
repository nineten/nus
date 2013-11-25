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
}
