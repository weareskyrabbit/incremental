package front_end.token;

public class String_ extends Token {
    public final String value;
    public String_(final String value) {
        super(Tag.STR);
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
}
