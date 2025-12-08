package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents t in a Picasso expression
 * 
 * @author Asya Yurkovskaya
 * @author Luis Coronel
 * 
 */
public class T extends ExpressionTreeNode {

    private static double tTime = -0.01;

	/**
	 * //FIXME
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
        System.out.println(tTime);
        tTime += 0.01;
		return new RGBColor(tTime, tTime, tTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof T)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns "t", the representation of this variable in Picasso expressions
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "t";
	}

}
