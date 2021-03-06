Vue.component("edit-manifestation-modal", {
    template: `
    <custom-modal modalName="editManifestationModal" title="Edit Manifestation">
        <manifestation-form :trigger="trigger"
							:value="updatedValue"
							:idPrefix='idPrefix'
							v-on:update-success="manifestation => this.$emit('update-success', manifestation)"
							v-on:image-update-done=""
							v-on:inputChange="newValue => updatedValue = newValue">
		</manifestation-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" v-on:click="openUpdateModal">Update Manifestation</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#editManifestationModal">Cancel</button>
        </div>
		<template v-slot:inner-modal>
			<confirmation-modal
				v-on:closed="clear"
				type="primary"
				modalName="confirmationModal" 
				title="Confirm Update" 
				:callback="updateManifestation"
				:callbackData="updatedValue">
				Are you sure you want to update this manifestation?
			</confirmation-modal>
		</template>


    </custom-modal>
    `,
   
	props: ["value"],
	
	data: function(){
		return {
			updatedValue: {},
			trigger: false,
			idPrefix: 'edit'
		}
	},

    methods: {
		openUpdateModal: function(){
			$("#confirmationModal").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationModal").modal("toggle");
		},
        validateManifestation: function (event) {
            event.preventDefault();

			if(!executeValidation(this.getValidators())) {
				return;
			}
			openUpdateModal();

        },
		updateManifestation: function(){
			const data = {
				id: this.updatedValue.id,
                name: this.updatedValue.name,
                type: this.updatedValue.type,
                seats: this.updatedValue.seats,
                eventDate: this.updatedValue.eventDate + " " + this.updatedValue.eventTime,
                eventEndDate: this.updatedValue.eventEndDate + " " + this.updatedValue.eventEndTime,
                regularTicketPrice: this.updatedValue.regularTicketPrice,
                locationId: this.updatedValue.locationId,
            };

            axios(putRestConfig("/WebProjekat/rest/manifestations", {}, data))
                .then((response) => {
					this.clear();
                    toastr.success(`You have successfully updated a manifestation.`, '');
					this.trigger = !this.trigger;
					this.$emit("update-success", response.data);
					$("#confirmationModal").modal("toggle");
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
		getValidators: function() {
			return {
				'editManifestationName': [validateLength(3, 150)],
				'editManifestationSeats': [validateMinNumber(1)],
				'editManifestationPrice': [validateMinNumber(0)],
				'editManifestationStartDate': [validateRequired()],
				'editManifestationStartTime': [validateRequired()],
				'editManifestationEndDate': [validateRequired()],
				'editManifestationEndTime': [validateRequired()],
				'editManifestationType': [validateRequired()],
				'editManifestationLocation': [validateLocation(this.value.locationId)]
			};
		},
		
		clear: function() {
			clearLastValidation(this.getValidators());	
		}
    },
	created: function() {
		this.updatedValue = {...this.value};
		let splitDateTime = this.value.eventDate.split(" ");
		this.updatedValue.eventDate = splitDateTime[0];
		this.updatedValue.eventTime = splitDateTime[1];
		splitDateTime = this.value.eventEndDate.split(" ");
		this.updatedValue.eventEndDate  = splitDateTime[0];
		this.updatedValue.eventEndTime = splitDateTime[1];
	}
});
