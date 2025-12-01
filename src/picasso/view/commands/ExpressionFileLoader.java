package picasso.view.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.FileCommand;
import picasso.util.ThreadedCommand;

/**
 * Loads an expression from a file into the expression field and renders it.
 */
public class ExpressionFileLoader extends FileCommand<Pixmap> {
	private final JComponent view;
    private final JTextField expressionField;

	public ExpressionFileLoader(JComponent view, JTextField expressionField) {
        super(JFileChooser.OPEN_DIALOG);
		this.view = view;
        this.expressionField = expressionField;
    }

    @Override
    public void execute(Pixmap target) {
        String fileName = getFileName();
        if (fileName == null) {
			return; // user cancelled dialog
        }
        try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			StringBuilder builder = new StringBuilder();
			for (String line : lines) {
				String trimmed = line.trim();
				if (trimmed.isEmpty() || trimmed.startsWith("//")) {
					continue; // skip blank lines and comments
				}
				if (builder.length() > 0) {
					builder.append(' ');
				}
				builder.append(trimmed);
			}
			String expr = builder.toString();
			expressionField.setText(expr);
			if (!expr.isEmpty()) {
				Evaluator evaluator = new Evaluator(expressionField);
				new ThreadedCommand<Pixmap>(view, evaluator).execute(target);
			}
        } catch (IOException e) {
			// keep UI silent on errors; log to console for debugging
            e.printStackTrace();
        }
    }
}
