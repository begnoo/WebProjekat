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
				<button v-on:click="getBuyerTickets()" type="submit" class="btn btn-primary">Submit</button>
				</form>
            </div>
            <div v-show="tickets && tickets.length != 0" class="col-8 mt-3">
                <buyer-tickets-table :tickets="tickets"></buyer-tickets-table>
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setTickets"></pagination>
            </div>
			<div v-show="selectFilter &&  tickets.length === 0" class="row">
				<div class="col mt-5">
					<h3>No such tickets.</h3>
				</div>
			</div>		
		</div>		
    </div>
    `,


    data: function() {
	    return {
	        tickets: [],
			restPath : "",
			params: null,
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
			selectFilter: false,
			trigger: false,
        }
    },
    
    methods: {
		
		setTickets: function({emittedData, selectedPage}){
			console.log(emittedData)
			this.tickets = emittedData;
			this.selectedPage = selectedPage;
		},
		getBuyerTickets: function(){
			this.restConfig.params["only-reserved"] = this.selectFilter;
			this.trigger = !this.trigger;
		}

    },

	created: function(){
		const buyerId = localStorage.getObject("loggedUser").user.id;	
		this.restPath = "/WebProjekat/rest/users/buyers/" + buyerId + "/tickets";
		this.params = {
			"only-reserved": this.selectFilter
		};
		this.restConfig = getRestConfig(this.restPath, this.params);
	},
	
});
