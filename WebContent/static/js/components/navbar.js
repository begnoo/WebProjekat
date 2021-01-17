Vue.component("navbar", {
    template: `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <a class="navbar-brand" href="#">WebProjekat</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item active">
	        <router-link class="nav-link" to="/">Home <span class="sr-only">(current)</span></router-link>
	      </li>
	      <li v-for="option in activeOptions" :key="option.name" class="nav-item active">
	        <router-link :to="option.path" class="nav-link">{{option.name}}</router-link>
	      </li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li v-show="this.sharedState.userLoggedIn" class="nav-item active">
	        	<a class="nav-link"> {{loggedUsername}} </a>
	      	</li>
			<li v-if="this.sharedState.userLoggedIn" class="nav-item active">
	        	<a v-on:click="logout" href="#" class="nav-link">Logout</a>
	      	</li>
			<li v-else class="nav-item active">
	        	<router-link to="/login" href="#" class="nav-link">Login</router-link>
	      	</li>
		</ul>
	  </div>
	</nav>
    `,

	data: function(){
		return {
			sharedState: store.state,
			loggedUsername: null,
			activeOptions: [],
			defaultOptions: [],
			buyerOptions: [],
			sellerOptions: [				
				{name : "ManifestationForm", path : "/add-manifestation"},
				{name : "ManifestationFormImage", path : "/add-manifestation-image"},],
			adminOptions: [
				{name : "LocationForm", path : "/add-location"},
				{name : "Users", path : "/users"},
			],
		}
	},
	
	mounted: function(){
		this.sharedState.userLoggedIn = !!window.localStorage.getObject("loggedUser");
		this.changeOptions();
	},
	
	
	watch: {
		"sharedState.userLoggedIn" : function(){
			this.changeOptions();
		}
	},
	
	methods: {
		changeOptions: function(){
			if(this.sharedState.userLoggedIn){
				
				const {role, username} = window.localStorage.getObject("loggedUser").user;
				this.loggedUsername = username;
				
				switch(role) {
				case "Buyer":
					this.activeOptions = this.buyerOptions;
					break;
				case "Seller":
					this.activeOptions = this.sellerOptions;
					break;
				case "Administrator":
					this.activeOptions = this.adminOptions;
				    break;
				  default:
				    this.activeOptions = this.defaultOptions;
				}
			}else{
				this.activeOptions = this.defaultOptions;
			}
		},
		
		logout: function(event){
			event.preventDefault();
			window.localStorage.removeItem("loggedUser");
			this.sharedState.userLoggedIn = false;
			this.sharedState.loggedUsername = null;
			this.$router.replace("/")
		}
	}
});
