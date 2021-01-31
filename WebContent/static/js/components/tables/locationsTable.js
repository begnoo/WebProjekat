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
							 class="btn btn-success btn-sm"
							 data-toggle="modal"
							 data-target="#editLocationModal"
						 	 v-on:click="emitSelectedLocation(location)">
							 	Edit
							 </button>
						  </td>	
					  </tr>
				</tbody>
	      </table>
  	</div>
    `,

	props: ["locations"],

    data: function() {
        return {
        }
    },
    
    methods: {
    	emitSelectedLocation: function(location){
			this.$emit("location-selected", location);
		},
    }
});