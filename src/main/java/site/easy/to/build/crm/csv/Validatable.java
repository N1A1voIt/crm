package site.easy.to.build.crm.csv;

public interface Validatable {
    boolean isValid();
    boolean isInvalid() throws InvalidRowException;
}
