package front_end;

public class While extends Statement {
    final int condition;
    final Closure closure;
    While(final int condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }

    @Override
    public String toString() {
        return "(while " + condition + " " + closure + ")";
    }
}
