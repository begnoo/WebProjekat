Vue.component('users-page',
{
    template:
    `
	<div>
    	<users-table :users="users"></users-table>
	    <pagination :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setUsers"></pagination>
  	</div>
    `,

    data: function() {
        return {
            users: [],
			restPath : "../WebProjekat/rest/users",
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
        }
    },
    
    methods: {
		
		setUsers: function({emittedData, selectedPage}){
			this.users = emittedData
			this.selectedPage = selectedPage
		}

    },

	created: function(){
		this.restConfig = getRestConfig(this.restPath);
	}
		
});