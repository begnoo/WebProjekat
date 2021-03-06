Vue.component('registration-form', {
	template:
		`
		<form>
			<account-info-form
				v-bind:value="accountInfo"
				@input="(newAccountInfo) => {accountInfo = newAccountInfo}"
			></account-info-form>
			<user-info-form
				v-bind:value="userInfo"
				@input="(newUserInfo) => {userInfo = newUserInfo}"
			></user-info-form>
	  		<button v-if="userType == 'buyer'"v-on:click="validateAndCreateUser" type="submit" class="btn btn-primary">Register</button>
		  	<button v-if="userType == 'seller'"v-on:click="validateSeller" type="submit" class="btn btn-primary">Register</button>
		</form>
    `,

	props: ["userType"],

	data: function() {
		return {
			accountInfo: {
				username: null,
				password: null
			},
			userInfo: {
				name: null,
				surname: null,
				gender: null,
				birthdate: null
			},
			validators: {
				'registrationUsername': [validateLength(3, 30)],
				'registrationPassword': [validateLength(8, 30)],
				'registrationName': [validateLength(2, 30)],
				'registrationSurname': [validateLength(2, 30)],
				'registrationBirthdate': [validateRequired(), validateUserAge()],
				'registrationGender': [validateRequired()],
			}
		}
	},
	
	methods:
	{
		validateAndCreateUser : function(event){
			event.preventDefault();
			
			if(!executeValidation(this.validators)) {
				return;
			}
			
			this.createUser()
		},
		validateSeller: function(event){
			event.preventDefault();
			
			if(!executeValidation(this.validators)) {
				return;
			}
			this.$emit('seller-submit', this.createUser, this.clearLastValidationWrapper)
		},

		createUser: function() {

			let path = '/WebProjekat/rest/users/';
			path += this.userType;

			axios(postRestConfig(path, {},
				{
					'username': this.accountInfo.username,
					'password': this.accountInfo.password,
					'name': this.userInfo.name,
					'surname': this.userInfo.surname,
					'gender': this.userInfo.gender,
					'birthdate': this.userInfo.birthdate + " 00:00"

				})).then(response => {
					toastr.success(`You have successfully created a new account.`, '');
					this.clear();
					this.$emit("create-user-success");
				})
				.catch(function(error) {
					toastr.error(error.response.data.errorMessage, '');
				});
		},
		
		clear: function() {
			this.clearForm();
			clearLastValidation(this.validators);	
		},
		
		clearForm: function() {
			this.accountInfo.username = null;
			this.accountInfo.password = null;
			
			this.userInfo.name = null;
			this.userInfo.surname = null;
			this.userInfo.gender = null;
			this.userInfo.birthdate = null;
		},
		clearLastValidationWrapper: function(){
			clearLastValidation(this.validators);	
		}
	
	}
});


