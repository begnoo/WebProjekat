Vue.component('registration-page', {
    template:
    `
        <div class="container">
    		<form>
    			<account-info-form ref="account-info"></account-info-form>
    			<user-info-form ref="user-info"></user-info-form>
		  		<button v-on:click="createUser" type="submit" class="btn btn-primary">Register</button>
    		</form>
        </div>
    `,

    methods:
	{
    	
    	createUser: function(event) {
            event.preventDefault();
            
            let accountInfo = this.$refs['account-info'];
            let userInfo = this.$refs['user-info'];
            
            axios.post('/WebProjekat/rest/users/buyer', 
            {
                'username': accountInfo.username,
                'password': accountInfo.password,
                'name': userInfo.name,
                'surname': userInfo.surname,
                'gender': userInfo.gender,
                'birthdate': userInfo.birthdate + " 00:00"
                
            }
    
            ).then(response => alert("Uspesno") )
             .catch(function(error)
              {
                alert(error.response.data.errorMessage);  	
              });
        }
	}
});


