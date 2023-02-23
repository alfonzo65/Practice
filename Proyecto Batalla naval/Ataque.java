
public class Ataque{

	private int ataque;;
	private boolean acerto;

	public int obtenerAtaques(){
		return ataque;
	}

	public void establecerAtaques( int n ){
		
		if( acerto == true ){
			ataque = n + 1;
			acerto = false;
		}else{
			ataque = n;
		}
	}

	public void decrementarAtaques(){
		ataque--;
	}

	public void establecerPuntoExtra(){
		acerto = true;
	}

	public boolean acertandoBlancos( int[][] t , int x , int y ){

		if( t[x][y] == 0 || t[x][y] == 1 )
			return false;
		else if( t[x][y] < 0 )
			return false;

		return true;
		
	}
}