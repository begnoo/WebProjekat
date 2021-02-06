Vue.component("buyer-tickets-table", {
	template: `
    <div class="container">
        <div class="row">
            <div class="col mt-3">
				<table class="table">
				  <thead class="thead-dark">
				    <tr>
				      <th scope="col">Id</th>
				      <th scope="col">Manifestation</th>
				      <th scope="col">Type</th>
				      <th scope="col">Price(RSD)</th>
					  <th scope="col">Status</th>
					  <th scope="col"></th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr v-for="ticket in tickets" :key="ticket.id">
					  <td>{{ticket.uniqueId}}</td>
				      <th><router-link :to="'/manifestations/' + ticket.manifestation.id">{{ticket.manifestation.name}}</router-link></th>
				      <td>{{ticket.type}}</td>
				      <td>{{ticket.price}}</td>
				      <td>{{ticket.status}}</td>
					  <td v-if="ticket.status == 'Reserved' && !isEventCancelationPeriodOver(ticket.manifestation)" style="text-align: center">
						<button class="btn btn-danger btn-sm" v-on:click="openCancelModal(ticket)">Cancel</button>
					  </td>	
					  <td v-else></td>
				    </tr>
				  </tbody>
				</table>
            </div>
        </div>
		<confirmation-modal
		type="danger"
		modalName="cancelModal" 
		title="Cancel Ticket" 
		:callback="cancelTicket"
		:callbackData="ticketToCancel">
			Are you sure you want to cancel this ticket?
		</confirmation-modal>
    </div>
    `,

	props: ["tickets"],
	
	data: function(){
 		return {
			ticketToCancel: null,
		};
	},

	methods: {
		isEventCancelationPeriodOver: function(manifestation){
			const eventDate = moment(manifestation.eventDate, "YYYY-MM-DD hh:mm");
			const res = eventDate.isBefore(moment().add(7, 'd'));
			return res;
		},
		openCancelModal: function(ticket){
			this.ticketToCancel = ticket;
			$('#cancelModal').modal('toggle');
		},
		cancelTicket: function(ticket) {
			axios(deleteRestConfig("/WebProjekat/rest/tickets/" + ticket.id))
				.then(response => {
					if (this.updateTicket(response.data)) {
						toastr.success(`You have successfully canceled ticket for ${ticket.manifestation.name}.`, '')
						this.updateBuyerInLocalStorage(response.data.buyer, ticket.id);
						$('#cancelModal').modal('toggle');
					}
				})
				.catch(error => toastr.error(error.response.data.errorMessage, ''));
		},
		updateTicket: function(updatedTicket) {
			if (updatedTicket.length == 0) {
				return false;
			}
			
			const index = this.tickets.findIndex((ticket) => ticket.id === updatedTicket.id);
			this.tickets.splice(index, 1, updatedTicket);
			return true;
		},
		updateBuyerInLocalStorage: function(updatedBuyer, ticketId) {
			let loggedUser = localStorage.getObject("loggedUser", ticketId);
			loggedUser.user.typeId = updatedBuyer.typeId;
			loggedUser.user.points = loggedUser.points;
			ticket = loggedUser.user.tickets.find(ticket => ticket.id == ticketId);
			ticket.status = "Canceled";
			localStorage.setObject("loggedUser", loggedUser);
		},

	},

});
