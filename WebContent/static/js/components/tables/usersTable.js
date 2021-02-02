Vue.component('users-table',
{
    template:
    `
    	<div class="container">
	        <table class="table">
				<thead class="thead-dark">
					  <tr>
							<th scope="col">Name</th>
							<th scope="col">Surname</th>
							<th scope="col">Username</th>
							<th scope="col">Gender</th>
							<th scope="col">Role</th>
							<th scope="col">Birthdate</th>
							<th scope=""></th>
					  </tr>
				</thead>
				<tbody>
					  <tr
					  	v-for="(user, index) in users"
					  	v-on:click="selectUser(user)"
					  	v-bind:class="{ 'table-active' : selectedUser === user}"
					  >
							<td>{{ user.name }}</td>
							<td>{{ user.surname }}</td>
							<td>
								<router-link v-if="user.role == 'Buyer'" :to="'/buyer-tickets/' + user.id" href="#" class="nav-link">{{ user.username }}</router-link>
								<router-link v-if="user.role == 'Seller'" to="/users" href="#" class="nav-link">{{ user.username }}</router-link>
								<span v-if="user.role == 'Administrator'" class="nav-link">{{ user.username }}</span>
							</td>
							<td>{{ user.gender }}</td>
							<td>{{ user.role }}</td>
							<td>{{ user.birthdate }}</td>
						  <td style="text-align: center">
							<button 
							 v-if="user.role != 'Administrator'"
							 type="button"
							 class="btn btn-danger btn-sm"
							 data-toggle="modal"
							 data-target="#blockUser"
						 	 v-on:click="userToBlock = user">
							 	Block
							 </button>
						  </td>	
					  </tr>
				</tbody>
	      </table>
		  <custom-modal modalName="blockUser" title="Block user">
			<div class="container">
				<div class="row">
					<p>Are you sure, that you want to block this user?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="blockUser(userToBlock)">Yes</button>
					<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#blockUser">No</button>
				</div>
			</div>
		  </custom-modal>
  	</div>
    `,

	props: ["users", "selectedPage", "pageSize", "block"],

    data: function() {
        return {
            selectedUser: null,
			userToBlock: null,
        }
    },
    
    methods: {
    
    	selectUser: function(user) {
    		this.selectedUser = user;
		},
		
		blockUser: function(user) {
    		axios(deleteRestConfig("/WebProjekat/rest/users/" + user.id))
				.then(response => {
					toastr.success(`${user.username} is successfully blocked.`, '');
					this.$emit("deleted-user", response.data);
				})
				.catch(error => toastr.error(error.response.data.errorMessage, ''));
		},
    }
});