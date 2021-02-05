Vue.component('login-form', {
    template:
    `
	<div class="container" style="min-height: 90vh;">
		<div class="row justify-content-center align-items-center" style="min-height: 90vh;">
			<div class="col-5">
				<div class="card">
					<div class="card-body">
						<form>
							<div class="mb-3">
								<label for="loginUsername" class="form-label">Username</label>
								<input type="text" class="form-control" id="loginUsername" v-model="username">
							</div>
							<div class="mb-3">
								<label for="loginPassword" class="form-label">Password</label>
								<input type="password" class="form-control" id="loginPassword" v-model="password">
				            </div>
				            <div class="d-flex d-flex justify-content-between">
					            <button v-on:click="login" class="btn btn-primary">Login</button>
					            <button v-on:click="redirectToRegister" class="btn btn-primary">Register</button>
				            </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
    `,

    data: function() {
        return {
            username: null,
            password: null,
			validators: {
				'loginUsername': [validateRequired()],
				'loginPassword': [validateRequired()]
			}
        };
    },

    methods:
    {
        login: function(event)
        {
            event.preventDefault();
        
			if(!executeValidation(this.validators)) {
				return;
			}
			
		    axios.post('/WebProjekat/rest/authorization', 
            {
                'username': this.username,
                'password': this.password
            }).then(response => this.onLoginSuccess(response))
              .catch(function(error)
              {
  	              	toastr.error(error.response.data.errorMessage, '');
              });
        },

		onLoginSuccess: function(response){
			this.clear();
			window.localStorage.setObject('loggedUser', response.data)
			store.state.userLoggedIn = true;
			this.$router.replace("/")
		},

        redirectToRegister: function(event)
        {
            this.$router.push('/register');
        },

		clear: function() {
			this.clearForm();
			clearLastValidation(this.validators);	
		},
		
		clearForm: function() {
			this.username = null;
			this.password = null;
		}
    }
});