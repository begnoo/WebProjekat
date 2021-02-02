Vue.component('manifestation-table',
	{
		template:
			`
    	<div class="container">
	        <table class="table">
				<thead class="thead-dark">
					  <tr>
							<th scope="col">Name</th>
							<th scope="col">Start</th>
							<th scope="col">End</th>
							<th scope="col">Seats left</th>
							<th scope="col">Price(RSD)</th>
							<th v-if="isAdmin" scope="col">Seller</th>
							<th scope="col"></th>
							<th scope="col"></th>
					  </tr>
				</thead>
				<tbody>
					  <tr v-for="manifestation in manifestations">
							<td>{{ manifestation.name}}</td>
							<td>{{ manifestation.eventDate}}</td>
							<td>{{ manifestation.eventEndDate}}</td>
							<td>{{ manifestation.seats}}</td>
							<td>{{ manifestation.regularTicketPrice}}</td>

							<td v-if="isAdmin">{{ manifestation.sellerId}}</td>
						  <td>
							<button type="button"
							 v-if="!isAdmin"
							 class="btn btn-success btn-sm"
							 data-toggle="modal"
							 data-target="#editLocationModal"
						 	 v-on:click="emitSelectedManifestation(manifestation)">
							 	Edit
							 </button>
						  </td>	
						  <td>
							<button type="button"
							 class="btn btn-danger btn-sm"
							 data-toggle="modal"
							 data-target="#editLocationModal"
						 	 v-on:click="deleteManifestation(manifestation)">
							 	Delete
							 </button>
						  </td>	
					  </tr>
				</tbody>
	      </table>
  	</div>
    `,

		props: ["manifestations"],

		data: function() {
			return {
				isAdmin: false,
			}
		},

		methods: {
			emitSelectedManifestation: function(location) {
				this.$emit("manifestation-selected", location);
			},
			
			deleteManifestation: function(manifestation) {
	    		axios(deleteRestConfig("/WebProjekat/rest/manifestations/" + manifestation.id))
					.then(response => {
						this.$emit("deleted-manifestation", response.data);
					})
					.catch(error => console.log(error));
			},
		},

		mounted: function() {
			this.isAdmin = localStorage.isLoggedUserRole(["Administrator"]);
		}
	});