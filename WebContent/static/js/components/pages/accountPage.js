Vue.component('account-page', {
    template:
    `
        <div class="container">
			<div class="row justify-content-center align-items-center" style="min-height: 90vh;">
				<div class="col-5">
					<div class="card">
						<div class="card-body">
				    		<form>
				    			<user-info-form
				    				v-bind:value="userInfo"
				    				@input="(newUserInfo) => {userInfo = newUserInfo}"
				    			></user-info-form>
				    			
				    			<div v-if="userRole==='Buyer'">
					    			<div class="form-row">
							            <div class="form-group col-md-6">
							                <label for="buyerTypeName">Buyer type</label>
							                <input v-model="buyerInfo.buyerTypeName" type="text" class="form-control" id="buyerTypeName" style="background-color: white;" disabled>
							            </div>
							            <div class="form-group col-md-6">
							                <label for="buyerPoints">Points</label>
							                <input v-model="buyerInfo.points" type="text" class="form-control" id="buyerPoints" style="background-color: white;" disabled>
							            </div>
							        </div>
				    			</div>
				    			
						  		<button v-on:click="validateLocation" type="submit" class="btn btn-primary">Update</button>
						
								<button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#changePasswordModal">Change Password</button>
						    	<custom-modal modalName="changePasswordModal" title="Change Password">
									<change-password-form 
										v-on:change-password-submit="openChangePasswordConfirmationModal"
										v-on:change-password-success="closeModal">
									</change-password-form>
									<template v-slot:inner-modal>
										<confirmation-modal
											v-on:closed="clearLastValidationWrapper"
											type="primary"
											modalName="confirmationChangePasswordModal" 
											title="Confirm Password Change" 
											:callback="changePassword"
											callbackData="">
											Are you sure you want change your password?
										</confirmation-modal>
									</template>
								</custom-modal>
				    		</form>
				
							<confirmation-modal
								v-on:closed="clear"
								type="primary"
								modalName="confirmationUpdateAccount" 
								title="Confirm Update" 
								:callback="updateUser"
								:callbackData="userInfo">
								Are you sure you want to update your account info?
							</confirmation-modal>
						</div>
					</div>
				</div>
			</div>
        </div>
    `,

    data: function () {
			return {
				userInfo: {
					name: null,
					surname: null,
					gender: null,
					birthdate: null
				},
				buyerInfo: {
					buyerTypeName: null,
					points: null
				},
				userRole: null,
				validators: {
					'registrationName': [validateLength(2, 30)],
					'registrationSurname': [validateLength(2, 30)],
					'registrationBirthdate': [validateRequired(), validateUserAge()],
					'registrationGender': [validateRequired()]
				},
				clearLastValidationWrapper: function(){},
				changePassword: function(){},
			}

    },
    
    methods:
	{
		openChangePasswordConfirmationModal: function(changePasswordCallback, clearCallback){
			this.changePassword = changePasswordCallback;
			this.clearLastValidationWrapper = clearCallback;
			$("#confirmationChangePasswordModal").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationChangePasswordModal").modal("toggle");
		},
		openUpdateModal: function(){
			$("#confirmationUpdateAccount").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationUpdateAccount").modal("toggle");
		},
		validateLocation: function(event){
            event.preventDefault();
            
			if(!executeValidation(this.validators)) {
				return;
			}
			this.openUpdateModal();
		},
    	updateUser: function() {

			let loggedUser = localStorage.getObject('loggedUser');
			let userId = loggedUser.user.id;	

            axios(putRestConfig('/WebProjekat/rest/users', {},
            {
            	'id': userId,
                'name': this.userInfo.name,
                'surname': this.userInfo.surname,
                'gender': this.userInfo.gender,
                'birthdate': this.userInfo.birthdate + " 00:00"
            })).then(response => {
				this.clear();
				toastr.success(`You have successfully updated your account.`, '');
				$("#confirmationUpdateAccount").modal("toggle");
            	loggedUser.user = response.data;
            	localStorage.setObject('loggedUser', loggedUser);
            })
             .catch(function(error)
              {
                toastr.error(error.response.data.errorMessage, ''); 	
              });
        },

		getBuyerInfo: function(buyerTypeId){
			axios(getRestConfig('/WebProjekat/rest/buyer-type/' + buyerTypeId)).then(response => {
            	this.buyerInfo.buyerTypeName = response.data.name;
            })
             .catch(function(error)
              {
                toastr.error(error.response.data.errorMessage, ''); 	
              });
		},

		clear: function() {
			clearLastValidation(this.validators);	
		},
		
		closeModal: function(){
			$('#confirmationChangePasswordModal').modal('toggle'); 
			$('#changePasswordModal').modal('toggle');
		}
	},
	
	mounted: function()
	{
		let loggedUser = localStorage.getObject('loggedUser');
		this.userInfo.name = loggedUser.user.name;
		this.userInfo.surname = loggedUser.user.surname;
		this.userInfo.gender = loggedUser.user.gender;
		this.userInfo.birthdate = loggedUser.user.birthdate.split(' ')[0];
		this.userRole = loggedUser.user.role;
		
		if(this.userRole === "Buyer") {
			this.getBuyerInfo(loggedUser.user.typeId);
			this.buyerInfo.points = loggedUser.user.points;
		}
	}		
});


