Vue.component("manifestation-tickets", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
				<div v-if="!sharedState.userLoggedIn">
					<p>Please log in to access the tickets page.</p>
				</div>
				<div v-else>
				<form>
					<label for="ticketTypeSelect" >Ticket type: </label>
					<select id="ticketType" name="ticketTypeSelect" v-model="ticketType">
						<option value="Regular">Regular</option>
						<option value="FanPit">Fan Pit</option>
						<option value="VIP">VIP</option>
					</select>
					<input type="submit">
					{{this.ticketPricesWithDiscounts}}
				</form>
				</div>
            </div>
        </div>
    </div>
    `,

	props: ["manifestation"],
	
	data: function(){
		return {
			ticketType: null,
			sharedState: store.state,
			ticketPricesWithDiscounts: {}
		};
	},
	
	methods: {
		getTicketPriceForUser: function(){
			const regularTicketPrice = this.manifestation.regularTicketPrice;
			console.log(window.localStorage.getObject("loggedUser"));
			const {buyerTypeId} = window.localStorage.getObject("loggedUser").user;
			axios.get("../WebProjekat/rest/tickets/buyer-type/" + buyerTypeId + "/prices/" + regularTicketPrice)
			.then(response => this.ticketPricesWithDiscounts = response.data)
			.catch(error => alert(error));
		}
	},
	
	mounted: function(){
		this.getTicketPriceForUser();
	}
});
