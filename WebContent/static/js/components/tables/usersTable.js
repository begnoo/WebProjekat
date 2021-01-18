Vue.component('users-table',
{
    template:
    `
    	<div class="container">
	        <table class="table">
				<thead class="thead-dark">
					  <tr>
							<th scope="col">#</th>
							<th scope="col">Name</th>
							<th scope="col">Surname</th>
							<th scope="col">Username</th>
							<th scope="col">Gender</th>
							<th scope="col">Birthdate</th>
							<th scope=""></th>
							<th scope=""></th>
					  </tr>
				</thead>
				<tbody>
					  <tr
					  	v-for="(user, index) in users"
					  	v-on:click="selectUser(user)"
					  	v-bind:class="{ 'table-active' : selectedUser === user}"
					  >
							<th scope="row">{{ (selectedPage - 1) * pageSize + index + 1 }}</th>
							<td>{{ user.name }}</td>
							<td>{{ user.surname }}</td>
							<td>{{ user.username }}</td>
							<td>{{ user.gender }}</td>
							<td>{{ user.birthdate }}</td>
							<td>Edit</td>
							<td>Delete</td>
					  </tr>
				</tbody>
	      </table>
	      
	      <nav aria-label="Page navigation example">
	        <ul class="pagination justify-content-center">
	            <li
		            class="page-item"
		            v-bind:class="{disabled : selectedPage === 1}"
		            v-on:click="changePageFor(-1)"
	            >
		            <p class="page-link pagination-item" aria-label="Previous">
		                <span aria-hidden="true">&laquo;</span>
		            </p>
	            </li>
	           
           		<li
	           		v-for="page in pages"
	           		class="page-item"
	           		v-bind:class="{active: page === selectedPage}"
	           		v-on:click="setPage(page)"
           		>
	            	<p class="page-link pagination-item">{{ page }}</p>
	           	</li>
	           
	            <li
		            class="page-item"
		            v-bind:class="{disabled: this.users.length < this.pageSize}"
		            v-on:click="changePageFor(1)"
	            >
		            <p class="page-link pagination-item" aria-label="Next">
		                <span aria-hidden="true">&raquo;</span>
		            </p>
	            </li>
	        </ul>
  		</nav>
  		
  	</div>
    `,

    data: function() {
        return {
            users: [],
            nextUsers: [],
            selectedUser: null,
            
            pageSize: 3,
            selectedPage: 1,
            pages: [1, 2, 3]
            
        }
    },
    
    methods: {
    	loadPageOfUsers : function(page, callback) {    		
    		axios.get('/WebProjekat/rest/users/page?number=' + page + "&size=" + this.pageSize)
             	 .then(response =>
             	 {
         	 		this.nextUsers = response.data;
         	 		callback();
         	 	});
    	},
    
    	selectUser: function(user) {
    		this.selectedUser = user;
		},
		
		setPage: function(pageNumber) {
			let changeFor = pageNumber - this.selectedPage;
			this.changePageFor(changeFor);
		},
		
		changePageFor: function(changeFor) {
			let nextPage = this.selectedPage;
			
			if(changeFor < 0 && this.selectedPage + changeFor >= 1) {
				nextPage += changeFor;
			} else if (changeFor > 0 && this.users.length === this.pageSize) { 
				nextPage += changeFor;
			}
			
			this.loadPageOfUsers(nextPage, () =>
			{
				if(this.nextUsers.length > 0) {
					this.users = this.nextUsers;
					this.selectedPage = nextPage;
					this.changePagesList();
				}
			});
		},
		
    	changePagesList: function() {
			if(this.selectedPage >= 2) {
				this.pages = [this.selectedPage - 1, this.selectedPage, this.selectedPage + 1];
			}
		}
    },

    mounted: function() {
    	this.loadPageOfUsers(this.selectedPage, () =>
    	{
    		this.users = this.nextUsers;
    	});
    }
});