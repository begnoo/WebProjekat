Vue.component("add-manifestation-modal", {
    template: `
    <custom-modal modalName="addManifestationModal" title="Add Manifestation">
        <manifestation-form :value="value" :idPrefix='idPrefix' v-on:inputChange="newValue => value = newValue"></manifestation-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" v-on:click="validateManifestation">Add Manifestation</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#addManifestationModal">Cancel</button>
		</div>
		
		<template v-slot:inner-modal>
			<confirmation-modal
				v-on:closed="clearLastValidationWrapper"
				type="primary"
				modalName="confirmationModal" 
				title="Confirm Add" 
				:callback="createManifestation"
				:callbackData="value">
				Are you sure you want to add this manifestation?
			</confirmation-modal>
		</template>
		
    </custom-modal>
    `,
    data: function () {
        return {
			value:{
				name: null,
	            type: null,
	            seats: null,
	            eventDate: null,
	            eventTime: null,
	            eventEndDate: null,
	            eventEndTime: null,
	            regularTicketPrice: null,
	            locationId: null,
				id: "",
			},
			idPrefix: 'add'
        };
    },

    methods: {
		openAddModal: function(){
			$("#confirmationModal").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationModal").modal("toggle");
		},
		validateManifestation: function (event) {
            event.preventDefault();

			if(!executeValidation(this.getValidators())) {
				return;
			}
			this.openAddModal();

        },
        createManifestation: function () {       
	   
            axios(postRestConfig("/WebProjekat/rest/manifestations", {}, {
                    name: this.value.name,
                    type: this.value.type,
                    seats: this.value.seats,
                    eventDate: this.value.eventDate + " " + this.value.eventTime,
                    eventEndDate: this.value.eventEndDate + " " + this.value.eventEndTime,
                    regularTicketPrice: this.value.regularTicketPrice,
                    locationId: this.value.locationId,
                    sellerId: window.localStorage.getObject("loggedUser").user
                        .id,
                }))
                .then((response) => {
					this.clear();
					$('#addManifestationModal').modal('toggle');
                    toastr.success(`You have successfully added a new manifestation.`, '');
					this.value.id = response.data.id;
					this.$emit("add-manifestation-success", response.data);
					this.updateSeller(response.data);
					$("#confirmationModal").modal("toggle");
					$("#addManifestationModal").modal("toggle");
                })
                .catch(function (error) {
					if(error.response.data.errorMessages) {
						toastr.options.preventDuplicates = false;
						for(let errorMessage of error.response.data.errorMessages) {
		                    toastr.error(errorMessage, '');
						}
	                	toastr.options.preventDuplicates = true;
					}
					
					if(error.response.data.errorMessage) {
	                    toastr.error(error.response.data.errorMessage, '');
					}
				});
        },
        
		updateSeller: function(manifestation){
			let loggedUser = localStorage.getObject("loggedUser");
			loggedUser.user.manifestations.push(manifestation);
			localStorage.setObject("loggedUser", loggedUser);
		},
		
		getValidators: function() {
			return {
				'addManifestationName': [validateLength(3, 150)],
				'addManifestationSeats': [validateMinNumber(1)],
				'addManifestationPrice': [validateMinNumber(0)],
				'addManifestationStartDate': [validateRequired()],
				'addManifestationStartTime': [validateRequired()],
				'addManifestationEndDate': [validateRequired()],
				'addManifestationEndTime': [validateRequired()],
				'addManifestationType': [validateRequired()],
				'addManifestationLocation': [validateLocation(this.value.locationId)]
			};
		},
		
		clear: function() {
			this.clearForm();
			clearLastValidation(this.getValidators());	
		},
		
		clearForm: function() {
			this.value.name = null;
            this.value.type = null;
            this.value.seats = null;
            this.value.eventDate = null;
            this.value.eventTime = null;
            this.value.eventEndDate = null;
            this.value.eventEndTime = null;
            this.value.regularTicketPrice = null;
            this.value.locationId = null;
			this.value.id = null;
		},
		
		clearLastValidationWrapper: function(){
			clearLastValidation(this.getValidators());
		}
    }
});
