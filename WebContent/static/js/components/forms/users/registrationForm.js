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
					this.$emit("create-user-success");
					alert("Uspesno");
				})
				.catch(function(error) {
					alert(error.response.data.errorMessages);
				});
		}
	}
});


