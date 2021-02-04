Vue.component('locations-table',
{
    template:
    `
    	<div class="container">
	        <table class="table">
				<thead class="thead-dark">
					  <tr>
							<th scope="col">Lon</th>
							<th scope="col">Lan</th>
							<th scope="col">Street</th>
							<th scope="col">House Number</th>
							<th scope="col">Place</th>
							<th scope="col">Postal Code</th>
							<th scope="col"></th>
							<th scope="col"></th>

					  </tr>
				</thead>
				<tbody>
					  <tr v-for="location in locations">
							<td>{{ location.longitude}}</td>
							<td>{{ location.latitude}}</td>
							<td>{{ location.address.street}}</td>
							<td>{{ location.address.houseNumber}}</td>
							<td>{{ location.address.place}}</td>
							<td>{{ location.address.postalCode}}</td>
						  <td>
							<button type="button"
							 v-if="isAdmin"
							 class="btn btn-success btn-sm"
							 data-toggle="modal"
							 data-target="#editLocationModal"
						 	 v-on:click="emitSelectedLocation(location)">
							 	Edit
							 </button>
						
							<button type="button"
							 v-if="isAdmin"
							 class="btn btn-danger btn-sm"
							 data-toggle="modal"
							 data-target="#deleteLocationModal"
						 	 v-on:click="emitSelectedLocation(location)">
							 	Delete
							 </button>
						
						  </td>	
					  </tr>
				</tbody>
	      </table>
			<confirmation-modal
				type="danger"
				modalName="deleteLocationModal" 
				title="Confirm Delete" 
				:callback="deleteLocation"
				:callbackData="selectedLocation">
				Are you sure you want to delete this location?
			</confirmation-modal>
  	</div>
    `,

	props: ["locations"],

    data: function() {
        return {
			isAdmin: false,
			selectedLocation: null,
        }
    },
    
    methods: {
    	emitSelectedLocation: function(location){
			this.selectedLocation = location;
			this.$emit("location-selected", location);
		},
		deleteLocation: function(location){
			axios(deleteRestConfig("/WebProjekat/rest/locations/" + location.id))
				.then(response => {
					toastr.success(`${location.address.street}, ${location.address.houseNumber} ${location.address.place}, ${location.address.postalCode}  is successfully deleted.`, '');
					$("#deleteLocationModal").modal("toggle");
					this.$emit("deleted-location", response.data);
				})
				.catch(error => toastr.error(error.response.data.errorMessage, ''));
		}
    },

	mounted: function(){
		this.isAdmin = localStorage.isLoggedUserRole(["Administrator"]);
	}
});