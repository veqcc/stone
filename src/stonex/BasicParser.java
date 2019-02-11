package stonex;
import static stonex.Parser.rule;
import java.util.HashSet;
import stonex.Parser.Operators;
import stonex.ast.*;

public class BasicParser {
    private HashSet<String> reserved = new HashSet<String>();
    private Operators operators = new Operators();
    private Parser expr0 = rule();
    private Parser primary = rule(PrimaryExpr.class).or(rule().sep("(").ast(expr0).sep(")"), rule().number(NumberLiteral.class), rule().identifier(Name.class, reserved), rule().string(StringLiteral.class));
    private Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);
    private Parser expr = expr0.expression(BinaryExpr.class, factor, operators);
    private Parser statement0 = rule();
    private Parser block = rule(BlockStmnt.class).sep("{").option(statement0).repeat(rule().sep(";", Token.EOL).option(statement0)).sep("}");
    private Parser simple = rule(PrimaryExpr.class).ast(expr);
    private Parser statement = statement0.or(rule(IfStmnt.class).sep("if").ast(expr).ast(block).option(rule().sep("else").ast(block)), rule(WhileStmnt.class).sep("while").ast(expr).ast(block), simple);
    private Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";", Token.EOL);
    private Parser param = rule().identifier(reserved);
    private Parser params = rule(ParameterList.class).ast(param).repeat(rule().sep(",").ast(param));
    private Parser paramList = rule().sep("(").maybe(params).sep(")");
    private Parser def = rule(DefStmnt.class).sep("def").identifier(reserved).ast(paramList).ast(block);
    private Parser args = rule(Arguments.class).ast(expr).repeat(rule().sep(",").ast(expr));
    private Parser postfix = rule().sep("(").maybe(args).sep(")");

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(")");
        reserved.add(Token.EOL);

        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);

        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
    }

    public ASTree parse(Lexer lexer) throws Exception {
        return program.parse(lexer);
    }
}