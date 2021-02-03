Vue.component('change-password-form', {
    template:
    `
        	<form>
				<div class="form-row justify-content-center">
					<div class="form-group col-md-6">
				    	<label for="currentPassword">Current Password</label>
				    	<input v-model="currentPassword" type="password" class="form-control" id="currentPassword" placeholder="Current password">
				    </div>
				</div>
			  

				<div class="form-row justify-content-center">
					<div class="form-group col-md-3">
						<label for="newPassword">New password</label>
						<input v-model="newPassword" type="password" class="form-control" id="newPassword" placeholder="New password">
					</div>
					
					<div class="form-group col-md-3">
						<label for="repeatedNewPassword">Repeat password</label>
						<input v-model="repeatedNewPassword" type="password" class="form-control" id="repeatedNewPassword" placeholder="Repeat password">
					</div>
				</div>
				
				<div class="form-row justify-content-center">
			  		<button v-on:click="changePassword" type="submit" class="btn btn-primary">Change</button>
				</div>
				
			</form>
    `,

    data: function() {
        return {
            currentPassword: null,
            newPassword: null,
            repeatedNewPassword: null,
            validators: {
            	'currentPassword': [validateRequired('currentPassword')],
            	'newPassword': [validateLength('newPassword', 8, 30)],
            	'repeatedNewPassword': [validateLength('repeatedNewPassword', 8, 30)]
            }
        }
    },

    methods: {
        changePassword: function(event) {
            event.preventDefault();

			if(!executeValidation(this.validators)) {
				return;
			}

			if(this.newPassword !== this.repeatedNewPassword) {
				alert("Passwords are not same.");
				return;
			}
			let loggedUser = localStorage.getObject('loggedUser');
			let userId = loggedUser.user.id;
			
            axios(putRestConfig('/WebProjekat/rest/users/' + userId + '/password', {},
            {
                'currentPassword': this.currentPassword,
                'newPassword': this.newPassword                
            }))
            .then(response => {
				this.$emit("change-password-success")
				toastr.success(`You have successfully changed your password.`, '')
			})
            .catch(function(error)
			{
				toastr.error(error.response.data.errorMessage, '');
			});
        }
    }
});