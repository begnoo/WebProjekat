Vue.component("manifestation-tickets", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
				<div v-if="!sharedState.userLoggedIn">
					<p>Please log in to access the tickets page.</p>
				</div>
				<div v-else>
					{{this.manifestation}}
				</div>
            </div>
        </div>
    </div>
    `,

	props: ["manifestation"],
	
	data: function(){
		return {
			sharedState: store.state,
		};
	},
});
