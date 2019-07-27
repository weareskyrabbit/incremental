package driver;

import back_end.Builder;
import front_end.ast.FunctionDeclaration;
import front_end.Parser;
import front_end.ParsingException;
import io.Reader;
import io.Writer;

import java.io.IOException;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws IOException {
        // front_end
        String input = Reader.use(args[0] + ".grp" ,Reader::read);
        Parser parser = new Parser();
        List<FunctionDeclaration> trees = null;
        try {
            trees = parser.parse(input);
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
        StringBuilder s = new StringBuilder();
        trees.forEach(tree -> s.append(tree.toS(0)).append('\n'));
        Writer.use(args[0] + ".ast", writer -> writer.write(s.toString()));
        // back_end
        String output = new Builder().build(trees);
        Writer.use(args[0] + ".s", writer -> writer.write(output));
    }
}
