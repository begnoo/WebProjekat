Vue.component("user-tickets-page", {
    template: `
    <div class="container">
		<div class="row">
			<search-tickets-form v-on:search-tickets-data="getBuyerTickets"></search-tickets-form>
			<div v-show="tickets && tickets.length != 0" class="col mt-3">
                <tickets-table :tickets="tickets"></tickets-table>
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setTickets"></pagination>
            </div>
		</div>
		<div class="row justify-content-center">
			<div v-show="tickets.length === 0" class="row">
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
			trigger: false,
        }
    },

	watch:{
		"$route.params.id": function(newValue){
			this.refresh();
			this.trigger = !this.trigger;
		},
	},
    
    methods: {
		
		setTickets: function({emittedData, selectedPage}){
			this.tickets = emittedData;
			this.selectedPage = selectedPage;
		},
		getBuyerTickets: function(searchData){
			searchData["buyerId"] = this.$route.params.id;
			this.restConfig = postRestConfig("/WebProjekat/rest/tickets/advance-search", {}, searchData);
			this.trigger = !this.trigger;
		},
		refresh: function(){
			const buyerId = this.$route.params.id;	
			this.restPath = "/WebProjekat/rest/users/buyers/" + buyerId + "/tickets";
			this.params = {
				"only-reserved": false
			};
			this.restConfig = getRestConfig(this.restPath, this.params);
		}

    },

	created: function(){
		this.refresh();
	},
	
});
