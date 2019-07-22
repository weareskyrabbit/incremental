package driver;

import back_end.Builder;
import front_end.Closure;
import front_end.Parser;
import front_end.ParsingException;
import io.Reader;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        String input = Reader.use(args[0] ,Reader::read);
        Parser parser = new Parser();
        Closure tree = null;
        try {
            tree = parser.parse(input);
        } catch (ParsingException exception) {
            System.out.println("ParsingException | line " + parser.line);
            /*
            for (int i = 0; i < parser.offset; i++) {
                System.out.print(" ");
            }
            System.out.println("^");
            */
            System.exit(1);
        }
        String output = new Builder().build(tree);
        System.out.println(output);
    }
}
