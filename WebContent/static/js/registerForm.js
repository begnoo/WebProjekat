Vue.component('register-form', {
    template:
    `
        <div class="container">
            <form>
                <div class="form-row">
                    <div class="form-group col-md-6">
                    <label for="registrationUsername">Username</label>
                    <input v-model="username" type="text" class="form-control" id="registrationUsername" placeholder="Username">
                    </div>
                    <div class="form-group col-md-6">
                    <label for="registrationPassword">Password</label>
                    <input v-model="password" type="password" class="form-control" id="registrationPassword" placeholder="Password">
                    </div>
                </div>

                <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="registrationFirstname">First name</label>
                    <input v-model="name" type="text" class="form-control" id="registrationFirstname" placeholder="First name">
                </div>
                <div class="form-group col-md-6">
                    <label for="registrationSirstname">Second name</label>
                    <input v-model="surname" type="text" class="form-control" id="registrationSirstname" placeholder="Second name">
                </div>
                </div>

                <div class="form-row">
                    <div class="form-group col">
                    <label for="registrationBirthdate">Birthdate</label>
                    <input v-model="birthdate" type="date" class="form-control" id="registrationBirthdate" placeholder="Birthdate">
                    </div>
                </div>
                
                <div class="form-row">
                <div class="form-group col">
                    <label for="registrationGender">Gender</label>
                    <select v-model="gender" id="registrationGender" class="form-control">
                    <option selected value="Male">Male</option>
                    <option value="Female">Female</option>
                    </select>
                </div>
                </div>
                
                <button v-on:click="createUser" class="btn btn-primary">Register</button>
            </form>
        </div>
    `,

    data: function() {
        return {
            username: null,
            password: null,
            name: null,
            surname: null,
            gender: null,
            birthdate: null
        }
    },

    methods: {
        createUser: function(event) {
            event.preventDefault();

            axios.post('/WebProjekat/rest/users/buyer', 
            {
                'username': this.username,
                'password': this.password,
                'name': this.name,
                'surname': this.surname,
                'gender': this.gender,
                'birthdate': this.birthdate + " 00:00"
                
            }
    
            ).then(response => alert("Uspesno") )
             .catch(function(error)
              {
                alert(error.response.data.errorMessage);  	
              });
        }
    }
});