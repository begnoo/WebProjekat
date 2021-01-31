Vue.component("edit-location-modal", {
    template: `
    <custom-modal modalName="editLocationModal" title="Edit Location">
		<location-form
			:value="updatedValue"
		></location-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="updateLocation">Update Location</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#editLocationModal">Cancel</button>
		</div>
	</custom-modal>
    `,

	props:["location"],
    
	data: function(){
		return {
			updatedValue: {},
		}
	},

    methods: {
        updateLocation: function (event) {
            event.preventDefault();
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
                    alert("Uspesno azurirana lokacija");
					this.trigger = !this.trigger;
					this.$emit("update-location-success", response.data);
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        }
    },
	created: function(){
		this.updatedValue = this.location;
	}
});
