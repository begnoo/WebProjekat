Vue.component('account-page', {
    template:
    `
        <div class="container">
    		<form>
    			<user-info-form
    				v-bind:value="userInfo"
    				@input="(newUserInfo) => {userInfo = newUserInfo}"
    			></user-info-form>
    			
    			<div v-if="userRole==='Buyer'">
	    			<div class="form-row">
			            <div class="form-group col-md-6">
			                <label for="buyerTypeName">Buyer type</label>
			                <input v-model="buyerInfo.buyerTypeName" type="text" class="form-control" id="buyerTypeName" style="background-color: white;" disabled>
			            </div>
			            <div class="form-group col-md-6">
			                <label for="buyerPoints">Points</label>
			                <input v-model="buyerInfo.points" type="text" class="form-control" id="buyerPoints" style="background-color: white;" disabled>
			            </div>
			        </div>
    			</div>
    			
    			
		  		<button v-on:click="updateUser" type="submit" class="btn btn-primary">Update</button>
    		</form>
        </div>
    `,

    data: function () {
			return {
				userInfo: {
					name: null,
					surname: null,
					gender: null,
					birthdate: null
				},
				buyerInfo: {
					buyerTypeName: null,
					points: null
				},
				userRole: null,
			}
    	
    },
    
    methods:
	{
    	updateUser: function(event) {
            event.preventDefault();
            
			let loggedUser = localStorage.getObject('loggedUser');
			let userId = loggedUser.user.id;	

            axios(putRestConfig('/WebProjekat/rest/users', {},
            {
            	'id': userId,
                'name': this.userInfo.name,
                'surname': this.userInfo.surname,
                'gender': this.userInfo.gender,
                'birthdate': this.userInfo.birthdate + " 00:00"
            })).then(response => {
            	loggedUser.user = response.data;
            	localStorage.setObject('loggedUser', loggedUser);
            })
             .catch(function(error)
              {
                alert(error.response.data.errorMessages);  	
              });
        }
	},
	
	mounted: function()
	{
		let loggedUser = localStorage.getObject('loggedUser');
		this.userInfo.name = loggedUser.user.name;
		this.userInfo.surname = loggedUser.user.surname;
		this.userInfo.gender = loggedUser.user.gender;
		this.userInfo.birthdate = loggedUser.user.birthdate.split(' ')[0];
		this.userRole = loggedUser.user.role;
		
		if(this.userRole === "Buyer") {
			this.buyerInfo.buyerTypeName = loggedUser.user.type.name;
			this.buyerInfo.points = loggedUser.user.points;
		}
	}		
});


