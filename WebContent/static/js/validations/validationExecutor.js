function executeValidation(validators) {
	clearLastValidation(validators);
		
	return validate(validators);
}

function clearLastValidation(validators) {
	for(let inputFieldId in validators) {
		$(`#${inputFieldId}`).removeClass('is-invalid');
		$(`#${inputFieldId}`).removeClass('is-valid');
		$(`#${inputFieldId}Feedback`).remove();
	}
}

function validate(validators) {
	let formValidationIsSuccessful = true;

	for(let listOfValidators of Object.values(validators)) {
		for(let validator of listOfValidators) {
			const validationResult = validator();
			formValidationIsSuccessful &= validationResult;
			
			if(!validationResult) {
				break;
			}
		}
	}
	
	return formValidationIsSuccessful;
}