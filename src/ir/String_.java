package ir;

public class String_ implements Operand {
    final String value;
    public String_(final String value) {
        this.value = value;
    }
    @Override
    public java.lang.String build() {
        return null; // TODO
    }
    @Override
    public int toWordCode() {
        return 0x00;
    }
}
