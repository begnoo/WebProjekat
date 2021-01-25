Vue.component("search-users-form", {
	template: `
    <div class="container mt-3">
		<div class = "row justify-content-center">
		<form class="form-inline">
		</form>
			<form>
				<div class="form-group row">
			    	<div class="col">
			      		<input v-model="username" type="text" class="form-control" id="inputUsername" placeholder="Username">
					</div>
					<div class="form-group row">
					    <div class="col-sm-10">
						    <div class="input-group">	
								<div class="btn-group" role="group" aria-label="Basic example">
									<button v-on:click="this.submitSearch" type="submit" class="btn btn-primary">Search</button>
									<button v-on:click="this.showHideForm" class="btn btn-primary dropdown-toggle"></button>
								</div>
							</div>
					    </div>
			    	</div>
			  	</div>
				<div v-show="showForm">
					<div class="form-group row">
						<div class="col-auto">
							<input v-model="name" type="text" class="form-control" id="inputName" placeholder="Name">
					    </div>
						<div class="col-auto">
						    <input v-model="surname" type="text" class="form-control" id="inputSurname" placeholder="Surename">
					    </div>
					</div>
			
					<div class="form-group row">
					    <label for="selectRole" class="col-sm-3 col-form-label">Role:</label>
					    <div class="col-auto">
							<select v-model="role" class="form-control" id="selectRole">
								<option value="">All</option>
								<option value="Seller">Seller</option>
								<option value="Buyer">Buyer</option>
								<option value="Administrator">Administartor</option>
							</select>
					    </div>
				 	</div>
			
					<div class="form-group row">
					    <label for="selectSort" class="col-sm-3 col-form-label">Sort by:</label>
					    <div class="col-auto">
							<select v-model="sortBy" class="form-control" id="selectSort">
								<option value="">None</option>
								<option value="username">Username</option>
								<option value="name">Name</option>
								<option value="surname">Surname</option>
							</select>
					    </div>
				 	</div>
				
					<div class="form-group row">
					    <label for="selectOrder" class="col-sm-3 col-form-label">Order by:</label>
					    <div class="col-auto">
							<select v-model="orderBy" class="form-control" id="selectOrder">
								<option value="Ascending">Ascending</option>
								<option value="Descending">Descending</option>
							</select>
					    </div>
				 	</div>
				</div>
			</form>
		</div>
	</div>
    `,


	data: function() {
		return {
			username: "",
			name: "",
			surname: "",
			role: "",
			sortBy: "",
			orderBy: "Ascending",
			showForm: false,
		}
	},

	methods: {
		submitSearch: function() {
			this.$emit("search-user-data", {
				username: this.username,
				name: this.name,
				surname: this.surname,
				role: this.role,
				sortBy: this.sortBy,
				orderBy: this.orderBy,
			});
		},
		showHideForm: function(){
			this.showForm = !this.showForm;
		}
	},

	created: function() {

	},

});
