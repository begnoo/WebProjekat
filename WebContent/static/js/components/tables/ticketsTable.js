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
				    <tr v-for="ticket in ticketsToShow" :key="ticket.id">
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
		<div>
		<nav aria-label="Page navigation example">
		  <ul class="pagination">
		    <li v-on:click="changePageTo(currentPageNumber-1)" class="page-item"><a class="page-link">Previous</a></li>
		    <li class="page-item"><a class="page-link">{{currentPageNumber}}</a></li>
		    <li v-on:click="changePageTo(currentPageNumber+1)" class="page-item"><a class="page-link">Next</a></li>
		  </ul>
		</nav>
		</div>
    </div>
    `,

	props: ['tickets'],
	
	watch:{
		tickets : function(){
			this.changePageTo(this.currentPageNumber);
		}
	},

	data: function() {
		return {
			ticketsToShow : [],
			currentPageNumber: 0,
			pageSize : 5,
		};
	},

	methods: {
		cancelTicket: function(ticketId) {
			axios.put("/WebProjekat/rest/tickets/" + ticketId + "/cancel")
				.then(response => {
					if (this.updateTicket(response.data)) {
						this.updateBuyerInLocalStorage(response.data.buyer);
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
		updateBuyerInLocalStorage: function(updatedBuyer) {
			let loggedUser = localStorage.getObject("loggedUser");
			loggedUser.user = updatedBuyer;
			localStorage.setObject("loggedUser", loggedUser);
		},
		changePageTo: function(pageNumber){
			if(pageNumber <= 0){
				return;
			}
			const begin = Math.min((pageNumber-1)*this.pageSize, this.tickets.length);
			const end = Math.min(pageNumber*this.pageSize, this.tickets.length);
			console.log(begin, end);
			
			if(begin == end){
				return;
			}
			this.currentPageNumber = pageNumber;
			this.ticketsToShow = this.tickets.slice(begin, end);
		},
	},
	
	mounted: function(){
		this.changePageTo(this.currentPageNumber+1);
	}

});
