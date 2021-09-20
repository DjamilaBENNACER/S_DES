/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1_s_des;

/**
 *
 * @author BENNACER Djamila
 */
public class MAIN {
    public static void main(String[] args) {
        
        
        StringBuilder msgEnc = new StringBuilder();//message chiffré
        StringBuilder msgDec = new StringBuilder();//message dechiffré (message en clair)

        
        String cle = "1010000010", message = "Bonjour, je suis le message à chiffrer";
    	TP1_S_DES sdes = new TP1_S_DES(cle);
        
        System.out.println("**Bonjour je suis le code de S_DES : \n"
                + "**Voici la clé que nous allons utilisé : "+ cle+"\n");
        
    	System.out.println("**Message a chiffrer : " + message);
    System.out.println("\n**Message chiffré est : ");
    	for (int i=0; i<message.length();i++){msgEnc.append(sdes.crypter(message.charAt(i)));}    	 
    	System.out.println(msgEnc.toString());
        
    	for (int i=0; i<msgEnc.length();i++){msgDec.append(sdes.decrypter(msgEnc.charAt(i)));}
    	System.out.println("\n**Message une fois déchiffrer est : " + msgDec +"\n \n Merci");
   
    
    }
    
}
