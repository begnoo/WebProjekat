Vue.component("buyer-tickets-page", {
    template: `
    <div class="container">
        <div v-if="tickets" class="row mx-auto">
            <div class="col-4 mt-3">
				<form>
	              <div class="form-group">
				    <label for="filterSelect">Show: </label>
				    <select v-model="selectFilter" class="form-control" id="filterSelect">
				      <option value="false">All Tickets</option>
				      <option value="true">Only Reserved Tickets</option>
				    </select>
				  </div>
				<button v-on:click="getBuyerTickets(selectFilter)" type="submit" class="btn btn-primary">Submit</button>
				</form>
            </div>
            <div v-if="tickets.length !== 0" class="col-8 mt-3">
                <tickets-table :tickets="this.tickets"></tickets-table>
            </div>
			<div v-else class="col-8 mt-5">
				<h3>You have no reserved tickets.</h3>
			</div>
        </div>
		<div v-else-if="!selectFilter && tickets && tickets.length !== 0" class="row">
			<div class="col mt-3">
				<h3>You have no tickets.</h3>
			</div>
		</div>
    </div>
    `,

	data: function(){
		return {
			tickets: null,
			selectFilter: false,
		};
	},
	
	methods: {
		getBuyerTickets: function(onlyReserved){
			console.log("drogica1");
			console.log(onlyReserved);
			const buyerId = localStorage.getObject("loggedUser").user.id;
			axios.get("/WebProjekat/rest/users/buyers/" + buyerId + "/tickets", {
				params:{
					"only-reserved": onlyReserved,
				}
			})
			.then(response => {
				this.tickets = response.data;
			})
			.catch(error => console.log(error));
		}
	},
	
	mounted: function(){
		this.getBuyerTickets(false);
	}
});
