
public class FilaCaixa {
	
	private FilaCaixa prox = null;
	private Pessoa pes = null;
	
	public FilaCaixa() {
		this.pes = new Pessoa();
	}
	
	public FilaCaixa getProx() {
		return prox;
	}
	public void setProx(FilaCaixa prox) {
		this.prox = prox;
	}
	public Pessoa getPes() {
		return pes;
	}
	public void setPes(Pessoa pes) {
		this.pes = pes;
	}
}