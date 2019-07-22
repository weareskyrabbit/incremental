package driver;

import front_end.Node;
import front_end.Parser;
import front_end.ParsingException;
import io.Reader;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        String input = Reader.use(args[0] ,Reader::read);
        Parser parser = new Parser();
        Node tree = null;
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
        System.out.println(tree.toString());
    }
}
