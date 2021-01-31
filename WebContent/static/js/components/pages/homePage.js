Vue.component("home-page", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-10 mt-3">
				<search-manifestations-form v-on:search-manifestation-data="searchManifestations"></search-manifestations-form>
                <manifestation-table v-on:add-manifestation-success="trigger = !trigger" :manifestations="manifestations"></manifestation-table>
				
				<button type="button"
				 class="btn btn-primary btn-sm float-right"
				 data-toggle="modal"
				 data-target="#addManifestationModal">Add Manifestation</button>
            </div>
            <div class="col">
            </div>
        </div>
        <div class="row">
			<div class="col">
            </div>
			<div class="col-10">
				<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setManifestations"></pagination>
            </div>
			<div class="col">
            </div>
        </div>
		<add-manifestation-modal></add-manifestation-modal>
    </div>
    `,
    
	data: function () {
        return {
            manifestations: [],
			pageSize: 3,
			selectedPage: 1,
			restConfig: null,
			restPath: "/WebProjekat/rest/manifestations",
			params: {"order-by-date" : true},
			trigger: false,
        };
    },

    methods: {
	
		setManifestations: function({emittedData, selectedPage}){
			this.manifestations = emittedData;
			this.selectedPage = selectedPage;
		},
		
		searchManifestations: function(searchData){
			this.restConfig = postRestConfig("/WebProjekat/rest/manifestations/advance-search", {}, searchData);
			this.trigger = !this.trigger;
		},
	},
	
	created: function(){
		this.restConfig = getRestConfig(this.restPath, this.params);
	}
});
