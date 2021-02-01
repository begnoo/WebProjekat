Vue.component('users-page',
{
    template:
    `
	<div>
		<search-users-form v-on:search-user-data="getUsersBasedOnSearchData"></search-users-form>
    	<users-table :users="users" v-on:deleted-user="trigger = !trigger"></users-table>
	    <pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setUsers"></pagination>
  	</div>
    `,

    data: function() {
        return {
            users: [],
			restPath : "../WebProjekat/rest/users",
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
			trigger: false,
        }
    },
    
    methods: {
		
		setUsers: function({emittedData, selectedPage}){
			this.users = emittedData
			this.selectedPage = selectedPage
		},
		
		getUsersBasedOnSearchData: function(searchData){
			this.restConfig = postRestConfig("/WebProjekat/rest/users/advance-search", {}, searchData);
			this.trigger = !this.trigger;
		}

    },

	created: function(){
		this.restConfig = getRestConfig(this.restPath);
	}
		
});