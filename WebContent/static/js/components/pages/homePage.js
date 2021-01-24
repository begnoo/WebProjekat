Vue.component("home-page", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-10 mt-3">
                <manifestation-table :manifestations="manifestations"></manifestation-table>
				<pagination :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setManifestations"></pagination>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    `,
    
	data: function () {
        return {
            manifestations: [],
			pageSize: 3,
			selectedPage: 1,
			restConfig: null,
			restPath: "/WebProjekat/rest/manifestations",
			params: {"order-by-date" : true}
        };
    },

    methods: {
	
		setManifestations({emittedData, selectedPage}){
			this.manifestations = emittedData
			this.selectedPage = selectedPage
		},
	},
	
	created: function(){
		this.restConfig = getRestConfig(this.restPath, this.params);
	}
});
