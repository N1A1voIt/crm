package site.easy.to.build.crm.depenses.exceptions;
public class TauxException extends Exception {
    private double taux;
    private double montant;

    public TauxException(String message, double taux, double montant) {
        super(message);
        this.taux = taux;
        this.montant = montant;
    }
    public TauxException(double taux, double montant) {
        super("Vous avez dépassé le taux que vous avez fixé.");
        this.taux = taux;
        this.montant = montant;
    }
    public double getTaux() {
        return taux;
    }

    public double getMontant() {
        return montant;
    }
}

