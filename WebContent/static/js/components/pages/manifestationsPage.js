Vue.component('manifestations-page',
	{
		template:
			`
	<div class="container">
		<div class="row mt-3">
			<search-manifestations-form :sellerId="sellerId" v-on:search-manifestation-data="searchManifestations"></search-manifestations-form>
			<manifestation-table :manifestations="manifestations" 
			v-on:deleted-manifestation="trigger = !trigger"
			v-on:manifestation-selected="setSelectedManifestation"></manifestation-table>
		</div>
		<div class="row mt-3">
			<div class="col">		
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setManifestations"></pagination>
			</div>

		</div>
		
  	</div>
    `,

		data: function() {
			return {
				manifestations: [],
				manifestation: {},
				restPath: "/WebProjekat/rest/manifestations/advance-search",
				restConfig: null,
				pageSize: 5,
				selectedPage: 1,
				trigger: false,
				sellerId: null,
			}
		},

		methods: {

			setManifestations: function({ emittedData, selectedPage }) {
				this.manifestations = emittedData
				this.selectedPage = selectedPage
			},

			updateCurrentManifestations: function(updatedManifestation) {
				const index = this.manifestations.findIndex((manifestation) => manifestation.id === updatedManifestation.id);
				this.manifestations.splice(index, 1, updatedManifestation);
			},

			setSelectedManifestation: function(emittedManifestation) {
				this.manifestation = emittedManifestation;
			},
			searchManifestations: function(searchData) {
				this.restConfig = postRestConfig("/WebProjekat/rest/manifestations/advance-search", {}, searchData);
				this.trigger = !this.trigger;
			},


		},

		created: function() {
			if (localStorage.isLoggedUserRole(["Seller"])) {
				this.sellerId = localStorage.getObject("loggedUser").user.id;
			}
			this.searchManifestations({
				name: "",
				type: "",
				city: "",
				priceFrom: 0,
				priceTo: Math.pow(2,31) - 1,
				onlyNotSolved: false,
				dateTo: null,
				dateFrom: null,
				sortBy: "",
				orderBy: "Ascending",
				sellerId: this.sellerId,
				statusSetting: "All",
				status: true,
			}
			);
		}

	});