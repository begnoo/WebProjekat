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
	  		<button v-on:click="createUser" type="submit" class="btn btn-primary">Register</button>
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
				'registrationUsername': [validateLength('registrationUsername', 3, 30)],
				'registrationPassword': [validateLength('registrationPassword', 8, 30)],
				'registrationName': [validateLength('registrationName', 2, 30)],
				'registrationSurname': [validateLength('registrationSurname', 2, 30)],
				'registrationBirthdate': [validateRequired('registrationBirthdate'), validateUserAge('registrationBirthdate')],
				'registrationGender': [validateRequired('registrationGender')],
			}
		}
	},
	
	methods:
	{

		createUser: function(event) {
			event.preventDefault();
			
			if(!executeValidation(this.validators)) {
				return;
			}

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
		}
	
	}
});


