package site.easy.to.build.crm.csv;
import java.util.*;
public class CsvTempResult<T> {
    public List<T> rows;
    public List<Exception> exceptions;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public CsvTempResult() {
    }

    public CsvTempResult(List<T> rows, List<Exception> exceptions) {
        this.rows = rows;
        this.exceptions = exceptions;
    }
}