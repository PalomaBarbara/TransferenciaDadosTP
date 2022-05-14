package transferenciadadostp;

import java.util.Random;

public class Transmissor {
    private String mensagem;

    public Transmissor(String mensagem) {
        this.mensagem = mensagem;
    }
    
    //convertendo um símbolo para "vetor" de boolean (bits)
    private boolean[] streamCaracter(char simbolo){
        
        //cada símbolo da tabela ASCII é representado com 8 bits
        boolean bits[] = new boolean[8];
        
        //convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
        int valorSimbolo = (int) simbolo;
        int indice = 7;
        
        //convertendo cada "bits" do valor da tabela ASCII
        while(valorSimbolo >= 2){
            int resto = valorSimbolo % 2;
            valorSimbolo /= 2;
            bits[indice] = (resto == 1);
            indice--;
        }
        bits[indice] = (valorSimbolo == 1);
        
        return bits;
    } 
    
    //não modifique (seu objetivo é corrigir esse erro gerado no receptor)
    private void geradorRuido(boolean bits[]){
        Random geradorAleatorio = new Random();
        
        //pode gerar um erro ou não..
        if(geradorAleatorio.nextInt(5) > 1){
            int indice = geradorAleatorio.nextInt(8);
            bits[indice] = !bits[indice];
        }
    }
    
    private boolean[] dadoBitsHamming(boolean bits[]){
        
        boolean bitsHamming[] = new boolean [12]; //vetor para receber os 8 bits + 4 bits de hemming
        //método Boolean que recebe e acrescenta os números de Hamming
        
        int contador = 0;
        
        //loop para incluir no vetor as posições de hamming 1,2,4 e 8
        for(int posicaoHamming = 0; posicaoHamming < bitsHamming.length; posicaoHamming ++){
            
            if (posicaoHamming != 0 && posicaoHamming != 1 && posicaoHamming != 3 && posicaoHamming !=7){
                bitsHamming[posicaoHamming] = bits [contador];
                contador++;
            }
        }

        //executando XOR para aplicação do código de hamming
        bitsHamming[0] = bitsHamming[2] ^ bitsHamming[4] ^ bitsHamming[6] ^ bitsHamming[8] ^ bitsHamming[10];
        bitsHamming[1] = bitsHamming[2] ^ bitsHamming[5] ^ bitsHamming[6] ^ bitsHamming[9] ^ bitsHamming[10];
        bitsHamming[3] = bitsHamming[4] ^ bitsHamming[5] ^ bitsHamming[6] ^ bitsHamming[11];
        bitsHamming[7] = bitsHamming[8] ^ bitsHamming[9] ^ bitsHamming[10] ^ bitsHamming[11];

        return bitsHamming;
    }
    
    public void enviaDado(Receptor receptor){
        for(int i = 0; i < this.mensagem.length();i++){
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));
            
            System.out.println("\n____________________________________________________________________________");
            
            System.out.println("Analisando letra: " + this.mensagem.charAt(i));
            
            //add os dados de hamming
            boolean bitsHamming[] = dadoBitsHamming(bits);
            
            System.out.println("Bits sem ruido:");
            for(int j = 0; j < bitsHamming.length; j++){
                System.out.print(bitsHamming[j] + " ");
            }            
            
            //add ruidos na mensagem a ser enviada para o receptor
            geradorRuido(bitsHamming);
            
            System.out.println("\nBits com ruido:");
            for(int k = 0; k < bitsHamming.length; k++){
                System.out.print(bitsHamming[k] + " ");
            }  
            
            //enviando a mensagem "pela rede" para o receptor
            receptor.receberDadoBits(bitsHamming);
        }
    }
}