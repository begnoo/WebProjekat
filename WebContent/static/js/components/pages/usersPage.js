Vue.component('users-page',
{
    template:
    `
	<div class="container">
		<div class="row mt-3">
			<div class="col">
				<search-users-form v-on:search-user-data="getUsersBasedOnSearchData"></search-users-form>
				<users-table :users="users" v-on:deleted-user="trigger = !trigger"></users-table>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col">
				<button type="button"
				 class="btn btn-primary btn-sm float-right mr-3"
				 data-toggle="modal"
				 data-target="#addSellerModal">Add Seller</button>
			</div">
		</div>
		
		<div class="row mt-3">
			<div class="col">
			    <pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setUsers"></pagination>
			</div>
		</div>
		
		<add-seller-modal v-on:create-user-success="trigger = !trigger"></add-seller-modal>
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