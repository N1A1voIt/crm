package site.easy.to.build.crm.csv;
import com.opencsv.bean.AbstractBeanField;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MultiPatternNumberConverter extends AbstractBeanField<Double, String> {

    @Override
    protected Double convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        NumberFormat[] formats = {
                NumberFormat.getInstance(Locale.US),
                NumberFormat.getInstance(Locale.FRANCE),
                NumberFormat.getInstance(Locale.GERMANY)
        };

        for (NumberFormat format : formats) {
            try {
                return format.parse(value).doubleValue();
            } catch (ParseException ignored) {
            }
        }

        throw new RuntimeException("Invalid number format: " + value);
    }
}

