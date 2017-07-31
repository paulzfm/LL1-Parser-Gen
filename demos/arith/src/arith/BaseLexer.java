package arith;

import java.io.IOException;

import arith.Driver;
import arith.Location;
import arith.error.CompileError;

public abstract class BaseLexer {

    private Parser parser;

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    abstract int yylex() throws IOException;

    abstract Location getLocation();

    private CompileError error = null;

    public void handleError() throws CompileError {
        if (error != null) throw error;
    }

    protected void issueError(CompileError err) {
        error = err;
    }

    protected void setSemantic(Location where, SemValue v) {
        v.loc = where;
        parser.val = v;
    }

    protected int operator(int code) {
        setSemantic(getLocation(), SemValue.createOperator(code));
        return code;
    }

    protected int intConst(String ival) {
        setSemantic(getLocation(), SemValue.createNum(Integer.decode(ival)));
        return Parser.NUM;
    }

    public void diagnose() throws IOException {
        while (yylex() != 0) {
            System.out.println(parser.val);
        }
    }
}
