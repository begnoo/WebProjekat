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
			  		<button v-on:click="validateChangePassword" type="submit" class="btn btn-primary">Change</button>
				</div>
					<confirmation-modal
						v-on:closed="clearLastValidationWrapper"
						type="primary"
						modalName="confirmationChangePassword" 
						title="Confirm Add" 
						:callback="changePassword"
						:callbackData="newPassword">
						Are you sure you want to change your password?
					</confirmation-modal>
			</form>
    `,

    data: function() {
        return {
            currentPassword: null,
            newPassword: null,
            repeatedNewPassword: null,
            validators: {
            	'currentPassword': [validateRequired()],
            	'newPassword': [validateLength(8, 30)],
            	'repeatedNewPassword': [validateLength(8, 30)]
            }
        }
    },

    methods: {
		openChangePasswordConfirmationModall: function(){
			$("#confirmationChangePassword").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationChangePassword").modal("toggle");
		},
		validateChangePassword: function(event){
			event.preventDefault();

			if(!executeValidation(this.validators)) {
				return;
			}
			this.openChangePasswordConfirmationModall();
			
		},
        changePassword: function() {

			if(this.newPassword !== this.repeatedNewPassword) {
				toastr.error(`Passwords are not same.`, '');
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
				this.clear();
				toastr.success(`You have successfully changed your password.`, '')
			})
            .catch(function(error)
			{
				toastr.error(error.response.data.errorMessage, '');
			});
        },

		clear: function() {
			this.clearForm();
			clearLastValidation(this.validators);	
		},
		
		clearForm: function() {
            this.currentPassword = null;
            this.newPassword = null;
            this.repeatedNewPassword = null;
		},
		clearLastValidationWrapper: function(){
			clearLastValidation(this.validators);	
		}
    }
});