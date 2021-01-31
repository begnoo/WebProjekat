Vue.component('locations-page',
	{
		template:
			`
	<div class="container">
		<div class="row mt-3">
			<locations-table :locations="locations" v-on:location-selected="updateLocation"></locations-table>
		</div>
		<div class="row mt-3">
			<div class="col">
				<button type="button"
				 class="btn btn-primary btn-sm float-right mr-3"
				 data-toggle="modal"
				 data-target="#addLocationModal"
			 	 v-on:click="userToBlock = user">Add Location</button>
		
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setLocations"></pagination>
			</div>

		</div>
		
		<edit-location-modal :location="locationToEdit" v-on:update-location-success="updateCurrentLocations"></edit-location-modal>
		<add-location-modal v-on:add-location-success="trigger = !trigger"></add-location-modal>
  	</div>
    `,

		data: function() {
			return {
				locations: [],
				restPath: "../WebProjekat/rest/locations",
				restConfig: null,
				pageSize: 5,
				selectedPage: 1,
				trigger: false,
				locationToEdit: {
					id: null,
					longitude: null,
					latitude: null,
					street: null,
					houseNumber: null,
					postalCode: null,
					place: null,
				},
			}
		},

		methods: {

			setLocations: function({ emittedData, selectedPage }) {
				this.locations = emittedData
				this.selectedPage = selectedPage
			},
			
			updateCurrentLocations: function(updatedLocation){
				const index = this.locations.findIndex((location) => location.id === updatedLocation.id);
				this.locations.splice(index, 1, updatedLocation);
			},

			updateLocation: function(emittedLocation) {
				this.locationToEdit.id = emittedLocation.id;
				this.locationToEdit.latitude = emittedLocation.latitude;
				this.locationToEdit.longitude = emittedLocation.longitude;
				this.locationToEdit.street = emittedLocation.address.street;
				this.locationToEdit.place = emittedLocation.address.place;
				this.locationToEdit.postalCode = emittedLocation.address.postalCode;
				this.locationToEdit.houseNumber = emittedLocation.address.houseNumber;
			},


		},

		created: function() {
			this.restConfig = getRestConfig(this.restPath);
		}

	});