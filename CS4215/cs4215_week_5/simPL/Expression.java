package simPL;

public interface Expression {
    public Type check(TypeEnvironment G) throws TypeError;
    public Expression eliminateLet();
    public String toXML();
}
