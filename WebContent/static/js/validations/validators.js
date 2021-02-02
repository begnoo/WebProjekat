function validateLength(inputFieldId, minLength, maxLength) {
	return function() {
		const inputedText = $(`#${inputFieldId}`).val();
		const inputedTextLength = inputedText.length;
		if(inputedTextLength < minLength || inputedTextLength > maxLength) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = $(`label[for=${inputFieldId}]`).text();
			const errorMessage = `${label} must be between ${minLength} and ${maxLength} characters long.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function validateRequired(inputFieldId) {
	return function() {
		const inputedText = $(`#${inputFieldId}`).val();
		if(!inputedText) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = $(`label[for=${inputFieldId}]`).text();
			const errorMessage = `${label} must be provided.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function validateUserAge(inputFieldId) {
	return function() {
		const userBirthdateText = $(`#${inputFieldId}`).val();
		const userBirthdate = new Date(userBirthdateText);
		let birthdateThreshold = new Date(Date.now());
		birthdateThreshold.setFullYear(birthdateThreshold.getFullYear() - 13);
		
		if(userBirthdate > birthdateThreshold) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const errorMessage = `You have to be at least 13 years old.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function addErrorMessage(inputFieldId, message) {
	const divWithErrorMessage = $('<div>').attr('id', `${inputFieldId}Feedback`)
										  .addClass('invalid-feedback')
										  .append(`<p>${message}</p>`);
								
	$(`#${inputFieldId}`).after(divWithErrorMessage);
}