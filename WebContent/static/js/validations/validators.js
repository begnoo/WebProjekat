function validateLength(minLength, maxLength) {
	return function(inputFieldId) {
		const inputedText = $(`#${inputFieldId}`).val();
		const inputedTextLength = inputedText.length;
		if(inputedTextLength < minLength || inputedTextLength > maxLength) {
			$(`#${inputFieldId}`).addClass('is-invalid');

			const label = getLabel(inputFieldId);
			const errorMessage = `${label} must be between ${minLength} and ${maxLength} characters long.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function validateRequired() {
	return function(inputFieldId) {
		const inputedText = $(`#${inputFieldId}`).val();
		if(!inputedText) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = getLabel(inputFieldId);
			const errorMessage = `${label} must be provided.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function validateFloatType() {
	return function(inputFieldId) {
		const inputedText = $(`#${inputFieldId}`).val();
		if(!isFloat(inputedText)) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = getLabel(inputFieldId);
			const errorMessage = `${label} must be a number.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function validateMinNumber(minNumber) {
	return function(inputFieldId) {
		const inputedText = $(`#${inputFieldId}`).val();
		
		if(!inputedText || parseInt(inputedText) < minNumber) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = getLabel(inputFieldId);
			const errorMessage = `${label} must be equal or greater than ${minNumber}.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}	
	}
}

function isFloat(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

function validateUserAge() {
	return function(inputFieldId) {
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

function validateLocation(locationId) {
	return function(inputFieldId) {
		if(!locationId) {
			$(`#${inputFieldId}`).addClass('is-invalid');
			
			const label = getLabel(inputFieldId);
			const errorMessage = `${label} must be provided.`;
			addErrorMessage(inputFieldId, errorMessage);
			
			return false;
		} else {
			$(`#${inputFieldId}`).addClass('is-valid');

			return true;
		}
	}
}
	
function getLabel(inputFieldId) {
	const label = $(`label[for=${inputFieldId}]`).text().trim();
	
	return label.endsWith(':') ? label.substr(0, label.length - 1) : label;
}

function addErrorMessage(inputFieldId, message) {
	const divWithErrorMessage = $('<div>').attr('id', `${inputFieldId}Feedback`)
										  .addClass('invalid-feedback')
										  .append(`<p>${message}</p>`);
								
	$(`#${inputFieldId}`).after(divWithErrorMessage);
}