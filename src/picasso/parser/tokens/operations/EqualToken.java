package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.OperatorPrecedence;

/**
 * 
 * 
 * @author Therese Elvira Mombou Gatsing
 */
public class EqualToken extends CharToken implements OperationInterface {
	
	public EqualToken() {
		super(CharConstants.EQUAL);
	}
	
	@Override
    public String toString() {
        return "EqualToken";
    }
	
	@Override
	public int orderOfOperation(){
		return OperatorPrecedence.ASSIGNMENT;
	}
	
}
