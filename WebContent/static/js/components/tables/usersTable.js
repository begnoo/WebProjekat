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
  	</div>
    `,

	props: ["users", "selectedPage", "pageSize"],

    data: function() {
        return {
            selectedUser: null,
        }
    },
    
    methods: {
    
    	selectUser: function(user) {
    		this.selectedUser = user;
		},

    }
});