Vue.component('user-info-form', {
    template:
    `
        <div>
            <div class="form-row">
	            <div class="form-group col-md-6">
	                <label for="registrationName">First name</label>
	                <input v-model="value.name" type="text" class="form-control" id="registrationName" placeholder="First name">
	            </div>
	            <div class="form-group col-md-6">
	                <label for="registrationSurname">Second name</label>
	                <input v-model="value.surname" type="text" class="form-control" id="registrationSurname" placeholder="Second name">
				</div>
	        </div>
	        
            <div class="form-row">
                <div class="form-group col">
	                <label for="registrationBirthdate">Birthdate</label>
	                <input v-model="value.birthdate" type="date" class="form-control" id="registrationBirthdate" placeholder="Birthdate">
				</div>
            </div>
            
            <div class="form-row">
	            <div class="form-group col">
	                <label for="registrationGender">Gender</label>
	                <select v-model="value.gender" id="registrationGender" class="form-control">
	                <option selected value="Male">Male</option>
	                <option value="Female">Female</option>
	                </select>
	            </div>
            </div>
        </div>
    `,

    props: ['value'],
    
    watch: {
        value() {
            this.$emit('input', this.value);
        }
    }

    
});