Vue.component('registration-page', {
    template:
    `
        <div class="container">
    		<form>
    			<account-info-form
    				v-bind:value="accountInfo"
    				@input="(newAccountInfo) => {accountInfo = newAccountInfo}"
    			></account-info-form>
    			<user-info-form
    				v-bind:value="userInfo"
    				@input="(newUserInfo) => {userInfo = newUserInfo}"
    			></user-info-form>
		  		<button v-on:click="createUser" type="submit" class="btn btn-primary">Register</button>
    		</form>
        </div>
    `,

    data: function () {
			return {
				accountInfo: {
					username: null,
					password: null
				},
				userInfo: {
					name: null,
					surname: null,
					gender: null,
					birthdate: null
				}
			}
    	
    },
    
    methods:
	{
    	
    	createUser: function(event) {
            event.preventDefault();
            
            axios.post('/WebProjekat/rest/users/buyer', 
            {
                'username': this.accountInfo.username,
                'password': this.accountInfo.password,
                'name': this.userInfo.name,
                'surname': this.userInfo.surname,
                'gender': this.userInfo.gender,
                'birthdate': this.userInfo.birthdate + " 00:00"
                
            }
    
            ).then(response => alert("Uspesno") )
             .catch(function(error)
              {
                alert(error.response.data.errorMessages);  	
              });
        }
	}
});


