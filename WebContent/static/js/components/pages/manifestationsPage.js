Vue.component('manifestations-page',
	{
		template:
			`
	<div class="container">
		<div class="row mt-3">
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
				restPath: "../WebProjekat/rest/manifestations/suggestions",
				restConfig: null,
				pageSize: 5,
				selectedPage: 1,
				trigger: false,
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


		},

		created: function() {
			this.restConfig = getRestConfig(this.restPath);
		}

	});