package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;

public class AntlrToExpression extends ExprBaseVisitor<Expression> {
    @Override
    public Expression visitDeclaration(ExprParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public Expression visitMultiplication(ExprParser.MultiplicationContext ctx) {
        return super.visitMultiplication(ctx);
    }

    @Override
    public Expression visitAddition(ExprParser.AdditionContext ctx) {
        return super.visitAddition(ctx);
    }

    @Override
    public Expression visitVariable(ExprParser.VariableContext ctx) {
        return super.visitVariable(ctx);
    }

    @Override
    public Expression visitNumber(ExprParser.NumberContext ctx) {
        return super.visitNumber(ctx);
    }
}
