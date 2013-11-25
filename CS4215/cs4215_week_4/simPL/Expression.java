package simPL;

public interface Expression {
    public Expression eliminateLet();
    public Type check(TypeEnvironment G) throws TypeError;
}
