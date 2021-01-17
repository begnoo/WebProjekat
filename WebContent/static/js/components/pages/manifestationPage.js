Vue.component("manifestation-page", {
	template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-10">
			{{this.manifestation}}
			<h1>{{this.manifestation.name}}</h1>
			<h3>{{this.manifestation.type}}</h3>
			<nav class="nav nav-pills nav-justified mt-8">
			  <a class="nav-item nav-link active" href="#">Info</a>
			  <a class="nav-item nav-link" href="#">Get tickets</a>
			</nav>
			<manifestation-info :manifestation="this.manifestaion"></manifestation-info>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    `,
	data: function() {
		return {
			manifestation: {},
		}
	},

	methods: {
		getManifestation: function(id) {
			axios.get("/WebProjekat/rest/manifestations/" + id)
				.then(response => this.manifestation = response.data)
				.catch(error => console.log(error));
		}

	},

	mounted: function() {
		let manifestationId = this.$route.params.id;
		this.getManifestation(manifestationId);
	}
});
