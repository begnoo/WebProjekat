Vue.component('comments-page',
{
    template:
    `
	<div>
    	<comments-small-table :comments="comments"></comments-small-table>
	    <pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setComments"></pagination>
  	</div>
    `,

    data: function() {
        return {
            comments: [],
			restPath : "../WebProjekat/rest/manifestations/",
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
			trigger: false,
        }
    },
    
    methods: {
		
		setComments: function({emittedData, selectedPage}){
			this.comments = emittedData
			this.selectedPage = selectedPage
		}
    },

	created: function(){
		let manifestationId = this.$route.params.id;
		this.restPath += manifestationId + "/comments";
		this.restConfig = getRestConfig(this.restPath);
	}
		
});