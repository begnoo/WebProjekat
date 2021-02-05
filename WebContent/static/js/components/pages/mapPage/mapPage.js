Vue.component("map-page", {
	template: `
    <div class="container" style="min-height: 90vh;">
        <div class="row" style="min-height: 90vh;">
            <div class="col">
				<manifestation-map v-on:location-clicked="getManifestationsForLocations" :locations="locations" :zoom="7" :initTrigger="initTrigger"></manifestation-map>
            </div>
        </div>
		<manifestations-modal :manifestations="manifestations"></manifestations-modal>
    </div>
    `,

	data: function() {
		return {
			locations: [],
			initTrigger: false,
			manifestations: [],
		};
	},

	methods: {
		getLocations(){
			axios.get("/WebProjekat/rest/locations")
			.then(response => {
				this.locations = response.data;
				this.initTrigger = !this.initTrigger;
			})
			.catch(error => toastr.error(error.response.data.errorMessage, ''))
		},
		getManifestationsForLocations(locationId){
			axios(getRestConfig("/WebProjekat/rest/manifestations/location/" + locationId, {number: 1, size: 3}))
			.then(response => {
				this.manifestations = response.data;
				$("#manifestationsModal").modal("toggle");
			})
			.catch(error => toastr.error(error.response.data.errorMessage, ''))
		}
	},

	created: function() {
		this.getLocations();
	}
});
