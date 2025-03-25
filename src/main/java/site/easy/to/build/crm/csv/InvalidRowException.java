package site.easy.to.build.crm.csv;

import java.util.List;

public class InvalidRowException extends Exception {
    List<String> invalidDesc;
    public InvalidRowException(String message) {
        super(message);
    }

    public List<String> getInvalidDesc() {
        return invalidDesc;
    }

    public void setInvalidDesc(List<String> invalidDesc) {
        this.invalidDesc = invalidDesc;
    }
}
