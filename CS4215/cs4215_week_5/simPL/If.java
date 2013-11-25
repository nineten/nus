package simPL;

public class If implements Expression {

   public Expression condition,thenPart,elsePart;

   public If(Expression c, Expression t, Expression e) {
      condition = c;
      thenPart = t;
      elsePart = e;
   }

    // Eliminate let in the condition, then part and else part
    public Expression eliminateLet() {
	return new If(condition.eliminateLet(), 
		      thenPart.eliminateLet(), 
		      elsePart.eliminateLet());
    }

    // //////////////////////
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {
	// get type of condition
	Type condType = condition.check(G);
	// get type of thenPart
	Type thenType = thenPart.check(G);
	// get type of elsePart
	Type elseType = elsePart.check(G);
	// if condition is bool Type and (then & else) have same type
	if (condType instanceof BoolType)
		if (EqualType.equalType(thenType,elseType))
			return thenType;	
		else throw new TypeError("then part has type "+thenType+" whereas else part has type "+elseType+
				                 " in expression "+this);
	else throw new TypeError("condition in "+this+" must have type bool, but instead has type "+condType);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return " if " + condition + " then " + 
             thenPart + " else " + elsePart + " end";
   }

   public String toXML() {
      return "<simpl:if>\n"
	  + "<simpl:condition>\n" + condition.toXML() + "</simpl:condition>\n"
	  + "<simpl:thenpart>\n" + thenPart.toXML() + "</simpl:thenpart>\n"
	  + "<simpl:elsepart>\n" + elsePart.toXML() + "</simpl:elsepart>\n"
	  + "</simpl:if>\n";
   }
}
