package app;

import antlr.ExprLexer;
import antlr.ExprParser;
import expression.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class ExpressionApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("Usage: filename");
        } else {
            String filename = args[0];
            ExprParser parser = getParser(filename);

            // Tell ANTLR to build a parse tree
            // Parse from the start symbol : "prog"
            ParseTree antlrAST = parser.prog();

            if (!ErrorListener.hasError) {
                // Create a visitor for converting the parse tree into Program/Expression object
                AntlrToProgram programVisitor = new AntlrToProgram();
                Program program = programVisitor.visit(antlrAST);

                if (programVisitor.semanticErrors.isEmpty()) {
                    ExpressionProcessor expressionProcessor = new ExpressionProcessor(program.expressions);
                    for (String evaluation : expressionProcessor.getEvaluationResults()) {
                        System.out.println(evaluation);
                    }
                } else {
                    for (String error : programVisitor.semanticErrors) {
                        System.out.println(error);
                    }
                }
            }
        }
    }

    private static ExprParser getParser(String filename) {
        ExprParser parser = null;

        try {
            CharStream input = CharStreams.fromFileName(filename);
            ExprLexer lexer = new ExprLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new ExprParser(tokens);

            // Syntax error handling
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ExprLexer / ExprParser -> nom dépend de l'intitulé du ficher Expr.g4

        return parser;
    }
}
