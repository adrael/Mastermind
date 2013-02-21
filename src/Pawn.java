/**
 * Created with IntelliJ IDEA.
 * User: Felix
 * Date: 15/02/13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */

import java.awt.Color;


import java.awt.Color;
import java.awt.Dimension;

public interface Pawn {
    void setPawnColor(Color c);
    void setPawnDimension(Dimension d);
    Color getPawnColor();
    Dimension getPawnDimension();
 }

