/**
 * Created with IntelliJ IDEA.
 * User: Felix
 * Date: 15/02/13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */


import java.awt.*;

public class PawnGame implements Pawn {
    private Color pawnColor = Color.GRAY;
    private Dimension pawnDimension = null;
    public PawnGame(Color c) {
            setPawnColor(c);
    }
       @Override
    public void setPawnColor(Color c) {
            pawnColor = c;
    }
       @Override
    public void setPawnDimension(Dimension d) {
            pawnDimension = d;
    }
       @Override
    public Color getPawnColor() {
            return pawnColor;
    }
        @Override
    public Dimension getPawnDimension() {
            return pawnDimension;
        }
}



