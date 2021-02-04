Vue.component("add-location-modal", {
    template: `
    <custom-modal modalName="addLocationModal" title="Create Location">
		<location-form
			:value="value"
			:updateSizeTrigger="updateSizeTrigger"
			:idPrefix="idPrefix"
		></location-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" v-on:click="validateLocation">Create Location</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#addLocationModal">Cancel</button>
		</div>
		<template v-slot:inner-modal>
			<confirmation-modal
				v-on:closed="clearLastValidationWrapper"
				type="primary"
				modalName="confirmationAddModal" 
				title="Confirm Add" 
				:callback="createLocation"
				:callbackData="value">
				Are you sure you want to add this location?
			</confirmation-modal>
		</template>
    </custom-modal>
    `,

	props: ["updateSizeTrigger"],
	
    data: function () {
        return {
			value:{
				longitude: null,
				latitude: null,
				street: null,
				houseNumber: null,
				postalCode: null,
				place: null,
			},
			idPrefix: 'add',
			validators: {
				'addLocationLatitude': [validateFloatType()],
				'addLocationLongitude': [validateFloatType()],
				'addLocationStreet': [validateLength(1, 50)],
				'addLocationHouseNumber': [validateLength(1, 15)],
				'addLocationPlace': [validateLength(1, 50)],
				'addLocationPostalCode': [validateLength(1, 15)]
			}
        };
    },

    methods: {
		openAddModal: function(){
			$("#confirmationAddModal").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationAddModal").modal("toggle");
		},
		validateLocation: function(event){
			event.preventDefault();
			
			if(!executeValidation(this.validators)) {
				return;
			}
			this.openAddModal();
		},
        createLocation: function(event) {
			
			axios(postRestConfig("/WebProjekat/rest/locations", {},
				{
					latitude: this.value.latitude,
					longitude: this.value.longitude,
					address: {
						street: this.value.street,
						houseNumber: this.value.houseNumber,
						postalCode: this.value.postalCode,
						place: this.value.place,
					}
				}))
				.then((response) => {
					this.clear();
					$('#addLocationModal').modal('toggle');
					toastr.success('You have successfully added a new location.', '');
					this.$emit("add-location-success");
					$("#confirmationAddModal").modal("toggle");
					$("#confirmationAddModal").modal("toggle");

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
			this.value.longitude = null;
			this.value.latitude = null;
			this.value.street = null;
			this.value.houseNumber = null;
			this.value.postalCode = null;
			this.value.place = null;
		},
		
		clearLastValidationWrapper: function(){
			clearLastValidation(this.validators);
		}
    },
});
