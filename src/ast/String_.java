package ast;

public class String_ extends Expression {
    private final int offset;
    public String_(final String value, final int offset) {
        super(value);
        this.offset = offset;
    }
    @Override
    public String build() {
        return "";
    }
    @Override
    public String toS(int tab) {
        return "\"" + operator + "\"";
    }
    @Override
    public ir.Operand toIR() {
        return new ir.Immediate(offset);
    } // TODO Memory
}
