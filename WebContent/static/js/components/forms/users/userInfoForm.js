Vue.component('user-info-form', {
    template:
    `
        <div>
            <div class="form-row">
	            <div class="form-group col-md-6">
	                <label for="registrationFirstname">First name</label>
	                <input v-model="name" type="text" class="form-control" id="registrationFirstname" placeholder="First name">
	            </div>
	            <div class="form-group col-md-6">
	                <label for="registrationSirstname">Second name</label>
	                <input v-model="surname" type="text" class="form-control" id="registrationSirstname" placeholder="Second name">
	            </div>
	        </div>
	        
            <div class="form-row">
                <div class="form-group col">
	                <label for="registrationBirthdate">Birthdate</label>
	                <input v-model="birthdate" type="date" class="form-control" id="registrationBirthdate" placeholder="Birthdate">
                </div>
            </div>
            
            <div class="form-row">
	            <div class="form-group col">
	                <label for="registrationGender">Gender</label>
	                <select v-model="gender" id="registrationGender" class="form-control">
	                <option selected value="Male">Male</option>
	                <option value="Female">Female</option>
	                </select>
	            </div>
            </div>
        </div>
    `,

    data: function() {
        return {
            name: null,
            surname: null,
            gender: null,
            birthdate: null
        }
    }
});