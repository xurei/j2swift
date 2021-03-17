import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Very basic Java to Swift syntax converter.
 * See test/Test.java and test/Test.swift for an idea of what this produces.
 * This is a work in progress...
 * @author Pat Niemeyer (pat@pat.net)
 */
public class J2Swift
{
	public static void main( String [] args ) throws Exception
    {
        // This boilerplate largely from the ANTLR example
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) { is = new FileInputStream(inputFile); }
        String outputFile = null;
        if ( args.length>1 ) { outputFile = args[1]; }
        PrintStream os = System.out;
        if ( outputFile!=null ) { os = new PrintStream(new FileOutputStream(outputFile)); }
        ANTLRInputStream input = new ANTLRInputStream(is);
        Java8Lexer lexer = new Java8Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        J2SwiftListener swiftListener = new J2SwiftListener(tokens);
        walker.walk(swiftListener, tree);

        String preconverted = swiftListener.rewriter.getText();
        preconverted = preconverted.replaceAll("static +final", "static");
        os.println( preconverted );


        os.close();
	}
}
