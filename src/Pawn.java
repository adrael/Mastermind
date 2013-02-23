import java.awt.Color;
import java.awt.Dimension;

public interface Pawn {
	void setPawnColor(Color c);

	void setPawnDimension(Dimension d);

	Color getPawnColor();

	Dimension getPawnDimension();
}
