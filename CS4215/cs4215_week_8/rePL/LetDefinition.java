package rePL;

public class LetDefinition {

    public String variable;

    public Expression rightHandExpression;

    public LetDefinition(String v, Expression rhe) {
	variable = v; rightHandExpression = rhe;
    }

    public String toString() {
	return variable+" = "+rightHandExpression;
    }
}
