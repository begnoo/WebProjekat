Vue.component("tickets-table", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col mt-3">
				<table class="table">
				  <thead class="thead-dark">
				    <tr>
				      <th scope="col">Manifestation</th>
				      <th scope="col">Type</th>
				      <th scope="col">Price(RSD)</th>
					  <th scope="col">Status</th>
					  <th scope="col"></th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr v-for="ticket in tickets" :key="ticket.id">
				      <th><router-link :to="'/manifestations/' + ticket.manifestation.id">{{ticket.manifestation.name}}</router-link></th>
				      <td>{{ticket.type}}</td>
				      <td>{{ticket.price}}</td>
				      <td>{{ticket.status}}</td>
					  <td v-if="ticket.status == 'Reserved'" style="text-align: center">
						<button class="btn btn-danger btn-sm" v-on:click="cancelTicket(ticket.id)">Cancel</button>
					  </td>	
					  <td v-else></td>
				    </tr>
				  </tbody>
				</table>
            </div>
        </div>
    </div>
    `,
	
	props:['tickets'],
	
	methods:{
		cancelTicket: function(ticketId){
			axios.put("/WebProjekat/rest/tickets/" + ticketId + "/cancel")
			.then(response => {
				this.updateTicket(response.data);
			})
			.catch(error => console.log(error));
		},
		updateTicket: function(updatedTicket){
			if(updatedTicket.length == 0){
				alert("Too late.");
				return;
			}
			const index = this.tickets.findIndex((ticket) => ticket.id === updatedTicket.id);
			this.tickets.splice(index, 1, updatedTicket);
		}
	},
	
});
