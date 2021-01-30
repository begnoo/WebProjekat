Vue.component('distrustful-buyers-page',
{
    template:
    `
	<div>
		<div v-show="users.length != 0">
		    	<users-table block="true" v-on:deleted-user="trigger = !trigger" :users="users"></users-table>
	    		<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setUsers"></pagination>
		</div>
		<div class="container" v-show="users.length == 0">
			<div class="row mt-5 justify-content-center">
				<h3>There are no distrustful users currently.</h3>
			</div>
		</div>
		
  	</div>
    `,

    data: function() {
        return {
            users: [],
			restPath : "/WebProjekat/rest/users/buyers/distrustful",
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
			console.log(this.users)
		},

    },

	created: function(){
		this.restConfig = getRestConfig(this.restPath);
	}
		
});