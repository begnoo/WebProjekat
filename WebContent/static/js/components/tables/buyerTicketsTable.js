Vue.component("buyer-tickets-table", {
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

	props: ["tickets"],

	methods: {
		cancelTicket: function(ticketId) {
			axios(deleteRestConfig("/WebProjekat/rest/tickets/" + ticketId))
				.then(response => {
					if (this.updateTicket(response.data)) {
						this.updateBuyerInLocalStorage(response.data.buyer, ticketId);
					}
				})
				.catch(error => console.log(error));
		},
		updateTicket: function(updatedTicket) {
			if (updatedTicket.length == 0) {
				alert("Too late.");
				return false;
			}
			const index = this.tickets.findIndex((ticket) => ticket.id === updatedTicket.id);
			this.tickets.splice(index, 1, updatedTicket);
			return true;
		},
		updateBuyerInLocalStorage: function(updatedBuyer, ticketId) {
			let loggedUser = localStorage.getObject("loggedUser", ticketId);
			loggedUser.user.buyerTypeId = updatedBuyer.buyerTypeId;
			loggedUser.user.points = loggedUser.points;
			console.log(ticketId, loggedUser.user.tickets);
			ticket = loggedUser.user.tickets.find(ticket => ticket.id == ticketId);
			ticket.status = "Canceled";
			localStorage.setObject("loggedUser", loggedUser);
		},

	},

});
