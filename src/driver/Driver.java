package driver;

import middle_end.Builder;
import middle_end.Module;
import ast.FunctionDeclaration;
import front_end.RecursiveDescentParser;
import front_end.ParsingException;
import io.Reader;
import io.Writer;

import java.io.IOException;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws IOException {
        // front_end
        final String input = Reader.use(args[0] + ".grp" ,Reader::read);
        final RecursiveDescentParser parser = new RecursiveDescentParser();
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
        if (args.length > 1 && args[1].equals("ast")) {
            final StringBuilder s = new StringBuilder();
            trees.forEach(tree -> s.append(tree.toS(0)).append('\n'));
            Writer.use(args[0] + ".ast", writer -> writer.write(s.toString()));
        }
        // middle_end
        Builder.clear();
        Module module = Builder.generate(trees);
        module.build();
        System.out.println(Builder.build());


        final StringBuilder ir = new StringBuilder();
        ir.append("; tab=8\n");
        trees.forEach(tree -> ir.append(tree.generate()).append('\n'));
        Writer.use(args[0] + ".ir", writer -> writer.write(ir.toString()));
        /*
        // back_end
        final String output = new Builder().build(trees);
        Writer.use(args[0] + ".s", writer -> writer.write(output));
        */
    }
}
