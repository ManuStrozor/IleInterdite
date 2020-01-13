package game;
/**
 *
 * @author estevmat
 */
public class CarteTresor extends Carte{
    private String nomCarteTresor;

    CarteTresor(String nom){
        nomCarteTresor = nom;
    }
    public String getNomCarteTresor() {
        return nomCarteTresor;
    }

    public void setNomCarteTresor(String nomCarte) {
        this.nomCarteTresor = nomCarte;
    }

}
