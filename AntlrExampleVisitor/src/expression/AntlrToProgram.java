package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;

public class AntlrToProgram extends ExprBaseVisitor<Program> {
    @Override
    public Program visitProgram(ExprParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }
}
