package ConnectFour;

public class Etat {

	// CONSTANTES
	// -----------
	public final static int NB_COLONNES = 7;
	public final static int NB_LIGNES = 6;

	public final static int PERSONNE = 0;
	public final static int HUMAIN = 1;
	public final static int ORDI = 2;

	public final static int PROFONDEUR_MAX = 6;

	// VARIABLES D'INSTANCE (ATTRIBUTS)
	// --------------------------------
	private int grille[][];
	// grille[c][l] vaut PERSONNE, HUMAIN ou ORDI selon que la
	// case a la colonne c et la ligne l est occupee par
	// personne, un pion du joueur ou un pion de l'ordinateur.

	// METHODES
	// --------
	public Etat() {
		// -------------------------------------------------
		// Constructeur initialisant la grille de sorte que
		// chaque case est inoccupee (~est occupee par PERSONNE)
		this.grille = new int[NB_COLONNES][NB_LIGNES];
		for (int col = 0; col < NB_COLONNES; col++) {
			for (int lig = 0; lig < NB_LIGNES; lig++) {
				this.grille[col][lig] = PERSONNE;
			}
		}
	}

	public void recopier(Etat e) {
		// -------------------------------------------------
		// Entree : e, un etat
		// Sortie : l'etat est modifie pour correspondre a e.
		for (int col = 0; col < NB_COLONNES; col++) {
			for (int lig = 0; lig < NB_LIGNES; lig++) {
				this.grille[col][lig] = e.grille[col][lig];
			}
		}
	}

	public int get(int c, int l) {
		if (c >= 0 && c < this.grille.length && l >= 0
				&& l < this.grille[c].length) {
			return this.grille[c][l];
		} else {
			return Etat.PERSONNE;
		}
	}

	public int adversaire(int joueur) {
		// -------------------------------------------------
		// Entree : joueur vaut HUMAIN ou ORDI
		// Sortie : retourne ORDI si joueur vaut joueur, HUMAIN sinon.
		if (joueur == HUMAIN) {
			return ORDI;
		} else {
			return HUMAIN;
		}
	}

	public boolean peutDeposerPion(int colonne) {
		// -------------------------------------------------
		// Sortie : retourne true si et seulement si la
		// colonne colonne n'est pas completement remplie.
		return (this.grille[colonne][NB_LIGNES - 1] == 0);
	}

	public void deposerPion(int colonne, int joueur) {
		// -------------------------------------------------
		// Entree : joueur vaut HUMAIN ou ORDI, et colonne est dans [0,
		// NB_COLONNES[
		// Sortie : depose un pion du joueur joueur dans
		// la colonne colonne si celle-ci n'est pas pleine.
		int ligne = 0;
		while ((ligne < NB_LIGNES)
				&& (this.grille[colonne][ligne] != PERSONNE)) {
			ligne++;
		}
		if (ligne < NB_LIGNES) {
			this.grille[colonne][ligne] = joueur;
		}
	}

	public boolean feuille() {
		// -------------------------------------------------
		// Sortie : retourne true si l'etat est une feuille,
		// c'est a dire si il existe un gagnant ou
		// si la grille est pleine.
		if (this.gagnant() != PERSONNE) {
			return true;
		}
		int col = 0;
		boolean toutesPleines = true;
		while ((toutesPleines) && (col < NB_COLONNES)) {
			if (this.peutDeposerPion(col)) {
				toutesPleines = false;
			} else {
				col++;
			}
		}
		return toutesPleines;
	}

	public int gagnantHorizontal(int col, int lig) {
		// -------------------------------------------------
		// Entree : col est dans [0, NB_COLONNES-4], et lig
		// est dans [0, NB_LIGNES]
		// Sortie : retourne PERSONNE si les 4 cases horizontales
		// de (col,lig) a (col+3, lig) n'appartiennent
		// pas toutes au meme joueur. Sinon, retourne
		// HUMAIN ou ORDI selon a quel joueur appartiennent
		// les 4 cases.
		if ((this.grille[col][lig] == this.grille[col + 1][lig])
				&& (this.grille[col][lig] == this.grille[col + 2][lig])
				&& (this.grille[col][lig] == this.grille[col + 3][lig])) {
			return this.grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	int gagnantVertical(int col, int lig) {
		if ((grille[col][lig] == grille[col][lig + 1])
				&& (grille[col][lig] == grille[col][lig + 2])
				&& (grille[col][lig] == grille[col][lig + 3])) {
			return grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	int gagnantDiagonale1(int col, int lig) {
		if ((grille[col][lig] == grille[col + 1][lig + 1])
				&& (grille[col][lig] == grille[col + 2][lig + 2])
				&& (grille[col][lig] == grille[col + 3][lig + 3])) {
			return grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	int gagnantDiagonale2(int col, int lig) {
		if ((grille[col][lig] == grille[col + 1][lig - 1])
				&& (grille[col][lig] == grille[col + 2][lig - 2])
				&& (grille[col][lig] == grille[col + 3][lig - 3])) {
			return grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	int gagnant() {
		// Sortie : Si il existe 4 cases alignees dans la grille, retourne le
		// proprietaire des 4 cases, sinon
		// retourne PERSONNE.
		int vainqueur = PERSONNE;
		int col = 0, lig;
		while ((vainqueur == PERSONNE) && (col < NB_COLONNES)) {
			lig = 0;
			while ((vainqueur == PERSONNE) && (lig < NB_LIGNES)) {
				if ((col < (NB_COLONNES - 3))
						&& (gagnantHorizontal(col, lig) != PERSONNE)) {
					vainqueur = gagnantHorizontal(col, lig);
				}
				if ((vainqueur == PERSONNE) && (lig < (NB_LIGNES - 3))
						&& (gagnantVertical(col, lig) != PERSONNE)) {
					vainqueur = gagnantVertical(col, lig);
				}
				if ((vainqueur == PERSONNE) && (col < NB_COLONNES - 3)
						&& (lig < NB_LIGNES - 3)
						&& (gagnantDiagonale1(col, lig) != PERSONNE)) {
					vainqueur = gagnantDiagonale1(col, lig);
				}
				if ((vainqueur == PERSONNE) && (col < NB_COLONNES - 3)
						&& (lig > 2)
						&& (gagnantDiagonale2(col, lig) != PERSONNE)) {
					vainqueur = gagnantDiagonale2(col, lig);
				}
				lig++;
			}
			col++;
		}
		return vainqueur;
	}

	public int gagnantDiagonaleGauche(int col, int lig) {
		// -------------------------------------------------
		// Entree : col est dans [0, NB_COLONNES-4], et lig
		// est dans [0, NB_LIGNES-4]
		// Sortie : retourne PERSONNE si les 4 cases diagonales
		// n'appartiennent
		// pas toutes au meme joueur. Sinon, retourne
		// HUMAIN ou ORDI selon a quel joueur appartiennent
		// les 4 cases.
		if ((this.grille[col][lig] == this.grille[col + 1][lig + 1])
				&& (this.grille[col][lig] == this.grille[col + 2][lig + 2])
				&& (this.grille[col][lig] == this.grille[col + 3][lig + 3])) {
			return this.grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	public int gagnantDiagonaleDroite(int col, int lig) {
		// -------------------------------------------------
		// Entree : col est dans [NB_COLONNES-4, NB_COLONNES], et lig
		// est dans [0, NB_LIGNES-4]
		// Sortie : retourne PERSONNE si les 4 cases diagonales
		// n'appartiennent
		// pas toutes au meme joueur. Sinon, retourne
		// HUMAIN ou ORDI selon a quel joueur appartiennent
		// les 4 cases.
		if ((this.grille[col][lig] == this.grille[col - 1][lig + 1])
				&& (this.grille[col][lig] == this.grille[col - 2][lig + 2])
				&& (this.grille[col][lig] == this.grille[col - 3][lig + 3])) {
			return this.grille[col][lig];
		} else {
			return PERSONNE;
		}
	}

	// POUR LA QUESTION 3 UNIQUEMENT !!!!
	public int estimeHorizontal(int col, int lig, int joueur) {
		// -------------------------------------------------
		int adv = adversaire(joueur);
		if ((this.grille[col][lig] != adv) && (this.grille[col + 1][lig] != adv)
				&& (this.grille[col + 2][lig] != adv)
				&& (this.grille[col + 3][lig] != adv)) {
			return (int) Math.pow(2,
					(this.grille[col][lig] + this.grille[col + 1][lig]
							+ this.grille[col + 2][lig]
							+ this.grille[col + 3][lig]) / joueur);
		} else {
			return 0;
		}
	}

	int estimeVertical(int col, int lig, int joueur) {
		int adv = adversaire(joueur);
		if ((grille[col][lig] != adv) && (grille[col][lig + 1] != adv)
				&& (grille[col][lig + 2] != adv)
				&& (grille[col][lig + 3] != adv))
			return (int) Math.pow(2,
					((grille[col][lig] + grille[col][lig + 1]
							+ grille[col][lig + 2] + grille[col][lig + 3])
							/ joueur));
		else
			return 0;
	}

	int estimeDiagonale1(int col, int lig, int joueur) {
		int adv = adversaire(joueur);
		if ((grille[col][lig] != adv) && (grille[col + 1][lig + 1] != adv)
				&& (grille[col + 2][lig + 2] != adv)
				&& (grille[col + 3][lig + 3] != adv))
			return (int) Math.pow(2,
					((grille[col][lig] + grille[col + 1][lig + 1]
							+ grille[col + 2][lig + 2]
							+ grille[col + 3][lig + 3]) / joueur));
		else
			return 0;
	}

	int estimeDiagonale2(int col, int lig, int joueur) {
		int adv = adversaire(joueur);
		if ((grille[col][lig] != adv) && (grille[col + 1][lig - 1] != adv)
				&& (grille[col + 2][lig - 2] != adv)
				&& (grille[col + 3][lig - 3] != adv))
			return (int) Math.pow(2,
					((grille[col][lig] + grille[col + 1][lig - 1]
							+ grille[col + 2][lig - 2]
							+ grille[col + 3][lig - 3]) / joueur));
		else
			return 0;
	}

	int estime() {
		int col = 0, lig;
		int ordi = 0;
		int humain = 0;
		for (col = 0; col < NB_COLONNES; col++) {
			for (lig = 0; lig < NB_LIGNES; lig++) {
				if (col < (NB_COLONNES - 3)) {
					ordi += estimeHorizontal(col, lig, ORDI);
					humain += estimeHorizontal(col, lig, HUMAIN);
				}
				if (lig < (NB_LIGNES - 3)) {
					ordi += estimeVertical(col, lig, ORDI);
					humain += estimeVertical(col, lig, HUMAIN);
				}
				if ((col < NB_COLONNES - 3) && (lig < NB_LIGNES - 3)) {
					ordi += estimeDiagonale1(col, lig, ORDI);
					humain += estimeDiagonale1(col, lig, HUMAIN);
				}
				if ((col < NB_COLONNES - 3) && (lig > 2)) {
					ordi += estimeDiagonale2(col, lig, ORDI);
					humain += estimeDiagonale2(col, lig, HUMAIN);
				}
			}
		}
		return (ordi - humain);
	}

	tripletEtiquetteGagnantPerdant etiquette(int joueurCourant,
			int profondeur) {
		int gagnant;
		tripletEtiquetteGagnantPerdant tripletARetourner;
		if (feuille()) {
			gagnant = gagnant();
			switch (gagnant) {
			case HUMAIN:
				tripletARetourner = new tripletEtiquetteGagnantPerdant(gagnant,
						0, 1);
				break;
			case ORDI:
				tripletARetourner = new tripletEtiquetteGagnantPerdant(gagnant,
						1, 0);
				break;
			default:
				tripletARetourner = new tripletEtiquetteGagnantPerdant(PERSONNE,
						0, 0);
				break;
			}
			return tripletARetourner;
		} else {
			tripletARetourner = new tripletEtiquetteGagnantPerdant(PERSONNE, 0,
					0);
			if (profondeur == 0) {
				return tripletARetourner;
			} else {
				boolean filsNul = false;
				tripletEtiquetteGagnantPerdant etiquetteFils;
				Etat fils = new Etat();
				// On va tester les differentes possibilites, c'est-a-dire
				// essayer de mettre un pion dans
				// les differentes colonnes pour voir si l'un des ces coups peut
				// nous garantir la victoire.
				for (int colonne = 0; colonne < NB_COLONNES; colonne++) {
					fils.recopier(this);
					if (fils.peutDeposerPion(colonne)) {
						// La colonne colonne n'est pas pleine : on peut essayer
						// de voir ce que donnerait le depot d'un pion dans
						// cette colonne.
						fils.deposerPion(colonne, joueurCourant);
						etiquetteFils = fils.etiquette(
								adversaire(joueurCourant), profondeur - 1);
						tripletARetourner.ajouter(etiquetteFils);
						if (etiquetteFils.getEtiquette() == joueurCourant) {
							// en jouant dans cette colonne on est assure de
							// gagner !
							tripletARetourner.setEtiquette(joueurCourant);
							return tripletARetourner;
						}
						if (etiquetteFils.getEtiquette() == PERSONNE) {
							// Il existe au moins un coup qui mene a un match
							// nul.
							filsNul = true;
						}
					}
				}
				if (!filsNul) {// Toutes les colonnes mene l'adversaire a gagner
								// : on est sur de perdre.
					tripletARetourner.setEtiquette(adversaire(joueurCourant));
				}
				return tripletARetourner;
			}
		}
	}

	int meilleurCoupPourOrdinateur() {
		tripletEtiquetteGagnantPerdant etiquetteFils;
		int meilleurCoup = NB_COLONNES;
		tripletEtiquetteGagnantPerdant valeurMeilleurCoup = new tripletEtiquetteGagnantPerdant(
				0, -9999999, 9999999);
		tripletEtiquetteGagnantPerdant valeurMoinsPire = new tripletEtiquetteGagnantPerdant(
				0, -9999999, 9999999);
		int moinsPire = NB_COLONNES;
		Etat fils = new Etat();
		for (int colonne = 0; colonne < NB_COLONNES; colonne++) {
			fils.recopier(this);
			if (fils.peutDeposerPion(colonne)) {
				fils.deposerPion(colonne, ORDI);
				etiquetteFils = fils.etiquette(HUMAIN, PROFONDEUR_MAX);
				if (etiquetteFils.getEtiquette() == ORDI) {
					return colonne;
				}
				if ((etiquetteFils.getEtiquette() == PERSONNE)
						&& (etiquetteFils.meilleur(valeurMeilleurCoup))) {
					valeurMeilleurCoup = etiquetteFils;
					meilleurCoup = colonne;
				} else {
					if (etiquetteFils.meilleur(valeurMoinsPire)) {
						valeurMoinsPire = etiquetteFils;
						moinsPire = colonne;
					}
				}
			}
		}
		if (meilleurCoup < NB_COLONNES) {
			return meilleurCoup;
		} else {
			return moinsPire;
		}
	}

}