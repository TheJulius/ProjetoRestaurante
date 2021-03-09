
public class Mesa {
	
	private Pessoa pes = null;
	private Mesa prox = null;
	private int numero = 0;
	
	public Mesa() {
		this.pes = new Pessoa();
	}

	public Pessoa getPes() {
		return pes;
	}

	public void setPes(Pessoa pes) {
		this.pes = pes;
	}

	public Mesa getProx() {
		return prox;
	}

	public void setProx(Mesa prox) {
		this.prox = prox;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
