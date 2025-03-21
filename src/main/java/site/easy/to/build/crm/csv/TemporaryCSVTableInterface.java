package site.easy.to.build.crm.csv;

import java.util.List;

public interface TemporaryCSVTableInterface {
    void createTemporaryTable();
    List<Exception> controlCSV(List<String> lines,String entityName);
    void importCSV(List<String> lines,String entityName);
    void dropTemporaryTable();
}
