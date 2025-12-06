package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageClip;
import picasso.parser.tokens.StringToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the ImageClip function
 * 
 * @author Luis Coronel
 */
public class ImageClipAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		// Pop ImageClip token (it's on top)
		tokens.pop();
		
		// In postfix, arguments are before the function
		// So we need to pop in reverse order: y, x, string
		
		if (tokens.isEmpty()) {
			throw new ParseException("imageClip requires 3 arguments: filename, x coordinate, and y coordinate");
		}
		
		// Get y coordinate expression (top of stack after function)
		ExpressionTreeNode yCoord = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		
		if (tokens.isEmpty()) {
			throw new ParseException("imageClip is missing arguments. Imag: filename, x coordinate, and y coordinate");
		}

		
		// Get x coordinate expression
		ExpressionTreeNode xCoord = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		
		if (tokens.isEmpty()) {
			throw new ParseException("imageClip is missing the filename argument");
		}
		
		Token filenameToken = tokens.pop();
		if (!(filenameToken instanceof StringToken)) {
			throw new ParseException("imageClip requires a string filename as the first argument");
		}
		
		String filename = ((StringToken) tokens.pop()).getValue();
		
		return new ImageClip(filename, xCoord, yCoord);
	}
}