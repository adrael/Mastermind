
public class Plate {
    private int numberOfRow, numberOfPawn;
    
    public Plate() {
    	
    }
    
    public Plate(int r, int p) {
    	setNumberOfRow(r);
    	setNumberOfPawn(p);
    }
    
    public void setNumberOfRow(int n) {
    	this.numberOfRow = n;
    }
    
    public int getNumberOfRow() {
    	return this.numberOfRow;
    }
    
    public void setNumberOfPawn(int n) {
    	this.numberOfPawn = n;
    }
    
    public int getNumberOfPawn() {
    	return this.numberOfPawn;
    }
}
