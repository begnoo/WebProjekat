Vue.component("manifestation-tickets", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
				<div v-if="!sharedState.userLoggedIn">
					<p>Please log in to access the tickets page.</p>
				</div>
				<div v-else>
				<form class="mt-3">
					<div class="form-group">
						<label for="ticketTypeSelect" >Ticket type: </label>
						<select id="ticketType" name="ticketTypeSelect" v-model="ticketType">
							<option value="Regular">Regular</option>
							<option value="FanPit">Fan Pit</option>
							<option value="VIP">VIP</option>
						</select>
					</div>
					<div class="form-group">
						<label for="ticketAmountField" >Amount: </label>
						<input type="number" v-model="ticketAmount" name="ticketAmountField">
					</div>
					<div class="mb-3" >
						<button v-on:click="addToCart" class="btn btn-primary">Add to Cart</button>
					</div>
					{{this.ticketPricesWithDiscounts}}
				</form>
				</div>
            </div>
        </div>
    </div>
    `,

    props: ["manifestation"],

    data: function () {
        return {
            ticketType: null,
			ticketAmount: null,
            sharedState: store.state,
            ticketPricesWithDiscounts: {},
        };
    },

    methods: {
        getTicketPriceForUser: function () {
            const regularTicketPrice = this.manifestation.regularTicketPrice;
            console.log(window.localStorage.getObject("loggedUser"));
            const { buyerTypeId } = window.localStorage.getObject(
                "loggedUser"
            ).user;
            axios
                .get(
                    "../WebProjekat/rest/tickets/buyer-type/" +
                        buyerTypeId +
                        "/prices/" +
                        regularTicketPrice
                )
                .then(
                    (response) =>
                        (this.ticketPricesWithDiscounts = response.data)
                )
                .catch((error) => alert(error));
        },
		addToCart: function(event){
			event.preventDefault();
			this.sharedState.order[this.ticketType] += ticketAmount;
		}
    },

    mounted: function () {
        this.getTicketPriceForUser();
    },
});
