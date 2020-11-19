package kubiak.lofapp.Validators;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword arg0) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Properties properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream("src/main/resources/messages.properties");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("Windows-1250"));
            BufferedReader reader = new BufferedReader(isr);
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageResolver resolver = new PropertiesMessageResolver(properties);

        PasswordValidator validator = new PasswordValidator(resolver,
                // at least 8 characters
                new LengthRule(8, 64),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // no whitespace
                new WhitespaceRule()
        );
        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
