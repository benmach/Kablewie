package ConnectFour;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;



public class P4 extends JFrame implements MouseListener {
	   public final static int LARGEUR_COLONNE = 40;
	   public final static int DECALLAGE_HORIZONTAL = 14;
	   private static final int FORM_WIDTH = 300;
	    private static final int FORM_HEIGHT = 400;

	private static final long serialVersionUID = 1L;
	private Etat etatCourant; 
	private Button quitter;
	private int gagnant;

	public P4() {
		setLocation(FORM_WIDTH, FORM_HEIGHT);
		this.etatCourant=new Etat();
		this.setLayout(null);
		this.quitter = new Button("Quitter");
		this.quitter.setLocation(10,305);
		this.quitter.setSize(290,30);
		this.add(quitter);
		this.addMouseListener(this);
		this.quitter.addMouseListener(this);
		this.gagnant = Etat.PERSONNE;
	}

	 public int colonneCliquee(int coordonneeHorizontaleEnPixels) {
		//-------------------------------------------------
		// Entree : la coordonnee horizontale du pixel ou a eu lieu de clic.
		// Sortie : retourne l'index de la colonne ou a eu lieu le clic.
		     return (coordonneeHorizontaleEnPixels-DECALLAGE_HORIZONTAL)/LARGEUR_COLONNE;
		   }
	public void mousePressed(MouseEvent evt) {
		if (evt.getSource() == this.quitter) {
			System.exit(0);
		}
		if ((this.gagnant==Etat.PERSONNE) && ((evt.getX()<300) && (this.etatCourant.peutDeposerPion(this.colonneCliquee(evt.getX())))))
			if ((evt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
				this.etatCourant.deposerPion( this.colonneCliquee(evt.getX()), 1);
				if (!this.etatCourant.feuille())
					this.etatCourant.deposerPion(this.etatCourant.meilleurCoupPourOrdinateur(), Etat.ORDI );        
				this.gagnant=this.etatCourant.gagnant();
				if (this.gagnant==Etat.HUMAIN) {
					this.quitter.setLabel("Vous avez gagné... Quitter"); 
				}
				if (gagnant==Etat.ORDI) {
					this.quitter.setLabel("J'ai gagné !!! ... Quitter"); 
				}
				this.dessine(getGraphics());
			}
	}

	public void paint(Graphics g) {
		this.dessine(g);
	}
	   public Color couleur(int joueur) {
		 //-------------------------------------------------
		 // Entree : joueur vaut PERSONNE, HUMAIN ou ORDI.
		 // Sortie : retourne la couleur correspondant a joueur.
		       Color aRetourner;
		       switch (joueur) {
		          case Etat.HUMAIN   : aRetourner=Color.orange; break;
		          case Etat.ORDI     : aRetourner=Color.red; break;
		          default       : aRetourner=Color.white; break;
		       }
		       return aRetourner;
		    }


		    void dessine(java.awt.Graphics g) {
		 //-------------------------------------------------
		 // Sortie : dessine la grille avec les pions qui la remplissent
		       int col, lig;
		       g.setColor(Color.blue);
		       g.fillRect(10,30,290,270);
		       for (col=0; col<Etat.NB_COLONNES ; col++ ) {
		          for (lig=0 ; lig<Etat.NB_LIGNES ; lig++) {
		             g.setColor(Color.white);
		             g.fillOval(DECALLAGE_HORIZONTAL+1+(col*LARGEUR_COLONNE),242-(lig*LARGEUR_COLONNE),LARGEUR_COLONNE-4, LARGEUR_COLONNE-4);
		             g.setColor(couleur(this.etatCourant.get(col, lig)));
		             g.fillOval(DECALLAGE_HORIZONTAL+3+(col*LARGEUR_COLONNE),244-(lig*LARGEUR_COLONNE),LARGEUR_COLONNE-8, LARGEUR_COLONNE-8);
		          }
		       }
		    }
	public void mouseReleased(MouseEvent evt)  {}
	public void mouseEntered(MouseEvent evt)  {}
	public void mouseExited(MouseEvent evt)  {}
	public void mouseClicked(MouseEvent evt) {}

	public static void main(String[] argv) {
		//-------------------------------------------------
		P4 jeu=new P4();
		jeu.setSize(310,340);
		jeu.setVisible(true);
		
	}
}
