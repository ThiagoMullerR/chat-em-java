package br.com.grupo.chat.servidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class GerenciadorArquivo {

	static Hashtable<String, String[]> listaArquivos = new Hashtable();
	static Hashtable<String, FileOutputStream> arqAbertos = new Hashtable();
	static String caminho = null;

	GerenciadorArquivo(String caminho) {
		this.caminho = caminho;
	}

	public void Escreve(MensagemTransferencia msg) throws Exception {
		if (!GerenciadorArquivo.arqAbertos.containsKey(msg.uuid)) {
			File fo = new File(caminho + msg.uuid);
			fo.createNewFile();

			FileOutputStream output = new FileOutputStream(fo);
			GerenciadorArquivo.arqAbertos.put(msg.uuid, output);

			Escreve(msg);
		} else {
			FileOutputStream output = (FileOutputStream) GerenciadorArquivo.arqAbertos.get(msg.uuid);

			if (msg.bytearq == -1) {
				output.close();
				GerenciadorArquivo.arqAbertos.remove(msg.uuid);
				System.out.println(String.format("Arquivo Recebido: %s - %s, %s", msg.uuid,msg.remetente,msg.nomearquivo));

				String[] info = { msg.remetente, msg.nomearquivo };
				listaArquivos.put(msg.uuid, info);

//				MensagemListaArquivos msglista = new MensagemListaArquivos(listaArquivos);
//				Broadcaster bd = new Broadcaster();
//				bd.broadcast(msglista, saida);

			} else {
				output.write(msg.bytearq);
//			System.out.println(arqAbertos.toString());
			}

		}
	}
}
