Vue.component("manifestation-tickets", {
	template: `
    <div class="container d-flex justify-content-center">
		<div v-if="!sharedState.userLoggedIn">
			<h3 class="mt-3">Please log in to access the tickets page.</h3>
		</div>
        <div v-else class="row">
			<div class="col-1">
			</div>
            <div class="col-4">

				<form class="mt-3">
					<div class="form-group">
						<label for="ticketTypeSelect" class="form-label">Ticket type: </label>
						<select id="ticketType" name="ticketTypeSelect" class="custom-select" v-model="ticketType">
							<option value="Regular">Regular</option>
							<option value="Vip">Vip</option>
							<option value="FanPit">Fan Pit</option>
						</select>
					</div>
					<div class="form-group">
						<label for="ticketAmountField" class="form-label">Amount: </label>
						<input type="number" min="1" v-model="ticketAmount" class="form-control" name="ticketAmountField">
					</div>
					<div class="form-group">
						<label for="ticketsPrice" class="form-label">Price of tickets: </label>
						<input type="text" style="background-color: white;" :value="ticketsPrice" class="form-control" name="ticketsPriceField" disabled>
					</div>
					<div v-if="isBuyer" class="mb-3" >
						<button v-on:click="addToCart" class="btn btn-primary">Add to Cart</button>
					</div>
				</form>
            </div>
			<div class="col-2">
			</div>
			<div class="col-3">
				<table class="table mt-3">
				  <thead>
				    <tr sytle="border-top: hidden;">
				      <th scope="col">Type</th>
				      <th scope="col">Price(RSD)</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <td>Regular</td>
				      <td>{{ticketPricesWithDiscounts.Regular}}</td>
				    </tr>
				    <tr>
				      <td>Vip</td>
				      <td>{{ticketPricesWithDiscounts.Vip}}</td>
				    </tr>
					<tr>
				      <td>Fan Pit</td>
				      <td>{{ticketPricesWithDiscounts.FanPit}}</td>
				    </tr>
				  </tbody>
				</table>
			</div>
        </div>
    </div>
    `,

	props: ["manifestation"],

	data: function() {
		return {
			ticketType: "Regular",
			isBuyer: false,
			ticketAmount: 1,
			sharedState: store.state,
			ticketPricesWithDiscounts: {
				Regular: 0,
				Vip: 0,
				FanPit: 0,
			},
		};
	},

	computed: {
		ticketsPrice: function() {
			return this.ticketAmount * this.ticketPricesWithDiscounts[this.ticketType];
		}
	},

	methods: {
		getTicketPriceForUser: function() {
			const regularTicketPrice = this.manifestation.regularTicketPrice;
			const loggedUser = window.localStorage.getObject("loggedUser");
			if (loggedUser) {
				const buyerTypeId = this.isBuyer ? loggedUser.user.buyerTypeId : null;
				axios(getRestConfig("../WebProjekat/rest/tickets/prices", {
					buyerTypeId: buyerTypeId,
					price: regularTicketPrice,
				}))
					.then(
						(response) =>
							(this.ticketPricesWithDiscounts = response.data)
					)
					.catch((error) => alert(error));
			}

		},
		addToCart: function(event) {
			event.preventDefault();
			if(this.isBuyer){
				if (!localStorage.getObject("shoppingCart")) {
				const shoppingCart = {};
				localStorage.setObject("shoppingCart", shoppingCart);
				}
				const shoppingCart = localStorage.getObject("shoppingCart");
				if (!shoppingCart[this.manifestation.id]) {
					shoppingCart[this.manifestation.id] = {
						manifestation: this.manifestation,
						order: {
							Vip: 0,
							Regular: 0,
							FanPit: 0,
						},
						prices: this.ticketPricesWithDiscounts,
					};
				}
				shoppingCart[this.manifestation.id].order[this.ticketType] += this.ticketAmount;
				localStorage.setObject("shoppingCart", shoppingCart);
				alert("Uspeno");
			}
		}
	},

	mounted: function() {
		this.isBuyer = localStorage.isLoggedUserRole(["Buyer"]);
		this.getTicketPriceForUser();
	},
});
