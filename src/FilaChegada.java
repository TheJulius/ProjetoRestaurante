
public class FilaChegada {
	
	private FilaChegada prox = null;
	private Pessoa pes = null;
	
	public FilaChegada() {
		this.pes = new Pessoa();
	}
	
	public FilaChegada getProx() {
		return prox;
	}
	public void setProx(FilaChegada prox) {
		this.prox = prox;
	}
	public Pessoa getPes() {
		return pes;
	}
	public void setPes(Pessoa pes) {
		this.pes = pes;
	}
}
