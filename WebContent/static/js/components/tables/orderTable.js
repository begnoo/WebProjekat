Vue.component("order-table", {
	template: `
    <div class="d-flex justify-content-center">
	<div class="row">
		<div v-if="Object.keys(shoppingCart).length !== 0">
			<table  class="table" >
			  <thead class="thead-light">
			    <tr>
				  <th scope="col">Manifestation</th>
			      <th scope="col">Type</th>
				  <th scope="col">Num. of Tickets</th>
			      <th scope="col">Price(RSD)</th>
				  <th scope="col"></th>
			    </tr>
			  </thead>
			  <tbody v-for="(manifestationOrder, manifestationId) in shoppingCart">
					<template v-for="(number, ticketType) in manifestationOrder.order">
						<tr v-if="number">
							<td>{{manifestationOrder.manifestation.name}}</td>
							<td>{{ticketType}}</td>
							<td>{{number}}</td>
							<td>{{manifestationOrder.prices[ticketType]*number}}</td>
							<td style="text-align: center"><button class="btn btn-danger btn-sm" v-on:click="deleteOrder(manifestationId, ticketType)">Delete</button></td>	
						</tr>
					</template>
				</tr>
			  </tbody>
			<tbody>
				<tr>
					<td>Total</td>
					<td></td>
					<td></td>
					<td>{{getTicketOrderSum()}}</td>
					<td><button class="btn btn-success btn-sm" style="text-align: center" v-on:click="buyTickets">Buy Tickets</button></td>
				</tr>
			</tbody>
			</table>
		</div>
		<div v-else>
			<h3 class="mt-3">You have nothing in your shopping cart.</h3>
		</div>
	</div>
	</div>
    `,

	data: function() {
		return {
			shoppingCart: {},
		}
	},

	methods: {
		deleteOrder: function(manifestationId, ticketType) {
			this.shoppingCart[manifestationId].order[ticketType] = 0;
			const numberOfTickets = Object.values(this.shoppingCart[manifestationId].order).reduce((sum, value) => sum += value);
			if (!numberOfTickets) {
				delete this.shoppingCart[manifestationId];
			}
			localStorage.setObject("shoppingCart", this.shoppingCart);
		},
		getTicketOrderSum: function() {
			let sum = 0;
			for (let manifestationOrder of Object.values(this.shoppingCart)) {
				const { prices, order } = manifestationOrder;
				for (let ticketType of Object.keys(order)) {
					sum += prices[ticketType] * order[ticketType];
				}
			}
			console.log(sum);
			return sum;
		},
		buyTickets: function() {
			for (let manifestationOrder of Object.values(this.shoppingCart)) {
				const { manifestation, order } = manifestationOrder;

				let shoppingCartToSend = {
					manifestationId: manifestation.id,
					buyerId: localStorage.getObject("loggedUser").user.id,
					numberOfOrderedTicketsMap: {},
				};

				for (ticketType of Object.keys(order)) {
					if (order[ticketType] > 0) {
						shoppingCartToSend.numberOfOrderedTicketsMap[ticketType] = order[ticketType];
					}
				}
				
				console.log(shoppingCartToSend);
				console.log(manifestation.id);
				
				axios.post("../WebProjekat/rest/tickets/", shoppingCartToSend)
					.then(response => {
						console.log(response.data);
					})
					.catch(error => console.log(error));
			}
			this.shoppingCart = {};
			localStorage.setObject("shoppingCart", this.shoppingCart);
			if(Object.keys(this.shoppingCart).length === 0){
				alert("Uspesno");
			}

		}

	},

	mounted: function() {
		this.shoppingCart = localStorage.getObject("shoppingCart");
	}

});
