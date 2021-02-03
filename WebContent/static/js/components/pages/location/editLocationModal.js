Vue.component("edit-location-modal", {
    template: `
    <custom-modal modalName="editLocationModal" title="Edit Location">
		<location-form
			:value="updatedValue"
			:updateSizeTrigger="updateSizeTrigger"
			:idPrefix="idPrefix"
		></location-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" v-on:click="updateLocation">Update Location</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#editLocationModal">Cancel</button>
		</div>
	</custom-modal>
    `,

	props:["location", "updateSizeTrigger"],
    
	data: function(){
		return {
			updatedValue: {},
			idPrefix: 'edit',
			validators: {
				'editLocationLatitude': [validateFloatType()],
				'editLocationLongitude': [validateFloatType()],
				'editLocationStreet': [validateLength(1, 50)],
				'editLocationHouseNumber': [validateLength(1, 15)],
				'editLocationPlace': [validateLength(1, 50)],
				'editLocationPostalCode': [validateLength(1, 15)]
			}
		}
	},

    methods: {
        updateLocation: function (event) {
            event.preventDefault();

			if(!executeValidation(this.validators)) {
				return;
			}

			const data = {
					id: this.updatedValue.id,
                    longitude: this.updatedValue.longitude,
					latitude: this.updatedValue.latitude,
					address: {
						street: this.updatedValue.street,
						houseNumber: this.updatedValue.houseNumber,
						place: this.updatedValue.place,
						postalCode: this.updatedValue.postalCode,
					}
                };
            axios(putRestConfig("/WebProjekat/rest/locations", {}, data))
                .then((response) => {
                    this.clear();
					toastr.success('You have successfully updated a location.', '');
					this.trigger = !this.trigger;
					this.$emit("update-location-success", response.data);
                })
                .catch(function (error) {
                    toastr.error(error.response.data.errorMessage, '');
                });
        },

		clear: function() {
			clearLastValidation(this.validators);	
		}
    },
	created: function(){
		this.updatedValue = this.location;
	}
});
