Vue.component("manifestation-tickets-page", {
    template: `
    <div class="container">
		<search-tickets-form :manifestationId="manifestationId" v-on:search-tickets-data="getBuyerTickets"></search-tickets-form>
        <div v-if="tickets" class="row mx-auto">
            <div v-show="tickets && tickets.length != 0" class="col mt-3">
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
			restPath : "/WebProjekat/rest/tickets/advance-search",
			params: null,
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
			selectFilter: false,
			trigger: false,
			manifestationId: null,
        }
    },
    
    methods: {
		
		setTickets: function({emittedData, selectedPage}){
			console.log(emittedData);
			this.tickets = emittedData;
			this.selectedPage = selectedPage;
		},
		getBuyerTickets: function(searchData){
			console.log(searchData);
			this.restConfig = postRestConfig(this.restPath, {}, searchData);
			this.trigger = !this.trigger;
		}

    },

	created: function(){
		this.manifestationId = this.$route.params["id"];	
		this.getBuyerTickets(
		{
			manifestationName: "",
			type: "",
			priceFrom: 0,
			priceTo: Math.pow(2,31) - 1,
			dateTo: null,
			dateFrom: null,
			status: "",
			sortBy: "",
			orderBy: "Ascending",
			buyerId: null,
			manifestationId: this.manifestationId,
		}
		);
	},
	
});
