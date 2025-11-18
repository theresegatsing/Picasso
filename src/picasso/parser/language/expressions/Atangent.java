package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

public class Atangent extends UnaryFunction{
	
	public Atangent(ExpressionTreeNode param) {
		super(param);
	}

	
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.atan(result.getRed());
		double green = Math.atan(result.getGreen());
		double blue = Math.atan(result.getBlue());

		return new RGBColor(red, green, blue);
	}
}
