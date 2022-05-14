package transferenciadadostp;

import javax.xml.transform.Source;

public class Receptor {

    //mensagem recebida pelo transmissor
    private String mensagem;    
            
    public Receptor() {
        
        //mensagem vazia no inicio da execução
        this.mensagem = "\n____________________________________________________________________________\nA mensagem recebida é: ";
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    private void decodificarDado(boolean bits[]){
        int codigoAscii = 0;
        int expoente = bits.length-1;
        
        //convertendo os "bits" para valor inteiro para então encontrar o valor tabela ASCII
        for(int i = 0; i < bits.length;i++){
            if(bits[i]){
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;
        }
        
        //concatenando cada simbolo na mensagem original
        this.mensagem += (char)codigoAscii;
    }
    
    private boolean[] decodificarDadoHamming(boolean[] bits){        
        
        //verificação de existencia de ruídos e sua correção
        boolean bitsposicao01 =  bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        boolean bitsposicao02 =  bits[1] ^ bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        boolean bitsposicao04 =  bits[3] ^ bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
        boolean bitsposicao08 =  bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];

        String verificacaoMensagem = bitsposicao08 + "" + bitsposicao04 + "" + bitsposicao02 + "" + bitsposicao01;
        
        verificacaoMensagem = verificacaoMensagem.replaceAll("true", "1").replaceAll("false", "0");
        
        if (!verificacaoMensagem.equals ("0000")){ // o ! nega a condição de equals
            System.out.println("\nDurante a transmissão houve um problema no bit: " + verificacaoMensagem.replaceAll("true", "1").replaceAll("false", "0"));
            
            if (verificacaoMensagem.equals("0001")){
                System.out.println("Posição com erro: 1");
                bits [0] = !bits[0];
            }
            if (verificacaoMensagem.equals("0010")){
                System.out.println("Posição com erro: 2");
                bits [1] = !bits [1];
            }
            if (verificacaoMensagem.equals("0011")){
                System.out.println("Posição com erro: 3");
                bits [2] = !bits [2];
            }
            if (verificacaoMensagem.equals("0100")){
                System.out.println("Posição com erro: 4");
                bits [3] = !bits [3];
            }
            if (verificacaoMensagem.equals("0101")){
                System.out.println("Posição com erro: 5");
                bits [4] = !bits [4];
            }
            if (verificacaoMensagem.equals("0110")){
                System.out.println("Posição com erro: 6");
                bits [5] = !bits [5];
            }
            if (verificacaoMensagem.equals("0111")){
                System.out.println("Posição com erro: 7");
                bits [6] = !bits [6];
            }
            if (verificacaoMensagem.equals("1000")){
                System.out.println("Posição com erro: 8");
                bits [7] = !bits [7];
            }
            if (verificacaoMensagem.equals("1001")){
                System.out.println("Posição com erro: 9");
                bits [8] = !bits [8];
            }
            if (verificacaoMensagem.equals("1010")){
                System.out.println("Posição com erro: 10");
                bits [9] = !bits [9];
            }
            if (verificacaoMensagem.equals("1011")){
                System.out.println("Posição com erro: 11");
                bits [10] = !bits [10];
            }  
            if (verificacaoMensagem.equals("1100")){
                System.out.println("Posição com erro: 12");
                bits [11] = !bits [11];
            }
        } 
        
        else{ //mensagem caso não exista ruído
            System.out.println("\nMensagem sem ruido!");
        }

        //vetor para receber o número corrigido 
        boolean bitsCorrigidos[] = new boolean [8]; 
        int contador = 0;

        //loop para incluir no vetor as posições de hamming 1,2,4 e 8
        for(int posicaoSemHamming = 0; posicaoSemHamming < bits.length; posicaoSemHamming ++){
            
            if (posicaoSemHamming != 0 && posicaoSemHamming != 1 && posicaoSemHamming != 3 && posicaoSemHamming !=7){
                
                bitsCorrigidos[contador] = bits [posicaoSemHamming];
                contador++;
            }
        }
        return bitsCorrigidos;
    }
        
    //recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]){
        
        //mensagem corrigida
        boolean bitsCorrigidos[] = decodificarDadoHamming(bits);
        
        //decodificarDadoHamming (bitsCorrigidos); 
        decodificarDado(bitsCorrigidos);
    }
}
