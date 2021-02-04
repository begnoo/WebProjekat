Vue.component("buyer-tickets-page", {
    template: `
    <div class="container">
        <div v-if="tickets" class="row mx-auto">
			<search-tickets-form v-on:search-tickets-data="getBuyerTickets"></search-tickets-form>
            <div v-show="tickets && tickets.length != 0" class="col mt-3">
                <buyer-tickets-table :tickets="tickets"></buyer-tickets-table>
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setTickets"></pagination>
            </div>		
		</div>		
		<div v-show="tickets.length === 0" class="row justify-content-center">
				<div class="col mt-5" style="text-align: center">
					<h3>No such tickets.</h3>
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
			trigger: false,
        }
    },
    
    methods: {
		
		setTickets: function({emittedData, selectedPage}){
			this.tickets = emittedData;
			this.selectedPage = selectedPage;
		},
		getBuyerTickets: function(searchData){
			searchData["buyerId"] = localStorage.getObject("loggedUser").user.id;
			this.restConfig = postRestConfig("/WebProjekat/rest/tickets/advance-search", {}, searchData);
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
