Vue.component("order-table", {
	template: `
    <div class="d-flex justify-content-center">
	<div class="row mt-3">
		<div v-if="!isShoppingCartEmpty()" class="mt-3">
			<table class="table" >
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
							<td>
								<router-link :to="'/manifestations/' + manifestationOrder.manifestation.id">
									{{manifestationOrder.manifestation.name}}
								</router-link>
							</td>
							<td>{{ticketType}}</td>
							<td>{{number}}</td>
							<td>{{manifestationOrder.prices[ticketType]*number}}</td>
							<td style="text-align: center"><button class="btn btn-danger btn-sm" v-on:click="openDeleteTicketsModal(manifestationId, ticketType)">Delete</button></td>	
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
					<td><button class="btn btn-success btn-sm" style="text-align: center" v-on:click="openBuyTicketsModal">Buy Tickets</button></td>
				</tr>
			</tbody>
			</table>
		</div>
		<div v-else class="mt-3">
			<h3>You have nothing in your shopping cart.</h3>
		</div>
	</div>
		<confirmation-modal
			type="success"
			modalName="buyTicketsModal" 
			title="Buy Tickets" 
			:callback="buyTickets"
			:callbackData="shoppingCart">
			Are you sure you want to buy this ticket order?
		</confirmation-modal>
		<confirmation-modal
			type="danger"
			modalName="deleteTicketsModal" 
			title="Delete Tickets" 
			:callback="deleteOrder"
			:callbackData="[manifestationIdToDelete, ticketTypeToDelete]">
			Are you sure you want to delete this ticket order?
		</confirmation-modal>
	</div>
    `,

	data: function() {
		return {
			shoppingCart: {},
			manifestationIdToDelete: null,
			ticketTypeToDelete: null,
		}
	},

	methods: {
		openDeleteTicketsModal: function(manifestationId, ticketType){
			this.manifestationIdToDelete = manifestationId;
			this.ticketTypeToDelete = ticketType;
			$('#deleteTicketsModal').modal('toggle');
		},
		openBuyTicketsModal: function(ticket){
			$('#buyTicketsModal').modal('toggle');
		},
		deleteOrder: function(data) {
			let manifestationId = data[0];
			let ticketType = data[1];
			this.shoppingCart[manifestationId].order[ticketType] = 0;
			const numberOfTickets = Object.values(this.shoppingCart[manifestationId].order).reduce((sum, value) => sum += value);
			if (!numberOfTickets) {
				delete this.shoppingCart[manifestationId];
			}
			$('#deleteTicketsModal').modal('toggle');
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
			return sum;
		},
		buyTickets: async function() {
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

				await axios(postRestConfig("../WebProjekat/rest/tickets/", {}, shoppingCartToSend))
					.then(response => {
						toastr.success(`We have received your order.`, '');
						const updatedBuyer = response.data[0].buyer;
						this.updateBuyerInLocalStorage(updatedBuyer, response.data);
					})
					.catch(error => toastr.error(error.response.data.errorMessage, ''));
			}
			this.shoppingCart = {};
			$('#buyTicketsModal').modal('toggle');
			localStorage.setObject("shoppingCart", this.shoppingCart);
		},
		
		isShoppingCartEmpty: function() {
			if (this.shoppingCart == null) {
				return true;
			}
			return Object.keys(this.shoppingCart).length === 0;
		},
		
		updateBuyerInLocalStorage: function(updatedBuyer, tickets) {
			let loggedUser = localStorage.getObject("loggedUser");
			loggedUser.user.typeId = updatedBuyer.typeId;
			loggedUser.user.points = updatedBuyer.points;
			loggedUser.user.tickets.push(...tickets);
			localStorage.setObject("loggedUser", loggedUser);
		},

	},

	mounted: function() {
		this.shoppingCart = localStorage.getObject("shoppingCart");
	}

});
