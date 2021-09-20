
package tp1_s_des;

import java.util.BitSet;
import java.util.ArrayList;
import java.util.List;

/** Fait par : (Binôme) 
 * @author BENNACER Djamila
 *  MEHRABI JEYHOUNABADI Milad 
 * 
 * Pour la répartition du travail :
 * La moitié du TP a été faite avant le confinement, ce qui nous a permis de travailler ensemble au même endroit.
 * Par la suite nous avons utilisé discord pour continuer le TP.
 * 
 *
 * 
 * 
 * Module : Sécurité et théorie de l'information
 */
public class TP1_S_DES {

    /**
     * @param args the command line arguments
     */
    private BitSet P10; //Clé de 10 bits
    
    //fait par milad  
    
    //Je vais verifier la validité de la clé saisie si elle n'ai pas valide je genére une exception  
    //La clé saisie doit être en binaire 0 ou 1
      //Je garde une trace de la clé utilisée 
      public TP1_S_DES(String cleUt){//clé utilisée 
     if (!cleUt.matches("[0,1]{10}"))                   
     throw new IllegalArgumentException("ERREUR!!, veuillez saisir une clé de 10 bits contenant que des 0 et 1");
        
     //Convertir de String vers BitSet
     BitSet cle = new BitSet(10); 
		for(int i=0; i < 10; i++){// De 0 a 9 ce qui nous donne 10 bits
			if(cleUt.charAt(i) == '1') cle.set(i);
		}
		P10 = cle; // P10 reçoit la clé valide
                
    }
   
  //fait par milad
      
// Fonction qui permet d'assister le changement d'un bits(valeur booleen) vers Bitset
// bitModif c'est lui qui sera modifé 
// i : l'indice du bitModif à modifer 
    private void changeBit(BitSet bitModif, int i, boolean nvValeur){
		if (nvValeur){
			bitModif.set(i); // VRAI alors on modifie 
		}else{
			bitModif.clear(i); //FAUX alors on supprime
		}
	}
    
    //fait par Djamila
    
	 // on à commencé par 0 au lieu de 1 par rapport a l'enoncé du TP,le resultat est bien evidemment correcte
    //C'est pour cela que c'est décalé de 1
	//P10 permutation 
          // la clé donnée par l'utilisateur {0,1,2,3,4,5,6,7,8,9}
	  // La nouvelle clé aprés permutation {2,4,1,6,3,9,0,8,7,5}	
           
	private BitSet p10(BitSet cle){
		BitSet clePermuter = new BitSet(10);
		changeBit(clePermuter, 0, cle.get(2));  
		changeBit(clePermuter, 1, cle.get(4));
		changeBit(clePermuter, 2, cle.get(1));
		changeBit(clePermuter, 3, cle.get(6));
		changeBit(clePermuter, 4, cle.get(3));
		changeBit(clePermuter, 5, cle.get(9));
		changeBit(clePermuter, 6, cle.get(0));
		changeBit(clePermuter, 7, cle.get(8));
		changeBit(clePermuter, 8, cle.get(7));
		changeBit(clePermuter, 9, cle.get(5));
		return clePermuter;
	}
    
        //fait par Djamila
        
        //rotation(circular left shift)
	 //separer en 5 bits chacun
	private BitSet rotation( BitSet cle, int decalage){
		BitSet CleR= new BitSet(10);	
                //5 bits gauche de la clé 
		for (int i=0; i < 5; i++){
			boolean bVal = cle.get(i);
			int decalageI = i - decalage;
			if (decalageI < 0){
				if(bVal){CleR.set(5+decalageI);	
				}else{CleR.clear(5+decalageI);}
			}else{
				if(bVal){CleR.set(decalageI);
				}else{CleR.clear(decalageI);}}
		}
		//5 bits restant(ceux de droite)
		for (int i=5; i < 10; i++){
			boolean bVal = cle.get(i);
			int decalageI = i - decalage;
			if (decalageI < 5){
				if(bVal){CleR.set(5+decalageI);
				}else{CleR.clear(5+decalageI);}
			}else{
				if(bVal){CleR.set(decalageI);
				}else{CleR.clear(decalageI);}
			}
		}
		return CleR; // nouvelle clé aprés la rotation
	}
        
        
        
        // Permutation de P8
        //clé de 8 bits aprés permutation {5,2,6,3,7,4,9,8}
	
	private BitSet p8(BitSet cle){
		BitSet p8 = new BitSet(8);
		changeBit(p8, 0, cle.get(5));
		changeBit(p8, 1, cle.get(2));
		changeBit(p8, 2, cle.get(6));
		changeBit(p8, 3, cle.get(3));
		changeBit(p8, 4, cle.get(7));
		changeBit(p8, 5, cle.get(4));
		changeBit(p8, 6, cle.get(9));
		changeBit(p8, 7, cle.get(8));
		return p8;
	}
        
        
         //ICi nous allons retourner une copie de la clé et garder l'originale 
    public BitSet getCle(){
		return (BitSet) this.P10.clone();
	}
       
    //fait par Milad
    
    //Génerer les clés 
	private List<BitSet> genererCles(){
            //on récupère la clé dans p10
		BitSet p10 = p10(this.getCle());
	//on fait la rotation (circular left shift) de p10 a gauche avec un decalage de 1 
		BitSet b10 = rotation(p10, 1);
	//K1 premiére clé (clé de 8 bits)	
		BitSet k1 = p8(b10);
	//on fait la rotation (circular left shift) de b10 avec un decalage de 2 	
		BitSet c2 = rotation(b10,2);
	//K2 deuxiéme clé (clé de 8 bits) 
		BitSet k2 = p8(c2);
	//* K1 et K2 sont les touches rondes pour les deux premiers tours et sont utilisées comme entrée des fonctions rondes	
		List<BitSet> cles = new ArrayList<>();
		cles.add(k1);
		cles.add(k2);
		return cles;//notre liste de clés K1 ET K2
	}
        
        //Fait par Djamila
        
   //G3-S-DES CRYPTER (ENCRYPTION)
   //IP permutation initiale      
   // L'entrée de l'algorithme est un bloc de 8 bits de texte en clair,
        //que nous permutons d'abord à l'aide de la fonction IP:
	private BitSet ip(BitSet textClair){
		BitSet ip = new BitSet(8);
		changeBit(ip, 0, textClair.get(1));
		changeBit(ip, 1, textClair.get(5));
		changeBit(ip, 2, textClair.get(2));
		changeBit(ip, 3, textClair.get(0));
		changeBit(ip, 4, textClair.get(3));
		changeBit(ip, 5, textClair.get(7));
		changeBit(ip, 6, textClair.get(4));
		changeBit(ip, 7, textClair.get(6));
		return ip;
	}
        
        
    //IP_1 : fait une re-permutation 
    //Cela conserve les 8 bits du texte en clair mais les mélange.  
        //A la fin de l'algorithme, l'inverse de la permutation est utilisée:   
	public BitSet ip_1(BitSet permuteText){
		BitSet ip_1 = new BitSet();
		changeBit(ip_1, 0, permuteText.get(3));
		changeBit(ip_1, 1, permuteText.get(0));
		changeBit(ip_1, 2, permuteText.get(2));
		changeBit(ip_1, 3, permuteText.get(4));
		changeBit(ip_1, 4, permuteText.get(6));
		changeBit(ip_1, 5, permuteText.get(1));
		changeBit(ip_1, 6, permuteText.get(7));
		changeBit(ip_1, 7, permuteText.get(5));
		return ip_1;  
	}
	
        //fait par Djamila
        
	 //Expansion/Permutation 	 
	 //Entrée	{0,1,2,3}	 
	 //Sortie	{3,0,1,2,1,2,3,0}
	 
	public BitSet ep(BitSet input){
		BitSet ep = new BitSet(8);
		if(input.get(0)){
			ep.set(1);
			ep.set(7);
		}
		if(input.get(1)){
			ep.set(2);
			ep.set(4);
		}
		if(input.get(2)){
			ep.set(3);
			ep.set(5);
		}
		if(input.get(3)){
			ep.set(0);
			ep.set(6);
		}
		return ep;
	}
        
        
        // notre fonction xor
	private BitSet xor( BitSet bit1, BitSet bit2){
		BitSet xr = (BitSet) bit1.clone();
		xr.xor(bit2);
		return xr;
	}
        
        
        
	
     //fait par Milad
       /*Les 4 premiers bits (première ligne) sont introduits
        dans la boîte S S0 pour produire une sortie à 2 bits, et les 4 bits restants (deuxième ligne)
        sont introduits dans S1 pour produire une autre sortie à 2 bits*/
        
    private static final boolean [][][] S0 = new boolean[4][4][2];
    private static final boolean [][][] S1 = new boolean[4][4][2];
      static {
                S0[0][0] = new boolean[]{false,true};
		S0[0][1] = new boolean[]{false,false};
		S0[0][2] = new boolean[]{true,true};
		S0[0][3] = new boolean[]{true,false};
		S0[1][0] = new boolean[]{true,true};
		S0[1][1] = new boolean[]{true,false};
		S0[1][2] = new boolean[]{false,true};
		S0[1][3] = new boolean[]{false,false};
		S0[2][0] = new boolean[]{false,false};
		S0[2][1] = new boolean[]{true,false};
		S0[2][2] = new boolean[]{false,true};
		S0[2][3] = new boolean[]{true,true};
		S0[3][0] = new boolean[]{true,true};
		S0[3][1] = new boolean[]{false,true};
		S0[3][2] = new boolean[]{true,true};
		S0[3][3] = new boolean[]{true,false};
                
                S1[0][0] = new boolean[]{false,false};
		S1[0][1] = new boolean[]{false,true};
		S1[0][2] = new boolean[]{true,false};
		S1[0][3] = new boolean[]{true,true};
		S1[1][0] = new boolean[]{true,false};
		S1[1][1] = new boolean[]{false,false};
		S1[1][2] = new boolean[]{false,true};
		S1[1][3] = new boolean[]{true,true};
		S1[2][0] = new boolean[]{true,true};
		S1[2][1] = new boolean[]{false,false};
		S1[2][2] = new boolean[]{false,true};
		S1[2][3] = new boolean[]{false,false};
		S1[3][0] = new boolean[]{true,false};
		S1[3][1] = new boolean[]{false,true};
		S1[3][2] = new boolean[]{false,false};
		S1[3][3] = new boolean[]{true,true};
      }
    
      //S-box fonction de transformation
        //entrée 4 bits
        //sortie 2 bits
	private BitSet s_box(BitSet entree,boolean [][][] s){
		boolean p0, p1, p2, p3;
		p0 = entree.get(0);
		p1 = entree.get(1);
		p2 = entree.get(2);
		p3 = entree.get(3);
		
		int ligne=0;
		if (!p0 && !p3) ligne = 0;
		if (!p0 && p3) ligne = 1;
		if (p0 && !p3) ligne = 2;
		if (p0 && p3) ligne = 3;
		
		int col=0;
		if (!p1 && !p2) col = 0;
		if (!p1 && p2) col = 1;
		if (p1 && !p2) col = 2;
		if (p1 && p2) col = 3;
		
		BitSet res = new BitSet(2);
		boolean[] val = s[ligne][col];
		if (val[0]){
			res.set(0);
		}
		if (val[1]){
			res.set(1);
		}
		return res;
	}
         
        private BitSet p4(BitSet part1, BitSet part2){
		BitSet p4 = new BitSet(4);
		changeBit(p4, 0, part1.get(1));
		changeBit(p4, 1, part2.get(1));
		changeBit(p4, 2, part2.get(0));
		changeBit(p4, 3, part1.get(0));
		return p4;
	}
      //La sortie de P4 est la sortie de la fonction F
	private BitSet f(BitSet droit, BitSet sk){
		BitSet ep = ep(droit);
		BitSet xor = xor(ep,sk);
		BitSet s0 = s_box(xor.get(0, 4), S0);
		BitSet s1 = s_box(xor.get(4, 8), S1);
		return p4(s0,s1);
	}
	//Notre fonction fk
      //  La fonction fK ne modifie que les 4 bits les plus à gauche de l'entrée
	private BitSet fK(BitSet bits, BitSet cle){
		BitSet f = f(bits.get(4, 8), cle);
		BitSet x = xor(bits.get(0, 4), f);
		BitSet c = new BitSet(8);
		for(int i=0; i<4;i++){
			if(x.get(i)){
				c.set(i);
			}
		}
		for(int i=4; i<8;i++){
			if(bits.get(i)){c.set(i);}
		} 
                return c;}
	
        
        
      //Entrée :  
   //Le BitSet à inverser 
   //La longueur doit être de 8 bits
//Sortie : Le BitSet inversé donne :
//les 4 derniers bits deviennent les 4 premiers et les 4 premiers deviennent dernier 
        //Fonction switch
	private BitSet sw(BitSet entree){
		BitSet inverse = new BitSet(8);
		for(int i=0; i<4;i++){
			if(entree.get(i)){inverse.set(i+4);}
		}
		for(int i=4; i<8;i++){
			if(entree.get(i)){inverse.set(i-4);}
		}
		return inverse;
	}
	
	
    // crypter 
    // "c" est le caractére a crypter 
	public char crypter(char c){
		//Generate and get keys
		List<BitSet> cles = genererCles();
		BitSet k1 = cles.get(0);
		BitSet k2 = cles.get(1);
		
		
        //Convertir un caractère en chaîne binaire, et ne pas mettre les 0 a gauche, on s'arrête au dernier 1
		String binaire = Integer.toBinaryString((int)c);
		if (binaire.length() > 8)
                throw new IllegalArgumentException("WARNING!!les caractères doivent être encodés sur 8 bits");
		
		BitSet b = new BitSet(8);
		int index = 7;
		for(int i = binaire.length()-1; i >= 0; i--){
			if (binaire.charAt(i) == '1'){
				b.set(index);
			}
			index--;
		}
		
		BitSet ip = ip(b);
		BitSet fk1 = fK(ip, k1);
		BitSet inverse = sw(fk1);
		BitSet fk2 = fK(inverse, k2);
		BitSet enc = ip_1(fk2);
		
		StringBuilder res = new StringBuilder();
		for (int i=0; i<8; i++){
			if (enc.get(i)){res.append("1");
			}else{res.append("0");}
		}
		
		int i = Integer.parseInt(res.toString(), 2);
		return (char)i;
	}
	
        //Fait par Djamila
        
        // decrypter le message 
    // "c" est le caractére a decryper
        // nous retourne le caractére en clair
	public char decrypter(char c){
            	
		
		String binary = Integer.toBinaryString((int)c);
		if (binary.length() > 8)
                   throw new IllegalArgumentException("WARNING!!les caractères doivent être encodés sur 8 bits");
		BitSet bit = new BitSet(8);
		int x = 7;
                
		for(int i = binary.length()-1; i>=0; i--){
			if (binary.charAt(i) == '1'){ bit.set(x);}
			x--;}
                
                List<BitSet> keys = genererCles();
		BitSet k1 = keys.get(0);
		BitSet k2 = keys.get(1);
                
		BitSet ip = ip(bit);
		BitSet fk1 = fK(ip,k2);
		BitSet inverse = sw(fk1);
		BitSet fk2 = fK(inverse, k1);
		BitSet dec = ip_1(fk2);
		
		StringBuilder don = new StringBuilder();
		for (int i=0; i<8; i++){
			if (dec.get(i)){ don.append("1");
			}else{ don.append("0");}
		}
		int i = Integer.parseInt(don.toString(), 2);
		return (char)i;
		
	}
	
        
    
   

    
   
}
