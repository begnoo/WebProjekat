Vue.component("add-location-modal", {
    template: `
    <custom-modal modalName="addLocationModal" title="Create Location">
		<location-form
			:value="value"
		></location-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="createLocation">Create Location</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#editLocationModal">Cancel</button>
		</div>
    </custom-modal>
    `,
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
        };
    },

    methods: {
        createLocation: function(event) {
			event.preventDefault();
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
					this.$emit("add-location-success");
					alert("Uspesno");
				})
				.catch(function(error) {
					alert(error.response.data.errorMessage);
				});
		},
    },
});
