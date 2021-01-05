Vue.component('login-form', {
    template:
    `
	<div class="d-flex justify-content-center min-vh-100 align-items-center">
		<div class="row">
			<form>
			<div class="mb-3">
				<label for="textField" class="form-label">Username</label>
				<input type="text" class="form-control" id="textField" v-model="username">
			</div>
			<div class="mb-3">
				<label for="inputPassword" class="form-label">Password</label>
				<input type="password" class="form-control" id="inputPassword" v-model="password">
            </div>
            <div class="d-flex d-flex justify-content-between">
            <button v-on:click="login" class="btn btn-primary">Login</button>
            <button v-on:click="redirectToRegister" class="btn btn-primary">Register</button>
            </div>
			</form>
		</div>
	</div>
    `,

    data: function() {
        return {
            username: null,
            password: null,
        }
    },

    methods:
    {
        login: function(event)
        {
            event.preventDefault();
            axios.post('/WebProjekat/rest/auhtorization', 
            {
                'username': this.username,
                'password': this.password
            }).then(response => 
				window.localStorage.setObject('loggedUser', response.data)
			  )
              .catch(function(error)
              {
  	              	alert(error.response.data.errorMessage);
              	
              });
        },

        redirectToRegister: function(event)
        {
            this.$router.push('/register');
        }
    }
});