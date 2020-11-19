package kubiak.lofapp.Validators;


import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        first = constraintAnnotation.first();
        second = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context){
        boolean valid = true;
        try{
            final Object firstObj = BeanUtils.getProperty(value, first);
            final Object secondObj = BeanUtils.getProperty(value, second);
            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch(final Exception ignore){

        }
        if(!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(first)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
