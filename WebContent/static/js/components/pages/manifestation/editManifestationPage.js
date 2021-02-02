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
			<button type="button" class="btn btn-primary" v-on:click="updateManifestation">Update Manifestation</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#editManifestationModal">Cancel</button>
        </div>
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
        updateManifestation: function (event) {
            event.preventDefault();

			if(!executeValidation(this.getValidators())) {
				return;
			}
			
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
			console.log(data);
            axios(putRestConfig("/WebProjekat/rest/manifestations", {}, data))
                .then((response) => {
                    alert("Uspesno azurirana manifestacija");
					this.trigger = !this.trigger;
					this.$emit("update-success", response.data);
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        },

		getValidators: function() {
			return {
				'editManifestationName': [validateLength('editManifestationName', 3, 150)],
				'editManifestationSeats': [validateMinNumber('editManifestationSeats', 1)],
				'editManifestationPrice': [validateMinNumber('editManifestationPrice', 0)],
				'editManifestationStartDate': [validateRequired('editManifestationStartDate')],
				'editManifestationStartTime': [validateRequired('editManifestationStartTime')],
				'editManifestationEndDate': [validateRequired('editManifestationEndDate')],
				'editManifestationEndTime': [validateRequired('editManifestationEndTime')],
				'editManifestationType': [validateRequired('editManifestationType')],
				'editManifestationLocation': [validateLocation('editManifestationLocation', this.value.locationId)]
			};
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
