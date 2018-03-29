package com.projetocepordenado.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Busca {
	
	private CEP cep;
	private String cepDigitado;
	private RandomAccessFile randomFile; 
	private static int numLoops;
	
	public Busca(String cepDigitado) throws IOException {
		
		try {
			this.cepDigitado = cepDigitado;
			this.randomFile = new RandomAccessFile(new File("data/CepOrdenadoMenor.dat").getAbsolutePath(), "r");
			this.cep = new CEP();
		}
		catch (FileNotFoundException exception) {
			System.out.println("Arquivo não encontrado");
		}
	}
	
	public void retornaResultado() {
        try{
            long raiz = 0;
            long bot = ((this.randomFile.length()/300)-1);
            long meio = 0;
           
            while (raiz <= bot) {
                meio = ( ( raiz + bot )  / 2 );
                this.randomFile.seek(meio*300);
                this.cep.leEndereco(this.randomFile);
                
                if(Integer.parseInt(this.cep.getCep())==Integer.parseInt(this.cepDigitado)){
                    System.out.println(this.cep.getLogradouro());
                    System.out.println(this.cep.getBairro());
                    System.out.println(this.cep.getCidade());
                    System.out.println(this.cep.getEstado());
                    System.out.println(this.cep.getSigla());
                    System.out.println(this.cep.getCep());
                    break;
                }
                
                else if (Integer.parseInt( this.cep.getCep()) > Integer.parseInt(this.cepDigitado) )
                         bot = meio - 1; // Vai para o filho da esquerda
                else if (Integer.parseInt( this.cep.getCep() ) < Integer.parseInt(this.cepDigitado) )
                         raiz = meio + 1; // Vai para o filho da direita
                
                numLoops++;
            }
            System.out.println("Numero total de consultas: "+numLoops);
            this.randomFile.close();
        }
        catch(Exception e) {
        	System.out.println("Ocorreu um erro ao rodar a busca. Tente novamente.");
        }    	
    }
}
