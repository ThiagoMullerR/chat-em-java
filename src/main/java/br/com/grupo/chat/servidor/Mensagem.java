package br.com.grupo.chat.servidor;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.UUID;

public class Mensagem implements Serializable {
	String remetente;
}

class MensagemUsuario extends Mensagem {
	String mensagem;

	MensagemUsuario(String remetente, String mensagem) {
		this.remetente = remetente;
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return String.format("Usuario - %s: %s", this.remetente, this.mensagem);

	}
}

class MensagemTransferencia extends Mensagem {
	String nomearquivo;
	int bytearq;
	String uuid;

	MensagemTransferencia(String remetente, String nomearquivo, int bytearq, String uuid) {
		this.remetente = remetente;
		this.nomearquivo = nomearquivo;
		this.bytearq = bytearq;
		this.uuid = uuid;

	}

	@Override
	public String toString() {
		return String.format("Arquivo %s - R:%s N:%s B:%s", this.uuid, this.remetente, this.nomearquivo, this.bytearq);

	}

}

class MensagemRequisitarArquivo extends Mensagem{
	String uuid;
	
	MensagemRequisitarArquivo(String uuid){
		this.uuid = uuid;
	}
}

class MensagemListaArquivos extends Mensagem{
	Hashtable<String, String[]> lista;
	
	MensagemListaArquivos(Hashtable<String, String[]> lista){
		this.lista = lista;
	}
	
	@Override
	public String toString() {
		return String.format("Lista %s", this.lista.toString());

	}
	
}