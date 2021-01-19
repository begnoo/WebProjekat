Vue.component("buyer-tickets-page", {
    template: `
    <div class="d-flex justify-content-center">
        <div class="row">
            <div v-if="tickets && tickets.length !== 0" class="col mt-3">
                <tickets-table :tickets="this.tickets"></tickets-table>
            </div>
			<div v-else class="col-10 mt-3">
                <h3>You have no tickets.</h3>
            </div>
        </div>
    </div>
    `,

	data: function(){
		return {
			tickets: null
		};
	},
	
	methods: {
		getBuyerTickets: function(){
			const buyerId = localStorage.getObject("loggedUser").user.id;
			axios.get("/WebProjekat/rest/users/buyers/" + buyerId + "/tickets")
			.then(response => {
				this.tickets = response.data;
			})
			.catch(error => console.log(error));
		}
	},
	
	mounted: function(){
		this.getBuyerTickets();
	}
});
