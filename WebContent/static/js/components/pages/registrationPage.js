Vue.component('registration-page', {
	template:
		`
		<div class="container">
		<div class="row justify-content-center align-items-center" style="min-height: 90vh;">
			<div class="col-5">
				<div class="card">
					<div class="card-body">
						<registration-form
						userType="buyer"
						v-on:create-user-success="redirectToLogin"
						></registration-form>
					</div>
				</div>
			</div>
		</div>

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


