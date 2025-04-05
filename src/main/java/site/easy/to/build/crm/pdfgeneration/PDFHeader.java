package site.easy.to.build.crm.pdfgeneration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PDFHeader {
    String value() default "";
    boolean activate() default true;
}
