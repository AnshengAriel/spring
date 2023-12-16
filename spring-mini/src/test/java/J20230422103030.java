import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.StringToNumberConverterFactory;
import org.springframework.converter.StringToBooleanConverter;
import org.springframework.converter.StringToIntegerConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 转换器
 * <a href='project\_20230421141647\src\test\java\J20230422103030.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422103030 {

    @Test
    public void testConverter() {

        // Converter实现
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer integer = converter.convert("1");
        assertThat(integer).isEqualTo(1);

        // ConverterFactory实现
        StringToNumberConverterFactory factory = new StringToNumberConverterFactory();
        Converter<String, Long> strToLongConverter = factory.getConverter(Long.class);
        Long aLong = strToLongConverter.convert("1");
        assertThat(aLong).isEqualTo(1);

        // GenericConverter实现
        StringToBooleanConverter stringToBooleanConverter = new StringToBooleanConverter();
        Object o = stringToBooleanConverter.convert("true", String.class, Boolean.class);
        assertThat(o).isEqualTo(true);
    }
}
