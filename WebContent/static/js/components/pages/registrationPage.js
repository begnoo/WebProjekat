Vue.component('registration-page', {
	template:
		`
		<div class="container">
			<registration-form
			userType="buyer"
			v-on:create-user-success="redirectToLogin"
			></registration-form>
		</div>
    `,


	data: function() {
		return {
		};
	},

	methods: {
		redirectToLogin: function() {
			this.$router.replace('/login');
		}
	}
});


