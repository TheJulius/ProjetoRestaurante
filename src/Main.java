import java.util.Scanner;

public class Main {
	
	static Scanner entrada = new Scanner(System.in);
	
	static FilaChegada InicioFilaChegada = null;
	static FilaChegada FimFilaChegada = null;
	
	static FilaCaixa InicioFilaCaixa = null;
	static FilaCaixa FimFilaCaixa = null;
	
	static Mesa InicioListaMesa = null;
	static Mesa FimListaMesa = null;
	
	static Prato TopoPilhaPratos = null;
	
	static int pessoasFilaAlmocar = 0;
	static int pessoasFilaCaixa = 0;
	static int pessoasAlmocando = 0;
	static int pessoasAtendidas = 0;
	static int qtPratos = 0;
	static int mesasLivres = 20;
	static double movimentacaoCaixa = 0;
	static int totalMesas = 20;
	final static int precoBuffet = 20;
	
	
	public static void excluirMesa(int numero) {
		if (InicioListaMesa != null && InicioListaMesa.getNumero() == numero) {
			if (InicioListaMesa.getProx() != null) {
				InicioListaMesa = InicioListaMesa.getProx();
			} else {
				if (InicioListaMesa == FimListaMesa) {
					FimListaMesa = null;
				}
				InicioListaMesa = null;
			}	
		} else {
			Mesa aux = InicioListaMesa;
			while (aux != null) {	
				if (aux.getProx() != null && aux.getProx() == FimListaMesa && aux.getProx().getNumero() == numero) {
					aux.setProx(null);
					FimListaMesa = aux;
				} else {
					if (aux.getProx() != null && aux.getProx().getNumero() == numero) {
						aux.setProx(aux.getProx().getProx());
					}
					aux = aux.getProx();
				} 
			}
		}	
	}
	
	public static void atenderFilaCaixa() {
		pessoasFilaCaixa--;
		if (!vazia2()) {
			System.out.print("ATENDENDO AGORA: ");
			verProximoFilaCaixa();
			System.out.println("ATENDIDO!");
			
			movimentacaoCaixa += precoBuffet;
			pessoasAtendidas++;
			
			if(InicioFilaCaixa == FimFilaCaixa) {
				FimFilaCaixa = null;
			}
					
			InicioFilaCaixa = InicioFilaCaixa.getProx();
		}

	}
	
	public static void inserirFilaCaixa() {
		entrada.nextLine();
		System.out.print("\nInforme o nome do cliente: ");
		String nome = entrada.nextLine();
		boolean tem = pesquisarPessoaMesa(nome) != null;
		if (tem) {
			int numero = pesquisarPessoaMesa(nome).getNumero();
			excluirMesa(numero);
			inserirMeio(numero, numero-1);
			inserirFilaCaixa(nome);
		}else {
			System.out.println("Nao localizado nas mesas!");
		}
	}
	
	public static void inserirMeio(int numero, int numeroBase) {
		boolean achou = false;
		Mesa novo = new Mesa();
		novo.setNumero(numero);
		novo.setPes(null);
		Mesa aux = InicioListaMesa;
		
		while(aux != null) { 
			if(aux.getNumero() == numeroBase) {
				novo.setProx(aux.getProx());
				aux.setProx(novo);
				achou = true;
				break;
			}
			aux = aux.getProx();
		}
		
		if(!achou) {
			if(InicioListaMesa == null) {
				InicioListaMesa = novo;
			}else {
				FimListaMesa.setProx(novo);
			}
			FimListaMesa = novo;
		}
	}

	public static void inserirFilaCaixa(String nome) {
		pessoasFilaCaixa++;
		mesasLivres++;
		pessoasAlmocando--;
		FilaCaixa novo = new FilaCaixa();
		novo.getPes().setNome(nome);
		if (InicioFilaCaixa == null) {
			InicioFilaCaixa = novo;
		}else {	
			FimFilaCaixa.setProx(novo);
		}
		FimFilaCaixa = novo;
		System.out.println("INSERIDO");
	}
	
	public static Mesa pesquisarPessoaMesa(String nome) {
		Mesa aux = InicioListaMesa;
		boolean achou = false;
		while(aux != null) {
			if(aux.getPes() != null && aux.getPes().getNome().equals(nome)) {
				achou = true;
				break;
			}
			aux = aux.getProx();
		}
		if(achou) {
			return aux;	
		}else {
			return null;
		}
	}

	public static Pessoa pesquisarPessoaChegada(String nome) {
		FilaChegada aux = InicioFilaChegada;
		boolean achou = false;
		while(aux != null) {
			if(aux.getPes() != null && aux.getPes().getNome().equals(nome)) {
				achou = true;
				return aux.getPes();
			}
			aux = aux.getProx();
		}
		if(!achou) {
			System.out.println("Contato nao cadastrado.");
		}
		return null;
	}
	
	public static void inserirPratos() {
		System.out.print("Entre com a quantidade de pratos: ");
		int qt = entrada.nextInt();
		for (int k = 0; k < qt; k++) {
			inserirPratos(1);
		}
	}
	
	private static void inserirPratos(int qt) {
		qtPratos += qt;
		Prato novo = new Prato();
		if (TopoPilhaPratos == null) {
			TopoPilhaPratos = novo;
		}else {	
			 novo.setProx(TopoPilhaPratos);
		}
		TopoPilhaPratos = novo;
	}
	
	public static void verProximoFilaChegada() {
		if (!vazia()) {
			System.out.println("\nProximo cliente a ser atendido: " + InicioFilaChegada.getPes().getNome());
		}
	}
	
	public static void verProximoFilaCaixa() {
		if (!vazia2()) {
			System.out.println("Cliente: " + InicioFilaCaixa.getPes().getNome());
		}
	}
	
	public static boolean vazia() {
		return InicioFilaChegada == null;
	}
	
	public static boolean vazia2() {
		return InicioFilaCaixa == null;
	}
	
	public static void inserirChegada() {
		entrada.nextLine();
		System.out.print("Informe o nome do cliente: ");
		String nome = entrada.nextLine();
		if (verificarPilhaPratos()) {
			inserirChegada(nome);
		}else {
			System.out.println("Sem pratos. Necessário repor pratos na pilha.");
		}
	}

	private static boolean inserirChegada(String nome) {
		qtPratos--;
		pessoasFilaAlmocar++;
		FilaChegada novo = new FilaChegada();
		novo.getPes().setNome(nome);
		if (verificarPilhaPratos()) {
			if (InicioFilaChegada == null) {
				InicioFilaChegada = novo;
			}else {	
				FimFilaChegada.setProx(novo);
			}
			FimFilaChegada = novo;	
			popPrato();
			return true;
		}else {
			return false;
		}
	}
	
	public static void atenderChegada(int numero) {
		pessoasFilaAlmocar--;
		pessoasAlmocando++;
		if (!vazia()) {
			verProximoFilaChegada();
			if (escolherMesa(InicioFilaChegada.getPes(), numero)) {
			

				if(InicioFilaChegada == FimFilaChegada) {
					FimFilaChegada = null;
				}
			
				InicioFilaChegada = InicioFilaChegada.getProx();
				System.out.println("INSERIDO NA MESA!");
			}else {
				System.out.println("Já existe um cliente nesta mesa!");
			}
		}

	}
	
	public static void popPrato() {
		if (vazia3()) {
			System.out.println("Pilha de pratos vazia.");
		} else {
			TopoPilhaPratos = TopoPilhaPratos.getProx();
		}
	}

	
	public static boolean verificarPilhaPratos() {
		return TopoPilhaPratos != null;
	}
	
	public static boolean vazia3() {
		return TopoPilhaPratos == null;
	}
	
	private	 static void inserirMesa(int numero) {
		totalMesas++;
		Mesa novo = new Mesa();
		novo.setNumero(numero);
		novo.setPes(null);
		if(InicioListaMesa == null) {
			InicioListaMesa = novo;
		}else {
			FimListaMesa.setProx(novo);
		}
		FimListaMesa = novo;
	}
	

	public static void escolherMesa() {
		if (mesasLivres > 1) {
			System.out.print("Insira o numero da mesa: ");
			int numero = entrada.nextInt();
			atenderChegada(numero);
		}else {
			System.out.println("Nao existem mesas disponiveis!!");
		}
		
	}
	

	
	public static boolean escolherMesa(Pessoa pes, int numero) {
		Mesa aux = InicioListaMesa;
		while (aux != null) {
			if (aux.getNumero() == numero && aux.getPes() == null) {
				aux.setPes(pes);
				mesasLivres--;
				return true;
			}
			aux = aux.getProx();
		}
		return false;
	}
	
	
	public static void listarMesas() {
		Mesa aux = InicioListaMesa;
		while(aux != null) {
			System.out.print("\nMESA ");
                        if (aux.getNumero() != 0) {
				System.out.println(aux.getNumero());
			}else {
				System.out.println("Sem numero\n");
			}
			if (aux.getPes() != null) {
				System.out.println("Mesa ocupada por: " +aux.getPes().getNome());
			}else {
				System.out.println("Mesa vazia.");
			}
			aux = aux.getProx();
		}
	}
	
	public static void menu() {
		int op = 0;
		Scanner entrada = new Scanner(System.in);
		
		// Valores iniciais
				
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Julio");
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setNome("Angelita");
		Pessoa pessoa3 = new Pessoa();
		pessoa3.setNome("Eva");
		
		for (int k = 0 ; k < 20; k++) {
			inserirPratos(1);
		}
		inserirMesa(1);
		inserirMesa(2);
		inserirMesa(3);
		inserirMesa(4);
		inserirMesa(5);
		inserirMesa(6);
		inserirMesa(7);
		inserirMesa(8);
		inserirMesa(9);
		inserirMesa(10);
		inserirMesa(11);
		inserirMesa(12);
		inserirMesa(13);
		inserirMesa(14);
		inserirMesa(15);
		inserirMesa(16);
		inserirMesa(17);
		inserirMesa(18);
		inserirMesa(19);
		inserirMesa(20);
		escolherMesa(pessoa3, 3);
		escolherMesa(pessoa2, 2);
		escolherMesa(pessoa, 7);
		
		totalMesas = 20;
		mesasLivres = 17;
		pessoasAlmocando = 3;
		pessoasFilaAlmocar = 0;
		pessoasFilaCaixa = 0;
		qtPratos = 20;
		pessoasAtendidas = 0;
		
		
		do {
				
			System.out.println("\n MENU PRINCIPAL \n");
			System.out.println("[1] - Inserir na fila de chegada");
			System.out.println("[2] - Ver proximo fila de chegada");
			System.out.println("[3] - Repor pratos na pilha");
			System.out.println("[4] - Inserir cliente na mesa");
			System.out.println("[5] - Ver lista de todas as mesas");
			System.out.println("[6] - Inserir na fila do caixa");
			System.out.println("[7] - Ver proximo fila do caixa");
			System.out.println("[8] - Atender caixa");
			System.out.println("[9] - Estatasticas");
			System.out.println("[0] - Sair");
			System.out.print("\nEscolha uma opcao: ");
			op = entrada.nextInt();
			
			switch (op) {
			case 1:
				inserirChegada();
				break;
			case 2:
				verProximoFilaChegada();
				break;
			case 3:
				inserirPratos();
				break;
			case 4:
				escolherMesa();
				break;
			case 5:
				listarMesas();
				break;
			case 6:
				inserirFilaCaixa();
				break;
			case 7:
				verProximoFilaCaixa();
				break;
			case 8:
				atenderFilaCaixa();
				break;
			case 9:
				System.out.println("\n TABELA COM A SITUACAO ATUAL DO RESTAURANTE DA UNIFIQUE\n");
				System.out.println("Pessoas na fila para almocar: " +pessoasFilaAlmocar+ " pessoas.");
				System.out.println("Pessoas na fila do caixa: " +pessoasFilaCaixa+ " pessoas.");
				System.out.println("Pessoas almocando: " +pessoasAlmocando+ " pessoas.");
				System.out.println("Pratos na pilha: " +qtPratos+ " pratos.");
				System.out.println("Numero de mesas livres: " +mesasLivres+ " mesas.");
				System.out.println("Numero de pessoas atendidas: " +pessoasAtendidas + " pessoas.");
				System.out.println("Movimentacao do caixa: " +movimentacaoCaixa + " reais.");
				break;
			case 0:
				System.out.println("Programa finalizado. Ate breve!");
				break;
			default:
				System.out.println("Opcao invalida!");
				break;
		}

				
		}while(op != 0);
			
		entrada.close();
			
	}	

	public static void main(String[] args) {
		menu();
	}

}
