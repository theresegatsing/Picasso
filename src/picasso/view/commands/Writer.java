package picasso.view.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.FileCommand;

/**
 * Save the chosen file. If the filename ends with .exp (or no known image extension),
 * save the current expression text; otherwise save the current image as a jpg.
 *
 * @author Robert C Duvall
 */
public class Writer extends FileCommand<Pixmap> {
	private final JTextField expressionField;

	public Writer(JTextField expressionField) {
		super(JFileChooser.SAVE_DIALOG);
		this.expressionField = expressionField;
	}

	public void execute(Pixmap target) {
		String fileName = getFileName();
		if (fileName == null) {
			return;
		}

		String trimmed = fileName.trim();
		if (trimmed.isEmpty()) {
			return;
		}

		String lower = trimmed.toLowerCase();
		boolean looksLikeImage = lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")
				|| lower.endsWith(".gif") || lower.endsWith(".bmp");
		boolean looksLikeExpression = lower.endsWith(".exp") || !looksLikeImage;

		if (looksLikeExpression) {
			// Ensure .exp extension
			if (!lower.endsWith(".exp")) {
				trimmed = trimmed + ".exp";
			}
			try {
				String expr = expressionField != null ? expressionField.getText() : "";
				Files.writeString(Paths.get(trimmed), expr == null ? "" : expr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// default to jpg write for images
			target.write(trimmed);
		}
	}
}
