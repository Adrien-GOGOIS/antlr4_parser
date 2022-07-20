package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import antlr.ExprVisitor;

import java.util.ArrayList;
import java.util.List;

public class AntlrToProgram extends ExprBaseVisitor<Program> {

    public List<String> semanticErrors;

    @Override
    public Program visitProgram(ExprParser.ProgramContext ctx) {
        Program program = new Program();
        semanticErrors = new ArrayList<>();
        AntlrToExpression expressionVisitor = new AntlrToExpression(semanticErrors); // helper visitor to transform each subtree into Expression

        for (int i =0; i < ctx.getChildCount(); i++) {
            if (i != ctx.getChildCount() - 1) { // Last child of the start symbol program is EOF -> Do not visit this child and try to convert into an Expression object
                program.addExpression(expressionVisitor.visit(ctx.getChild(i)));
            }
        }

        return program;
    }
}
