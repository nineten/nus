package simPL;

public interface Expression {
    public Expression eliminateLet();
    public boolean reducible();
    public Expression oneStep();
    public StringSet freeVars();
    public Expression substitute(String var, Expression replacement);
    public String toXML();
}
