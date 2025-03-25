package site.easy.to.build.crm.depenses.exceptions;

public class BudgetException extends Exception {
    private double budget;
    private double depense;

    public BudgetException(String message, double budget, double depense) {
        super(message);
        this.budget = budget;
        this.depense = depense;
    }

    public BudgetException(String message, double budget, double depense, Throwable cause) {
        super(message, cause);
        this.budget = budget;
        this.depense = depense;
    }

    public double getBudget() {
        return budget;
    }

    public double getDepense() {
        return depense;
    }
}

