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
							<th scope="col">Birthdate</th>
							<th v-if="block" scope=""></th>
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
							<td>{{ user.username }}</td>
							<td>{{ user.gender }}</td>
							<td>{{ user.birthdate }}</td>
						  <td v-if="block" style="text-align: center">
							<button class="btn btn-danger btn-sm" v-on:click="blockUser(user)">Block</button>
						  </td>	
					  </tr>
				</tbody>
	      </table>	      
  	</div>
    `,

	props: ["users", "selectedPage", "pageSize", "block"],

    data: function() {
        return {
            selectedUser: null,
        }
    },
    
    methods: {
    
    	selectUser: function(user) {
    		this.selectedUser = user;
		},
		
		blockUser: function(user) {
    		axios(deleteRestConfig("/WebProjekat/rest/users/" + user.id))
				.then(response => {
					this.$emit("deleted-user", response.data);
				})
				.catch(error => console.log(error));
		},
    }
});