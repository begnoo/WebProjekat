package core.servlets.validators;

public interface IValidatorFactory {
	IObjectValidator<?> getValidator(Object validatedObject);
}
