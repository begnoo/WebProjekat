Vue.component('account-info-form', {
    template:
    `
    	<div>
		    <div class="form-row">
		        <div class="form-group col-md-6">
			        <label for="registrationUsername">Username</label>
			        <input v-model="value.username" type="text" class="form-control" id="registrationUsername" placeholder="Username">
		        </div>
		        <div class="form-group col-md-6">
			        <label for="registrationPassword">Password</label>
			        <input v-model="value.password" type="password" class="form-control" id="registrationPassword" placeholder="Password">
		        </div>
		    </div>
		</div>
    `,

    props: ['value'],
    
    watch: {
        value() {
            this.$emit('input', this.value);
        }
    }

});